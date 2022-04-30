package fit.android.app.helper;

import android.content.Context;
import android.content.DialogInterface;
import android.location.GnssAntennaInfo;

import androidx.appcompat.app.AlertDialog;

public class Message {

    //show modal message in app
    public static void showMessage(Context context, String title, String message) {
        AlertDialog alertDialog = new AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(message).show();
    }

    //show modal confirm message in app - send data from interface
    // 1 -> true, 0 -> false
    // context get result and handle
    public static void  showConfirmMessgae(Context context, String title, String message, final MessageBoxListener listener ) {
        AlertDialog alertDialog = new AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        listener.result(1);
                    }
                }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        listener.result(0);
                    }
                }).show();
    }
}
