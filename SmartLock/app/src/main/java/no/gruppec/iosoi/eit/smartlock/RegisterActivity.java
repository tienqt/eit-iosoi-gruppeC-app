package no.gruppec.iosoi.eit.smartlock;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

public class RegisterActivity extends Activity {

    TextView new_user_name;
    TextView new_user_email;
    TextView new_user_phone;
    TextView new_user_pwd;
    TextView new_user_verify_pwd;

    Button add_new_user_btn;

    TextView name_err;
    TextView email_err;
    TextView phone_err;
    TextView pwd_err;
    TextView pwd_verify_err;

    public Context register_context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_register);

        register_context = this;

        new_user_name = (TextView)findViewById(R.id.new_user_name);
        new_user_email = (TextView)findViewById(R.id.new_user_email);
        new_user_phone = (TextView)findViewById(R.id.new_user_phone);
        new_user_pwd = (TextView)findViewById(R.id.new_user_pwd);
        new_user_verify_pwd = (TextView)findViewById(R.id.new_user_verity_pwd);

        add_new_user_btn = (Button)findViewById(R.id.add_new_user_btn);

        new_user_name.setText("Name");
        new_user_email.setText("E-mail");
        new_user_phone.setText("Tlf.");
        new_user_pwd.setText("Password");
        new_user_pwd.setTransformationMethod(null);
        new_user_verify_pwd.setText("Verify password");
        new_user_verify_pwd.setTransformationMethod(null);


        new_user_name.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus && new_user_name.getText().toString().equals("Name")) {
                    new_user_name.setText("");
                } else {
                    if (new_user_name.getText().toString().equals("")) {
                        new_user_name.setText("Name");
                    }
                }
            }
        });

        new_user_email.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus && new_user_email.getText().toString().equals("E-mail")) {
                    new_user_email.setText("");
                } else {
                    if (new_user_email.getText().toString().equals("")) {
                        new_user_email.setText("E-mail");
                    }
                }
            }
        });

        new_user_phone.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus && new_user_phone.getText().toString().equals("Tlf.")) {
                    new_user_phone.setText("");
                } else {
                    if (new_user_phone.getText().toString().equals("")) {
                        new_user_phone.setText("Tlf");
                    }
                }
            }
        });

        new_user_pwd.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus && new_user_pwd.getText().toString().equals("Password")) {
                    new_user_pwd.setText("");
                    new_user_pwd.setTransformationMethod(new PasswordTransformationMethod());
                } else {
                    if (new_user_pwd.getText().toString().equals("")) {
                        new_user_pwd.setText("Password");
                        new_user_pwd.setTransformationMethod(null);
                    }
                }
            }
        });

        new_user_verify_pwd.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus && new_user_verify_pwd.getText().toString().equals("Verify password")) {
                    new_user_verify_pwd.setText("");
                    new_user_verify_pwd.setTransformationMethod(new PasswordTransformationMethod());
                } else {
                    if (new_user_verify_pwd.getText().toString().equals("")) {
                        new_user_verify_pwd.setText("Verify password");
                        new_user_verify_pwd.setTransformationMethod(null);
                    }
                }
            }
        });

        name_err = (TextView) findViewById(R.id.name_err);
        email_err = (TextView) findViewById(R.id.email_err);
        phone_err = (TextView) findViewById(R.id.phone_err);
        pwd_err = (TextView) findViewById(R.id.pwd_err);
        pwd_verify_err = (TextView) findViewById(R.id.pwd_verify_err);

        add_new_user_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(new_user_name.getText().toString().equals("")||new_user_name.getText().toString().equals("Name")){

                    Toast.makeText(RegisterActivity.this, "Not all requirements are met.", Toast.LENGTH_LONG).show();
                    name_err.setVisibility(View.VISIBLE);

                }

                if(new_user_email.getText().toString().equals("")||new_user_email.getText().toString().equals("E-mail")){

                    Toast.makeText(RegisterActivity.this, "Not all requirements are met.", Toast.LENGTH_LONG).show();
                    email_err.setVisibility(View.VISIBLE);

                }

                if(new_user_phone.getText().toString().equals("")||new_user_phone.getText().toString().equals("Tlf.")){

                    Toast.makeText(RegisterActivity.this, "Not all requirements are met.", Toast.LENGTH_LONG).show();
                    phone_err.setVisibility(View.VISIBLE);

                }

                if(new_user_pwd.getText().toString().equals("")||new_user_pwd.getText().toString().equals("Password")){

                    Toast.makeText(RegisterActivity.this, "Not all requirements are met.", Toast.LENGTH_LONG).show();
                    pwd_err.setVisibility(View.VISIBLE);

                }

                if(new_user_verify_pwd.getText().toString().equals("")||new_user_verify_pwd.getText().toString().equals("Verify password")){

                    Toast.makeText(RegisterActivity.this, "Not all requirements are met.", Toast.LENGTH_LONG).show();
                    pwd_verify_err.setVisibility(View.VISIBLE);

                }

                if(!(new_user_pwd.getText().toString() != new_user_verify_pwd.getText().toString() )){

                    Toast.makeText(RegisterActivity.this, "Unable to verify password.", Toast.LENGTH_LONG).show();
                    pwd_err.setVisibility(View.VISIBLE);
                    pwd_verify_err.setVisibility(View.VISIBLE);

                }

                if(!util.validEmail(new_user_email.getText().toString())){

                    Toast.makeText(RegisterActivity.this, "Please provide a valid e-mail address.", Toast.LENGTH_LONG).show();
                    email_err.setVisibility(View.VISIBLE);

                }

                else{

                    String line;
                    URL url = null;
                    try {
                        url = new URL("http://drawroulette.com/eit/adduser.php?username=" + new_user_name.getText().toString() + "&email=" + new_user_email.getText().toString() + "&password=" + new_user_pwd.getText().toString() + "&mobnr=" + new_user_phone.getText().toString());
                        System.out.println(url.toString());
                        BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));

                        while((line = in.readLine()) != null){
                            if(line.equals("success")){

                                //  Intent main_intent = new Intent(register_context, MainActivity.class);
                                //startActivity(main_intent);

                                Toast.makeText(RegisterActivity.this, "Success", Toast.LENGTH_LONG).show();

                            } else {

                                Toast.makeText(RegisterActivity.this, "User already exists.", Toast.LENGTH_LONG).show();

                            }
                        }
                        in.close();
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }

            }
        });

    }

}


