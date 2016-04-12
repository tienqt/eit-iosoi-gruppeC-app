package com.smartlock.eit.smartlock;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by tien on 4/2/2016.
 */
public class Userdata {

    private String loginDataFileName = "logindata";
    private FileOutputStream outputStream;

    String username;
    String email;
    String mobileNumber;
    String user_id;
    ArrayList<Bike> bikes;

    boolean startMainScreenn = false;
    boolean updatelock;

    ProfileFrag profileFrag;
    HomeFrag homeFrag;

    Context context;

    private static Userdata ourInstance;
    public static Userdata getInstance() {
        if(ourInstance == null){
            ourInstance = new Userdata();
        }
        return ourInstance;
    }
    private Userdata(){
        bikes = new ArrayList<Bike>();
        username = "Could not load data.";
        email = "Could not load data.";
        mobileNumber = "Could not load data.";
        profileFrag = null;
        homeFrag = null;
        updatelock = false;
    }

    public void loeadDataFromDB(String username, boolean startMainActivity){
        if(!updatelock) {
            updatelock = true;
            startMainScreenn = startMainActivity;
            LoadDataFromDBTask userdata = new LoadDataFromDBTask();
            userdata.execute(username);
        }
    }

    public void setBikeLock(String bikeID, boolean locked){
        SetBikeLockStatusTask setBikeLockStatusTask = new SetBikeLockStatusTask(locked);
        setBikeLockStatusTask.execute(bikeID);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public void addBike(Bike bike){
        bikes.add(bike);
    }

    public void removeBike(Bike bike){
        bikes.remove(bike);
    }

    public Bike getBike(int id) {
        return bikes.get(id);
    }


    public boolean writeDataToStorage(){
        return true;
    }

    public ArrayList<Bike> getBike(){
        return bikes;
    }

    public void setContext(Context context){
        this.context = context;
    }

    public Context getContext() {
        return context;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public ProfileFrag getProfileFrag() {
        return profileFrag;
    }

    public void setProfileFrag(ProfileFrag profileFrag) {
        this.profileFrag = profileFrag;
    }

    public HomeFrag getHomeFrag() {
        return homeFrag;
    }

    public void setHomeFrag(HomeFrag homeFrag) {
        this.homeFrag = homeFrag;
    }

    public class Bike {
        String BikeId;
        String BikeName;
        String ownder;

        boolean locked;
        boolean stolen;

        public String getBikeId() {
            return BikeId;
        }

        public void setBikeId(String bikeId) {
            BikeId = bikeId;
        }

        public String getBikeName() {
            return BikeName;
        }

        public void setBikeName(String bikeName) {
            BikeName = bikeName;
        }

        public boolean isStolen() {
            return stolen;
        }

        public void setStolen(boolean status) {
            this.stolen = status;
        }

        public Bike(String bikeName, String bikeId) {
            BikeId = bikeId;
            BikeName = bikeName;
        }

        public String getOwnder() {
            return ownder;
        }

        public void setOwnder(String ownder) {
            this.ownder = ownder;
        }

        public boolean isLocked() {
            return locked;
        }

        public void setLocked(boolean locked) {
            this.locked = locked;
        }
    }

    public class LoadDataFromDBTask extends AsyncTask<String, Void, Void>{

        String data;
        Activity activity;


        @Override
        protected Void doInBackground(String... params) {
            String dataUrl = "http://drawroulette.com/eit/getuserdata.php?username=" + params[0];

            data = utils.getHttp(dataUrl, getContext());

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            String dataparts[] = data.split(",");
            Userdata.getInstance().setUser_id(dataparts[0]);
            Userdata.getInstance().setUsername(dataparts[1]);
            Userdata.getInstance().setEmail(dataparts[2]);
            Userdata.getInstance().setMobileNumber(dataparts[3]);

            if(bikes.size() > 0){
                bikes.clear();
            }

            for(int i = 4 ; i<dataparts.length; i+= 5){
                Bike newBike = new Bike(dataparts[i], dataparts[i+1]);
                if(dataparts[i+2].toUpperCase().equals("OK")){
                    newBike.setStolen(false);
                } else {
                    newBike.setStolen(true);
                }

                if(dataparts[i+3].toUpperCase().equals("0")){
                    newBike.setLocked(false);
                } else {
                    newBike.setLocked(true);
                }

                newBike.setOwnder(dataparts[i + 4]);
                Userdata.getInstance().addBike(newBike);

            }

            if(profileFrag != null){
                profileFrag.updateUI();
            }

            if(homeFrag != null){
                homeFrag.updateUI();
            }

            updatelock = false;

            if(context != null){
                if(startMainScreenn) {
                    Intent mainActivityIntent = new Intent(context, MenuActivity.class);
                    mainActivityIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    context.startActivity(mainActivityIntent);
                }
            }
        }

    }

    public class SetBikeLockStatusTask extends AsyncTask<String, Void, Void> {

        boolean locked;
        String bikeid;

        public SetBikeLockStatusTask(boolean locked) {
            this.locked = locked;
            this.bikeid = bikeid;
        }

        @Override
        protected Void doInBackground(String... params) {
            String lockUrl = "http://drawroulette.com/eit/lockbike.php?bikeid=" + params[0];
            String unlockUrl = "http://drawroulette.com/eit/unlockbike.php?bikeid=" + params[0];
            bikeid = params[0];

            if(locked) {
                utils.getHttp(lockUrl, getContext());
            } else {
                 utils.getHttp(unlockUrl, getContext());
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if(homeFrag != null){
                homeFrag.updateLockBtn();
            }
        }
    }


}
