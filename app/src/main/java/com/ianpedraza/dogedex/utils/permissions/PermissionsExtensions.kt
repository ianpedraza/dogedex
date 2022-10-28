package com.ianpedraza.dogedex.utils.permissions

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment

fun Activity.shouldShowRequestPermissionsRationale(permissions: Array<String>): Boolean {
    return permissions.fold(true) { value, item ->
        return if (ActivityCompat.shouldShowRequestPermissionRationale(this, item)) {
            value
        } else {
            false
        }
    }
}

fun Fragment.shouldShowRequestPermissionsRationale(permissions: Array<String>): Boolean {
    return permissions.fold(true) { value, item ->
        return if (shouldShowRequestPermissionRationale(item)) {
            value
        } else {
            false
        }
    }
}

fun Context.areAllPermissionsGranted(permissions: Array<String>): Boolean {
    return permissions.fold(true) { value, item ->
        return if (isPermissionGranted(item)) {
            value
        } else {
            false
        }
    }
}

fun Context.isPermissionGranted(permission: String): Boolean {
    return ContextCompat.checkSelfPermission(
        this,
        permission
    ) == PackageManager.PERMISSION_GRANTED
}
