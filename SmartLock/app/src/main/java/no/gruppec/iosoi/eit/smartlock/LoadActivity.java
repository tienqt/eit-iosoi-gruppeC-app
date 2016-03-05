package no.gruppec.iosoi.eit.smartlock;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class LoadActivity extends Activity {

    ImageView locked_img;
    ImageView unlocked_img;
    TextView load_text;
    ProgressBar load_progress;

    public static Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load);

        context = this;

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                doSomething();
            }
        }, 3000);

        locked_img = (ImageView)findViewById(R.id.lockedLoad);
        unlocked_img = (ImageView)findViewById(R.id.unlockedLoad);
        load_text = (TextView)findViewById(R.id.progressBarText);
        load_progress = (ProgressBar)findViewById(R.id.progressBarLoad);



        handler.postDelayed(new Runnable() {
            public void run() {
                Intent login_intent = new Intent(context, LoginActivity.class);
                startActivity(login_intent);
            }
        }, 4500);



    }

    void doSomething(){

        locked_img.setVisibility(ImageView.INVISIBLE);
        load_progress.setVisibility(ImageView.INVISIBLE);
        unlocked_img.setVisibility(ImageView.VISIBLE);
        load_text.setText("Complete.");

    }
}

