package com.smartlock.eit.smartlock;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.SpinnerAdapter;

import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by tien on 4/2/2016.
 */
public class Userdata {

    public class Bike {
        String BikeId;
        String BikeName;
        String ownder;

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
    }

    public class LoadDataFromDBTask extends AsyncTask<String, Void, Void>{

        String data;

        @Override
        protected Void doInBackground(String... params) {
            String dataUrl = "http://drawroulette.com/eit/getuserdata.php?username=" + params[0];
            try {
                data = utils.getHttp(dataUrl);
            } catch (Exception e) {
                e.printStackTrace();
            }
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

            if(bikes.size() > 0){
                bikes.clear();
            }

            for(int i = 4 ; i<dataparts.length; i+= 4){
                Bike newBike = new Bike(dataparts[i], dataparts[i+1]);
                if(dataparts[i+2].equals("OK")){
                    newBike.setStolen(false);
                } else {
                    newBike.setStolen(true);
                }
                newBike.setOwnder(dataparts[i + 3]);
                Userdata.getInstance().addBike(newBike);

            }

            if(context != null){
               if(startMainScreenn) {
                   Intent mainActivityIntent = new Intent(context, MenuActivity.class);
                   mainActivityIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                   context.startActivity(mainActivityIntent);
               }
            }
        }
    }

    private String loginDataFileName = "logindata";
    private FileOutputStream outputStream;

    String username;
    String email;
    String mobileNumber;
    String user_id;
    ArrayList<Bike> bikes;

    boolean startMainScreenn = false;

    ListAdapter profileAdapter;
    SpinnerAdapter homeAdapter;
    List<String> homelist;
    List<String> profilelist;

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
       if(bikes.size() >= id) {
           return bikes.get(id);
       }
        return null;
    }

    public void loeadDataFromDB(String username, boolean startMainActivity){
        startMainScreenn = startMainActivity;
        LoadDataFromDBTask userdata = new LoadDataFromDBTask();
        userdata.execute(username);
    }

    public boolean loadUserDataFromFile(){

        return true;
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

    public List<String> getHomelist() {
        return homelist;
    }

    public void setHomelist(List<String> homelist) {
        this.homelist = homelist;
    }

    public List<String> getProfilelist() {
        return profilelist;
    }

    public void setProfilelist(List<String> profilelist) {
        this.profilelist = profilelist;
    }

    public void setProfileAdapter(ListAdapter profileAdapter) {
        this.profileAdapter = profileAdapter;
    }


    public void setHomeAdapter(SpinnerAdapter homeAdapter) {
        this.homeAdapter = homeAdapter;
    }

    public void updateUI(){
        for(int bikeid = 0; bikeid<bikes.size(); bikeid++){
            if(Userdata.getInstance().getBike(bikeid).getOwnder().equals(Userdata.getInstance().getUsername())) {
                homelist.add(Userdata.getInstance().getBike(bikeid).getBikeName());
                profilelist.add(Userdata.getInstance().getBike(bikeid).getBikeName());
            }
            if(!Userdata.getInstance().getBike(bikeid).getOwnder().equals(Userdata.getInstance().getUsername())) {
                homelist.add(Userdata.getInstance().getBike(bikeid).getOwnder() + " " + Userdata.getInstance().getBike(bikeid).getBikeName());
                profilelist.add(Userdata.getInstance().getBike(bikeid).getOwnder() + " " + Userdata.getInstance().getBike(bikeid).getBikeName());
            }
        }
    }
}
