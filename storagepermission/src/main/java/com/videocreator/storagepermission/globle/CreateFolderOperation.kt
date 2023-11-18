package com.videocreator.storagepermission.globle

import android.content.Context
import android.os.Environment
import com.videocreator.storagepermission.GlobleConstant
import java.io.File

internal class CreateFolderOperation (val context: Context,val folderName:String){
    private var createdFolder: File?=null
    private var errorMessage: String=""

   fun versionCheckForCreatFolder():Pair<File?, String>{
        if (GlobleConstant.sdkEqOrAbove30()) {
            createdFolder=  createFolderAbove30()
        }else{
            createdFolder=createMyFolderBelow()
        }
        return Pair(createdFolder,errorMessage)
    }
    fun createFolderAbove30() :File?{
        val documentsDirectory = File(
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS),
            folderName )
        try {
            if (!documentsDirectory.exists() || !documentsDirectory.isDirectory) {
                // Create the directory if it doesn't exist
                if (documentsDirectory.mkdirs()) {
                    // Directory created successfully

                }
            }
        } catch (e: Exception) {
             errorMessage= e.message.toString()
        }
        return documentsDirectory
    }
    fun createMyFolderBelow() :File?{
        val externalStorageDir = Environment.getExternalStorageDirectory()
        val documentsDirectory = File(externalStorageDir, folderName)
        try {
            if (!documentsDirectory.exists()) {
                if (documentsDirectory.mkdirs()) {
                    // Directory created successfully
                }
            }
        }catch (e: Exception) {
            // Handle folder creation exception

            errorMessage= e.message.toString()
        }
        return documentsDirectory
    }
}