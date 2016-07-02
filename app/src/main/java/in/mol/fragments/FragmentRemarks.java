package in.mol.fragments;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;

import in.mol.Util.ActivityFileBrowser;
import in.mol.database.DatabaseHelper;
import in.mol.labourinspection.MainActivity;
import in.mol.labourinspection.R;
import in.mol.Util.UserSessionManager;
import in.mol.Util.Utilities;

public class FragmentRemarks extends Fragment {

    private static final String ARG_PARAM1 = "User_NAME";
    private static final String ARG_PARAM2 = "User_ID";
    private static ViewPager viewPager;
    private View view;
    static Context context;
    private static MainActivity mainActivity;
    String user_name, user_id;
    EditText remarks;
    Button save, btn_browse_file1, btn_browse_file2;
    TextView tv_file_1, tv_file_2;
    private static JSONObject dataToDatabase;
    DatabaseHelper dbHelper;
    private UserSessionManager session;
    String licence_no, inspection_no;
    CustomDialogClass custumdialog;
    private final int CAMERA_REQUEST = 1001;
    private int IMAGE_REQUEST;
    int PICKDOC_REQUEST_CODE = 1;
    Uri uri_image1, uri_image2;
    File folder, file_image1, file_image2;
    boolean flag_browse_camera1, flag_browse_camera2;
    boolean flag_browse_upload1, flag_browse_upload2;
    File file_upload1, file_upload2;
    String filepath1 = "";
    String filepath2 = "";

    public FragmentRemarks() {
        // Required empty public constructor
    }

    public static Fragment newInstance(MainActivity mainAct, Context applicationContext,
                                       ViewPager viewPager, String username, String userId) {
        FragmentRemarks fragment = new FragmentRemarks();
        Bundle args = new Bundle();

        args.putString(ARG_PARAM1, username);
        args.putString(ARG_PARAM2, userId);
        fragment.setArguments(args);

        viewPager = viewPager;
        context = applicationContext;
        mainActivity = mainAct;

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

            user_name = getArguments().getString(ARG_PARAM1);
            user_id = getArguments().getString(ARG_PARAM2);
        }
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_remarks_documents, container, false);

        init();
        setDefaults();
        setEventListeners();

        return view;
    }

    private void init() {
        dbHelper = new DatabaseHelper(context);
        dataToDatabase = new JSONObject();
        session = new UserSessionManager(context);
        custumdialog = new CustomDialogClass(mainActivity);

        remarks = (EditText) view.findViewById(R.id.edit_remarks);
        save = (Button) view.findViewById(R.id.btn_save);
        tv_file_1 = (TextView) view.findViewById(R.id.tv_file_1);
        tv_file_2 = (TextView) view.findViewById(R.id.tv_file_2);
        btn_browse_file1 = (Button) view.findViewById(R.id.btn_browse_1);
        btn_browse_file2 = (Button) view.findViewById(R.id.btn_browse_2);
    }

    private void setDefaults() {
        licence_no = session.getLicenseNo();
        inspection_no = session.getInspectionNo();

        folder = new File(Environment.getExternalStorageDirectory()
                + "/Labour/" + ".Images");
        if (!folder.exists()) {
            folder.mkdirs();
        }
    }

    private void setEventListeners() {
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String strRemarks = remarks.getText().toString();

                try {
                    dataToDatabase.put("Remark", strRemarks);

                    System.out.println(dataToDatabase.toString());
                    Thread.sleep(1000);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                long result = -1;
                result = dbHelper.insertBasicDetails(user_name, licence_no, inspection_no, dataToDatabase.toString());

                long resultFile = -1;

                if(flag_browse_camera1 || flag_browse_upload1){
                    if(flag_browse_camera1){
                        filepath1 = file_image1.getAbsolutePath();
                    } else {
                        filepath1 = file_upload1.getAbsolutePath();
                    }
                    resultFile = dbHelper.insertFileDetails(user_name, licence_no, inspection_no, filepath1);
                }

                if(flag_browse_camera2 || flag_browse_upload2){
                    if(flag_browse_camera2){
                        filepath2 = file_image2.getAbsolutePath();
                    } else {
                        filepath2 = file_upload2.getAbsolutePath();
                    }
                    resultFile = dbHelper.insertFileDetails(user_name, licence_no, inspection_no, filepath2);
                }


                if (result > 0) {
                    Utilities.showMessage("Data saved sucessfully", context);

                    mainActivity.finish();
                } else {
                    Utilities.showMessage("Data not saved", context);
                }
            }
        });

        btn_browse_file1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IMAGE_REQUEST = 1;
                file_image1 = new File(folder, "IMG_" + licence_no + "_File1.jpeg");
                uri_image1 = Uri.fromFile(file_image1);
                custumdialog = new CustomDialogClass(mainActivity);
                custumdialog.setTitle("Upload Document");
                custumdialog.show();
            }
        });

        btn_browse_file2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IMAGE_REQUEST = 2;
                file_image2 = new File(folder, "IMG_" + licence_no + "_File2.jpeg");
                uri_image2 = Uri.fromFile(file_image2);
                custumdialog = new CustomDialogClass(mainActivity);
                custumdialog.setTitle("Upload Document");
                custumdialog.show();
            }
        });
    }

    public class CustomDialogClass extends Dialog {

        public Activity c;
        public Dialog d;
        public ImageView upload_img, upload_doc, take_photo;
        Button cancel;
        Intent intentPhoto;

        public CustomDialogClass(Activity a) {
            super(a);
            // TODO Auto-generated constructor stub
            this.c = a;

        }

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            // requestWindowFeature(Window.FEATURE_NO_TITLE);
            setContentView(R.layout.dialog_document);

            cancel = (Button) findViewById(R.id.btn_cancel);
            // upload_img = (ImageView) findViewById(R.id.iv_upload_img);
            upload_doc = (ImageView) findViewById(R.id.iv_upload_doc);
            take_photo = (ImageView) findViewById(R.id.iv_take_photo);
            intentPhoto = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

            take_photo.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    if (IMAGE_REQUEST == 1) {
                        intentPhoto.putExtra(MediaStore.EXTRA_OUTPUT,
                                uri_image1);
                        startActivityForResult(intentPhoto, CAMERA_REQUEST);
                        custumdialog.dismiss();
                    }
                    if (IMAGE_REQUEST == 2) {
                        intentPhoto.putExtra(MediaStore.EXTRA_OUTPUT,
                                uri_image2);
                        startActivityForResult(intentPhoto, CAMERA_REQUEST);
                        custumdialog.dismiss();
                    }
                }

            });

            upload_doc.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    Intent fileExploreIntent = new Intent(
                            ActivityFileBrowser.INTENT_ACTION_SELECT_FILE,
                            null, context,
                            ActivityFileBrowser.class);
                    startActivityForResult(fileExploreIntent,
                            PICKDOC_REQUEST_CODE);
                    custumdialog.dismiss();
                }
            });

            cancel.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub

                    custumdialog.dismiss();
                }
            });
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {

            switch (IMAGE_REQUEST) {
                case 1:
                    tv_file_1.setText(file_image1.getName());
                    flag_browse_camera1 = true;
                    flag_browse_upload1 = false;

                    break;

                case 2:
                    tv_file_2.setText(file_image2.getName());
                    flag_browse_camera2 = true;
                    flag_browse_upload2 = false;

                    break;

                default:
                    break;
            }
        }

        if (requestCode == PICKDOC_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            // Make sure the request was successful
            switch (IMAGE_REQUEST) {
                case 1:
                    String idFile = data
                            .getStringExtra(ActivityFileBrowser.returnFileParameter);
                    file_upload1 = new File(idFile);
                    tv_file_1.setText(file_upload1.getName());
                    flag_browse_camera1 = false;
                    flag_browse_upload1 = true;
                    break;

                case 2:
                    String idFile2 = data
                            .getStringExtra(ActivityFileBrowser.returnFileParameter);
                    file_upload2 = new File(idFile2);
                    tv_file_2.setText(file_upload2.getName());
                    flag_browse_camera2 = false;
                    flag_browse_upload2 = true;
                    break;

                default:
                    break;
            }
        }
    }


    public void sendData(String s) {
        try {
            dataToDatabase = new JSONObject(s);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
