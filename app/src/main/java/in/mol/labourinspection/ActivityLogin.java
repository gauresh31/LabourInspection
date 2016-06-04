package in.mol.labourinspection;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.morpho.capture.MorphoTabletFPSensorDevice;

import org.json.JSONArray;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import in.mol.models.UserSessionManager;
import in.mol.models.Utilities;
import in.mol.models.VolleySingleton;
import in.mol.services.DataSyncService;
import in.mol.services.WebService;

/**
 * Created by 1742 on 29-04-2016.
 */
public class ActivityLogin extends AppCompatActivity {

    private AutoCompleteTextView mUserNameView;
    private EditText mPasswordView;
    private UserLoginTask mAuthTask = null;
    private ProgressDialog progressDialog;
    private Context context;
    private UserSessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        init();
        setDefaults();

        Button signInButton = (Button) findViewById(R.id.sign_in_button);
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validate()) {
                    attemptLogin();
                }
            }
        });

        startService(new Intent(ActivityLogin.this, DataSyncService.class));
    }


    private void init() {
        context = getApplicationContext();
        session = new UserSessionManager(context);

        mUserNameView = (AutoCompleteTextView) findViewById(R.id.username);
        mPasswordView = (EditText) findViewById(R.id.password);
    }

    private void setDefaults() {
        mUserNameView.setText("sadmin");
        mPasswordView.setText("Pass@123");
    }

    public void attemptLogin() {
        String username = mUserNameView.getText().toString();
        String password = mPasswordView.getText().toString();
        String passwordMD5 = md5(password);

        //Async Request
//        if (Utilities.isNetworkAvailable(getApplicationContext())) {
//
            mAuthTask = new UserLoginTask(username, password, getMacAddress());
            mAuthTask.execute("");
//        } else {
//            Utilities.showMessage("No Internet Connection", getApplicationContext());
//        }

        //Request Using Volley Library
//        loginUsingVolley();
    }

    public void loginUsingVolley() {
        String username = mUserNameView.getText().toString();
        String password = mPasswordView.getText().toString();
        String passwordMD5 = md5(password);

        if (Utilities.isNetworkAvailable(getApplicationContext())) {
            progressDialog = new ProgressDialog(ActivityLogin.this);
            progressDialog.setMessage("Authenticating User....");
            progressDialog.setCancelable(true);
            progressDialog.show();

//            mPasswordView.setText(passwordMD5);

            RequestQueue requestQueue = VolleySingleton.getInstance().getRequestQueue();

            JsonObjectRequest stringRequest = new JsonObjectRequest(
                    WebService.Login_Url + "Username=" + username + "&Password=" + passwordMD5 + "&MachineID=" + "100001000100101", null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            if (progressDialog.isShowing()) {
                                progressDialog.dismiss();
                            }
                            Log.i("Inside Response", response.toString());
                            try {
                                String status = response.getString("LoginStatus");

                                if (status.equalsIgnoreCase("Successfull")) {
                                    session.createUserLoginSession(response.toString());
                                    callLicenseList();
                                } else {
                                    Utilities.showMessage("Invalid Username Or Password", getApplicationContext());
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    if (progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }
                    Log.i("Inside Error ", error.toString());
                    Utilities.showMessage("Error occurred....please try again", getApplicationContext());
                }
            });

            requestQueue.add(stringRequest);
        } else {
            Utilities.showMessage("No Internet Connection", getApplicationContext());
        }
    }

    public void callLicenseList() {
        if (Utilities.isNetworkAvailable(getApplicationContext())) {
            progressDialog = new ProgressDialog(ActivityLogin.this);
            progressDialog.setMessage("Downloading License List...");
            progressDialog.setCancelable(true);
            progressDialog.show();

            RequestQueue requestQueue = VolleySingleton.getInstance().getRequestQueue();

            JsonArrayRequest stringRequest = new JsonArrayRequest(
                    WebService.License_Url + "UserID=" + "411F82DF-1702-4E37-9016-6DB1C4909FFE",
                    new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray response) {
                            if (progressDialog.isShowing()) {
                                progressDialog.dismiss();
                            }
                            Log.i("Inside Response", "onResponse");
                            session.createLicenseDetails(response.toString());
                            Intent in = new Intent(ActivityLogin.this, LicenseListActivity.class);
                            startActivity(in);
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    if (progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }
                    Log.i("Inside Error", error.toString());
                    Utilities.showMessage("Unable to download....please try again", getApplicationContext());
                }
            });

            requestQueue.add(stringRequest);
        } else {
            Utilities.showMessage("No Internet Connection", getApplicationContext());
        }
    }

    public class UserLoginTask extends AsyncTask<String, String, String> {

        private final String user_name;
        private final String mPassword;
        private final String mMacAddr;
        String name = "null";

        UserLoginTask(String username, String password, String macAddress) {
            user_name = username;
            mPassword = password;
            mMacAddr = macAddress;

            progressDialog = new ProgressDialog(ActivityLogin.this);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.setMessage("Authenticating User....");
            progressDialog.setCancelable(true);
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            // TODO: attempt authentication against a network service.
            String response = "";
            String status;

            try {
                String passwordMD5 = md5(mPassword);
                response = WebService.loginService(user_name, passwordMD5, mMacAddr);

                try {
                    Thread.sleep(3000);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } catch (Exception e) {
                return "";
            }
            return response;
        }

        @Override
        protected void onPostExecute(final String result) {

            if (progressDialog.isShowing()) {
                progressDialog.dismiss();
            }

            String resp = "{\"UserName\":\"sadmin\",\"Password\":\"F91E15DBEC69FC40F81F0876E7009648\"," +
                    "\"UserID\":\"59fbcf05-f9f7-47d9-ae90-742122c6a292\",\"UserTypeID\":\"920494CF-C6E9-4AB3-9295-310F0881717E\"," +
                    "\"UsertypeName\":\"Super\",\"LoginStatus\":\"Successfull\",\"hfrandomtoken\":\"629142AF968E2B28C2FD176FBAEFC28F\"," +
                    "\"FullName\":\"Priyanka Nilesh Wadekar\",\"FullNameInMarathi\":\"प्रियांका निलेश वाडेकर\",\"FatherFullName\":\"\"," +
                    "\"FatherFullNameInMarathi\":\"\",\"DateofBirth\":\"01/01/1988 00:00:00\",\"MobileNo\":\"9833675193\"," +
                    "\"MachineId\":\"100001000100101\",\"DivisionID\":\"0\",\"DistrictID\":\"0\",\"OfficeID\":\"0\"}\n";

            String status;
            if (resp.equalsIgnoreCase("Timeout")) {
                Utilities.showMessage("Connection Timeout...Check Internet Connection", getApplicationContext());
            } else if (resp != null) {
                try {
                    JSONObject jsonResponse = new JSONObject(resp);
                    status = jsonResponse.getString("LoginStatus");

                    if (status.equalsIgnoreCase("Successfull")) {
                        session.createUserLoginSession(resp);
                        new GetLicenseList("").execute("");//jsonResponse.getString("UserID");
                    } else {
                        Utilities.showMessage("Invalid Username Or Password", getApplicationContext());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    class GetLicenseList extends AsyncTask<String, String, String> {

        String userId;

        GetLicenseList(String user_id) {
            userId = user_id;
            progressDialog = new ProgressDialog(ActivityLogin.this);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.setMessage("Please Wait....Downloading License List");
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            String response;

            response = WebService.getLicenseList(userId);

            try {
                Thread.sleep(3000);
            } catch (Exception e) {
                e.printStackTrace();
            }

            return response;
        }

        @Override
        protected void onPostExecute(final String result) {

            if (progressDialog.isShowing()) {
                progressDialog.dismiss();
            }

            String res = "[{\"LicenseNo\":\"1631000310277242\",\"AssignedInspectionCount\":0,\"RemainingInspectionCount\":0," +
                    "\"InspectionID\":0,\"Def_InspectionCount\":0,\"InspectionNo\":\"160504031000037\",\"Username\":\"insmum2\"," +
                    "\"FromDate\":\"04/05/2016\",\"ToDate\":\"11/05/2016\",\"Staus\":\"Pending\"}," +
                    "{\"LicenseNo\":\"1631000310114079\",\"AssignedInspectionCount\":0,\"RemainingInspectionCount\":0,\"InspectionID\":0," +
                    "\"Def_InspectionCount\":0,\"InspectionNo\":\"160504031000038\",\"Username\":\"insmum2\",\"FromDate\":\"04/05/2016\"," +
                    "\"ToDate\":\"11/05/2016\",\"Staus\":\"Pending\"}," +
                    "{\"LicenseNo\":\"1631000310207501\",\"AssignedInspectionCount\":0,\"RemainingInspectionCount\":0," +
                    "\"InspectionID\":0,\"Def_InspectionCount\":0,\"InspectionNo\":\"160504031000039\",\"Username\":\"insmum2\"," +
                    "\"FromDate\":\"04/05/2016\",\"ToDate\":\"11/05/2016\",\"Staus\":\"Pending\"}," +
                    "{\"LicenseNo\":\"1631000310230086\",\"AssignedInspectionCount\":0,\"RemainingInspectionCount\":0,\"InspectionID\":0," +
                    "\"Def_InspectionCount\":0,\"InspectionNo\":\"160504031000040\",\"Username\":\"insmum2\",\"FromDate\":\"04/05/2016\"," +
                    "\"ToDate\":\"11/05/2016\",\"Staus\":\"Pending\"}," +
                    "{\"LicenseNo\":\"1631000310268442\",\"AssignedInspectionCount\":0," +
                    "\"RemainingInspectionCount\":0,\"InspectionID\":0,\"Def_InspectionCount\":0,\"InspectionNo\":\"160504031000041\"," +
                    "\"Username\":\"insmum2\",\"FromDate\":\"04/05/2016\",\"ToDate\":\"11/05/2016\",\"Staus\":\"Pending\"}," +
                    "{\"LicenseNo\":\"1631000310184708\",\"AssignedInspectionCount\":0,\"RemainingInspectionCount\":0,\"InspectionID\":0," +
                    "\"Def_InspectionCount\":0,\"InspectionNo\":\"160504031000042\",\"Username\":\"insmum2\",\"FromDate\":\"04/05/2016\"," +
                    "\"ToDate\":\"11/05/2016\",\"Staus\":\"Pending\"}]\n";

            if (res != null) {
                session.createLicenseDetails(res);
                Intent in = new Intent(ActivityLogin.this, LicenseListActivity.class);
                startActivity(in);
            } else {
                Utilities.showMessage("Unable to download....please try again", getApplicationContext());
            }
        }
    }

    private boolean validate() {
        String username = mUserNameView.getText().toString();
        String password = mPasswordView.getText().toString();

        if (username.equalsIgnoreCase("")) {
            Utilities.showMessage("Enter Username", getApplicationContext());
            return false;
        } else if (password.equalsIgnoreCase("")) {
            Utilities.showMessage("Enter Password", getApplicationContext());
            return false;
        }
        return true;
    }

    public String getMacAddress() {
        WifiManager wifiMgr = (WifiManager) getSystemService(WIFI_SERVICE);
        WifiInfo wifiInfo = wifiMgr.getConnectionInfo();
        String macAddr = wifiInfo.getMacAddress();
        return macAddr;
    }

    public static final String md5(final String s) {
        final String MD5 = "MD5";
        try {
            // Create MD5 Hash
            MessageDigest digest = java.security.MessageDigest
                    .getInstance(MD5);
            digest.update(s.getBytes());
            byte messageDigest[] = digest.digest();

            // Create Hex String
            StringBuilder hexString = new StringBuilder();
            for (byte aMessageDigest : messageDigest) {
                String h = Integer.toHexString(0xFF & aMessageDigest);
                while (h.length() < 2)
                    h = "0" + h;
                hexString.append(h);
            }
            return hexString.toString().toUpperCase();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }
}
