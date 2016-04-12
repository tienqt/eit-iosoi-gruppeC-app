package com.smartlock.eit.smartlock;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import org.w3c.dom.UserDataHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tien on 4/2/2016.
 */

public class HomeFrag extends Fragment {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";
    boolean locked;
    Spinner bikeSpinner;
    ImageButton lockbtn;

    public HomeFrag() {
        Userdata.getInstance().setHomeFrag(this);
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static HomeFrag newInstance(int sectionNumber) {
        HomeFrag fragment = new HomeFrag();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    TextView statusText;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);

        bikeSpinner = (Spinner)rootView.findViewById(R.id.bikeSpinner);
        statusText = (TextView) rootView.findViewById(R.id.bikeStatus);
        lockbtn = (ImageButton) rootView.findViewById(R.id.lockbikebtn);
        ImageButton statbtn = (ImageButton) rootView.findViewById(R.id.bikestatsbtn);
        ImageButton findbtn = (ImageButton) rootView.findViewById(R.id.findBikebtn);

        locked = true;

        updateUI();

        findbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startMapIntent = new Intent(Userdata.getInstance().getContext(), MapsActivity.class);
                // startMapIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(startMapIntent);

            }
        });

        lockbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Userdata.getInstance().getBike(bikeSpinner.getSelectedItemPosition()).isLocked()) {
                    Userdata.getInstance().getBike(bikeSpinner.getSelectedItemPosition()).setLocked(false);
                    Userdata.getInstance().setBikeLock(Userdata.getInstance().getBike(bikeSpinner.getSelectedItemPosition()).getBikeId(), false);
                } else {
                    Userdata.getInstance().getBike(bikeSpinner.getSelectedItemPosition()).setLocked(true);
                    Userdata.getInstance().setBikeLock(Userdata.getInstance().getBike(bikeSpinner.getSelectedItemPosition()).getBikeId(), true);
                }
            }
        });


        statbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        return rootView;
    }

    public void updateUI(){
        ArrayAdapter<String> adapter;
        List<String> list;

        list = new ArrayList<String>();
        if(Userdata.getInstance().getBike().size() == 0){
            list.add("No bike registered");
        }

        for(int i = 0; i<Userdata.getInstance().getBike().size(); i++){
            if(Userdata.getInstance().getBike(i).getOwnder().equals(Userdata.getInstance().getUsername())) {
                list.add(Userdata.getInstance().getBike(i).getBikeName());
            }
        }

        for(int i = 0; i<Userdata.getInstance().getBike().size(); i++){
            if(!Userdata.getInstance().getBike(i).getOwnder().equals(Userdata.getInstance().getUsername())) {
                list.add(Userdata.getInstance().getBike(i).getBikeName() + "     " + Userdata.getInstance().getBike(i).getOwnder());
            }
        }

        adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_item, list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        bikeSpinner.setAdapter(adapter);

        bikeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                if (parent.getChildAt(0) != null) {
                    ((TextView) parent.getChildAt(0)).setTextSize(20);
                }

                if (Userdata.getInstance().getBike().size() < pos) {
                    if (Userdata.getInstance().getBike(pos).isStolen() == false) {
                        statusText.setTextColor(Color.GREEN);
                        statusText.setText("");
                    } else {
                        statusText.setTextColor(Color.RED);
                        statusText.setText("STOLEN");
                    }
                }
                updateLockBtn();
            }

            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        updateLockBtn();
    }

    public void updateLockBtn(){
        if (Userdata.getInstance().getBike().size() > bikeSpinner.getSelectedItemPosition() ) {
            if (Userdata.getInstance().getBike(bikeSpinner.getSelectedItemPosition()).isLocked()) {
                lockbtn.setImageResource(R.drawable.bikelock);
            } else {
                lockbtn.setImageResource(R.drawable.bikeunlock);
            }
        }
    }
}