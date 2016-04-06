package com.smartlock.eit.smartlock;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {

    /**
     * Represents an asynchronous registration task used to authenticate
     * the user.
     */
    public class UserRegisterTask extends AsyncTask<String, Void, Void> {

        String emailRes;
        String usernameRes;
        String loginRes;


        UserRegisterTask() {}

        @Override
        protected Void doInBackground(String... params) {
            try {
                emailRes = utils.getHttp(params[0]);
                usernameRes = utils.getHttp(params[1]);
                loginRes = utils.getHttp(params[2]);

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void params) {
            boolean rdyForReg = true;
            if (emailRes.equals("1")) {
                email.setError("Email is already used.");
                rdyForReg = false;
            }
            if(usernameRes.equals("1")){
                username.setError("Username is taken.");
                rdyForReg = false;
            }

            if(rdyForReg){
                if(loginRes.startsWith("1")){
                    Userdata.getInstance().setEmail(email.getText().toString());
                    Userdata.getInstance().setUsername(username.getText().toString());
                    Userdata.getInstance().setMobileNumber(phoneNumber.getText().toString());
                    Userdata.getInstance().getBike().clear();

                    Intent mainActivityIntent = new Intent(RegisterActivity.this, MenuActivity.class);
                    mainActivityIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(mainActivityIntent);

                }
            }
        }
    }

    TextView username;
    TextView email;
    TextView password;
    TextView confirmPassword;
    TextView phoneNumber;

    Button register_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getSupportActionBar().hide();

        username = (TextView) findViewById(R.id.reg_username);
        email = (TextView)findViewById(R.id.reg_emailTxt);
        password = (TextView)findViewById(R.id.reg_passwordOneTxt);
        confirmPassword = (TextView)findViewById(R.id.reg_passwordTwoTxt);
        phoneNumber = (TextView)findViewById(R.id.reg_phoneTxt);
        register_btn = (Button)findViewById(R.id.registerbtn);

        phoneNumber.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {

                    register();

                }
                return true;
            }
        });

        register_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                register();

            }
        });
    }

    public void register(){

        boolean readyForReg = true;

        if (username.getText().toString().equals("")) {
            username.setError("Please enter a valid username.");
        }
        if (email.getText().toString().equals("")) {
            email.setError("Please enter email.");
            readyForReg = false;
        }
        if (password.getText().toString().equals("")) {
            password.setError("Please enter a password.");
            readyForReg = false;
        }
        if (confirmPassword.getText().toString().equals("")) {
            confirmPassword.setError("Please confirm password.");
            readyForReg = false;
        }
        if (!password.getText().toString().equals(confirmPassword.getText().toString())) {
            confirmPassword.setError("Password do not match.");
            readyForReg = false;
        }
        if (phoneNumber.getText().toString().equals("")) {
            phoneNumber.setError("Please enter a valid phone number.");
        }

        String checkeusernameURL = "http://drawroulette.com/eit/checkifuserexsist.php?username=" + username.getText().toString();
        String checkemailURL = "http://drawroulette.com/eit/checkifemailexsist.php?email=" + email.getText().toString();
        String addUserUrl = "http://drawroulette.com/eit/adduser.php?username=" + username.getText().toString() + "&email=" + email.getText().toString() + "&password=" + password.getText().toString() + "&mobnr=" + phoneNumber.getText().toString();

        if (readyForReg) {
            UserRegisterTask regUser = new UserRegisterTask();
            regUser.execute(checkemailURL, checkeusernameURL, addUserUrl);
        }

    }

}
