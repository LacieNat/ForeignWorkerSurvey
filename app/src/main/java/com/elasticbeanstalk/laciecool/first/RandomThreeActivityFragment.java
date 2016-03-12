package com.elasticbeanstalk.laciecool.first;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * A placeholder fragment containing a simple view.
 */
public class RandomThreeActivityFragment extends Fragment {

    private static final String qnsId = "qnsId";
    private static final String userAns = "userAns";
    private static final String qns = "qns;";
    private static final String fragIndex = "fragIndex";
    private static final String pageIndex = "pageIndex";

    private OnFragmentInteractionListener mListener;

    private int qId;
    private String ua;
    private String q;
    private int fi;
    private int pi;

    private boolean isEnglish;

    public static RandomThreeActivityFragment newInstance(int qId, String qns, String userAns, int fragIndex, int pageIndex) {
        RandomThreeActivityFragment fragment = new RandomThreeActivityFragment();
        Bundle b = new Bundle(1);
        b.putInt(RandomThreeActivityFragment.qnsId, qId);
        b.putString(RandomThreeActivityFragment.userAns, userAns);
        b.putString(RandomThreeActivityFragment.qns, qns);

        b.putInt(RandomThreeActivityFragment.fragIndex, fragIndex);
        b.putInt(RandomThreeActivityFragment.pageIndex, pageIndex);

        fragment.setArguments(b);
        return fragment;
    }

    public RandomThreeActivityFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isEnglish = getActivity().getSharedPreferences("sessionData", Context.MODE_PRIVATE).getInt("lang",0)==0;
        if(getArguments()!=null) {
            qId = getArguments().getInt(RandomThreeActivityFragment.qnsId);
            ua = getArguments().getString(RandomThreeActivityFragment.userAns);
            q = getArguments().getString(RandomThreeActivityFragment.qns);
            pi = getArguments().getInt(RandomThreeActivityFragment.pageIndex);
            fi = getArguments().getInt(RandomThreeActivityFragment.fragIndex);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v;

        if(fi == 0) {
            v = isEnglish?
                    inflater.inflate(R.layout.fragment_random_three_one, container, false):
                    inflater.inflate(R.layout.fragment_random_three_one_bahasa, container, false);
            TextView qView = (TextView) v.findViewById(R.id.randThreeQns);
            qView.setText(q);

            TextView uaView = (TextView) v.findViewById(R.id.userAns);

            if(ua.equals("99") || ua.equals("")) {
                if(isEnglish) {
                    uaView.setText(GlobalValues.noRespEnglish);
                } else {
                    uaView.setText(GlobalValues.noRespBahasa);
                }
            }

            else if(qId == 31) {
                SurveySQL db = new SurveySQL(getActivity());
                Cursor c = db.getOptionsFromQnsId(31);
                if(ua.equals("99")) {
                    if(isEnglish) {
                        uaView.setText(GlobalValues.noRespEnglish);
                    } else {
                        uaView.setText(GlobalValues.noRespBahasa);
                    }
                } else {
                    c.moveToFirst();
                    for(int i=0; i<c.getCount(); i++) {
                        if(c.getInt(c.getColumnIndexOrThrow("value")) == Integer.parseInt(ua)) {
                            if(isEnglish)
                                uaView.setText(c.getString(c.getColumnIndexOrThrow("option")));
                            else
                                uaView.setText(c.getString(c.getColumnIndexOrThrow("optionBahasa")));

                        }

                        c.moveToNext();
                    }
                }

            } else {
                uaView.setText(ua);
            }
        } else if(fi==1) {

            v = isEnglish?
                    inflater.inflate(R.layout.fragment_random_three_two, container, false):
                    inflater.inflate(R.layout.fragment_random_three_two_bahasa, container, false);
            ImageView iv = (ImageView) v.findViewById(R.id.imageRandomThreeMain);

            if(qId==22)
                setImage(iv, R.drawable.wage_main_scale, R.drawable.wage_main_scale_b);

            else if(qId==81)
                setImage(iv, R.drawable.hours_main_scale, R.drawable.hours_main_scale_b);

            else if(qId == 21)
                setImage(iv, R.drawable.offdays_main_scale, R.drawable.offdays_main_scale_b);

            else
                setImage(iv, R.drawable.freedom_main_scale, R.drawable.freedom_main_scale_b);
        }

         else if (fi==2){
            v = isEnglish?
                    inflater.inflate(R.layout.fragment_random_three_two, container, false):
                    inflater.inflate(R.layout.fragment_random_three_two_bahasa, container, false);
            ImageView iv = (ImageView) v.findViewById(R.id.imageRandomThreeMain);
            int ans = Integer.parseInt(ua);
            if(qId == 22) {

                if(ans == 99 || ans == -1) {
                    setImage(iv, R.drawable.wage_main, R.drawable.wage_main_b);
                }
                else if(ans > 700) {
                    setImage(iv, R.drawable.wage_1a, R.drawable.wage_1a_b);
                }

                else if(ans>600 && ans<=700) {
                    setImage(iv, R.drawable.wage_2a, R.drawable.wage_2a_b);
                }

                else if(ans>500 && ans<=600) {
                    setImage(iv, R.drawable.wage_3a, R.drawable.wage_3a_b);
                }

                else if(ans>400 && ans<=500) {
                    setImage(iv, R.drawable.wage_4a, R.drawable.wage_4a_b);
                }

                else {
                    setImage(iv, R.drawable.wage_5a, R.drawable.wage_5a_b);
                }
            }

            else if(qId == 81) {

                if(ans == 99 || ans == -1) {
                    setImage(iv, R.drawable.hours_main, R.drawable.hours_main_b);
                }
                else if(ans<8) {
                    setImage(iv, R.drawable.hours_1a, R.drawable.hours_1a_b);
                }

                else if(ans>8 && ans<=10) {
                    setImage(iv, R.drawable.hours_2a, R.drawable.hours_2a_b);
                }

                else if(ans>10 && ans<=12)  {
                    setImage(iv, R.drawable.hours_3a, R.drawable.hours_3a_b);
                }

                else if(ans>12 && ans<=14) {
                    setImage(iv, R.drawable.hours_4a, R.drawable.hours_4a_b);
                }

                else {
                    setImage(iv, R.drawable.hours_5a, R.drawable.hours_5a_b);
                }
            }

            else if(qId == 21) {

                if(ans == 99 || ans == -1) {
                    setImage(iv, R.drawable.offdays_main, R.drawable.offdays_main_b);
                }
                else if(ans >= 4) {
                    setImage(iv, R.drawable.offdays_1a, R.drawable.offdays_1a_b);
                }

                else if(ans == 3) {
                    setImage(iv, R.drawable.offdays_2a, R.drawable.offdays_2a_b);
                }

                else if(ans == 2) {
                    setImage(iv, R.drawable.offdays_3a, R.drawable.offdays_3a_b);
                }

                else {
                    setImage(iv, R.drawable.offdays_4a, R.drawable.offdays_4a_b);
                }
            }

            else {

                if(ans == 99 || ans == -1) {
                    setImage(iv, R.drawable.freedom_main, R.drawable.freedom_main_b);
                }
                else if(ans == 5) {
                    setImage(iv, R.drawable.freedom_1a, R.drawable.freedom_1a_b);
                }

                else if(ans == 4) {
                    setImage(iv, R.drawable.freedom_2a, R.drawable.freedom_2a_b);
                }

                else if(ans == 3) {
                    setImage(iv, R.drawable.freedom_3a, R.drawable.freedom_3a_b);
                }

                else if(ans == 2) {
                    setImage(iv, R.drawable.freedom_4a, R.drawable.freedom_4a_b);
                }

                else {
                    setImage(iv, R.drawable.freedom_5a, R.drawable.freedom_5a_b);
                }
            }

        } else if(fi==3) {
            v = isEnglish?
                    inflater.inflate(R.layout.fragment_random_three_two, container, false):
                    inflater.inflate(R.layout.fragment_random_three_two_bahasa, container, false);
            ImageView iv = (ImageView) v.findViewById(R.id.imageRandomThreeMain);
            int ans = Integer.parseInt(ua);
            if(qId == 22) {

                if(ans > 700) {
                    setImage(iv, R.drawable.wage_1b, R.drawable.wage_1b_b);
                }

                else if(ans>600 && ans<=700) {
                    setImage(iv, R.drawable.wage_2b, R.drawable.wage_2b_b);
                }

                else if(ans>500 && ans<=600) {
                    setImage(iv, R.drawable.wage_3b, R.drawable.wage_3b_b);
                }

                else if(ans>400 && ans<=500) {
                    setImage(iv, R.drawable.wage_4b, R.drawable.wage_4b_b);
                }

                else {
                    setImage(iv, R.drawable.wage_5b, R.drawable.wage_5b_b);
                }
            }

            else if(qId == 81) {

                if(ans<8) {
                    setImage(iv, R.drawable.hours_1b, R.drawable.hours_1b_b);
                }

                else if(ans>8 && ans<=10) {
                    setImage(iv, R.drawable.hours_2b, R.drawable.hours_2b_b);
                }

                else if(ans>10 && ans<=12)  {
                    setImage(iv, R.drawable.hours_3b, R.drawable.hours_3b_b);
                }

                else if(ans>12 && ans<=14) {
                    setImage(iv, R.drawable.hours_4b, R.drawable.hours_4b_b);
                }

                else {
                    setImage(iv, R.drawable.hours_5b, R.drawable.hours_5b_b);
                }
            }

            else if(qId == 21) {

                if(ans >= 4) {
                    setImage(iv, R.drawable.offdays_1b, R.drawable.offdays_1b_b);
                }

                else if(ans == 3) {
                    setImage(iv, R.drawable.offdays_2b, R.drawable.offdays_2b_b);
                }

                else if(ans == 2) {
                    setImage(iv, R.drawable.offdays_3b, R.drawable.offdays_3b_b);
                }

                else {
                    setImage(iv, R.drawable.offdays_4b, R.drawable.offdays_4b_b);
                }
            }

            else {

                if(ans == 5) {
                    setImage(iv, R.drawable.freedom_1b, R.drawable.freedom_1b_b);
                }

                else if(ans == 4) {
                    setImage(iv, R.drawable.freedom_2b, R.drawable.freedom_2b_b);
                }

                else if(ans == 3) {
                    setImage(iv, R.drawable.freedom_3b, R.drawable.freedom_3b_b);
                }

                else if(ans == 2) {
                    setImage(iv, R.drawable.freedom_4b, R.drawable.freedom_4b_b);
                }

                else {
                    setImage(iv, R.drawable.freedom_5b, R.drawable.freedom_5b_b);
                }
            }
        } else {
            v = isEnglish?
                    inflater.inflate(R.layout.fragment_random_three_two, container, false):
                    inflater.inflate(R.layout.fragment_random_three_two_bahasa, container, false);
            ImageView iv = (ImageView) v.findViewById(R.id.imageRandomThreeMain);
            int ans = Integer.parseInt(ua);
            if(qId == 22) {

                if(ans>600 && ans<=700) {
                    setImage(iv, R.drawable.wage_2c, R.drawable.wage_2c_b);
                }

                else if(ans>500 && ans<=600) {
                    setImage(iv, R.drawable.wage_3c, R.drawable.wage_3c_b);
                }

                else if(ans>400 && ans<=500) {
                    setImage(iv, R.drawable.wage_4c, R.drawable.wage_4c_b);
                }

            }

            else if(qId == 81) {

                if(ans>8 && ans<=10) {
                    setImage(iv, R.drawable.hours_2c, R.drawable.hours_2c_b);
                }

                else if(ans>10 && ans<=12)  {
                    setImage(iv, R.drawable.hours_3c, R.drawable.hours_3c_b);
                }

                else if(ans>12 && ans<=14) {
                    setImage(iv, R.drawable.hours_4c, R.drawable.hours_4c_b);
                }

            }

            else if(qId == 21) {

                if(ans == 3) {
                    setImage(iv, R.drawable.offdays_2c, R.drawable.offdays_2c_b);
                }

                else if(ans == 2) {
                    setImage(iv, R.drawable.offdays_3c, R.drawable.offdays_3c_b);
                }

            }

            else {

                if(ans == 4) {
                    setImage(iv, R.drawable.freedom_2c, R.drawable.freedom_2c_b);
                }

                else if(ans == 3) {
                    setImage(iv, R.drawable.freedom_3c, R.drawable.freedom_3c_b);
                }

                else if(ans == 2) {
                    setImage(iv, R.drawable.freedom_4c, R.drawable.freedom_4c_b);
                }

            }
        }

        Button btnN = (Button) v.findViewById(R.id.randomThreeNext);
        Button btnP = (Button) v.findViewById(R.id.randomThreePrev);

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

    public void setImage(ImageView v, int resE, int resB) {
        if (isEnglish) {
            v.setImageResource(resE);
        } else {
            v.setImageResource(resB);
        }
    }

    public void onNext(View v) {
        //if last fragment, go end activity
        int childCount = ((RandomThreeActivity) getActivity()).getFragmentSize();
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
