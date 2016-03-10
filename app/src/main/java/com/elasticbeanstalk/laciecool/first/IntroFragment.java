package com.elasticbeanstalk.laciecool.first;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SurveyFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SurveyFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class IntroFragment extends Fragment {

    private static final String question = "INTRO_QNS";
    private static final String index = "index";
    private int radioChecked = 0;
    private int ind;

    private OnFragmentInteractionListener mListener;

    public static IntroFragment newInstance(String qns, int ind) {
        IntroFragment fragment = new IntroFragment();
        Bundle b = new Bundle(1);
        b.putString(question, qns);
        b.putInt(index, ind);
        fragment.setArguments(b);
        return fragment;
    }

    public IntroFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ind = getArguments().getInt(index);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        String m = getArguments().getString(question);

        boolean isEnglish = getActivity().getSharedPreferences("sessionData", Context.MODE_PRIVATE).getInt("lang", 0) == 0;
        View v;
        if(isEnglish)
            v = inflater.inflate(R.layout.fragment_intro, container, false);
        else
            v = inflater.inflate(R.layout.fragment_intro_bahasa, container, false);

        if(ind == 0) {
            v.findViewById(R.id.introPrevQnsBtn).setVisibility(View.GONE);
        }

        TextView quesn = (TextView) v.findViewById(R.id.introQns);
        quesn.setText(m);

        RadioGroup rg = (RadioGroup) v.findViewById(R.id.introRadioGrp);
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch(checkedId) {
                    case R.id.radioYes:
                        radioChecked = 0;
                        break;
                    case R.id.radioNo:
                        radioChecked = 1;
                        break;
                }
            }
        });

        return v;
    }

    public int getRadioResult() {
        return radioChecked;
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
