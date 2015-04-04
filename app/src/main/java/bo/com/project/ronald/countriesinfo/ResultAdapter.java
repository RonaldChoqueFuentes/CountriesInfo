package bo.com.project.ronald.countriesinfo;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import bo.com.project.ronald.countriesinfo.data.ResultEntry;

/**
 * I will set the display
 * Created by Administrator on 4/4/2015.
 */
public class ResultAdapter extends CursorAdapter {

    public static final String[] RESULT_COLUMNS = {
            ResultEntry._ID,
            ResultEntry.COLUMN_CITY_NAME,
            ResultEntry.COLUMN_COUNTRY_NAME,
            ResultEntry.COLUMN_POPULATION
    };

    static final int COL_RESULT_ID = 0;
    static final int COL_CITY_NAME = 1;
    static final int COL_COUNTRY_NAME = 2;
    static final int COL_POPULATION = 3;

    public ResultAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
    }

    private String convertCursorToResult(Cursor cursor) {
        return String.format("%s: %s - %d",
                cursor.getString(COL_COUNTRY_NAME),
                cursor.getInt(COL_CITY_NAME),
                cursor.getInt(COL_POPULATION));
    }


    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.results_view, parent, false);

        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        TextView textView = (TextView)view;
        textView.setText(convertCursorToResult(cursor));
    }
}
