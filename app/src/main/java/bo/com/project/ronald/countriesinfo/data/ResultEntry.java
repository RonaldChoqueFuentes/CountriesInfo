package bo.com.project.ronald.countriesinfo.data;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by Administrator on 4/4/2015.
 */
public  final class ResultEntry implements BaseColumns
{
    public static final Uri CONTENT_URI = ResultContract.BASE_CONTENT_URI.buildUpon().appendPath(ResultContract.PATH_RESULT).build();

    public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + ResultContract.CONTENT_AUTHORITY + "/" + ResultContract.PATH_RESULT;
    public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + ResultContract.CONTENT_AUTHORITY + "/" + ResultContract.PATH_RESULT;

    public static final String TABLE_NAME = "result";

    public static final String COLUMN_CITY_NAME = "city_name";
    public static final String COLUMN_COUNTRY_NAME = "country_name";
    public static final String COLUMN_POPULATION = "city_population";


    public static Uri buildCity(String city) {

    return CONTENT_URI.buildUpon().appendPath(city).build();
    }

    public static String getCityFromUri(Uri uri) {
        return uri.getPathSegments().get(1);
    }

    public static Uri buildResulUri(long id) {
        return ContentUris.withAppendedId(CONTENT_URI, id);
    }
}
