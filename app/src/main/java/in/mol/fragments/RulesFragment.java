package in.mol.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ItemDecoration;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.Spinner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import in.mol.database.DatabaseHelper;
import in.mol.labourinspection.ActivityActList;
import in.mol.labourinspection.MainActivity;
import in.mol.labourinspection.R;
import in.mol.models.SpinnerObject;
import in.mol.models.UserSessionManager;
import in.mol.models.Utilities;

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
    String[] strRules;
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
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btn_submit:
                JSONObject jsonData = new JSONObject();
                try {
                    jsonData.put("licence_no", licence_no);
                    jsonData.put("inspection_no", inspection_no);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                for (int i = 0; i < strRules.length; i++) {
                    try {
                        jsonData.put("" + i, strRules[i]);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                JSONArray jsonArray = new JSONArray();
                jsonArray.put(jsonData);

                JSONArray jsonArray1 = new JSONArray();
                String basicInfo = session.getBasicDetailsInfo();
                if (basicInfo != null) {
                    jsonArray1.put(basicInfo);
                }

                try {
                    dataToDatabase.put("basic_details_list", basicInfo);
                    dataToDatabase.put("rules_list", jsonData);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                try {
                    Thread.sleep(1000);
                } catch (Exception e) {
                    e.printStackTrace();
                }
//                long result = dbHelper.insertBasicDetails(user_name, licence_no, inspection_no, dataToDatabase.toString());

//                if (result > 0) {
                    Utilities.showMessage("Data saved sucessfully", context);
//                Intent in = new Intent(context, ActivityActList.class);
//                in.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                in.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                in.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
//                startActivity(in);
                    mainActivity.finish();
//                } else {
//                    Utilities.showMessage("Data not saved", context);
//                }

            default:
                break;
        }
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
                    if (holder.rb_rules.isChecked())
                        strRules[position] = lst_rules.get(position).getActId();
                    else
                        strRules[position] = null;
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

