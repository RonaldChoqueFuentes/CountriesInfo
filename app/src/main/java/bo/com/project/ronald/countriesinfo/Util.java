package bo.com.project.ronald.countriesinfo;

import android.net.Uri;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Formatter;

/**
 * Created by Administrator on 3/31/2015.
 */
public class Util {

    private static final String LOG_TAG = Util.class.getSimpleName();

    public static String getJsonStringFromNetwork(String query, String fuzzy) {
        Log.d(LOG_TAG, "Starting network connection");
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;

        String maxRows="10";
        String userName="Ronald.Choque";

        try {


            final String Cities_BASE_URL = " http://api.geonames.org/searchJSON";
            final String QUERY_PATH = "q";
            final String FUZZY_PATH = "fuzzy";
            final String MAX_ROWS_PATH = "maxRows";

            final String USER_PATH = "username";


            Uri builtUri = Uri.parse(Cities_BASE_URL).buildUpon()
                    .appendQueryParameter(QUERY_PATH, query)
                    .appendQueryParameter(FUZZY_PATH, fuzzy)
                    .appendQueryParameter(MAX_ROWS_PATH, maxRows)
                    .appendQueryParameter(USER_PATH, userName)
                    .build();

            URL url = new URL(builtUri.toString());

            urlConnection = (HttpURLConnection)url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();

            if (inputStream == null)
                return "";
            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;

            while ((line = reader.readLine()) != null) {
                buffer.append(line).append("\n");
            }

            if (buffer.length() == 0)
                return "";

            return buffer.toString();
        } catch (IOException e) {
            Log.e(LOG_TAG, e.getMessage(), e);
            e.printStackTrace();
        } finally {
            if (urlConnection != null)
                urlConnection.disconnect();
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    Log.e(LOG_TAG, "Error closing stream", e);
                    e.printStackTrace();
                }
            }
        }

        return "";
    }

    public static String[]  parseFixtureJson(String fixtureJson) throws JSONException {

        JSONObject jsonObject = new JSONObject(fixtureJson);
        ArrayList<String> result = new ArrayList<String>();

        final String LIST = "geonames";

        final String RESULT = "result";



        // final String Item1="lng";
        // final String Item2="geonameId";
        final String COUNTRY_CODE="countryCode";
        //final String Item4="name";
        //final String Item5="fclName";
        final String TOPONOMY_Name="toponymName";
        //final String Item7="fcodeName";
        final String WIKIPEDIA="wikipedia";
        //final String Item9="lat";
        //final String Item10="fcl";
        final String POPULATION="population";
        //final String Item12="fcode";


        JSONArray fixturesArray = jsonObject.getJSONArray(LIST);

        for (int i = 0; i < fixturesArray.length(); i++) {
            String cityName;
            String countryCode;
            int population;
            JSONObject matchObject = fixturesArray.getJSONObject(i);

            cityName = matchObject.getString(TOPONOMY_Name);

            countryCode = matchObject.getString(COUNTRY_CODE);
            population = matchObject.getInt(POPULATION);

            String resultString = new Formatter().format("%s: %s - %d", countryCode, cityName, population).toString();
            result.add(resultString);
        }
        return result.toArray(new String[result.size()]);
    }

    }

