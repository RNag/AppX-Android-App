package appx_homescreen.appx;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class AppX_Users extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 6;
    private static final String DATABASE_NAME = "AppX_Users.db";
    public static final String TABLE_USERDATA = "users";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_ADDR = "email";
    public static final String COLUMN_KEYS = "keys";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_RANK = "rank";
    public static final String COLUMN_ORG = "org";
    public static final String COLUMN_PREFIX = "gender";
    public static final String COLUMN_EDLEVEL = "education";
    public static final String COLUMN_AGE = "age";

    public AppX_Users(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase Database) {
        String query = "create table " +
                TABLE_USERDATA +
                " (" +
                COLUMN_ID + " integer primary key autoincrement not null," +
                COLUMN_ADDR + " text," +
                COLUMN_KEYS + " text," +
                COLUMN_NAME + " text," +
                COLUMN_RANK + " text," +
                COLUMN_ORG + " text," +
                COLUMN_PREFIX + " int," +
                COLUMN_EDLEVEL + " int," +
                COLUMN_AGE + " int" +
                ");";
        Database.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERDATA);
        onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int newVersion, int oldVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERDATA);
        onCreate(db);
    }

    public void addUser(InputData userData) {
        ContentValues values = new ContentValues();

        values.put(COLUMN_ADDR, userData.get_userName());
        values.put(COLUMN_KEYS, userData.get_userPass());
        values.put(COLUMN_NAME, userData.get_fullName());
        values.put(COLUMN_RANK, userData.get_Occupation());
        values.put(COLUMN_ORG, userData.get_Org());
        values.put(COLUMN_PREFIX, userData.get_prefix());
        values.put(COLUMN_EDLEVEL, userData.get_edLevel());
        values.put(COLUMN_AGE, userData.get_age());

        SQLiteDatabase Database = getWritableDatabase();
        Database.insert(TABLE_USERDATA, null, values);
        Database.close();

    }

    public boolean checkAddr(String userValue) {
        SQLiteDatabase Database = getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_USERDATA + " WHERE " + COLUMN_ADDR + " = '" + userValue + "'";
        Cursor c = Database.rawQuery(query, null);
        boolean email_fieldExists = false;
        if (c.moveToFirst()) {
            email_fieldExists = true;
        }
        return email_fieldExists;
    }

    public boolean ValidateDataEntry(String userValue, String passValue) {
        SQLiteDatabase Database = getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_USERDATA + " WHERE " + COLUMN_ADDR + " = '" + userValue + "' AND " + COLUMN_KEYS + " = '" + passValue + "'";
        Cursor c = Database.rawQuery(query, null);

        boolean checkIfValid = false;
        if (c.moveToFirst()) {
            checkIfValid = true;
        }
        c.close();
        Database.close();
        return checkIfValid;
    }

    public String mostRecentEntry() {
        SQLiteDatabase Database = getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_USERDATA + " ORDER BY " + COLUMN_ID + " DESC LIMIT 1";
        Cursor c = Database.rawQuery(query, null);
        if (c.moveToFirst()) {
            return c.getString(c.getColumnIndex("email"));
        }
        return null;
    }

    public String fetchDatabaseEntry(String userValue, String Column_Index) {
        String retrievedValue = null;
        SQLiteDatabase Database = getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_USERDATA + " WHERE " + COLUMN_ADDR + " = '" + userValue + "'";

        Cursor c = Database.rawQuery(query, null);

        if (c.moveToFirst())
            retrievedValue = c.getString(c.getColumnIndex(Column_Index));

        c.close();
        Database.close();
        return retrievedValue;
    }


    public InputData returnUserEntries (String userValue) {
        InputData return_UserData = null;

        String query = "SELECT * FROM " + TABLE_USERDATA + " WHERE " + COLUMN_ADDR + " = '" + userValue + "'";

        SQLiteDatabase Database = getWritableDatabase();
        Cursor c = Database.rawQuery(query, null);

        if (c.moveToFirst())
            return_UserData = new InputData(c.getString(c.getColumnIndex("email")), c.getString(c.getColumnIndex("keys")), c.getString(c.getColumnIndex("name")), c.getString(c.getColumnIndex("rank")), c.getString(c.getColumnIndex("org")), c.getInt(c.getColumnIndex("gender")), c.getInt(c.getColumnIndex("education")), c.getInt(c.getColumnIndex("age")));

        c.close();
        Database.close();
        return return_UserData;

    }

}
