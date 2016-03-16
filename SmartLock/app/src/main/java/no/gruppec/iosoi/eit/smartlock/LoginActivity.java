package no.gruppec.iosoi.eit.smartlock;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends Activity {

    TextView email_text;
    TextView pwd_text;
    Button log_button;
    Button reg_button;

    TextView email_err;
    TextView pwd_err;

    public static Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);

        context = this;

        email_text = (TextView)findViewById(R.id.email_text);
        pwd_text = (TextView)findViewById(R.id.pwd_text);
        log_button = (Button)findViewById(R.id.log_button);
        reg_button = (Button)findViewById(R.id.reg_button);

        email_text.setText("E-mail");
        pwd_text.setText("Password");
        pwd_text.setTransformationMethod(null);

        email_text.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus && email_text.getText().toString().equals("E-mail")) {
                    email_text.setText("");
                } else {
                    if (email_text.getText().toString().equals("")) {
                        email_text.setText("E-mail");
                    }
                }
            }
        });

        pwd_text.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus && pwd_text.getText().toString().equals("Password")) {
                    pwd_text.setText("");
                    pwd_text.setTransformationMethod(new PasswordTransformationMethod());
                } else {
                    if (pwd_text.getText().toString().equals("")) {
                        pwd_text.setText("Password");
                        pwd_text.setTransformationMethod(null);
                    }
                }
            }
        });

        email_err = (TextView) findViewById(R.id.email_err);
        pwd_err = (TextView)findViewById(R.id.pwd_err);

        log_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!(email_text.getText().toString().equals(""))&& !(email_text.getText().toString().equals("Email")) && (pwd_text.getText().toString().equals("")||pwd_text.getText().toString().equals("Password"))){

                    if(util.validEmail(email_text.getText().toString())) {

                        Toast.makeText(LoginActivity.this, "Please provide a password.", Toast.LENGTH_LONG).show();
                        pwd_err.setVisibility(View.VISIBLE);

                    }

                    else{

                        Toast.makeText(LoginActivity.this, "Please enter a valid e-mail address.", Toast.LENGTH_LONG).show();
                        email_err.setVisibility(View.VISIBLE);

                    }

                }

                if((email_text.getText().toString().equals("")||email_text.getText().toString().equals("E-mail")) && !(pwd_text.getText().toString().equals(""))){

                    email_err.setVisibility(View.VISIBLE);
                    Toast.makeText(LoginActivity.this, "Please provide a valid e-mail address.", Toast.LENGTH_LONG).show();

                }

                else{

                    //Do something.

                }

            }

        });

        reg_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent register_intent = new Intent(context, RegisterActivity.class);
                startActivity(register_intent);

            }
        });

    }
}
