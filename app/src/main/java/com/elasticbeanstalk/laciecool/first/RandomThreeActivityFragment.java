package com.elasticbeanstalk.laciecool.first;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
            v = inflater.inflate(R.layout.fragment_random_three_one, container, false);
            TextView qView = (TextView) v.findViewById(R.id.randThreeQns);
            qView.setText(q);

            TextView uaView = (TextView) v.findViewById(R.id.userAns);

            if(qId == 31) {
                switch(ua) {
                    case "1":
                        uaView.setText("Never");
                        break;
                    case "2":
                        uaView.setText("Rarely");
                        break;
                    case "3":
                        uaView.setText("Sometimes");
                        break;
                    case "4":
                        uaView.setText("Often");
                        break;
                    case "5":
                        uaView.setText("Always");
                        break;
                }
            } else {
                uaView.setText(ua);
            }
        } else if(fi==1) {

            v = inflater.inflate(R.layout.fragment_random_three_two, container, false);
            ImageView iv = (ImageView) v.findViewById(R.id.imageRandomThreeMain);

            if(qId==22)
                iv.setImageResource(R.drawable.wage_main_scale);

            else if(qId==81)
                iv.setImageResource(R.drawable.hours_main_scale);

            else if(qId == 21)
                iv.setImageResource(R.drawable.offdays_main_scale);

            else
                iv.setImageResource(R.drawable.freedom_main_scale);
        }
        else if(fi == 2) {
            v = inflater.inflate(R.layout.fragment_random_three_two, container, false);
            ImageView iv = (ImageView) v.findViewById(R.id.imageRandomThreeMain);

            if(qId == 22)
                iv.setImageResource(R.drawable.wage_main);

            else if(qId == 81)
                iv.setImageResource(R.drawable.hours_main);

            else if(qId == 21)
                iv.setImageResource(R.drawable.offdays_main);

            else    //qId == 31
                iv.setImageResource(R.drawable.freedom_main);

        } else if (fi==3){    //fi == 3
            v = inflater.inflate(R.layout.fragment_random_three_three, container, false);
            ImageView iv = (ImageView) v.findViewById(R.id.imageRandomThreeThree);
            int ans = Integer.parseInt(ua);
            if(qId == 22) {

                if(ans > 700) {
                    iv.setImageResource(R.drawable.wage_1a);
                }

                else if(ans>600 && ans<=700) {
                    iv.setImageResource(R.drawable.wage_2a);
                }

                else if(ans>500 && ans<=600) {
                    iv.setImageResource(R.drawable.wage_3a);
                }

                else if(ans>400 && ans<=500) {
                    iv.setImageResource(R.drawable.wage_4a);
                }

                else {
                    iv.setImageResource(R.drawable.wage_5a);
                }
            }

            else if(qId == 81) {

                if(ans<8) {
                    iv.setImageResource(R.drawable.hours_1a);
                }

                else if(ans>8 && ans<=10) {
                    iv.setImageResource(R.drawable.hours_2a);
                }

                else if(ans>10 && ans<=12)  {
                    iv.setImageResource(R.drawable.hours_3a);
                }

                else if(ans>12 && ans<=14) {
                    iv.setImageResource(R.drawable.hours_4a);
                }

                else {
                    iv.setImageResource(R.drawable.hours_5a);
                }
            }

            else if(qId == 21) {

                if(ans >= 4) {
                    iv.setImageResource(R.drawable.offdays_1a);
                }

                else if(ans == 3) {
                    iv.setImageResource(R.drawable.offdays_2a);
                }

                else if(ans == 2) {
                    iv.setImageResource(R.drawable.offdays_3a);
                }

                else {
                    iv.setImageResource(R.drawable.offdays_4a);
                }
            }

            else {

                if(ans == 5) {
                    iv.setImageResource(R.drawable.freedom_1a);
                }

                else if(ans == 4) {
                    iv.setImageResource(R.drawable.freedom_2a);
                }

                else if(ans == 3) {
                    iv.setImageResource(R.drawable.freedom_3a);
                }

                else if(ans == 2) {
                    iv.setImageResource(R.drawable.freedom_4a);
                }

                else {
                    iv.setImageResource(R.drawable.freedom_5a);
                }
            }

        } else if(fi==4) {
            v = inflater.inflate(R.layout.fragment_random_three_three, container, false);
            ImageView iv = (ImageView) v.findViewById(R.id.imageRandomThreeThree);
            int ans = Integer.parseInt(ua);
            if(qId == 22) {

                if(ans > 700) {
                    iv.setImageResource(R.drawable.wage_1b);
                }

                else if(ans>600 && ans<=700) {
                    iv.setImageResource(R.drawable.wage_2b);
                }

                else if(ans>500 && ans<=600) {
                    iv.setImageResource(R.drawable.wage_3b);
                }

                else if(ans>400 && ans<=500) {
                    iv.setImageResource(R.drawable.wage_4b);
                }

                else {
                    iv.setImageResource(R.drawable.wage_5b);
                }
            }

            else if(qId == 81) {

                if(ans<8) {
                    iv.setImageResource(R.drawable.hours_1b);
                }

                else if(ans>8 && ans<=10) {
                    iv.setImageResource(R.drawable.hours_2b);
                }

                else if(ans>10 && ans<=12)  {
                    iv.setImageResource(R.drawable.hours_3b);
                }

                else if(ans>12 && ans<=14) {
                    iv.setImageResource(R.drawable.hours_4b);
                }

                else {
                    iv.setImageResource(R.drawable.hours_5b);
                }
            }

            else if(qId == 21) {

                if(ans >= 4) {
                    iv.setImageResource(R.drawable.offdays_1b);
                }

                else if(ans == 3) {
                    iv.setImageResource(R.drawable.offdays_2b);
                }

                else if(ans == 2) {
                    iv.setImageResource(R.drawable.offdays_3b);
                }

                else {
                    iv.setImageResource(R.drawable.offdays_4b);
                }
            }

            else {

                if(ans == 5) {
                    iv.setImageResource(R.drawable.freedom_1b);
                }

                else if(ans == 4) {
                    iv.setImageResource(R.drawable.freedom_2b);
                }

                else if(ans == 3) {
                    iv.setImageResource(R.drawable.freedom_3b);
                }

                else if(ans == 2) {
                    iv.setImageResource(R.drawable.freedom_4b);
                }

                else {
                    iv.setImageResource(R.drawable.freedom_5b);
                }
            }
        } else {
            v = inflater.inflate(R.layout.fragment_random_three_three, container, false);
            ImageView iv = (ImageView) v.findViewById(R.id.imageRandomThreeThree);
            int ans = Integer.parseInt(ua);
            if(qId == 22) {

                if(ans>600 && ans<=700) {
                    iv.setImageResource(R.drawable.wage_2c);
                }

                else if(ans>500 && ans<=600) {
                    iv.setImageResource(R.drawable.wage_3c);
                }

                else if(ans>400 && ans<=500) {
                    iv.setImageResource(R.drawable.wage_4c);
                }

            }

            else if(qId == 81) {

                if(ans>8 && ans<=10) {
                    iv.setImageResource(R.drawable.hours_2c);
                }

                else if(ans>10 && ans<=12)  {
                    iv.setImageResource(R.drawable.hours_3c);
                }

                else if(ans>12 && ans<=14) {
                    iv.setImageResource(R.drawable.hours_4c);
                }

            }

            else if(qId == 21) {

                if(ans == 3) {
                    iv.setImageResource(R.drawable.offdays_2c);
                }

                else if(ans == 2) {
                    iv.setImageResource(R.drawable.offdays_3c);
                }

            }

            else {

                if(ans == 4) {
                    iv.setImageResource(R.drawable.freedom_2c);
                }

                else if(ans == 3) {
                    iv.setImageResource(R.drawable.freedom_3c);
                }

                else if(ans == 2) {
                    iv.setImageResource(R.drawable.freedom_4c);
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
