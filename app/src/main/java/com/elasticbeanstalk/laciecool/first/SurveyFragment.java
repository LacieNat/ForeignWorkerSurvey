package com.elasticbeanstalk.laciecool.first;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SurveyFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SurveyFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SurveyFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private int gId;
    private int qId;
    private String gName;
    private String q;
    private int numInput;
    private boolean hasOpt;
    private String optType;
    private String u;
    private ArrayList<Options> opt;
    private String r;
    private int ind;
    private int ord;

    private View vPtr;

    private static final String grpId = "grpId";
    private static final String qnsId = "qnsId";
    private static final String groupName = "groupName";
    private static final String qns = "qns";
    private static final String order = "order";
    private static final String numOfInput = "numOfInput";
    private static final String hasOptions = "hasOptions";
    private static final String optionType = "optionType";
    private static final String units = "units";
    private static final String options = "options";
    private static final String rating = "rating";

    private ArrayList<String> answers;
    private static HashMap<Integer, Integer> qnsWithPgJump = new HashMap<>();
    private boolean isEnglish;

    private OnFragmentInteractionListener mListener;

    private TextWatcher noResponseClearWatcher;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment SurveyFragment.
     */
    // TODO: Rename and change types and number of parameters
    // String grpName, String qns, int numOfInput, boolean hasOptions,
    // String units, boolean hasMulOpt, String rating, ArrayList<Options> options
    public static SurveyFragment newInstance(int grpId, int qId, String grpName, String qns, int numOfInput, boolean hasOptions, String optionType,
                                             String units, String rating, ArrayList<Options> options, int index, int ord) {
        SurveyFragment fragment = new SurveyFragment();
        Bundle b = new Bundle(1);
        b.putInt(SurveyFragment.grpId, grpId);
        b.putInt(SurveyFragment.qnsId, qId);
        b.putString(SurveyFragment.groupName, grpName);
        b.putString(SurveyFragment.qns, qns);
        b.putInt(SurveyFragment.numOfInput, numOfInput);
        b.putBoolean(SurveyFragment.hasOptions, hasOptions);
        b.putString(SurveyFragment.optionType, optionType);
        b.putString(SurveyFragment.units, units);
        b.putString(SurveyFragment.rating, rating);
        b.putInt(SurveyFragment.order, ord);
        b.putParcelableArrayList(SurveyFragment.options, options);
        b.putInt("index", index);

        fragment.setArguments(b);
        return fragment;
    }

    public SurveyFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isEnglish = getActivity().getSharedPreferences("sessionData", Context.MODE_PRIVATE).getInt("lang",0) == 0;

        if (getArguments() != null) {
            gId = getArguments().getInt(SurveyFragment.grpId);
            qId = getArguments().getInt(SurveyFragment.qnsId);
            gName = getArguments().getString(SurveyFragment.groupName);
            q = getArguments().getString(SurveyFragment.qns);
            numInput = getArguments().getInt(SurveyFragment.numOfInput);
            hasOpt = getArguments().getBoolean(SurveyFragment.hasOptions);
            optType = getArguments().getString(SurveyFragment.optionType);
            u = getArguments().getString(SurveyFragment.units);
            opt = getArguments().getParcelableArrayList(SurveyFragment.options);
            r = getArguments().getString(SurveyFragment.rating);
            ind = getArguments().getInt("index");
            ord = getArguments().getInt(SurveyFragment.order);
        }
        answers = new ArrayList<>();
        noResponseClearWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(count>0)
                    noResponseRadioGroupClearCheck();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };
        if(ind==0)
            fillQnsWithPgJump();
    }

    public void fillQnsWithPgJump() {
        // default all to no page jump first
        qnsWithPgJump.put(8, 1);
        qnsWithPgJump.put(11, 1);
        qnsWithPgJump.put(24, 1);
        qnsWithPgJump.put(34, 1);
        qnsWithPgJump.put(74, 1);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        String m = getArguments().getString(SurveyFragment.groupName);

        View v = inflater.inflate(R.layout.fragment_survey, container, false);
        vPtr = v;

        TextView title = (TextView) v.findViewById(R.id.groupName);
        TextView quesn = (TextView) v.findViewById(R.id.question);
        title.setText(m);
        quesn.setText(getArguments().getString(SurveyFragment.qns));

        if(gId == 8) {
            createContactView(v);
        }

        else if(gId == 0) {
            createLocationView(v);
        }

        else if(gId == 1) {
            createDemographicView(v);
        }

        else if(gId == 2) {
            createMigrationView(v);
        }

        else if(gId == 3) {
            createEmploymentView(v);
        }

        else if(gId == 4) {
            createCurrentEmploymentView(v);
        }

        else if(gId == 5) {
            createBargainView(v);
        }

        else if(gId == 6) {
            createExpectationView(v);
        }

        else if(gId == 7) {
            createKnowledgeView(v);
        }

        return v;
    }

    public void createContactView(View v) {
        LinearLayout l = (LinearLayout) v.findViewById(R.id.survey_fragment_layout);
        if(qId == 2) {
            l.addView(LayoutInflater.from(getActivity()).inflate(R.layout.best_time_input, null));
        } else if(qId == 3 || qId == 75 || qId == 67 || qId == 71) {
            l.addView(createPhoneNumberView());
        } else {
            if (numInput == 1) {
                l.addView(createSingleInputWithUnits());
            }

            if(hasOpt) {
                if(optType.equals("RADIO")) {
                    l.addView(createRadioGroupFromOptions());
                } else if(optType.equals("CHECKBOX")) {
                    createCheckBoxes(l);
                }
            }
        }

        if(qId == 64 || qId == 65 || qId == 68 || qId == 69) {
            l.addView(createNoResponseOption());
        }

        l.addView(createNextAndPrevBtns());
    }

    public void createLocationView(View v) {
        LinearLayout l = (LinearLayout) v.findViewById(R.id.survey_fragment_layout);

        l.addView(createRadioGroupFromOptions());
        l.addView(createNextAndPrevBtns());
    }

    public View createPhoneNumberView() {
        return LayoutInflater.from(getActivity()).inflate(R.layout.phone_number_input, null);
    }

    public void createDemographicView(View v) {
        LinearLayout l = (LinearLayout) v.findViewById(R.id.survey_fragment_layout);

        if(numInput == 1) {
            l.addView(createSingleInputWithUnits());
        }

        if(hasOpt) {
            l.addView(createRadioGroupFromOptions());
        }

        l.addView(createNoResponseOption());
        l.addView(createNextAndPrevBtns());
    }

    public void createMigrationView(View v) {
        LinearLayout l = (LinearLayout) v.findViewById(R.id.survey_fragment_layout);

        if(qId == 10 || qId == 12) {
            l.addView(createYearInput());
        }

        if(hasOpt) {
            l.addView(createRadioGroupFromOptions());
        }

        l.addView(createNoResponseOption());
        l.addView(createNextAndPrevBtns());
    }

    public void createEmploymentView(View v) {
        LinearLayout l = (LinearLayout) v.findViewById(R.id.survey_fragment_layout);

        //if question id is 13, create double edittext
        if(qId == 13) {
            String[] unitsArr = u.split("/");

            for(int i=0; i<unitsArr.length; i++) {
                LinearLayout ll = new LinearLayout(getActivity());
                ll.setOrientation(LinearLayout.HORIZONTAL);
                if(i==0) {
                    ll.setId(R.id.monthDouble);
                } else {
                    ll.setId(R.id.yearDouble);
                }

                //create input filter to limit characters to 1 per input
                InputFilter[] filters = new InputFilter[1];
                filters[0] = new InputFilter.LengthFilter(1);

                EditText e1 = new EditText(getActivity());
                e1.setWidth(70);
                e1.setFilters(filters);
                e1.setInputType(InputType.TYPE_CLASS_NUMBER);
                e1.addTextChangedListener(noResponseClearWatcher);

                EditText e2 = new EditText(getActivity());
                e2.setWidth(70);
                e2.setFilters(filters);
                e2.setInputType(InputType.TYPE_CLASS_NUMBER);
                e2.addTextChangedListener(noResponseClearWatcher);

                TextView e3 = new TextView(getActivity());
                e3.setText(unitsArr[i]);
                e3.setTextSize(20);
                e3.setTextColor(Color.BLACK);

                ll.addView(e1);
                ll.addView(e2);
                ll.addView(e3);
                l.addView(ll);
            }
        }

        else if(numInput > 0) {
            l.addView(createSingleInputWithUnits());
        }

        if(hasOpt) {
            if(optType.equals("CHECKBOX")) {
                createCheckBoxes(l);
            }

            else if(optType.equals("RADIO")) {
                l.addView(createRadioGroupFromOptions());
            }
        }

        l.addView(createNoResponseOption());
        l.addView(createNextAndPrevBtns());
    }
    public void clearMonthAndYearDouble() {
        clearAllEditTextInView((LinearLayout) getActivity().findViewById(R.id.monthDouble));
        clearAllEditTextInView((LinearLayout) getActivity().findViewById(R.id.yearDouble));
    }

    public void clearAllEditTextInView(LinearLayout v) {
        if(v!=null) {
            for (int i = 0; i < v.getChildCount(); i++) {
                if (v.getChildAt(i) instanceof EditText) {
                    EditText e = (EditText) v.getChildAt(i);
                    e.setText("");
                }
            }
        }
    }

    public void createCurrentEmploymentView(View v) {
        LinearLayout l = (LinearLayout) v.findViewById(R.id.survey_fragment_layout);

        if(numInput == 1) {
            l.addView(createSingleInputWithUnits());
        }

        else if(numInput == 2) {
            l.addView(createDoubleInputWithUnits());
        }

        else if(!r.equals(null) && r.length() > 0) {
            l.addView(createRatingView());
        }

        if(hasOpt) {
            if(optType.equals("RADIO")) {
                l.addView(createRadioGroupFromOptions());
            }

            else if(optType.equals("CHECKBOX")) {
                createCheckBoxes(l);
            }
        }

        l.addView(createNoResponseOption());
        l.addView(createNextAndPrevBtns());
    }

    public View createDoubleInputWithUnits() {
        String[] unitsArr = u.split("/");
        View v = LayoutInflater.from(getActivity()).inflate(R.layout.double_input_with_units, null);
        EditText e = (EditText) v.findViewById(R.id.input1);
        e.addTextChangedListener(noResponseClearWatcher);
        EditText e2 = (EditText) v.findViewById(R.id.input2);
        e2.addTextChangedListener(noResponseClearWatcher);

        if(unitsArr.length == 2) {
            ((TextView)v.findViewById(R.id.unit1)).setText(unitsArr[0]);
            ((TextView)v.findViewById(R.id.unit2)).setText(unitsArr[1]);
        }

        return v;
    }

    public void clearDoubleInputWithUnits() {
        View v = getActivity().findViewById(R.id.doubleInput);

        if(v!=null) {
            EditText e = (EditText) getActivity().findViewById(R.id.input1);
            e.setText("");
            EditText e2 = (EditText) getActivity().findViewById(R.id.input2);
            e2.setText("");
        }
    }

    public void createBargainView(View v) {
        LinearLayout l = (LinearLayout) v.findViewById(R.id.survey_fragment_layout);

        if(hasOpt && optType.equals("RADIO")) {
            l.addView(createRadioGroupFromOptions());
        }

        l.addView(createNoResponseOption());
        l.addView(createNextAndPrevBtns());
    }

    public void createExpectationView(View v) {
        LinearLayout l = (LinearLayout) v.findViewById(R.id.survey_fragment_layout);

        if(numInput == 1) {
            l.addView(createSingleInputWithUnits());
        }

        if(!r.equals(null) && r.length() > 0) {
            l.addView(createRatingView());
        }

        if(hasOpt) {
            if(optType.equals("DROPDOWN")) {
                l.addView(createDropdown());
            }

            else if(optType.equals("RADIO")) {
                l.addView(createRadioGroupFromOptions());
            }
        }

        if(qId == 45 || qId == 47 || qId == 49 || qId == 51 || qId == 53) {
            l.addView(createNoResponseOrDoesNotKnowOption());
        } else {
            l.addView(createNoResponseOption());
        }

        l.addView(createNextAndPrevBtns());
    }

    public void createKnowledgeView(View v) {
        LinearLayout l = (LinearLayout) v.findViewById(R.id.survey_fragment_layout);

        if(hasOpt && optType.equals("RADIO")) {
            l.addView(createRadioGroupFromOptions());
        }

        l.addView(createNoResponseOption());
        l.addView(createNextAndPrevBtns());
    }

    public View createDropdown() {
        Spinner sp = new Spinner(getActivity());
        sp.setId(R.id.spinnerDropDown);
        ArrayAdapter<String> spinnerArr = new ArrayAdapter<String>(getActivity(), R.layout.array_adapter_view);
        spinnerArr.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        for(int i=0; i<opt.size(); i++) {
            spinnerArr.add(opt.get(i).getOption());
        }

        sp.setAdapter(spinnerArr);
        return sp;
    }

    public void createCheckBoxes(LinearLayout l) {

        for(int i=0; i<opt.size(); i++) {

            CheckBox cb = new CheckBox(getActivity());
            cb.setText(opt.get(i).getOption());
            cb.setPadding(0, 15, 0, 15);
            cb.setTag(opt.get(i).getValue());
            cb.setId(opt.get(i).getValue());

            cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton btnView, boolean isChecked) {
                    // if noresponse or don't know is selected, deselect other group

                    if (isChecked) {
                        noResponseRadioGroupClearCheck();
                        btnView.setChecked(isChecked);
                    }
                }
            });

            //if option has input
            if(opt.get(i).getNumOfInput()==1) {
                cb.setId(R.id.otherBox);
                LinearLayout rl = new LinearLayout(getActivity());
                rl.setOrientation(LinearLayout.HORIZONTAL);
                rl.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT));
                rl.addView(cb);
                EditText e = new EditText(getActivity());
                e.setId(R.id.othersEditText);
                e.setWidth(150);

                rl.addView(e);
                l.addView(rl);

                e.addTextChangedListener(new OthersTextWatcher(rl) {
                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        if (count > 0) {
                            CheckBox cb = (CheckBox) this.getView().findViewById(R.id.otherBox);
                            cb.setChecked(true);
                            noResponseRadioGroupClearCheck();
                        }
                    }

                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                    }
                });
                continue;
            }

            l.addView(cb);
        }

    }

    public void clearCheckBoxes() {
        LinearLayout l = (LinearLayout) vPtr.findViewById(R.id.survey_fragment_layout);

        if(l!=null) {
            for (int i = 0; i < l.getChildCount(); i++) {
                if (l.getChildAt(i) instanceof CheckBox) {
                    CheckBox cb = (CheckBox) l.getChildAt(i);
                    cb.setChecked(false);
                }
            }
        }
    }

    public void clearOtherCheckBox() {
        CheckBox cb = (CheckBox) getActivity().findViewById(R.id.otherBox);
        if(cb!=null)
            cb.setChecked(false);
    }

    public void clearOtherEditText() {
        EditText e = (EditText) getActivity().findViewById(R.id.othersEditText);
        if(e!=null)
            e.setText("");
    }

    //*** TODO **** jump to next input when the prev input is filled
    //when input is filled uncheck no response
    public View createYearInput() {
        LinearLayout ll = new LinearLayout(getActivity());
        ll.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        ));
        ll.setOrientation(LinearLayout.HORIZONTAL);
        ll.setId(R.id.yearLinearLayout);

        //create input filter to limit characters to 1 per input
        InputFilter[] filters = new InputFilter[1];
        filters[0] = new InputFilter.LengthFilter(1);

        //create 4 EditText for each number
        for(int i=0; i < 4; i++) {
            EditText et = new EditText(getActivity());
            et.setWidth(70);
            et.setInputType(InputType.TYPE_CLASS_NUMBER);
            et.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            ));
            et.setPadding(20, 0, 0, 0);
            et.setFilters(filters);
            et.addTextChangedListener(noResponseClearWatcher);
            ll.addView(et);
        }

        return ll;
    }

    public void clearYearInput() {
        LinearLayout ll = (LinearLayout) getActivity().findViewById(R.id.yearLinearLayout);

        if(ll!=null) {
            for(int i=0; i<ll.getChildCount(); i++) {
                EditText e = (EditText) ll.getChildAt(i);
                e.setText("");
            }
        }
    }

    private RadioGroup.OnCheckedChangeListener noResponseChangeListener = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            // if noresponse or don't know is selected, deselect other group

            if (checkedId != -1) {
                surveyRadioGroupClearCheck();
                clearSingleInput();
                clearYearInput();
                clearDoubleInputWithUnits();
                clearMonthAndYearDouble();
                clearOtherCheckBox();
                clearRatingGroup();
                clearOtherEditText();
                clearCheckBoxes();
                group.check(checkedId);
            }
        }
    };
    //**** TODO **** when no response is click delete all other input
    public View createNoResponseOption() {
        RadioGroup rg = new RadioGroup(getActivity());
        rg.setId(R.id.noResponseRg);
        RadioButton rb = new RadioButton(getActivity());
        rb.setText("No response");
        rb.setPadding(0, 15, 0, 15);
        rb.setId(R.id.noResponse);
        rg.addView(rb);

        rg.setOnCheckedChangeListener(noResponseChangeListener);

        rg.setPadding(0,30,0,0);

        return rg;
    }


    public View createNoResponseOrDoesNotKnowOption() {
        RadioGroup rg = new RadioGroup(getActivity());
        rg.setOrientation(LinearLayout.VERTICAL);
        rg.setId(R.id.noResponseRg);

        RadioButton rb = new RadioButton(getActivity());
        rb.setText("No response");
        rb.setPadding(0, 15, 0, 15);
        rb.setId(R.id.noResponse);

        RadioButton rb2 = new RadioButton(getActivity());
        rb2.setText("Don't Know");
        rb2.setPadding(0, 15, 0, 15);
        rb2.setId(R.id.dontKnow);

        rg.addView(rb2);
        rg.addView(rb);

        rg.setOnCheckedChangeListener(noResponseChangeListener);

        return rg;
    }

    public void noResponseRadioGroupClearCheck() {
        RadioGroup nrg = (RadioGroup) vPtr.findViewById(R.id.noResponseRg);
        if(nrg !=null) {
            nrg.clearCheck();
        }
    }

    public void surveyRadioGroupClearCheck() {
        RadioGroup nrg = (RadioGroup) vPtr.findViewById(R.id.surveyRadioGrp);
        if(nrg !=null) {
            nrg.clearCheck();
        }
    }

    public View createRatingView() {
        String ratings[] = r.split("/");

        View child = LayoutInflater.from(getActivity()).inflate(R.layout.rating, null);
        TextView leftR = (TextView) child.findViewById(R.id.leftRating);
        TextView rightR = (TextView) child.findViewById(R.id.rightRating);

        RadioGroup rg = (RadioGroup) child.findViewById(R.id.ratingRg);
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId!=-1) {
                    noResponseRadioGroupClearCheck();
                    group.check(checkedId);
                }
            }
        });

        leftR.setText(ratings[0]);
        rightR.setText(ratings[1]);

        return child;
    }

    public void clearRatingGroup() {
        RadioGroup rg = (RadioGroup) getActivity().findViewById(R.id.ratingRg);
        if(rg!=null) {
            rg.clearCheck();
        }
    }

    public View createSingleInputWithUnits() {
        LinearLayout l = new LinearLayout(getActivity());
        l.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        l.setOrientation(LinearLayout.HORIZONTAL);

        EditText editInput = new EditText(getActivity());
        editInput.setWidth(300);
        editInput.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        editInput.setId(R.id.mainInput);
        editInput.addTextChangedListener(noResponseClearWatcher);

        l.addView(editInput);

        if(qId == 6 || qId == 9) {
            setNumberInputType(editInput);
        }

        if(u.length()>0) {
            TextView tv = new TextView(getActivity());
            tv.setText(u);
            tv.setTextAppearance(getActivity(), android.R.style.TextAppearance_Large);
            l.addView(tv);
        }

        return l;
    }

    public void clearSingleInput() {
        EditText et = (EditText) getActivity().findViewById(R.id.mainInput);
        if(et!=null) {
            et.setText("");
        }
    }

    public void setNumberInputType(EditText e) {
        e.setInputType(InputType.TYPE_CLASS_NUMBER);
    }

    //**** TODO **** increase space between radio options
    public View createRadioGroupFromOptions() {
        boolean optFlag = false;
        View child;

        //find if one of the option has input to determine which view to inflate
        for(int i=0; i<opt.size();i++) {
            if(opt.get(i).getNumOfInput()==1) {
                optFlag = true;
            }
        }

        if(optFlag) {
            child = LayoutInflater.from(getActivity()).inflate(R.layout.radio_with_input, null);
        } else {
            child = LayoutInflater.from(getActivity()).inflate(R.layout.radio_without_input, null);
        }

        //for each option, add into radio group
        RadioGroup rg = (RadioGroup) child.findViewById(R.id.surveyRadioGrp);
        for(int i=0; i<opt.size(); i++) {
            if(i==10) {
                rg = (RadioGroup) child.findViewById(R.id.surveyRadioGrpMore);
            }
            //create radio button set text and set sequential id
            RadioButton rb = new RadioButton(getActivity());
            rb.setText(opt.get(i).getOption());
            rb.setPadding(0,15,0,15);
            rb.setId(opt.get(i).getValue());

            //If radio button is the first in the list, check it
            if(i==0) {
                rb.setChecked(true);
            }

            rg.addView(rb);
        }

        //if there is input, when inserting input, select the radio button associated with the input
        if(optFlag) {
            final EditText othersInput = (EditText) child.findViewById(R.id.othersEditText);
            othersInput.addTextChangedListener(new OthersTextWatcher(child) {
                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if (count > 0) {
                        RadioButton rb = (RadioButton) this.getView().findViewById(opt.get(opt.size() - 1).getValue());
                        rb.setChecked(true);
                    }
                }

                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void afterTextChanged(Editable s) {
                }
            });
        }

        final RadioGroup g1 = (RadioGroup) child.findViewById(R.id.surveyRadioGrp);
        final RadioGroup g2 = (RadioGroup) child.findViewById(R.id.surveyRadioGrpMore);
        g1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // if noresponse or don't know is selected, deselect other group
                if (checkedId != -1) {
                    noResponseRadioGroupClearCheck();
                    if(g2!=null) {
                        g2.clearCheck();
                    }
                    group.check(checkedId);
                }

                clearOtherEditText();
            }
        });

        if(g2!=null) {
            g2.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    // if noresponse or don't know is selected, deselect other group
                    if (checkedId != -1) {
                        noResponseRadioGroupClearCheck();
                        if (g1 != null) {
                            g1.clearCheck();
                        }
                        group.check(checkedId);
                    }

                    clearOtherEditText();
                }
            });
        }

        return child;
    }

//    public void createQuestionViews(View v) {
//        LinearLayout l = (LinearLayout) v.findViewById(R.id.survey_fragment_layout);
//
//        Bundle args = getArguments();
//
//        //if ratings not null render rating view
//        if(!args.getString(rating).equals(null) && !args.getString(rating).equals("")) {
//            String ratings[] = args.getString(rating).split("/");
//
//            if(ratings.length==2) {
//                View child = LayoutInflater.from(getActivity()).inflate(R.layout.rating, null);
//                TextView leftR = (TextView) child.findViewById(R.id.leftRating);
//                TextView rightR = (TextView) child.findViewById(R.id.rightRating);
//
//                leftR.setText(ratings[0]);
//                rightR.setText(ratings[1]);
//
//                l.addView(child);
//            }
//        }
//
//
//        // if numofinput is more than 0, then add edit text
//        else if(args.getInt(numOfInput) == 1) {
//            EditText editInput = new EditText(getActivity());
//            editInput.setWidth(300);
//            editInput.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
//            l.addView(editInput, 2);
//        }
//        // if numofinput is more than 1
//        else if(args.getInt(numOfInput) > 1) {
//            String units[] = args.getString(SurveyFragment.units).split("/");
//            LinearLayout ll = new LinearLayout(getActivity());
//            ll.setOrientation(LinearLayout.HORIZONTAL);
//
//            for(int i=0; i<args.getInt(numOfInput); i++) {
//
//                EditText editInput = new EditText(getActivity());
//                editInput.setWidth(150);
//                editInput.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
//                ll.addView(editInput);
//
//                if(units.length>i && !units[i].equals("")) {
//                    TextView tv = new TextView(getActivity());
//                    tv.setTextSize(17);
//                    tv.setTextColor(Color.BLACK);
//                    tv.setText(units[i]);
//                    ll.addView(tv);
//                }
//            }
//
//            l.addView(ll);
//        }
//
//        //if qns has options display options
//        if(args.getBoolean(hasOptions)) {
//            ArrayList<Options> opts = args.getParcelableArrayList(options);
//
//            //render views depending on option type
//            //if option type is checkbox
//            if(args.getString(optionType).equals("CHECKBOX")) {
//
//                for(int i=0; i<opts.size(); i++) {
//                    LinearLayout rl = new LinearLayout(getActivity());
//                    rl.setOrientation(LinearLayout.HORIZONTAL);
//                    rl.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
//                        LinearLayout.LayoutParams.WRAP_CONTENT));
//                    CheckBox cb = new CheckBox(getActivity());
//                    TextView tv = new TextView(getActivity());
//                    tv.setText(opts.get(i).getOption());
//                    tv.setTextColor(Color.BLACK);
//
//                    cb.setTag(opts.get(i).getValue());
//
//                    rl.addView(cb);
//                    rl.addView(tv);
//
//                    //if option has input
//                    if(opts.get(i).getNumOfInput()==1) {
//                        EditText e = new EditText(getActivity());
//                        e.setWidth(150);
//                        rl.addView(e);
//                    }
//
//                    l.addView(rl);
//                }
//            }
//
//            //if option type is dropdown
//            else if(args.getString(optionType).equals("DROPDOWN")) {
//
//                Spinner sp = new Spinner(getActivity());
//                ArrayAdapter<String> spinnerArr = new ArrayAdapter<String>(getActivity(), R.layout.array_adapter_view);
//                spinnerArr.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//                for(int i=0; i<opts.size(); i++) {
//                    spinnerArr.add(opts.get(i).getOption());
//                }
//
//                sp.setAdapter(spinnerArr);
//                l.addView(sp);
//            }
//
//            //if option type is lettering
//            else if(args.getString(optionType).equals("LETTER")) {
//                String ls[] = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o"};
//
//                for(int i=0; i<opts.size(); i++) {
//                    LinearLayout letterLayout = new LinearLayout(getActivity());
//                    letterLayout.setOrientation(LinearLayout.HORIZONTAL);
//                    TextView tv = new TextView(getActivity());
//                    tv.setText(ls[i] + ". " + opts.get(i).getOption());
//                    letterLayout.addView(tv);
//
//                    if(opts.get(i).getNumOfInput()>0) {
//                        for(int j=0; j<opts.get(i).getNumOfInput(); j++) {
//                            EditText et = new EditText(getActivity());
//                            et.setWidth(100);
//                            letterLayout.addView(et);
//
////                            if(units.length > j) {
////                                TextView u = new TextView(getActivity());
////                                u.setText(units[j]);
////                                letterLayout.addView(u);
////                            }
//                        }
//                    }
//
//                    l.addView(letterLayout);
//
//                }
//            }
//
//            else {
//                boolean optFlag = false;
//                View child;
//
//                //find if one of the option has input to determine which view to inflate
//                for(int i=0; i<opts.size();i++) {
//                    if(opts.get(i).getNumOfInput()==1) {
//                        optFlag = true;
//                    }
//                }
//
//                if(optFlag) {
//                    child = LayoutInflater.from(getActivity()).inflate(R.layout.radio_with_input, null);
//                } else {
//                    child = LayoutInflater.from(getActivity()).inflate(R.layout.radio_without_input, null);
//                }
//
//                //for each option, add into radio group
//                RadioGroup rg = (RadioGroup) child.findViewById(R.id.surveyRadioGrp);
//                for(int i=0; i<opts.size(); i++) {
//                    RadioButton rb = new RadioButton(getActivity());
//                    rb.setText(opts.get(i).getOption());
//                    rb.setTag(opts.get(i).getValue());
//                    rg.addView(rb);
//                }
//
//                l.addView(child);
//            }
//
//        }
//
//        LinearLayout btnLayout = new LinearLayout(getActivity());
//        btnLayout.setOrientation(LinearLayout.HORIZONTAL);
//        LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
//        btnLayout.setLayoutParams(p);
//        btnLayout.setGravity(Gravity.RIGHT);
//
//        Button btnN = new Button(getActivity());
//        Button btnP = new Button(getActivity());
//        btnN.setText("Next");
//        btnN.setGravity(Gravity.RIGHT);
//
//        btnP.setText("Previous");
//        btnP.setGravity(Gravity.RIGHT);
//
//        btnN.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                onNext(v);
//            }
//        });
//        btnP.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                onPrev(v);
//            }
//        });
//
//        btnLayout.addView(btnP);
//        btnLayout.addView(btnN);
//
//        l.addView(btnLayout);
//
//    }

    public View createNextAndPrevBtns() {
        LinearLayout btnLayout = new LinearLayout(getActivity());
        btnLayout.setOrientation(LinearLayout.HORIZONTAL);
        LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        btnLayout.setLayoutParams(p);
        btnLayout.setGravity(Gravity.RIGHT);

        Button btnN = new Button(getActivity());
        Button btnP = new Button(getActivity());
        if(isEnglish)
            btnN.setText("Next");
        else
            btnN.setText(R.string.nextBahasa);

        btnN.setId(R.id.surveyNextBtn);
        btnN.setGravity(Gravity.RIGHT);

        if(isEnglish)
            btnP.setText("Previous");
        else
            btnP.setText(R.string.previousBahasa);

        btnP.setGravity(Gravity.RIGHT);

        btnP.setId(R.id.surveyPrevBtn);

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

        if(ind==0)
            btnP.setEnabled(false);

        btnLayout.addView(btnP);
        btnLayout.addView(btnN);

        return btnLayout;
    }

    public void onNext(View v) {

        //update data
        switch(gId) {
            case 0:
                updateLocationView();
                break;
            case 1:
                updateDemoData();
                break;
            case 2:
                updateMigData();
                break;
            case 3:
                updateEmpData();
                break;
            case 4:
                updateCurrEmpData();
                break;
            case 5:
                updateBargData();
                break;
            case 6:
                updateExpData();
                break;
            case 7:
                updateKnowData();
                break;
            case 8:
                updateContData();
                break;
        }

        //if last fragment, generate random number, start new activity.
        int childCount = ((SurveyQnsDisplayActivity) getActivity()).getFragmentSize();
        if(ind == childCount - 1) {
            int randNum = (getActivity()).getSharedPreferences("sessionData", Context.MODE_PRIVATE).getInt("randNum", -1);
            //save randNum
//            ((SurveyQnsDisplayActivity) getActivity()).saveRandomNum(randNum);
            startActivityWithRandomNum(randNum);
        }

        else if(qnsWithPgJump.containsKey(qId)) {
            int ans = qnsWithPgJump.get(qId);
            if(ans == 1) {
                flipNextPage();
            } else {
                skipNextPage();
            }
        }
        //else set the next page as current page
        else {
            flipNextPage();
        }

    }

    public void flipNextPage() {
        ViewPager vp = (ViewPager) getActivity().findViewById(R.id.viewpager);
        vp.setCurrentItem(ind + 1);
    }

    public void flipPrevPage() {
        ViewPager vp = (ViewPager) getActivity().findViewById(R.id.viewpager);
        vp.setCurrentItem(ind-1);
    }

    public void skipNextPage() {
        ViewPager vp = (ViewPager) getActivity().findViewById(R.id.viewpager);
        vp.setCurrentItem(ind + 2);
    }

    public void skipPrevPage() {
        ViewPager vp = (ViewPager) getActivity().findViewById(R.id.viewpager);
        vp.setCurrentItem(ind - 2);
    }

    public void onPrev(View v) {
        if(qnsWithPgJump.containsKey(qId-2)) {
            int ans = qnsWithPgJump.get(qId-2);

            if(ans == 1) {
                flipPrevPage();
            } else {
                skipPrevPage();
            }
        } else {
            flipPrevPage();
        }
    }

    public void startActivityWithRandomNum(int randNum) {
        Intent i = new Intent(getActivity(), PhoneNumberActivity.class);
        switch (randNum) {

            case 1:
                startActivity(i);
                break;
            case 2:
                HashMap<String, String> userAns = ((SurveyQnsDisplayActivity) getActivity()).getRandomTwo();
                HashMap<String, String> correctAns = ((SurveyQnsDisplayActivity) getActivity()).getCorrectAns();

                i.putExtra("userAns2", userAns);
                i.putExtra("correctAns", correctAns);

                startActivity(i);
                break;
            case 3:
                HashMap<String, String> uAns = ((SurveyQnsDisplayActivity) getActivity()).getRandomThree();
                i.putExtra("userAns3", uAns);
                startActivity(i);
                break;
            case 4:
                startActivity(i);
                break;
            case 5:
                HashMap<String, String> userAns2 = ((SurveyQnsDisplayActivity) getActivity()).getRandomTwo();
                HashMap<String, String> userAns3 = ((SurveyQnsDisplayActivity) getActivity()).getRandomThree();
                i.putExtra("userAns2", userAns2);
                i.putExtra("userAns3", userAns3);
                i.putExtra("correctAns", ((SurveyQnsDisplayActivity) getActivity()).getCorrectAns());
                startActivity(i);
                break;
        }
    }

    public boolean isResponseBtnChecked() {
        RadioButton noResponseBtn = (RadioButton) vPtr.findViewById(R.id.noResponse);

        if(noResponseBtn!=null && noResponseBtn.isChecked()) {
            answers.add(GlobalValues.noRespValue);
            return true;
        }

        return false;
    }

    public boolean isResponseOrDoesNotKnowBtnChecked() {
        RadioButton noResponseBtn = (RadioButton) vPtr.findViewById(R.id.noResponse);
        RadioButton doesNotKnowBtn = (RadioButton) vPtr.findViewById(R.id.dontKnow);

        if(noResponseBtn!=null && noResponseBtn.isChecked()) {
            answers.add(GlobalValues.noRespValue);
            return true;
        } else if(doesNotKnowBtn!=null && doesNotKnowBtn.isChecked()) {
            answers.add(GlobalValues.dkValue);
            return true;
        }

        return false;
    }

    public void updateLocationView() {
        answers.clear();

        saveRadioGroupAnswers();
    }

    public void updateCurrEmpData() {
        answers.clear();

        if(isResponseBtnChecked()) {
            ((SurveyQnsDisplayActivity) getActivity()).putRandomThree(
                    Integer.toString(qId),
                    GlobalValues.noRespValue);
            return;
        }

        if(isResponseOrDoesNotKnowBtnChecked()) {
            ((SurveyQnsDisplayActivity) getActivity()).putRandomThree(
                    Integer.toString(qId),
                    GlobalValues.dkValue);
            return;
        }

        saveMainInput();

        saveDoubleInput();

        saveRatingAnswers();

        if(hasOpt) {
            if(optType.equals("RADIO")) {
                saveRadioGroupAnswers();
            }

            else if(optType.equals("CHECKBOX")) {
                saveCheckBoxAnswers();
            }

            saveOtherSpecify();
        }
    }

    public void updateContData() {
        answers.clear();

        if(isResponseBtnChecked()) {
            return;
        }

        saveTimeInput();

        savePhoneNum();

        saveMainInput();

        saveCheckBoxAnswers();

        saveRadioGroupAnswers();

        saveOtherSpecify();
    }

    public void updateDemoData() {
        //clear the answer array
        answers.clear();

        if(isResponseBtnChecked()) {
            return;
        }

        saveMainInput();

        saveRadioGroupAnswers();

        saveOtherSpecify();

    }

    public void updateMigData() {
        answers.clear();

        if(isResponseBtnChecked()) {
            return;
        }

        //If year input exists
        LinearLayout l = (LinearLayout) vPtr.findViewById(R.id.yearLinearLayout);
        if(l!=null) {
            String y = "";
            for (int i = 0; i < l.getChildCount(); i++) {
                EditText e = (EditText) l.getChildAt(i);
                y = y + e.getText().toString();
            }

            answers.add(y);
        }

        saveRadioGroupAnswers();

    }

    public void updateEmpData() {
        answers.clear();

        if(isResponseBtnChecked()) {
            return;
        }

        if(qId == 13) {
            int ids[] = {R.id.monthDouble, R.id.yearDouble};

            for(int i=0; i<ids.length; i++) {
                LinearLayout l = (LinearLayout) vPtr.findViewById(ids[i]);
                String a = "";
                for(int j=0; j<l.getChildCount(); j++) {
                    if(l.getChildAt(j) instanceof EditText) {
                        a = a + ((EditText) l.getChildAt(j)).getText().toString();
                    }
                }
                answers.add(a);
            }

            return;
        }

        if(numInput > 0) {
            saveMainInput();
        }

        if(hasOpt) {
            if(optType.equals("RADIO")) {
                saveRadioGroupAnswers();
            } else if(optType.equals("CHECKBOX")) {
                saveCheckBoxAnswers();
                saveOtherSpecify();
            }
        }
    }

    public void updateBargData() {
        answers.clear();

        if(isResponseBtnChecked()) {
            return;
        }

        saveRadioGroupAnswers();
    }

    public void updateExpData() {
        answers.clear();

        if(isResponseOrDoesNotKnowBtnChecked()) {
            return;
        }

        if(!r.equals(null) && !r.equals("")) {
            saveRatingAnswers();
        }

        if(numInput == 1) {
            saveMainInput();
        }

        if(hasOpt) {
            if(optType.equals("DROPDOWN")) {
                saveSpinnerAnswers();
            } else if(optType.equals("RADIO")) {
                saveRadioGroupAnswers();
            }
        }
    }

    public void updateKnowData() {
        answers.clear();

        if(isResponseBtnChecked()) {
            ((SurveyQnsDisplayActivity) getActivity()).putRandomTwo(
                    Integer.toString(qId),
                    GlobalValues.noRespValue);
            return;
        }

        if(hasOpt && optType.equals("RADIO")) {
            saveRadioGroupAnswers();
        }
    }

    public void saveRatingAnswers() {
        RadioGroup rg = (RadioGroup) vPtr.findViewById(R.id.ratingRg);

        if(rg!= null) {
            switch(rg.getCheckedRadioButtonId()) {
                case R.id.r1:
                    answers.add("1");
                    break;
                case R.id.r2:
                    answers.add("2");
                    break;
                case R.id.r3:
                    answers.add("3");
                    break;
                case R.id.r4:
                    answers.add("4");
                    break;
                case R.id.r5:
                    answers.add("5");
            }
        }
    }

    public void saveSpinnerAnswers() {
        Spinner sp = (Spinner) vPtr.findViewById(R.id.spinnerDropDown);

        if(sp!= null) {
            answers.add(Integer.toString(sp.getSelectedItemPosition() + 1));
        }
    }

    public void saveRadioGroupAnswers() {
        //save radio input next
        RadioGroup rg = (RadioGroup) vPtr.findViewById(R.id.surveyRadioGrp);
        if(rg!=null && rg.getCheckedRadioButtonId()!=-1) {
            int checked = rg.getCheckedRadioButtonId();
            answers.add(Integer.toString(checked));

            //if groupId is Knowledge, save answers into hashmap
            if(gId == 7) {
                ((SurveyQnsDisplayActivity) getActivity()).putRandomTwo(
                        Integer.toString(qId),
                        Integer.toString(checked));
            }

            if(qId == 31) {
                ((SurveyQnsDisplayActivity) getActivity()).putRandomThree(
                        Integer.toString(qId),
                        Integer.toString(checked));
            }

            //if qnsWithPageJump contains qnsId, update qnsWithPageJump the answer
            if(qnsWithPgJump.containsKey(qId)) {
                qnsWithPgJump.put(qId, checked);
            }
        }
    }

    public void saveCheckBoxAnswers() {
        LinearLayout l = (LinearLayout) vPtr.findViewById(R.id.survey_fragment_layout);

        for(int i=0; i<l.getChildCount(); i++) {
            if(l.getChildAt(i) instanceof CheckBox) {
                CheckBox c = (CheckBox) l.getChildAt(i);
                if(c.isChecked())
                    answers.add(c.getTag().toString());
            }
        }

        CheckBox c = (CheckBox) vPtr.findViewById(R.id.otherBox);
        if(c!=null && c.isChecked()) {
            answers.add(c.getTag().toString());
        }
    }

    public void saveOtherSpecify() {
        EditText othersEt = (EditText) vPtr.findViewById(R.id.othersEditText);
        //**********TODO: once others edit text is chosen immediately choose others.***********
        if(othersEt!=null && !othersEt.getText().toString().equals("")) {
            answers.add(othersEt.getText().toString());
        }
    }

    public void saveMainInput() {
        //save main input first if exists
        EditText et = (EditText) vPtr.findViewById(R.id.mainInput);
        if(et !=null) {
            String etText = et.getText().toString();

            if(etText.equals("")) {
                etText = GlobalValues.noRespValue;
            }

            if(qId == 22 || qId == 21 || qId == 81) {
                ((SurveyQnsDisplayActivity) getActivity()).putRandomThree(
                        Integer.toString(qId),
                        etText);
            }

            answers.add(et.getText().toString());
        }
    }

    public void savePhoneNum() {
        View v = vPtr.findViewById(R.id.phoneNumInput);

        if(v!=null) {
            String n1 = ((EditText) v.findViewById(R.id.n1)).getText().toString();
            String n2 = ((EditText) v.findViewById(R.id.n2)).getText().toString();
            String n3 = ((EditText) v.findViewById(R.id.n3)).getText().toString();
            String n4 = ((EditText) v.findViewById(R.id.n4)).getText().toString();
            String n5 = ((EditText) v.findViewById(R.id.n5)).getText().toString();
            String n6 = ((EditText) v.findViewById(R.id.n6)).getText().toString();
            String n7 = ((EditText) v.findViewById(R.id.n7)).getText().toString();
            String n8 = ((EditText) v.findViewById(R.id.n8)).getText().toString();

            answers.add(n1+n2+n3+n4+n5+n6+n7+n8);
        }
    }

    public void saveTimeInput() {
        View v = vPtr.findViewById(R.id.timeInputLayout);

        if(v!=null) {
            String hrTen = ((EditText) v.findViewById(R.id.hourTen)).getText().toString();
            String hrOne = ((EditText) v.findViewById(R.id.hourOne)).getText().toString();
            String minTen = ((EditText) v.findViewById(R.id.minTen)).getText().toString();
            String minOne = ((EditText) v.findViewById(R.id.minOne)).getText().toString();
            RadioGroup timeRg = ((RadioGroup) v.findViewById(R.id.timeRg));
            String timeCat;

            if(timeRg.getCheckedRadioButtonId() == R.id.amBtn) {
                timeCat = "am";
            } else {
                timeCat = "pm";
            }

            answers.add(hrTen+hrOne+minTen+minOne + timeCat);
        }
    }

    public void saveDoubleInput() {
        View v = vPtr.findViewById(R.id.doubleInput);

        if(v!=null) {
            EditText et1 = (EditText) vPtr.findViewById(R.id.input1);
            EditText et2 = (EditText) vPtr.findViewById(R.id.input2);

            answers.add(et1.getText().toString());
            answers.add(et2.getText().toString());
        }
    }

    public ArrayList<String> getAnswers() {
        if(answers == null) {
            return new ArrayList<String> ();
        }
        return answers;
    }

    public String getQns() {
        if(q == null) {
            return "";
        }
        return q;
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
