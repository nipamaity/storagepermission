package com.videocreator.storagepermission.interfaces

import java.io.File

interface OnRespondsListner {
fun onResult( result: Boolean,message:String,file: File?=null,filePath:String?=null)
}