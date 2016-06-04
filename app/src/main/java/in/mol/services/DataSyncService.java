package in.mol.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Timer;
import java.util.TimerTask;

import in.mol.database.DatabaseHelper;
import in.mol.models.M_Data;
import in.mol.models.Utilities;

/**
 * Created by 1742 on 06-04-2016.
 */
public class DataSyncService extends Service {

    DatabaseHelper db;
    private static int cnt = 0;
    private Timer timer;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        // TODO Auto-generated method stub
        db = new DatabaseHelper(getApplicationContext());
    }

    @Override
    public void onStart(Intent intent, int startId) {
        // TODO Auto-generated method stub
        System.out.println(cnt);
        if (cnt == 0) {
            timer = new Timer();
            timer.schedule(new TimerTask() {

                @Override
                public void run() {
                    // TODO Auto-generated method stub
                    cnt++;
                    System.out.println("Attempt : " + cnt);
                    if (Utilities.isNetworkAvailable(getApplicationContext())) {
                        String response = "";
                        M_Data data = db.getData();

                        if (data.getData() != null) {
                            System.out.println(data.getData());
                            try {
                                JSONObject json = new JSONObject(data.getData());
                                response = WebService.uploadData(json);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }

                        if (response.equalsIgnoreCase("\"Insert Success\"\n")) {
                            db.updateData(data.getRowid());
                        }
                    }
                }
            }, 2000, 5000);
        }
    }
}
