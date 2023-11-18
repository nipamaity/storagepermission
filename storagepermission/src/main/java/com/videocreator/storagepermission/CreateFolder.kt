package com.videocreator.storagepermission

import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import com.videocreator.storagepermission.globle.CreateFolderOperation
import com.videocreator.storagepermission.interfaces.OnRespondsListner
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CreateFolder(builder:Builder) : DialogFragment() {
    lateinit var ListOfStorage : List<String>
    private var onRespondsListner: OnRespondsListner?
    private var folderName:String

    init {
        this.onRespondsListner = builder.onRespondsListner
        this.folderName=builder.folderName
    }

    private var permissionGrant=false
    private fun requestStoragePermissionCheck() {

        ListOfStorage= GlobleConstant.requestStoragePermissionCheckList(requireActivity())
        if(ListOfStorage.size>0){
            requestPermissionsLauncher.launch(ListOfStorage.toTypedArray())
        }else{
            permissionGrant = true
            createFolderByName()
        }
    }
    private val requestPermissionsLauncher =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
            // Check if all permissions are granted
            val allPermissionsGranted = permissions.all { it.value }

            if (allPermissionsGranted) {
                permissionGrant=true
                createFolderByName()
            } else {
                permissionGrant=false
                permissionNotGrand("Some permisson is denied")
            }

        }
    fun permissionNotGrand(message:String){
        onRespondsListner?.onResult(false,message)
        dismissAllowingStateLoss()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestStoragePermissionCheck()
    }
    override fun onStart() {
        super.onStart()
        if(permissionGrant){
            // create folder
            createFolderByName()

        }else{
            permissionNotGrand("Permission is denied")
        }
    }
    fun createFolderByName(){
         val coroutineScope = CoroutineScope(Dispatchers.Main)
        coroutineScope.launch {
            val folderResult=  CreateFolderOperation(requireContext(), folderName).versionCheckForCreatFolder()
            coroutineScope.launch(Dispatchers.Main) {
                onRespondsListner?.onResult(result = if(folderResult.first!=null) true else false,
                    message = if(folderResult.first!=null) "Successfully folder create" else " Failure to create folder",
                    file = folderResult.first,
                    filePath = folderResult.second)
                dismissAllowingStateLoss()
            }

        }

    }
    class Builder(val appCompactActivity: AppCompatActivity){

         var folderName:String=""
            set(value) {
                if (value.isNotBlank()) {
                    field = value
                } else {
                  // throw IllegalArgumentException("Value cannot be blank.")
                    onRespondsListner?.onResult(false,"Folder folderName empty")
                }
            }
        var onRespondsListner: OnRespondsListner? = null
            private set

        fun build() = CreateFolder(this)
        fun setfolderName(folderName:String)=apply { this.folderName =folderName}
        fun setOnRespondsListner(
            onRespondsListner: OnRespondsListner?
        ) = apply { this.onRespondsListner = onRespondsListner }
        fun buildAndShow() = build().show(
            appCompactActivity.supportFragmentManager,
            "dialog.fragment"
        )
    }
}