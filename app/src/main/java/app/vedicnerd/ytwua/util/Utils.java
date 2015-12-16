package app.vedicnerd.ytwua.util;

import android.content.Context;
import android.widget.Toast;

public class Utils {

    public static void showToast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

}