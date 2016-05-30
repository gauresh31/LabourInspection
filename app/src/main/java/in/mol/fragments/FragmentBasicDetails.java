package in.mol.fragments;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Xml;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
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

import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import in.mol.database.DatabaseHelper;
import in.mol.labourinspection.MainActivity;
import in.mol.labourinspection.R;
import in.mol.models.SpinnerObject;
import in.mol.models.UserSessionManager;
import in.mol.models.Utilities;

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
    private static final String ARG_PARAM1 = "acts";
    private static final String ARG_PARAM2 = "act_ID";
    private static final String ARG_PARAM3 = "act_NAME";
    private static final String ARG_PARAM4 = "User_NAME";

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
    private EditText name_of_establishment, address, address_site, name_of_employer, male, female, total,
            registration, schedule_empl, working_hours, weekly_off, no_of_contractors_count, representative_of_principle,
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
            edit_attend_card13, edit_over_time_rate13, edit_salary_per_day13, edit_date_of_payment13, edit_bonus13;

    static EditText date;

    private ImageView biometric_worker_1, biometric_worker_2, biometric_worker_3, biometric_worker_4, biometric_worker_5,
            biometric_worker_6, biometric_worker_7, biometric_worker_8, biometric_worker_9, biometric_worker_10,
            biometric_worker_11, biometric_worker_12, biometric_worker_13;
    private TextView tv_Declaration;
    private RelativeLayout rel_main;
    private LinearLayout ll1, ll2, ll3, ll4, ll5, ll6, ll7, ll8, ll9, ll10;
    private LinearLayout llw1, llw2, llw3, llw4, llw5, llw6, llw7, llw8, llw9, llw10, llw11, llw12, llw13;
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
    String licence_no, inspection_no, actId, actNAME, user_name;

    public FragmentBasicDetails() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param acts Parameter 1.
     * @return A new instance of fragment FragmentBasicDetails.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentBasicDetails newInstance(MainActivity activityMain, Context con, ViewPager vPager,
                                                   String acts, String actID, String actName, String userName) {
        FragmentBasicDetails fragment = new FragmentBasicDetails();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, acts);
        args.putString(ARG_PARAM2, actID);
        args.putString(ARG_PARAM3, actName);
        args.putString(ARG_PARAM4, userName);
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
            actList = getArguments().getString(ARG_PARAM1);
            actId = getArguments().getString(ARG_PARAM2);
            actNAME = getArguments().getString(ARG_PARAM3);
            user_name = getArguments().getString(ARG_PARAM4);
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

        rel_main = (RelativeLayout) view.findViewById(R.id.rel_basic_details);
        scroll_list = (ScrollView) view.findViewById(R.id.scroll_list);
        tv_Declaration = (TextView) view.findViewById(R.id.tv_decalration);
        tvHeader = (TextView) view.findViewById(R.id.tv_head);
        spn_act = (Spinner) view.findViewById(R.id.spn_act);
        submit = (Button) view.findViewById(R.id.btn_submit);
        name_of_establishment = (EditText) view.findViewById(R.id.edit_name_of_establishment);
        address = (EditText) view.findViewById(R.id.edit_address);
        address_site = (EditText) view.findViewById(R.id.edit_address_of_site);
        name_of_employer = (EditText) view.findViewById(R.id.edit_name_of_employer);
        male = (EditText) view.findViewById(R.id.edit_male);
        female = (EditText) view.findViewById(R.id.edit_female);
        total = (EditText) view.findViewById(R.id.edit_total);
        registration = (EditText) view.findViewById(R.id.edit_registration_under);
        schedule_empl = (EditText) view.findViewById(R.id.edit_schedule_employment);
        working_hours = (EditText) view.findViewById(R.id.edit_working_hours);
        weekly_off = (EditText) view.findViewById(R.id.edit_weekly_off);
        date = (EditText) view.findViewById(R.id.edit_date_of_inspection);
        representative_of_principle = (EditText) view.findViewById(R.id.edit_representative_of_principle);
        direct_worker = (EditText) view.findViewById(R.id.edit_direct_employees);
        contract_worker = (EditText) view.findViewById(R.id.edit_contract_worker);
        year_of_starting = (EditText) view.findViewById(R.id.edit_year_of_starting);
        accounting_year = (EditText) view.findViewById(R.id.edit_accouting_year);
        no_of_workers = (EditText) view.findViewById(R.id.edit_no_of_workers);
        declaration_designation = (EditText) view.findViewById(R.id.edit_declar_designation);
        is_present = (EditText) view.findViewById(R.id.edit_is_present);

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
    }

    private void setDefault() {
        Utilities.invisibleAll(ll1, ll2, ll3, ll4, ll5, ll6, ll7, ll8, ll9, ll10);
        Utilities.invisibleAll(llw1, llw2, llw3, llw4, llw5, llw6, llw7, llw8, llw9, llw10, llw11, llw12, llw13);
//        scroll_list.setVisibility(View.GONE);

        licence_no = session.getLicenseNo();
        inspection_no = session.getInspectionNo();

        tvHeader.setText(actNAME);

        String basicData = session.getInfoBasedOnLicense();

        JSONObject jsonObject;
        try {
            jsonObject = new JSONObject(basicData);
            JSONObject json = jsonObject.optJSONObject("objLabourInspectionSchema");

            name_of_establishment.setText(json.getString("Institution_Name"));
            address.setText(json.getString("Institution_Addr"));
            name_of_employer.setText(json.getString("Owner_Name"));
//            no_of_workers.setText(json.getString("TotalWorkers"));
            direct_worker.setText(json.getString("TotalDirectEmp"));
            contract_worker.setText(json.getString("TotalContractEmp"));

            male.setText("0");
            female.setText("0");


        } catch (JSONException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (actId.equalsIgnoreCase("102") || actId.equalsIgnoreCase("103") || actId.equalsIgnoreCase("104")) {
            ll_no_of_workers.setVisibility(View.GONE);
            hs_worker_info.setVisibility(View.GONE);
        } else if (actId.equalsIgnoreCase("105")) {
            ll_no_of_workers.setVisibility(View.GONE);
            hs_worker_info.setVisibility(View.GONE);
            ll_working_hours.setVisibility(View.GONE);
        } else if (actId.equalsIgnoreCase("107")) {
            ll_no_of_workers.setVisibility(View.GONE);
            hs_worker_info.setVisibility(View.GONE);
            ll_employees.setVisibility(View.GONE);
            ll_working_hours.setVisibility(View.GONE);
            ll_address_site_1970.setVisibility(View.VISIBLE);
            ll_employees_1970.setVisibility(View.VISIBLE);
            ll_representative.setVisibility(View.VISIBLE);
            ll_no_of_contractor.setVisibility(View.VISIBLE);

        } else if (actId.equalsIgnoreCase("108")) {
            ll_no_of_workers.setVisibility(View.GONE);
            hs_worker_info.setVisibility(View.GONE);
            ll_schedule_emp.setVisibility(View.GONE);
            ll_year_of_startng.setVisibility(View.VISIBLE);
            ll_acc_year.setVisibility(View.VISIBLE);
        }

    }

    private void setEventListeners() {
        date.setOnClickListener(this);
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
//        spn_act.setOnItemSelectedListener(this);

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
                                    ll7, ll8, ll9);
                            break;
                        case 1:
                            Utilities.invisibleAll(ll1, ll2, ll3, ll4, ll5, ll6,
                                    ll7, ll8, ll9);
                            Utilities.visibleAll(ll1);
                            break;

                        case 2:
                            Utilities.invisibleAll(ll1, ll2, ll3, ll4, ll5, ll6,
                                    ll7, ll8, ll9);
                            Utilities.visibleAll(ll1, ll2);
                            break;

                        case 3:
                            Utilities.invisibleAll(ll1, ll2, ll3, ll4, ll5, ll6,
                                    ll7, ll8, ll9);
                            Utilities.visibleAll(ll1, ll2, ll3);
                            break;

                        case 4:
                            Utilities.invisibleAll(ll1, ll2, ll3, ll4, ll5, ll6,
                                    ll7, ll8, ll9);
                            Utilities.visibleAll(ll1, ll2, ll3, ll4);
                            break;

                        case 5:
                            Utilities.invisibleAll(ll1, ll2, ll3, ll4, ll5, ll6,
                                    ll7, ll8, ll9);
                            Utilities.visibleAll(ll1, ll2, ll3, ll4, ll5);
                            break;

                        case 6:
                            Utilities.invisibleAll(ll1, ll2, ll3, ll4, ll5, ll6,
                                    ll7, ll8, ll9);
                            Utilities.visibleAll(ll1, ll2, ll3, ll4, ll5, ll6);
                            break;

                        case 7:
                            Utilities.invisibleAll(ll1, ll2, ll3, ll4, ll5, ll6,
                                    ll7, ll8, ll9);
                            Utilities.visibleAll(ll1, ll2, ll3, ll4, ll5, ll6, ll7);
                            break;

                        case 8:
                            Utilities.invisibleAll(ll1, ll2, ll3, ll4, ll5, ll6,
                                    ll7, ll8, ll9);
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
                            Utilities.invisibleAll(llw1, llw2, llw3, llw4, llw5, llw6, llw7, llw8, llw9, llw10);
                            break;
                        case 1:
                            Utilities.visibleAll(llw1);
                            break;

                        case 2:
                            Utilities.visibleAll(llw1, llw2);
                            break;

                        case 3:
                            Utilities.visibleAll(llw1, llw2, llw3);
                            break;

                        case 4:
                            Utilities.visibleAll(llw1, llw2, llw3, llw4);
                            break;

                        case 5:
                            Utilities.visibleAll(llw1, llw2, llw3, llw4, llw5);
                            break;

                        case 6:
                            Utilities.visibleAll(llw1, llw2, llw3, llw4, llw5, llw6);
                            break;

                        case 7:
                            Utilities.visibleAll(llw1, llw2, llw3, llw4, llw5, llw6, llw7);
                            break;

                        case 8:
                            Utilities.visibleAll(llw1, llw2, llw3, llw4, llw5, llw6, llw7,
                                    llw8);
                            break;

                        case 9:
                            Utilities.visibleAll(llw1, llw2, llw3, llw4, llw5, llw6, llw7,
                                    llw8, llw9);
                            break;

                        case 10:
                            Utilities.visibleAll(llw1, llw2, llw3, llw4, llw5, llw6, llw7,
                                    llw8, llw9, llw10);
                            break;

                        case 11:
                            Utilities.visibleAll(llw1, llw2, llw3, llw4, llw5, llw6, llw7,
                                    llw8, llw9, llw10, llw11);
                            break;

                        case 12:
                            Utilities.visibleAll(llw1, llw2, llw3, llw4, llw5, llw6, llw7,
                                    llw8, llw9, llw10, llw11, llw12);
                            break;

                        case 13:
                            Utilities.visibleAll(llw1, llw2, llw3, llw4, llw5, llw6, llw7,
                                    llw8, llw9, llw10, llw11, llw12, llw13);
                            break;

                        default:
                            break;
                    }
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
//                if (validation()) {
                if (count > 0) {
                    createContractorJson();
                }
                if (worker_count > 0) {
                    createWorkerJson();
                }
                createJson();
//                }
                break;

            case R.id.edit_date_of_inspection:
                DialogFragment newFragment = new DatePickerFragment();
                newFragment.show(getFragmentManager(), "datePicker");
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

    private String writeXml() {
        XmlSerializer serializer = Xml.newSerializer();
        StringWriter writer = new StringWriter();
        try {
            serializer.setOutput(writer);
            serializer.startDocument("UTF-8", true);
            serializer.startTag("", "message");

            serializer.startTag("", "name_of_establishment");
            serializer.text(name_of_establishment.getText().toString());
            serializer.endTag("", "name_of_establishment");

            serializer.startTag("", "address");
            serializer.text(address.getText().toString());
            serializer.endTag("", "address");

            serializer.startTag("", "name_of_employer");
            serializer.text(name_of_employer.getText().toString());
            serializer.endTag("", "name_of_employer");

            serializer.startTag("", "address_site");
            serializer.text(address_site.getText().toString());
            serializer.endTag("", "address_site");

            serializer.startTag("", "male");
            serializer.text(male.getText().toString());
            serializer.endTag("", "male");

            serializer.startTag("", "female");
            serializer.text(female.getText().toString());
            serializer.endTag("", "female");

            serializer.endTag("", "message");
            serializer.endDocument();
            return writer.toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void createJson() {

//        String xml = writeXml();

        JSONObject jsonn = new JSONObject();
        JSONArray jarr = new JSONArray();
        try {

            jsonn.put("license_no", licence_no);
            jsonn.put("inspection_no", inspection_no);

            jsonn.put("name_of_establishment", name_of_establishment.getText().toString());
            jsonn.put("address", address.getText().toString());
            jsonn.put("address_site", address_site.getText().toString());
            jsonn.put("name_of_employer", name_of_employer.getText().toString());
            jsonn.put("male", male.getText().toString());
            jsonn.put("female", female.getText().toString());
            jsonn.put("total", total.getText().toString());
            jsonn.put("registration", registration.getText().toString());
            jsonn.put("schedule_empl", schedule_empl.getText().toString());
            jsonn.put("working_hours", working_hours.getText().toString());
            jsonn.put("weekly_off", weekly_off.getText().toString());
            jsonn.put("date", date.getText().toString());
            jsonn.put("representative_of_principle", representative_of_principle.getText().toString());
            jsonn.put("direct_worker", direct_worker.getText().toString());
            jsonn.put("contract_worker", contract_worker.getText().toString());
            jsonn.put("year_of_starting", year_of_starting.getText().toString());
            jsonn.put("accounting_year", accounting_year.getText().toString());
            jsonn.put("declaration_designation", declaration_designation.getText().toString());
            jsonn.put("is_present", is_present.getText().toString());

            jarr.put(jsonn);
            dataToDatabase.put("basic_details_list", jarr);

            session.createBasicInfoSession(jsonn.toString());

        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println(dataToDatabase.toString());

//        long rowId = dbHelper.insertBasicDetails(user_name, licence_no, inspection_no, dataToDatabase.toString());

//        if (rowId > 0) {
        clear();
        Utilities.showMessage("Data Saved Successfully", context);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        viewPager.setCurrentItem(1);
//        } else {
//            Utilities.showMessage("Data not Saved", context);
//        }
    }

    private void clear() {
        name_of_establishment.setText("");
        address.setText("");
        address_site.setText("");
        name_of_employer.setText("");
        male.setText("");
        female.setText("");
        total.setText("");
        registration.setText("");
        schedule_empl.setText("");
        working_hours.setText("");
        weekly_off.setText("");
        date.setText("");
        representative_of_principle.setText("");
        direct_worker.setText("");
        contract_worker.setText("");
        year_of_starting.setText("");
        accounting_year.setText("");
        declaration_designation.setText("");
        is_present.setText("");

        no_of_workers.setText("0");
        no_of_contractors_count.setText("0");

        name_of_establishment.setError(null);
        address.setError(null);
        address_site.setError(null);
        name_of_employer.setError(null);
        male.setError(null);
        female.setError(null);
        total.setError(null);
        registration.setError(null);
        schedule_empl.setError(null);
        working_hours.setError(null);
        weekly_off.setError(null);
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
                    .getJSONArray("labour_contractor_list");
        } catch (JSONException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }

        try {
            json.put("license_no", licence_no);
            json.put("inspection_no", inspection_no);

            json.put("Name_Of_Contractor", editTexts[1].getText().toString()
                    .trim());
            json.put("Nature_Of_Work", editTexts[2].getText().toString()
                    .trim());
            json.put("Number_Of_Workers", editTexts[3].getText().toString()
                    .trim());
            json.put("Date_Of_Commencement", editTexts[4].getText().toString()
                    .trim());
            jarr.put(json);

            dataToDatabase.put("labour_contractor_list", jarr);

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

            default:
                break;
        }
    }

    private void workerJson(String template, EditText... editTexts) {
        JSONObject json = new JSONObject();
        JSONArray jarr = new JSONArray();
        try {
            jarr = dataToDatabase
                    .getJSONArray("labour_worker_list");
        } catch (JSONException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        try {

            json.put("license_no", licence_no);
            json.put("inspection_no", inspection_no);

            json.put("worker_name", editTexts[0].getText().toString()
                    .trim());
            json.put("worker_designation", editTexts[1].getText().toString()
                    .trim());
            json.put("worker_length_of_service", editTexts[2].getText().toString()
                    .trim());
            json.put("worker_working_hours", editTexts[3].getText().toString()
                    .trim());
            json.put("worker_rest_time", editTexts[4].getText().toString()
                    .trim());
            json.put("worker_attend_card", editTexts[5].getText().toString()
                    .trim());
            json.put("worker_over_time", editTexts[6].getText().toString()
                    .trim());
            json.put("worker_salary_per_day", editTexts[7].getText().toString()
                    .trim());
            json.put("worker_date_of_payment", editTexts[8].getText().toString()
                    .trim());
            json.put("worker_bonus", editTexts[9].getText().toString()
                    .trim());

            jarr.put(json);

            dataToDatabase.put("labour_worker_list", jarr);
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

        if (ll_employees.getVisibility() == View.VISIBLE) {
            if (male.getText().toString().equalsIgnoreCase("")) {
                Utilities.showMessage("Enter Male Employees", context);
                return false;
            }
            if (female.getText().toString().equalsIgnoreCase("")) {
                Utilities.showMessage("Enter female Employees", context);
                return false;
            }
        }
        if (registration.getText().toString().equalsIgnoreCase("")) {
            Utilities.showMessage("Enter Registration", context);
            registration.setError("Enter Value");
            return false;
        }
        if (ll_schedule_emp.getVisibility() == View.VISIBLE) {
            if (schedule_empl.getText().toString().equalsIgnoreCase("")) {
                Utilities.showMessage("Enter Schedule Employment", context);
                return false;
            }
        }
        if (ll_working_hours.getVisibility() == View.VISIBLE) {
            if (working_hours.getText().toString().equalsIgnoreCase("")) {
                Utilities.showMessage("Enter Working hours", context);
                return false;
            }
            if (weekly_off.getText().toString().equalsIgnoreCase("")) {
                Utilities.showMessage("Enter Weekly off", context);
                return false;
            }
        }
        if (date.getText().toString().equalsIgnoreCase("")) {
            Utilities.showMessage("Enter Date of inspection", context);
            date.setError("Enter Value");
            return false;
        }

        if (ll_address_site_1970.getVisibility() == View.VISIBLE) {
            if (address_site.getText().toString().equalsIgnoreCase("")) {
                Utilities.showMessage("Enter Address of site", context);
                return false;
            }
        }
        if (ll_employees_1970.getVisibility() == View.VISIBLE) {
            if (direct_worker.getText().toString().equalsIgnoreCase("")) {
                Utilities.showMessage("Enter Direct Worker Count", context);
                return false;
            }
            if (contract_worker.getText().toString().equalsIgnoreCase("")) {
                Utilities.showMessage("Enter Contract Worker Count", context);
                return false;
            }
            if (representative_of_principle.getText().toString().equalsIgnoreCase("")) {
                Utilities.showMessage("Enter Representative", context);
                return false;
            }
        }

        if (actId.equalsIgnoreCase("108")) {
            if (year_of_starting.getText().toString().equalsIgnoreCase("")) {
                Utilities.showMessage("Enter year of starting", context);
                return false;
            }
            if (accounting_year.getText().toString().equalsIgnoreCase("")) {
                Utilities.showMessage("Enter Accounting year", context);
                return false;
            }
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
