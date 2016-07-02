package in.mol.fragments;

import android.content.Context;
import android.graphics.Canvas;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import in.mol.database.DatabaseHelper;
import in.mol.labourinspection.MainActivity;
import in.mol.labourinspection.R;
import in.mol.models.SpinnerObject;
import in.mol.Util.UserSessionManager;
import in.mol.Util.Utilities;

public class FragmentActs extends Fragment {

    View view;
    private static ViewPager viewPager;
    static Context context;
    private static MainActivity mainActivity;

    private static final String ARG_PARAM1 = "User_NAME";
    private static final String ARG_PARAM2 = "User_ID";
    private String actNAME, user_name, user_id;
    private Spinner spin_acts;
    private DatabaseHelper dbHelper;
    List<SpinnerObject> m_list_acts;
    List<SpinnerObject> m_list_rules;
    private UserSessionManager session;
    ContentAdapter contentAdapter;
    String[] etValArr;
    boolean[] isSelect;
    RecyclerView recyclerView;
    LinearLayout ll_skilled, ll_semiskilled, ll_unskilled, ll_highly_skilled;
    EditText edit_highly_skilled_basic, edit_highly_skilled_special, edit_highly_skilled_total,
            edit_skilled_basic, edit_skilled_special, edit_skilled_total,
            edit_semi_skilled_basic, edit_semi_skilled_special, edit_semi_total,
            edit_unskilled_basic, edit_unskilled_special, edit_unskilled_total;
    TextView tv_min_wages;
    private Button save, next;
    String act_Id, act_name, basicInfo;
    JSONArray arrRules, arrSelectedRules;
    JSONObject rules, selectedActs, selectedActSchema;

    static JSONObject dataToDatabase;
    JSONArray jarr;

    public FragmentActs() {
        // Required empty public constructor
    }

    public static FragmentActs newInstance(MainActivity mainAct, Context con, ViewPager viewPagr,
                                           String userName, String userID) {
        FragmentActs fragment = new FragmentActs();
        Bundle args = new Bundle();

        args.putString(ARG_PARAM1, userName);
        args.putString(ARG_PARAM2, userID);
        fragment.setArguments(args);

        viewPager = viewPagr;
        context = con;
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

        view = inflater.inflate(R.layout.fragment_acts, container, false);

        init();
        setDefaults();
        setEventListeners();

        return view;
    }

    private void init() {
        dbHelper = new DatabaseHelper(getActivity());
        m_list_acts = new ArrayList<>();
        session = new UserSessionManager(context);
        jarr = new JSONArray();
        arrSelectedRules = new JSONArray();
        dataToDatabase = new JSONObject();
        selectedActs = new JSONObject();
        selectedActSchema = new JSONObject();

        spin_acts = (Spinner) view.findViewById(R.id.spn_select_acts);
        recyclerView = (RecyclerView) view.findViewById(R.id.rules_recycler_view);
        save = (Button) view.findViewById(R.id.btn_submit);
        next = (Button) view.findViewById(R.id.btn_next);

        ll_skilled = (LinearLayout) view.findViewById(R.id.ll_skilled);
        ll_semiskilled = (LinearLayout) view.findViewById(R.id.ll_semi_skilled);
        ll_unskilled = (LinearLayout) view.findViewById(R.id.ll_unskilled);
        ll_highly_skilled = (LinearLayout) view.findViewById(R.id.ll_highly_skilled);

        edit_highly_skilled_basic = (EditText) view.findViewById(R.id.edt_highly_skilled_basic);
        edit_highly_skilled_special = (EditText) view.findViewById(R.id.edt_highly_skilled_special_allow);
        edit_highly_skilled_total = (EditText) view.findViewById(R.id.edt_highly_skilled_total);

        edit_skilled_basic = (EditText) view.findViewById(R.id.edt_skilled_basic);
        edit_skilled_special = (EditText) view.findViewById(R.id.edt_skilled_special_allow);
        edit_skilled_total = (EditText) view.findViewById(R.id.edt_skilled_total);

        edit_semi_skilled_basic = (EditText) view.findViewById(R.id.edt_semi_skilled_basic);
        edit_semi_skilled_special = (EditText) view.findViewById(R.id.edt_semi_skilled_special_allow);
        edit_semi_total = (EditText) view.findViewById(R.id.edt_semi_skilled_total);

        edit_unskilled_basic = (EditText) view.findViewById(R.id.edt_unskilled_basic);
        edit_unskilled_special = (EditText) view.findViewById(R.id.edt_unskilled_special_allow);
        edit_unskilled_total = (EditText) view.findViewById(R.id.edt_unskilled_total);

        tv_min_wages = (TextView) view.findViewById(R.id.tv_minimum_wages);

        tv_min_wages.setVisibility(View.GONE);
        Utilities.invisibleAll(ll_skilled, ll_semiskilled, ll_unskilled, ll_highly_skilled);
    }

    private void setDefaults() {
        m_list_acts = dbHelper.getActs();

        ArrayAdapter<SpinnerObject> adapter = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_dropdown_item_1line,
                m_list_acts);
        spin_acts.setAdapter(adapter);

        if (spin_acts.getSelectedItem().toString().equalsIgnoreCase("Select")) {
            save.setVisibility(View.GONE);
            next.setVisibility(View.GONE);
        }
    }

    private void setEventListeners() {
        spin_acts.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                SpinnerObject spinnerObject = (SpinnerObject) parent.getItemAtPosition(position);
                act_Id = spinnerObject.getRuleId();
                act_name = spinnerObject.getRuleName();

                if (act_Id.equalsIgnoreCase("0")) {
                    save.setVisibility(View.GONE);
                    next.setVisibility(View.GONE);
                } else {
                    save.setVisibility(View.VISIBLE);
                    next.setVisibility(View.VISIBLE);
                }

                if (act_Id.equalsIgnoreCase("101")) {
                    tv_min_wages.setVisibility(View.VISIBLE);
                    Utilities.visibleAll(ll_skilled, ll_semiskilled, ll_unskilled, ll_highly_skilled);
                } else {
                    tv_min_wages.setVisibility(View.GONE);
                    Utilities.invisibleAll(ll_skilled, ll_semiskilled, ll_unskilled, ll_highly_skilled);
                }

                m_list_rules = dbHelper.getRules(act_Id);
                contentAdapter = new ContentAdapter(m_list_rules);
                recyclerView.setAdapter(contentAdapter);
                recyclerView.setHasFixedSize(true);
                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                recyclerView.setItemAnimator(new DefaultItemAnimator());
                RecyclerView.ItemDecoration itemDecoration = new RecyclerView.ItemDecoration() {
                    @Override
                    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
                        super.onDrawOver(c, parent, state);
                    }
                };
                recyclerView.addItemDecoration(itemDecoration);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        edit_highly_skilled_basic.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                try {
                    if (edit_highly_skilled_basic.getText().toString().equals("") && edit_highly_skilled_special.getText().toString().equals("")) {
                        edit_highly_skilled_total.setText("");
                    } else {
                        double basic = Double.valueOf(edit_highly_skilled_basic.getText().toString());
                        double allow;
                        if (!edit_highly_skilled_special.getText().toString().equalsIgnoreCase("")) {
                            allow = Double.valueOf(edit_highly_skilled_special.getText().toString());
                        } else {
                            allow = 0;
                        }
                        DecimalFormat df = new DecimalFormat(".00");
                        String formate = df.format((basic + allow));
                        edit_highly_skilled_total.setText(formate);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        edit_highly_skilled_special.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                try {
                    if (edit_highly_skilled_basic.getText().toString().equals("") && edit_highly_skilled_special.getText().toString().equals("")) {
                        edit_highly_skilled_total.setText("");
                    } else {
                        double basic = Double.valueOf(edit_highly_skilled_special.getText().toString());
                        double allow;
                        if (!edit_highly_skilled_basic.getText().toString().equalsIgnoreCase("")) {
                            allow = Double.valueOf(edit_highly_skilled_basic.getText().toString());
                        } else {
                            allow = 0;
                        }
                        DecimalFormat df = new DecimalFormat(".00");
                        String formate = df.format((basic + allow));
                        edit_highly_skilled_total.setText(formate);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        edit_skilled_basic.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                try {
                    if (edit_skilled_basic.getText().toString().equals("") && edit_skilled_special.getText().toString().equals("")) {
                        edit_skilled_total.setText("");
                    } else {
                        double basic = Double.valueOf(edit_skilled_basic.getText().toString());
                        double allow;
                        if (!edit_skilled_special.getText().toString().equalsIgnoreCase("")) {
                            allow = Double.valueOf(edit_skilled_special.getText().toString());
                        } else {
                            allow = 0;
                        }
                        DecimalFormat df = new DecimalFormat(".00");
                        String formate = df.format((basic + allow));
                        edit_skilled_total.setText(formate);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        edit_skilled_special.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                try {
                    if (edit_skilled_basic.getText().toString().equals("") && edit_skilled_special.getText().toString().equals("")) {
                        edit_skilled_total.setText("");
                    } else {
                        double basic = Double.valueOf(edit_skilled_special.getText().toString());
                        double allow;
                        if (!edit_skilled_basic.getText().toString().equalsIgnoreCase("")) {
                            allow = Double.valueOf(edit_skilled_basic.getText().toString());
                        } else {
                            allow = 0;
                        }
                        DecimalFormat df = new DecimalFormat(".00");
                        String formate = df.format((basic + allow));
                        edit_skilled_total.setText(formate);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        edit_semi_skilled_basic.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                try {
                    if (edit_semi_skilled_basic.getText().toString().equals("") && edit_semi_skilled_special.getText().toString().equals("")) {
                        edit_semi_total.setText("");
                    } else {
                        double basic = Double.valueOf(edit_semi_skilled_basic.getText().toString());
                        double allow;
                        if (!edit_semi_skilled_special.getText().toString().equalsIgnoreCase("")) {
                            allow = Double.valueOf(edit_semi_skilled_special.getText().toString());
                        } else {
                            allow = 0;
                        }
                        DecimalFormat df = new DecimalFormat(".00");
                        String formate = df.format((basic + allow));
                        edit_semi_total.setText(formate);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        edit_semi_skilled_special.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                try {
                    if (edit_semi_skilled_basic.getText().toString().equals("") && edit_semi_skilled_special.getText().toString().equals("")) {
                        edit_semi_total.setText("");
                    } else {
                        double basic = Double.valueOf(edit_semi_skilled_special.getText().toString());
                        double allow;
                        if (!edit_semi_skilled_basic.getText().toString().equalsIgnoreCase("")) {
                            allow = Double.valueOf(edit_semi_skilled_basic.getText().toString());
                        } else {
                            allow = 0;
                        }
                        DecimalFormat df = new DecimalFormat(".00");
                        String formate = df.format((basic + allow));
                        edit_semi_total.setText(formate);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        edit_unskilled_basic.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                try {
                    if (edit_unskilled_basic.getText().toString().equals("") && edit_unskilled_special.getText().toString().equals("")) {
                        edit_unskilled_total.setText("");
                    } else {
                        double basic = Double.valueOf(edit_unskilled_basic.getText().toString());
                        double allow;
                        if (!edit_unskilled_special.getText().toString().equalsIgnoreCase("")) {
                            allow = Double.valueOf(edit_unskilled_special.getText().toString());
                        } else {
                            allow = 0;
                        }
                        DecimalFormat df = new DecimalFormat(".00");
                        String formate = df.format((basic + allow));
                        edit_unskilled_total.setText(formate);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        edit_unskilled_special.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                try {
                    if (edit_unskilled_basic.getText().toString().equals("") && edit_unskilled_special.getText().toString().equals("")) {
                        edit_unskilled_total.setText("");
                    } else {
                        double basic = Double.valueOf(edit_unskilled_special.getText().toString());
                        double allow;
                        if (!edit_unskilled_basic.getText().toString().equalsIgnoreCase("")) {
                            allow = Double.valueOf(edit_unskilled_basic.getText().toString());
                        } else {
                            allow = 0;
                        }
                        DecimalFormat df = new DecimalFormat(".00");
                        String formate = df.format((basic + allow));
                        edit_unskilled_total.setText(formate);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });


        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveData();
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

    public class PagerAdapter extends FragmentPagerAdapter {

        private FragmentRemarks fragmentRemarks;
        private FragmentActs actFragment;

        public PagerAdapter(FragmentManager manager) {
            super(manager);
            this.fragmentRemarks = new FragmentRemarks();
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
                    return actFragment;

                case 1:
                    return fragmentRemarks;
                default:
                    return null;
            }
        }
    }

    private void saveFinalData() {
        try {
            dataToDatabase.put("objLabourActSchema", selectedActs);
            PagerAdapter fragmentPagerAdapter = new PagerAdapter(getFragmentManager());
            FragmentRemarks frag = (FragmentRemarks) fragmentPagerAdapter.getItem(1);
            frag.sendData(dataToDatabase.toString());

            Utilities.showMessage("Data Saved", context);
            viewPager.setCurrentItem(3);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void saveData() {
        int len = m_list_rules.size();
        JSONArray arrSelect;

        rules = new JSONObject();
        for (int i = 0; i < len; i++) {
            try {
                arrSelectedRules = dataToDatabase
                        .getJSONArray("objLabourRulesSchema");
            } catch (JSONException e1) {
                e1.printStackTrace();
            }
            if (isSelect[i]) {
                try {
                    JSONObject jsonData = new JSONObject();
                    jsonData.put("RuleId", m_list_rules.get(i).getRuleId());
                    jsonData.put("RuleName", m_list_rules.get(i).getRuleName());
                    jsonData.put("Actid", act_Id);
                    jsonData.put("IsSelected", isSelect[i]);
                    jsonData.put("ActName", JSONObject.NULL);
                    jsonData.put("ComplaintRmk", etValArr[i]);
                    jsonData.put("UserTypeName", JSONObject.NULL);
                    jsonData.put("CreatedOn", JSONObject.NULL);
                    jsonData.put("Username", JSONObject.NULL);
                    jsonData.put("objLabourRulesSchemaList", JSONObject.NULL);
                    arrSelectedRules.put(jsonData);

                    dataToDatabase.put("objLabourRulesSchema", arrSelectedRules);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

        arrRules = new JSONArray();
        rules = new JSONObject();
        for (int i = 0; i < len; i++) {
            try {
                arrRules = rules
                        .getJSONArray("LabourRulesSchema");
            } catch (JSONException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
            try {
                JSONObject jsonData = new JSONObject();
                jsonData.put("RuleId", m_list_rules.get(i).getRuleId());
                jsonData.put("RuleName", m_list_rules.get(i).getRuleName());
                jsonData.put("Actid", act_Id);
                jsonData.put("ActName", JSONObject.NULL);
                jsonData.put("IsSelected", isSelect[i]);
                jsonData.put("ComplaintRmk", etValArr[i]);
                jsonData.put("UserTypeName", JSONObject.NULL);
                jsonData.put("CreatedOn", JSONObject.NULL);
                jsonData.put("Username", JSONObject.NULL);
                jsonData.put("objLabourRulesSchemaList", JSONObject.NULL);
                arrRules.put(jsonData);

                rules.put("LabourRulesSchema", arrRules);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        JSONObject actSchema = new JSONObject();
        try {
            actSchema.put("Actid", act_Id);
            actSchema.put("ActName", act_name);
            actSchema.put("ISSelected", true);
            actSchema.put("objLabourRulesSchema", arrRules);

            arrSelect = new JSONArray();
            try {
                arrSelect = selectedActs.getJSONArray("SelectedActs");

            } catch (JSONException e) {
                e.printStackTrace();
            }

            arrSelect.put(actSchema);
            selectedActs.put("SelectedActs", arrSelect);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        if (act_Id.equalsIgnoreCase("101")) {
            JSONObject json = new JSONObject();
            try {
                json.put("CategoryID", "101");
                json.put("Category", JSONObject.NULL);
                json.put("Basic", edit_skilled_basic.getText().toString());
                json.put("SpecialAllowance", edit_skilled_special.getText().toString());
                json.put("Total", edit_skilled_total.getText().toString());
                jarr.put(json);

                json = new JSONObject();
                json.put("CategoryID", "102");
                json.put("Category", JSONObject.NULL);
                json.put("Basic", edit_semi_skilled_basic.getText().toString());
                json.put("SpecialAllowance", edit_semi_skilled_special.getText().toString());
                json.put("Total", edit_semi_total.getText().toString());
                jarr.put(json);

                json = new JSONObject();
                json.put("CategoryID", "103");
                json.put("Category", JSONObject.NULL);
                json.put("Basic", edit_unskilled_basic.getText().toString());
                json.put("SpecialAllowance", edit_unskilled_special.getText().toString());
                json.put("Total", edit_unskilled_total.getText().toString());
                jarr.put(json);

                json = new JSONObject();
                json.put("CategoryID", "104");
                json.put("Category", JSONObject.NULL);
                json.put("Basic", edit_highly_skilled_basic.getText().toString());
                json.put("SpecialAllowance", edit_highly_skilled_special.getText().toString());
                json.put("Total", edit_highly_skilled_total.getText().toString());
                jarr.put(json);

                dataToDatabase.put("objInspectionEmpMinWages", jarr);

                tv_min_wages.setVisibility(View.GONE);
                Utilities.invisibleAll(ll_skilled, ll_semiskilled, ll_unskilled, ll_highly_skilled);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        clear();

        Utilities.showMessage("Data Saved", context);
        List<SpinnerObject> m_list_temp = new ArrayList<>();
        contentAdapter = new ContentAdapter(m_list_temp);
        recyclerView.setAdapter(contentAdapter);
    }

    private void clear() {
        edit_skilled_basic.setText("");
        edit_skilled_special.setText("");
        edit_skilled_total.setText("");
        edit_semi_skilled_basic.setText("");
        edit_semi_skilled_special.setText("");
        edit_semi_total.setText("");
        edit_unskilled_basic.setText("");
        edit_unskilled_special.setText("");
        edit_unskilled_total.setText("");
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        final CheckBox rb_rules;
        final EditText edit_remarks;
        final CustomEtListener customEtListener;

        public ViewHolder(View view, CustomEtListener listener) {
            super(view);
            rb_rules = (CheckBox) view.findViewById(R.id.rb_rules);
            edit_remarks = (EditText) view.findViewById(R.id.edit_remarks);
            this.customEtListener = listener;

            edit_remarks.addTextChangedListener(customEtListener);
//            getAdapterPosition();
        }
    }

    public class CustomEtListener implements TextWatcher {
        private int position;

        /**
         * Updates the position according to onBindViewHolder
         *
         * @param position - position of the focused item
         */
        public void updatePosition(int position) {
            this.position = position;
        }

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            // Change the value of array according to the position
            etValArr[position] = charSequence.toString();
        }

        @Override
        public void afterTextChanged(Editable editable) {
        }
    }

    public class ContentAdapter extends RecyclerView.Adapter<ViewHolder> {

//        JSONObject jsonResult;
//        JSONArray arrRule = new JSONArray();
        List<SpinnerObject> lst_rules = new ArrayList<>();
        int size;

        public ContentAdapter(List<SpinnerObject> result) {
            lst_rules = result;
            size = lst_rules.size();
            etValArr = new String[size];
            isSelect = new boolean[size];

            for (int i = 0; i < etValArr.length; i++) {
                etValArr[i] = "";
            }
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_rules, parent, false);

            return new ViewHolder(view, new CustomEtListener());
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, final int position) {
//            JSONObject jsonData;
//                jsonData = arrRule.getJSONObject(position);
            String actName = lst_rules.get(position).getRuleName();
            holder.rb_rules.setText(actName);

            final SpinnerObject obj = lst_rules.get(position);
            holder.customEtListener.updatePosition(position);

            holder.rb_rules.setOnCheckedChangeListener(null);
            holder.edit_remarks.setText(etValArr[position]);
            holder.rb_rules.setChecked(obj.isSelected());

            holder.rb_rules.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    //set your object's last status
                    obj.setSelected(isChecked);
                    if (isChecked) {
                        holder.edit_remarks.setVisibility(View.VISIBLE);
                        isSelect[position] = true;
                    } else {
                        holder.edit_remarks.setText("");
                        holder.edit_remarks.setVisibility(View.GONE);
                        isSelect[position] = false;
                    }
                }
            });
        }

        @Override
        public int getItemCount() {
            return size;
        }
    }
}
