package in.mol.labourinspection;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import in.mol.fragments.FragmentActs;
import in.mol.fragments.FragmentBasicDetails;
import in.mol.fragments.FragmentEmployerDetails;
import in.mol.fragments.FragmentRemarks;
import in.mol.models.ApplicationConstants;
import in.mol.Util.UserSessionManager;

public class MainActivity extends AppCompatActivity implements FragmentBasicDetails.OnFragmentInteractionListener {

    Toolbar toolbar;
    TabLayout tabs;
    ViewPager viewPager;
    UserSessionManager session;
    JSONArray jsonArray;
    Intent in;
    String act_name, act_id, username, userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
        setDefaults();

    }

    private void init() {
        session = new UserSessionManager(getApplicationContext());
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        tabs = (TabLayout) findViewById(R.id.tabs);
        viewPager = (ViewPager) findViewById(R.id.viewpager);

        in = getIntent();

        try {
            JSONObject user_data = new JSONObject(session.getUserDetails().
                    get(ApplicationConstants.KEY_LOGIN_INFO));
            username = user_data.getString("UserName");
            userId = user_data.getString("UserID");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void setDefaults() {
        setSupportActionBar(toolbar);

//        try {
//            jsonArray = new JSONArray(session.getActsDetails().
//                    get(ApplicationConstants.KEY_ACT_LIST));
//
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
        act_name = in.getStringExtra("act_name");
        act_id = in.getStringExtra("act_id");

        setupViewPager(viewPager);
        tabs.setupWithViewPager(viewPager);

        //Disable touch event on tabs
//        LinearLayout tabStrip = ((LinearLayout) tabs.getChildAt(0));
//        for (int i = 0; i < tabStrip.getChildCount(); i++) {
//            tabStrip.getChildAt(i).setOnTouchListener(new View.OnTouchListener() {
//                @Override
//                public boolean onTouch(View v, MotionEvent event) {
//                    return true;
//                }
//            });
//        }
    }

    // Add Fragments to ViewPager
    private void setupViewPager(final ViewPager viewPager) {
        Adapter adapter = new Adapter(getSupportFragmentManager());
        adapter.addFragment(FragmentBasicDetails.
                newInstance(MainActivity.this, getApplicationContext(), viewPager, username, userId), "Basic Data");
        adapter.addFragment(FragmentEmployerDetails.
                newInstance(MainActivity.this, getApplicationContext(), viewPager), "Employer Details");
        adapter.addFragment(FragmentActs.
                newInstance(MainActivity.this, getApplicationContext(), viewPager, username, userId), "Acts");
        adapter.addFragment(FragmentRemarks.
                newInstance(MainActivity.this, getApplicationContext(), viewPager, username, userId), "Remarks");
//        adapter.addFragment(RulesFragment.
//                newInstance(MainActivity.this, getApplicationContext(), viewPager, "", act_id, act_name, username, userId), "Rules");

        viewPager.setAdapter(adapter);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {
    }

    static class Adapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public Adapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    @Override
    public void onBackPressed() {

        //get current tab index.
        int index = tabs.getSelectedTabPosition();

        //decide what to do
        if(index!=0){
            viewPager.setCurrentItem(index-1);
        } else {
            super.onBackPressed();
        }
    }
}
