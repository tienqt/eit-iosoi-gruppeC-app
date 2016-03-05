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
import android.view.animation.Transformation;
import android.widget.Button;
import android.widget.TextView;

public class LoginActivity extends Activity {

    TextView email_text;
    TextView pwd_text;
    Button logreg_button;

    TextView email_err;
    TextView pwd_err;

    public static Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        context = this;

        email_text = (TextView)findViewById(R.id.email_text);
        pwd_text = (TextView)findViewById(R.id.pwd_text);
        logreg_button = (Button)findViewById(R.id.logreg_button);

        email_text.setText("Email");
        pwd_text.setText("Password (optional)");
        pwd_text.setTransformationMethod(null);

        email_text.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus && email_text.getText().toString().equals("Email")) {
                    email_text.setText("");
                } else {
                    if (email_text.getText().toString().equals("")) {
                        email_text.setText("Email");
                    }
                }
            }
        });

        pwd_text.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus && pwd_text.getText().toString().equals("Password (optional)")) {
                    pwd_text.setText("");
                    pwd_text.setTransformationMethod(new PasswordTransformationMethod());
                } else {
                    if (pwd_text.getText().toString().equals("")) {
                        pwd_text.setText("Password (optional)");
                        pwd_text.setTransformationMethod(null);
                    }
                }
            }
        });

        email_err = (TextView) findViewById(R.id.email_err);
        pwd_err = (TextView)findViewById(R.id.pwd_err);

        logreg_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!(email_text.getText().toString().equals(""))&& !(email_text.getText().toString().equals("Email")) && (pwd_text.getText().toString().equals("")||pwd_text.getText().toString().equals("Password (optional)"))){

                    Intent register_intent = new Intent(context, RegisterActivity.class);
                    startActivity(register_intent);

                }

                if((email_text.getText().toString().equals("")||email_text.getText().toString().equals("Email")) && !(pwd_text.getText().toString().equals(""))){

                    email_err.setVisibility(View.VISIBLE);
                    pwd_err.setVisibility(View.VISIBLE);

                }
            }

        });
    }
}
