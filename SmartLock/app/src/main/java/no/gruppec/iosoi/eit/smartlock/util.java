package no.gruppec.iosoi.eit.smartlock;

import android.util.Patterns;

import java.util.regex.Pattern;

/**
 * Created by thorbjornsomod on 16/03/2016.
 */
public class util {

    public static boolean validEmail(String email){

        Pattern pattern = Patterns.EMAIL_ADDRESS;
        return pattern.matcher(email).matches();

    }

}
