package in.mol.labourinspection;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.Calendar;

public class FormActivity extends AppCompatActivity implements View.OnClickListener {

    EditText name_of_establishment, address, name_of_employer, male, female, total,
            registration, schedule_empl, working_hours, weekly_off;
    static EditText date;
    Button submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        init();
        setDefault();
        setEventListeners();
    }

    private void init() {
        submit = (Button) findViewById(R.id.btn_submit);
        name_of_establishment = (EditText) findViewById(R.id.edit_name_of_establishment);
        address = (EditText) findViewById(R.id.edit_address);
        name_of_employer = (EditText) findViewById(R.id.edit_name_of_employer);
        male = (EditText) findViewById(R.id.edit_male);
        female = (EditText) findViewById(R.id.edit_female);
        total = (EditText) findViewById(R.id.edit_total);
        registration = (EditText) findViewById(R.id.edit_registration_under);
        schedule_empl = (EditText) findViewById(R.id.edit_schedule_employment);
        working_hours = (EditText) findViewById(R.id.edit_working_hours);
        weekly_off = (EditText) findViewById(R.id.edit_weekly_off);
        date = (EditText) findViewById(R.id.edit_date_of_inspection);

    }

    private void setDefault() {

    }

    private void setEventListeners() {
        date.setOnClickListener(this);
        submit.setOnClickListener(this);

        male.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                try {
                    if (male.getText().toString().equals("") && female.getText().toString().equals("")) {
                        total.setText("");
                    } else {
                        int strMale = Integer.parseInt(male.getText().toString());
                        int strFemale;
                        if (!female.getText().toString().equalsIgnoreCase("")) {
                            strFemale = Integer.parseInt(female.getText().toString());
                        } else {
                            strFemale = 0;
                        }
                        total.setText(Integer.toString(strMale + strFemale));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        female.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                try {
                    if (male.getText().toString().equals("") && female.getText().toString().equals("")) {
                        total.setText("");
                    } else {
                        int strFemale = Integer.parseInt(female.getText().toString());
                        int strMale;
                        if (!male.getText().toString().equalsIgnoreCase("")) {
                            strMale = Integer.parseInt(male.getText().toString());
                        } else {
                            strMale = 0;
                        }
                        total.setText(Integer.toString(strMale + strFemale));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }

    public static class DatePickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            // Create a new instance of DatePickerDialog and return it
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            // Do something with the date chosen by the user
            date.setText(day + "/" + (month + 1) + "/" + year);

        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_submit:
                if (validation()) {
                }
                break;

            case R.id.edit_date_of_inspection:
                DialogFragment newFragment = new DatePickerFragment();
                newFragment.show(getSupportFragmentManager(), "datePicker");
                break;
        }
    }

    private boolean validation() {

//        int resQue1 = rg_que_1.getCheckedRadioButtonId();
//        if (resQue1 > 0) {
//
//        }

        return false;
    }
}
