package appx_homescreen.appx;

import android.annotation.TargetApi;

import android.app.Activity;
import android.app.ProgressDialog;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.TableRow.LayoutParams;
import android.widget.Toast;

import java.text.BreakIterator;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import appx_homescreen.appx.Settings.AppX_SettingsEntries;


public class ListActivity extends AppCompatActivity {

    private static final int START_ID = 2160;
    private static int NUM_ENTRIES_PAGE;
    private static final String[] ColumnNames = {"_id", "listname", "org", "date"};
    private static final int[] ColId_out = {0xb0b3cced, 0x8c1a46ed, 0x8c345eed, 0xFFD4D4EE};
    private static final int[] ColId_over = {0xB092AACB, 0x8C173EC8, 0x8C3056C9, 0xFFC7C7E0};
    String userValue;

    Appx_ListEntries dbHandler; //Public reference to database file
    AppX_SettingsEntries settingsHandler = new AppX_SettingsEntries(this, null, null, 6);

    TableLayout listLayout;     //Table Container
    TableRow tr;                //Table Row
    TextView c[] = new TextView[5];             //Table Columns
    Button editButton, delButton, listButton;               //Buttons ('Edit' is currently unused)
    //EditText edit_listName, edit_listDate, edit_listAbout;  //User-input fields
    TextView Page[] = new TextView[4];
    TextView Prev, Next, Results;
    LayoutParams tr_params, c_params[]= new LayoutParams[5];     //Table elements layout parameters
    int Order_preset = 0, currentPage = 1;
    String ColumnName_preset = "_id", ColumnToSearch_preset = null, Tag_preset = null;
    SearchView searchView;


    float x1,x2;
    float y1, y2;

    /** List declarations to hold predefined list entries since none exist upon creating or upgrading the database version */
    List<String> new_listName = new ArrayList<>();
    List<String> new_listOrg = new ArrayList<>();
    List<String> new_listDate = new ArrayList<>();
    List<String> new_listAbout = new ArrayList<>();
    List<String> new_listAuthor = new ArrayList<>();

    ListData newList;                                   //Public reference to the list class used to store entries in the database
    SimpleDateFormat fromFormat, toFormat;              //Format strings to convert to/from the 'accepted' SQLite Date-string format
    String formatName, formatDate, formatAbout = null;  //The string values for data in the list entries to format as needed

    public static ProgressDialog processView;

    boolean[] Switch_sortOrder = new boolean[4];    //Boolean array elements to switch between the sort order for column values
    int UniqueID;                                   //Unique identifiers (ID) for Views that are dynamically created upon startup
int clr_e;

    String searchColumn_Selected = "*";
    int Selected_pos = 0;
    Spinner searchSpinner;
    ArrayAdapter<CharSequence> columnSearchAdapter;
    HashMap <String, String> searchColumns = new HashMap<String,String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        Bundle extras = getIntent().getExtras();
        userValue = (extras != null)? extras.getString("userValue"): "N/A";

        NUM_ENTRIES_PAGE = (settingsHandler.fetchPreference_NumEntries(userValue) != 0)? settingsHandler.fetchPreference_NumEntries(userValue) : 5;
        /** These are where the predefined list entries are defined. They are added dynamically using the 'list' object declaration,
         *  therefore we don't need to worry about declaring or initializing an array size since we will constantly be adding or
         *  removing entries to the list. These values for string literals in each list (essentially they each hold specific types
         *  of data for each list entry to be added) are each stored in a separate list for no other reason than it is easier to read
         *  and modify the values. Since the string values supplied for the description (about) field can be many sentences and word
         *  characters long, it makes sense to declare a separate list for each list entry field to enhance readability rather than
         *  store them in a single aggregated list of type <String[]>, i.e. a String array type. For testing or development purposes,
         *  one can always modify or rearrange these entries to see how they will be displayed on the screen. You can also safely
         *  delete or remove any of these list entries if you find that they contribute to the space on the screen  or UI appearing
         *  too cluttered. However, if such is your intention just remember to delete all the fields for each list entry (i.e. remove
         *  the individual elements appended to each of the separate lists in each case). A complete declaration of each individual
         *  entry added to the running list should clearly follow this format:
         *
         *      new_listName.add({String} Event Name);
         *      new_listDate.add({String} Event Date);
         *      new_listAbout.add({String} Optional description);
         **/

        new_listName.add("Sample VCU Event");
        new_listOrg.add("VCU");
        new_listDate.add("7/18/13");
        //new_listDate.add("2013-07-18"); //This format was used in previous versions, but you can uncomment this to see what will be returned
        new_listAbout.add("This is just a simple description for the event to be added, but feel free to make this as long as you want it to be.");
        new_listAuthor.add("VCU");

        new_listName.add("Another event");
        new_listOrg.add("Chess Club");
        new_listDate.add("12/20/14");
        new_listAbout.add("Add some more entries to fill the screen so that you can see that the table is indeed scrollable");
        new_listAuthor.add("N/A");

        new_listName.add("Random meeting");
        new_listOrg.add("HNK Society");
        new_listDate.add("N/A");
        new_listAbout.add("Description3");
        new_listAuthor.add("HNK");

        new_listName.add("Cycling Club @VCU");
        new_listOrg.add("Cycling Club");
        new_listDate.add("11/1/15");
        new_listAbout.add("Another simple example of an event meeting listed. Please also note that: \n\n" +
                "This is an actual club that exists, this was not made up for the sake of creating or generating a sample event " +
                "that can be used to introduce an example. ");
        new_listAuthor.add("VCU");

        new_listName.add("My Event");
        new_listOrg.add("Unspecified");
        new_listDate.add("N/A");
        new_listAbout.add("This list has been authored by you and can be deleted.");
        new_listAuthor.add(userValue);


        fromFormat = new SimpleDateFormat("M/d/yy", java.util.Locale.US);
        toFormat = new SimpleDateFormat("yyyy-MM-dd", java.util.Locale.US);

        dbHandler = new Appx_ListEntries(this, null, null, 6);
        for (int i = 0; i < new_listName.size(); i++) {
            try {
                formatDate = toFormat.format(fromFormat.parse(new_listDate.get(i)));
            }
            catch (ParseException e){
                formatDate = new_listDate.get(i);
                //Toast.makeText(this, "The date '"+ new_listDate.get(i) + "' was unparseable, and the list entry could not be added.",Toast.LENGTH_LONG).show();
                //continue;
            }
            newList = new ListData(new_listName.get(i), new_listOrg.get(i), formatDate, new_listAbout.get(i), new_listAuthor.get(i));
            dbHandler.addList(newList);
        }


        new_listName.add("Loop Event");
        new_listOrg.add("Unspecified");
        new_listDate.add("N/A");
        new_listAbout.add("This list has been authored by you and can be deleted.");
        new_listAuthor.add(userValue);

        int last = new_listName.size() - 1;
        for (int i = 0; i < 20; i++) {

            try {
                formatDate = toFormat.format(fromFormat.parse(new_listDate.get(last)));
            }
            catch (ParseException e){
                formatDate = new_listDate.get(last);
                //Toast.makeText(this, "The date '"+ new_listDate.get(i) + "' was unparseable, and the list entry could not be added.",Toast.LENGTH_LONG).show();
                //continue;
            }
            newList = new ListData(new_listName.get(last) + Integer.toString(i), new_listOrg.get(last), formatDate, new_listAbout.get(last), new_listAuthor.get(last));
            dbHandler.addList(newList);
        }


        listLayout = (TableLayout) findViewById(R.id.listLayout);

        /** Please pay close attention to how the values getting passed to the function arguments are defined here!
         *  By default we want to sort the list by the _id tag (which is the string name for the column in our list database
         *  that we want to reference). This identifier is automatically created, or incremented, each time a new entry
         *  is added to the running list of entries that is stored. The _id tag can therefore be thought of as a pre-defined
         *  identifier column which directly references the new row in the database that is added (the row that contains all other
         *  column entries which describe the individual entry more in detail). Therefore, suppose we want to sort the entries by
         *  the order in which they were created and which is supposedly the sorting-by order that the database will default to,
         *  then we should opt to select all the stored entries and sort them by increasing order and by the '_id' tag. This is
         *  in fact what the argument values specify and what will be returned in this example function call. The function
         *  declaration or description which includes its parameters information as well as what would be deemed acceptable
         *  (or valid) argument values for this function call are listed below. Please note that, if in doubt or if further
         *  information are needed, one can always find and reference the java .class file titled 'Appx_ListEntries' listed
         *  under the main directory or folder of this activity declaration for more details referencing the function
         *  declaration statements.
         *
         * public List<ListData> returnListEntries_byOrder(String COLUMN_NAME, int sortByOrder)
         *  Takes values as (Remember, CaSe-SENSITIVE):
         *
         *  BEGIN
         *      SELECT [COLUMN_NAME]:
         *          (String) _id        IS      Column 'Id' {Autoincrement Integer identifier}
         *          (String) listname   IS      List Title {Name}
         *          (String) date       IS      Event Date {Expected}
         *          (String) about      IS      Description {Optional}
         *          (String) author     IS      Contributor {Thread Starter}
         *
         *      SELECT [sortByOrder]:
         *          (int) 0         IS      ASCENDING {Order}
         *          (int) 1         IS      DESCENDING {Order}
         *  DONE
         * */


        Page[1] = (TextView) findViewById(R.id.Page1);
        Page[2] = (TextView) findViewById(R.id.Page2);
        Page[3] = (TextView) findViewById(R.id.Page3);
        Results = (TextView) findViewById(R.id.ResultsHeader);
        searchView = (SearchView) findViewById(R.id.searchView);
searchView.setOnQueryTextListener(queryTextListener);


        Prev = (TextView) findViewById(R.id.Prev);
        Prev.setOnClickListener(SetNextPage());
        Next = (TextView) findViewById(R.id.Next);
        Next.setOnClickListener(SetNextPage());

        ColumnToSearch_preset = "*";
        Tag_preset = "*";
        PopulateTableElements(ColumnName_preset,Order_preset, currentPage, ColumnToSearch_preset, Tag_preset);

        List<ListData> countList = dbHandler.returnListEntries_byOrder(ColumnName_preset,Order_preset, ColumnToSearch_preset, Tag_preset);

        for (int i=1; i<= 3; i++) {
            Page[i].setOnClickListener(SetNextPage());
            int LowerLimit = ((i - 1) * NUM_ENTRIES_PAGE) + 1;
            int UpperLimit = countList.size() < (NUM_ENTRIES_PAGE * i) ? countList.size() : NUM_ENTRIES_PAGE * i;
            Page[i].setText(Integer.valueOf(LowerLimit) + " - " + Integer.valueOf(UpperLimit));
            if (countList.size() < LowerLimit) Page[i].setVisibility(View.INVISIBLE);

        }


        for (int i=1; i <= 4; i++) {
            int imageId = getResources().getIdentifier("imageView" + i, "id", getPackageName());
            ImageView arrow_img = (ImageView) findViewById(imageId);
            arrow_img.bringToFront();
            int resId = getResources().getIdentifier("Header" + (i-1), "id", getPackageName());
            TextView col_header = (TextView) findViewById(resId);
            col_header.setOnClickListener(onClick());
            col_header.setOnTouchListener(onTouch());
        }

        //((ImageView) findViewById(R.id.textView38)).bringToFront();

        searchSpinner = (Spinner) findViewById(R.id.columnSearch_spinner);

        // Create an ArrayAdapter using the string array and a default spinner
        columnSearchAdapter = ArrayAdapter
                .createFromResource(this, R.array.ColumnSearch_values,
                        android.R.layout.simple_spinner_item);

        // Apply the adapter to the spinners
            columnSearchAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            searchSpinner.setAdapter(columnSearchAdapter);
        searchSpinner.setOnItemSelectedListener(new SpinnerActivity());

        String[] ColumnSearch_values = getResources().getStringArray(R.array.ColumnSearch_values);
        String[] ColumnName_values = {"_id", "listname", "org", "date", "about"};

        for (int i = 0; i < ColumnName_values.length; i++)
            searchColumns.put(ColumnSearch_values[i],ColumnName_values[i]);

        ((LinearLayout) findViewById(R.id.LinLayout)).setOnTouchListener(new OnSwipeTouchListener(this) {

            public void onSwipeRight() {
                setPageDirection("Prev");
            }
            public void onSwipeLeft() {
                setPageDirection("Next");
            }
            public void onSwipeTop() {
            }
            public void onSwipeBottom() {
            }

        });

        searchView.clearFocus();
    }


    @Override
    public void onRestart()
    {  // After a pause OR at startup
        super.onRestart();
        //Refresh your stuff here
        listLayout.removeAllViews();
        PopulateTableElements(ColumnName_preset, Order_preset, currentPage, null, null);
        searchView.clearFocus();
    }

    public class SpinnerActivity extends Activity implements AdapterView.OnItemSelectedListener {


        public void onItemSelected(AdapterView<?> parent, View view,
                                   int pos, long id) {
                    searchColumn_Selected = parent.getItemAtPosition(pos).toString();
                    Selected_pos = pos;
            searchView.clearFocus();

            ColumnName_preset = "_id";
            Order_preset = 0;

            ColumnToSearch_preset = searchColumns.get(searchColumn_Selected);
            currentPage = 1;
            RefreshListEntries();
        }

            // An item was selected. You can retrieve the selected item using
            // parent.getItemAtPosition(pos)


        public void onNothingSelected(AdapterView<?> parent) {
            // Another interface callback
        }
    }

        public void PopulateTableElements(String SortByColumnName, int Order, int PageNum, String column_to_search, String tag_to_search){

            column_to_search = column_to_search != null ? column_to_search : "*";
            tag_to_search = tag_to_search != null ? tag_to_search : "*";

       // AddTableHeaders();


        //List<ListData> sortedList = dbHandler.returnListEntries_byOrder(SortByColumnName,Order);
        List<ListData> sortedList = dbHandler.returnListEntries_byOrder(SortByColumnName, Order, column_to_search, tag_to_search);

        toFormat = new SimpleDateFormat("M/d/yy", java.util.Locale.US);
        fromFormat = new SimpleDateFormat("yyyy-MM-dd", java.util.Locale.US);


        int LowerLimit = ((PageNum-1) * NUM_ENTRIES_PAGE);
        int UpperLimit = sortedList.size() < (NUM_ENTRIES_PAGE * PageNum) ? sortedList.size() : NUM_ENTRIES_PAGE * PageNum;


            if (sortedList.size() <= UpperLimit)
                Next.setVisibility(View.INVISIBLE);
            else
                Next.setVisibility(View.VISIBLE);

            if (PageNum == 1)
                Prev.setVisibility(View.INVISIBLE);
            else
                Prev.setVisibility(View.VISIBLE);


        if (sortedList.size() == 0)
            AddRowEntry("No results found.", null, null, null);

            for (int i = LowerLimit; i < UpperLimit; i++) {
            ListData fetchListItem = sortedList.get(i);

            try {
                formatDate = toFormat.format(fromFormat.parse(fetchListItem.get_listDate()));
            }
            catch (ParseException e) {
                formatDate = "N/A";
                //Toast.makeText(this, "The date '"+ fetchListItem.get_listDate() + "' was unparseable, and the list entry could not be fetched.",Toast.LENGTH_LONG).show();
                //continue;
            }
            AddRowEntry(fetchListItem.get_listTitle(), fetchListItem.get_listOrg(), formatDate, fetchListItem.get_listAbout());
        }

            int PageNumIndex =  (PageNum % 3) == 0 ? 3: PageNum % 3;


            for (int i=1; i<= 3; i++) {
                LowerLimit = (((PageNum - 1) * NUM_ENTRIES_PAGE) + 1) - ((PageNumIndex - i) * NUM_ENTRIES_PAGE);
                UpperLimit = Math.min(sortedList.size(),((PageNum * NUM_ENTRIES_PAGE) - ((PageNumIndex - i) * NUM_ENTRIES_PAGE)));
                Page[i].setText(Integer.valueOf(LowerLimit) + " - " + Integer.valueOf(UpperLimit));

                    int clr = (Page[i].equals(Page[PageNumIndex])) ? 0x82ffac8b : 0x00000000;
                    Page[i].setBackgroundColor(clr);
                    if (sortedList.size() < LowerLimit)
                        Page[i].setVisibility(View.INVISIBLE);
                    else
                        Page[i].setVisibility(View.VISIBLE);
            }


            Results.setText(String.format("Showing results %s-%s of %s", Math.min(sortedList.size(), ((currentPage - 1) * NUM_ENTRIES_PAGE) + 1), Math.min(sortedList.size(), NUM_ENTRIES_PAGE * currentPage), sortedList.size()));

        /*
        for (ListData fetchListItem : sortedList) {
            try {
                formatDate = toFormat.format(fromFormat.parse(fetchListItem.get_listDate()));
            }
            catch (ParseException e) {
                formatDate = fetchListItem.get_listDate();
                //Toast.makeText(this, "The date '"+ fetchListItem.get_listDate() + "' was unparseable, and the list entry could not be fetched.",Toast.LENGTH_LONG).show();
                //continue;
            }
            AddRowEntry(fetchListItem.get_listTitle(), fetchListItem.get_listOrg(), formatDate, fetchListItem.get_listAbout());
        }
        */

        c_params[0] = new TableRow.LayoutParams(500, 5);
        c_params[0].setMargins(0, 0, 0, 40);

        tr = new TableRow(this);
        View c1_view = new View(this);
        c1_view.setBackgroundColor(0xe5386569);
        c1_view.setLayoutParams(c_params[0]);
        tr.addView(c1_view);
        listLayout.addView(tr);
    }

    public void AddTableHeaders(){
        tr_params = new TableRow.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        c_params[0] = new TableRow.LayoutParams(0, LayoutParams.WRAP_CONTENT, 0.25f);
        c_params[1] = new TableRow.LayoutParams(0, LayoutParams.WRAP_CONTENT, 0.75f);
        c_params[2] = new TableRow.LayoutParams(0, LayoutParams.WRAP_CONTENT, 0.6f);
        c_params[3] = new TableRow.LayoutParams(0, LayoutParams.WRAP_CONTENT, 0.45f);
        //tr_params.setMargins(0,140,0,140);

        c_params[0].setMargins(0, -25, 0, 0);
        c_params[2].setMargins(0, -25, 0, 0);
        c_params[3].setMargins(0, 0, 20, 0);
        tr = new TableRow(this);
        tr.setLayoutParams(tr_params);


        for (int i=0;i<4;i++){
            c[i] = new TextView(this);
            c[i].setLayoutParams(c_params[i]);
            c[i].setTextSize(15);
            c[i].setGravity(Gravity.CENTER);
            c[i].setTypeface(Typeface.DEFAULT_BOLD);
            c[i].setTextColor(0xFF000000);
            c[i].setOnClickListener(onClick());
            c[i].setOnTouchListener(onTouch());
            c[i].setPadding(0, 30, 0, 30);
        }
        //,c[2].setPadding(0, 25, 0, 7);
        c_params[2].setMargins(0, -25, 0, 0);
        c[2].setLayoutParams(c_params[2]);
        //c[2].setPadding(0, 30, 0, 15);
        c[2].setPadding(0, 30, 0, 4);
        c[0].setText("Event\nID");

        c[1].setBackgroundColor(0x8c1a46ed);
        c[1].setText("Title");

        c[2].setText("Sponsor /\nOrg. Name");
        c[2].setBackgroundColor(0x8c345eed);

        c[3].setText("Date");



        tr.addView(c[0]);
        tr.addView(c[1]);
        tr.addView(c[2]);
        tr.addView(c[3]);
        listLayout.addView(tr);
    }

    public void AddRowEntry(String list_item1, String list_item2, String list_item3, String list_item4) {
        tr_params = new TableRow.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        tr = new TableRow(this);
        tr.setLayoutParams(tr_params);
        c_params[0] = new TableRow.LayoutParams(0, LayoutParams.WRAP_CONTENT, 0.25f);
        c_params[1] = new TableRow.LayoutParams(0, LayoutParams.WRAP_CONTENT, 0.85f);
        c_params[2] = new TableRow.LayoutParams(0, LayoutParams.WRAP_CONTENT, 0.65f);
        c_params[3] = new TableRow.LayoutParams(0, LayoutParams.WRAP_CONTENT, 0.40f);


        for (int i=0;i<4;i++){
            c[i] = new TextView(this);
            c_params[i].setMargins(0,-5,0,0);
            c[i].setLayoutParams(c_params[i]);
        }


        editButton = new Button(this);
        delButton = new Button(this);


        if (list_item1 == null || list_item2 == null || list_item3 == null)
            c[0].setText("");
        else
            c[0].setText(String.valueOf(dbHandler.fetchDatabaseEntry("listname", list_item1).get_id()));

        c[1].setText(list_item1);
        c[1].setTextColor(0xFF0C9CC6);
        c[1].setOnClickListener(onClick());
        c[1].setOnTouchListener(onTouch());
        c[1].setId(getUniqueID());

        c[2].setText(list_item2);
        c[2].setTextColor(0xFF0C9CC6);
        c[2].setOnClickListener(onClick());
        c[2].setOnTouchListener(onTouch());

        c[3].setText(list_item3);


        //editButton.setText("Edit"); editButton.setOnClickListener(onClick(editButton));

        delButton.setText("Delete");
        delButton.setOnClickListener(onClick());


        //c[1].setPadding(30, 0, 10, 0);

        //c[1].setBackgroundColor(0x8c4f75ed);
        c[1].setBackgroundColor(0xe5dee7e8);

        c[2].setBackgroundColor(0xE5E7F0F1);

        c[2].setGravity(Gravity.CENTER);

        c[3].setGravity(Gravity.CENTER);

        LayoutParams del_params = new TableRow.LayoutParams(60, 60, 0.15f);
        del_params.setMargins(0, 0, -10, 0);
        delButton.setLayoutParams(del_params);
        delButton.setWidth(2);



        /*
        c_params[2] = new TableRow.LayoutParams(0, var_height + (var_height * tast), 0.65f);

            c_params[2].setMargins(0,50,0,0);
            c[2].setLayoutParams(c_params[2]);
*/


                //c[2].setPadding(0, 30, 0, 4);

                //c_params[2] = new TableRow.LayoutParams(0, c[1].getLineCount() * c[1].getLineHeight(), 0.65f);
                //c[2].setLayoutParams(c_params[2]);

        tr.addView(c[0]);
        tr.addView(c[1]);
        tr.addView(c[2]);
        tr.addView(c[3]);



//        Toast.makeText(getApplicationContext(), String.valueOf(tast), Toast.LENGTH_SHORT).show();
        //Toast.makeText(getApplicationContext(), String.valueOf(c[1].getLineHeight() + "," + String.valueOf(c[1].getMeasuredHeight()) + "," + String.valueOf(tr.getMeasuredHeight())), Toast.LENGTH_SHORT).show();


        //tr.addView(editButton);
       // tr.addView(delButton);

        ImageView del_img = new ImageView(this);

        del_img.setImageResource(R.drawable.remove2);
        del_img.setLayoutParams(del_params);

        del_img.setClickable(true);
        del_img.setOnClickListener(onClick());
        del_img.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View view, MotionEvent event) {
                ImageView castView = (ImageView) view;
                if (event.getAction() == MotionEvent.ACTION_DOWN)
                    castView.setImageResource(R.drawable.remove_down);
                else
                    castView.setImageResource(R.drawable.remove2);
                return false;
            }
        });
        //del_img.setLayoutParams(c_params[2]);
        tr.addView(del_img);

        if (!(list_item1 == null || list_item2 == null || list_item3 == null))
            listLayout.addView(tr);


        if (!dbHandler.isThreadCreator(list_item1,userValue))
            del_img.setVisibility(View.INVISIBLE);


        tr = new TableRow(this);
        tr_params = new TableRow.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);

        c_params[0] = new TableRow.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, 0.20f);
        c_params[1] = new TableRow.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, 0.80f);

        tr.setLayoutParams(tr_params);
        c_params[0].setMargins(180, 20, 0, 0);
        c_params[1].setMargins(0, 20, 20, 0);

        c[0] = new TextView(this);

        if (list_item1 == null || list_item2 == null || list_item3 == null) {
            c[0].setText("No results found.\n\n");
            c_params[0] = new TableRow.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, 1.00f);
            c_params[0].setMargins(180, 10, 0, 0);
            //c_params[0].setMargins(180, 20, 0, 0);
            c[0].setLayoutParams(c_params[0]);
            c[0].setTypeface(Typeface.DEFAULT, Typeface.BOLD);
            c[0].setTextSize(18);
            c[0].setTextColor(0xFF000000);
        }
        else {
            c[0].setText("What:");

            c[0].setLayoutParams(c_params[0]);
            c[0].setTypeface(Typeface.DEFAULT, Typeface.ITALIC);
            c[0].setTextColor(0xFF000000);
        }

        int number_chars = 65;
        if (list_item4 != null) {
            if (list_item4.length() > number_chars) {
                BreakIterator bi = BreakIterator.getWordInstance();
                bi.setText(list_item4);
                int first_after = bi.following(number_chars);
                list_item4 = (list_item4.substring(0, first_after)).trim().concat("...");

            }
        }
        c[1] = new TextView(this);
        c[1].setText(String.format("%s\n\n", list_item4));

        c[1].setLayoutParams(c_params[1]);

        tr.addView(c[0]);
        if (!(list_item4 == null))
            tr.addView(c[1]);
        listLayout.addView(tr);
    }


    public synchronized int getUniqueID(){
    /**
     *   Returns a unique ID (autoincrement value of int type) which in this case initially increments from a default value of '1'.
     *   The purpose for declaring and returning an ID value is for cases when such dynamic, i.e. programmatically created elements
     *   will need to be referenced at some unknown point in the code. Using this method, it is entirely possible for an earlier
     *   partition in the code to reference an undeclared element (at that current point in time that such an individual line is
     *   read in) by using its ID tag value, before reaching that line in the code where that element is even instantiated or
     *   even before it is assigned the identifier value that has been referenced a priori to the declaration statement. This will
     *   be useful particularly if multiple IDs are being assigned to various objects or instances and you want to make sure that
     *   each one of them will have a unique identifier value that can be referenced in any point or section of the code without
     *   worrying overmuch about any particular case of overlap that can result from duplicate IDs being assigned to various
     *   elements.
     *   */

        UniqueID ++;
        return UniqueID;
    }


    public View.OnTouchListener onTouch () {
        return new View.OnTouchListener() {


            public boolean onTouch(View view, MotionEvent event) {
                TextView castView = (TextView) view;
                clr_e = ((ColorDrawable) view.getBackground()).getColor();
                // es = new Color(0xFF0DB6E2)
                if (event.getAction() == MotionEvent.ACTION_DOWN) {

                    for (int i = 0; i <= 3; i++) {
                        int resId = getResources().getIdentifier("Header" + i, "id", getPackageName());
                        TextView col_header = (TextView) findViewById(resId);
                        if (col_header.equals(castView))
                            castView.setBackgroundColor(ColId_over[i]);
                    }

                        if ((castView.getParent()).getParent() == listLayout)
                            castView.setTextColor(0xFF0DB6E2);

                }
                else {
                    for (int i = 0; i <= 3; i++) {
                        int resId = getResources().getIdentifier("Header" + i, "id", getPackageName());
                        TextView col_header = (TextView) findViewById(resId);
                        if (col_header.equals(castView))
                            castView.setBackgroundColor(ColId_out[i]);
                    }
                        if((castView.getParent()).getParent() == listLayout)
                            castView.setTextColor(0xFF0C9CC6);

                }
                //castView.setText(castView.getText().toString());
                return false;
            }

        };
    }



    public void setPageDirection(String Direction){
        switch (Direction){
            case "Next":
                currentPage += 1;
                listLayout.removeAllViews();
                PopulateTableElements(ColumnName_preset, Order_preset, currentPage, ColumnToSearch_preset, Tag_preset);
            break;
            case "Prev":
                if (currentPage != 1) {
                    currentPage -= 1;
                    listLayout.removeAllViews();
                    PopulateTableElements(ColumnName_preset, Order_preset, currentPage, ColumnToSearch_preset, Tag_preset);
                }
            break;
        }
    }

    public View.OnClickListener SetNextPage() {
        return new View.OnClickListener() {
            public void onClick(View view) {
                int Bounds[] = new int[2];
                List<ListData> countList = dbHandler.returnListEntries_byOrder(ColumnName_preset,Order_preset, ColumnToSearch_preset, Tag_preset);
                //if (Prev.getVisibility() == View.INVISIBLE) Prev.setVisibility(View.VISIBLE);
                //if (Next.getVisibility() == View.INVISIBLE) Next.setVisibility(View.VISIBLE);

                if (view.equals(Next)) {
                    setPageDirection("Next");
                }
                else if (view.equals(Prev)){
                    setPageDirection("Prev");
                }
                else {
                    int currentPageIndex = 0;
                    int previousPageIndex =  (currentPage % 3) == 0 ? 3: currentPage % 3;
                    for (int i = 1; i <= 3; i++)
                        currentPageIndex = ((TextView) view == Page[i])? i : currentPageIndex;
                    currentPage += (currentPageIndex - previousPageIndex);
                    listLayout.removeAllViews();
                    PopulateTableElements(ColumnName_preset, Order_preset, currentPage, ColumnToSearch_preset, Tag_preset);
                }

                final ScrollView scroll = (ScrollView) findViewById(R.id.ScrollView01);
                scroll.post(new Runnable() {
                    @Override
                    public void run() {
                        scroll.fullScroll(ScrollView.FOCUS_DOWN);
                    }
                });


            }
    };
    }

    public static void toggleKeyboard(Context context) {
        InputMethodManager imm = (InputMethodManager) context
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm.isActive()) {
            imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT,
                    InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }


    public void RefreshListEntries(){
        listLayout.removeAllViews();
        PopulateTableElements(ColumnName_preset,Order_preset, currentPage, ColumnToSearch_preset, Tag_preset);

        int currentPageIndex = currentPage;

        List<ListData> countList = dbHandler.returnListEntries_byOrder(ColumnName_preset, Order_preset, ColumnToSearch_preset, Tag_preset);
        for (int i = 1; i <= 3; i++) {

            int clr = (Page[i].equals(Page[currentPageIndex]))? 0x82ffac8b : 0x00000000;
            Page[i].setBackgroundColor(clr);
            int LowerLimit = Math.min(countList.size(),(((currentPage + (i-1)) - 1) * NUM_ENTRIES_PAGE) + 1);
            int UpperLimit = Math.min(countList.size(), NUM_ENTRIES_PAGE * (currentPage + (i-1)));
            Page[i].setText(Integer.valueOf(LowerLimit) + " - " + Integer.valueOf(UpperLimit));
            //if (countList.size() < LowerLimit) Page[i].setVisibility(View.INVISIBLE);
        }

    }

    SearchView.OnQueryTextListener queryTextListener = new SearchView.OnQueryTextListener() {

        public boolean onQueryTextChange(String newText) {

            if (newText == null || newText.trim().equals("")){
                //searchView.clearFocus();
                listLayout.requestFocus();
                //searchView.setInputType(InputType.TYPE_NULL);
                //searchView.setInputType(InputType.TYPE_CLASS_TEXT);
                //toggleKeyboard(getBaseContext());

                   // InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);

                }

                //searchView.setClickable(false);



            // Reset sorting by columns before searching
            ColumnName_preset = "_id";
            Order_preset = 0;

            ColumnToSearch_preset = searchColumns.get(searchColumn_Selected);
            Tag_preset = newText;
            currentPage = 1;
            RefreshListEntries();

            return false;
        }



        public boolean onQueryTextSubmit(String query)
        {

            return false;
        }



    };
    public View.OnClickListener onClick () {
        return new View.OnClickListener() {
            @TargetApi(11)

            public void onClick(View view) {

                final ViewGroup ListRow = (ViewGroup) view.getParent();



                if (view instanceof TextView) {


                    ListData tempList;
                    int c = ListRow.indexOfChild(view);
                    View FirstRow = listLayout.getChildAt(0);

                    String listName = ((TextView)ListRow.getChildAt(1)).getText().toString();
                    if (dbHandler.listItem_alreadyExists("listname", listName)){

                        //new MyTask().execute();

                        tempList = dbHandler.fetchDatabaseEntry("listname", listName);
                        Intent i = new Intent();
                        switch(c) {
                            case (1): //Event TITLE is clicked
                                i = new Intent(getApplicationContext(), EventPage.class);
                                break;
                            case (2): //Event ORGANIZATION is clicked
                                i = new Intent(getApplicationContext(), SponsorPage.class);
                                break;
                        }
                        i.putExtra("userValue", userValue);
                        i.putExtra("EventId", tempList.get_id());
                        startActivity(i);

                    }


                    else if (ListRow.equals(findViewById(R.id.linearLayout2))) {

                        for (int i=0; i <= 3; i++) {
                            int resId = getResources().getIdentifier("Header" + i, "id", getPackageName());
                            TextView col_header = (TextView) findViewById(resId);
                            if (view.equals(col_header))
                                c = i;
                        }

                        currentPage = 1;

                        listLayout.removeAllViews();
                        ColumnName_preset = ColumnNames[c];
                        Order_preset = Switch_sortOrder[c]? 1 : 0;
                        PopulateTableElements(ColumnName_preset, Order_preset, currentPage, ColumnToSearch_preset, Tag_preset);

                        int currentPageIndex = currentPage;
                        List<ListData> countList = dbHandler.returnListEntries_byOrder(ColumnName_preset, Order_preset, ColumnToSearch_preset, Tag_preset);
                        for (int i = 1; i <= 3; i++) {

                            int clr = (Page[i].equals(Page[currentPageIndex]))? 0x82ffac8b : 0x00000000;
                            Page[i].setBackgroundColor(clr);
                            int LowerLimit = Math.min(countList.size(),(((currentPage + (i-1)) - 1) * NUM_ENTRIES_PAGE) + 1);
                            int UpperLimit = Math.min(countList.size(), NUM_ENTRIES_PAGE * (currentPage + (i-1)));
                            Page[i].setText(Integer.valueOf(LowerLimit) + " - " + Integer.valueOf(UpperLimit));
                        }

                        for (int i=0; i<4; i++)
                            Switch_sortOrder[i] ^= (i != c)? Switch_sortOrder[i] : Boolean.TRUE;

                    }
                }

                if (view instanceof ImageView) {
                    if ((((ImageView) view).getDrawable().getConstantState()).equals(getResources().getDrawable(R.drawable.remove2).getConstantState())) {
                        final ViewGroup ListDesc = (ViewGroup) (listLayout.getChildAt(listLayout.indexOfChild(ListRow) + 1));
                        final String listName = ((TextView) ListRow.getChildAt(1)).getText().toString();


                        AlertDialog.Builder adb = new AlertDialog.Builder(ListActivity.this);
                        adb.setIcon(R.drawable.remove2);
                        adb.setTitle("Confirm Delete");

                        adb.setMessage("Are you sure you want to remove the listing for this event?");

                        adb.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });

                        adb.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                                dbHandler.deleteList(listName);
                                //listLayout.removeView(ListRow);
                                //listLayout.removeView(ListDesc);
                                listLayout.removeAllViews();
                                Toast.makeText(getApplicationContext(), "The listing '" + listName + "' has been removed.", Toast.LENGTH_LONG).show();
                                float HeightPos = ((ScrollView) findViewById(R.id.ScrollView01)).getY();
                                PopulateTableElements(ColumnName_preset, Order_preset, currentPage, ColumnToSearch_preset, Tag_preset);

                                ((ScrollView) findViewById(R.id.ScrollView01)).setY(HeightPos);

                                int currentPageIndex = (currentPage % 3 == 0) ? 3 : currentPage % 3;


                                List<ListData> countList = dbHandler.returnListEntries_byOrder(ColumnName_preset, Order_preset, ColumnToSearch_preset, Tag_preset);
                                for (int refPageIndex = 1; refPageIndex <= 3; refPageIndex++) {
                                    int LowerLimit = Integer.parseInt(Page[refPageIndex].getText().toString().split("-")[0].trim());
                                    int refPageNum = ((LowerLimit - 1) / NUM_ENTRIES_PAGE) + 1;
                                    int UpperLimit = Math.min(countList.size(), NUM_ENTRIES_PAGE * refPageNum);
                                    Page[refPageIndex].setText(Integer.valueOf(LowerLimit) + " - " + Integer.valueOf(UpperLimit));
                                    if (countList.size() < LowerLimit) {
                                        Page[refPageIndex].setVisibility(View.INVISIBLE);
                                        if (refPageIndex == currentPageIndex) {
                                            currentPage -= 1;
                                            currentPageIndex = (currentPage % 3 == 0) ? 3 : currentPage % 3;
                                            for (int i = 1; i <= 3; i++) {
                                                int clr = (Page[i].equals(Page[currentPageIndex])) ? 0x82ffac8b : 0x00000000;
                                                Page[i].setBackgroundColor(clr);

                                                if (currentPageIndex == 3) {
                                                    int LowerLimit_page = (((currentPage - (3 - i)) - 1) * NUM_ENTRIES_PAGE) + 1;
                                                    int UpperLimit_page = NUM_ENTRIES_PAGE * (currentPage - (3 - i));
                                                    Page[i].setText(Integer.valueOf(LowerLimit_page) + " - " + Integer.valueOf(UpperLimit_page));
                                                    if (Page[i].getVisibility() == View.INVISIBLE)
                                                        Page[i].setVisibility(View.VISIBLE);
                                                    if (countList.size() < LowerLimit_page)
                                                        Page[i].setVisibility(View.INVISIBLE);
                                                }
                                            }
                                        }
                                    }

                                }


                                //currentPage += 1;
                                listLayout.removeAllViews();
                                PopulateTableElements(ColumnName_preset, Order_preset, currentPage, ColumnToSearch_preset, Tag_preset);
                                if (countList.size() < ((currentPage * NUM_ENTRIES_PAGE) + 1))
                                    Next.setVisibility(View.INVISIBLE);
                                int Bounds[] = {Integer.parseInt(Page[currentPageIndex].getText().toString().split("-")[0].trim()), Integer.parseInt(Page[currentPageIndex].getText().toString().split("-")[1].trim())};
                                Results.setText(String.format("Showing results %s-%s of %s", Bounds[0], Bounds[1], countList.size()));

                            }
                        });



                        adb.show();

                    }
                }



                if (view instanceof Button) {
                    String tmp = ((Button) view).getText().toString().trim();
                    switch (tmp) {
                        case "Edit":
                            TextView test = (TextView) ((ViewGroup) view.getParent()).getChildAt(0);
                            Toast.makeText(getApplicationContext(), test.getText().toString(), Toast.LENGTH_LONG).show();
                        break;
                    }
                }
            }


        };
}



    public class MyTask extends AsyncTask<Void, Void, Void> {

        public void onPreExecute() {

            // Disable this here, as it detracts from the activity's transition view in this case
            processView = ProgressDialog.show(ListActivity.this, "", "Please wait...", true, false);
            processView.show();
        }

        public Void doInBackground(Void... unused) {

            //Intent i = new Intent(getApplicationContext(), AddEvent.class);
            //startActivity(i);
            return null;
        }

        public void onPostExecute(Void unused) {
            //processView.dismiss();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
