import androidx.appcompat.app.AppCompatActivity
import com.videocreator.storagepermission.CreateFolder
import com.videocreator.storagepermission.interfaces.OnRespondsListner

fun AppCompatActivity.createActivity(onRespondsListner : OnRespondsListner? = null, foldername:String,)=
    CreateFolder.Builder(this)
        .setfolderName(foldername)
        .setOnRespondsListner(onRespondsListner)
        .buildAndShow()