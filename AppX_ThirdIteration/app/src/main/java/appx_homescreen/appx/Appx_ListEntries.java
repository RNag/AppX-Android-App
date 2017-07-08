package appx_homescreen.appx;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;
import java.util.List;

public class Appx_ListEntries extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 8;
    private static final String DATABASE_NAME = "AppX_Lists.db";
    public static final String TABLE_LISTDATA = "lists";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_LIST = "listname";
    public static final String COLUMN_ORG = "org";
    public static final String COLUMN_DATE = "date";
    public static final String COLUMN_END_DATE = "end_date";
    public static final String COLUMN_START_TIME = "start_time";
    public static final String COLUMN_END_TIME = "end_time";

    public static final String COLUMN_WHERE = "loc";
    public static final String COLUMN_ADDRESS = "addr";
    public static final String COLUMN_CITYNAME = "city";
    public static final String COLUMN_STATE = "state";

    public static final String COLUMN_DESC = "about";
    public static final String COLUMN_CONTRIBUTOR = "author";

    public Appx_ListEntries(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase Database) {
        String query = "create table " +
                TABLE_LISTDATA +
                " (" +
                COLUMN_ID + " integer primary key autoincrement not null," +
                COLUMN_LIST + " text," +
                COLUMN_ORG + " text," +
                COLUMN_DATE + " date," +
                COLUMN_END_DATE + " date," +
                COLUMN_START_TIME + " text," +
                COLUMN_END_TIME + " text," +
                COLUMN_WHERE + " text," +
                COLUMN_ADDRESS + " text," +
                COLUMN_CITYNAME + " text," +
                COLUMN_STATE + " text," +
                COLUMN_DESC + " text," +
                COLUMN_CONTRIBUTOR + " text" +
                ");";
        Database.execSQL(query);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LISTDATA);
        onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int newVersion, int oldVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LISTDATA);
        onCreate(db);
    }

    public void addList(ListData newList) {
        Integer StartID;
        if (listItem_alreadyExists("listname", newList.get_listTitle()) == false) {
            SQLiteDatabase Database = getWritableDatabase();
            ContentValues values = new ContentValues();
            String query = "SELECT count(*) FROM " + TABLE_LISTDATA;
            Cursor c = Database.rawQuery(query, null);
            c.moveToFirst();
            int count = c.getInt(0);

            if (count > 0)
                StartID = null;
            else
                StartID = 2160;
            c.close();

            //populate table
            values.put(COLUMN_ID, StartID);
            values.put(COLUMN_LIST, newList.get_listTitle());
            values.put(COLUMN_ORG, newList.get_listOrg());
            values.put(COLUMN_DATE, newList.get_listDate());
            values.put(COLUMN_END_DATE, newList.get_list_endDate());
            values.put(COLUMN_START_TIME, newList.get_list_fromTime());
            values.put(COLUMN_END_TIME, newList.get_list_toTime());
            values.put(COLUMN_WHERE, newList.get_listWhere());
            values.put(COLUMN_ADDRESS, newList.get_list_Address());
            values.put(COLUMN_CITYNAME, newList.get_list_cityName());
            values.put(COLUMN_STATE, newList.get_list_State());
            values.put(COLUMN_DESC, newList.get_listAbout());
            values.put(COLUMN_CONTRIBUTOR, newList.get_listAuthor());
            Database.insert(TABLE_LISTDATA, null, values);
            Database.close();
        }

    }

    public void deleteList(String listName) {
        SQLiteDatabase Database = getWritableDatabase();
        String query = "DELETE FROM " + TABLE_LISTDATA + " WHERE " + COLUMN_LIST + " = '" + listName + "'";
        Database.execSQL(query);
        Database.close();
    }

    public boolean isThreadCreator(String listName, String userValue) {
        SQLiteDatabase Database = getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_LISTDATA + " WHERE " + COLUMN_LIST + " = '" + listName + "' AND " + COLUMN_CONTRIBUTOR + " = '" + userValue + "'";
        Cursor c = Database.rawQuery(query, null);

        boolean list_isAuthored = false;
        if (c.moveToFirst()) {
            list_isAuthored = true;
        }
        c.close();
        Database.close();
        return list_isAuthored;
    }


    public boolean listItem_alreadyExists(String ref_ColumnIndex, String ref_ColumnValue) {
        boolean checkfor_DuplicateEntries = false;
        SQLiteDatabase Database = getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_LISTDATA + " WHERE " + ref_ColumnIndex + " = '" + ref_ColumnValue + "' COLLATE NOCASE";
        Cursor c = Database.rawQuery(query, null);
        if (c.moveToFirst()) {
            checkfor_DuplicateEntries = true;
        }
        c.close();
        Database.close();
        return checkfor_DuplicateEntries;
    }

    public ListData fetchDatabaseEntry(String ref_ColumnIndex, String ref_ColumnValue) {
        ListData return_ListEntry = null;
        String query = null;
        SQLiteDatabase Database = getWritableDatabase();
        if (ref_ColumnIndex.equals("_id"))
            query = "SELECT * FROM " + TABLE_LISTDATA + " WHERE " + ref_ColumnIndex + " = '" + Integer.parseInt(ref_ColumnValue) + "'";
        else
            query = "SELECT * FROM " + TABLE_LISTDATA + " WHERE " + ref_ColumnIndex + " = '" + ref_ColumnValue + "'";

        Cursor c = Database.rawQuery(query, null);

        if (c.moveToFirst()) {
            if (c.getString(c.getColumnIndex(ref_ColumnIndex)) != null)
                return_ListEntry = new ListData(c.getInt(c.getColumnIndex("_id")), c.getString(c.getColumnIndex("listname")), c.getString(c.getColumnIndex("org")), c.getString(c.getColumnIndex("date")), c.getString(c.getColumnIndex("end_date")), c.getString(c.getColumnIndex("start_time")), c.getString(c.getColumnIndex("end_time")), c.getString(c.getColumnIndex("loc")), c.getString(c.getColumnIndex("addr")), c.getString(c.getColumnIndex("city")), c.getString(c.getColumnIndex("state")), c.getString(c.getColumnIndex("about")), c.getString(c.getColumnIndex("author")));
        }

        c.close();
        Database.close();
        return return_ListEntry;
    }


    public List<ListData> returnListEntries_byOrder(String COLUMN_NAME, int sortByOrder, String column_to_search, String tag_to_search) {
        List<ListData> return_List = new ArrayList<ListData>();
        String query;

        if (tag_to_search.equals("*")) {
            query = "SELECT * FROM " + TABLE_LISTDATA + " ORDER BY " + COLUMN_NAME;

            if (!(sortByOrder == 0))
                query = "SELECT * FROM " + TABLE_LISTDATA + " ORDER BY " + COLUMN_NAME + " DESC";
        } else {

            query = "SELECT * FROM " + TABLE_LISTDATA + " WHERE " + column_to_search + " LIKE '%" + tag_to_search + "%' ORDER BY " + COLUMN_NAME;

            if (!(sortByOrder == 0))
                query = "SELECT * FROM " + TABLE_LISTDATA + " WHERE " + column_to_search + " LIKE '%" + tag_to_search + "%' ORDER BY " + COLUMN_NAME + " DESC";

        }

        SQLiteDatabase Database = getWritableDatabase();
        Cursor c = Database.rawQuery(query, null);
        c.moveToFirst();

        while (!c.isAfterLast()) {
            if (c.getString(c.getColumnIndex("listname")) != null) {
                return_List.add(new ListData(c.getString(c.getColumnIndex("listname")), c.getString(c.getColumnIndex("org")), c.getString(c.getColumnIndex("date")), c.getString(c.getColumnIndex("about")), c.getString(c.getColumnIndex("author"))));
                c.moveToNext();
            }
        }

        c.close();
        Database.close();
        return return_List;

    }


    public List<ListData> fetchListEntriesAuthored(String ref_ColumnIndex, String ref_ColumnValue) {
        List<ListData> return_List = new ArrayList<ListData>();
        String query = null;

        SQLiteDatabase Database = getWritableDatabase();
        if (ref_ColumnIndex.equals("_id"))
            query = "SELECT * FROM " + TABLE_LISTDATA + " WHERE " + ref_ColumnIndex + " = '" + Integer.parseInt(ref_ColumnValue) + "' COLLATE NOCASE";
        else
            query = "SELECT * FROM " + TABLE_LISTDATA + " WHERE " + ref_ColumnIndex + " = '" + ref_ColumnValue + "' COLLATE NOCASE";

        Cursor c = Database.rawQuery(query, null);

        c.moveToFirst();

        while (!c.isAfterLast()) {
            if (c.getString(c.getColumnIndex(ref_ColumnIndex)) != null) {
                return_List.add(new ListData(c.getInt(c.getColumnIndex("_id")), c.getString(c.getColumnIndex("listname")), c.getString(c.getColumnIndex("org")), c.getString(c.getColumnIndex("date")), c.getString(c.getColumnIndex("end_date")), c.getString(c.getColumnIndex("start_time")), c.getString(c.getColumnIndex("end_time")), c.getString(c.getColumnIndex("loc")), c.getString(c.getColumnIndex("addr")), c.getString(c.getColumnIndex("city")), c.getString(c.getColumnIndex("state")), c.getString(c.getColumnIndex("about")), c.getString(c.getColumnIndex("author"))));
                c.moveToNext();
            }
        }

        c.close();
        Database.close();
        return return_List;
    }
}