import androidx.appcompat.app.AppCompatActivity
import com.videocreator.storagepermission.FilePermission
import com.videocreator.storagepermission.interfaces.OnRespondsListner

fun AppCompatActivity.permissionRequest( onRespondsListner : OnRespondsListner? = null,) =
    FilePermission.Builder(this).setOnRespondsListner(onRespondsListner).buildAndShow()