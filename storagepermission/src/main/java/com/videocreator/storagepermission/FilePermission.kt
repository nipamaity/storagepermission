package com.videocreator.storagepermission

import android.content.DialogInterface
import android.os.Bundle

import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment

import com.videocreator.storagepermission.GlobleConstant.Companion.addPermission
import com.videocreator.storagepermission.GlobleConstant.Companion.requestStoragePermissionCheckList
import com.videocreator.storagepermission.GlobleConstant.Companion.storagePermissionsArray
import com.videocreator.storagepermission.GlobleConstant.Companion.storagePermissionsArrayAbouve30
import com.videocreator.storagepermission.interfaces.OnRespondsListner

internal class FilePermission(builder:Builder) : DialogFragment() {

    lateinit var ListOfStorage : List<String>
    private var onRespondsListner: OnRespondsListner?

    init {
        this.onRespondsListner = builder.onRespondsListner
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestStoragePermissionCheck()
    }


    private fun requestStoragePermissionCheck() {

        ListOfStorage=requestStoragePermissionCheckList(requireActivity())
        if(ListOfStorage.size>0){
            requestPermissionsLauncher.launch(ListOfStorage.toTypedArray())
        }else{
            permissionGrant = true
            onRespondsListner?.onResult(true,"All storage permission granted")
            dismissAllowingStateLoss()
        }
    }
    private var permissionGrant=false
    private val requestPermissionsLauncher =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
            // Check if all permissions are granted
            val allPermissionsGranted = permissions.all { it.value }

            if (allPermissionsGranted) {
                // All permissions are granted, you can now perform your tasks

                permissionGrant=true
                onRespondsListner?.onResult(true,"All storage permission granted")
            } else {
                // Some or all permissions are denied, handle accordingly
                // Example: showPermissionDeniedDialog()
                onRespondsListner?.onResult(false,"Some permission is denied")
                somepermissondenied()
            }
            dismissAllowingStateLoss()
        }
    private fun somepermissondenied(){

    }

    class Builder(val appCompactActivity:AppCompatActivity){
        var permissions: List<String> = arrayListOf()
            private set
        var onRespondsListner: OnRespondsListner? = null
            private set
        fun setPermissions(permissionsSet:List<String>)=apply { permissions=permissionsSet }
        fun build() = FilePermission(this)
        fun setOnRespondsListner(
            onRespondsListner: OnRespondsListner?
        ) = apply { this.onRespondsListner = onRespondsListner }
        fun buildAndShow() = build().show(
            appCompactActivity.supportFragmentManager,
            "dialog.fragment"
        )

    }
    override fun onDismiss(dialog: DialogInterface) {
       // isShown = false
        super.onDismiss(dialog)
    }

    override fun onStop() {
        super.onStop()

    }
    override fun onDestroyView() {
        super.onDestroyView()

        requestPermissionsLauncher.unregister()

    }

}