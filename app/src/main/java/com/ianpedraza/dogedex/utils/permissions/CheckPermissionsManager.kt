package com.ianpedraza.dogedex.utils.permissions

interface CheckPermissionsManager {
    fun onAlreadyGranted()
    fun onRequestPermissionRationale()
    fun onRequest(permissions: Array<String>)
}
