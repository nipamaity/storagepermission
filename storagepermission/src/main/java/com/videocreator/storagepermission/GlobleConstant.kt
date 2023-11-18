package com.videocreator.storagepermission

import android.content.pm.PackageManager

import android.os.Build

import androidx.annotation.ChecksSdkIntAtLeast

import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity


internal class GlobleConstant {
    companion object {
        val storagePermissionsArray = arrayOf(
            android.Manifest.permission.READ_EXTERNAL_STORAGE,
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE
        )
        val storagePermissionsArrayAbouve30 = arrayOf(
            android.Manifest.permission.READ_MEDIA_IMAGES,
            android.Manifest.permission.READ_MEDIA_VIDEO,
            android.Manifest.permission.READ_MEDIA_AUDIO
        )

        @ChecksSdkIntAtLeast(api = Build.VERSION_CODES.TIRAMISU)
        fun sdkEqOrAbove33() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU

        @ChecksSdkIntAtLeast(api = Build.VERSION_CODES.R)
        public  fun sdkEqOrAbove30() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.R

       fun addPermission(activity: FragmentActivity, storageArray: Array<String>): List<String> {
              val permissionsToRequest = storageArray.filter {
                ContextCompat.checkSelfPermission(activity, it) != PackageManager.PERMISSION_GRANTED
            }
            return permissionsToRequest
        }

         fun requestStoragePermissionCheckList(activity: FragmentActivity) : List<String>{
            lateinit var ListOfStorage : List<String>
            if (sdkEqOrAbove33()) {
                ListOfStorage= addPermission(activity,storagePermissionsArrayAbouve30)
            }else {

                ListOfStorage= addPermission(activity,storagePermissionsArray)

            }
            return ListOfStorage

        }

    }


}
