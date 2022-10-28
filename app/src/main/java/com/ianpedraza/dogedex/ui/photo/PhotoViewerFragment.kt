package com.ianpedraza.dogedex.ui.photo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.ianpedraza.dogedex.databinding.FragmentPhotoViewerBinding
import com.ianpedraza.dogedex.utils.ViewExtensions.Companion.fromFile
import java.io.File

class PhotoViewerFragment : Fragment() {

    private var _binding: FragmentPhotoViewerBinding? = null
    private val binding: FragmentPhotoViewerBinding get() = _binding!!

    private val args: PhotoViewerFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPhotoViewerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
    }

    private fun setupUI() {
        args.photoUri.path?.let { path ->
            val photo = File(path)
            binding.imageViewPhotoViewer.fromFile(photo)
        }
    }
}
