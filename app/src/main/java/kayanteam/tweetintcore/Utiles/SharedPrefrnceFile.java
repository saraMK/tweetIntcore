package kayanteam.tweetintcore.Utiles;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;

import java.util.Locale;

/**
 * Created by mosta on 1/7/2018.
 */

public class SharedPrefrnceFile {
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private Context context;
    private java.lang.String iso_code;

    public SharedPrefrnceFile(Context context){
        this.context=context;
        sharedPreferences=context.getSharedPreferences("intcoreSharedPrefrence",context.MODE_PRIVATE);
        editor=sharedPreferences.edit();
    }

    public void saveSharedValue(String key, String value){
        editor.putString(key,value);
        editor.commit();
    }

    public String getSharedValue(String key , String expectedValue)
    {
        return sharedPreferences.getString(key,expectedValue);
    }


    
    public void set_loclization(String lang , Context context){

        if (lang.equals("1"))
            iso_code="en";
        else
            iso_code="ar";

        Locale locale = new Locale(iso_code);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        context.getResources().updateConfiguration(config, context.getResources().getDisplayMetrics());
    }
    public void removeKey(String key){
        editor.remove(key);
        editor.commit();
    }
}
