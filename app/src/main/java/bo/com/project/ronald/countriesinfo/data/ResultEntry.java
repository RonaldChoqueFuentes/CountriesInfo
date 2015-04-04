package bo.com.project.ronald.countriesinfo.data;

import android.provider.BaseColumns;

/**
 * Created by Administrator on 4/4/2015.
 */
public  final class ResultEntry implements BaseColumns
{
    public static final String TABLE_NAME = "result";

    public static final String COLUMN_HOME_TEAM = "home_team_name";
    public static final String COLUMN_AWAY_TEAM = "away_team_name";
    public static final String COLUMN_HOME_SCORE = "home_score";
    public static final String COLUMN_AWAY_SCORE = "away_score";
    public static final String COLUMN_MATCH_DATE = "match_date";
    public static final String COLUMN_TEAM_ID = "team_id";

}
