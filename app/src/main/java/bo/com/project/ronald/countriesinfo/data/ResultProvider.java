package bo.com.project.ronald.countriesinfo.data;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

/**
 * Created by Administrator on 4/4/2015.
 */
public class ResultProvider extends ContentProvider {

    private static final UriMatcher uriMatcher = buildUriMatcher();

    static final int RESULT = 100;
    static final int RESULT_WITH_CITY = 101;

    private ResultDbHelper dbHelper;

    private static final String citySelection = ResultEntry.TABLE_NAME + "." + ResultEntry.COLUMN_CITY_NAME + "= ? ";

    static UriMatcher buildUriMatcher() {
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = ResultContract.CONTENT_AUTHORITY;

        matcher.addURI(authority, ResultContract.PATH_RESULT, RESULT);
        matcher.addURI(authority, ResultContract.PATH_RESULT + "/#", RESULT_WITH_CITY );

        return matcher;
    }


    private Cursor getResultByCity(Uri uri, String []projection, String sortOrder) {
        String citySetting = ResultEntry.getCityFromUri(uri);


        String [] selectionArgs;
        String selection;

        selection = citySelection;
        selectionArgs = new String [] {citySetting};


        SQLiteDatabase db = dbHelper.getReadableDatabase();

        return db.query(ResultEntry.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                sortOrder);

    }

    @Override
    public boolean onCreate() {
        dbHelper = new ResultDbHelper(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        Cursor retCursor;

        switch (uriMatcher.match(uri)) {
            case RESULT:
                retCursor = dbHelper.getReadableDatabase().query(ResultEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                break;
            case RESULT_WITH_CITY:
                retCursor = getResultByCity(uri, projection, sortOrder);
                break;


            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        retCursor.setNotificationUri(getContext().getContentResolver(), uri);

        return retCursor;
    }

    @Override
    public String getType(Uri uri) {
        int match = uriMatcher.match(uri);

        switch (match) {
            case RESULT:
                return ResultEntry.CONTENT_TYPE;
            case RESULT_WITH_CITY:
                return ResultEntry.CONTENT_TYPE;
          //  case RESULT_WITH_TEAM_AND_DATE:
            //    return ResultContract.ResultEntry.CONTENT_ITEM_TYPE;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int match = uriMatcher.match(uri);
        Uri returnUri;

        if (match == RESULT) {
          //  normalizeDate(values);
            long id = db.insert(ResultEntry.TABLE_NAME, null, values);

            if (id > 0)
                returnUri =ResultEntry.buildResulUri(id);
            else
                throw new SQLException("Failed to insert row into " + uri);
        } else {
            throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);

        return returnUri;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int match = uriMatcher.match(uri);
        int deleted;

        if (selection == null)
            selection = "1";

        if (match == RESULT) {
            deleted = db.delete(ResultEntry.TABLE_NAME, selection, selectionArgs);
        } else {
            throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        if (deleted != 0)
            getContext().getContentResolver().notifyChange(uri, null);

        return deleted;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int match = uriMatcher.match(uri);
        int updated;

        if (selection == null)
            selection = "1";

        if (match == RESULT) {
            updated = db.update(ResultEntry.TABLE_NAME, values, selection, selectionArgs);
        } else {
            throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        if (updated != 0)
            getContext().getContentResolver().notifyChange(uri, null);

        return updated;
    }

    @Override
    public void shutdown() {
        dbHelper.close();
        super.shutdown();
    }
}
