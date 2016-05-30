package in.mol.labourinspection;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import in.mol.models.UserSessionManager;

public class ActivityActList extends AppCompatActivity implements View.OnClickListener {

    LinearLayout ll_act_1, ll_act_2, ll_act_3, ll_act_4, ll_act_5, ll_act_6, ll_act_7,
            ll_act_8, ll_act_9, ll_act_10, ll_act_11, ll_act_12, ll_act_13, ll_act_14, ll_act_15;
    UserSessionManager sessionManager;
    TextView tv_header;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_list);

        init();
        setDefaults();
        setEventListeners();
    }


    private void init() {
        sessionManager = new UserSessionManager(getApplicationContext());

        tv_header = (TextView) findViewById(R.id.tv_header);
        ll_act_1 = (LinearLayout) findViewById(R.id.ll_act_1);
        ll_act_2 = (LinearLayout) findViewById(R.id.ll_act_2);
        ll_act_3 = (LinearLayout) findViewById(R.id.ll_act_3);
        ll_act_4 = (LinearLayout) findViewById(R.id.ll_act_4);
        ll_act_5 = (LinearLayout) findViewById(R.id.ll_act_5);
        ll_act_6 = (LinearLayout) findViewById(R.id.ll_act_6);
        ll_act_7 = (LinearLayout) findViewById(R.id.ll_act_7);
        ll_act_8 = (LinearLayout) findViewById(R.id.ll_act_8);
        ll_act_9 = (LinearLayout) findViewById(R.id.ll_act_9);
        ll_act_10 = (LinearLayout) findViewById(R.id.ll_act_10);
        ll_act_11 = (LinearLayout) findViewById(R.id.ll_act_11);
        ll_act_12 = (LinearLayout) findViewById(R.id.ll_act_12);
        ll_act_13 = (LinearLayout) findViewById(R.id.ll_act_13);
        ll_act_14 = (LinearLayout) findViewById(R.id.ll_act_14);
        ll_act_15 = (LinearLayout) findViewById(R.id.ll_act_15);

    }

    private void setDefaults() {
        String license = sessionManager.getLicenseNo();

        tv_header.setText("Select Act for License no:" + license);
    }


    private void setEventListeners() {
        ll_act_1.setOnClickListener(this);
        ll_act_2.setOnClickListener(this);
        ll_act_3.setOnClickListener(this);
        ll_act_4.setOnClickListener(this);
        ll_act_5.setOnClickListener(this);
        ll_act_6.setOnClickListener(this);
        ll_act_7.setOnClickListener(this);
        ll_act_8.setOnClickListener(this);
        ll_act_9.setOnClickListener(this);
        ll_act_10.setOnClickListener(this);
        ll_act_11.setOnClickListener(this);
        ll_act_12.setOnClickListener(this);
        ll_act_13.setOnClickListener(this);
        ll_act_14.setOnClickListener(this);
        ll_act_15.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        Intent in;
        switch (v.getId()) {

            case R.id.ll_act_1:
                ll_act_1.setBackgroundColor(Color.parseColor("#D2E4FC"));
                in = new Intent(ActivityActList.this, MainActivity.class);
                in.putExtra("act_name", getResources().getString(R.string.act_1948));
                in.putExtra("act_id", "101");
                startActivity(in);
                break;

            case R.id.ll_act_2:
                ll_act_2.setBackgroundColor(Color.parseColor("#D2E4FC"));
                in = new Intent(ActivityActList.this, MainActivity.class);
                in.putExtra("act_name", getResources().getString(R.string.act_1936));
                in.putExtra("act_id", "102");
                startActivity(in);
                break;

            case R.id.ll_act_3:
                ll_act_3.setBackgroundColor(Color.parseColor("#D2E4FC"));
                in = new Intent(ActivityActList.this, MainActivity.class);
                in.putExtra("act_name", getResources().getString(R.string.act_1976));
                in.putExtra("act_id", "103");
                startActivity(in);
                break;

            case R.id.ll_act_4:
                ll_act_4.setBackgroundColor(Color.parseColor("#D2E4FC"));
                in = new Intent(ActivityActList.this, MainActivity.class);
                in.putExtra("act_name", getResources().getString(R.string.act_1965));
                in.putExtra("act_id", "104");
                startActivity(in);
                break;

            case R.id.ll_act_5:
                ll_act_5.setBackgroundColor(Color.parseColor("#D2E4FC"));
                in = new Intent(ActivityActList.this, MainActivity.class);
                in.putExtra("act_name", getResources().getString(R.string.act_1983));
                in.putExtra("act_id", "105");
                startActivity(in);
                break;

            case R.id.ll_act_6:
                ll_act_6.setBackgroundColor(Color.parseColor("#D2E4FC"));
                in = new Intent(ActivityActList.this, MainActivity.class);
                in.putExtra("act_name", getResources().getString(R.string.act_1966));
                in.putExtra("act_id", "106");
                startActivity(in);
                break;

            case R.id.ll_act_7:
                ll_act_7.setBackgroundColor(Color.parseColor("#D2E4FC"));
                in = new Intent(ActivityActList.this, MainActivity.class);
                in.putExtra("act_name", getResources().getString(R.string.act_1970));
                in.putExtra("act_id", "107");
                startActivity(in);
                break;

            case R.id.ll_act_8:
                ll_act_8.setBackgroundColor(Color.parseColor("#D2E4FC"));
                in = new Intent(ActivityActList.this, MainActivity.class);
                in.putExtra("act_name", getResources().getString(R.string.act_1961));
                in.putExtra("act_id", "108");
                startActivity(in);
                break;

            case R.id.ll_act_9:
                ll_act_9.setBackgroundColor(Color.parseColor("#D2E4FC"));
                in = new Intent(ActivityActList.this, MainActivity.class);
                in.putExtra("act_name", getResources().getString(R.string.act_1976_sales));
                in.putExtra("act_id", "109");
                startActivity(in);
                break;

            case R.id.ll_act_10:
                ll_act_10.setBackgroundColor(Color.parseColor("#D2E4FC"));
                in = new Intent(ActivityActList.this, MainActivity.class);
                in.putExtra("act_name", getResources().getString(R.string.act_1996));
                in.putExtra("act_id", "110");
                startActivity(in);
                break;

            case R.id.ll_act_11:
                ll_act_11.setBackgroundColor(Color.parseColor("#D2E4FC"));
                in = new Intent(ActivityActList.this, MainActivity.class);
                in.putExtra("act_name", getResources().getString(R.string.act_1955));
                in.putExtra("act_id", "111");
                startActivity(in);
                break;

            case R.id.ll_act_12:
                ll_act_12.setBackgroundColor(Color.parseColor("#D2E4FC"));
                in = new Intent(ActivityActList.this, MainActivity.class);
                in.putExtra("act_name", getResources().getString(R.string.act_1979));
                in.putExtra("act_id", "112");
                startActivity(in);
                break;

            case R.id.ll_act_13:
                ll_act_13.setBackgroundColor(Color.parseColor("#D2E4FC"));
                in = new Intent(ActivityActList.this, MainActivity.class);
                in.putExtra("act_name", getResources().getString(R.string.act_1961_maternity));
                in.putExtra("act_id", "113");
                startActivity(in);
                break;

            case R.id.ll_act_14:
                ll_act_14.setBackgroundColor(Color.parseColor("#D2E4FC"));
                in = new Intent(ActivityActList.this, MainActivity.class);
                in.putExtra("act_name", getResources().getString(R.string.act_1972));
                in.putExtra("act_id", "114");
                startActivity(in);
                break;

            case R.id.ll_act_15:
                ll_act_15.setBackgroundColor(Color.parseColor("#D2E4FC"));
                in = new Intent(ActivityActList.this, MainActivity.class);
                in.putExtra("act_name", getResources().getString(R.string.act_1986));
                in.putExtra("act_id", "115");
                startActivity(in);
                break;

            default:
                break;
        }
    }
}
