package in.mol.models;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by 1742 on 11-04-2016.
 */
public class Utilities {

    /* show message */
    public static void showMessage(String msg, Context context) {

        Toast toast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);

        if (toast.getView() instanceof LinearLayout) {

            LinearLayout toastLayout = (LinearLayout) toast.getView();

            TextView toastTV = (TextView) toastLayout.getChildAt(0);
            toastTV.setTextSize(18);
        } else if (toast.getView() instanceof RelativeLayout) {
            RelativeLayout toastLayout = (RelativeLayout) toast.getView();
            TextView toastTV = (TextView) toastLayout.getChildAt(0);
            toastTV.setTextSize(18);
        }
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivity = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity == null) {
            return false;
        } else {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null) {
                for (int i = 0; i < info.length; i++) {
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;  //<--  --  -- Connected
                    }
                }
            }
        }
        return false;  //<--  --  -- NOT Connected
    }

    public static Bitmap setToImageView(String selectedImagePath) {
        Bitmap bm;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(selectedImagePath, options);
        final int REQUIRED_SIZE = 100;
        int scale = 1;
        while (options.outWidth / scale / 2 >= REQUIRED_SIZE
                && options.outHeight / scale / 2 >= REQUIRED_SIZE)
            scale *= 2;
        options.inSampleSize = scale;
        options.inJustDecodeBounds = false;
        bm = BitmapFactory.decodeFile(selectedImagePath, options);

        return bm;
    }

    public static boolean chkEmptyEditText(EditText... edt) {
        boolean flag = true;
        for (int i = 0; i < edt.length; i++) {
            if (edt[i].getText().toString().trim().length() == 0) {
                edt[i].setError("Enter value");
                edt[i].requestFocus();
                flag = false;
            } else {
                edt[i].setError(null);
            }
        }
        return flag;
    }

    public static void invisibleAll(LinearLayout... linearLayout) {
        for (int i = 0; i < linearLayout.length; i++) {
            linearLayout[i].setVisibility(View.GONE);
        }
    }

    public static void visibleAll(LinearLayout... linearLayout) {
        for (int i = 0; i < linearLayout.length; i++) {
            linearLayout[i].setVisibility(View.VISIBLE);
        }
    }
}
