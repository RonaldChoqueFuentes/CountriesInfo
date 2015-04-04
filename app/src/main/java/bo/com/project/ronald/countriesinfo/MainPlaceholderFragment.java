package bo.com.project.ronald.countriesinfo;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
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

/**
 * A placeholder fragment containing a simple view.
 */
public class MainPlaceholderFragment extends Fragment  {

    private static final String LOG_TAG = MainPlaceholderFragment.class.getSimpleName();

   private ArrayAdapter arrayAdapter;

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

        arrayAdapter = new ArrayAdapter<String>(getActivity(),
                R.layout.results_view,
                R.id.result_text_view,
                new ArrayList<String>());

        ListView listView = (ListView)rootView.findViewById(R.id.result_list_view);

        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), DetailsActivity.class);

                String d1 = (String) arrayAdapter.getItem(position);
                intent.putExtra(Intent.EXTRA_TEXT, d1);
                startActivity(intent);
            }
        });


        return rootView;
    }

    private void updateResults() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String city = sharedPreferences.getString(getString(R.string.pref_query_key), getString(R.string.pref_Cochabamba_entry));

        String fuzzy = sharedPreferences.getString(getString(R.string.pref_fuzzy_key), getString(R.string.pref_fuzzy_default));

        ResultTaskAsyn task = new ResultTaskAsyn(arrayAdapter);

        task.execute(city, fuzzy);
    }


}
