package bo.com.project.ronald.countriesinfo;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;

import java.text.ParseException;

/**
 * Created by Administrator on 4/4/2015.
 */
public class FetchResultTask  extends AsyncTask<String, Void, Void> {
    private Context context;

    private static String LOG_TAG = FetchResultTask.class.getSimpleName();

    public FetchResultTask(Context theContext) {
        context = theContext;
    }


    @Override
    protected Void doInBackground(String... params) {
        if (params.length != 2)
            return null;

        String param1 = params[0];

        String param2 = params[1];

        String resultString = Util.getJsonStringFromNetwork(param1, param2);

        try {

            Util.parseFixtureJson(resultString, context);

        } catch (JSONException e) {

            Log.e(LOG_TAG, "Error parsing" + e.getMessage(), e);
            e.printStackTrace();
            return null;

        } catch (Exception e) {

            Log.e(LOG_TAG, "Error parsing" + e.getMessage(), e);
            e.printStackTrace();
            return null;

        }

        return null;
    }
}
