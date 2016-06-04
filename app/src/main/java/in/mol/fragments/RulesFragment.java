package in.mol.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ItemDecoration;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Xml;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xml.sax.SAXException;

import org.xmlpull.v1.XmlSerializer;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import in.mol.database.DatabaseHelper;
import in.mol.labourinspection.ActivityActList;
import in.mol.labourinspection.MainActivity;
import in.mol.labourinspection.R;
import in.mol.models.SpinnerObject;
import in.mol.models.UserSessionManager;
import in.mol.models.Utilities;
import in.mol.services.WebService;

public class RulesFragment extends Fragment implements View.OnClickListener {

    static Context context;
    ViewPager vPager;
    private static final String ARG_PARAM1 = "acts";
    private static final String ARG_PARAM2 = "act_ID";
    private static final String ARG_PARAM3 = "act_NAME";
    private static final String ARG_PARAM4 = "User_NAME";
    private Button submit;
    private Spinner spn_act;
    private String jsonRule;
    private JSONObject jsonActs;
    private JSONArray arrActs;
    ContentAdapter contentAdapter;
    private DatabaseHelper dbHelper;
    String[] strRules, strRuleName;
    int rulesLen;
    RecyclerView recyclerView;
    View view;
    List<SpinnerObject> m_list;
    String actId = "0", actNAME;
    private String actList, user_name;
    private static ViewPager viewPager;
    private static UserSessionManager session;
    String licence_no, inspection_no;
    JSONObject dataToDatabase;
    private static MainActivity mainActivity;
    private XmlSerializer xmlSerializer;
    LinearLayout ll_skilled, ll_semiskilled, ll_unskilled;
    EditText edit_skilled_basic, edit_skilled_special, edit_skilled_total,
            edit_semi_skilled_basic, edit_semi_skilled_special, edit_semi_total,
            edit_unskilled_basic, edit_unskilled_special, edit_unskilled_total;
    TextView tv_min_wages;

    public RulesFragment() {
        // Required empty public constructor
    }

    public static RulesFragment newInstance(MainActivity mainAct, Context con, ViewPager viewPagr,
                                            String json, String actID, String actName, String userName) {
        RulesFragment fragment = new RulesFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, json);
        args.putString(ARG_PARAM2, actID);
        args.putString(ARG_PARAM3, actName);
        args.putString(ARG_PARAM4, userName);

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
            actList = getArguments().getString(ARG_PARAM1);
            actId = getArguments().getString(ARG_PARAM2);
            actNAME = getArguments().getString(ARG_PARAM3);
            user_name = getArguments().getString(ARG_PARAM4);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_rules, container, false);

        init();
        setDefaults();
        setEventListeners();

        return view;
    }

    private void init() {
        dbHelper = new DatabaseHelper(getActivity());
        m_list = new ArrayList<>();
        session = new UserSessionManager(context);
        dataToDatabase = new JSONObject();

        recyclerView = (RecyclerView) view.findViewById(R.id.my_recycler_view);
        submit = (Button) view.findViewById(R.id.btn_submit);
        spn_act = (Spinner) view.findViewById(R.id.spn_act);

        ll_skilled = (LinearLayout) view.findViewById(R.id.ll_skilled);
        ll_semiskilled = (LinearLayout) view.findViewById(R.id.ll_semi_skilled);
        ll_unskilled = (LinearLayout) view.findViewById(R.id.ll_unskilled);

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
        Utilities.invisibleAll(ll_skilled, ll_semiskilled, ll_unskilled);

    }

    private void setDefaults() {
        m_list = dbHelper.getRules(actId);

        contentAdapter = new ContentAdapter(m_list);
        recyclerView.setAdapter(contentAdapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        RecyclerView.ItemDecoration itemDecoration = new ItemDecoration() {
            @Override
            public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
                super.onDrawOver(c, parent, state);
            }
        };
        recyclerView.addItemDecoration(itemDecoration);
        licence_no = session.getLicenseNo();
        inspection_no = session.getInspectionNo();

        if (actId.equalsIgnoreCase("101")) {
            tv_min_wages.setVisibility(View.VISIBLE);
            Utilities.visibleAll(ll_skilled, ll_semiskilled, ll_unskilled);
        }
//        try {
//            SpinnerObject spinnerObj;
//            arrActs = new JSONArray(actList);
//            m_list.add(new SpinnerObject("0", "Select"));
//            for (int i = 0; i < arrActs.length(); i++) {
//                JSONObject jsson = arrActs.getJSONObject(i);
//                spinnerObj = new SpinnerObject(jsson.getString("Actid"),
//                        jsson.getString("ActName"));
//                m_list.add(spinnerObj);
//            }
//            ArrayAdapter<SpinnerObject> adapter1 = new ArrayAdapter<SpinnerObject>(getActivity(),
//                    android.R.layout.simple_dropdown_item_1line,
//                    m_list);
//
//            spn_act.setAdapter(adapter1);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
    }

    private void setEventListeners() {
        submit.setOnClickListener(this);
//        spn_act.setOnItemSelectedListener(this);

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


    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btn_submit:
                saveData();
//                saveDataXml();
                break;

            default:
                break;
        }
    }

    private void saveDataXml() {
        try {

            if (xmlSerializer != null) {
                if (strRules.length > 0) {

                    try {
                        xmlSerializer.startTag("", "objLabourRulesSchema");
                        for (int i = 0; i < strRules.length; i++) {
                            xmlSerializer.startTag("", "LabourRulesSchema");

                            xmlSerializer.startTag("", "RuleId");
                            xmlSerializer.text(strRules[i]);
                            xmlSerializer.endTag("", "RuleId");

                            xmlSerializer.startTag("", "RuleName");
                            xmlSerializer.text(strRuleName[i]);
                            xmlSerializer.endTag("", "RuleName");

                            xmlSerializer.startTag("", "Actid");
                            xmlSerializer.text(actId);
                            xmlSerializer.endTag("", "Actid");

                            xmlSerializer.startTag("", "IsSelected");
                            xmlSerializer.text("true");
                            xmlSerializer.endTag("", "IsSelected");

                            xmlSerializer.endTag("", "LabourRulesSchema");
                        }
                        xmlSerializer.endTag("", "objLabourRulesSchema");


                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            if (actId.equalsIgnoreCase("101")) {
                xmlSerializer.startTag("", "objInspectionEmpMinWages");

                xmlSerializer.startTag("", "InspectionEmpMinWages");
                xmlSerializer.startTag("", "CategoryID");
                xmlSerializer.text("101");
                xmlSerializer.startTag("", "CategoryID");

                xmlSerializer.startTag("", "Basic");
                xmlSerializer.text(edit_skilled_basic.getText().toString());
                xmlSerializer.startTag("", "Basic");

                xmlSerializer.startTag("", "SpecialAllowance");
                xmlSerializer.text(edit_skilled_special.getText().toString());
                xmlSerializer.startTag("", "SpecialAllowance");

                xmlSerializer.startTag("", "Total");
                xmlSerializer.text(edit_skilled_total.getText().toString());
                xmlSerializer.startTag("", "Total");
                xmlSerializer.endTag("", "InspectionEmpMinWages");

                xmlSerializer.startTag("", "InspectionEmpMinWages");
                xmlSerializer.startTag("", "CategoryID");
                xmlSerializer.text("102");
                xmlSerializer.startTag("", "CategoryID");

                xmlSerializer.startTag("", "Basic");
                xmlSerializer.text(edit_semi_skilled_basic.getText().toString());
                xmlSerializer.startTag("", "Basic");

                xmlSerializer.startTag("", "SpecialAllowance");
                xmlSerializer.text(edit_semi_skilled_special.getText().toString());
                xmlSerializer.startTag("", "SpecialAllowance");

                xmlSerializer.startTag("", "Total");
                xmlSerializer.text(edit_semi_total.getText().toString());
                xmlSerializer.startTag("", "Total");
                xmlSerializer.endTag("", "InspectionEmpMinWages");

                xmlSerializer.startTag("", "InspectionEmpMinWages");
                xmlSerializer.startTag("", "CategoryID");
                xmlSerializer.text("103");
                xmlSerializer.startTag("", "CategoryID");

                xmlSerializer.startTag("", "Basic");
                xmlSerializer.text(edit_unskilled_basic.getText().toString());
                xmlSerializer.startTag("", "Basic");

                xmlSerializer.startTag("", "SpecialAllowance");
                xmlSerializer.text(edit_unskilled_special.getText().toString());
                xmlSerializer.startTag("", "SpecialAllowance");

                xmlSerializer.startTag("", "Total");
                xmlSerializer.text(edit_unskilled_total.getText().toString());
                xmlSerializer.startTag("", "Total");
                xmlSerializer.endTag("", "InspectionEmpMinWages");

                xmlSerializer.endTag("", "objInspectionEmpMinWages");
            }
            xmlSerializer.endDocument();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void saveData() {
        JSONObject dataToUpload = new JSONObject();
        String basicInfo = session.getBasicDetailsInfo();
        JSONObject empData;
        try {
//            dataToDatabase.put("objLabourInspectionSchema", basicInfo);
            dataToDatabase = new JSONObject(basicInfo);

            empData = dataToDatabase.optJSONObject("objLabourInspectionSchema");
            dataToDatabase.put("PresentEmpName", empData.getString("PresentEmpName"));
            dataToDatabase.put("PresentEmpDesg", empData.getString("PresentEmpDesg"));
            dataToDatabase.put("DateOfInspection", empData.getString("DateOfInspection"));
            dataToDatabase.put("Remark", "Test Data");

        } catch (JSONException e) {
            e.printStackTrace();
        }

        if (strRules.length > 0) {
            JSONArray arrRules = new JSONArray();
            JSONObject rules = new JSONObject();
            int len = strRules.length;
            for (int i = 0; i < len; i++) {
                try {
                    arrRules = rules
                            .getJSONArray("LabourRulesSchema");
                } catch (JSONException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
                if (strRules[i] != null) {
                    try {
                        JSONObject jsonData = new JSONObject();
                        jsonData.put("RuleId", strRules[i]);
                        jsonData.put("RuleName", strRuleName[i]);
                        jsonData.put("Actid", actId);
                        jsonData.put("IsSelected", true);
                        arrRules.put(jsonData);

                        rules.put("LabourRulesSchema", arrRules.toString());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            try {
                dataToDatabase.put("objLabourRulesSchema", rules);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        JSONArray jarr = new JSONArray();
        if (actId.equalsIgnoreCase("101")) {
            JSONObject json = new JSONObject();
            JSONObject minWages = new JSONObject();
            try {
                json.put("CategoryID", "101");
                json.put("Basic", edit_skilled_basic.getText().toString());
                json.put("SpecialAllowance", edit_skilled_special.getText().toString());
                json.put("Total", edit_skilled_total.getText().toString());
                jarr.put(json);

                json = new JSONObject();
                json.put("CategoryID", "102");
                json.put("Basic", edit_semi_skilled_basic.getText().toString());
                json.put("SpecialAllowance", edit_semi_skilled_special.getText().toString());
                json.put("Total", edit_semi_total.getText().toString());
                jarr.put(json);

                json = new JSONObject();
                json.put("CategoryID", "103");
                json.put("Basic", edit_unskilled_basic.getText().toString());
                json.put("SpecialAllowance", edit_unskilled_special.getText().toString());
                json.put("Total", edit_unskilled_total.getText().toString());
                jarr.put(json);

                minWages.put("InspectionEmpMinWages", jarr.toString());

                dataToDatabase.put("objInspectionEmpMinWages", minWages);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        JSONObject jsonActSchema = new JSONObject();

        try {
            jsonActSchema.put("Actid", actId);
            jsonActSchema.put("ISSelected", true);
            jsonActSchema.put("ISSelected", true);

            dataToDatabase.put("objLabourActSchema", jsonActSchema);
        } catch (JSONException e) {
            e.printStackTrace();
        }


        try {
            dataToUpload.put("InspectionActRemarks", dataToDatabase);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        System.out.println(dataToDatabase.toString());
        try {
            Thread.sleep(1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        long result = -1;
        result = dbHelper.insertBasicDetails(user_name, licence_no, inspection_no, dataToUpload.toString());

        if (result > 0) {
            Utilities.showMessage("Data saved sucessfully", context);

//        SendData send = new SendData(dataToUpload);
//        send.execute("");

//                Intent in = new Intent(context, ActivityActList.class);
//                in.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                in.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                in.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
//                startActivity(in);
            mainActivity.finish();
        } else {
            Utilities.showMessage("Data not saved", context);
        }
    }

    private class SendData extends AsyncTask<String, String, String> {

        JSONObject json;

        SendData(JSONObject jsonObject) {
            json = jsonObject;
        }

        @Override
        protected String doInBackground(String... params) {

            WebService.uploadData(json);

            return null;
        }
    }

    public void sendData(XmlSerializer serializer) {
        xmlSerializer = serializer;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        final CheckBox rb_rules;

        public ViewHolder(View view, ViewGroup parent) {
            super(view);
            rb_rules = (CheckBox) view.findViewById(R.id.rb_rules);
            getAdapterPosition();
        }
    }

    public class ContentAdapter extends RecyclerView.Adapter<ViewHolder> {

        JSONObject jsonResult;
        JSONArray arrRule = new JSONArray();
        List<SpinnerObject> lst_rules = new ArrayList<>();
        int size;

        public ContentAdapter(List<SpinnerObject> result) {
            lst_rules = result;
            size = lst_rules.size();
            strRules = new String[size];
            strRuleName = new String[size];
//            try {
//                jsonResult = new JSONObject(result);
//                arrRule = jsonResult.getJSONArray("Rules");
//                Log.i("Array Length", "" + arrRule.length());
//                strRules = new String[arrRule.length()];
//                rulesLen = arrRule.length();
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_rules, parent, false);

            return new ViewHolder(view, parent);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, final int position) {
//            JSONObject jsonData;
//                jsonData = arrRule.getJSONObject(position);
            String actName = lst_rules.get(position).getActName();
            holder.rb_rules.setText(actName);

            holder.rb_rules.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (holder.rb_rules.isChecked()) {
                        strRules[position] = lst_rules.get(position).getActId();
                        strRuleName[position] = lst_rules.get(position).getActName();
                    } else {
                        strRules[position] = null;
                        strRuleName[position] = null;
                    }
                }
            });
//            holder.itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    vPager.setCurrentItem(0);
//                }
//            });
        }

        @Override
        public int getItemCount() {
            return size;
        }
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }


}