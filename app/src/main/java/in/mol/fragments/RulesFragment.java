package in.mol.fragments;

import android.app.Activity;
import android.content.Context;
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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import org.xmlpull.v1.XmlSerializer;

import java.io.IOException;
import java.io.StringWriter;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import in.mol.database.DatabaseHelper;
import in.mol.labourinspection.MainActivity;
import in.mol.labourinspection.R;
import in.mol.models.SpinnerObject;
import in.mol.Util.UserSessionManager;
import in.mol.Util.Utilities;
import in.mol.services.WebService;

public class RulesFragment extends Fragment implements View.OnClickListener {

    static Context context;
    ViewPager vPager;
    private static final String ARG_PARAM1 = "acts";
    private static final String ARG_PARAM2 = "act_ID";
    private static final String ARG_PARAM3 = "act_NAME";
    private static final String ARG_PARAM4 = "User_NAME";
    private static final String ARG_PARAM5 = "User_ID";
    private Button submit;
    private Spinner spn_act;
    private String jsonRule;
    private JSONObject jsonActs;
    private JSONArray arrActs;
    ContentAdapter contentAdapter;
    private DatabaseHelper dbHelper;
    String[] etValArr;
    RecyclerView recyclerView;
    View view;
    List<SpinnerObject> m_list;
    String actId = "0", actNAME;
    private String actList, user_name, user_id;
    private static ViewPager viewPager;
    private static UserSessionManager session;
    String licence_no, inspection_no;
    JSONObject dataToDatabase;
    private static MainActivity mainActivity;
    private static XmlSerializer xmlSerializer;
    LinearLayout ll_skilled, ll_semiskilled, ll_unskilled;
    EditText edit_skilled_basic, edit_skilled_special, edit_skilled_total,
            edit_semi_skilled_basic, edit_semi_skilled_special, edit_semi_total,
            edit_unskilled_basic, edit_unskilled_special, edit_unskilled_total, edit_remarks;
    TextView tv_min_wages;
    boolean[] isSelect;
    static StringWriter writer;

    public RulesFragment() {
        // Required empty public constructor
    }

    public static RulesFragment newInstance(MainActivity mainAct, Context con, ViewPager viewPagr,
                                            String json, String actID, String actName, String userName, String userID) {
        RulesFragment fragment = new RulesFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, json);
        args.putString(ARG_PARAM2, actID);
        args.putString(ARG_PARAM3, actName);
        args.putString(ARG_PARAM4, userName);
        args.putString(ARG_PARAM5, userID);
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
            user_id = getArguments().getString(ARG_PARAM5);
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

        edit_remarks = (EditText) view.findViewById(R.id.edit_remarks);

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
                saveDataJson();
//                saveDataXml();
                break;

            default:
                break;
        }
    }

    public void sendData(XmlSerializer serializer) {
//        xmlSerializer = Xml.newSerializer();
        writer = new StringWriter();
        try {
//            xmlSerializer.setOutput(writer);
            xmlSerializer = serializer;

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void saveDataXml() {
        try {
            if (xmlSerializer != null) {

                    try {
                        xmlSerializer.startTag("", "objLabourActSchema");

                        xmlSerializer.startTag("", "Actid");
                        xmlSerializer.text("0");
                        xmlSerializer.endTag("", "Actid");
                        xmlSerializer.startTag("", "ISSelected");
                        xmlSerializer.text("" + true);
                        xmlSerializer.endTag("", "ISSelected");

                        xmlSerializer.startTag("", "SelectedActs");

                        xmlSerializer.startTag("", "LabourActSchema");

                        xmlSerializer.startTag("", "Actid");
                        xmlSerializer.text(actId);
                        xmlSerializer.endTag("", "Actid");
                        xmlSerializer.startTag("", "ActName");
                        xmlSerializer.text(actNAME);
                        xmlSerializer.endTag("", "ActName");
                        xmlSerializer.startTag("", "ISSelected");
                        xmlSerializer.text("" + true);
                        xmlSerializer.endTag("", "ISSelected");

                        xmlSerializer.startTag("", "objLabourRulesSchema");
                        int len = isSelect.length;
                        for (int i = 0; i < len; i++) {
                            xmlSerializer.startTag("", "LabourRulesSchema");

                            xmlSerializer.startTag("", "RuleId");
                            xmlSerializer.text(m_list.get(i).getRuleId());
                            xmlSerializer.endTag("", "RuleId");

                            xmlSerializer.startTag("", "RuleName");
                            xmlSerializer.text(m_list.get(i).getRuleName());
                            xmlSerializer.endTag("", "RuleName");

                            xmlSerializer.startTag("", "Actid");
                            xmlSerializer.text(actId);
                            xmlSerializer.endTag("", "Actid");

                            xmlSerializer.startTag("", "IsSelected");
                            xmlSerializer.text("" + isSelect[i]);
                            xmlSerializer.endTag("", "IsSelected");

                            xmlSerializer.startTag("", "ComplaintRmk");
                            xmlSerializer.text(etValArr[i]);
                            xmlSerializer.endTag("", "ComplaintRmk");

                            xmlSerializer.endTag("", "LabourRulesSchema");
                        }
                        xmlSerializer.endTag("", "objLabourRulesSchema");
                        xmlSerializer.endTag("", "LabourActSchema");
                        xmlSerializer.endTag("", "SelectedActs");

                        xmlSerializer.endTag("", "objLabourActSchema");

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
            }
            if (actId.equalsIgnoreCase("101")) {
                xmlSerializer.startTag("", "objInspectionEmpMinWages");

                xmlSerializer.startTag("", "InspectionEmpMinWages");
                xmlSerializer.startTag("", "CategoryID");
                xmlSerializer.text("101");
                xmlSerializer.endTag("", "CategoryID");

                xmlSerializer.startTag("", "Basic");
                xmlSerializer.text(edit_skilled_basic.getText().toString());
                xmlSerializer.endTag("", "Basic");

                xmlSerializer.startTag("", "SpecialAllowance");
                xmlSerializer.text(edit_skilled_special.getText().toString());
                xmlSerializer.endTag("", "SpecialAllowance");

                xmlSerializer.startTag("", "Total");
                xmlSerializer.text(edit_skilled_total.getText().toString());
                xmlSerializer.endTag("", "Total");
                xmlSerializer.endTag("", "InspectionEmpMinWages");

                xmlSerializer.startTag("", "InspectionEmpMinWages");
                xmlSerializer.startTag("", "CategoryID");
                xmlSerializer.text("102");
                xmlSerializer.endTag("", "CategoryID");

                xmlSerializer.startTag("", "Basic");
                xmlSerializer.text(edit_semi_skilled_basic.getText().toString());
                xmlSerializer.endTag("", "Basic");

                xmlSerializer.startTag("", "SpecialAllowance");
                xmlSerializer.text(edit_semi_skilled_special.getText().toString());
                xmlSerializer.endTag("", "SpecialAllowance");

                xmlSerializer.startTag("", "Total");
                xmlSerializer.text(edit_semi_total.getText().toString());
                xmlSerializer.endTag("", "Total");
                xmlSerializer.endTag("", "InspectionEmpMinWages");

                xmlSerializer.startTag("", "InspectionEmpMinWages");
                xmlSerializer.startTag("", "CategoryID");
                xmlSerializer.text("103");
                xmlSerializer.endTag("", "CategoryID");

                xmlSerializer.startTag("", "Basic");
                xmlSerializer.text(edit_unskilled_basic.getText().toString());
                xmlSerializer.endTag("", "Basic");

                xmlSerializer.startTag("", "SpecialAllowance");
                xmlSerializer.text(edit_unskilled_special.getText().toString());
                xmlSerializer.endTag("", "SpecialAllowance");

                xmlSerializer.startTag("", "Total");
                xmlSerializer.text(edit_unskilled_total.getText().toString());
                xmlSerializer.endTag("", "Total");
                xmlSerializer.endTag("", "InspectionEmpMinWages");

                xmlSerializer.endTag("", "objInspectionEmpMinWages");
            }
            xmlSerializer.endDocument();

//            String xmlOutput = xmlSerializer.toString();

            String xmlOutput = "<?xml version='1.0' encoding='UTF-8' standalone='yes' ?>\n" +
                    "<InspectionActRemarks>\n" +
                    "    <PresentEmpName>did</PresentEmpName>\n" +
                    "    <PresentEmpDesg>iitdit</PresentEmpDesg>\n" +
                    "    <DateOfInspection>9/6/2016</DateOfInspection>\n" +
                    "    <Remark>Test Data</Remark>\n" +
                    "    <CreatedBy>411f82df-1702-4e37-9016-6db1c4909ffe</CreatedBy>\n" +
                    "    <objLabourInspectionSchema>\n" +
                    "        <LicenseNo>1631000310277242</LicenseNo>\n" +
                    "        <Institution_Name>CLEAR CARGO LOGISTIK PVT LTD</Institution_Name>\n" +
                    "        <Institution_Addr>B - 307 , HARBOUR COURT, PLOT NO 25 A B C D, SECTOR NO 02, REVENUE VILLAGE DRONAGIRI, NAVI MUMBAI (M CORP.) , THANE, THANE, 400707</Institution_Addr>\n" +
                    "        <Owner_Name>NISHIGANDH REDKAR</Owner_Name>\n" +
                    "        <Owner_Addr>B - 307 , HARBOUR COURT, PLOT NO 25 A B C D, SECTOR NO 02, REVENUE VILLAGE DRONAGIRI, NAVI MUMBAI (M CORP.) , THANE, THANE, 400707</Owner_Addr>\n" +
                    "        <TotalWorkers>1</TotalWorkers>\n" +
                    "        <Male>05</Male>\n" +
                    "        <Female>05</Female>\n" +
                    "        <TotalDirectEmp>1</TotalDirectEmp>\n" +
                    "        <TotalContractEmp>1</TotalContractEmp>\n" +
                    "        <Transgender>0</Transgender>\n" +
                    "        <StatusId>0</StatusId>\n" +
                    "        <RegistrationUnder>sut</RegistrationUnder>\n" +
                    "        <ScheduleEmp>jgusu</ScheduleEmp>\n" +
                    "        <WorkingHr>jgjg</WorkingHr>\n" +
                    "        <WeeklyOff>gsfu</WeeklyOff>\n" +
                    "        <OfficeID>0</OfficeID>\n" +
                    "        <ServiceID>4</ServiceID>\n" +
                    "    </objLabourInspectionSchema>\n" +
                    "    <objInspectedEmpDetails>\n" +
                    "        <InspectedEmpDetails>\n" +
                    "            <Name>kdjgd</Name>\n" +
                    "            <Designation></Designation>\n" +
                    "            <LengthOfService></LengthOfService>\n" +
                    "            <WorkingHr></WorkingHr>\n" +
                    "            <RestHr></RestHr>\n" +
                    "            <AttendCard></AttendCard>\n" +
                    "            <OverTimeRate></OverTimeRate>\n" +
                    "            <SalayPerDay></SalayPerDay>\n" +
                    "            <DateOfPayment></DateOfPayment>\n" +
                    "            <Bonus></Bonus>\n" +
                    "        </InspectedEmpDetails>\n" +
                    "    </objInspectedEmpDetails>\n" +
                    "    <objLabourActSchema>\n" +
                    "        <Actid>0</Actid>\n" +
                    "        <ISSelected>true</ISSelected>\n" +
                    "        <SelectedActs>\n" +
                    "            <LabourActSchema>\n" +
                    "                <Actid>101</Actid>\n" +
                    "                <ActName>The Minimum Wages Act, 1948</ActName>\n" +
                    "                <ISSelected>true</ISSelected>\n" +
                    "                <objLabourRulesSchema>\n" +
                    "                    <LabourRulesSchema>\n" +
                    "                        <RuleId>1001</RuleId>\n" +
                    "                        <RuleName>Muster roll cum wage register in form II is not mentioned.This is breech of Sec.18(1) R/W Rule 27(1)</RuleName>\n" +
                    "                        <Actid>101</Actid>\n" +
                    "                        <IsSelected>true</IsSelected>\n" +
                    "                        <ComplaintRmk>hfjgjf</ComplaintRmk>\n" +
                    "                    </LabourRulesSchema>\n" +
                    "                    <LabourRulesSchema>\n" +
                    "                        <RuleId>1002</RuleId>\n" +
                    "                        <RuleName>Attendence card cum wage slips are not provided to worker.This is breech of Sec.18(3) R/W Rule 27(2)</RuleName>\n" +
                    "                        <Actid>101</Actid>\n" +
                    "                        <IsSelected>true</IsSelected>\n" +
                    "                        <ComplaintRmk></ComplaintRmk>\n" +
                    "                    </LabourRulesSchema>\n" +
                    "                    <LabourRulesSchema>\n" +
                    "                        <RuleId>1003</RuleId>\n" +
                    "                        <RuleName>Inspection book is not kept. This is breech of Sec.18 R/W Rule 28</RuleName>\n" +
                    "                        <Actid>101</Actid>\n" +
                    "                        <IsSelected>false</IsSelected>\n" +
                    "                        <ComplaintRmk></ComplaintRmk>\n" +
                    "                    </LabourRulesSchema>\n" +
                    "                    <LabourRulesSchema>\n" +
                    "                        <RuleId>1004</RuleId>\n" +
                    "                        <RuleName>The exact wages for overtime work at the double of normal rate of wages are not being paid to the workers.This is breech of Sec.14(1) R/W Rule 26(1) </RuleName>\n" +
                    "                        <Actid>101</Actid>\n" +
                    "                        <IsSelected>false</IsSelected>\n" +
                    "                        <ComplaintRmk></ComplaintRmk>\n" +
                    "                    </LabourRulesSchema>\n" +
                    "                    <LabourRulesSchema>\n" +
                    "                        <RuleId>1005</RuleId>\n" +
                    "                        <RuleName>Some/ all workers are being paid wages less than the prescribed Minimum wages.This is breech of Sec.12(1)</RuleName>\n" +
                    "                        <Actid>101</Actid>\n" +
                    "                        <IsSelected>false</IsSelected>\n" +
                    "                        <ComplaintRmk></ComplaintRmk>\n" +
                    "                    </LabourRulesSchema>\n" +
                    "                    <LabourRulesSchema>\n" +
                    "                        <RuleId>1006</RuleId>\n" +
                    "                        <RuleName>The worker are being paid wages after the seventh of every month. This is breech of Rules21(1)(a)</RuleName>\n" +
                    "                        <Actid>101</Actid>\n" +
                    "                        <IsSelected>false</IsSelected>\n" +
                    "                        <ComplaintRmk></ComplaintRmk>\n" +
                    "                    </LabourRulesSchema>\n" +
                    "                    <LabourRulesSchema>\n" +
                    "                        <RuleId>1007</RuleId>\n" +
                    "                        <RuleName>The employer has omitted the name of some employees from muster roll cum wage register. This is breech of Rule 31 (A)(III)</RuleName>\n" +
                    "                        <Actid>101</Actid>\n" +
                    "                        <IsSelected>false</IsSelected>\n" +
                    "                        <ComplaintRmk></ComplaintRmk>\n" +
                    "                    </LabourRulesSchema>\n" +
                    "                    <LabourRulesSchema>\n" +
                    "                        <RuleId>1008</RuleId>\n" +
                    "                        <RuleName>The record for last three year has not preserved.This is breech Rule 30</RuleName>\n" +
                    "                        <Actid>101</Actid>\n" +
                    "                        <IsSelected>false</IsSelected>\n" +
                    "                        <ComplaintRmk></ComplaintRmk>\n" +
                    "                    </LabourRulesSchema>\n" +
                    "                    <LabourRulesSchema>\n" +
                    "                        <RuleId>1009</RuleId>\n" +
                    "                        <RuleName>The employer has making false entry in the records. This is breech of Rule 31 (A)(III)</RuleName>\n" +
                    "                        <Actid>101</Actid>\n" +
                    "                        <IsSelected>false</IsSelected>\n" +
                    "                        <ComplaintRmk></ComplaintRmk>\n" +
                    "                    </LabourRulesSchema>\n" +
                    "                    <LabourRulesSchema>\n" +
                    "                        <RuleId>1010</RuleId>\n" +
                    "                        <RuleName>Weekly off is not provided to the worker.This is breech of Rule 23</RuleName>\n" +
                    "                        <Actid>101</Actid>\n" +
                    "                        <IsSelected>true</IsSelected>\n" +
                    "                        <ComplaintRmk></ComplaintRmk>\n" +
                    "                    </LabourRulesSchema>\n" +
                    "                    <LabourRulesSchema>\n" +
                    "                        <RuleId>1011</RuleId>\n" +
                    "                        <RuleName>The employer has released wages in cash mode.This is breech of Sec 11(1) </RuleName>\n" +
                    "                        <Actid>101</Actid>\n" +
                    "                        <IsSelected>true</IsSelected>\n" +
                    "                        <ComplaintRmk></ComplaintRmk>\n" +
                    "                    </LabourRulesSchema>\n" +
                    "                </objLabourRulesSchema>\n" +
                    "            </LabourActSchema>\n" +
                    "        </SelectedActs>\n" +
                    "    </objLabourActSchema>\n" +
                    "    <objInspectionEmpMinWages>\n" +
                    "        <InspectionEmpMinWages>\n" +
                    "            <CategoryID>101</CategoryID>\n" +
                    "            <Basic>25456</Basic>\n" +
                    "            <SpecialAllowance>84</SpecialAllowance>\n" +
                    "            <Total>25540.00</Total>\n" +
                    "        </InspectionEmpMinWages>\n" +
                    "        <InspectionEmpMinWages>\n" +
                    "            <CategoryID>102</CategoryID>\n" +
                    "            <Basic>95</Basic>\n" +
                    "            <SpecialAllowance>54</SpecialAllowance>\n" +
                    "            <Total>149.00</Total>\n" +
                    "        </InspectionEmpMinWages>\n" +
                    "        <InspectionEmpMinWages>\n" +
                    "            <CategoryID>103</CategoryID>\n" +
                    "            <Basic>84</Basic>\n" +
                    "            <SpecialAllowance>98</SpecialAllowance>\n" +
                    "            <Total>182.00</Total>\n" +
                    "        </InspectionEmpMinWages>\n" +
                    "    </objInspectionEmpMinWages>\n" +
                    "</InspectionActRemarks>";

            long result = -1;
            result = dbHelper.insertBasicDetails(user_name, licence_no, inspection_no, xmlOutput);

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

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void saveDataJson() {
        JSONObject rulesSchema = new JSONObject();
        JSONObject dataToUpload = new JSONObject();
        JSONObject rules = new JSONObject();

        String basicInfo = session.getBasicDetailsInfo();
        JSONObject empData;
        try {
//            dataToDatabase.put("objLabourInspectionSchema", basicInfo);
            dataToDatabase = new JSONObject(basicInfo);
            empData = dataToDatabase.optJSONObject("objLabourInspectionSchema");

            dataToDatabase.put("PresentEmpName", empData.getString("PresentEmpName"));
            dataToDatabase.put("PresentEmpDesg", empData.getString("PresentEmpDesg"));
            dataToDatabase.put("DateOfInspection", empData.getString("DateOfInspection"));
            dataToDatabase.put("Remark", edit_remarks.getText().toString());
            dataToDatabase.put("CreatedBy", user_id);
        } catch (Exception e) {
            e.printStackTrace();
        }

        JSONArray arrRules = new JSONArray();
        int len = m_list.size();
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
                jsonData.put("RuleId", m_list.get(i).getRuleId());
                jsonData.put("RuleName", m_list.get(i).getRuleName());
                jsonData.put("Actid", actId);
                jsonData.put("IsSelected", isSelect[i]);
                jsonData.put("ComplaintRmk", etValArr[i]);
                arrRules.put(jsonData);

                rules.put("LabourRulesSchema", arrRules);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        JSONObject actSchema = new JSONObject();
        JSONObject selectedActs = new JSONObject();
        try {
            actSchema.put("Actid", actId);
            actSchema.put("ActName", actNAME);
            actSchema.put("ISSelected", true);
            actSchema.put("objLabourRulesSchema", arrRules);

//            rulesSchema.put("LabourActSchema", actSchema);

            JSONArray selActs = new JSONArray();
            selActs.put(actSchema);

            selectedActs.put("SelectedActs", selActs);
            selectedActs.put("ISSelected", false);
            selectedActs.put("Actid", actId);
            selectedActs.put("ActName", actNAME);

            dataToDatabase.put("objLabourActSchema", selectedActs);

        } catch (Exception e) {
            e.printStackTrace();
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

//                minWages.put("InspectionEmpMinWages", jarr);

                dataToDatabase.put("objInspectionEmpMinWages", jarr);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        try {
//            dataToUpload.put("InspectionActRemarks", dataToDatabase);

        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println(dataToDatabase.toString());
        try {
            Thread.sleep(1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        long result = -1;
        result = dbHelper.insertBasicDetails(user_name, licence_no, inspection_no, dataToDatabase.toString());

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

            WebService.uploadData(json.toString());

            return null;
        }
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

        JSONObject jsonResult;
        JSONArray arrRule = new JSONArray();
        List<SpinnerObject> lst_rules = new ArrayList<>();
        int size;

        public ContentAdapter(List<SpinnerObject> result) {
            lst_rules = result;
            size = lst_rules.size();
            etValArr = new String[size];
            isSelect = new boolean[size];

            for(int i = 0 ; i < etValArr.length ; i++){
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
                        isSelect[position] = true;
                    } else {
                        isSelect[position] = false;
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