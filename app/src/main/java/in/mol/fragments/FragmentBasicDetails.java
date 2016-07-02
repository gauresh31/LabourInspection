package in.mol.fragments;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Xml;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;

import com.morpho.capture.AuthBfdCap;
import com.morpho.capture.MorphoTabletFPSensorDevice;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xmlpull.v1.XmlSerializer;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import in.mol.Util.CustomTimeDialog;
import in.mol.database.DatabaseHelper;
import in.mol.labourinspection.MainActivity;
import in.mol.labourinspection.R;
import in.mol.models.SpinnerObject;
import in.mol.Util.UserSessionManager;
import in.mol.Util.Utilities;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FragmentBasicDetails.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FragmentBasicDetails#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentBasicDetails extends Fragment implements View.OnClickListener, AuthBfdCap {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    private static final String ARG_PARAM1 = "User_NAME";
    private static final String ARG_PARAM2 = "User_ID";

    // TODO: Rename and change types of parameters
    private String actList;
    private String mParam2;
    private static Context context;
    View view;
    private int bio_count = 0;
    private int count = 0;
    private int worker_count = 0;
    private String[] strBioTemplate;
    private ScrollView scroll_list;
    private TextView tvHeader;
    private static EditText working_hours;
    private EditText name_of_establishment, address, address_site, name_of_employer, address_employer, email, mobile_no, male, female, transgender, total,
            registration, schedule_empl, no_of_contractors_count, representative_of_principle,
            direct_worker, contract_worker, year_of_starting, accounting_year, no_of_workers, declaration_designation, is_present;
    private EditText edit_name_of_contractor, edit_nature_of_work, edit_no_of_workers, edit_date_of_commencement,
            edit_name_of_contractor2, edit_nature_of_work2, edit_no_of_workers2, edit_date_of_commencement2,
            edit_name_of_contractor3, edit_nature_of_work3, edit_no_of_workers3, edit_date_of_commencement3,
            edit_name_of_contractor4, edit_nature_of_work4, edit_no_of_workers4, edit_date_of_commencement4,
            edit_name_of_contractor5, edit_nature_of_work5, edit_no_of_workers5, edit_date_of_commencement5,
            edit_name_of_contractor6, edit_nature_of_work6, edit_no_of_workers6, edit_date_of_commencement6,
            edit_name_of_contractor7, edit_nature_of_work7, edit_no_of_workers7, edit_date_of_commencement7,
            edit_name_of_contractor8, edit_nature_of_work8, edit_no_of_workers8, edit_date_of_commencement8,
            edit_name_of_contractor9, edit_nature_of_work9, edit_no_of_workers9, edit_date_of_commencement9,
            edit_name_of_contractor10, edit_nature_of_work10, edit_no_of_workers10, edit_date_of_commencement10;

    private EditText edit_name_of_worker1, edit_designation1, edit_lenght_of_service1, edit_working_hours1, edit_rest_time1,
            edit_attend_card1, edit_over_time_rate1, edit_salary_per_day1, edit_date_of_payment1, edit_bonus1,
            edit_name_of_worker2, edit_designation2, edit_lenght_of_service2, edit_working_hours2, edit_rest_time2,
            edit_attend_card2, edit_over_time_rate2, edit_salary_per_day2, edit_date_of_payment2, edit_bonus2,
            edit_name_of_worker3, edit_designation3, edit_lenght_of_service3, edit_working_hours3, edit_rest_time3,
            edit_attend_card3, edit_over_time_rate3, edit_salary_per_day3, edit_date_of_payment3, edit_bonus3,
            edit_name_of_worker4, edit_designation4, edit_lenght_of_service4, edit_working_hours4, edit_rest_time4,
            edit_attend_card4, edit_over_time_rate4, edit_salary_per_day4, edit_date_of_payment4, edit_bonus4,
            edit_name_of_worker5, edit_designation5, edit_lenght_of_service5, edit_working_hours5, edit_rest_time5,
            edit_attend_card5, edit_over_time_rate5, edit_salary_per_day5, edit_date_of_payment5, edit_bonus5,
            edit_name_of_worker6, edit_designation6, edit_lenght_of_service6, edit_working_hours6, edit_rest_time6,
            edit_attend_card6, edit_over_time_rate6, edit_salary_per_day6, edit_date_of_payment6, edit_bonus6,
            edit_name_of_worker7, edit_designation7, edit_lenght_of_service7, edit_working_hours7, edit_rest_time7,
            edit_attend_card7, edit_over_time_rate7, edit_salary_per_day7, edit_date_of_payment7, edit_bonus7,
            edit_name_of_worker8, edit_designation8, edit_lenght_of_service8, edit_working_hours8, edit_rest_time8,
            edit_attend_card8, edit_over_time_rate8, edit_salary_per_day8, edit_date_of_payment8, edit_bonus8,
            edit_name_of_worker9, edit_designation9, edit_lenght_of_service9, edit_working_hours9, edit_rest_time9,
            edit_attend_card9, edit_over_time_rate9, edit_salary_per_day9, edit_date_of_payment9, edit_bonus9,
            edit_name_of_worker10, edit_designation10, edit_lenght_of_service10, edit_working_hours10, edit_rest_time10,
            edit_attend_card10, edit_over_time_rate10, edit_salary_per_day10, edit_date_of_payment10, edit_bonus10,
            edit_name_of_worker11, edit_designation11, edit_lenght_of_service11, edit_working_hours11, edit_rest_time11,
            edit_attend_card11, edit_over_time_rate11, edit_salary_per_day11, edit_date_of_payment11, edit_bonus11,
            edit_name_of_worker12, edit_designation12, edit_lenght_of_service12, edit_working_hours12, edit_rest_time12,
            edit_attend_card12, edit_over_time_rate12, edit_salary_per_day12, edit_date_of_payment12, edit_bonus12,
            edit_name_of_worker13, edit_designation13, edit_lenght_of_service13, edit_working_hours13, edit_rest_time13,
            edit_attend_card13, edit_over_time_rate13, edit_salary_per_day13, edit_date_of_payment13, edit_bonus13,
            edit_name_of_worker14, edit_designation14, edit_lenght_of_service14, edit_working_hours14, edit_rest_time14,
            edit_attend_card14, edit_over_time_rate14, edit_salary_per_day14, edit_date_of_payment14, edit_bonus14,
            edit_name_of_worker15, edit_designation15, edit_lenght_of_service15, edit_working_hours15, edit_rest_time15,
            edit_attend_card15, edit_over_time_rate15, edit_salary_per_day15, edit_date_of_payment15, edit_bonus15,
            edit_name_of_worker16, edit_designation16, edit_lenght_of_service16, edit_working_hours16, edit_rest_time16,
            edit_attend_card16, edit_over_time_rate16, edit_salary_per_day16, edit_date_of_payment16, edit_bonus16,
            edit_name_of_worker17, edit_designation17, edit_lenght_of_service17, edit_working_hours17, edit_rest_time17,
            edit_attend_card17, edit_over_time_rate17, edit_salary_per_day17, edit_date_of_payment17, edit_bonus17;

    private Spinner spin_weeklyoff1, spin_wages1;

    private Spinner spn_weekly_off;
    static EditText date;

    private ImageView biometric_worker_1, biometric_worker_2, biometric_worker_3, biometric_worker_4, biometric_worker_5,
            biometric_worker_6, biometric_worker_7, biometric_worker_8, biometric_worker_9, biometric_worker_10,
            biometric_worker_11, biometric_worker_12, biometric_worker_13, biometric_worker_14, biometric_worker_15, biometric_worker_16, biometric_worker_17;
    private TextView tv_Declaration;
    private RelativeLayout rel_main;
    private LinearLayout ll1, ll2, ll3, ll4, ll5, ll6, ll7, ll8, ll9, ll10;
    private LinearLayout llw1, llw2, llw3, llw4, llw5, llw6, llw7, llw8, llw9, llw10, llw11, llw12, llw13, llw14, llw15, llw16, llw17;
    Button submit;
    Spinner spn_act;
    LinearLayout ll_address_site_1970, ll_employees, ll_employees_1970, ll_working_hours, ll_no_of_contractor,
            ll_representative, ll_year_of_startng, ll_acc_year, ll_no_of_workers, ll_schedule_emp;
    HorizontalScrollView hs_worker_info;
    private static ViewPager viewPager;
    private MorphoTabletFPSensorDevice fpSensorDevice;
    private DatabaseHelper dbHelper;
    private JSONObject dataToDatabase;
    private OnFragmentInteractionListener mListener;
    private static MainActivity mainAct;
    byte[] bioTemplate;
    JSONArray arrActs;
    List<SpinnerObject> m_list;
    private static UserSessionManager session;
    String licence_no, inspection_no, actId, actNAME, user_name, user_id;
    JSONObject json;
    XmlSerializer serializer;
    JSONObject jsonWorker;
    JSONArray jarr;

    public FragmentBasicDetails() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static FragmentBasicDetails newInstance(MainActivity activityMain, Context con, ViewPager vPager, String userName, String userId) {
        FragmentBasicDetails fragment = new FragmentBasicDetails();
        Bundle args = new Bundle();

        args.putString(ARG_PARAM1, userName);
        args.putString(ARG_PARAM2, userId);

        fragment.setArguments(args);

        viewPager = vPager;
        context = con;
        mainAct = activityMain;

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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_basic_details, container, false);
        init();
        initContractorDetails();
        initWorkerDetails();
        setDefault();
        setEventListeners();

        return view;
    }

    private void init() {

        dataToDatabase = new JSONObject();
        strBioTemplate = new String[13];
        dbHelper = new DatabaseHelper(getActivity());
        session = new UserSessionManager(context);
        m_list = new ArrayList<>();
        jsonWorker = new JSONObject();
        jarr = new JSONArray();

        rel_main = (RelativeLayout) view.findViewById(R.id.rel_basic_details);
        scroll_list = (ScrollView) view.findViewById(R.id.scroll_list);
        tv_Declaration = (TextView) view.findViewById(R.id.tv_decalration);
        tvHeader = (TextView) view.findViewById(R.id.tv_head);
        spn_act = (Spinner) view.findViewById(R.id.spn_act);
        submit = (Button) view.findViewById(R.id.btn_submit);
        name_of_establishment = (EditText) view.findViewById(R.id.edit_name_of_establishment);
        address = (EditText) view.findViewById(R.id.edit_address);
        address_site = (EditText) view.findViewById(R.id.edit_address_of_site);
        address_employer = (EditText) view.findViewById(R.id.edit_address_of_employer);
        name_of_employer = (EditText) view.findViewById(R.id.edit_name_of_employer);
        email = (EditText) view.findViewById(R.id.edit_email);
        mobile_no = (EditText) view.findViewById(R.id.edit_mobile);
        male = (EditText) view.findViewById(R.id.edit_male);
        female = (EditText) view.findViewById(R.id.edit_female);
        transgender = (EditText) view.findViewById(R.id.edit_transgender);
        total = (EditText) view.findViewById(R.id.edit_total);
        registration = (EditText) view.findViewById(R.id.edit_registration_under);
        schedule_empl = (EditText) view.findViewById(R.id.edit_schedule_employment);
        working_hours = (EditText) view.findViewById(R.id.edit_working_hours);
        date = (EditText) view.findViewById(R.id.edit_date_of_inspection);
        representative_of_principle = (EditText) view.findViewById(R.id.edit_representative_of_principle);
        direct_worker = (EditText) view.findViewById(R.id.edit_direct_employees);
        contract_worker = (EditText) view.findViewById(R.id.edit_contract_worker);
        year_of_starting = (EditText) view.findViewById(R.id.edit_year_of_starting);
        accounting_year = (EditText) view.findViewById(R.id.edit_accouting_year);
        no_of_workers = (EditText) view.findViewById(R.id.edit_no_of_workers);
        declaration_designation = (EditText) view.findViewById(R.id.edit_declar_designation);
        is_present = (EditText) view.findViewById(R.id.edit_is_present);
        spn_weekly_off = (Spinner) view.findViewById(R.id.spn_weekly_off);

        ll_working_hours = (LinearLayout) view.findViewById(R.id.ll_working_hours);
        ll_employees = (LinearLayout) view.findViewById(R.id.ll_no_of_employees);
        ll_schedule_emp = (LinearLayout) view.findViewById(R.id.ll_schedule_emp);
        hs_worker_info = (HorizontalScrollView) view.findViewById(R.id.hs_worker_info);

        ll_no_of_workers = (LinearLayout) view.findViewById(R.id.ll_no_of_workers);
        ll_year_of_startng = (LinearLayout) view.findViewById(R.id.ll_year_of_starting);
        ll_acc_year = (LinearLayout) view.findViewById(R.id.ll_accounting_year);
        //Visiblity Gone
        ll_address_site_1970 = (LinearLayout) view.findViewById(R.id.ll_address_site);
        ll_employees_1970 = (LinearLayout) view.findViewById(R.id.ll_no_of_employees_1970);
        ll_no_of_contractor = (LinearLayout) view.findViewById(R.id.ll_no_of_contractor);
        ll_representative = (LinearLayout) view.findViewById(R.id.ll_representative);
        //number of contractors
        no_of_contractors_count = (EditText) view.findViewById(R.id.edit_no_of_contractors);

        fpSensorDevice = new MorphoTabletFPSensorDevice(FragmentBasicDetails.this);
        fpSensorDevice.open(mainAct);

    }

    private void initContractorDetails() {
        ll1 = (LinearLayout) view.findViewById(R.id.ll_f1);
        ll2 = (LinearLayout) view.findViewById(R.id.ll_f2);
        ll3 = (LinearLayout) view.findViewById(R.id.ll_f3);
        ll4 = (LinearLayout) view.findViewById(R.id.ll_f4);
        ll5 = (LinearLayout) view.findViewById(R.id.ll_f5);
        ll6 = (LinearLayout) view.findViewById(R.id.ll_f6);
        ll7 = (LinearLayout) view.findViewById(R.id.ll_f7);
        ll8 = (LinearLayout) view.findViewById(R.id.ll_f8);
        ll9 = (LinearLayout) view.findViewById(R.id.ll_f9);
        ll10 = (LinearLayout) view.findViewById(R.id.ll_f10);

        edit_name_of_contractor = (EditText) view.findViewById(R.id.edt_name_of_contractor1);
        edit_name_of_contractor2 = (EditText) view.findViewById(R.id.edt_name_of_contractor2);
        edit_name_of_contractor3 = (EditText) view.findViewById(R.id.edt_name_of_contractor3);
        edit_name_of_contractor4 = (EditText) view.findViewById(R.id.edt_name_of_contractor4);
        edit_name_of_contractor5 = (EditText) view.findViewById(R.id.edt_name_of_contractor5);
        edit_name_of_contractor6 = (EditText) view.findViewById(R.id.edt_name_of_contractor6);
        edit_name_of_contractor7 = (EditText) view.findViewById(R.id.edt_name_of_contractor7);
        edit_name_of_contractor8 = (EditText) view.findViewById(R.id.edt_name_of_contractor8);
        edit_name_of_contractor9 = (EditText) view.findViewById(R.id.edt_name_of_contractor9);
        edit_name_of_contractor10 = (EditText) view.findViewById(R.id.edt_name_of_contractor10);

        edit_nature_of_work = (EditText) view.findViewById(R.id.edt_nature_of_work1);
        edit_nature_of_work2 = (EditText) view.findViewById(R.id.edt_nature_of_work2);
        edit_nature_of_work3 = (EditText) view.findViewById(R.id.edt_nature_of_work3);
        edit_nature_of_work4 = (EditText) view.findViewById(R.id.edt_nature_of_work4);
        edit_nature_of_work5 = (EditText) view.findViewById(R.id.edt_nature_of_work5);
        edit_nature_of_work6 = (EditText) view.findViewById(R.id.edt_nature_of_work6);
        edit_nature_of_work7 = (EditText) view.findViewById(R.id.edt_nature_of_work7);
        edit_nature_of_work8 = (EditText) view.findViewById(R.id.edt_nature_of_work8);
        edit_nature_of_work9 = (EditText) view.findViewById(R.id.edt_nature_of_work9);
        edit_nature_of_work10 = (EditText) view.findViewById(R.id.edt_nature_of_work10);

        edit_no_of_workers = (EditText) view.findViewById(R.id.edt_no_of_worker1);
        edit_no_of_workers2 = (EditText) view.findViewById(R.id.edt_no_of_worker2);
        edit_no_of_workers3 = (EditText) view.findViewById(R.id.edt_no_of_worker3);
        edit_no_of_workers4 = (EditText) view.findViewById(R.id.edt_no_of_worker4);
        edit_no_of_workers5 = (EditText) view.findViewById(R.id.edt_no_of_worker5);
        edit_no_of_workers6 = (EditText) view.findViewById(R.id.edt_no_of_worker6);
        edit_no_of_workers7 = (EditText) view.findViewById(R.id.edt_no_of_worker7);
        edit_no_of_workers8 = (EditText) view.findViewById(R.id.edt_no_of_worker8);
        edit_no_of_workers9 = (EditText) view.findViewById(R.id.edt_no_of_worker9);
        edit_no_of_workers10 = (EditText) view.findViewById(R.id.edt_no_of_worker10);

        edit_date_of_commencement = (EditText) view.findViewById(R.id.edt_date_of_commencement1);
        edit_date_of_commencement2 = (EditText) view.findViewById(R.id.edt_date_of_commencement2);
        edit_date_of_commencement3 = (EditText) view.findViewById(R.id.edt_date_of_commencement3);
        edit_date_of_commencement4 = (EditText) view.findViewById(R.id.edt_date_of_commencement4);
        edit_date_of_commencement5 = (EditText) view.findViewById(R.id.edt_date_of_commencement5);
        edit_date_of_commencement6 = (EditText) view.findViewById(R.id.edt_date_of_commencement6);
        edit_date_of_commencement7 = (EditText) view.findViewById(R.id.edt_date_of_commencement7);
        edit_date_of_commencement8 = (EditText) view.findViewById(R.id.edt_date_of_commencement8);
        edit_date_of_commencement9 = (EditText) view.findViewById(R.id.edt_date_of_commencement9);
        edit_date_of_commencement10 = (EditText) view.findViewById(R.id.edt_date_of_commencement10);
    }

    private void initWorkerDetails() {
        edit_name_of_worker1 = (EditText) view.findViewById(R.id.edit_name_of_worker1);
        edit_designation1 = (EditText) view.findViewById(R.id.edit_designation1);
        edit_lenght_of_service1 = (EditText) view.findViewById(R.id.edit_lenght_of_service1);
        edit_working_hours1 = (EditText) view.findViewById(R.id.edit_working_hours1);
        edit_rest_time1 = (EditText) view.findViewById(R.id.edit_rest_time1);
        edit_attend_card1 = (EditText) view.findViewById(R.id.edit_attend_card1);
        edit_over_time_rate1 = (EditText) view.findViewById(R.id.edit_over_time_rate1);
        edit_salary_per_day1 = (EditText) view.findViewById(R.id.edit_salary_per_day1);
        edit_date_of_payment1 = (EditText) view.findViewById(R.id.edit_date_of_payment1);
        edit_bonus1 = (EditText) view.findViewById(R.id.edit_bonus1);
        spin_wages1 = (Spinner) view.findViewById(R.id.spin_wages1);
        spin_weeklyoff1 = (Spinner) view.findViewById(R.id.spin_weeklyOff1);

        edit_name_of_worker2 = (EditText) view.findViewById(R.id.edit_name_of_worker2);
        edit_designation2 = (EditText) view.findViewById(R.id.edit_designation2);
        edit_lenght_of_service2 = (EditText) view.findViewById(R.id.edit_lenght_of_service2);
        edit_working_hours2 = (EditText) view.findViewById(R.id.edit_working_hours2);
        edit_rest_time2 = (EditText) view.findViewById(R.id.edit_rest_time2);
        edit_attend_card2 = (EditText) view.findViewById(R.id.edit_attend_card2);
        edit_over_time_rate2 = (EditText) view.findViewById(R.id.edit_over_time_rate2);
        edit_salary_per_day2 = (EditText) view.findViewById(R.id.edit_salary_per_day2);
        edit_date_of_payment2 = (EditText) view.findViewById(R.id.edit_date_of_payment2);
        edit_bonus2 = (EditText) view.findViewById(R.id.edit_bonus2);

        edit_name_of_worker3 = (EditText) view.findViewById(R.id.edit_name_of_worker3);
        edit_designation3 = (EditText) view.findViewById(R.id.edit_designation3);
        edit_lenght_of_service3 = (EditText) view.findViewById(R.id.edit_lenght_of_service3);
        edit_working_hours3 = (EditText) view.findViewById(R.id.edit_working_hours3);
        edit_rest_time3 = (EditText) view.findViewById(R.id.edit_rest_time3);
        edit_attend_card3 = (EditText) view.findViewById(R.id.edit_attend_card3);
        edit_over_time_rate3 = (EditText) view.findViewById(R.id.edit_over_time_rate3);
        edit_salary_per_day3 = (EditText) view.findViewById(R.id.edit_salary_per_day3);
        edit_date_of_payment3 = (EditText) view.findViewById(R.id.edit_date_of_payment3);
        edit_bonus3 = (EditText) view.findViewById(R.id.edit_bonus3);

        edit_name_of_worker4 = (EditText) view.findViewById(R.id.edit_name_of_worker4);
        edit_designation4 = (EditText) view.findViewById(R.id.edit_designation4);
        edit_lenght_of_service4 = (EditText) view.findViewById(R.id.edit_lenght_of_service4);
        edit_working_hours4 = (EditText) view.findViewById(R.id.edit_working_hours4);
        edit_rest_time4 = (EditText) view.findViewById(R.id.edit_rest_time4);
        edit_attend_card4 = (EditText) view.findViewById(R.id.edit_attend_card4);
        edit_over_time_rate4 = (EditText) view.findViewById(R.id.edit_over_time_rate4);
        edit_salary_per_day4 = (EditText) view.findViewById(R.id.edit_salary_per_day4);
        edit_date_of_payment4 = (EditText) view.findViewById(R.id.edit_date_of_payment4);
        edit_bonus4 = (EditText) view.findViewById(R.id.edit_bonus4);

        edit_name_of_worker5 = (EditText) view.findViewById(R.id.edit_name_of_worker5);
        edit_designation5 = (EditText) view.findViewById(R.id.edit_designation5);
        edit_lenght_of_service5 = (EditText) view.findViewById(R.id.edit_lenght_of_service5);
        edit_working_hours5 = (EditText) view.findViewById(R.id.edit_working_hours5);
        edit_rest_time5 = (EditText) view.findViewById(R.id.edit_rest_time5);
        edit_attend_card5 = (EditText) view.findViewById(R.id.edit_attend_card5);
        edit_over_time_rate5 = (EditText) view.findViewById(R.id.edit_over_time_rate5);
        edit_salary_per_day5 = (EditText) view.findViewById(R.id.edit_salary_per_day5);
        edit_date_of_payment5 = (EditText) view.findViewById(R.id.edit_date_of_payment5);
        edit_bonus5 = (EditText) view.findViewById(R.id.edit_bonus5);

        edit_name_of_worker6 = (EditText) view.findViewById(R.id.edit_name_of_worker6);
        edit_designation6 = (EditText) view.findViewById(R.id.edit_designation6);
        edit_lenght_of_service6 = (EditText) view.findViewById(R.id.edit_lenght_of_service6);
        edit_working_hours6 = (EditText) view.findViewById(R.id.edit_working_hours6);
        edit_rest_time6 = (EditText) view.findViewById(R.id.edit_rest_time6);
        edit_attend_card6 = (EditText) view.findViewById(R.id.edit_attend_card6);
        edit_over_time_rate6 = (EditText) view.findViewById(R.id.edit_over_time_rate6);
        edit_salary_per_day6 = (EditText) view.findViewById(R.id.edit_salary_per_day6);
        edit_date_of_payment6 = (EditText) view.findViewById(R.id.edit_date_of_payment6);
        edit_bonus6 = (EditText) view.findViewById(R.id.edit_bonus6);

        edit_name_of_worker7 = (EditText) view.findViewById(R.id.edit_name_of_worker7);
        edit_designation7 = (EditText) view.findViewById(R.id.edit_designation7);
        edit_lenght_of_service7 = (EditText) view.findViewById(R.id.edit_lenght_of_service7);
        edit_working_hours7 = (EditText) view.findViewById(R.id.edit_working_hours7);
        edit_rest_time7 = (EditText) view.findViewById(R.id.edit_rest_time7);
        edit_attend_card7 = (EditText) view.findViewById(R.id.edit_attend_card7);
        edit_over_time_rate7 = (EditText) view.findViewById(R.id.edit_over_time_rate7);
        edit_salary_per_day7 = (EditText) view.findViewById(R.id.edit_salary_per_day7);
        edit_date_of_payment7 = (EditText) view.findViewById(R.id.edit_date_of_payment7);
        edit_bonus7 = (EditText) view.findViewById(R.id.edit_bonus7);

        edit_name_of_worker8 = (EditText) view.findViewById(R.id.edit_name_of_worker8);
        edit_designation8 = (EditText) view.findViewById(R.id.edit_designation8);
        edit_lenght_of_service8 = (EditText) view.findViewById(R.id.edit_lenght_of_service8);
        edit_working_hours8 = (EditText) view.findViewById(R.id.edit_working_hours8);
        edit_rest_time8 = (EditText) view.findViewById(R.id.edit_rest_time8);
        edit_attend_card8 = (EditText) view.findViewById(R.id.edit_attend_card8);
        edit_over_time_rate8 = (EditText) view.findViewById(R.id.edit_over_time_rate8);
        edit_salary_per_day8 = (EditText) view.findViewById(R.id.edit_salary_per_day8);
        edit_date_of_payment8 = (EditText) view.findViewById(R.id.edit_date_of_payment8);
        edit_bonus8 = (EditText) view.findViewById(R.id.edit_bonus8);

        edit_name_of_worker9 = (EditText) view.findViewById(R.id.edit_name_of_worker9);
        edit_designation9 = (EditText) view.findViewById(R.id.edit_designation9);
        edit_lenght_of_service9 = (EditText) view.findViewById(R.id.edit_lenght_of_service9);
        edit_working_hours9 = (EditText) view.findViewById(R.id.edit_working_hours9);
        edit_rest_time9 = (EditText) view.findViewById(R.id.edit_rest_time9);
        edit_attend_card9 = (EditText) view.findViewById(R.id.edit_attend_card9);
        edit_over_time_rate9 = (EditText) view.findViewById(R.id.edit_over_time_rate9);
        edit_salary_per_day9 = (EditText) view.findViewById(R.id.edit_salary_per_day9);
        edit_date_of_payment9 = (EditText) view.findViewById(R.id.edit_date_of_payment9);
        edit_bonus9 = (EditText) view.findViewById(R.id.edit_bonus9);

        edit_name_of_worker10 = (EditText) view.findViewById(R.id.edit_name_of_worker10);
        edit_designation10 = (EditText) view.findViewById(R.id.edit_designation10);
        edit_lenght_of_service10 = (EditText) view.findViewById(R.id.edit_lenght_of_service10);
        edit_working_hours10 = (EditText) view.findViewById(R.id.edit_working_hours10);
        edit_rest_time10 = (EditText) view.findViewById(R.id.edit_rest_time10);
        edit_attend_card10 = (EditText) view.findViewById(R.id.edit_attend_card10);
        edit_over_time_rate10 = (EditText) view.findViewById(R.id.edit_over_time_rate10);
        edit_salary_per_day10 = (EditText) view.findViewById(R.id.edit_salary_per_day10);
        edit_date_of_payment10 = (EditText) view.findViewById(R.id.edit_date_of_payment10);
        edit_bonus10 = (EditText) view.findViewById(R.id.edit_bonus10);

        edit_name_of_worker11 = (EditText) view.findViewById(R.id.edit_name_of_worker11);
        edit_designation11 = (EditText) view.findViewById(R.id.edit_designation11);
        edit_lenght_of_service11 = (EditText) view.findViewById(R.id.edit_lenght_of_service11);
        edit_working_hours11 = (EditText) view.findViewById(R.id.edit_working_hours11);
        edit_rest_time11 = (EditText) view.findViewById(R.id.edit_rest_time11);
        edit_attend_card11 = (EditText) view.findViewById(R.id.edit_attend_card11);
        edit_over_time_rate11 = (EditText) view.findViewById(R.id.edit_over_time_rate11);
        edit_salary_per_day11 = (EditText) view.findViewById(R.id.edit_salary_per_day11);
        edit_date_of_payment11 = (EditText) view.findViewById(R.id.edit_date_of_payment11);
        edit_bonus11 = (EditText) view.findViewById(R.id.edit_bonus11);

        edit_name_of_worker12 = (EditText) view.findViewById(R.id.edit_name_of_worker12);
        edit_designation12 = (EditText) view.findViewById(R.id.edit_designation12);
        edit_lenght_of_service12 = (EditText) view.findViewById(R.id.edit_lenght_of_service12);
        edit_working_hours12 = (EditText) view.findViewById(R.id.edit_working_hours12);
        edit_rest_time12 = (EditText) view.findViewById(R.id.edit_rest_time12);
        edit_attend_card12 = (EditText) view.findViewById(R.id.edit_attend_card12);
        edit_over_time_rate12 = (EditText) view.findViewById(R.id.edit_over_time_rate12);
        edit_salary_per_day12 = (EditText) view.findViewById(R.id.edit_salary_per_day12);
        edit_date_of_payment12 = (EditText) view.findViewById(R.id.edit_date_of_payment12);
        edit_bonus12 = (EditText) view.findViewById(R.id.edit_bonus12);

        edit_name_of_worker13 = (EditText) view.findViewById(R.id.edit_name_of_worker13);
        edit_designation13 = (EditText) view.findViewById(R.id.edit_designation13);
        edit_lenght_of_service13 = (EditText) view.findViewById(R.id.edit_lenght_of_service13);
        edit_working_hours13 = (EditText) view.findViewById(R.id.edit_working_hours13);
        edit_rest_time13 = (EditText) view.findViewById(R.id.edit_rest_time13);
        edit_attend_card13 = (EditText) view.findViewById(R.id.edit_attend_card13);
        edit_over_time_rate13 = (EditText) view.findViewById(R.id.edit_over_time_rate13);
        edit_salary_per_day13 = (EditText) view.findViewById(R.id.edit_salary_per_day13);
        edit_date_of_payment13 = (EditText) view.findViewById(R.id.edit_date_of_payment13);
        edit_bonus13 = (EditText) view.findViewById(R.id.edit_bonus13);

        edit_name_of_worker14 = (EditText) view.findViewById(R.id.edit_name_of_worker14);
        edit_designation14 = (EditText) view.findViewById(R.id.edit_designation14);
        edit_lenght_of_service14 = (EditText) view.findViewById(R.id.edit_lenght_of_service14);
        edit_working_hours14 = (EditText) view.findViewById(R.id.edit_working_hours14);
        edit_rest_time14 = (EditText) view.findViewById(R.id.edit_rest_time14);
        edit_attend_card14 = (EditText) view.findViewById(R.id.edit_attend_card14);
        edit_over_time_rate14 = (EditText) view.findViewById(R.id.edit_over_time_rate14);
        edit_salary_per_day14 = (EditText) view.findViewById(R.id.edit_salary_per_day14);
        edit_date_of_payment14 = (EditText) view.findViewById(R.id.edit_date_of_payment14);
        edit_bonus14 = (EditText) view.findViewById(R.id.edit_bonus14);

        edit_name_of_worker15 = (EditText) view.findViewById(R.id.edit_name_of_worker15);
        edit_designation15 = (EditText) view.findViewById(R.id.edit_designation15);
        edit_lenght_of_service15 = (EditText) view.findViewById(R.id.edit_lenght_of_service15);
        edit_working_hours15 = (EditText) view.findViewById(R.id.edit_working_hours15);
        edit_rest_time15 = (EditText) view.findViewById(R.id.edit_rest_time15);
        edit_attend_card15 = (EditText) view.findViewById(R.id.edit_attend_card15);
        edit_over_time_rate15 = (EditText) view.findViewById(R.id.edit_over_time_rate15);
        edit_salary_per_day15 = (EditText) view.findViewById(R.id.edit_salary_per_day15);
        edit_date_of_payment15 = (EditText) view.findViewById(R.id.edit_date_of_payment15);
        edit_bonus15 = (EditText) view.findViewById(R.id.edit_bonus15);

        edit_name_of_worker16 = (EditText) view.findViewById(R.id.edit_name_of_worker16);
        edit_designation16 = (EditText) view.findViewById(R.id.edit_designation16);
        edit_lenght_of_service16 = (EditText) view.findViewById(R.id.edit_lenght_of_service16);
        edit_working_hours16 = (EditText) view.findViewById(R.id.edit_working_hours16);
        edit_rest_time16 = (EditText) view.findViewById(R.id.edit_rest_time16);
        edit_attend_card16 = (EditText) view.findViewById(R.id.edit_attend_card16);
        edit_over_time_rate16 = (EditText) view.findViewById(R.id.edit_over_time_rate16);
        edit_salary_per_day16 = (EditText) view.findViewById(R.id.edit_salary_per_day16);
        edit_date_of_payment16 = (EditText) view.findViewById(R.id.edit_date_of_payment16);
        edit_bonus16 = (EditText) view.findViewById(R.id.edit_bonus16);

        edit_name_of_worker17 = (EditText) view.findViewById(R.id.edit_name_of_worker17);
        edit_designation17 = (EditText) view.findViewById(R.id.edit_designation17);
        edit_lenght_of_service17 = (EditText) view.findViewById(R.id.edit_lenght_of_service17);
        edit_working_hours17 = (EditText) view.findViewById(R.id.edit_working_hours17);
        edit_rest_time17 = (EditText) view.findViewById(R.id.edit_rest_time17);
        edit_attend_card17 = (EditText) view.findViewById(R.id.edit_attend_card17);
        edit_over_time_rate17 = (EditText) view.findViewById(R.id.edit_over_time_rate17);
        edit_salary_per_day17 = (EditText) view.findViewById(R.id.edit_salary_per_day17);
        edit_date_of_payment17 = (EditText) view.findViewById(R.id.edit_date_of_payment17);
        edit_bonus17 = (EditText) view.findViewById(R.id.edit_bonus17);

        biometric_worker_1 = (ImageView) view.findViewById(R.id.iv_biometric1);
        biometric_worker_2 = (ImageView) view.findViewById(R.id.iv_biometric2);
        biometric_worker_3 = (ImageView) view.findViewById(R.id.iv_biometric3);
        biometric_worker_4 = (ImageView) view.findViewById(R.id.iv_biometric4);
        biometric_worker_5 = (ImageView) view.findViewById(R.id.iv_biometric5);
        biometric_worker_6 = (ImageView) view.findViewById(R.id.iv_biometric6);
        biometric_worker_7 = (ImageView) view.findViewById(R.id.iv_biometric7);
        biometric_worker_8 = (ImageView) view.findViewById(R.id.iv_biometric8);
        biometric_worker_9 = (ImageView) view.findViewById(R.id.iv_biometric9);
        biometric_worker_10 = (ImageView) view.findViewById(R.id.iv_biometric10);
        biometric_worker_11 = (ImageView) view.findViewById(R.id.iv_biometric11);
        biometric_worker_12 = (ImageView) view.findViewById(R.id.iv_biometric12);
        biometric_worker_13 = (ImageView) view.findViewById(R.id.iv_biometric13);
        biometric_worker_14 = (ImageView) view.findViewById(R.id.iv_biometric14);
        biometric_worker_15 = (ImageView) view.findViewById(R.id.iv_biometric15);
        biometric_worker_16 = (ImageView) view.findViewById(R.id.iv_biometric16);
        biometric_worker_17 = (ImageView) view.findViewById(R.id.iv_biometric17);

        llw1 = (LinearLayout) view.findViewById(R.id.ll_Worker1);
        llw2 = (LinearLayout) view.findViewById(R.id.ll_Worker2);
        llw3 = (LinearLayout) view.findViewById(R.id.ll_Worker3);
        llw4 = (LinearLayout) view.findViewById(R.id.ll_Worker4);
        llw5 = (LinearLayout) view.findViewById(R.id.ll_Worker5);
        llw6 = (LinearLayout) view.findViewById(R.id.ll_Worker6);
        llw7 = (LinearLayout) view.findViewById(R.id.ll_Worker7);
        llw8 = (LinearLayout) view.findViewById(R.id.ll_Worker8);
        llw9 = (LinearLayout) view.findViewById(R.id.ll_Worker9);
        llw10 = (LinearLayout) view.findViewById(R.id.ll_Worker10);
        llw11 = (LinearLayout) view.findViewById(R.id.ll_Worker11);
        llw12 = (LinearLayout) view.findViewById(R.id.ll_Worker12);
        llw13 = (LinearLayout) view.findViewById(R.id.ll_Worker13);
        llw14 = (LinearLayout) view.findViewById(R.id.ll_Worker14);
        llw15 = (LinearLayout) view.findViewById(R.id.ll_Worker15);
        llw16 = (LinearLayout) view.findViewById(R.id.ll_Worker16);
        llw17 = (LinearLayout) view.findViewById(R.id.ll_Worker17);
    }

    private void setDefault() {
        Utilities.invisibleAll(ll1, ll2, ll3, ll4, ll5, ll6, ll7, ll8, ll9, ll10);
        Utilities.invisibleAll(llw1, llw2, llw3, llw4, llw5, llw6, llw7, llw8, llw9, llw10, llw11, llw12,
                llw13, llw14, llw15, llw16, llw17);
//        scroll_list.setVisibility(View.GONE);

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

        spn_weekly_off.setAdapter(adapter);

        licence_no = session.getLicenseNo();
        inspection_no = session.getInspectionNo();

        tvHeader.setText("Basic Details");

        String basicData = session.getInfoBasedOnLicense();

        JSONObject jsonObject;
        try {
            jsonObject = new JSONObject(basicData);
            json = jsonObject.optJSONObject("objLabourInspectionSchema");

            name_of_establishment.setText(json.getString("Institution_Name"));
            address.setText(json.getString("Institution_Addr"));
            name_of_employer.setText(json.getString("Owner_Name"));
            address_employer.setText(json.getString("Owner_Addr"));
            direct_worker.setText(json.getString("TotalDirectEmp"));
            contract_worker.setText(json.getString("TotalContractEmp"));
            total.setText(json.getString("TotalWorkers"));
            male.setText(json.getString("Male"));
            female.setText(json.getString("Female"));
            transgender.setText(json.getString("Transgender"));

        } catch (Exception e) {
            male.setText("0");
            female.setText("0");
            transgender.setText("0");
            e.printStackTrace();
        }

//        String basicInfo = session.getBasicDetailsInfo();
//        JSONObject basic;
//        JSONObject objJson;
//        try {
//            basic = new JSONObject(basicInfo);
//            objJson = basic.optJSONObject("objLabourInspectionSchema");
//
//            male.setText(objJson.getString("Male"));
//            female.setText(objJson.getString("Female"));
//            registration.setText(objJson.getString("RegistrationUnder"));
//            schedule_empl.setText(objJson.getString("ScheduleEmp"));
//            working_hours.setText(objJson.getString("WorkingHr"));
//            weekly_off.setText(objJson.getString("WeeklyOff"));
//            date.setText(objJson.getString("DateOfInspection"));
//            representative_of_principle.setText(objJson.getString("representative_of_principle"));
//            direct_worker.setText(objJson.getString("TotalDirectEmp"));
//            contract_worker.setText(objJson.getString("TotalContractEmp"));
//            year_of_starting.setText(objJson.getString("year_of_starting"));
//            accounting_year.setText(objJson.getString("accounting_year"));
//            declaration_designation.setText(objJson.getString("PresentEmpDesg"));
//            is_present.setText(objJson.getString("PresentEmpName"));
//            name_of_employer.setText(objJson.getString("Owner_Name"));
//            total.setText(objJson.getString("TotalWorkers"));
//
//        } catch (JSONException e) {
//            e.printStackTrace();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        ll_no_of_workers.setVisibility(View.GONE);
        hs_worker_info.setVisibility(View.GONE);
        ll_address_site_1970.setVisibility(View.GONE);
        ll_employees_1970.setVisibility(View.GONE);
    }

    private void setEventListeners() {
        date.setOnClickListener(this);
        working_hours.setOnClickListener(this);
        submit.setOnClickListener(this);

        biometric_worker_1.setOnClickListener(this);
        biometric_worker_2.setOnClickListener(this);
        biometric_worker_3.setOnClickListener(this);
        biometric_worker_4.setOnClickListener(this);
        biometric_worker_5.setOnClickListener(this);
        biometric_worker_6.setOnClickListener(this);
        biometric_worker_7.setOnClickListener(this);
        biometric_worker_8.setOnClickListener(this);
        biometric_worker_9.setOnClickListener(this);
        biometric_worker_10.setOnClickListener(this);


//        male.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//                try {
//                    if (male.getText().toString().equals("") && female.getText().toString().equals("")) {
//                        total.setText("");
//                    } else {
//                        int strMale = Integer.parseInt(male.getText().toString());
//                        int strFemale;
//                        if (!female.getText().toString().equalsIgnoreCase("")) {
//                            strFemale = Integer.parseInt(female.getText().toString());
//                        } else {
//                            strFemale = 0;
//                        }
//                        total.setText(Integer.toString(strMale + strFemale));
//                    }
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        });
//
//        female.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//                try {
//                    if (male.getText().toString().equals("") && female.getText().toString().equals("")) {
//                        total.setText("");
//                    } else {
//                        int strFemale = Integer.parseInt(female.getText().toString());
//                        int strMale;
//                        if (!male.getText().toString().equalsIgnoreCase("")) {
//                            strMale = Integer.parseInt(male.getText().toString());
//                        } else {
//                            strMale = 0;
//                        }
//                        total.setText(Integer.toString(strMale + strFemale));
//                    }
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        });

        no_of_contractors_count.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!s.toString().equalsIgnoreCase("")) {
                    count = Integer.parseInt(no_of_contractors_count.getText()
                            .toString());
                    switch (count) {
                        case 0:
                            Utilities.invisibleAll(ll1, ll2, ll3, ll4, ll5, ll6,
                                    ll7, ll8, ll9, ll10);
                            break;
                        case 1:
                            Utilities.invisibleAll(ll1, ll2, ll3, ll4, ll5, ll6,
                                    ll7, ll8, ll9, ll10);
                            Utilities.visibleAll(ll1);
                            break;

                        case 2:
                            Utilities.invisibleAll(ll1, ll2, ll3, ll4, ll5, ll6,
                                    ll7, ll8, ll9, ll10);
                            Utilities.visibleAll(ll1, ll2);
                            break;

                        case 3:
                            Utilities.invisibleAll(ll1, ll2, ll3, ll4, ll5, ll6,
                                    ll7, ll8, ll9, ll10);
                            Utilities.visibleAll(ll1, ll2, ll3);
                            break;

                        case 4:
                            Utilities.invisibleAll(ll1, ll2, ll3, ll4, ll5, ll6,
                                    ll7, ll8, ll9, ll10);
                            Utilities.visibleAll(ll1, ll2, ll3, ll4);
                            break;

                        case 5:
                            Utilities.invisibleAll(ll1, ll2, ll3, ll4, ll5, ll6,
                                    ll7, ll8, ll9, ll10);
                            Utilities.visibleAll(ll1, ll2, ll3, ll4, ll5);
                            break;

                        case 6:
                            Utilities.invisibleAll(ll1, ll2, ll3, ll4, ll5, ll6,
                                    ll7, ll8, ll9, ll10);
                            Utilities.visibleAll(ll1, ll2, ll3, ll4, ll5, ll6);
                            break;

                        case 7:
                            Utilities.invisibleAll(ll1, ll2, ll3, ll4, ll5, ll6,
                                    ll7, ll8, ll9, ll10);
                            Utilities.visibleAll(ll1, ll2, ll3, ll4, ll5, ll6, ll7);
                            break;

                        case 8:
                            Utilities.invisibleAll(ll1, ll2, ll3, ll4, ll5, ll6,
                                    ll7, ll8, ll9, ll10);
                            Utilities.visibleAll(ll1, ll2, ll3, ll4, ll5, ll6, ll7,
                                    ll8);
                            break;

                        case 9:
                            Utilities.invisibleAll(ll1, ll2, ll3, ll4, ll5, ll6,
                                    ll7, ll8, ll9, ll10);
                            Utilities.visibleAll(ll1, ll2, ll3, ll4, ll5, ll6, ll7,
                                    ll8, ll9);
                            break;

                        case 10:
                            Utilities.invisibleAll(ll1, ll2, ll3, ll4, ll5, ll6,
                                    ll7, ll8, ll9, ll10);
                            Utilities.visibleAll(ll1, ll2, ll3, ll4, ll5, ll6, ll7,
                                    ll8, ll9, ll10);
                            break;

                        default:
                            break;
                    }
                }
            }
        });

        no_of_workers.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!s.toString().equalsIgnoreCase("")) {
                    worker_count = Integer.parseInt(no_of_workers.getText()
                            .toString());
                    switch (worker_count) {
                        case 0:
                            Utilities.invisibleAll(llw1, llw2, llw3, llw4, llw5, llw6, llw7, llw8, llw9, llw10, llw11, llw12, llw13, llw14, llw15, llw16, llw17);
                            break;
                        case 1:
                            Utilities.invisibleAll(llw1, llw2, llw3, llw4, llw5, llw6, llw7, llw8, llw9, llw10, llw11, llw12, llw13, llw14, llw15, llw16, llw17);
                            Utilities.visibleAll(llw1);
                            break;

                        case 2:
                            Utilities.invisibleAll(llw1, llw2, llw3, llw4, llw5, llw6, llw7, llw8, llw9, llw10, llw11, llw12, llw13, llw14, llw15, llw16, llw17);
                            Utilities.visibleAll(llw1, llw2);
                            break;

                        case 3:
                            Utilities.invisibleAll(llw1, llw2, llw3, llw4, llw5, llw6, llw7, llw8, llw9, llw10, llw11, llw12, llw13, llw14, llw15, llw16, llw17);
                            Utilities.visibleAll(llw1, llw2, llw3);
                            break;

                        case 4:
                            Utilities.invisibleAll(llw1, llw2, llw3, llw4, llw5, llw6, llw7, llw8, llw9, llw10, llw11, llw12, llw13, llw14, llw15, llw16, llw17);
                            Utilities.visibleAll(llw1, llw2, llw3, llw4);
                            break;

                        case 5:
                            Utilities.invisibleAll(llw1, llw2, llw3, llw4, llw5, llw6, llw7, llw8, llw9, llw10, llw11, llw12, llw13, llw14, llw15, llw16, llw17);
                            Utilities.visibleAll(llw1, llw2, llw3, llw4, llw5);
                            break;

                        case 6:
                            Utilities.invisibleAll(llw1, llw2, llw3, llw4, llw5, llw6, llw7, llw8, llw9, llw10, llw11, llw12, llw13, llw14, llw15, llw16, llw17);
                            Utilities.visibleAll(llw1, llw2, llw3, llw4, llw5, llw6);
                            break;

                        case 7:
                            Utilities.invisibleAll(llw1, llw2, llw3, llw4, llw5, llw6, llw7, llw8, llw9, llw10, llw11, llw12, llw13, llw14, llw15, llw16, llw17);
                            Utilities.visibleAll(llw1, llw2, llw3, llw4, llw5, llw6, llw7);
                            break;

                        case 8:
                            Utilities.invisibleAll(llw1, llw2, llw3, llw4, llw5, llw6, llw7, llw8, llw9, llw10, llw11, llw12, llw13, llw14, llw15, llw16, llw17);
                            Utilities.visibleAll(llw1, llw2, llw3, llw4, llw5, llw6, llw7,
                                    llw8);
                            break;

                        case 9:
                            Utilities.invisibleAll(llw1, llw2, llw3, llw4, llw5, llw6, llw7, llw8, llw9, llw10, llw11, llw12, llw13, llw14, llw15, llw16, llw17);
                            Utilities.visibleAll(llw1, llw2, llw3, llw4, llw5, llw6, llw7,
                                    llw8, llw9);
                            break;

                        case 10:
                            Utilities.invisibleAll(llw1, llw2, llw3, llw4, llw5, llw6, llw7, llw8, llw9, llw10, llw11, llw12, llw13, llw14, llw15, llw16, llw17);
                            Utilities.visibleAll(llw1, llw2, llw3, llw4, llw5, llw6, llw7,
                                    llw8, llw9, llw10);
                            break;

                        case 11:
                            Utilities.invisibleAll(llw1, llw2, llw3, llw4, llw5, llw6, llw7, llw8, llw9, llw10, llw11, llw12, llw13, llw14, llw15, llw16, llw17);
                            Utilities.visibleAll(llw1, llw2, llw3, llw4, llw5, llw6, llw7,
                                    llw8, llw9, llw10, llw11);
                            break;

                        case 12:
                            Utilities.invisibleAll(llw1, llw2, llw3, llw4, llw5, llw6, llw7, llw8, llw9, llw10, llw11, llw12, llw13, llw14, llw15, llw16, llw17);
                            Utilities.visibleAll(llw1, llw2, llw3, llw4, llw5, llw6, llw7,
                                    llw8, llw9, llw10, llw11, llw12);
                            break;

                        case 13:
                            Utilities.invisibleAll(llw1, llw2, llw3, llw4, llw5, llw6, llw7, llw8, llw9, llw10, llw11, llw12, llw13, llw14, llw15, llw16, llw17);
                            Utilities.visibleAll(llw1, llw2, llw3, llw4, llw5, llw6, llw7,
                                    llw8, llw9, llw10, llw11, llw12, llw13);
                            break;

                        case 14:
                            Utilities.invisibleAll(llw1, llw2, llw3, llw4, llw5, llw6, llw7, llw8, llw9, llw10, llw11, llw12, llw13, llw14, llw15, llw16, llw17);
                            Utilities.visibleAll(llw1, llw2, llw3, llw4, llw5, llw6, llw7,
                                    llw8, llw9, llw10, llw11, llw12, llw13, llw14);
                            break;

                        case 15:
                            Utilities.invisibleAll(llw1, llw2, llw3, llw4, llw5, llw6, llw7, llw8, llw9, llw10, llw11, llw12, llw13, llw14, llw15, llw16, llw17);
                            Utilities.visibleAll(llw1, llw2, llw3, llw4, llw5, llw6, llw7,
                                    llw8, llw9, llw10, llw11, llw12, llw13, llw14, llw15);
                            break;

                        case 16:
                            Utilities.invisibleAll(llw1, llw2, llw3, llw4, llw5, llw6, llw7, llw8, llw9, llw10, llw11, llw12, llw13, llw14, llw15, llw16, llw17);
                            Utilities.visibleAll(llw1, llw2, llw3, llw4, llw5, llw6, llw7,
                                    llw8, llw9, llw10, llw11, llw12, llw13, llw14, llw15, llw16);
                            break;

                        case 17:
                            Utilities.invisibleAll(llw1, llw2, llw3, llw4, llw5, llw6, llw7, llw8, llw9, llw10, llw11, llw12, llw13, llw14, llw15, llw16, llw17);
                            Utilities.visibleAll(llw1, llw2, llw3, llw4, llw5, llw6, llw7,
                                    llw8, llw9, llw10, llw11, llw12, llw13, llw14, llw15, llw16, llw17);
                            break;

                        default:
                            break;
                    }
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_submit:
                if (validation()) {

                    createJson();
//                writeXml();
                }
                break;

            case R.id.edit_date_of_inspection:
                DatePickerFragment newFragment = new DatePickerFragment();
                newFragment.setInstance(date);
                newFragment.show(getFragmentManager(), "datePicker");
                break;

            case R.id.edit_working_hours:
                CustomTimeDialog dialogBuilder = new CustomTimeDialog(mainAct, working_hours);
                dialogBuilder.setTitle("Set Time");
                AlertDialog alertDialog = dialogBuilder.create();
                dialogBuilder.getAlert(alertDialog);
                alertDialog.show();
                break;

            case R.id.spn_act:
                break;

            case R.id.iv_biometric1:
                bio_count = 1;
                onCaptureBioMetric(null);
                break;

            case R.id.iv_biometric2:
                bio_count = 2;
                onCaptureBioMetric(null);
                break;

            case R.id.iv_biometric3:
                bio_count = 3;
                onCaptureBioMetric(null);
                break;

            case R.id.iv_biometric4:
                bio_count = 4;
                onCaptureBioMetric(null);
                break;

            case R.id.iv_biometric5:
                bio_count = 5;
                onCaptureBioMetric(null);
                break;

            case R.id.iv_biometric6:
                bio_count = 6;
                onCaptureBioMetric(null);
                break;

            case R.id.iv_biometric7:
                bio_count = 7;
                onCaptureBioMetric(null);
                break;

            case R.id.iv_biometric8:
                bio_count = 8;
                onCaptureBioMetric(null);
                break;

            case R.id.iv_biometric9:
                bio_count = 9;
                onCaptureBioMetric(null);
                break;

            case R.id.iv_biometric10:
                bio_count = 10;
                onCaptureBioMetric(null);
                break;

            case R.id.iv_biometric11:
                bio_count = 11;
                onCaptureBioMetric(null);
                break;

            case R.id.iv_biometric12:
                bio_count = 12;
                onCaptureBioMetric(null);
                break;

            case R.id.iv_biometric13:
                bio_count = 13;
                onCaptureBioMetric(null);
                break;

            default:
                break;
        }
    }

    public void onCaptureBioMetric(View view) {

        if (bio_count == 1) {
            fpSensorDevice.setViewToUpdate(biometric_worker_1);
        } else if (bio_count == 2) {
            fpSensorDevice.setViewToUpdate(biometric_worker_2);
        } else if (bio_count == 3) {
            fpSensorDevice.setViewToUpdate(biometric_worker_3);
        } else if (bio_count == 4) {
            fpSensorDevice.setViewToUpdate(biometric_worker_4);
        } else if (bio_count == 5) {
            fpSensorDevice.setViewToUpdate(biometric_worker_5);
        } else if (bio_count == 6) {
            fpSensorDevice.setViewToUpdate(biometric_worker_6);
        } else if (bio_count == 7) {
            fpSensorDevice.setViewToUpdate(biometric_worker_7);
        } else if (bio_count == 8) {
            fpSensorDevice.setViewToUpdate(biometric_worker_8);
        } else if (bio_count == 9) {
            fpSensorDevice.setViewToUpdate(biometric_worker_9);
        } else if (bio_count == 10) {
            fpSensorDevice.setViewToUpdate(biometric_worker_10);
        } else if (bio_count == 11) {
            fpSensorDevice.setViewToUpdate(biometric_worker_11);
        } else if (bio_count == 12) {
            fpSensorDevice.setViewToUpdate(biometric_worker_12);
        } else if (bio_count == 13) {
            fpSensorDevice.setViewToUpdate(biometric_worker_13);
        }


        try {
            fpSensorDevice.startCapture();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateImageView(final ImageView im, final Bitmap bm, final String s, boolean b, int i) {
        mainAct.runOnUiThread(new Runnable() {

            @Override
            public void run() {
                if (im != null) {
                    im.setImageBitmap(bm);
                }
                if (s.equalsIgnoreCase("Finger captured successfully")) {
                    bioTemplate = fpSensorDevice.templateBuffer;

                    if (bio_count == 1) {
                        strBioTemplate[0] = Base64.encodeToString(bioTemplate, Base64.NO_WRAP);
                    } else if (bio_count == 2) {
                        strBioTemplate[1] = Base64.encodeToString(bioTemplate, Base64.NO_WRAP);
                    } else if (bio_count == 3) {
                        strBioTemplate[2] = Base64.encodeToString(bioTemplate, Base64.NO_WRAP);
                    } else if (bio_count == 4) {
                        strBioTemplate[3] = Base64.encodeToString(bioTemplate, Base64.NO_WRAP);
                    } else if (bio_count == 5) {
                        strBioTemplate[4] = Base64.encodeToString(bioTemplate, Base64.NO_WRAP);
                    } else if (bio_count == 6) {
                        strBioTemplate[5] = Base64.encodeToString(bioTemplate, Base64.NO_WRAP);
                    } else if (bio_count == 7) {
                        strBioTemplate[6] = Base64.encodeToString(bioTemplate, Base64.NO_WRAP);
                    } else if (bio_count == 8) {
                        strBioTemplate[7] = Base64.encodeToString(bioTemplate, Base64.NO_WRAP);
                    } else if (bio_count == 9) {
                        strBioTemplate[8] = Base64.encodeToString(bioTemplate, Base64.NO_WRAP);
                    } else if (bio_count == 10) {
                        strBioTemplate[9] = Base64.encodeToString(bioTemplate, Base64.NO_WRAP);
                    } else if (bio_count == 11) {
                        strBioTemplate[10] = Base64.encodeToString(bioTemplate, Base64.NO_WRAP);
                    } else if (bio_count == 12) {
                        strBioTemplate[11] = Base64.encodeToString(bioTemplate, Base64.NO_WRAP);
                    } else if (bio_count == 13) {
                        strBioTemplate[12] = Base64.encodeToString(bioTemplate, Base64.NO_WRAP);
                    }
                }
            }
        });
    }

    @Override
    public void setQlyFinger(int i) {

    }

    public class PagerAdapter extends FragmentPagerAdapter {

        private FragmentBasicDetails fragmentBasicDetails;
        private FragmentEmployerDetails employerFragment;

        public PagerAdapter(FragmentManager manager) {
            super(manager);
            this.fragmentBasicDetails = new FragmentBasicDetails();
            this.employerFragment = new FragmentEmployerDetails();
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public Fragment getItem(int position) {

            switch (position) {
                case 0:
                    return fragmentBasicDetails;

                case 1:
                    return employerFragment;
                default:
                    return null;
            }
        }
    }

    private String writeXml() {

        serializer = Xml.newSerializer();
        StringWriter writer = new StringWriter();
        try {
            serializer.setOutput(writer);
            serializer.startDocument("UTF-8", true);
            serializer.startTag("", "InspectionActRemarks");

            serializer.startTag("", "PresentEmpName");
            serializer.text(is_present.getText().toString());
            serializer.endTag("", "PresentEmpName");
            serializer.startTag("", "PresentEmpDesg");
            serializer.text(declaration_designation.getText().toString());
            serializer.endTag("", "PresentEmpDesg");
            serializer.startTag("", "DateOfInspection");
            serializer.text(date.getText().toString());
            serializer.endTag("", "DateOfInspection");
            serializer.startTag("", "Remark");
            serializer.text("Test Data");
            serializer.endTag("", "Remark");
            serializer.startTag("", "CreatedBy");
            serializer.text("411f82df-1702-4e37-9016-6db1c4909ffe");
            serializer.endTag("", "CreatedBy");

            serializer.startTag("", "objLabourInspectionSchema");

            serializer.startTag("", "LicenseNo");
            serializer.text(licence_no);
            serializer.endTag("", "LicenseNo");

            serializer.startTag("", "Institution_Name");
            serializer.text(name_of_establishment.getText().toString());
            serializer.endTag("", "Institution_Name");

            serializer.startTag("", "Institution_Addr");
            serializer.text(address.getText().toString());
            serializer.endTag("", "Institution_Addr");

            serializer.startTag("", "Owner_Name");
            serializer.text(json.getString("Owner_Name"));
            serializer.endTag("", "Owner_Name");

            serializer.startTag("", "Owner_Addr");
            serializer.text(address.getText().toString());
            serializer.endTag("", "Owner_Addr");

            serializer.startTag("", "TotalWorkers");
            serializer.text(no_of_workers.getText().toString());
            serializer.endTag("", "TotalWorkers");

            serializer.startTag("", "Male");
            serializer.text(male.getText().toString());
            serializer.endTag("", "Male");

            serializer.startTag("", "Female");
            serializer.text(female.getText().toString());
            serializer.endTag("", "Female");

            serializer.startTag("", "Transgender");
            serializer.text(transgender.getText().toString());
            serializer.endTag("", "Transgender");

            serializer.startTag("", "TotalDirectEmp");
            serializer.text(direct_worker.getText().toString());
            serializer.endTag("", "TotalDirectEmp");

            serializer.startTag("", "TotalContractEmp");
            serializer.text(contract_worker.getText().toString());
            serializer.endTag("", "TotalContractEmp");

            serializer.startTag("", "Transgender");
            serializer.text("0");
            serializer.endTag("", "Transgender");

            serializer.startTag("", "StatusId");
            serializer.text("0");
            serializer.endTag("", "StatusId");

            serializer.startTag("", "RegistrationUnder");
            serializer.text(registration.getText().toString());
            serializer.endTag("", "RegistrationUnder");

            serializer.startTag("", "ScheduleEmp");
            serializer.text(schedule_empl.getText().toString());
            serializer.endTag("", "ScheduleEmp");

            serializer.startTag("", "WorkingHr");
            serializer.text(working_hours.getText().toString());
            serializer.endTag("", "WorkingHr");

//            serializer.startTag("", "WeeklyOff");
//            serializer.text(weekly_off.getText().toString());
//            serializer.endTag("", "WeeklyOff");

            serializer.startTag("", "OfficeID");
            serializer.text("0");
            serializer.endTag("", "OfficeID");

            serializer.startTag("", "ServiceID");
            serializer.text("4");
            serializer.endTag("", "ServiceID");

            serializer.endTag("", "objLabourInspectionSchema");

            initWorkerXml();
//            initContractorXml();

//            serializer.endTag("", "InspectionActRemarks");
//            serializer.endDocument();

            String strSerial = writer.toString();

            session.createBasicInfoSession(strSerial);

            PagerAdapter fragmentPagerAdapter = new PagerAdapter(getFragmentManager());
            RulesFragment frag = (RulesFragment) fragmentPagerAdapter.getItem(1);
            frag.sendData(serializer);


        } catch (Exception e) {
            e.printStackTrace();
        }

        clear();
        Utilities.showMessage("Data Saved Successfully", context);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        viewPager.setCurrentItem(1);
        return writer.toString();
    }

    private void initContractorXml() {
        switch (count) {
            case 1:
                contractorXml(edit_name_of_contractor, edit_nature_of_work, edit_no_of_workers, edit_date_of_commencement);
                break;

            case 2:
                contractorXml(edit_name_of_contractor, edit_nature_of_work, edit_no_of_workers, edit_date_of_commencement);
                contractorXml(edit_name_of_contractor2, edit_nature_of_work2, edit_no_of_workers2, edit_date_of_commencement2);
                break;

            case 3:
                contractorXml(edit_name_of_contractor, edit_nature_of_work, edit_no_of_workers, edit_date_of_commencement);
                contractorXml(edit_name_of_contractor2, edit_nature_of_work2, edit_no_of_workers2, edit_date_of_commencement2);
                contractorXml(edit_name_of_contractor3, edit_nature_of_work3, edit_no_of_workers3, edit_date_of_commencement3);
                break;

            case 4:
                contractorXml(edit_name_of_contractor, edit_nature_of_work, edit_no_of_workers, edit_date_of_commencement);
                contractorXml(edit_name_of_contractor2, edit_nature_of_work2, edit_no_of_workers2, edit_date_of_commencement2);
                contractorXml(edit_name_of_contractor3, edit_nature_of_work3, edit_no_of_workers3, edit_date_of_commencement3);
                contractorXml(edit_name_of_contractor4, edit_nature_of_work4, edit_no_of_workers4, edit_date_of_commencement4);
                break;

            case 5:
                contractorXml(edit_name_of_contractor, edit_nature_of_work, edit_no_of_workers, edit_date_of_commencement);
                contractorXml(edit_name_of_contractor2, edit_nature_of_work2, edit_no_of_workers2, edit_date_of_commencement2);
                contractorXml(edit_name_of_contractor3, edit_nature_of_work3, edit_no_of_workers3, edit_date_of_commencement3);
                contractorXml(edit_name_of_contractor4, edit_nature_of_work4, edit_no_of_workers4, edit_date_of_commencement4);
                contractorXml(edit_name_of_contractor5, edit_nature_of_work5, edit_no_of_workers5, edit_date_of_commencement5);
                break;

            case 6:
                contractorXml(edit_name_of_contractor, edit_nature_of_work, edit_no_of_workers, edit_date_of_commencement);
                contractorXml(edit_name_of_contractor2, edit_nature_of_work2, edit_no_of_workers2, edit_date_of_commencement2);
                contractorXml(edit_name_of_contractor3, edit_nature_of_work3, edit_no_of_workers3, edit_date_of_commencement3);
                contractorXml(edit_name_of_contractor4, edit_nature_of_work4, edit_no_of_workers4, edit_date_of_commencement4);
                contractorXml(edit_name_of_contractor5, edit_nature_of_work5, edit_no_of_workers5, edit_date_of_commencement5);
                contractorXml(edit_name_of_contractor6, edit_nature_of_work6, edit_no_of_workers6, edit_date_of_commencement6);
                break;

            case 7:
                contractorXml(edit_name_of_contractor, edit_nature_of_work, edit_no_of_workers, edit_date_of_commencement);
                contractorXml(edit_name_of_contractor2, edit_nature_of_work2, edit_no_of_workers2, edit_date_of_commencement2);
                contractorXml(edit_name_of_contractor3, edit_nature_of_work3, edit_no_of_workers3, edit_date_of_commencement3);
                contractorXml(edit_name_of_contractor4, edit_nature_of_work4, edit_no_of_workers4, edit_date_of_commencement4);
                contractorXml(edit_name_of_contractor5, edit_nature_of_work5, edit_no_of_workers5, edit_date_of_commencement5);
                contractorXml(edit_name_of_contractor6, edit_nature_of_work6, edit_no_of_workers6, edit_date_of_commencement6);
                contractorXml(edit_name_of_contractor7, edit_nature_of_work7, edit_no_of_workers7, edit_date_of_commencement7);
                break;

            case 8:
                contractorXml(edit_name_of_contractor, edit_nature_of_work, edit_no_of_workers, edit_date_of_commencement);
                contractorXml(edit_name_of_contractor2, edit_nature_of_work2, edit_no_of_workers2, edit_date_of_commencement2);
                contractorXml(edit_name_of_contractor3, edit_nature_of_work3, edit_no_of_workers3, edit_date_of_commencement3);
                contractorXml(edit_name_of_contractor4, edit_nature_of_work4, edit_no_of_workers4, edit_date_of_commencement4);
                contractorXml(edit_name_of_contractor5, edit_nature_of_work5, edit_no_of_workers5, edit_date_of_commencement5);
                contractorXml(edit_name_of_contractor6, edit_nature_of_work6, edit_no_of_workers6, edit_date_of_commencement6);
                contractorXml(edit_name_of_contractor7, edit_nature_of_work7, edit_no_of_workers7, edit_date_of_commencement7);
                contractorXml(edit_name_of_contractor8, edit_nature_of_work8, edit_no_of_workers8, edit_date_of_commencement8);
                break;

            case 9:
                contractorXml(edit_name_of_contractor, edit_nature_of_work, edit_no_of_workers, edit_date_of_commencement);
                contractorXml(edit_name_of_contractor2, edit_nature_of_work2, edit_no_of_workers2, edit_date_of_commencement2);
                contractorXml(edit_name_of_contractor3, edit_nature_of_work3, edit_no_of_workers3, edit_date_of_commencement3);
                contractorXml(edit_name_of_contractor4, edit_nature_of_work4, edit_no_of_workers4, edit_date_of_commencement4);
                contractorXml(edit_name_of_contractor5, edit_nature_of_work5, edit_no_of_workers5, edit_date_of_commencement5);
                contractorXml(edit_name_of_contractor6, edit_nature_of_work6, edit_no_of_workers6, edit_date_of_commencement6);
                contractorXml(edit_name_of_contractor7, edit_nature_of_work7, edit_no_of_workers7, edit_date_of_commencement7);
                contractorXml(edit_name_of_contractor8, edit_nature_of_work8, edit_no_of_workers8, edit_date_of_commencement8);
                contractorXml(edit_name_of_contractor9, edit_nature_of_work9, edit_no_of_workers9, edit_date_of_commencement9);
                break;

            case 10:
                contractorXml(edit_name_of_contractor, edit_nature_of_work, edit_no_of_workers, edit_date_of_commencement);
                contractorXml(edit_name_of_contractor2, edit_nature_of_work2, edit_no_of_workers2, edit_date_of_commencement2);
                contractorXml(edit_name_of_contractor3, edit_nature_of_work3, edit_no_of_workers3, edit_date_of_commencement3);
                contractorXml(edit_name_of_contractor4, edit_nature_of_work4, edit_no_of_workers4, edit_date_of_commencement4);
                contractorXml(edit_name_of_contractor5, edit_nature_of_work5, edit_no_of_workers5, edit_date_of_commencement5);
                contractorXml(edit_name_of_contractor6, edit_nature_of_work6, edit_no_of_workers6, edit_date_of_commencement6);
                contractorXml(edit_name_of_contractor7, edit_nature_of_work7, edit_no_of_workers7, edit_date_of_commencement7);
                contractorXml(edit_name_of_contractor8, edit_nature_of_work8, edit_no_of_workers8, edit_date_of_commencement8);
                contractorXml(edit_name_of_contractor9, edit_nature_of_work9, edit_no_of_workers9, edit_date_of_commencement9);
                contractorXml(edit_name_of_contractor10, edit_nature_of_work10, edit_no_of_workers10, edit_date_of_commencement10);
                break;

            default:
                break;
        }
    }

    private void contractorXml(EditText... editTexts) {

    }

    private void initWorkerXml() {
        try {

            if (worker_count > 0) {
                serializer.startTag("", "objInspectedEmpDetails");
            }

            switch (worker_count) {

                case 1:
                    workerXml(strBioTemplate[0], edit_name_of_worker1, edit_designation1, edit_lenght_of_service1, edit_working_hours1, edit_rest_time1,
                            edit_attend_card1, edit_over_time_rate1, edit_salary_per_day1, edit_date_of_payment1, edit_bonus1);
                    break;

                case 2:
                    workerXml(strBioTemplate[0], edit_name_of_worker1, edit_designation1, edit_lenght_of_service1, edit_working_hours1, edit_rest_time1,
                            edit_attend_card1, edit_over_time_rate1, edit_salary_per_day1, edit_date_of_payment1, edit_bonus1);
                    workerXml(strBioTemplate[1], edit_name_of_worker2, edit_designation2, edit_lenght_of_service2, edit_working_hours2, edit_rest_time2,
                            edit_attend_card2, edit_over_time_rate2, edit_salary_per_day2, edit_date_of_payment2, edit_bonus2);
                    break;

                case 3:
                    workerXml(strBioTemplate[0], edit_name_of_worker1, edit_designation1, edit_lenght_of_service1, edit_working_hours1, edit_rest_time1,
                            edit_attend_card1, edit_over_time_rate1, edit_salary_per_day1, edit_date_of_payment1, edit_bonus1);
                    workerXml(strBioTemplate[1], edit_name_of_worker2, edit_designation2, edit_lenght_of_service2, edit_working_hours2, edit_rest_time2,
                            edit_attend_card2, edit_over_time_rate2, edit_salary_per_day2, edit_date_of_payment2, edit_bonus2);
                    workerXml(strBioTemplate[2], edit_name_of_worker3, edit_designation3, edit_lenght_of_service3, edit_working_hours3, edit_rest_time3,
                            edit_attend_card3, edit_over_time_rate3, edit_salary_per_day3, edit_date_of_payment3, edit_bonus3);
                    break;

                case 4:
                    workerXml(strBioTemplate[0], edit_name_of_worker1, edit_designation1, edit_lenght_of_service1, edit_working_hours1, edit_rest_time1,
                            edit_attend_card1, edit_over_time_rate1, edit_salary_per_day1, edit_date_of_payment1, edit_bonus1);
                    workerXml(strBioTemplate[1], edit_name_of_worker2, edit_designation2, edit_lenght_of_service2, edit_working_hours2, edit_rest_time2,
                            edit_attend_card2, edit_over_time_rate2, edit_salary_per_day2, edit_date_of_payment2, edit_bonus2);
                    workerXml(strBioTemplate[2], edit_name_of_worker3, edit_designation3, edit_lenght_of_service3, edit_working_hours3, edit_rest_time3,
                            edit_attend_card3, edit_over_time_rate3, edit_salary_per_day3, edit_date_of_payment3, edit_bonus3);
                    workerXml(strBioTemplate[3], edit_name_of_worker4, edit_designation4, edit_lenght_of_service4, edit_working_hours4, edit_rest_time4,
                            edit_attend_card4, edit_over_time_rate4, edit_salary_per_day4, edit_date_of_payment4, edit_bonus4);
                    break;

                case 5:
                    workerXml(strBioTemplate[0], edit_name_of_worker1, edit_designation1, edit_lenght_of_service1, edit_working_hours1, edit_rest_time1,
                            edit_attend_card1, edit_over_time_rate1, edit_salary_per_day1, edit_date_of_payment1, edit_bonus1);
                    workerXml(strBioTemplate[1], edit_name_of_worker2, edit_designation2, edit_lenght_of_service2, edit_working_hours2, edit_rest_time2,
                            edit_attend_card2, edit_over_time_rate2, edit_salary_per_day2, edit_date_of_payment2, edit_bonus2);
                    workerXml(strBioTemplate[2], edit_name_of_worker3, edit_designation3, edit_lenght_of_service3, edit_working_hours3, edit_rest_time3,
                            edit_attend_card3, edit_over_time_rate3, edit_salary_per_day3, edit_date_of_payment3, edit_bonus3);
                    workerXml(strBioTemplate[3], edit_name_of_worker4, edit_designation4, edit_lenght_of_service4, edit_working_hours4, edit_rest_time4,
                            edit_attend_card4, edit_over_time_rate4, edit_salary_per_day4, edit_date_of_payment4, edit_bonus4);
                    workerXml(strBioTemplate[4], edit_name_of_worker5, edit_designation5, edit_lenght_of_service5, edit_working_hours5, edit_rest_time5,
                            edit_attend_card5, edit_over_time_rate5, edit_salary_per_day5, edit_date_of_payment5, edit_bonus5);
                    break;

                case 6:
                    workerXml(strBioTemplate[0], edit_name_of_worker1, edit_designation1, edit_lenght_of_service1, edit_working_hours1, edit_rest_time1,
                            edit_attend_card1, edit_over_time_rate1, edit_salary_per_day1, edit_date_of_payment1, edit_bonus1);
                    workerXml(strBioTemplate[1], edit_name_of_worker2, edit_designation2, edit_lenght_of_service2, edit_working_hours2, edit_rest_time2,
                            edit_attend_card2, edit_over_time_rate2, edit_salary_per_day2, edit_date_of_payment2, edit_bonus2);
                    workerXml(strBioTemplate[2], edit_name_of_worker3, edit_designation3, edit_lenght_of_service3, edit_working_hours3, edit_rest_time3,
                            edit_attend_card3, edit_over_time_rate3, edit_salary_per_day3, edit_date_of_payment3, edit_bonus3);
                    workerXml(strBioTemplate[3], edit_name_of_worker4, edit_designation4, edit_lenght_of_service4, edit_working_hours4, edit_rest_time4,
                            edit_attend_card4, edit_over_time_rate4, edit_salary_per_day4, edit_date_of_payment4, edit_bonus4);
                    workerXml(strBioTemplate[4], edit_name_of_worker5, edit_designation5, edit_lenght_of_service5, edit_working_hours5, edit_rest_time5,
                            edit_attend_card5, edit_over_time_rate5, edit_salary_per_day5, edit_date_of_payment5, edit_bonus5);
                    workerXml(strBioTemplate[5], edit_name_of_worker6, edit_designation6, edit_lenght_of_service6, edit_working_hours6, edit_rest_time6,
                            edit_attend_card6, edit_over_time_rate6, edit_salary_per_day6, edit_date_of_payment6, edit_bonus6);
                    break;

                case 7:
                    workerXml(strBioTemplate[0], edit_name_of_worker1, edit_designation1, edit_lenght_of_service1, edit_working_hours1, edit_rest_time1,
                            edit_attend_card1, edit_over_time_rate1, edit_salary_per_day1, edit_date_of_payment1, edit_bonus1);
                    workerXml(strBioTemplate[1], edit_name_of_worker2, edit_designation2, edit_lenght_of_service2, edit_working_hours2, edit_rest_time2,
                            edit_attend_card2, edit_over_time_rate2, edit_salary_per_day2, edit_date_of_payment2, edit_bonus2);
                    workerXml(strBioTemplate[2], edit_name_of_worker3, edit_designation3, edit_lenght_of_service3, edit_working_hours3, edit_rest_time3,
                            edit_attend_card3, edit_over_time_rate3, edit_salary_per_day3, edit_date_of_payment3, edit_bonus3);
                    workerXml(strBioTemplate[3], edit_name_of_worker4, edit_designation4, edit_lenght_of_service4, edit_working_hours4, edit_rest_time4,
                            edit_attend_card4, edit_over_time_rate4, edit_salary_per_day4, edit_date_of_payment4, edit_bonus4);
                    workerXml(strBioTemplate[4], edit_name_of_worker5, edit_designation5, edit_lenght_of_service5, edit_working_hours5, edit_rest_time5,
                            edit_attend_card5, edit_over_time_rate5, edit_salary_per_day5, edit_date_of_payment5, edit_bonus5);
                    workerXml(strBioTemplate[5], edit_name_of_worker6, edit_designation6, edit_lenght_of_service6, edit_working_hours6, edit_rest_time6,
                            edit_attend_card6, edit_over_time_rate6, edit_salary_per_day6, edit_date_of_payment6, edit_bonus6);
                    workerXml(strBioTemplate[6], edit_name_of_worker7, edit_designation7, edit_lenght_of_service7, edit_working_hours7, edit_rest_time7,
                            edit_attend_card7, edit_over_time_rate7, edit_salary_per_day7, edit_date_of_payment7, edit_bonus7);
                    break;

                case 8:

                    workerXml(strBioTemplate[0], edit_name_of_worker1, edit_designation1, edit_lenght_of_service1, edit_working_hours1, edit_rest_time1,
                            edit_attend_card1, edit_over_time_rate1, edit_salary_per_day1, edit_date_of_payment1, edit_bonus1);
                    workerXml(strBioTemplate[1], edit_name_of_worker2, edit_designation2, edit_lenght_of_service2, edit_working_hours2, edit_rest_time2,
                            edit_attend_card2, edit_over_time_rate2, edit_salary_per_day2, edit_date_of_payment2, edit_bonus2);
                    workerXml(strBioTemplate[2], edit_name_of_worker3, edit_designation3, edit_lenght_of_service3, edit_working_hours3, edit_rest_time3,
                            edit_attend_card3, edit_over_time_rate3, edit_salary_per_day3, edit_date_of_payment3, edit_bonus3);
                    workerXml(strBioTemplate[3], edit_name_of_worker4, edit_designation4, edit_lenght_of_service4, edit_working_hours4, edit_rest_time4,
                            edit_attend_card4, edit_over_time_rate4, edit_salary_per_day4, edit_date_of_payment4, edit_bonus4);
                    workerXml(strBioTemplate[4], edit_name_of_worker5, edit_designation5, edit_lenght_of_service5, edit_working_hours5, edit_rest_time5,
                            edit_attend_card5, edit_over_time_rate5, edit_salary_per_day5, edit_date_of_payment5, edit_bonus5);
                    workerXml(strBioTemplate[5], edit_name_of_worker6, edit_designation6, edit_lenght_of_service6, edit_working_hours6, edit_rest_time6,
                            edit_attend_card6, edit_over_time_rate6, edit_salary_per_day6, edit_date_of_payment6, edit_bonus6);
                    workerXml(strBioTemplate[6], edit_name_of_worker7, edit_designation7, edit_lenght_of_service7, edit_working_hours7, edit_rest_time7,
                            edit_attend_card7, edit_over_time_rate7, edit_salary_per_day7, edit_date_of_payment7, edit_bonus7);
                    workerXml(strBioTemplate[7], edit_name_of_worker8, edit_designation8, edit_lenght_of_service8, edit_working_hours8, edit_rest_time8,
                            edit_attend_card8, edit_over_time_rate8, edit_salary_per_day8, edit_date_of_payment8, edit_bonus8);
                    break;


                case 9:
                    workerXml(strBioTemplate[0], edit_name_of_worker1, edit_designation1, edit_lenght_of_service1, edit_working_hours1, edit_rest_time1,
                            edit_attend_card1, edit_over_time_rate1, edit_salary_per_day1, edit_date_of_payment1, edit_bonus1);
                    workerXml(strBioTemplate[1], edit_name_of_worker2, edit_designation2, edit_lenght_of_service2, edit_working_hours2, edit_rest_time2,
                            edit_attend_card2, edit_over_time_rate2, edit_salary_per_day2, edit_date_of_payment2, edit_bonus2);
                    workerXml(strBioTemplate[2], edit_name_of_worker3, edit_designation3, edit_lenght_of_service3, edit_working_hours3, edit_rest_time3,
                            edit_attend_card3, edit_over_time_rate3, edit_salary_per_day3, edit_date_of_payment3, edit_bonus3);
                    workerXml(strBioTemplate[3], edit_name_of_worker4, edit_designation4, edit_lenght_of_service4, edit_working_hours4, edit_rest_time4,
                            edit_attend_card4, edit_over_time_rate4, edit_salary_per_day4, edit_date_of_payment4, edit_bonus4);
                    workerXml(strBioTemplate[4], edit_name_of_worker5, edit_designation5, edit_lenght_of_service5, edit_working_hours5, edit_rest_time5,
                            edit_attend_card5, edit_over_time_rate5, edit_salary_per_day5, edit_date_of_payment5, edit_bonus5);
                    workerXml(strBioTemplate[5], edit_name_of_worker6, edit_designation6, edit_lenght_of_service6, edit_working_hours6, edit_rest_time6,
                            edit_attend_card6, edit_over_time_rate6, edit_salary_per_day6, edit_date_of_payment6, edit_bonus6);
                    workerXml(strBioTemplate[6], edit_name_of_worker7, edit_designation7, edit_lenght_of_service7, edit_working_hours7, edit_rest_time7,
                            edit_attend_card7, edit_over_time_rate7, edit_salary_per_day7, edit_date_of_payment7, edit_bonus7);
                    workerXml(strBioTemplate[7], edit_name_of_worker8, edit_designation8, edit_lenght_of_service8, edit_working_hours8, edit_rest_time8,
                            edit_attend_card8, edit_over_time_rate8, edit_salary_per_day8, edit_date_of_payment8, edit_bonus8);
                    workerXml(strBioTemplate[8], edit_name_of_worker9, edit_designation9, edit_lenght_of_service9, edit_working_hours9, edit_rest_time9,
                            edit_attend_card9, edit_over_time_rate9, edit_salary_per_day9, edit_date_of_payment9, edit_bonus9);

                    break;

                case 10:
                    workerXml(strBioTemplate[0], edit_name_of_worker1, edit_designation1, edit_lenght_of_service1, edit_working_hours1, edit_rest_time1,
                            edit_attend_card1, edit_over_time_rate1, edit_salary_per_day1, edit_date_of_payment1, edit_bonus1);
                    workerXml(strBioTemplate[1], edit_name_of_worker2, edit_designation2, edit_lenght_of_service2, edit_working_hours2, edit_rest_time2,
                            edit_attend_card2, edit_over_time_rate2, edit_salary_per_day2, edit_date_of_payment2, edit_bonus2);
                    workerXml(strBioTemplate[2], edit_name_of_worker3, edit_designation3, edit_lenght_of_service3, edit_working_hours3, edit_rest_time3,
                            edit_attend_card3, edit_over_time_rate3, edit_salary_per_day3, edit_date_of_payment3, edit_bonus3);
                    workerXml(strBioTemplate[3], edit_name_of_worker4, edit_designation4, edit_lenght_of_service4, edit_working_hours4, edit_rest_time4,
                            edit_attend_card4, edit_over_time_rate4, edit_salary_per_day4, edit_date_of_payment4, edit_bonus4);
                    workerXml(strBioTemplate[4], edit_name_of_worker5, edit_designation5, edit_lenght_of_service5, edit_working_hours5, edit_rest_time5,
                            edit_attend_card5, edit_over_time_rate5, edit_salary_per_day5, edit_date_of_payment5, edit_bonus5);
                    workerXml(strBioTemplate[5], edit_name_of_worker6, edit_designation6, edit_lenght_of_service6, edit_working_hours6, edit_rest_time6,
                            edit_attend_card6, edit_over_time_rate6, edit_salary_per_day6, edit_date_of_payment6, edit_bonus6);
                    workerXml(strBioTemplate[6], edit_name_of_worker7, edit_designation7, edit_lenght_of_service7, edit_working_hours7, edit_rest_time7,
                            edit_attend_card7, edit_over_time_rate7, edit_salary_per_day7, edit_date_of_payment7, edit_bonus7);
                    workerXml(strBioTemplate[7], edit_name_of_worker8, edit_designation8, edit_lenght_of_service8, edit_working_hours8, edit_rest_time8,
                            edit_attend_card8, edit_over_time_rate8, edit_salary_per_day8, edit_date_of_payment8, edit_bonus8);
                    workerXml(strBioTemplate[8], edit_name_of_worker9, edit_designation9, edit_lenght_of_service9, edit_working_hours9, edit_rest_time9,
                            edit_attend_card9, edit_over_time_rate9, edit_salary_per_day9, edit_date_of_payment9, edit_bonus9);
                    workerXml(strBioTemplate[9], edit_name_of_worker10, edit_designation10, edit_lenght_of_service10, edit_working_hours10, edit_rest_time10,
                            edit_attend_card10, edit_over_time_rate10, edit_salary_per_day10, edit_date_of_payment10, edit_bonus10);
                    break;

                case 11:
                    workerXml(strBioTemplate[0], edit_name_of_worker1, edit_designation1, edit_lenght_of_service1, edit_working_hours1, edit_rest_time1,
                            edit_attend_card1, edit_over_time_rate1, edit_salary_per_day1, edit_date_of_payment1, edit_bonus1);
                    workerXml(strBioTemplate[1], edit_name_of_worker2, edit_designation2, edit_lenght_of_service2, edit_working_hours2, edit_rest_time2,
                            edit_attend_card2, edit_over_time_rate2, edit_salary_per_day2, edit_date_of_payment2, edit_bonus2);
                    workerXml(strBioTemplate[2], edit_name_of_worker3, edit_designation3, edit_lenght_of_service3, edit_working_hours3, edit_rest_time3,
                            edit_attend_card3, edit_over_time_rate3, edit_salary_per_day3, edit_date_of_payment3, edit_bonus3);
                    workerXml(strBioTemplate[3], edit_name_of_worker4, edit_designation4, edit_lenght_of_service4, edit_working_hours4, edit_rest_time4,
                            edit_attend_card4, edit_over_time_rate4, edit_salary_per_day4, edit_date_of_payment4, edit_bonus4);
                    workerXml(strBioTemplate[4], edit_name_of_worker5, edit_designation5, edit_lenght_of_service5, edit_working_hours5, edit_rest_time5,
                            edit_attend_card5, edit_over_time_rate5, edit_salary_per_day5, edit_date_of_payment5, edit_bonus5);
                    workerXml(strBioTemplate[5], edit_name_of_worker6, edit_designation6, edit_lenght_of_service6, edit_working_hours6, edit_rest_time6,
                            edit_attend_card6, edit_over_time_rate6, edit_salary_per_day6, edit_date_of_payment6, edit_bonus6);
                    workerXml(strBioTemplate[6], edit_name_of_worker7, edit_designation7, edit_lenght_of_service7, edit_working_hours7, edit_rest_time7,
                            edit_attend_card7, edit_over_time_rate7, edit_salary_per_day7, edit_date_of_payment7, edit_bonus7);
                    workerXml(strBioTemplate[7], edit_name_of_worker8, edit_designation8, edit_lenght_of_service8, edit_working_hours8, edit_rest_time8,
                            edit_attend_card8, edit_over_time_rate8, edit_salary_per_day8, edit_date_of_payment8, edit_bonus8);
                    workerXml(strBioTemplate[8], edit_name_of_worker9, edit_designation9, edit_lenght_of_service9, edit_working_hours9, edit_rest_time9,
                            edit_attend_card9, edit_over_time_rate9, edit_salary_per_day9, edit_date_of_payment9, edit_bonus9);
                    workerXml(strBioTemplate[9], edit_name_of_worker10, edit_designation10, edit_lenght_of_service10, edit_working_hours10, edit_rest_time10,
                            edit_attend_card10, edit_over_time_rate10, edit_salary_per_day10, edit_date_of_payment10, edit_bonus10);
                    workerXml(strBioTemplate[10], edit_name_of_worker11, edit_designation11, edit_lenght_of_service11, edit_working_hours11, edit_rest_time11,
                            edit_attend_card11, edit_over_time_rate11, edit_salary_per_day11, edit_date_of_payment11, edit_bonus11);
                    break;

                case 12:
                    workerXml(strBioTemplate[0], edit_name_of_worker1, edit_designation1, edit_lenght_of_service1, edit_working_hours1, edit_rest_time1,
                            edit_attend_card1, edit_over_time_rate1, edit_salary_per_day1, edit_date_of_payment1, edit_bonus1);
                    workerXml(strBioTemplate[1], edit_name_of_worker2, edit_designation2, edit_lenght_of_service2, edit_working_hours2, edit_rest_time2,
                            edit_attend_card2, edit_over_time_rate2, edit_salary_per_day2, edit_date_of_payment2, edit_bonus2);
                    workerXml(strBioTemplate[2], edit_name_of_worker3, edit_designation3, edit_lenght_of_service3, edit_working_hours3, edit_rest_time3,
                            edit_attend_card3, edit_over_time_rate3, edit_salary_per_day3, edit_date_of_payment3, edit_bonus3);
                    workerXml(strBioTemplate[3], edit_name_of_worker4, edit_designation4, edit_lenght_of_service4, edit_working_hours4, edit_rest_time4,
                            edit_attend_card4, edit_over_time_rate4, edit_salary_per_day4, edit_date_of_payment4, edit_bonus4);
                    workerXml(strBioTemplate[4], edit_name_of_worker5, edit_designation5, edit_lenght_of_service5, edit_working_hours5, edit_rest_time5,
                            edit_attend_card5, edit_over_time_rate5, edit_salary_per_day5, edit_date_of_payment5, edit_bonus5);
                    workerXml(strBioTemplate[5], edit_name_of_worker6, edit_designation6, edit_lenght_of_service6, edit_working_hours6, edit_rest_time6,
                            edit_attend_card6, edit_over_time_rate6, edit_salary_per_day6, edit_date_of_payment6, edit_bonus6);
                    workerXml(strBioTemplate[6], edit_name_of_worker7, edit_designation7, edit_lenght_of_service7, edit_working_hours7, edit_rest_time7,
                            edit_attend_card7, edit_over_time_rate7, edit_salary_per_day7, edit_date_of_payment7, edit_bonus7);
                    workerXml(strBioTemplate[7], edit_name_of_worker8, edit_designation8, edit_lenght_of_service8, edit_working_hours8, edit_rest_time8,
                            edit_attend_card8, edit_over_time_rate8, edit_salary_per_day8, edit_date_of_payment8, edit_bonus8);
                    workerXml(strBioTemplate[8], edit_name_of_worker9, edit_designation9, edit_lenght_of_service9, edit_working_hours9, edit_rest_time9,
                            edit_attend_card9, edit_over_time_rate9, edit_salary_per_day9, edit_date_of_payment9, edit_bonus9);
                    workerXml(strBioTemplate[9], edit_name_of_worker10, edit_designation10, edit_lenght_of_service10, edit_working_hours10, edit_rest_time10,
                            edit_attend_card10, edit_over_time_rate10, edit_salary_per_day10, edit_date_of_payment10, edit_bonus10);
                    workerXml(strBioTemplate[10], edit_name_of_worker11, edit_designation11, edit_lenght_of_service11, edit_working_hours11, edit_rest_time11,
                            edit_attend_card11, edit_over_time_rate11, edit_salary_per_day11, edit_date_of_payment11, edit_bonus11);
                    workerXml(strBioTemplate[11], edit_name_of_worker12, edit_designation12, edit_lenght_of_service12, edit_working_hours12, edit_rest_time12,
                            edit_attend_card12, edit_over_time_rate12, edit_salary_per_day12, edit_date_of_payment12, edit_bonus12);
                    break;

                case 13:
                    workerXml(strBioTemplate[0], edit_name_of_worker1, edit_designation1, edit_lenght_of_service1, edit_working_hours1, edit_rest_time1,
                            edit_attend_card1, edit_over_time_rate1, edit_salary_per_day1, edit_date_of_payment1, edit_bonus1);
                    workerXml(strBioTemplate[1], edit_name_of_worker2, edit_designation2, edit_lenght_of_service2, edit_working_hours2, edit_rest_time2,
                            edit_attend_card2, edit_over_time_rate2, edit_salary_per_day2, edit_date_of_payment2, edit_bonus2);
                    workerXml(strBioTemplate[2], edit_name_of_worker3, edit_designation3, edit_lenght_of_service3, edit_working_hours3, edit_rest_time3,
                            edit_attend_card3, edit_over_time_rate3, edit_salary_per_day3, edit_date_of_payment3, edit_bonus3);
                    workerXml(strBioTemplate[3], edit_name_of_worker4, edit_designation4, edit_lenght_of_service4, edit_working_hours4, edit_rest_time4,
                            edit_attend_card4, edit_over_time_rate4, edit_salary_per_day4, edit_date_of_payment4, edit_bonus4);
                    workerXml(strBioTemplate[4], edit_name_of_worker5, edit_designation5, edit_lenght_of_service5, edit_working_hours5, edit_rest_time5,
                            edit_attend_card5, edit_over_time_rate5, edit_salary_per_day5, edit_date_of_payment5, edit_bonus5);
                    workerXml(strBioTemplate[5], edit_name_of_worker6, edit_designation6, edit_lenght_of_service6, edit_working_hours6, edit_rest_time6,
                            edit_attend_card6, edit_over_time_rate6, edit_salary_per_day6, edit_date_of_payment6, edit_bonus6);
                    workerXml(strBioTemplate[6], edit_name_of_worker7, edit_designation7, edit_lenght_of_service7, edit_working_hours7, edit_rest_time7,
                            edit_attend_card7, edit_over_time_rate7, edit_salary_per_day7, edit_date_of_payment7, edit_bonus7);
                    workerXml(strBioTemplate[7], edit_name_of_worker8, edit_designation8, edit_lenght_of_service8, edit_working_hours8, edit_rest_time8,
                            edit_attend_card8, edit_over_time_rate8, edit_salary_per_day8, edit_date_of_payment8, edit_bonus8);
                    workerXml(strBioTemplate[8], edit_name_of_worker9, edit_designation9, edit_lenght_of_service9, edit_working_hours9, edit_rest_time9,
                            edit_attend_card9, edit_over_time_rate9, edit_salary_per_day9, edit_date_of_payment9, edit_bonus9);
                    workerXml(strBioTemplate[9], edit_name_of_worker10, edit_designation10, edit_lenght_of_service10, edit_working_hours10, edit_rest_time10,
                            edit_attend_card10, edit_over_time_rate10, edit_salary_per_day10, edit_date_of_payment10, edit_bonus10);
                    workerXml(strBioTemplate[10], edit_name_of_worker11, edit_designation11, edit_lenght_of_service11, edit_working_hours11, edit_rest_time11,
                            edit_attend_card11, edit_over_time_rate11, edit_salary_per_day11, edit_date_of_payment11, edit_bonus11);
                    workerXml(strBioTemplate[11], edit_name_of_worker12, edit_designation12, edit_lenght_of_service12, edit_working_hours12, edit_rest_time12,
                            edit_attend_card12, edit_over_time_rate12, edit_salary_per_day12, edit_date_of_payment12, edit_bonus12);
                    workerXml(strBioTemplate[12], edit_name_of_worker13, edit_designation13, edit_lenght_of_service13, edit_working_hours13, edit_rest_time13,
                            edit_attend_card13, edit_over_time_rate13, edit_salary_per_day13, edit_date_of_payment13, edit_bonus13);
                    break;

                default:
                    break;
            }
            if (worker_count > 0) {
                serializer.endTag("", "objInspectedEmpDetails");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void workerXml(String template, EditText... editTexts) {

        try {
            serializer.startTag("", "InspectedEmpDetails");

            serializer.startTag("", "Name");
            serializer.text(editTexts[0].getText().toString());
            serializer.endTag("", "Name");

            serializer.startTag("", "Designation");
            serializer.text(editTexts[1].getText().toString());
            serializer.endTag("", "Designation");

            serializer.startTag("", "LengthOfService");
            serializer.text(editTexts[2].getText().toString());
            serializer.endTag("", "LengthOfService");

            serializer.startTag("", "WorkingHr");
            serializer.text(editTexts[3].getText().toString());
            serializer.endTag("", "WorkingHr");

            serializer.startTag("", "RestHr");
            serializer.text(editTexts[4].getText().toString());
            serializer.endTag("", "RestHr");

            serializer.startTag("", "AttendCard");
            serializer.text(editTexts[5].getText().toString());
            serializer.endTag("", "AttendCard");

            serializer.startTag("", "OverTimeRate");
            serializer.text(editTexts[6].getText().toString());
            serializer.endTag("", "OverTimeRate");

            serializer.startTag("", "SalayPerDay");
            serializer.text(editTexts[7].getText().toString());
            serializer.endTag("", "SalayPerDay");

            serializer.startTag("", "DateOfPayment");
            serializer.text(editTexts[8].getText().toString());
            serializer.endTag("", "DateOfPayment");

            serializer.startTag("", "Bonus");
            serializer.text(editTexts[9].getText().toString());
            serializer.endTag("", "Bonus");

            serializer.endTag("", "InspectedEmpDetails");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void createJson() {

//        String xml = writeXml();

        JSONObject jsonn = new JSONObject();
        try {

            jsonn.put("LicenseNo", licence_no);
            jsonn.put("InspectionNo", inspection_no);

            jsonn.put("Institution_Name", name_of_establishment.getText().toString());
            jsonn.put("Institution_Addr", address.getText().toString());
            jsonn.put("Institution_State", JSONObject.NULL);
            jsonn.put("Institution_District", JSONObject.NULL);
            jsonn.put("Institution_Taluka", JSONObject.NULL);
            jsonn.put("Institution_Village", JSONObject.NULL);
            jsonn.put("Institution_Pin", JSONObject.NULL);
            jsonn.put("Owner_Name", name_of_employer.getText().toString());
            jsonn.put("Owner_Addr", address.getText().toString());
            jsonn.put("SiteAddr", address_site.getText().toString());
            jsonn.put("Male", male.getText().toString());
            jsonn.put("Female", female.getText().toString());
            jsonn.put("Transgender", transgender.getText().toString());
            jsonn.put("MobileNo", mobile_no.getText().toString());
            jsonn.put("EmailID", email.getText().toString());
            jsonn.put("TotalWorkers", total.getText().toString());
            jsonn.put("StatusId", 0);
            jsonn.put("Flag", JSONObject.NULL);
            jsonn.put("LinkText", JSONObject.NULL);
            jsonn.put("RegistrationUnder", registration.getText().toString());
            jsonn.put("ScheduleEmp", schedule_empl.getText().toString());
            jsonn.put("WorkingHr", working_hours.getText().toString());
            jsonn.put("FromDate", JSONObject.NULL);
            jsonn.put("ToDate", JSONObject.NULL);
            jsonn.put("OfficeID", 0);
            jsonn.put("ServiceID", 3);
            jsonn.put("lstLabourInspectionSchema", JSONObject.NULL);
            jsonn.put("ServiceName", JSONObject.NULL);
            jsonn.put("WeeklyOff", spn_weekly_off.getSelectedItem().toString());
//            jsonn.put("representative_of_principle", representative_of_principle.getText().toString());
            jsonn.put("TotalDirectEmp", direct_worker.getText().toString());
            jsonn.put("TotalContractEmp", contract_worker.getText().toString());
            jsonn.put("CreatedBy", user_id);

//            if (year_of_starting.getText().toString().equalsIgnoreCase("")) {
//                year_of_starting.setText("1900");
//            }
//            if (accounting_year.getText().toString().equalsIgnoreCase("")) {
//                accounting_year.setText("1900");
//            }
//
//            jsonn.put("year_of_starting", year_of_starting.getText().toString());
//            jsonn.put("accounting_year", accounting_year.getText().toString());


        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            dataToDatabase.put("objLabourInspectionSchema", jsonn);
            dataToDatabase.put("PresentEmpName", is_present.getText().toString());
            dataToDatabase.put("PresentEmpDesg", declaration_designation.getText().toString());
            dataToDatabase.put("DateOfInspection", date.getText().toString());
            dataToDatabase.put("InspectionNo", JSONObject.NULL);
            dataToDatabase.put("DateOfInspections", JSONObject.NULL);
            dataToDatabase.put("CreatedBy", user_id);


            session.createBasicInfoSession(dataToDatabase.toString());

            PagerAdapter fragmentPagerAdapter = new PagerAdapter(getFragmentManager());
            FragmentEmployerDetails frag = (FragmentEmployerDetails) fragmentPagerAdapter.getItem(1);
            frag.sendData(dataToDatabase.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        System.out.println(dataToDatabase.toString());
//        clear();
        Utilities.showMessage("Data Saved Successfully", context);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        viewPager.setCurrentItem(1);
    }

    private void clear() {
        name_of_establishment.setText("");
        address.setText("");
        address_site.setText("");
        name_of_employer.setText("");
        email.setText("");
        mobile_no.setText("");
        male.setText("");
        female.setText("");
        transgender.setText("");
        total.setText("");
        registration.setText("");
        schedule_empl.setText("");
        working_hours.setText("");
        date.setText("");
        representative_of_principle.setText("");
        direct_worker.setText("");
        contract_worker.setText("");
        year_of_starting.setText("");
        accounting_year.setText("");
        declaration_designation.setText("");
        is_present.setText("");
        address_employer.setText("");

        no_of_workers.setText("0");
        no_of_contractors_count.setText("0");

        name_of_establishment.setError(null);
        address.setError(null);
        address_site.setError(null);
        name_of_employer.setError(null);
        male.setError(null);
        female.setError(null);
        transgender.setError(null);
        total.setError(null);
        registration.setError(null);
        schedule_empl.setError(null);
        working_hours.setError(null);
        date.setError(null);
        representative_of_principle.setError(null);
        direct_worker.setError(null);
        contract_worker.setError(null);
        year_of_starting.setError(null);
        accounting_year.setError(null);
        declaration_designation.setError(null);
        is_present.setError(null);
    }

    public void createContractorJson() {
        switch (count) {
            case 1:
                contractorJson(edit_name_of_contractor, edit_nature_of_work, edit_no_of_workers, edit_date_of_commencement);
                break;

            case 2:
                contractorJson(edit_name_of_contractor, edit_nature_of_work, edit_no_of_workers, edit_date_of_commencement);
                contractorJson(edit_name_of_contractor2, edit_nature_of_work2, edit_no_of_workers2, edit_date_of_commencement2);
                break;

            case 3:
                contractorJson(edit_name_of_contractor, edit_nature_of_work, edit_no_of_workers, edit_date_of_commencement);
                contractorJson(edit_name_of_contractor2, edit_nature_of_work2, edit_no_of_workers2, edit_date_of_commencement2);
                contractorJson(edit_name_of_contractor3, edit_nature_of_work3, edit_no_of_workers3, edit_date_of_commencement3);
                break;

            case 4:
                contractorJson(edit_name_of_contractor, edit_nature_of_work, edit_no_of_workers, edit_date_of_commencement);
                contractorJson(edit_name_of_contractor2, edit_nature_of_work2, edit_no_of_workers2, edit_date_of_commencement2);
                contractorJson(edit_name_of_contractor3, edit_nature_of_work3, edit_no_of_workers3, edit_date_of_commencement3);
                contractorJson(edit_name_of_contractor4, edit_nature_of_work4, edit_no_of_workers4, edit_date_of_commencement4);
                break;

            case 5:
                contractorJson(edit_name_of_contractor, edit_nature_of_work, edit_no_of_workers, edit_date_of_commencement);
                contractorJson(edit_name_of_contractor2, edit_nature_of_work2, edit_no_of_workers2, edit_date_of_commencement2);
                contractorJson(edit_name_of_contractor3, edit_nature_of_work3, edit_no_of_workers3, edit_date_of_commencement3);
                contractorJson(edit_name_of_contractor4, edit_nature_of_work4, edit_no_of_workers4, edit_date_of_commencement4);
                contractorJson(edit_name_of_contractor5, edit_nature_of_work5, edit_no_of_workers5, edit_date_of_commencement5);
                break;

            case 6:
                contractorJson(edit_name_of_contractor, edit_nature_of_work, edit_no_of_workers, edit_date_of_commencement);
                contractorJson(edit_name_of_contractor2, edit_nature_of_work2, edit_no_of_workers2, edit_date_of_commencement2);
                contractorJson(edit_name_of_contractor3, edit_nature_of_work3, edit_no_of_workers3, edit_date_of_commencement3);
                contractorJson(edit_name_of_contractor4, edit_nature_of_work4, edit_no_of_workers4, edit_date_of_commencement4);
                contractorJson(edit_name_of_contractor5, edit_nature_of_work5, edit_no_of_workers5, edit_date_of_commencement5);
                contractorJson(edit_name_of_contractor6, edit_nature_of_work6, edit_no_of_workers6, edit_date_of_commencement6);
                break;

            case 7:
                contractorJson(edit_name_of_contractor, edit_nature_of_work, edit_no_of_workers, edit_date_of_commencement);
                contractorJson(edit_name_of_contractor2, edit_nature_of_work2, edit_no_of_workers2, edit_date_of_commencement2);
                contractorJson(edit_name_of_contractor3, edit_nature_of_work3, edit_no_of_workers3, edit_date_of_commencement3);
                contractorJson(edit_name_of_contractor4, edit_nature_of_work4, edit_no_of_workers4, edit_date_of_commencement4);
                contractorJson(edit_name_of_contractor5, edit_nature_of_work5, edit_no_of_workers5, edit_date_of_commencement5);
                contractorJson(edit_name_of_contractor6, edit_nature_of_work6, edit_no_of_workers6, edit_date_of_commencement6);
                contractorJson(edit_name_of_contractor7, edit_nature_of_work7, edit_no_of_workers7, edit_date_of_commencement7);
                break;

            case 8:
                contractorJson(edit_name_of_contractor, edit_nature_of_work, edit_no_of_workers, edit_date_of_commencement);
                contractorJson(edit_name_of_contractor2, edit_nature_of_work2, edit_no_of_workers2, edit_date_of_commencement2);
                contractorJson(edit_name_of_contractor3, edit_nature_of_work3, edit_no_of_workers3, edit_date_of_commencement3);
                contractorJson(edit_name_of_contractor4, edit_nature_of_work4, edit_no_of_workers4, edit_date_of_commencement4);
                contractorJson(edit_name_of_contractor5, edit_nature_of_work5, edit_no_of_workers5, edit_date_of_commencement5);
                contractorJson(edit_name_of_contractor6, edit_nature_of_work6, edit_no_of_workers6, edit_date_of_commencement6);
                contractorJson(edit_name_of_contractor7, edit_nature_of_work7, edit_no_of_workers7, edit_date_of_commencement7);
                contractorJson(edit_name_of_contractor8, edit_nature_of_work8, edit_no_of_workers8, edit_date_of_commencement8);
                break;

            case 9:
                contractorJson(edit_name_of_contractor, edit_nature_of_work, edit_no_of_workers, edit_date_of_commencement);
                contractorJson(edit_name_of_contractor2, edit_nature_of_work2, edit_no_of_workers2, edit_date_of_commencement2);
                contractorJson(edit_name_of_contractor3, edit_nature_of_work3, edit_no_of_workers3, edit_date_of_commencement3);
                contractorJson(edit_name_of_contractor4, edit_nature_of_work4, edit_no_of_workers4, edit_date_of_commencement4);
                contractorJson(edit_name_of_contractor5, edit_nature_of_work5, edit_no_of_workers5, edit_date_of_commencement5);
                contractorJson(edit_name_of_contractor6, edit_nature_of_work6, edit_no_of_workers6, edit_date_of_commencement6);
                contractorJson(edit_name_of_contractor7, edit_nature_of_work7, edit_no_of_workers7, edit_date_of_commencement7);
                contractorJson(edit_name_of_contractor8, edit_nature_of_work8, edit_no_of_workers8, edit_date_of_commencement8);
                contractorJson(edit_name_of_contractor9, edit_nature_of_work9, edit_no_of_workers9, edit_date_of_commencement9);
                break;

            case 10:
                contractorJson(edit_name_of_contractor, edit_nature_of_work, edit_no_of_workers, edit_date_of_commencement);
                contractorJson(edit_name_of_contractor2, edit_nature_of_work2, edit_no_of_workers2, edit_date_of_commencement2);
                contractorJson(edit_name_of_contractor3, edit_nature_of_work3, edit_no_of_workers3, edit_date_of_commencement3);
                contractorJson(edit_name_of_contractor4, edit_nature_of_work4, edit_no_of_workers4, edit_date_of_commencement4);
                contractorJson(edit_name_of_contractor5, edit_nature_of_work5, edit_no_of_workers5, edit_date_of_commencement5);
                contractorJson(edit_name_of_contractor6, edit_nature_of_work6, edit_no_of_workers6, edit_date_of_commencement6);
                contractorJson(edit_name_of_contractor7, edit_nature_of_work7, edit_no_of_workers7, edit_date_of_commencement7);
                contractorJson(edit_name_of_contractor8, edit_nature_of_work8, edit_no_of_workers8, edit_date_of_commencement8);
                contractorJson(edit_name_of_contractor9, edit_nature_of_work9, edit_no_of_workers9, edit_date_of_commencement9);
                contractorJson(edit_name_of_contractor10, edit_nature_of_work10, edit_no_of_workers10, edit_date_of_commencement10);
                break;

            default:
                break;
        }
    }

    private void contractorJson(EditText... editTexts) {
        JSONObject json = new JSONObject();
        JSONArray jarr = new JSONArray();

        try {
            jarr = dataToDatabase
                    .getJSONArray("InspectedContractorDetails");
        } catch (JSONException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }

        try {
//            json.put("license_no", licence_no);
//            json.put("inspection_no", inspection_no);

            json.put("NameOfContractor", editTexts[1].getText().toString()
                    .trim());
            json.put("NatureOfWork", editTexts[2].getText().toString()
                    .trim());
            json.put("NoOfWorker", editTexts[3].getText().toString()
                    .trim());
            json.put("DateOfCommencement", editTexts[4].getText().toString()
                    .trim());
            jarr.put(json);

            dataToDatabase.put("InspectedContractorDetails", jarr);

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private void createWorkerJson() {
        switch (worker_count) {
            case 1:
                workerJson(strBioTemplate[0], edit_name_of_worker1, edit_designation1, edit_lenght_of_service1, edit_working_hours1, edit_rest_time1,
                        edit_attend_card1, edit_over_time_rate1, edit_salary_per_day1, edit_date_of_payment1, edit_bonus1);
                break;

            case 2:
                workerJson(strBioTemplate[0], edit_name_of_worker1, edit_designation1, edit_lenght_of_service1, edit_working_hours1, edit_rest_time1,
                        edit_attend_card1, edit_over_time_rate1, edit_salary_per_day1, edit_date_of_payment1, edit_bonus1);
                workerJson(strBioTemplate[1], edit_name_of_worker2, edit_designation2, edit_lenght_of_service2, edit_working_hours2, edit_rest_time2,
                        edit_attend_card2, edit_over_time_rate2, edit_salary_per_day2, edit_date_of_payment2, edit_bonus2);
                break;

            case 3:
                workerJson(strBioTemplate[0], edit_name_of_worker1, edit_designation1, edit_lenght_of_service1, edit_working_hours1, edit_rest_time1,
                        edit_attend_card1, edit_over_time_rate1, edit_salary_per_day1, edit_date_of_payment1, edit_bonus1);
                workerJson(strBioTemplate[1], edit_name_of_worker2, edit_designation2, edit_lenght_of_service2, edit_working_hours2, edit_rest_time2,
                        edit_attend_card2, edit_over_time_rate2, edit_salary_per_day2, edit_date_of_payment2, edit_bonus2);
                workerJson(strBioTemplate[2], edit_name_of_worker3, edit_designation3, edit_lenght_of_service3, edit_working_hours3, edit_rest_time3,
                        edit_attend_card3, edit_over_time_rate3, edit_salary_per_day3, edit_date_of_payment3, edit_bonus3);
                break;

            case 4:
                workerJson(strBioTemplate[0], edit_name_of_worker1, edit_designation1, edit_lenght_of_service1, edit_working_hours1, edit_rest_time1,
                        edit_attend_card1, edit_over_time_rate1, edit_salary_per_day1, edit_date_of_payment1, edit_bonus1);
                workerJson(strBioTemplate[1], edit_name_of_worker2, edit_designation2, edit_lenght_of_service2, edit_working_hours2, edit_rest_time2,
                        edit_attend_card2, edit_over_time_rate2, edit_salary_per_day2, edit_date_of_payment2, edit_bonus2);
                workerJson(strBioTemplate[2], edit_name_of_worker3, edit_designation3, edit_lenght_of_service3, edit_working_hours3, edit_rest_time3,
                        edit_attend_card3, edit_over_time_rate3, edit_salary_per_day3, edit_date_of_payment3, edit_bonus3);
                workerJson(strBioTemplate[3], edit_name_of_worker4, edit_designation4, edit_lenght_of_service4, edit_working_hours4, edit_rest_time4,
                        edit_attend_card4, edit_over_time_rate4, edit_salary_per_day4, edit_date_of_payment4, edit_bonus4);
                break;

            case 5:
                workerJson(strBioTemplate[0], edit_name_of_worker1, edit_designation1, edit_lenght_of_service1, edit_working_hours1, edit_rest_time1,
                        edit_attend_card1, edit_over_time_rate1, edit_salary_per_day1, edit_date_of_payment1, edit_bonus1);
                workerJson(strBioTemplate[1], edit_name_of_worker2, edit_designation2, edit_lenght_of_service2, edit_working_hours2, edit_rest_time2,
                        edit_attend_card2, edit_over_time_rate2, edit_salary_per_day2, edit_date_of_payment2, edit_bonus2);
                workerJson(strBioTemplate[2], edit_name_of_worker3, edit_designation3, edit_lenght_of_service3, edit_working_hours3, edit_rest_time3,
                        edit_attend_card3, edit_over_time_rate3, edit_salary_per_day3, edit_date_of_payment3, edit_bonus3);
                workerJson(strBioTemplate[3], edit_name_of_worker4, edit_designation4, edit_lenght_of_service4, edit_working_hours4, edit_rest_time4,
                        edit_attend_card4, edit_over_time_rate4, edit_salary_per_day4, edit_date_of_payment4, edit_bonus4);
                workerJson(strBioTemplate[4], edit_name_of_worker5, edit_designation5, edit_lenght_of_service5, edit_working_hours5, edit_rest_time5,
                        edit_attend_card5, edit_over_time_rate5, edit_salary_per_day5, edit_date_of_payment5, edit_bonus5);
                break;

            case 6:
                workerJson(strBioTemplate[0], edit_name_of_worker1, edit_designation1, edit_lenght_of_service1, edit_working_hours1, edit_rest_time1,
                        edit_attend_card1, edit_over_time_rate1, edit_salary_per_day1, edit_date_of_payment1, edit_bonus1);
                workerJson(strBioTemplate[1], edit_name_of_worker2, edit_designation2, edit_lenght_of_service2, edit_working_hours2, edit_rest_time2,
                        edit_attend_card2, edit_over_time_rate2, edit_salary_per_day2, edit_date_of_payment2, edit_bonus2);
                workerJson(strBioTemplate[2], edit_name_of_worker3, edit_designation3, edit_lenght_of_service3, edit_working_hours3, edit_rest_time3,
                        edit_attend_card3, edit_over_time_rate3, edit_salary_per_day3, edit_date_of_payment3, edit_bonus3);
                workerJson(strBioTemplate[3], edit_name_of_worker4, edit_designation4, edit_lenght_of_service4, edit_working_hours4, edit_rest_time4,
                        edit_attend_card4, edit_over_time_rate4, edit_salary_per_day4, edit_date_of_payment4, edit_bonus4);
                workerJson(strBioTemplate[4], edit_name_of_worker5, edit_designation5, edit_lenght_of_service5, edit_working_hours5, edit_rest_time5,
                        edit_attend_card5, edit_over_time_rate5, edit_salary_per_day5, edit_date_of_payment5, edit_bonus5);
                workerJson(strBioTemplate[5], edit_name_of_worker6, edit_designation6, edit_lenght_of_service6, edit_working_hours6, edit_rest_time6,
                        edit_attend_card6, edit_over_time_rate6, edit_salary_per_day6, edit_date_of_payment6, edit_bonus6);
                break;

            case 7:
                workerJson(strBioTemplate[0], edit_name_of_worker1, edit_designation1, edit_lenght_of_service1, edit_working_hours1, edit_rest_time1,
                        edit_attend_card1, edit_over_time_rate1, edit_salary_per_day1, edit_date_of_payment1, edit_bonus1);
                workerJson(strBioTemplate[1], edit_name_of_worker2, edit_designation2, edit_lenght_of_service2, edit_working_hours2, edit_rest_time2,
                        edit_attend_card2, edit_over_time_rate2, edit_salary_per_day2, edit_date_of_payment2, edit_bonus2);
                workerJson(strBioTemplate[2], edit_name_of_worker3, edit_designation3, edit_lenght_of_service3, edit_working_hours3, edit_rest_time3,
                        edit_attend_card3, edit_over_time_rate3, edit_salary_per_day3, edit_date_of_payment3, edit_bonus3);
                workerJson(strBioTemplate[3], edit_name_of_worker4, edit_designation4, edit_lenght_of_service4, edit_working_hours4, edit_rest_time4,
                        edit_attend_card4, edit_over_time_rate4, edit_salary_per_day4, edit_date_of_payment4, edit_bonus4);
                workerJson(strBioTemplate[4], edit_name_of_worker5, edit_designation5, edit_lenght_of_service5, edit_working_hours5, edit_rest_time5,
                        edit_attend_card5, edit_over_time_rate5, edit_salary_per_day5, edit_date_of_payment5, edit_bonus5);
                workerJson(strBioTemplate[5], edit_name_of_worker6, edit_designation6, edit_lenght_of_service6, edit_working_hours6, edit_rest_time6,
                        edit_attend_card6, edit_over_time_rate6, edit_salary_per_day6, edit_date_of_payment6, edit_bonus6);
                workerJson(strBioTemplate[6], edit_name_of_worker7, edit_designation7, edit_lenght_of_service7, edit_working_hours7, edit_rest_time7,
                        edit_attend_card7, edit_over_time_rate7, edit_salary_per_day7, edit_date_of_payment7, edit_bonus7);
                break;

            case 8:

                workerJson(strBioTemplate[0], edit_name_of_worker1, edit_designation1, edit_lenght_of_service1, edit_working_hours1, edit_rest_time1,
                        edit_attend_card1, edit_over_time_rate1, edit_salary_per_day1, edit_date_of_payment1, edit_bonus1);
                workerJson(strBioTemplate[1], edit_name_of_worker2, edit_designation2, edit_lenght_of_service2, edit_working_hours2, edit_rest_time2,
                        edit_attend_card2, edit_over_time_rate2, edit_salary_per_day2, edit_date_of_payment2, edit_bonus2);
                workerJson(strBioTemplate[2], edit_name_of_worker3, edit_designation3, edit_lenght_of_service3, edit_working_hours3, edit_rest_time3,
                        edit_attend_card3, edit_over_time_rate3, edit_salary_per_day3, edit_date_of_payment3, edit_bonus3);
                workerJson(strBioTemplate[3], edit_name_of_worker4, edit_designation4, edit_lenght_of_service4, edit_working_hours4, edit_rest_time4,
                        edit_attend_card4, edit_over_time_rate4, edit_salary_per_day4, edit_date_of_payment4, edit_bonus4);
                workerJson(strBioTemplate[4], edit_name_of_worker5, edit_designation5, edit_lenght_of_service5, edit_working_hours5, edit_rest_time5,
                        edit_attend_card5, edit_over_time_rate5, edit_salary_per_day5, edit_date_of_payment5, edit_bonus5);
                workerJson(strBioTemplate[5], edit_name_of_worker6, edit_designation6, edit_lenght_of_service6, edit_working_hours6, edit_rest_time6,
                        edit_attend_card6, edit_over_time_rate6, edit_salary_per_day6, edit_date_of_payment6, edit_bonus6);
                workerJson(strBioTemplate[6], edit_name_of_worker7, edit_designation7, edit_lenght_of_service7, edit_working_hours7, edit_rest_time7,
                        edit_attend_card7, edit_over_time_rate7, edit_salary_per_day7, edit_date_of_payment7, edit_bonus7);
                workerJson(strBioTemplate[7], edit_name_of_worker8, edit_designation8, edit_lenght_of_service8, edit_working_hours8, edit_rest_time8,
                        edit_attend_card8, edit_over_time_rate8, edit_salary_per_day8, edit_date_of_payment8, edit_bonus8);
                break;


            case 9:
                workerJson(strBioTemplate[0], edit_name_of_worker1, edit_designation1, edit_lenght_of_service1, edit_working_hours1, edit_rest_time1,
                        edit_attend_card1, edit_over_time_rate1, edit_salary_per_day1, edit_date_of_payment1, edit_bonus1);
                workerJson(strBioTemplate[1], edit_name_of_worker2, edit_designation2, edit_lenght_of_service2, edit_working_hours2, edit_rest_time2,
                        edit_attend_card2, edit_over_time_rate2, edit_salary_per_day2, edit_date_of_payment2, edit_bonus2);
                workerJson(strBioTemplate[2], edit_name_of_worker3, edit_designation3, edit_lenght_of_service3, edit_working_hours3, edit_rest_time3,
                        edit_attend_card3, edit_over_time_rate3, edit_salary_per_day3, edit_date_of_payment3, edit_bonus3);
                workerJson(strBioTemplate[3], edit_name_of_worker4, edit_designation4, edit_lenght_of_service4, edit_working_hours4, edit_rest_time4,
                        edit_attend_card4, edit_over_time_rate4, edit_salary_per_day4, edit_date_of_payment4, edit_bonus4);
                workerJson(strBioTemplate[4], edit_name_of_worker5, edit_designation5, edit_lenght_of_service5, edit_working_hours5, edit_rest_time5,
                        edit_attend_card5, edit_over_time_rate5, edit_salary_per_day5, edit_date_of_payment5, edit_bonus5);
                workerJson(strBioTemplate[5], edit_name_of_worker6, edit_designation6, edit_lenght_of_service6, edit_working_hours6, edit_rest_time6,
                        edit_attend_card6, edit_over_time_rate6, edit_salary_per_day6, edit_date_of_payment6, edit_bonus6);
                workerJson(strBioTemplate[6], edit_name_of_worker7, edit_designation7, edit_lenght_of_service7, edit_working_hours7, edit_rest_time7,
                        edit_attend_card7, edit_over_time_rate7, edit_salary_per_day7, edit_date_of_payment7, edit_bonus7);
                workerJson(strBioTemplate[7], edit_name_of_worker8, edit_designation8, edit_lenght_of_service8, edit_working_hours8, edit_rest_time8,
                        edit_attend_card8, edit_over_time_rate8, edit_salary_per_day8, edit_date_of_payment8, edit_bonus8);
                workerJson(strBioTemplate[8], edit_name_of_worker9, edit_designation9, edit_lenght_of_service9, edit_working_hours9, edit_rest_time9,
                        edit_attend_card9, edit_over_time_rate9, edit_salary_per_day9, edit_date_of_payment9, edit_bonus9);

                break;

            case 10:
                workerJson(strBioTemplate[0], edit_name_of_worker1, edit_designation1, edit_lenght_of_service1, edit_working_hours1, edit_rest_time1,
                        edit_attend_card1, edit_over_time_rate1, edit_salary_per_day1, edit_date_of_payment1, edit_bonus1);
                workerJson(strBioTemplate[1], edit_name_of_worker2, edit_designation2, edit_lenght_of_service2, edit_working_hours2, edit_rest_time2,
                        edit_attend_card2, edit_over_time_rate2, edit_salary_per_day2, edit_date_of_payment2, edit_bonus2);
                workerJson(strBioTemplate[2], edit_name_of_worker3, edit_designation3, edit_lenght_of_service3, edit_working_hours3, edit_rest_time3,
                        edit_attend_card3, edit_over_time_rate3, edit_salary_per_day3, edit_date_of_payment3, edit_bonus3);
                workerJson(strBioTemplate[3], edit_name_of_worker4, edit_designation4, edit_lenght_of_service4, edit_working_hours4, edit_rest_time4,
                        edit_attend_card4, edit_over_time_rate4, edit_salary_per_day4, edit_date_of_payment4, edit_bonus4);
                workerJson(strBioTemplate[4], edit_name_of_worker5, edit_designation5, edit_lenght_of_service5, edit_working_hours5, edit_rest_time5,
                        edit_attend_card5, edit_over_time_rate5, edit_salary_per_day5, edit_date_of_payment5, edit_bonus5);
                workerJson(strBioTemplate[5], edit_name_of_worker6, edit_designation6, edit_lenght_of_service6, edit_working_hours6, edit_rest_time6,
                        edit_attend_card6, edit_over_time_rate6, edit_salary_per_day6, edit_date_of_payment6, edit_bonus6);
                workerJson(strBioTemplate[6], edit_name_of_worker7, edit_designation7, edit_lenght_of_service7, edit_working_hours7, edit_rest_time7,
                        edit_attend_card7, edit_over_time_rate7, edit_salary_per_day7, edit_date_of_payment7, edit_bonus7);
                workerJson(strBioTemplate[7], edit_name_of_worker8, edit_designation8, edit_lenght_of_service8, edit_working_hours8, edit_rest_time8,
                        edit_attend_card8, edit_over_time_rate8, edit_salary_per_day8, edit_date_of_payment8, edit_bonus8);
                workerJson(strBioTemplate[8], edit_name_of_worker9, edit_designation9, edit_lenght_of_service9, edit_working_hours9, edit_rest_time9,
                        edit_attend_card9, edit_over_time_rate9, edit_salary_per_day9, edit_date_of_payment9, edit_bonus9);
                workerJson(strBioTemplate[9], edit_name_of_worker10, edit_designation10, edit_lenght_of_service10, edit_working_hours10, edit_rest_time10,
                        edit_attend_card10, edit_over_time_rate10, edit_salary_per_day10, edit_date_of_payment10, edit_bonus10);
                break;

            case 11:
                workerJson(strBioTemplate[0], edit_name_of_worker1, edit_designation1, edit_lenght_of_service1, edit_working_hours1, edit_rest_time1,
                        edit_attend_card1, edit_over_time_rate1, edit_salary_per_day1, edit_date_of_payment1, edit_bonus1);
                workerJson(strBioTemplate[1], edit_name_of_worker2, edit_designation2, edit_lenght_of_service2, edit_working_hours2, edit_rest_time2,
                        edit_attend_card2, edit_over_time_rate2, edit_salary_per_day2, edit_date_of_payment2, edit_bonus2);
                workerJson(strBioTemplate[2], edit_name_of_worker3, edit_designation3, edit_lenght_of_service3, edit_working_hours3, edit_rest_time3,
                        edit_attend_card3, edit_over_time_rate3, edit_salary_per_day3, edit_date_of_payment3, edit_bonus3);
                workerJson(strBioTemplate[3], edit_name_of_worker4, edit_designation4, edit_lenght_of_service4, edit_working_hours4, edit_rest_time4,
                        edit_attend_card4, edit_over_time_rate4, edit_salary_per_day4, edit_date_of_payment4, edit_bonus4);
                workerJson(strBioTemplate[4], edit_name_of_worker5, edit_designation5, edit_lenght_of_service5, edit_working_hours5, edit_rest_time5,
                        edit_attend_card5, edit_over_time_rate5, edit_salary_per_day5, edit_date_of_payment5, edit_bonus5);
                workerJson(strBioTemplate[5], edit_name_of_worker6, edit_designation6, edit_lenght_of_service6, edit_working_hours6, edit_rest_time6,
                        edit_attend_card6, edit_over_time_rate6, edit_salary_per_day6, edit_date_of_payment6, edit_bonus6);
                workerJson(strBioTemplate[6], edit_name_of_worker7, edit_designation7, edit_lenght_of_service7, edit_working_hours7, edit_rest_time7,
                        edit_attend_card7, edit_over_time_rate7, edit_salary_per_day7, edit_date_of_payment7, edit_bonus7);
                workerJson(strBioTemplate[7], edit_name_of_worker8, edit_designation8, edit_lenght_of_service8, edit_working_hours8, edit_rest_time8,
                        edit_attend_card8, edit_over_time_rate8, edit_salary_per_day8, edit_date_of_payment8, edit_bonus8);
                workerJson(strBioTemplate[8], edit_name_of_worker9, edit_designation9, edit_lenght_of_service9, edit_working_hours9, edit_rest_time9,
                        edit_attend_card9, edit_over_time_rate9, edit_salary_per_day9, edit_date_of_payment9, edit_bonus9);
                workerJson(strBioTemplate[9], edit_name_of_worker10, edit_designation10, edit_lenght_of_service10, edit_working_hours10, edit_rest_time10,
                        edit_attend_card10, edit_over_time_rate10, edit_salary_per_day10, edit_date_of_payment10, edit_bonus10);
                workerJson(strBioTemplate[10], edit_name_of_worker11, edit_designation11, edit_lenght_of_service11, edit_working_hours11, edit_rest_time11,
                        edit_attend_card11, edit_over_time_rate11, edit_salary_per_day11, edit_date_of_payment11, edit_bonus11);
                break;

            case 12:
                workerJson(strBioTemplate[0], edit_name_of_worker1, edit_designation1, edit_lenght_of_service1, edit_working_hours1, edit_rest_time1,
                        edit_attend_card1, edit_over_time_rate1, edit_salary_per_day1, edit_date_of_payment1, edit_bonus1);
                workerJson(strBioTemplate[1], edit_name_of_worker2, edit_designation2, edit_lenght_of_service2, edit_working_hours2, edit_rest_time2,
                        edit_attend_card2, edit_over_time_rate2, edit_salary_per_day2, edit_date_of_payment2, edit_bonus2);
                workerJson(strBioTemplate[2], edit_name_of_worker3, edit_designation3, edit_lenght_of_service3, edit_working_hours3, edit_rest_time3,
                        edit_attend_card3, edit_over_time_rate3, edit_salary_per_day3, edit_date_of_payment3, edit_bonus3);
                workerJson(strBioTemplate[3], edit_name_of_worker4, edit_designation4, edit_lenght_of_service4, edit_working_hours4, edit_rest_time4,
                        edit_attend_card4, edit_over_time_rate4, edit_salary_per_day4, edit_date_of_payment4, edit_bonus4);
                workerJson(strBioTemplate[4], edit_name_of_worker5, edit_designation5, edit_lenght_of_service5, edit_working_hours5, edit_rest_time5,
                        edit_attend_card5, edit_over_time_rate5, edit_salary_per_day5, edit_date_of_payment5, edit_bonus5);
                workerJson(strBioTemplate[5], edit_name_of_worker6, edit_designation6, edit_lenght_of_service6, edit_working_hours6, edit_rest_time6,
                        edit_attend_card6, edit_over_time_rate6, edit_salary_per_day6, edit_date_of_payment6, edit_bonus6);
                workerJson(strBioTemplate[6], edit_name_of_worker7, edit_designation7, edit_lenght_of_service7, edit_working_hours7, edit_rest_time7,
                        edit_attend_card7, edit_over_time_rate7, edit_salary_per_day7, edit_date_of_payment7, edit_bonus7);
                workerJson(strBioTemplate[7], edit_name_of_worker8, edit_designation8, edit_lenght_of_service8, edit_working_hours8, edit_rest_time8,
                        edit_attend_card8, edit_over_time_rate8, edit_salary_per_day8, edit_date_of_payment8, edit_bonus8);
                workerJson(strBioTemplate[8], edit_name_of_worker9, edit_designation9, edit_lenght_of_service9, edit_working_hours9, edit_rest_time9,
                        edit_attend_card9, edit_over_time_rate9, edit_salary_per_day9, edit_date_of_payment9, edit_bonus9);
                workerJson(strBioTemplate[9], edit_name_of_worker10, edit_designation10, edit_lenght_of_service10, edit_working_hours10, edit_rest_time10,
                        edit_attend_card10, edit_over_time_rate10, edit_salary_per_day10, edit_date_of_payment10, edit_bonus10);
                workerJson(strBioTemplate[10], edit_name_of_worker11, edit_designation11, edit_lenght_of_service11, edit_working_hours11, edit_rest_time11,
                        edit_attend_card11, edit_over_time_rate11, edit_salary_per_day11, edit_date_of_payment11, edit_bonus11);
                workerJson(strBioTemplate[11], edit_name_of_worker12, edit_designation12, edit_lenght_of_service12, edit_working_hours12, edit_rest_time12,
                        edit_attend_card12, edit_over_time_rate12, edit_salary_per_day12, edit_date_of_payment12, edit_bonus12);
                break;

            case 13:
                workerJson(strBioTemplate[0], edit_name_of_worker1, edit_designation1, edit_lenght_of_service1, edit_working_hours1, edit_rest_time1,
                        edit_attend_card1, edit_over_time_rate1, edit_salary_per_day1, edit_date_of_payment1, edit_bonus1);
                workerJson(strBioTemplate[1], edit_name_of_worker2, edit_designation2, edit_lenght_of_service2, edit_working_hours2, edit_rest_time2,
                        edit_attend_card2, edit_over_time_rate2, edit_salary_per_day2, edit_date_of_payment2, edit_bonus2);
                workerJson(strBioTemplate[2], edit_name_of_worker3, edit_designation3, edit_lenght_of_service3, edit_working_hours3, edit_rest_time3,
                        edit_attend_card3, edit_over_time_rate3, edit_salary_per_day3, edit_date_of_payment3, edit_bonus3);
                workerJson(strBioTemplate[3], edit_name_of_worker4, edit_designation4, edit_lenght_of_service4, edit_working_hours4, edit_rest_time4,
                        edit_attend_card4, edit_over_time_rate4, edit_salary_per_day4, edit_date_of_payment4, edit_bonus4);
                workerJson(strBioTemplate[4], edit_name_of_worker5, edit_designation5, edit_lenght_of_service5, edit_working_hours5, edit_rest_time5,
                        edit_attend_card5, edit_over_time_rate5, edit_salary_per_day5, edit_date_of_payment5, edit_bonus5);
                workerJson(strBioTemplate[5], edit_name_of_worker6, edit_designation6, edit_lenght_of_service6, edit_working_hours6, edit_rest_time6,
                        edit_attend_card6, edit_over_time_rate6, edit_salary_per_day6, edit_date_of_payment6, edit_bonus6);
                workerJson(strBioTemplate[6], edit_name_of_worker7, edit_designation7, edit_lenght_of_service7, edit_working_hours7, edit_rest_time7,
                        edit_attend_card7, edit_over_time_rate7, edit_salary_per_day7, edit_date_of_payment7, edit_bonus7);
                workerJson(strBioTemplate[7], edit_name_of_worker8, edit_designation8, edit_lenght_of_service8, edit_working_hours8, edit_rest_time8,
                        edit_attend_card8, edit_over_time_rate8, edit_salary_per_day8, edit_date_of_payment8, edit_bonus8);
                workerJson(strBioTemplate[8], edit_name_of_worker9, edit_designation9, edit_lenght_of_service9, edit_working_hours9, edit_rest_time9,
                        edit_attend_card9, edit_over_time_rate9, edit_salary_per_day9, edit_date_of_payment9, edit_bonus9);
                workerJson(strBioTemplate[9], edit_name_of_worker10, edit_designation10, edit_lenght_of_service10, edit_working_hours10, edit_rest_time10,
                        edit_attend_card10, edit_over_time_rate10, edit_salary_per_day10, edit_date_of_payment10, edit_bonus10);
                workerJson(strBioTemplate[10], edit_name_of_worker11, edit_designation11, edit_lenght_of_service11, edit_working_hours11, edit_rest_time11,
                        edit_attend_card11, edit_over_time_rate11, edit_salary_per_day11, edit_date_of_payment11, edit_bonus11);
                workerJson(strBioTemplate[11], edit_name_of_worker12, edit_designation12, edit_lenght_of_service12, edit_working_hours12, edit_rest_time12,
                        edit_attend_card12, edit_over_time_rate12, edit_salary_per_day12, edit_date_of_payment12, edit_bonus12);
                workerJson(strBioTemplate[12], edit_name_of_worker13, edit_designation13, edit_lenght_of_service13, edit_working_hours13, edit_rest_time13,
                        edit_attend_card13, edit_over_time_rate13, edit_salary_per_day13, edit_date_of_payment13, edit_bonus13);
                break;

            case 14:
                workerJson(strBioTemplate[0], edit_name_of_worker1, edit_designation1, edit_lenght_of_service1, edit_working_hours1, edit_rest_time1,
                        edit_attend_card1, edit_over_time_rate1, edit_salary_per_day1, edit_date_of_payment1, edit_bonus1);
                workerJson(strBioTemplate[1], edit_name_of_worker2, edit_designation2, edit_lenght_of_service2, edit_working_hours2, edit_rest_time2,
                        edit_attend_card2, edit_over_time_rate2, edit_salary_per_day2, edit_date_of_payment2, edit_bonus2);
                workerJson(strBioTemplate[2], edit_name_of_worker3, edit_designation3, edit_lenght_of_service3, edit_working_hours3, edit_rest_time3,
                        edit_attend_card3, edit_over_time_rate3, edit_salary_per_day3, edit_date_of_payment3, edit_bonus3);
                workerJson(strBioTemplate[3], edit_name_of_worker4, edit_designation4, edit_lenght_of_service4, edit_working_hours4, edit_rest_time4,
                        edit_attend_card4, edit_over_time_rate4, edit_salary_per_day4, edit_date_of_payment4, edit_bonus4);
                workerJson(strBioTemplate[4], edit_name_of_worker5, edit_designation5, edit_lenght_of_service5, edit_working_hours5, edit_rest_time5,
                        edit_attend_card5, edit_over_time_rate5, edit_salary_per_day5, edit_date_of_payment5, edit_bonus5);
                workerJson(strBioTemplate[5], edit_name_of_worker6, edit_designation6, edit_lenght_of_service6, edit_working_hours6, edit_rest_time6,
                        edit_attend_card6, edit_over_time_rate6, edit_salary_per_day6, edit_date_of_payment6, edit_bonus6);
                workerJson(strBioTemplate[6], edit_name_of_worker7, edit_designation7, edit_lenght_of_service7, edit_working_hours7, edit_rest_time7,
                        edit_attend_card7, edit_over_time_rate7, edit_salary_per_day7, edit_date_of_payment7, edit_bonus7);
                workerJson(strBioTemplate[7], edit_name_of_worker8, edit_designation8, edit_lenght_of_service8, edit_working_hours8, edit_rest_time8,
                        edit_attend_card8, edit_over_time_rate8, edit_salary_per_day8, edit_date_of_payment8, edit_bonus8);
                workerJson(strBioTemplate[8], edit_name_of_worker9, edit_designation9, edit_lenght_of_service9, edit_working_hours9, edit_rest_time9,
                        edit_attend_card9, edit_over_time_rate9, edit_salary_per_day9, edit_date_of_payment9, edit_bonus9);
                workerJson(strBioTemplate[9], edit_name_of_worker10, edit_designation10, edit_lenght_of_service10, edit_working_hours10, edit_rest_time10,
                        edit_attend_card10, edit_over_time_rate10, edit_salary_per_day10, edit_date_of_payment10, edit_bonus10);
                workerJson(strBioTemplate[10], edit_name_of_worker11, edit_designation11, edit_lenght_of_service11, edit_working_hours11, edit_rest_time11,
                        edit_attend_card11, edit_over_time_rate11, edit_salary_per_day11, edit_date_of_payment11, edit_bonus11);
                workerJson(strBioTemplate[11], edit_name_of_worker12, edit_designation12, edit_lenght_of_service12, edit_working_hours12, edit_rest_time12,
                        edit_attend_card12, edit_over_time_rate12, edit_salary_per_day12, edit_date_of_payment12, edit_bonus12);
                workerJson(strBioTemplate[12], edit_name_of_worker13, edit_designation13, edit_lenght_of_service13, edit_working_hours13, edit_rest_time13,
                        edit_attend_card13, edit_over_time_rate13, edit_salary_per_day13, edit_date_of_payment13, edit_bonus13);
                workerJson(strBioTemplate[13], edit_name_of_worker14, edit_designation14, edit_lenght_of_service14, edit_working_hours14, edit_rest_time14,
                        edit_attend_card14, edit_over_time_rate14, edit_salary_per_day14, edit_date_of_payment14, edit_bonus14);

                break;

            case 15:
                workerJson(strBioTemplate[0], edit_name_of_worker1, edit_designation1, edit_lenght_of_service1, edit_working_hours1, edit_rest_time1,
                        edit_attend_card1, edit_over_time_rate1, edit_salary_per_day1, edit_date_of_payment1, edit_bonus1);
                workerJson(strBioTemplate[1], edit_name_of_worker2, edit_designation2, edit_lenght_of_service2, edit_working_hours2, edit_rest_time2,
                        edit_attend_card2, edit_over_time_rate2, edit_salary_per_day2, edit_date_of_payment2, edit_bonus2);
                workerJson(strBioTemplate[2], edit_name_of_worker3, edit_designation3, edit_lenght_of_service3, edit_working_hours3, edit_rest_time3,
                        edit_attend_card3, edit_over_time_rate3, edit_salary_per_day3, edit_date_of_payment3, edit_bonus3);
                workerJson(strBioTemplate[3], edit_name_of_worker4, edit_designation4, edit_lenght_of_service4, edit_working_hours4, edit_rest_time4,
                        edit_attend_card4, edit_over_time_rate4, edit_salary_per_day4, edit_date_of_payment4, edit_bonus4);
                workerJson(strBioTemplate[4], edit_name_of_worker5, edit_designation5, edit_lenght_of_service5, edit_working_hours5, edit_rest_time5,
                        edit_attend_card5, edit_over_time_rate5, edit_salary_per_day5, edit_date_of_payment5, edit_bonus5);
                workerJson(strBioTemplate[5], edit_name_of_worker6, edit_designation6, edit_lenght_of_service6, edit_working_hours6, edit_rest_time6,
                        edit_attend_card6, edit_over_time_rate6, edit_salary_per_day6, edit_date_of_payment6, edit_bonus6);
                workerJson(strBioTemplate[6], edit_name_of_worker7, edit_designation7, edit_lenght_of_service7, edit_working_hours7, edit_rest_time7,
                        edit_attend_card7, edit_over_time_rate7, edit_salary_per_day7, edit_date_of_payment7, edit_bonus7);
                workerJson(strBioTemplate[7], edit_name_of_worker8, edit_designation8, edit_lenght_of_service8, edit_working_hours8, edit_rest_time8,
                        edit_attend_card8, edit_over_time_rate8, edit_salary_per_day8, edit_date_of_payment8, edit_bonus8);
                workerJson(strBioTemplate[8], edit_name_of_worker9, edit_designation9, edit_lenght_of_service9, edit_working_hours9, edit_rest_time9,
                        edit_attend_card9, edit_over_time_rate9, edit_salary_per_day9, edit_date_of_payment9, edit_bonus9);
                workerJson(strBioTemplate[9], edit_name_of_worker10, edit_designation10, edit_lenght_of_service10, edit_working_hours10, edit_rest_time10,
                        edit_attend_card10, edit_over_time_rate10, edit_salary_per_day10, edit_date_of_payment10, edit_bonus10);
                workerJson(strBioTemplate[10], edit_name_of_worker11, edit_designation11, edit_lenght_of_service11, edit_working_hours11, edit_rest_time11,
                        edit_attend_card11, edit_over_time_rate11, edit_salary_per_day11, edit_date_of_payment11, edit_bonus11);
                workerJson(strBioTemplate[11], edit_name_of_worker12, edit_designation12, edit_lenght_of_service12, edit_working_hours12, edit_rest_time12,
                        edit_attend_card12, edit_over_time_rate12, edit_salary_per_day12, edit_date_of_payment12, edit_bonus12);
                workerJson(strBioTemplate[12], edit_name_of_worker13, edit_designation13, edit_lenght_of_service13, edit_working_hours13, edit_rest_time13,
                        edit_attend_card13, edit_over_time_rate13, edit_salary_per_day13, edit_date_of_payment13, edit_bonus13);
                workerJson(strBioTemplate[13], edit_name_of_worker14, edit_designation14, edit_lenght_of_service14, edit_working_hours14, edit_rest_time14,
                        edit_attend_card14, edit_over_time_rate14, edit_salary_per_day14, edit_date_of_payment14, edit_bonus14);
                workerJson(strBioTemplate[14], edit_name_of_worker15, edit_designation15, edit_lenght_of_service15, edit_working_hours15, edit_rest_time15,
                        edit_attend_card15, edit_over_time_rate15, edit_salary_per_day15, edit_date_of_payment15, edit_bonus15);

                break;

            case 16:
                workerJson(strBioTemplate[0], edit_name_of_worker1, edit_designation1, edit_lenght_of_service1, edit_working_hours1, edit_rest_time1,
                        edit_attend_card1, edit_over_time_rate1, edit_salary_per_day1, edit_date_of_payment1, edit_bonus1);
                workerJson(strBioTemplate[1], edit_name_of_worker2, edit_designation2, edit_lenght_of_service2, edit_working_hours2, edit_rest_time2,
                        edit_attend_card2, edit_over_time_rate2, edit_salary_per_day2, edit_date_of_payment2, edit_bonus2);
                workerJson(strBioTemplate[2], edit_name_of_worker3, edit_designation3, edit_lenght_of_service3, edit_working_hours3, edit_rest_time3,
                        edit_attend_card3, edit_over_time_rate3, edit_salary_per_day3, edit_date_of_payment3, edit_bonus3);
                workerJson(strBioTemplate[3], edit_name_of_worker4, edit_designation4, edit_lenght_of_service4, edit_working_hours4, edit_rest_time4,
                        edit_attend_card4, edit_over_time_rate4, edit_salary_per_day4, edit_date_of_payment4, edit_bonus4);
                workerJson(strBioTemplate[4], edit_name_of_worker5, edit_designation5, edit_lenght_of_service5, edit_working_hours5, edit_rest_time5,
                        edit_attend_card5, edit_over_time_rate5, edit_salary_per_day5, edit_date_of_payment5, edit_bonus5);
                workerJson(strBioTemplate[5], edit_name_of_worker6, edit_designation6, edit_lenght_of_service6, edit_working_hours6, edit_rest_time6,
                        edit_attend_card6, edit_over_time_rate6, edit_salary_per_day6, edit_date_of_payment6, edit_bonus6);
                workerJson(strBioTemplate[6], edit_name_of_worker7, edit_designation7, edit_lenght_of_service7, edit_working_hours7, edit_rest_time7,
                        edit_attend_card7, edit_over_time_rate7, edit_salary_per_day7, edit_date_of_payment7, edit_bonus7);
                workerJson(strBioTemplate[7], edit_name_of_worker8, edit_designation8, edit_lenght_of_service8, edit_working_hours8, edit_rest_time8,
                        edit_attend_card8, edit_over_time_rate8, edit_salary_per_day8, edit_date_of_payment8, edit_bonus8);
                workerJson(strBioTemplate[8], edit_name_of_worker9, edit_designation9, edit_lenght_of_service9, edit_working_hours9, edit_rest_time9,
                        edit_attend_card9, edit_over_time_rate9, edit_salary_per_day9, edit_date_of_payment9, edit_bonus9);
                workerJson(strBioTemplate[9], edit_name_of_worker10, edit_designation10, edit_lenght_of_service10, edit_working_hours10, edit_rest_time10,
                        edit_attend_card10, edit_over_time_rate10, edit_salary_per_day10, edit_date_of_payment10, edit_bonus10);
                workerJson(strBioTemplate[10], edit_name_of_worker11, edit_designation11, edit_lenght_of_service11, edit_working_hours11, edit_rest_time11,
                        edit_attend_card11, edit_over_time_rate11, edit_salary_per_day11, edit_date_of_payment11, edit_bonus11);
                workerJson(strBioTemplate[11], edit_name_of_worker12, edit_designation12, edit_lenght_of_service12, edit_working_hours12, edit_rest_time12,
                        edit_attend_card12, edit_over_time_rate12, edit_salary_per_day12, edit_date_of_payment12, edit_bonus12);
                workerJson(strBioTemplate[12], edit_name_of_worker13, edit_designation13, edit_lenght_of_service13, edit_working_hours13, edit_rest_time13,
                        edit_attend_card13, edit_over_time_rate13, edit_salary_per_day13, edit_date_of_payment13, edit_bonus13);
                workerJson(strBioTemplate[13], edit_name_of_worker14, edit_designation14, edit_lenght_of_service14, edit_working_hours14, edit_rest_time14,
                        edit_attend_card14, edit_over_time_rate14, edit_salary_per_day14, edit_date_of_payment14, edit_bonus14);
                workerJson(strBioTemplate[14], edit_name_of_worker15, edit_designation15, edit_lenght_of_service15, edit_working_hours15, edit_rest_time15,
                        edit_attend_card15, edit_over_time_rate15, edit_salary_per_day15, edit_date_of_payment15, edit_bonus15);
                workerJson(strBioTemplate[15], edit_name_of_worker16, edit_designation16, edit_lenght_of_service16, edit_working_hours16, edit_rest_time16,
                        edit_attend_card16, edit_over_time_rate16, edit_salary_per_day16, edit_date_of_payment16, edit_bonus16);

                break;

            case 17:
                workerJson(strBioTemplate[0], edit_name_of_worker1, edit_designation1, edit_lenght_of_service1, edit_working_hours1, edit_rest_time1,
                        edit_attend_card1, edit_over_time_rate1, edit_salary_per_day1, edit_date_of_payment1, edit_bonus1);
                workerJson(strBioTemplate[1], edit_name_of_worker2, edit_designation2, edit_lenght_of_service2, edit_working_hours2, edit_rest_time2,
                        edit_attend_card2, edit_over_time_rate2, edit_salary_per_day2, edit_date_of_payment2, edit_bonus2);
                workerJson(strBioTemplate[2], edit_name_of_worker3, edit_designation3, edit_lenght_of_service3, edit_working_hours3, edit_rest_time3,
                        edit_attend_card3, edit_over_time_rate3, edit_salary_per_day3, edit_date_of_payment3, edit_bonus3);
                workerJson(strBioTemplate[3], edit_name_of_worker4, edit_designation4, edit_lenght_of_service4, edit_working_hours4, edit_rest_time4,
                        edit_attend_card4, edit_over_time_rate4, edit_salary_per_day4, edit_date_of_payment4, edit_bonus4);
                workerJson(strBioTemplate[4], edit_name_of_worker5, edit_designation5, edit_lenght_of_service5, edit_working_hours5, edit_rest_time5,
                        edit_attend_card5, edit_over_time_rate5, edit_salary_per_day5, edit_date_of_payment5, edit_bonus5);
                workerJson(strBioTemplate[5], edit_name_of_worker6, edit_designation6, edit_lenght_of_service6, edit_working_hours6, edit_rest_time6,
                        edit_attend_card6, edit_over_time_rate6, edit_salary_per_day6, edit_date_of_payment6, edit_bonus6);
                workerJson(strBioTemplate[6], edit_name_of_worker7, edit_designation7, edit_lenght_of_service7, edit_working_hours7, edit_rest_time7,
                        edit_attend_card7, edit_over_time_rate7, edit_salary_per_day7, edit_date_of_payment7, edit_bonus7);
                workerJson(strBioTemplate[7], edit_name_of_worker8, edit_designation8, edit_lenght_of_service8, edit_working_hours8, edit_rest_time8,
                        edit_attend_card8, edit_over_time_rate8, edit_salary_per_day8, edit_date_of_payment8, edit_bonus8);
                workerJson(strBioTemplate[8], edit_name_of_worker9, edit_designation9, edit_lenght_of_service9, edit_working_hours9, edit_rest_time9,
                        edit_attend_card9, edit_over_time_rate9, edit_salary_per_day9, edit_date_of_payment9, edit_bonus9);
                workerJson(strBioTemplate[9], edit_name_of_worker10, edit_designation10, edit_lenght_of_service10, edit_working_hours10, edit_rest_time10,
                        edit_attend_card10, edit_over_time_rate10, edit_salary_per_day10, edit_date_of_payment10, edit_bonus10);
                workerJson(strBioTemplate[10], edit_name_of_worker11, edit_designation11, edit_lenght_of_service11, edit_working_hours11, edit_rest_time11,
                        edit_attend_card11, edit_over_time_rate11, edit_salary_per_day11, edit_date_of_payment11, edit_bonus11);
                workerJson(strBioTemplate[11], edit_name_of_worker12, edit_designation12, edit_lenght_of_service12, edit_working_hours12, edit_rest_time12,
                        edit_attend_card12, edit_over_time_rate12, edit_salary_per_day12, edit_date_of_payment12, edit_bonus12);
                workerJson(strBioTemplate[12], edit_name_of_worker13, edit_designation13, edit_lenght_of_service13, edit_working_hours13, edit_rest_time13,
                        edit_attend_card13, edit_over_time_rate13, edit_salary_per_day13, edit_date_of_payment13, edit_bonus13);
                workerJson(strBioTemplate[13], edit_name_of_worker14, edit_designation14, edit_lenght_of_service14, edit_working_hours14, edit_rest_time14,
                        edit_attend_card14, edit_over_time_rate14, edit_salary_per_day14, edit_date_of_payment14, edit_bonus14);
                workerJson(strBioTemplate[14], edit_name_of_worker15, edit_designation15, edit_lenght_of_service15, edit_working_hours15, edit_rest_time15,
                        edit_attend_card15, edit_over_time_rate15, edit_salary_per_day15, edit_date_of_payment15, edit_bonus15);
                workerJson(strBioTemplate[15], edit_name_of_worker16, edit_designation16, edit_lenght_of_service16, edit_working_hours16, edit_rest_time16,
                        edit_attend_card16, edit_over_time_rate16, edit_salary_per_day16, edit_date_of_payment16, edit_bonus16);
                workerJson(strBioTemplate[16], edit_name_of_worker17, edit_designation17, edit_lenght_of_service17, edit_working_hours17, edit_rest_time17,
                        edit_attend_card17, edit_over_time_rate17, edit_salary_per_day17, edit_date_of_payment17, edit_bonus17);

                break;

            default:
                break;
        }
    }

    private void workerJson(String template, EditText... editTexts) {
        JSONObject json = new JSONObject();

        try {
            jarr = jsonWorker
                    .getJSONArray("objInspectedEmpDetails");
        } catch (JSONException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        try {

            json.put("Name", editTexts[0].getText().toString()
                    .trim());
            json.put("Designation", editTexts[1].getText().toString()
                    .trim());
            json.put("LengthOfService", editTexts[2].getText().toString()
                    .trim());
            json.put("WorkingHr", editTexts[3].getText().toString()
                    .trim());
            json.put("RestHr", editTexts[4].getText().toString()
                    .trim());
            json.put("AttendCard", editTexts[5].getText().toString()
                    .trim());
            json.put("OverTimeRate", editTexts[6].getText().toString()
                    .trim());
            json.put("SalayPerDay", editTexts[7].getText().toString()
                    .trim());
            json.put("DateOfPayment", editTexts[8].getText().toString()
                    .trim());
            json.put("Bonus", editTexts[9].getText().toString()
                    .trim());
            json.put("EmployeeWeeklyOff", "Monday");
            json.put("LeaveWithWages", "1");

            jarr.put(json);

            jsonWorker.put("objInspectedEmpDetails", jarr);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private boolean validation() {

        if (name_of_establishment.getText().toString().equalsIgnoreCase("")) {
            Utilities.showMessage("Enter Name of Establishment", context);
            name_of_establishment.setError("Enter Value");
            return false;
        }
        if (address.getText().toString().equalsIgnoreCase("")) {
            Utilities.showMessage("Enter Address", context);
            address.setError("Enter Value");
            return false;
        }
        if (name_of_employer.getText().toString().equalsIgnoreCase("")) {
            Utilities.showMessage("Enter Name of Employer", context);
            name_of_employer.setError("Enter Value");
            return false;
        }

        if (male.getText().toString().equalsIgnoreCase("")) {
            Utilities.showMessage("Enter Male Employees", context);
            return false;
        }

        if (female.getText().toString().equalsIgnoreCase("")) {
            Utilities.showMessage("Enter female Employees", context);
            return false;
        }

        if (transgender.getText().toString().equalsIgnoreCase("")) {
            Utilities.showMessage("Enter Transgender Employees", context);
            return false;
        }

        if (!email.getText().toString().equalsIgnoreCase("")) {
            if (TextUtils.isEmpty(email.getText().toString())) {
                return false;
            } else {
                if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email.getText().toString()).matches()) {
                    Utilities.showMessage("Enter Valid Email-id", context);
                    return false;
                }
            }
        }

        if(!mobile_no.getText().toString().equalsIgnoreCase("")){
            if((mobile_no.getText().length() != 10)){
                Utilities.showMessage("Enter Valid Mobile number", context);
                mobile_no.setError("10 digit mobile number");
                return  false;
            }
        }

        if (registration.getText().toString().equalsIgnoreCase("")) {
            Utilities.showMessage("Enter Registration under", context);
            return false;
        }

        if (schedule_empl.getText().toString().equalsIgnoreCase("")) {
            Utilities.showMessage("Enter Schedule Employment", context);
            return false;
        }

        if (working_hours.getText().toString().equalsIgnoreCase("")) {
            Utilities.showMessage("Enter Working hours", context);
            return false;
        }

        if (date.getText().toString().equalsIgnoreCase("")) {
            Utilities.showMessage("Enter Date of inspection", context);
            date.setError("Enter Value");
            return false;
        }

        if (spn_weekly_off.getSelectedItem().toString().equalsIgnoreCase("Select")) {
            Utilities.showMessage("Select Weekly Off", context);
            return false;
        }

        if (declaration_designation.getText().toString().equalsIgnoreCase("")) {
            Utilities.showMessage("Enter Designation", context);
            declaration_designation.setError("Enter Value");
            return false;
        }
        if (is_present.getText().toString().equalsIgnoreCase("")) {
            Utilities.showMessage("Enter Value", context);
            is_present.setError("Enter Value");
            return false;
        }

        return true;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
