package com.elasticbeanstalk.laciecool.first;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;


/**
 * A placeholder fragment containing a simple view.
 */
public class RandomTwoActivityFragment extends Fragment {

    private static final String userAns = "userAns";
    private static final String correctAns = "correctAns";
    private static final String qns = "qns;";

    private OnFragmentInteractionListener mListener;

    private String ua;
    private String ca;
    private String q;
    private int pi;

    public static RandomTwoActivityFragment newInstance(String qns, String userAns, String correctAns, int pageIndex) {
        RandomTwoActivityFragment fragment = new RandomTwoActivityFragment();
        Bundle b = new Bundle(1);
        b.putString(RandomTwoActivityFragment.userAns, userAns);
        b.putString(RandomTwoActivityFragment.correctAns, correctAns);
        b.putString(RandomTwoActivityFragment.qns, qns);
        b.putInt("index", pageIndex);

        fragment.setArguments(b);
        return fragment;
    }

    public RandomTwoActivityFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(getArguments()!=null) {
            ua = getArguments().getString(RandomTwoActivityFragment.userAns);
            ca = getArguments().getString(RandomTwoActivityFragment.correctAns);
            q = getArguments().getString(RandomTwoActivityFragment.qns);
            pi = getArguments().getInt("index");
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_random_two, container, false);

        //populate user ans and right ans;
        TextView resultQns = (TextView) v.findViewById(R.id.resultQns);
        resultQns.setText(q);
        TextView userAnsText = (TextView) v.findViewById(R.id.userAns);

        if(ua == null || ca == null || ua.equals(null) || ca.equals(null)){
            Intent i = new Intent(getActivity(), EndActivity.class);
            startActivity(i);
            return null;
        }

        if(ua.equals("1")) {
            userAnsText.setText("True");
        } else if(ua.equals("99")) {
            userAnsText.setText("No response");
        } else {
            userAnsText.setText("False");
        }
        TextView correctAnsText = (TextView) v.findViewById(R.id.correctAns);

        if(ca.equals("1")) {
            correctAnsText.setText("True");
        } else if(ca.equals("99")) {
            correctAnsText.setText("No response");
        } else {
            correctAnsText.setText("False");
        }

        if(ua.equals(ca)) {
            userAnsText.setTextColor(Color.BLUE);
        } else {
            userAnsText.setTextColor(Color.RED);
        }

        Button btnN = (Button) v.findViewById(R.id.randomTwoNext);
        Button btnP = (Button) v.findViewById(R.id.randomTwoPrev);

        btnP.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                onPrev(v);
            }
        });

        btnN.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                onNext(v);
            }
        });

        return v;
    }

    public void onNext(View v) {
        //if last fragment, generate random number, start new activity.
        int childCount = ((RandomTwoActivity) getActivity()).getFragmentSize();
        if(pi == childCount - 1) {
            Intent i = new Intent(getActivity(), EndActivity.class);
            startActivity(i);
        }

        //else set the next page as current page
        else {
            ViewPager vp = (ViewPager) getActivity().findViewById(R.id.resultViewpager);
            vp.setCurrentItem(pi + 1);
        }
    }

    public void onPrev(View v) {
        ViewPager vp = (ViewPager) getActivity().findViewById(R.id.resultViewpager);
        vp.setCurrentItem(pi-1);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
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
        public void onFragmentInteraction(Uri uri);
    }
}
