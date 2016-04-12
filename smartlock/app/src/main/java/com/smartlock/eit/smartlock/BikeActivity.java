package com.smartlock.eit.smartlock;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class BikeActivity extends AppCompatActivity {

    TextView bicycle_name;
    TextView bicycle_id;
    Button update_bike_button;

    public class NewBikeTask extends AsyncTask<String, Void, Boolean> {
       String res;
        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            if(res.equals("0")){
                Toast.makeText(getApplicationContext(), "You are already the owner of this bicycle.", Toast.LENGTH_SHORT).show();
            } else {
                Userdata.getInstance().loeadDataFromDB(Userdata.getInstance().getUsername(), false);
                onBackPressed();
            }
        }

        @Override
        protected Boolean doInBackground(String... params) {

            String add_bike_URL = "http://drawroulette.com/eit/addbike.php?bikeid=" + params[0] + "&bikeStatus=OK&owner=" + Userdata.getInstance().getUsername() + "&userid=" + Userdata.getInstance().getUser_id() + "&bikename=" + params[1];
            res = utils.getHttp(add_bike_URL, getApplicationContext());
            return null;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bike);

        bicycle_name = (TextView) findViewById(R.id.bicycle_name);
        bicycle_id = (TextView) findViewById(R.id.bicycle_id);
        update_bike_button = (Button) findViewById(R.id.update_bike_btn);

        update_bike_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NewBikeTask addbike = new NewBikeTask();
                addbike.execute(bicycle_id.getText().toString(), bicycle_name.getText().toString());

            }
        });

    }
}
