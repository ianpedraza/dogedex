package com.ianpedraza.dogedex.ui.home

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts.*
import androidx.appcompat.app.AlertDialog
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.ianpedraza.dogedex.R
import com.ianpedraza.dogedex.databinding.FragmentHomeBinding
import com.ianpedraza.dogedex.utils.ViewExtensions.Companion.createOutputDirectory
import com.ianpedraza.dogedex.utils.ViewExtensions.Companion.showToast
import com.ianpedraza.dogedex.utils.permissions.CheckPermissionsManager
import com.ianpedraza.dogedex.utils.permissions.CheckPermissionsUtil
import com.ianpedraza.dogedex.utils.permissions.areAllPermissionsGranted
import dagger.hilt.android.AndroidEntryPoint
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding: FragmentHomeBinding get() = _binding!!

    private val navController: NavController get() = findNavController()

    private val viewModel: CameraViewModel by viewModels()

    private val permissions = arrayOf(android.Manifest.permission.CAMERA)

    private lateinit var imageCapture: ImageCapture
    private lateinit var cameraExecutor: ExecutorService

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
        subscribeObservers()
    }

    private fun setupUI() {
        binding.fabHomeSettings.setOnClickListener {
            navController.navigate(HomeFragmentDirections.actionHomeFragmentToSettingsFragment())
        }

        binding.fabHomeDogsList.setOnClickListener {
            navController.navigate(HomeFragmentDirections.actionHomeFragmentToListFragment())
        }

        binding.fabHomeTakePhoto.setOnClickListener {
            takePhoto()
        }

        CheckPermissionsUtil.check(this, permissions, permissionManager)
    }

    private fun subscribeObservers() {
        viewModel.isCameraReady.observe(viewLifecycleOwner) { isCameraReady ->
            binding.fabHomeTakePhoto.isEnabled = isCameraReady
        }

        viewModel.takenPhoto.observe(viewLifecycleOwner) { takenPhoto ->
            takenPhoto?.let {
                openPhoto(takenPhoto)
                viewModel.handledTakenPhoto()
            }
        }
    }

    private val permissionManager = object : CheckPermissionsManager {
        override fun onAlreadyGranted() = setupCamera()
        override fun onRequestPermissionRationale() = requestPermissionRationale()
        override fun onRequest(permissions: Array<String>) = requestPermission()
    }

    private val requestPermissionLauncher by lazy {
        registerForActivityResult(RequestMultiplePermissions()) {
            if (requireContext().areAllPermissionsGranted(permissions)) {
                setupCamera()
            } else {
                requireContext().showToast(R.string.should_accept_camera_permission)
            }
        }
    }

    private fun requestPermission() = requestPermissionLauncher.launch(permissions)

    private fun requestPermissionRationale() {
        AlertDialog.Builder(requireContext())
            .setTitle(R.string.accept_camera_permission)
            .setMessage(
                getString(
                    R.string.camera_permissions_its_needed,
                    getString(R.string.app_name)
                )
            ).setPositiveButton(android.R.string.ok) { _, _ ->
                requestPermission()
            }.setNegativeButton(android.R.string.cancel, null)
            .create()
            .show()
    }

    private fun setupCamera() {
        binding.previewViewCamera.post {
            imageCapture = ImageCapture.Builder()
                .setTargetRotation(binding.previewViewCamera.display.rotation)
                .build()

            cameraExecutor = Executors.newSingleThreadExecutor()

            startCamera()

            viewModel.setCameraReady()
        }
    }

    private fun startCamera() {
        val cameraProvideFeature = ProcessCameraProvider.getInstance(requireContext())

        cameraProvideFeature.addListener({
            val cameraProvider = cameraProvideFeature.get()

            val cameraSelector = CameraSelector.Builder()
                .requireLensFacing(CameraSelector.LENS_FACING_BACK)
                .build()

            val preview = Preview.Builder().build()
            preview.setSurfaceProvider(binding.previewViewCamera.surfaceProvider)

            val imageAnalysis = ImageAnalysis.Builder()
                .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                .build()

            imageAnalysis.setAnalyzer(cameraExecutor) { imageProxy ->
                val rotationDegrees = imageProxy.imageInfo.rotationDegrees
                imageProxy.close()
            }

            cameraProvider.bindToLifecycle(
                viewLifecycleOwner,
                cameraSelector,
                preview,
                imageCapture,
                imageAnalysis
            )
        }, ContextCompat.getMainExecutor(requireContext()))
    }

    private fun takePhoto() {
        val photoName = getString(R.string.app_name) + ".jpg"
        val outputDirectory = requireActivity().createOutputDirectory(photoName)
        val outputFileOptions = ImageCapture.OutputFileOptions.Builder(outputDirectory).build()
        imageCapture.takePicture(outputFileOptions, cameraExecutor, imageSaveCallback)
    }

    private val imageSaveCallback by lazy {
        object : ImageCapture.OnImageSavedCallback {
            override fun onError(error: ImageCaptureException) {
                requireContext().showToast(R.string.error_taking_photo)
            }

            override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                viewModel.setTakenPhoto(outputFileResults.savedUri)
            }
        }
    }

    private fun openPhoto(photoUri: Uri) {
        val action = HomeFragmentDirections.actionHomeFragmentToPhotoViewerFragment(photoUri)
        findNavController().navigate(action)
    }

    override fun onDestroy() {
        super.onDestroy()
        if (::cameraExecutor.isInitialized) cameraExecutor.shutdown()
    }
}
