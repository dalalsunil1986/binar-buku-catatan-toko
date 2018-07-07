package galih.binar.bukucatatantoko.Helper;

import android.content.Context;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;

/**
 * Created by Galih Laras Prakoso on 7/7/2018.
 */

public class Common {
    Context context;
    MaterialDialog loading;

    public Common(Context context){
        this.context = context;
    }

    public void showT(String text){
        Toast.makeText(context,text,Toast.LENGTH_SHORT).show();
    }

    public void showL(String text){
        MaterialDialog.Builder builder = new MaterialDialog.Builder(context)
                .title("Loading").content(text).cancelable(false).progress(true,0);

        loading = builder.show();
    }

    public void disL(){
        loading.dismiss();
    }
}
