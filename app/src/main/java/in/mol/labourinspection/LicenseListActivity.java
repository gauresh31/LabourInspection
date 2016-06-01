package in.mol.labourinspection;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.TextView;
import android.app.Activity;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import in.mol.database.DatabaseHelper;
import in.mol.models.ApplicationConstants;
import in.mol.models.UserSessionManager;
import in.mol.models.Utilities;
import in.mol.models.VolleySingleton;
import in.mol.services.WebService;

public class LicenseListActivity extends AppCompatActivity {

    JSONObject user_data;
    UserSessionManager session;
    String userId;
    private ProgressDialog progressDialog;
    RecyclerView recyclerView;
    ContentLicenseAdapter adapter;
    JSONArray jsonArray;
    JSONObject jsonObject;
    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_license_list);

        init();
        setDefaults();


    }

    private void init() {
        recyclerView = (RecyclerView) findViewById(R.id.recycler_license_list);
        db = new DatabaseHelper(getApplicationContext());
    }

    private void setDefaults() {
        session = new UserSessionManager(getApplicationContext());

        try {

//            user_data = new JSONObject(session.getUserDetails().
//                    get(ApplicationConstants.KEY_LOGIN_INFO));
//
//            userId = user_data.getString("UserID");

            jsonArray = new JSONArray(session.getLicenseDetails().
                    get(ApplicationConstants.KEY_LICENSE_INFO));

            adapter = new ContentLicenseAdapter(jsonArray.toString());
            recyclerView.setAdapter(adapter);
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            db.copyAppDbToDownloadFolder();
        } catch (IOException e) {

            e.printStackTrace();
        }
    }

    public class ContentLicenseAdapter extends RecyclerView.Adapter<LicenseHolder> {

        String strData;
        JSONArray jsonArray;
        int len;
        String license_no = "", inspection_no = "", status = "";
        private int[] colors = new int[]{Color.parseColor("#D2E4FC"), Color.parseColor("#F0F0F0")};

        ContentLicenseAdapter(String data) {
            strData = data;
            try {
                jsonArray = new JSONArray(strData);
                len = jsonArray.length();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        public LicenseHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).
                    inflate(R.layout.holder_license_list, parent, false);

            Log.i("LicenseActivity", "onCreateViewHolder called");
            return new LicenseHolder(view);
        }

        @Override
        public void onBindViewHolder(final LicenseHolder holder, final int position) {
            Log.i("LicenseActivity", "onBindViewHolder called" + position);
            int colorPos = position % colors.length;
            holder.itemView.setBackgroundColor(colors[colorPos]);
            try {
                jsonObject = jsonArray.getJSONObject(position);
                license_no = jsonObject.getString("LicenseNo");
                inspection_no = jsonObject.getString("InspectionNo");
//                status = jsonObject.getString("Staus");
                holder.tv_srno.setText("" + (position + 1));
                holder.tv_license_no.setText(license_no);
//                holder.tv_status.setText(status);

            } catch (JSONException e) {
                e.printStackTrace();
            }

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

//                    holder.itemView.setBackgroundColor(getResources().getColor(R.color.grey));
                    try {
                        jsonObject = jsonArray.getJSONObject(position);

                        license_no = jsonObject.getString("LicenseNo");
                        inspection_no = jsonObject.getString("InspectionNo");

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    session.createLicenseInspectionInfo(license_no, inspection_no);

//                    if (Utilities.isNetworkAvailable(getApplicationContext())) {
//
                    GetBasicDetails basic = new GetBasicDetails(license_no);
                    basic.execute("");
//                    } else {
//                        Utilities.showMessage("No Internet Connection", getApplicationContext());
//                    }
//                    callLicenseList(license_no);

                }
            });

        }

        @Override
        public int getItemCount() {
            return len;
        }
    }

    public void callLicenseList(String licenseNo) {
        if (Utilities.isNetworkAvailable(getApplicationContext())) {
            progressDialog = new ProgressDialog(LicenseListActivity.this);
            progressDialog.setMessage("Please Wait...");
            progressDialog.setCancelable(true);
            progressDialog.show();

            RequestQueue requestQueue = VolleySingleton.getInstance().getRequestQueue();
            JsonObjectRequest stringRequest = new JsonObjectRequest(
                    WebService.URL_BASIC_DATA + "Licence=" + licenseNo, null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            if (progressDialog.isShowing()) {
                                progressDialog.dismiss();
                            }
                            Log.i("Inside Response", "onResponse");
                            if (response != null) {
                                session.createBasicDetailsInfo(response.toString());
                                Intent in = new Intent(LicenseListActivity.this, ActivityActList.class);
                                startActivity(in);
                            } else {
                                Utilities.showMessage("Fail to load data...check Internet connection", getApplicationContext());
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    if (progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }
                    Log.i("Inside Error", "onError");
                    Utilities.showMessage("Fail to load data...check Internet connection", getApplicationContext());
                }
            });
            requestQueue.add(stringRequest);
        } else {
            Utilities.showMessage("No Internet Connection", getApplicationContext());
        }
    }

    public static class LicenseHolder extends RecyclerView.ViewHolder {

        final TextView tv_srno;
        final TextView tv_license_no;
        final TextView tv_status;
        final View itemView;

        public LicenseHolder(View view) {
            super(view);
            tv_srno = (TextView) view.findViewById(R.id.tv_sr_no);
            tv_license_no = (TextView) view.findViewById(R.id.tv_license_no);
            tv_status = (TextView) view.findViewById(R.id.tv_status);
            itemView = view;
        }
    }

    class GetBasicDetails extends AsyncTask<String, String, String> {

        String license;

        GetBasicDetails(String license_no) {
            license = license_no;
            progressDialog = new ProgressDialog(LicenseListActivity.this);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.setMessage("Please Wait....");
            progressDialog.setCancelable(true);
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            String response;
            response = WebService.getBasicData(license);
            return response;
        }

        @Override
        protected void onPostExecute(final String result) {

            if (progressDialog.isShowing()) {
                progressDialog.dismiss();
            }

            String resp =

                    "{\n" +
                    "\t\"objLabourInspectionSchema\": {\n" +
                    "\t\t\"Institution_Name\": \"CLEAR CARGO LOGISTIK PVT LTD\",\n" +
                    "\t\t\"Institution_Addr\": \"B - 307 , HARBOUR COURT, PLOT NO 25 A B C D, SECTOR NO 02, REVENUE VILLAGE DRONAGIRI, NAVI MUMBAI (M CORP.) , THANE, THANE, 400707\",\n" +
                    "\t\t\"Owner_Name\": \"NISHIGANDH REDKAR\",\n" +
                    "\t\t\"Owner_Addr\": \"16 , JIJAU BHAVAN, LALUBHAI PARK ROAD EXTN, NEAR IRLA NALA, VILE PARLE WEST, MUMBAI CITY , MUMBAI CITY, MUMBAI CITY, 400056\",\n" +
                    "\t\t\"TotalWorkers\": 30,\n" +
                    "\t\t\"Male\": 0,\n" +
                    "\t\t\"Female\": 0,\n" +
                    "\t\t\"CreatedBy\": \"00000000-0000-0000-0000-000000000000\",\n" +
                    "\t\t\"TotalDirectEmp\": 0,\n" +
                    "\t\t\"TotalContractEmp\": 0,\n" +
                    "\t\t\"Transgender\": 0,\n" +
                    "\t\t\"StatusId\": 0\n" +
                    "\t}\n" +
                    "}";

            if (resp != null) {
                session.createBasicDetailsInfo(resp);
                Log.i("Response: ", resp);
                Intent in = new Intent(LicenseListActivity.this, ActivityActList.class);
                startActivity(in);
            } else {
                Utilities.showMessage("Fail to get data...check Internet connection", getApplicationContext());
            }
        }
    }

//    class GetActlist extends AsyncTask<String, String, String> {
//
//        String license, inspection;
//
//        GetActlist(String license_no, String inspection_no) {
//            license = license_no;
//            inspection = inspection_no;
//            progressDialog = new ProgressDialog(LicenseListActivity.this);
//        }
//
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//            progressDialog.setMessage("Please Wait....");
//            progressDialog.setCancelable(true);
//            progressDialog.show();
//        }
//
//        @Override
//        protected String doInBackground(String... params) {
//            String response;
//
//            response = WebService.getActList(license, inspection);
//            return response;
//        }
//
//        @Override
//        protected void onPostExecute(final String result) {
//
//            if (progressDialog.isShowing()) {
//                progressDialog.dismiss();
//            }
//            if (result != null) {
//                JSONArray jsonArray;
//                JSONObject jsonObj;
//                JSONObject jsonObj2;
//
//                try {
//                    jsonObj = new JSONObject(result);
//                    jsonArray = jsonObj.getJSONArray("listLabourActSchema");
//                    jsonObj2 = jsonObj.getJSONObject("objLabourInspectionSchema");
//                    session.createActList(jsonArray.toString(), jsonObj2.toString());
//                } catch (JSONException e) {
//                    Utilities.showMessage("Fail to get data...check Internet connection", getApplicationContext());
//                    e.printStackTrace();
//                }
////                Intent in = new Intent(LicenseListActivity.this, MainActivity.class);
////                startActivity(in);
//            }
//        }
//    }
}
