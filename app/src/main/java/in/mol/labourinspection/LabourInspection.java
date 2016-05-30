package in.mol.labourinspection;

import android.app.Application;
import android.content.Context;

import java.io.File;

import in.mol.database.DatabaseHelper;

/**
 * Created by 1742 on 12-04-2016.
 */
public class LabourInspection extends Application {

    private DatabaseHelper dataBaseHelper;
    private Context context;
    private boolean dbExist;
    public static LabourInspection sInstance;

    @Override
    public void onCreate() {
        // TODO Auto-generated method stub
        super.onCreate();
        sInstance = this;
        context = getApplicationContext();
        File dbFile = context.getDatabasePath("Labour_Database");
        dbExist = dbFile.exists();
        dataBaseHelper = new DatabaseHelper(context);
        try {
            dataBaseHelper.createDataBase(dbExist);
            // System.out.println("Database Created");
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static LabourInspection getInstance(){
        return  sInstance;
    }
    public static Context getAppContext(){
        return sInstance.getApplicationContext();
    }
}
