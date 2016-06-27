package in.mol.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import in.mol.Util.CustomTimeDialog;
import in.mol.labourinspection.MainActivity;
import in.mol.labourinspection.R;
import in.mol.Util.Utilities;

public class FragmentEmployerDetails extends Fragment {

    static MainActivity mainActivity;
    private static Context context;
    static ViewPager viewPager;
    private View view;
    static private EditText edit_name_of_worker, edit_designation, edit_lenght_of_service, edit_working_hours, edit_rest_time,
            edit_attend_card, edit_over_time_rate, edit_salary_per_day, edit_date_of_payment, edit_bonus;
    Button add, next;
    private Spinner spin_wages, spin_weeklyoff;
    JSONArray jsonArray;
    RecyclerView rc_emp;
    ContentAdapter contentAdapter;
    static JSONObject dataToDatabase;

    public static Fragment newInstance(MainActivity mainAct, Context applicationContext, ViewPager viewPgr) {
        FragmentEmployerDetails fragment = new FragmentEmployerDetails();
        mainActivity = mainAct;
        context = applicationContext;
        viewPager = viewPgr;

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_employer_details, container, false);

        init();
        setDefault();
        setEventListeners();

        return view;
    }

    private void init() {
        jsonArray = new JSONArray();
        dataToDatabase = new JSONObject();

        rc_emp = (RecyclerView) view.findViewById(R.id.rv_employer);
        edit_name_of_worker = (EditText) view.findViewById(R.id.edit_name_of_worker);
        edit_designation = (EditText) view.findViewById(R.id.edit_designation);
        edit_lenght_of_service = (EditText) view.findViewById(R.id.edit_length_of_service);
        edit_working_hours = (EditText) view.findViewById(R.id.edit_working_hours);
        edit_rest_time = (EditText) view.findViewById(R.id.edit_rest_time);
        edit_attend_card = (EditText) view.findViewById(R.id.edit_attend_card);
        edit_over_time_rate = (EditText) view.findViewById(R.id.edit_over_time_rate);
        edit_salary_per_day = (EditText) view.findViewById(R.id.edit_salary_per_day);
        edit_date_of_payment = (EditText) view.findViewById(R.id.edit_date_of_payment);
        edit_bonus = (EditText) view.findViewById(R.id.edit_bonus);
        spin_wages = (Spinner) view.findViewById(R.id.spin_wages);
        spin_weeklyoff = (Spinner) view.findViewById(R.id.spin_weeklyOff);

        add = (Button) view.findViewById(R.id.btn_add);
        next = (Button) view.findViewById(R.id.btn_next);
    }

    private void setDefault() {
        List<String> lstWeekly = new ArrayList<>();
        lstWeekly.add("Select");
        lstWeekly.add("Sunday");
        lstWeekly.add("Monday");
        lstWeekly.add("Tuesday");
        lstWeekly.add("Wednesday");
        lstWeekly.add("Thursday");
        lstWeekly.add("Friday");
        lstWeekly.add("Saturday");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                context, R.layout.default_textview, lstWeekly);

        spin_weeklyoff.setAdapter(adapter);

        List<String> lstWages = new ArrayList<>();
        lstWages.add("Select");
        lstWages.add("Yes");
        lstWages.add("No");

        ArrayAdapter<String> adapterWages = new ArrayAdapter<>(
                context, R.layout.default_textview, lstWages);

        spin_wages.setAdapter(adapterWages);

        contentAdapter = new ContentAdapter(jsonArray);

        rc_emp.setAdapter(contentAdapter);
        rc_emp.setHasFixedSize(true);
        rc_emp.setLayoutManager(new LinearLayoutManager(getActivity()));
        rc_emp.setItemAnimator(new DefaultItemAnimator());

    }

    private void setEventListeners() {
        edit_date_of_payment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerFragment newFragment = new DatePickerFragment();
                newFragment.setInstance(edit_date_of_payment);
                newFragment.show(getFragmentManager(), "datePicker");
            }
        });

        edit_working_hours.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                TimePickerFragment newFragment = new TimePickerFragment();
//                newFragment.getInstance(edit_working_hours);
//                newFragment.show(getFragmentManager(), "timePicker");

                CustomTimeDialog dialogBuilder = new CustomTimeDialog(mainActivity, edit_working_hours);
                dialogBuilder.setTitle("Set Time");
                AlertDialog alertDialog = dialogBuilder.create();
                dialogBuilder.getAlert(alertDialog);
                alertDialog.show();

            }
        });

        edit_rest_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomTimeDialog dialogBuilder = new CustomTimeDialog(mainActivity, edit_rest_time);
                dialogBuilder.setTitle("Set Time");
                AlertDialog alertDialog = dialogBuilder.create();
                dialogBuilder.getAlert(alertDialog);
                alertDialog.show();


            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validate()) {
                    addData();
                }

            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveFinalData();
            }
        });
    }

    public void sendData(String basicJson) {
        try {
            dataToDatabase = new JSONObject(basicJson);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void addData() {
        JSONObject json = new JSONObject();

        try {
            json.put("Name", edit_name_of_worker.getText().toString()
                    .trim());
            json.put("Designation", edit_designation.getText().toString()
                    .trim());
            json.put("LengthOfService", edit_lenght_of_service.getText().toString()
                    .trim());
            json.put("WorkingHr", edit_working_hours.getText().toString()
                    .trim());
            json.put("RestHr", edit_rest_time.getText().toString()
                    .trim());
            json.put("AttendCard", edit_attend_card.getText().toString()
                    .trim());
            json.put("OverTimeRate", edit_over_time_rate.getText().toString()
                    .trim());
            json.put("SalayPerDay", edit_salary_per_day.getText().toString()
                    .trim());
            json.put("DateOfPayment", edit_date_of_payment.getText().toString()
                    .trim());
            json.put("Bonus", edit_bonus.getText().toString()
                    .trim());
            json.put("EmployeeWeeklyOff", spin_weeklyoff.getSelectedItem().toString());

            if (spin_wages.getSelectedItem().toString().equalsIgnoreCase("Yes")) {
                json.put("LeaveWithWages", "1");
            } else {
                json.put("LeaveWithWages", "0");
            }

            jsonArray.put(json);
            Utilities.showMessage("Employer Added", context);
            contentAdapter.notifyDataSetChanged();
            clear();

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void clear() {
        edit_name_of_worker.setText("");
        edit_designation.setText("");
        edit_lenght_of_service.setText("");
        edit_working_hours.setText("");
        edit_rest_time.setText("");
        edit_attend_card.setText("");
        edit_over_time_rate.setText("");
        edit_salary_per_day.setText("");
        edit_date_of_payment.setText("");
        edit_bonus.setText("");

        spin_wages.setSelection(0);
        spin_weeklyoff.setSelection(0);
    }

    private void saveFinalData() {
        if (jsonArray.length() > 0) {
            try {
                dataToDatabase.put("objInspectedEmpDetails", jsonArray);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            PagerAdapter fragmentPagerAdapter = new PagerAdapter(getFragmentManager());
            FragmentActs frag = (FragmentActs) fragmentPagerAdapter.getItem(1);
            frag.sendData(dataToDatabase.toString());

            Utilities.showMessage("Data Saved Successfully", context);

            viewPager.setCurrentItem(2);
        } else {
            Utilities.showMessage("Enter Employee details", context);
        }
    }

    private boolean validate() {

        if (edit_name_of_worker.getText().toString().equalsIgnoreCase("")) {
            Utilities.showMessage("Enter Name", context);
            return false;
        }
        if (edit_designation.getText().toString().equalsIgnoreCase("")) {
            Utilities.showMessage("Enter Designation", context);
            return false;
        }

        if (edit_lenght_of_service.getText().toString().equalsIgnoreCase("")) {
            Utilities.showMessage("Enter Length of Servcie", context);
            return false;
        }
        if (edit_working_hours.getText().toString().equalsIgnoreCase("")) {
            Utilities.showMessage("Enter Working hours", context);
            return false;
        }
        if (edit_rest_time.getText().toString().equalsIgnoreCase("")) {
            Utilities.showMessage("Enter Rest time", context);
            return false;
        }
        if (edit_attend_card.getText().toString().equalsIgnoreCase("")) {
            Utilities.showMessage("Enter Attendance card", context);
            return false;
        }
        if (edit_over_time_rate.getText().toString().equalsIgnoreCase("")) {
            Utilities.showMessage("Enter Overtime rate", context);
            return false;
        }
        if (edit_salary_per_day.getText().toString().equalsIgnoreCase("")) {
            Utilities.showMessage("Enter Salary per day", context);
            return false;
        }
        if (edit_date_of_payment.getText().toString().equalsIgnoreCase("")) {
            Utilities.showMessage("Enter Date of Payment", context);
            return false;
        }
        if (edit_bonus.getText().toString().equalsIgnoreCase("")) {
            Utilities.showMessage("Enter Bonus", context);
            return false;
        }
        if (spin_wages.getSelectedItem().toString().equalsIgnoreCase("Select")) {
            Utilities.showMessage("Select Wages", context);
            return false;
        }
        if (spin_weeklyoff.getSelectedItem().toString().equalsIgnoreCase("Select")) {
            Utilities.showMessage("Select Weekly Off", context);
            return false;
        }

        return true;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        final EditText edit_name, edit_desig, edit_len, edit_work_hrs;
        final TextView tv_emp_num;

        public ViewHolder(View view) {
            super(view);
            edit_name = (EditText) view.findViewById(R.id.edit_holder_name_of_worker);
            edit_desig = (EditText) view.findViewById(R.id.edit_holder_designation);
            edit_len = (EditText) view.findViewById(R.id.edit_holder_lenght_of_service);
            edit_work_hrs = (EditText) view.findViewById(R.id.edit_holder_working_hours);
            tv_emp_num = (TextView) view.findViewById(R.id.tv_holder_num);
        }
    }

    public class ContentAdapter extends RecyclerView.Adapter<ViewHolder> {

        JSONArray arrEmpInfo;
        int count;

        public ContentAdapter(JSONArray arrEmp) {
            this.arrEmpInfo = arrEmp;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.holder_employer_details, parent, false);

            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            JSONObject json = jsonArray.optJSONObject(position);

            try {
                holder.tv_emp_num.setText(Integer.toString(position + 1));
                holder.edit_name.setText(json.getString("Name"));
                holder.edit_desig.setText(json.getString("Designation"));
                holder.edit_len.setText(json.getString("LengthOfService"));
                holder.edit_work_hrs.setText(json.getString("WorkingHr"));

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        public int getItemCount() {
            count = this.arrEmpInfo.length();
            return count;
        }
    }

    public class PagerAdapter extends FragmentPagerAdapter {

        private FragmentEmployerDetails empDetailsfragment;
        private FragmentActs actFragment;

        public PagerAdapter(FragmentManager manager) {
            super(manager);
            this.empDetailsfragment = new FragmentEmployerDetails();
            this.actFragment = new FragmentActs();
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public Fragment getItem(int position) {

            switch (position) {
                case 0:
                    return empDetailsfragment;

                case 1:
                    return actFragment;
                default:
                    return null;
            }
        }
    }
}
