package bo.com.project.ronald.countriesinfo;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.ArrayAdapter;

import org.json.JSONException;

/**
* Created by Administrator on 4/4/2015.
*/
public class ResultTaskAsyn extends AsyncTask<String, Void, String []>
{
    private ArrayAdapter arrayAdapter;

    private static final String LOG_TAG = ResultTaskAsyn.class.getSimpleName();

    public ResultTaskAsyn(ArrayAdapter adapter)
    {
        arrayAdapter = adapter;
    }

    @Override
    protected String[] doInBackground(String... params) {

        if (params.length != 2)
            return new String[] {"No DATA"};

        String param1 = params[0];

        String param2 = params[1];

        String resultString = Util.getJsonStringFromNetwork(param1,param2);

        try {
            return Util.parseFixtureJson(resultString);
        } catch (JSONException e) {
            Log.e(ResultTaskAsyn.LOG_TAG, "Error parsing" + e.getMessage(), e);
            e.printStackTrace();
            return new String[] {"No DATA"};
        }

   }

    @Override
    protected void onPostExecute(String[] strings)
    {
        this.arrayAdapter.clear();
        for (String result : strings) {
            this.arrayAdapter.add(result);
        }
    }
}
