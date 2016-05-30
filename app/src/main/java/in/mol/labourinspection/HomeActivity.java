package in.mol.labourinspection;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

public class HomeActivity extends AppCompatActivity {

    TextView tvTotalInspection, tvCompleted, tvPending;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        init();
        setDefaults();
        setEventListeners();
    }

    private void init() {
        tvTotalInspection = (TextView) findViewById(R.id.tv_inspection);
        tvCompleted = (TextView) findViewById(R.id.tv_completed);
        tvPending = (TextView) findViewById(R.id.tv_pending);

    }

    private void setDefaults() {

    }

    private void setEventListeners() {
    }

}
