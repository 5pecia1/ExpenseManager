package sol_5pecia1.expense_manager.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;

/**
 * Created by sol on 2016-12-20.
 */
public class AccountSQLiteOpenHelper
        extends SQLiteOpenHelper
        implements  AccountModel {
    private final static String DB_NAME = "Account.db";
    private final static int VERSION = 1;

    private final static String TABLE_NAME = "account";
    private final static String ID = "_id";
    private final static String MONEY = "money";
    private final static String CLASSIFICATION = "classification";
    private final static String SAVE_DATE = "saved_date";
    private final static String BESIDES = "besides";

    private final static String CREATE_QUERY
            = "CREATE TABLE " + TABLE_NAME
            + "("
            + ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, "
            + MONEY + " INTEGER, "
            + CLASSIFICATION + " VARCHAR, "
            + SAVE_DATE + " DATE, "
            + BESIDES + " TEXT"
            + ");";

    private final static SimpleDateFormat DATE_FORMAT
            = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
    private final String[] classificationItems;

    public AccountSQLiteOpenHelper(@NonNull Context context
            , @NonNull String[] classificationItems) {
        super(context,DB_NAME, null, VERSION);
        this.classificationItems = classificationItems;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_QUERY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //TODO If database needs upgrade, when you write.
        Log.e(this.getClass().getName(), "you did not write this code");
    }

    @Override
    public long addAccount(@NonNull Money money, @NonNull String classification
            , @NonNull Calendar saveDate, @NonNull String besides) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(MONEY, money.getMoney());
        contentValues.put(CLASSIFICATION, classification);
        contentValues.put(SAVE_DATE
                , DATE_FORMAT.format(saveDate.getTime()));
        contentValues.put(BESIDES, besides);

        SQLiteDatabase db = getWritableDatabase();
        long success = db.insert(TABLE_NAME, null, contentValues);
        
        db.close();
        return success;
    }

    @NonNull
    public Money getSelectedRangeUsage(@NonNull Calendar start
            , @NonNull Calendar end) {
        if (start.compareTo(end) > 0) {
            return new Money();
        }
        String moneySum = "moneySum";
        String[] columns = new String[]{
                AccountSQLiteOpenHelper.ID
                , "SUM(" + MONEY + ") AS " + moneySum
        };
        String useSelection
                = "(" + SAVE_DATE + " BETWEEN ? AND ?" + ")"
                + " AND "
                + "(" + CLASSIFICATION + "!= ?" + ")";
        String incomeSelection
                = "(" + SAVE_DATE + " BETWEEN ? AND ?" + ")" +
                " AND " +
                "(" + CLASSIFICATION + "== ?" + ")";
        String[] selectionArgs = new String[]{
                DATE_FORMAT.format(start.getTime())
                , DATE_FORMAT.format(end.getTime())
                , classificationItems[classificationItems.length - 1]
        };

        String groupBy = null;
        String having = null;
        String orderBy = null;

        SQLiteDatabase db = getReadableDatabase();
        Cursor useCursor = db.query(TABLE_NAME
                , columns
                , useSelection
                , selectionArgs
                , groupBy
                , having
                , orderBy);
        Cursor incomeCursor = db.query(TABLE_NAME
                , columns
                , incomeSelection
                , selectionArgs
                , groupBy
                , having
                , orderBy);

        int useMoney = (useCursor.moveToFirst())
                ? useCursor.getInt(useCursor.getColumnIndex(moneySum))
                : 0;
        int incomeMoney = (incomeCursor.moveToFirst())
                ? incomeCursor.getInt(useCursor.getColumnIndex(moneySum))
                : 0;

        useCursor.close();
        incomeCursor.close();

        db.close();

        return new Money(useMoney - incomeMoney);
    }

    @NonNull
    @Override
    public Money getPreviousDayOfWeekAverage(@DayOfWeek int dayOfWeek) {
        /*
         * Change week of day for sqlite
         * Sqlite : day of week 0-6 with Sunday==0
         * Calendar : day of week 1-7 with Sunday==1
         */
        dayOfWeek--;
        Calendar current = GregorianCalendar.getInstance();
        String averageColnum = "aver";

        String[] columns = new String[]{
                "SUM(" + MONEY + ")"
                        + "/COUNT(DISTINCT " + SAVE_DATE + ""
                        + ") AS " + averageColnum
        };
        String selection
                = "strftime('%w', " + SAVE_DATE + ")=?"
                + " AND "
                + SAVE_DATE + "<?";
        String[] selectionArgs = new String[]{
                String.valueOf(dayOfWeek)
                , DATE_FORMAT.format(current.getTime())
        };
        String groupBy = null;
        String having = null;
        String orderBy = null;

        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.query(TABLE_NAME
                , columns
                , selection
                , selectionArgs
                , groupBy
                , having
                , orderBy);

        int average = (cursor.moveToFirst())
                ? cursor.getInt(cursor.getColumnIndex(averageColnum))
                : 0;

        db.close();

        return new Money(average);
    }
}
