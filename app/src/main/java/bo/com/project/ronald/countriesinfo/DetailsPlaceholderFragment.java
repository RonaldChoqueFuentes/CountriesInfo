package bo.com.project.ronald.countriesinfo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * A placeholder fragment containing a simple view.
 */
public class DetailsPlaceholderFragment extends Fragment {

    public DetailsPlaceholderFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_details, container, false);
        String result = getActivity().getIntent().getStringExtra(Intent.EXTRA_TEXT);
        TextView detailsText = (TextView)rootView.findViewById(R.id.details_text);

        detailsText.setText(result);

        return rootView;
    }
}
