package bo.com.project.ronald.countriesinfo;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import bo.com.project.ronald.countriesinfo.data.ResultEntry;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainPlaceholderFragment extends Fragment  implements LoaderManager.LoaderCallbacks<Cursor>{

    private static final String LOG_TAG = MainPlaceholderFragment.class.getSimpleName();

    private ArrayAdapter arrayAdapter;


    private static final int RESULT_LOADER = 0;

    private ResultAdapter resultAdapter;


    public MainPlaceholderFragment() {
    }


    @Override
    public void onStart() {
        super.onStart();
        updateResults();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.results_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_refresh:
                updateResults();
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        resultAdapter = new ResultAdapter(getActivity(), null, 0);


        ListView listView = (ListView)rootView.findViewById(R.id.result_list_view);

        listView.setAdapter(resultAdapter);

      /*  listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
               Intent intent = new Intent(getActivity(), DetailsActivity.class);

                String d1 = (String) resultAdapter.getItem(position);
                intent.putExtra(Intent.EXTRA_TEXT, d1);
                startActivity(intent);

            }
        });*/


        return rootView;
    }

    private void updateResults()
    {

        String city = Util.getPreferredCity(this.getActivity());
        String fuzzy = Util.getPreferredFuzzy(this.getActivity());

        FetchResultTask resultTask = new FetchResultTask(getActivity());


        resultTask.execute(city, fuzzy);
    }


    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {

        String citySetting = Util.getPreferredCity(getActivity());

        String sortOrder = ResultEntry.COLUMN_COUNTRY_NAME + " DESC";

        Uri resultForTeamUri = ResultEntry.buildCity(citySetting);

        return new CursorLoader(getActivity(), resultForTeamUri, ResultAdapter.RESULT_COLUMNS, null, null, sortOrder);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        resultAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        resultAdapter.swapCursor(null);
    }

    public void onTeamChanged() {
        updateResults();
        getLoaderManager().restartLoader(RESULT_LOADER, null, this);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        getLoaderManager().initLoader(RESULT_LOADER, null, this);
        super.onActivityCreated(savedInstanceState);
    }

}
