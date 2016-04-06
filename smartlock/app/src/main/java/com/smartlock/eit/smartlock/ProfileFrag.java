package com.smartlock.eit.smartlock;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by tien on 4/2/2016.
 */

public class ProfileFrag extends Fragment {

    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";

    ListView bikeView;
    ArrayList<String> listItems=new ArrayList<String>();

    ArrayAdapter<String> adapter;


    public ProfileFrag() {
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static ProfileFrag newInstance(int sectionNumber) {
        ProfileFrag fragment = new ProfileFrag();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_profile, container, false);
        bikeView = (ListView) rootView.findViewById(R.id.bikeListView);
        listItems = new ArrayList<String>();

        TextView username = (TextView) rootView.findViewById(R.id.prof_username);
        TextView email = (TextView) rootView.findViewById(R.id.prof_email);
        TextView phone = (TextView) rootView.findViewById(R.id.prof_mobile);

        ImageButton new_bike_btn = (ImageButton) rootView.findViewById(R.id.new_bike_btn);

        new_bike_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent bike_intent = new Intent(getContext(), BikeActivity.class);
                startActivity(bike_intent);

            }
        });

        adapter = (new ArrayAdapter<String>(Userdata.getInstance().getContext(), android.R.layout.simple_list_item_1, listItems) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                TextView textView = (TextView) super.getView(position, convertView, parent);
                if(Userdata.getInstance().getBike(position).isStolen()){
                    textView.setTextColor(Color.RED);
                } else {
                    textView.setTextColor(Color.BLACK);
                }
                return textView;
            }
        });

        bikeView.setAdapter(adapter);



        for(int i = 0; i<Userdata.getInstance().getBike().size(); i++){
            if(Userdata.getInstance().getBike(i).getOwnder().equals(Userdata.getInstance().getUsername())) {
                adapter.add(Userdata.getInstance().getBike(i).getBikeName());
            }
        }

        for(int i = 0; i<Userdata.getInstance().getBike().size(); i++){
            if(!Userdata.getInstance().getBike(i).getOwnder().equals(Userdata.getInstance().getUsername())) {
                adapter.add(Userdata.getInstance().getBike(i).getOwnder() + " " + Userdata.getInstance().getBike(i).getBikeName());
            }
        }



        email.setText(Userdata.getInstance().getEmail());
        username.setText(Userdata.getInstance().getUsername());
        phone.setText(Userdata.getInstance().getMobileNumber());

        return rootView;
    }
}