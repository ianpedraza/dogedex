package com.ianpedraza.dogedex.utils.permissions

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

object CheckPermissionsUtil {
    fun check(
        activity: AppCompatActivity,
        permissions: Array<String>,
        permissionsManager: CheckPermissionsManager
    ) {
        when {
            activity.areAllPermissionsGranted(permissions) -> permissionsManager.onAlreadyGranted()
            activity.shouldShowRequestPermissionsRationale(permissions) -> {
                permissionsManager.onRequestPermissionRationale()
            }
            else -> permissionsManager.onRequest(permissions)
        }
    }

    fun check(
        fragment: Fragment,
        permissions: Array<String>,
        permissionsManager: CheckPermissionsManager
    ) {
        when {
            fragment.requireContext()
                .areAllPermissionsGranted(permissions) -> permissionsManager.onAlreadyGranted()
            fragment.shouldShowRequestPermissionsRationale(permissions) -> {
                permissionsManager.onRequestPermissionRationale()
            }
            else -> permissionsManager.onRequest(permissions)
        }
    }
}
