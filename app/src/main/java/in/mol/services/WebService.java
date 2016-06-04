package in.mol.services;

import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.Charset;

import in.mol.models.VolleySingleton;

public class WebService {

    public static final String Login_Url = "http://testlmsrandomization.mahaonlinegov.in/api/LoginSchema?";//172.16.0.27
    public static final String License_Url = "http://testlmsrandomization.mahaonlinegov.in/api/LabourInspection?";
    public static String URL_ACTS = "http://testlmsrandomization.mahaonlinegov.in/api/LabourInspection?";
    public static String URL_BASIC_DATA = "http://testlmsrandomization.mahaonlinegov.in/api/InspectionActRemarks?";

    public static String resp;

    public static String loginService(String uname, String password, String macAddr) {
        int code = 400;

        try {
            URL url = new URL(Login_Url + "Username=" + uname + "&Password=" + password + "&MachineID=" + "100001000100101");
            HttpURLConnection urlConnection = (HttpURLConnection) url
                    .openConnection();
            urlConnection.setConnectTimeout(60000);
            code = urlConnection.getResponseCode();
            try {
                BufferedReader bufferedReader = new BufferedReader(
                        new InputStreamReader(urlConnection.getInputStream()));
                StringBuilder stringBuilder = new StringBuilder();
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line).append("\n");
                }
                bufferedReader.close();
                return stringBuilder.toString();
            } catch (Exception e) {
                Log.e("ERROR", e.getMessage(), e);
                return "Timeout";
            } finally {
                urlConnection.disconnect();
            }
        } catch (MalformedURLException e) {
            Log.e("ERROR", e.getMessage(), e);
            return null;
        } catch (IOException e) {
            Log.e("ERROR", e.getMessage(), e);
            return "Timeout";
        }
    }

    public static String getLicenseList(String userId) {

        int code = 400;

        try {
            // URL url = new URL();
            URL url = new URL(License_Url + "UserID=" + "411F82DF-1702-4E37-9016-6DB1C4909FFE");//59FBCF05-F9F7-47D9-AE90-742122C6A292 //userId
            //BE6EB98C-9D9B-45ED-9033-441023A7394E
            //411F82DF-1702-4E37-9016-6DB1C4909FFE
            System.out.println("URL: " + url.toString());
            HttpURLConnection urlConnection = (HttpURLConnection) url
                    .openConnection();
            urlConnection.setConnectTimeout(60000);
            code = urlConnection.getResponseCode();
            try {
                BufferedReader bufferedReader = new BufferedReader(
                        new InputStreamReader(urlConnection.getInputStream()));
                StringBuilder stringBuilder = new StringBuilder();
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line).append("\n");
                }
                bufferedReader.close();
                return stringBuilder.toString();
            } catch (Exception e) {
                Log.e("ERROR", e.getMessage(), e);
                return String.valueOf(code);
            } finally {
                urlConnection.disconnect();
            }
        } catch (MalformedURLException e) {
            Log.e("ERROR", e.getMessage(), e);
            return null;
        } catch (IOException e) {
            Log.e("ERROR", e.getMessage(), e);

            return String.valueOf(code);
        }
    }

    public static String getBasicData(String licenseNo) {

        int code = 400;

        try {
            URL url = new URL(URL_BASIC_DATA + "Licence=" + licenseNo);
            HttpURLConnection urlConnection = (HttpURLConnection) url
                    .openConnection();
            urlConnection.setConnectTimeout(60000);
            code = urlConnection.getResponseCode();
            try {
                BufferedReader bufferedReader = new BufferedReader(
                        new InputStreamReader(urlConnection.getInputStream()));
                StringBuilder stringBuilder = new StringBuilder();
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line).append("\n");
                }
                bufferedReader.close();
                return stringBuilder.toString();
            } catch (Exception e) {
                Log.e("ERROR", e.getMessage(), e);
                return String.valueOf(code);
            } finally {
                urlConnection.disconnect();
            }
        } catch (MalformedURLException e) {
            Log.e("ERROR", e.getMessage(), e);
            return null;
        } catch (IOException e) {
            Log.e("ERROR", e.getMessage(), e);
            return null;
        }
    }

    public static String getActList(String licenseNo, String inspectionNo) {

        int code = 400;

        try {
            // URL url = new URL();
            URL url = new URL(URL_ACTS + "LicenseNo=" + "1610200310122688" + "&InspectionNo=" + "160426031000019");//licenseNo,inspectionNo
            HttpURLConnection urlConnection = (HttpURLConnection) url
                    .openConnection();
            urlConnection.setConnectTimeout(60000);
            code = urlConnection.getResponseCode();
            try {
                BufferedReader bufferedReader = new BufferedReader(
                        new InputStreamReader(urlConnection.getInputStream()));
                StringBuilder stringBuilder = new StringBuilder();
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line).append("\n");
                }
                bufferedReader.close();
                return stringBuilder.toString();
            } catch (Exception e) {
                Log.e("ERROR", e.getMessage(), e);
                return String.valueOf(code);
            } finally {
                urlConnection.disconnect();
            }
        } catch (MalformedURLException e) {
            Log.e("ERROR", e.getMessage(), e);
            return null;
        } catch (IOException e) {
            Log.e("ERROR", e.getMessage(), e);

            return String.valueOf(code);
        }
    }

    public static String uploadData(JSONObject data) {
        int code = 400;
        DataOutputStream printout;
        DataInputStream input;
        try {
            // URL url = new URL();
            URL url = new URL("http://testlmsrandomization.mahaonlinegov.in/api/InspectionActRemarks");//jsonData
            HttpURLConnection urlConnection = (HttpURLConnection) url
                    .openConnection();
            urlConnection.setConnectTimeout(60000);
//            urlConnection.setRequestMethod("GET");
            urlConnection.setDoInput(true);
            urlConnection.setDoOutput(true);
            urlConnection.setUseCaches(false);
            urlConnection.setRequestProperty("Content-Type", "application/json");
            urlConnection.connect();
//            OutputStreamWriter out = new OutputStreamWriter(urlConnection.getOutputStream());
//            out.write(data.toString());
//            out.close();

            printout = new DataOutputStream(urlConnection.getOutputStream());
//            printout.write(Integer.parseInt(URLEncoder.encode(data.toString(), "UTF-8")));
            String str = data.toString();
            byte[] data1 = str.getBytes("UTF-8");
            printout.write(data1);
            printout.flush();
            printout.close();

            code = urlConnection.getResponseCode();
            try {
                BufferedReader bufferedReader = new BufferedReader(
                        new InputStreamReader(urlConnection.getInputStream()));
                StringBuilder stringBuilder = new StringBuilder();
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line).append("\n");
                }
                bufferedReader.close();
                return stringBuilder.toString();
            } catch (Exception e) {
                Log.e("ERROR", e.getMessage(), e);
                return String.valueOf(code);
            } finally {
                urlConnection.disconnect();
            }
        } catch (MalformedURLException e) {
            Log.e("ERROR", e.getMessage(), e);
            return null;
        } catch (IOException e) {
            Log.e("ERROR", e.getMessage(), e);
            return String.valueOf(code);
        }
    }

//    public static String loginService1(String email, String password, String macAddr) {
//        String result = "";
//        HttpURLConnection urlConnection = null;
//        BufferedReader reader = null;
//
//        Uri buildUri = Uri.parse(Login_Url).buildUpon()
//                .appendQueryParameter("Username", email)
//                .appendQueryParameter("Password", password)
//                .appendQueryParameter("MachineID", "100001000100101")
//                .build();
//
//        URL url = null;
//        try {
//            url = new URL(buildUri.toString());
//
//            Log.i("Build Uri", buildUri.toString());
////            System.setProperty("http.keepAlive", "false");
//            // Create the request and open the connection
//            urlConnection = (HttpURLConnection) url.openConnection();
//            urlConnection.setConnectTimeout(30000);
////            urlConnection.setRequestMethod("GET");
////            urlConnection.setDoOutput(true);
////            urlConnection.setDoInput(true);
////            urlConnection.setRequestProperty("connection", "close");
////            urlConnection.connect();
//
//            // Read the input stream into a String
//            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
//            StringBuilder stringBuilder = new StringBuilder();
//            String line;
//            while ((line = bufferedReader.readLine()) != null) {
//                stringBuilder.append(line).append("\n");
//            }
//            bufferedReader.close();
//
//
//        } catch (MalformedURLException e) {
//            e.printStackTrace();
//        } catch (IOException e1) {
//            try {
//                int resp = urlConnection.getResponseCode();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            e1.printStackTrace();
//        } finally {
//            if (urlConnection != null) {
//                urlConnection.disconnect();
//            }
//            if (reader != null) {
//                try {
//                    reader.close();
//                } catch (final IOException e) {
//                    Log.e("Login Activity", "Error closing stream", e);
//                }
//            }
//        }
//        return result;
//    }
}
