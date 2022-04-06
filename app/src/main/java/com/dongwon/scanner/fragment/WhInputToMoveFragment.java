package com.dongwon.scanner.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.dongwon.scanner.R;
import com.google.android.material.tabs.TabLayout;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link WhInputToMoveFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WhInputToMoveFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public WhInputToMoveFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static WhInputToMoveFragment newInstance(String param1, String param2) {
        WhInputToMoveFragment fragment = new WhInputToMoveFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

       // pager2 = getView().findViewById(R.id.viewPager);

       /* FragmentManager fm = getActivity().getSupportFragmentManager();
        adapter= new WhInputToMoveFragmentAdapter(fm,getLifecycle());
        pager2.setAdapter(adapter);*/

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        getActivity().setTitle("WH Input - Na odvoz");

        View view = inflater.inflate(R.layout.fragment_wh_input_to_move, container, false);
       /* tabLayout = view.findViewById(R.id.tabLayout);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {

            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                loadData();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });*/
        // Inflate the layout for this fragment
        WebView webview = view.findViewById(R.id.webView);
        webview.loadUrl("https://portal.dwmic.cz/LogisticVisualisation/ProductionToExpeditionForklift");

        return view;
    }
    private void loadData() {

    }
}