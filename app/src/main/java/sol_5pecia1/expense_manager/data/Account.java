package sol_5pecia1.expense_manager.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by sol on 2016-12-20.
 */

public class Account extends SQLiteOpenHelper implements  AccountModel {
    private final static String DB_NAME = "Account.db";
    private final static int VERSION = 1;

    private final static String TABLE_NAME = "account";
    private final static String ID = "_id";
    private final static String MONEY = "money";
    private final static String CLASSIFICATION = "classification";
    private final static String SAVE_DATE = "saved_date";
    private final static String BESIDES = "besides";

    private final static String CREATE_QUERY
            = "CREATE TABLE " + Account.TABLE_NAME
            + "("
            + ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, "
            + MONEY + " INTEGER, "
            + CLASSIFICATION + " VARCHAR, "
            + SAVE_DATE + " DATE, "
            + BESIDES + " TEXT"
            + ");";

    public Account(Context context) {
        super(context, Account.DB_NAME, null, Account.VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Account.CREATE_QUERY);
        Log.e("test", "create");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //TODO If database needs upgrade, when you write.
        Log.e(this.getClass().getName(), "you did not write this code");
    }

    @Override
    public long addAccount(Money money, String classification, Calendar saveDate, String besides) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        ContentValues contentValues = new ContentValues();
        contentValues.put(Account.MONEY, money.toString());
        contentValues.put(Account.CLASSIFICATION, classification);
        contentValues.put(Account.SAVE_DATE
                , dateFormat.format(saveDate.getTime()));
        contentValues.put(Account.BESIDES, besides);

        SQLiteDatabase db = getWritableDatabase();
        long success = db.insert(
                Account.TABLE_NAME
                , null
                , contentValues
        );
        db.close();
        return success;
    }
}
