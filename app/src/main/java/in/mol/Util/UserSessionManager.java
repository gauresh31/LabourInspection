package in.mol.Util;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;

import in.mol.models.ApplicationConstants;

/**
 * Created by 1742 on 29-04-2016.
 */
public class UserSessionManager {
    // Shared Preferences reference
    SharedPreferences pref;

    // Editor reference for Shared preferences
    SharedPreferences.Editor editor;

    // Context
    Context _context;

    // Shared pref mode
    int PRIVATE_MODE = 0;

    // Constructor
    public UserSessionManager(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(ApplicationConstants.PREFERENCE_NAME,
                PRIVATE_MODE);
        editor = pref.edit();
    }

    // Create login session
    public void createUserLoginSession(String login_info) {

        pref = _context.getSharedPreferences(ApplicationConstants.PREFERENCE_NAME,
                Context.MODE_PRIVATE);
        editor = pref.edit();
        editor.putString(ApplicationConstants.KEY_LOGIN_INFO, login_info);
        // commit changes
        editor.commit();
    }

    public void createLicenseDetails(String listArr) {
        pref = _context.getSharedPreferences(ApplicationConstants.PREFERENCE_NAME,
                Context.MODE_PRIVATE);
        editor = pref.edit();
        editor.putString(ApplicationConstants.KEY_LICENSE_INFO, listArr);
        // commit changes
        editor.commit();
    }

    public HashMap<String, String> getUserDetails() {
        pref = _context.getSharedPreferences(ApplicationConstants.PREFERENCE_NAME,
                Context.MODE_PRIVATE);
        // Use hashmap to store user credentials
        HashMap<String, String> user = new HashMap<>();

        // user name
        user.put(ApplicationConstants.KEY_LOGIN_INFO,
                pref.getString(ApplicationConstants.KEY_LOGIN_INFO, null));

        // return user
        return user;
    }

    public HashMap<String, String> getLicenseDetails() {
        pref = _context.getSharedPreferences(ApplicationConstants.PREFERENCE_NAME,
                Context.MODE_PRIVATE);
        // Use hashmap to store user credentials
        HashMap<String, String> user = new HashMap<>();

        // user name
        user.put(ApplicationConstants.KEY_LICENSE_INFO,
                pref.getString(ApplicationConstants.KEY_LICENSE_INFO, null));

        // return user
        return user;
    }

    public void createActList(String act_list, String objInspectionSchema) {
        pref = _context.getSharedPreferences(ApplicationConstants.PREFERENCE_NAME,
                Context.MODE_PRIVATE);
        editor = pref.edit();
        editor.putString(ApplicationConstants.KEY_ACT_LIST, act_list);
        editor.putString(ApplicationConstants.KEY_INSPECTION_SCHEMA, objInspectionSchema);

        // commit changes
        editor.commit();
    }

    public HashMap<String, String> getActsDetails() {
        pref = _context.getSharedPreferences(ApplicationConstants.PREFERENCE_NAME,
                Context.MODE_PRIVATE);
        // Use hashmap to store user credentials
        HashMap<String, String> user = new HashMap<>();

        // user name
        user.put(ApplicationConstants.KEY_ACT_LIST,
                pref.getString(ApplicationConstants.KEY_ACT_LIST, null));

        // return user
        return user;
    }

    public void createLicenseInspectionInfo(String license_no, String inspection_no) {
        editor.putString(ApplicationConstants.KEY_LICENSE_NO, license_no);
        editor.putString(ApplicationConstants.KEY_INSPECTION_NO, inspection_no);
        // commit changes
        editor.commit();
    }

    public String getLicenseNo() {
        String licence_no;
        pref = _context.getSharedPreferences(ApplicationConstants.PREFERENCE_NAME,
                Context.MODE_PRIVATE);
        licence_no = pref.getString(ApplicationConstants.KEY_LICENSE_NO, null);
        return licence_no;
    }

    public String getInspectionNo() {
        String insp_no;
        pref = _context.getSharedPreferences(ApplicationConstants.PREFERENCE_NAME,
                Context.MODE_PRIVATE);
        insp_no = pref.getString(ApplicationConstants.KEY_INSPECTION_NO, null);
        return insp_no;
    }


    public void createBasicInfoSession(String jsonn) {
        editor.putString(ApplicationConstants.KEY_BASIC_DETAILS_INFO, jsonn);
        // commit changes
        editor.commit();
    }

    public String getBasicDetailsInfo() {
        String basicInfo;
        pref = _context.getSharedPreferences(ApplicationConstants.PREFERENCE_NAME,
                Context.MODE_PRIVATE);
        basicInfo = pref.getString(ApplicationConstants.KEY_BASIC_DETAILS_INFO, null);
        return basicInfo;
    }

    public void createBasicDetailsInfo(String result) {
        editor.putString(ApplicationConstants.KEY_INFO_BASED_ON_LICENSE, result);
        // commit changes
        editor.commit();
    }
    public String getInfoBasedOnLicense() {
        String basicInfo;
        pref = _context.getSharedPreferences(ApplicationConstants.PREFERENCE_NAME,
                Context.MODE_PRIVATE);
        basicInfo = pref.getString(ApplicationConstants.KEY_INFO_BASED_ON_LICENSE, null);
        return basicInfo;
    }

}
