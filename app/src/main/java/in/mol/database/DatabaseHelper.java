package in.mol.database;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import in.mol.models.M_Data;
import in.mol.models.SpinnerObject;

/**
 * Created by 1742 on 06-04-2016.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    // The Android's default system path of your application database.
    private static String DB_PATH = "";

    private static String DB_NAME = "Labour_Database";

    // Database Version
    private static final int DATABASE_VERSION = 1;
    // This must be change to call onUpgrade()

    private SQLiteDatabase myDataBase;

    private final Context myContext;

    SimpleDateFormat sd = new SimpleDateFormat("dd-MM-yy-hh-mm-ss");
    File f, folder;

    /**
     * Constructor Takes and keeps a reference of the passed context in order to
     * access to the application assets and resources.
     *
     * @param context
     */
    public DatabaseHelper(Context context) {

        super(context, DB_NAME, null, DATABASE_VERSION);
        this.myContext = context;

        // DB_PATH=folder.getPath();
        if (android.os.Build.VERSION.SDK_INT >= 4.2) {
            DB_PATH = context.getApplicationInfo().dataDir + "/databases/";
        } else {
            DB_PATH = "/data/data/" + context.getPackageName() + "/databases/";
        }

        // DB_PATH = myContext.getFilesDir()+ "/databases/";
        System.out.println("Database path : " + DB_PATH);
    }

    /**
     * Creates a empty database on the system and rewrites it with your own
     * database.
     */
    public void createDataBase(boolean dbExist) throws IOException {

        if (dbExist) {
            // do nothing - database already exist
        } else {

            // By calling this method and empty database will be created into
            // the default system path
            // of your application so we are gonna be able to overwrite that
            // database with our database.
            this.getReadableDatabase();

            try {
                System.out.println("Database copying started");

                copyDataBase();
                System.out.println("Database copying completed");
            } catch (IOException e) {

                throw new Error("Error copying database");

            }
        }

    }

    /**
     * Check if the database already exist to avoid re-copying the file each
     * time you open the application.
     *
     * @return true if it exists, false if it doesn't
     */
    private boolean checkDataBase() {

        SQLiteDatabase checkDB = null;

        try {
            String myPath = DB_PATH + DB_NAME;
            checkDB = SQLiteDatabase.openDatabase(myPath, null,
                    SQLiteDatabase.OPEN_READONLY);

        } catch (SQLiteException e) {
            // database does't exist yet.
        }

        if (checkDB != null) {
            checkDB.close();

        }

        return checkDB != null ? true : false;
    }

    /**
     * Copies your database from your local assets-folder to the just created
     * empty database in the system folder, from where it can be accessed and
     * handled. This is done by transfering bytestream.
     */
    private void copyDataBase() throws IOException {

        // Open your local db as the input stream
        InputStream myInput = myContext.getAssets().open(DB_NAME);

        // Path to the just created empty db
        String outFileName = DB_PATH + DB_NAME;

        // Open the empty db as the output stream
        OutputStream myOutput = new FileOutputStream(outFileName);

        // transfer bytes from the inputfile to the outputfile
        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer)) > 0) {
            myOutput.write(buffer, 0, length);
        }

        // Close the streams
        myOutput.flush();
        myOutput.close();
        myInput.close();

    }

    public void copyAppDbToDownloadFolder() throws IOException {
        try {
            File backupDB = new File(
                    Environment
                            .getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),
                    DB_NAME + sd.format(new Date())); // for example
            // "my_data_backup.db"
            File currentDB = new File(DB_PATH + DB_NAME); // databaseName=your
            // current
            // application
            // database name,
            // for example
            // "my_data.db"
            if (currentDB.exists()) {

                FileInputStream fis = new FileInputStream(currentDB);
                FileOutputStream fos = new FileOutputStream(backupDB);
                fos.getChannel().transferFrom(fis.getChannel(), 0,
                        fis.getChannel().size());
                // or fis.getChannel().transferTo(0, fis.getChannel().size(),
                // fos.getChannel());
                fis.close();
                fos.close();
                Log.i("Database successfully", " copied to download folder");
                // return true;
            } else
                Log.i("Copying Database", " fail, database not found");
        } catch (IOException e) {
            Log.d("Copying Database", "fail, reason:", e);
        }
    }

    private void openDataBase() throws SQLException {

        // Open the database
        String myPath = DB_PATH + DB_NAME;
        myDataBase = SQLiteDatabase.openDatabase(myPath, null,
                SQLiteDatabase.OPEN_READWRITE);

    }

    @Override
    public synchronized void close() {

        if (myDataBase != null)
            myDataBase.close();

        super.close();

    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        if (oldVersion < newVersion) {
            /*
             * final String ALTER_TBL = "ALTER TABLE " + ChallanDetails +
			 * " ADD COLUMN Col1 varchar(200) null;"; db.execSQL(ALTER_TBL);
			 */
            // db.copyAppDbToDownloadFolder();
            System.out.println("Upgrading.....");
            try {
                copyAppDbToDownloadFolder();
                copyDataBase();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    public List<SpinnerObject> getActs() {
        List<SpinnerObject> lst_acts = new ArrayList<>();
        String id, name;
        SpinnerObject obj_spn;
        myDataBase = this.getReadableDatabase();

        Cursor c = myDataBase.query("Act_Master", new String[]{"ActId", "ActName"},
                null, null, null, null, null);

        lst_acts.add(new SpinnerObject("0", "Select"));
        if (c.moveToFirst()) {
            do {
                id = c.getString(0);
                name = c.getString(1);

                obj_spn = new SpinnerObject(id, name);
                lst_acts.add(obj_spn);
            } while (c.moveToNext());
        }
        return lst_acts;
    }


    public List<SpinnerObject> getRules(String actId) {
        List<SpinnerObject> lst_rules = new ArrayList<>();
        String id, name;
        myDataBase = this.getReadableDatabase();

        Cursor c = myDataBase.query("Rules_Master", new String[]{"RuleID", "RuleName"},
                "ActId=?", new String[]{actId}, null, null, null);
        if (c.moveToFirst()) {
            do {
                id = c.getString(0);
                name = c.getString(1);

                SpinnerObject obj_spn = new SpinnerObject(id, name);
                lst_rules.add(obj_spn);
            } while (c.moveToNext());
        }
        return lst_rules;
    }

    public long insertBasicDetails(String user_name, String licence_no, String inspection_no, String jsonData) {
        myDataBase = this.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put("UserName", user_name);
        cv.put("LicenseNo", licence_no);
        cv.put("InspectionNo", inspection_no);
        cv.put("JsonData", jsonData);
        cv.put("IsUploaded", false);
        cv.put("DateTime", sd.format(new Date()));

        long result = -1;
        try {
            myDataBase.beginTransaction();
            result = myDataBase.insertOrThrow("Labour_Data", null, cv);
            System.out.println("Result :" + result);
            if (result == -1) {
            } else {
                myDataBase.setTransactionSuccessful();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            myDataBase.endTransaction();
            myDataBase.close();
        }
        return result;
    }

    public long insertFileDetails(String user_name, String licence_no, String inspection_no, String path) {
        myDataBase = this.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put("Username", user_name);
        cv.put("License_no", licence_no);
        cv.put("Inspection_no", inspection_no);
        cv.put("File_Path", path);
        cv.put("IsUploaded", false);
        cv.put("DateTime", sd.format(new Date()));

        long result = -1;
        try {
            myDataBase.beginTransaction();
            result = myDataBase.insertOrThrow("Labour_File_Upload", null, cv);
            System.out.println("Result :" + result);
            if (result == -1) {
            } else {
                myDataBase.setTransactionSuccessful();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            myDataBase.endTransaction();
            myDataBase.close();
        }
        return result;
    }

    public M_Data getData() {
        String data = "";
        M_Data mData = new M_Data();
        myDataBase = this.getWritableDatabase();
        Cursor c = myDataBase.query("Labour_Data", new String[]{"rowid", "JsonData"},
                "IsUploaded=?", new String[]{"0"}, null, null, null);

        if (c.moveToFirst()) {
            do {
                mData.setRowid(c.getString(0));
                mData.setData(c.getString(1));
            } while (c.moveToNext());
        }
        return mData;
    }

    public void updateData(String rowid) {
        myDataBase = getReadableDatabase();

        ContentValues cv = new ContentValues();
        cv.put("IsUploaded", true);

        try {
            myDataBase.beginTransaction();
            long result = -1;
            result = myDataBase.update("Labour_Data", cv, "IsUploaded =? and rowid=?", new String[]{"0", rowid});

            if (result == -1) {

            } else {
                myDataBase.setTransactionSuccessful();
            }
        } finally {
            myDataBase.endTransaction();
            myDataBase.close();
        }
    }


}
