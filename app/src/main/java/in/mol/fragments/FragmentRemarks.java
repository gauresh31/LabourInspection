package in.mol.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import org.json.JSONException;
import org.json.JSONObject;

import in.mol.database.DatabaseHelper;
import in.mol.labourinspection.MainActivity;
import in.mol.labourinspection.R;
import in.mol.Util.UserSessionManager;
import in.mol.Util.Utilities;

public class FragmentRemarks extends Fragment{

    private static final String ARG_PARAM1 = "User_NAME";
    private static final String ARG_PARAM2 = "User_ID";
    private static ViewPager viewPager;
    private View view;
    static Context context;
    private static MainActivity mainActivity;
    String user_name, user_id;
    EditText remarks;
    Button save;
    private static JSONObject dataToDatabase;
    DatabaseHelper dbHelper;
    private UserSessionManager session;
    String licence_no, inspection_no;

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

        remarks = (EditText) view.findViewById(R.id.edit_remarks);
        save = (Button) view.findViewById(R.id.btn_save);
    }

    private void setDefaults() {
        licence_no = session.getLicenseNo();
        inspection_no = session.getInspectionNo();
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

                if (result > 0) {
                    Utilities.showMessage("Data saved sucessfully", context);

                    mainActivity.finish();
                } else {
                    Utilities.showMessage("Data not saved", context);
                }
            }
        });
    }

    public void sendData(String s) {
        try {
            dataToDatabase = new JSONObject(s);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
