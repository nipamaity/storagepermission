package com.storagepermission

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import com.videocreator.storagepermission.interfaces.OnRespondsListner
import createActivity
import permissionRequest
import java.io.File

class MainActivity : AppCompatActivity() {
    private var permissionGrant=false
    private lateinit var clickMe: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        clickMe=findViewById(R.id.cliclMe)
        permissionRequest(onRespondsListner = object :OnRespondsListner{
            override fun onResult(result: Boolean,
                                  message: String,
                                  file: File?,
                                  filePath: String?) {

                if(result){
                    permissionGrant=true

                    Toast.makeText(applicationContext,message,Toast.LENGTH_LONG).show()
                    // if grant please call your function to execute
                }else{
                    permissionGrant=false
                    Toast.makeText(applicationContext,message,Toast.LENGTH_LONG).show()
                }
            }


        })
        /*
* for create folder and sub folder just use folder1/folder2
*
* */
// provide folder name  folder and subfolder name can provide by /
        val folderName="fotestfolderbypermission/filepermission"
        clickMe.setOnClickListener {
            createActivity(foldername = folderName, onRespondsListner = object :OnRespondsListner{
                override fun onResult(
                    result: Boolean,
                    message: String,
                    file: File?,
                    filePath: String?
                ) {

                    if(result){
                        permissionGrant=true
                        Toast.makeText(applicationContext,message+ " path: "+filePath,Toast.LENGTH_LONG).show()
                        // if grant please call your function to execute
                    }else{
                        permissionGrant=false
                        Toast.makeText(applicationContext,message,Toast.LENGTH_LONG).show()
                    }
                }

            })
        }
    }
}