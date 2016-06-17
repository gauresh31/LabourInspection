package in.mol.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import in.mol.labourinspection.MainActivity;

public class FragmentRemarks extends Fragment{

    private static final String ARG_PARAM1 = "User_NAME";
    private static final String ARG_PARAM2 = "User_ID";
    private static ViewPager viewPager;
    static Context context;
    private static MainActivity mainActivity;

    public static Fragment newInstance(MainActivity mainAct, Context applicationContext,
                                       ViewPager viewPager, String username, String userId) {
        FragmentRemarks fragment = new FragmentRemarks();
        Bundle args = new Bundle();

        args.putString(ARG_PARAM1, username);
        args.putString(ARG_PARAM2, userId);
        fragment.setArguments(args);

        viewPager = viewPager;
        context = applicationContext;
        mainActivity = mainAct;

        return fragment;
    }
}
