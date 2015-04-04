package bo.com.project.ronald.countriesinfo.data;

import android.net.Uri;

/**
 * Created by Administrator on 4/4/2015.
 */
public class ResultContract {

    public static final String CONTENT_AUTHORITY = "bo.com.project.ronald.countriesinfo";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final String PATH_RESULT = "result";
}
