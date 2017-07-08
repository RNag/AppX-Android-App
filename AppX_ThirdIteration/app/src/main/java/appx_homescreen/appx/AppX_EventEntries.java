package appx_homescreen.appx;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;
import java.util.List;

public class AppX_EventEntries extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 4;
    private static final String DATABASE_NAME = "AppX_Events.db";
    public static final String TABLE_EVENTDATA = "eventso";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_WHO = "who";
    public static final String COLUMN_WHAT = "what";
    public static final String COLUMN_WHEN = "cwhen";
    public static final String COLUMN_WHERE = "cwhere";
    public static final String COLUMN_HOW = "how";
    public static final String COLUMN_MORE = "more";

    public AppX_EventEntries(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase Database) {
        String query = "create table " +
                TABLE_EVENTDATA +
                " (" +
                COLUMN_ID + " integer primary key autoincrement not null," +
                COLUMN_WHO + " text," +
                COLUMN_WHAT + " text," +
                COLUMN_WHEN + " text," +
                COLUMN_WHERE + " text," +
                COLUMN_HOW + " text," +
                COLUMN_MORE + " int" +
                ");";
        Database.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EVENTDATA);
        onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int newVersion, int oldVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EVENTDATA);
        onCreate(db);
    }

    public void addEvent(EventData eventData) {
        ContentValues values = new ContentValues();

        values.put(COLUMN_WHO, eventData.get_Who());
        values.put(COLUMN_WHAT, eventData.get_What());
        values.put(COLUMN_WHEN, eventData.get_When());
        values.put(COLUMN_WHERE, eventData.get_Where());
        values.put(COLUMN_HOW, eventData.get_How());
        values.put(COLUMN_MORE, eventData.get_More());

        SQLiteDatabase Database = getWritableDatabase();
        Database.insert(TABLE_EVENTDATA, null, values);
        Database.close();

    }

    public boolean checkForDuplicateEvent2(String eventName){
        boolean eventName_exists = false;
        SQLiteDatabase Database = getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_EVENTDATA + " WHERE " + COLUMN_WHAT + " = '" + eventName + "'";
        Cursor c = Database.rawQuery(query, null);
        if (c.moveToFirst()){
            eventName_exists = true;
        }
        c.close();
        Database.close();
        return eventName_exists;
    }



}
