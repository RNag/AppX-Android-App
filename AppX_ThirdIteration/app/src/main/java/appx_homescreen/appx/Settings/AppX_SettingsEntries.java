package appx_homescreen.appx.Settings;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import appx_homescreen.appx.ListData;

public class AppX_SettingsEntries extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 8;
    private static final String DATABASE_NAME = "AppX_Settings.db";
    public static final String TABLE_SETTINGS = "settings";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_ADDR = "email";
    public static final String COLUMN_ORG = "org";
    public static final String COLUMN_ORG_RATING= "rating";
    public static final String COLUMN_NUM_ENTRIES = "num";

    public AppX_SettingsEntries(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase Database) {
        String query = "create table " +
                TABLE_SETTINGS +
                " (" +
                COLUMN_ID + " integer primary key autoincrement not null," +
                COLUMN_ADDR + " text," +
                COLUMN_ORG + " text," +
                COLUMN_ORG_RATING + " real," +
                COLUMN_NUM_ENTRIES + " integer" +
                ");";
        Database.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SETTINGS);
        onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int newVersion, int oldVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SETTINGS);
        onCreate(db);
    }

    public void addPreferences(SettingsData settingsData) {
        ContentValues values = new ContentValues();

        values.put(COLUMN_ADDR, settingsData.get_userName());
        values.put(COLUMN_ORG, settingsData.get_userOrg());
        values.put(COLUMN_ORG_RATING, settingsData.get_userRating());
        values.put(COLUMN_NUM_ENTRIES, settingsData.get_numEntries());

        SQLiteDatabase Database = getWritableDatabase();
        Database.insert(TABLE_SETTINGS, null, values);
        Database.close();

    }

    public boolean checkAddr(String userValue) {
        SQLiteDatabase Database = getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_SETTINGS + " WHERE " + COLUMN_ADDR + " = '" + userValue + "'";
        Cursor c = Database.rawQuery(query, null);
        boolean email_fieldExists = false;
        if (c.moveToFirst()) {
            email_fieldExists = true;
        }
        return email_fieldExists;
    }


    public float fetchSponsorRating(String userValue, String userOrg) {
        float retrievedValue = 0;
        SQLiteDatabase Database = getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_SETTINGS + " WHERE " + COLUMN_ADDR + " = '" + userValue + "' AND " + COLUMN_ORG + " = '" + userOrg + "'";

        Cursor c = Database.rawQuery(query, null);

        if (c.moveToFirst())
            retrievedValue = c.getFloat(c.getColumnIndex("rating"));
        c.close();
        Database.close();
        return retrievedValue;
    }

    public int fetchPreference_NumEntries(String userValue) {
        int retrievedValue = 0;
        SQLiteDatabase Database = getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_SETTINGS + " WHERE " + COLUMN_ADDR + " = '" + userValue + "' AND " + COLUMN_ORG + " = 'N/A'";

        Cursor c = Database.rawQuery(query, null);

        if (c.moveToFirst())
            retrievedValue = c.getInt(c.getColumnIndex("num"));
        c.close();
        Database.close();
        return retrievedValue;
    }


    public void update_userSponsorRating(String userValue, String userOrg, float ColumnRating) {
        SQLiteDatabase Database = getWritableDatabase();
        String query = "UPDATE " + TABLE_SETTINGS + " SET " + COLUMN_ORG_RATING + " = '" + ColumnRating + "' WHERE " + COLUMN_ADDR + " = '" + userValue + "' AND " + COLUMN_ORG + " = '" + userOrg + "'";
        Database.execSQL(query);
    }

    public void update_userPreferences_NumEntries(String userValue, int numEntries) {
        SQLiteDatabase Database = getWritableDatabase();
        String query = "UPDATE " + TABLE_SETTINGS + " SET " + COLUMN_NUM_ENTRIES + " = '" + numEntries + "' WHERE " + COLUMN_ADDR + " = '" + userValue + "' AND " + COLUMN_ORG + " = 'N/A'";
        Database.execSQL(query);
    }



    public boolean userSponsor_alreadyExists(String userValue, String ref_ColumnValue) {
        boolean checkfor_userSponsor = false;
        SQLiteDatabase Database = getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_SETTINGS + " WHERE " + COLUMN_ADDR + " = '" + userValue + "' AND " + COLUMN_ORG + " = '" + ref_ColumnValue + "'";

        Cursor c = Database.rawQuery(query, null);
        if (c.moveToFirst()) {
            checkfor_userSponsor = true;
        }
        c.close();
        Database.close();
        return checkfor_userSponsor;
    }

    public List<Float> fetchSponsorRatings(String ref_ColumnValue) {
        List<Float> return_Ratings = new ArrayList<Float>();
        String query = null;

        SQLiteDatabase Database = getWritableDatabase();

            query = "SELECT * FROM " + TABLE_SETTINGS + " WHERE " + COLUMN_ORG + " = '" + ref_ColumnValue + "'";

        Cursor c = Database.rawQuery(query, null);

        c.moveToFirst();

        while (!c.isAfterLast()) {
                return_Ratings.add(c.getFloat(c.getColumnIndex("rating")));
                c.moveToNext();
        }


        c.close();
        Database.close();
        return return_Ratings;
    }

}
