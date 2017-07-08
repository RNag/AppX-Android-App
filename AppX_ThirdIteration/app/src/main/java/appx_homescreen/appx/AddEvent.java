package appx_homescreen.appx;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.SparseArray;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AddEvent extends AppCompatActivity {
    Appx_ListEntries dbHandler; //Public reference to database file
    SparseArray<Group> groups = new SparseArray<Group>();

    EditText editWho, editWhat, editWhen, editWhere, editHow, editMore, edit_toWhen, edit_fromWhen, edit_AddressLine, edit_cityName, edit_State;  //User-input fields
    String formatWho, formatWhat, formatWhen, formatWhere, formatHow, format_fromWhen, format_toWhen, fromformatTime, toformatTime, formatAddressLine, formatCityName, formatState;
    public static String[] Selected = new String[6];
    public static int[] Selected_pos = new int[6];
    public static ExpandableListView expandableView = null;
    int formatMore;
    Spinner[] staticSpinner = new Spinner[6];
    ArrayAdapter<CharSequence>[] staticAdapter = new ArrayAdapter[6];
    String userValue;

    ListData newEvent;                                   //Public reference to the list class used to store entries in the database
    SimpleDateFormat fromFormat, toFormat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);

        Bundle extras = getIntent().getExtras();
        userValue = (extras != null) ? extras.getString("userValue") : "N/A";


        editWho = (EditText) findViewById(R.id.editWho);
        editWhat = (EditText) findViewById(R.id.editWhat);
        editHow = (EditText) findViewById(R.id.editHow);
        editMore = (EditText) findViewById(R.id.editMoreEvents);
        expandableView = (ExpandableListView) findViewById(R.id.expandableList);

        dbHandler = new Appx_ListEntries(this, null, null, 5);

        createData();
        ExpandableListView listView = (ExpandableListView) findViewById(R.id.expandableList);
        ExpandableListAdapter_Item1 adapter = new ExpandableListAdapter_Item1(this,
                groups);
        listView.setAdapter(adapter);


        editHow.postDelayed(new Runnable() {
            @Override
            public void run() {
                ExpandableListAdapter_Item1.editWhere.setNextFocusDownId(R.id.editHow);
                editWhen = ExpandableListAdapter_Item1.editWhen;
                // editWhen = (EditText) findViewById(ExpandableListAdapter_Item1.editWhen.getId());
                editWhere = ExpandableListAdapter_Item1.editWhere;
                //edit_fromWhen = (EditText) findViewById(ExpandableListAdapter_Item1.edit_fromWhen.getId());
                //edit_toWhen = (EditText) findViewById(ExpandableListAdapter_Item1.edit_toWhen.getId());
               edit_fromWhen = (EditText) ExpandableListAdapter_Item1.edit_fromWhen;
                edit_toWhen = (EditText) ExpandableListAdapter_Item1.edit_toWhen;

            }
        }, 1);


    }


    public void createData() {
        String[] Group_names = {"When:", "Where:"};
        Group group = null;
        for (int j = 0; j < 2; j++) {
            group = new Group(Group_names[j]);

            if (j == 0) {
                final View layout = getLayoutInflater().inflate(R.layout.listgroup_when_details, null);
                group.children.add(layout.findViewById(R.id.RLayout));
            } else {
                final View layout = getLayoutInflater().inflate(R.layout.listgroup_where_details, null);
                group.children.add(layout.findViewById(R.id.RLayout));

            }
            groups.append(j, group);
        }
    }


    public static class SpinnerActivity extends Activity implements AdapterView.OnItemSelectedListener {


        public void onItemSelected(AdapterView<?> parent, View view,
                                   int pos, long id) {
            ((TextView) parent.getChildAt(0)).setTextColor(Color.LTGRAY);
            ((TextView) parent.getChildAt(0)).setBackgroundColor(0xFF006699);
            ((TextView) parent.getChildAt(0)).setTextSize(16);
            for (int i = 0; i < 6; i++) {
                if (parent == ExpandableListAdapter_Item1.staticSpinner2[i]) {
                    Selected[i] = parent.getItemAtPosition(pos).toString();
                    Selected_pos[i] = pos;
                }
            }
            // An item was selected. You can retrieve the selected item using
            // parent.getItemAtPosition(pos)
        }

        public void onNothingSelected(AdapterView<?> parent) {
            // Another interface callback
        }
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.doneButton:

                //Toast.makeText(this, ExpandableListAdapter_Item1.editWhere.getText().toString(), Toast.LENGTH_SHORT).show();

                String errorMsg = "Please enter a valid description for:\n";
                boolean throwInvalidError = false;
                boolean selectedWhen_details = false;
                boolean selectedWhere_details = false;
                fromformatTime = "";
                toformatTime = "";

                formatWho = editWho.getText().toString().trim();
                formatWho = formatWho.replaceAll("\\s+", " ");
                formatHow = editHow.getText().toString().trim();
                formatHow = formatHow.replaceAll("( )+", " ");
                formatHow = formatHow.replaceAll("(\\r?\\n){2,}", "\n\n");

                if (formatWho.replaceAll("( )", "").length() < 2) {
                    throwInvalidError = true;
                    errorMsg = errorMsg.concat("\n-Who");
                }
                if (formatHow == null || formatHow.equals("")){
                    formatHow = "No description available.";
                }

                formatWhat = editWhat.getText().toString().trim();
                formatWhat = formatWhat.replaceAll("\\s+", " ");
                if (formatWhat.replaceAll("( )", "").length() < 2) {
                    throwInvalidError = true;
                    errorMsg = errorMsg.concat("\n-What");
                } else if (dbHandler.listItem_alreadyExists("listname", formatWhat)) {
                    throwInvalidError = true;
                    Toast.makeText(getApplicationContext(), "There is already a duplicate entry under 'What', your listing could not be added.", Toast.LENGTH_LONG).show();
                    return;
                }
                else
                    formatWhat = formatWhat.substring(0, 1).toUpperCase() + formatWhat.substring(1);

                formatWhen = editWhen.getText().toString().trim();
                formatWhen = formatWhen.replaceAll("\\s+", " ");

                if (formatWhen == null || formatWhen.equals("")){

                    edit_fromWhen = ExpandableListAdapter_Item1.edit_fromWhen;
                    edit_toWhen = ExpandableListAdapter_Item1.edit_toWhen;
                    format_fromWhen = (edit_fromWhen == null)? "" : edit_fromWhen.getText().toString().trim();
                    format_toWhen = (edit_toWhen == null)? "" : edit_toWhen.getText().toString().trim();
                    if (format_fromWhen.equals("") && format_toWhen.equals("") && Selected_pos[0]==0 && Selected_pos[3]==0 ){
                        throwInvalidError = true;
                        errorMsg = errorMsg.concat("\n-When");
                    }
                    else if (format_fromWhen.equals("") || format_toWhen.equals("") || Selected_pos[0]==0 || Selected_pos[3]==0) {
                        throwInvalidError = true;
                        errorMsg = errorMsg.concat("\n-From-to When");
                    }
                    else if ((Selected_pos[0] > Selected_pos[3]) && Selected_pos[2] == Selected_pos[5]) {
                        throwInvalidError = true;
                        errorMsg = errorMsg.concat("\n-From-to When");
                    }
                    else { //everything is OK
                        fromFormat = new SimpleDateFormat("M/d/yy", java.util.Locale.US);
                        toFormat = new SimpleDateFormat("yyyy-MM-dd", java.util.Locale.US);
                        try {
                            format_fromWhen = toFormat.format(fromFormat.parse(format_fromWhen));
                            format_toWhen = toFormat.format(fromFormat.parse(format_toWhen));

                            if ( ((Date) toFormat.parse(format_toWhen)).compareTo(((Date) toFormat.parse(format_fromWhen))) <= 0){
                                throwInvalidError = true;
                                errorMsg = errorMsg.concat("\n-From-to When ('to' date is earlier than start date)");
                            }

                        } catch (ParseException e) {
                            throwInvalidError = true;
                            errorMsg = errorMsg.concat("\n-From-to When");
                        }

                        fromformatTime = String.format("%s:%s %s", Selected[0], Selected[1], Selected[2]);
                        toformatTime = String.format("%s:%s %s", Selected[3], Selected[4], Selected[5]);
                        selectedWhen_details = true;
                    }
                }
                else {
                    if (formatWhen.replaceAll("( )", "").length() < 3) {
                        throwInvalidError = true;
                        errorMsg = errorMsg.concat("\n-When");
                    }
                    fromFormat = new SimpleDateFormat("M/d/yy", java.util.Locale.US);
                    toFormat = new SimpleDateFormat("yyyy-MM-dd", java.util.Locale.US);
                    try {
                        formatWhen = toFormat.format(fromFormat.parse(formatWhen));
                    } catch (ParseException e) {
                        formatWhen = formatWhen;
                    }
                }


                if (!editMore.getText().toString().equals("")) {
                    formatMore = Integer.parseInt(editMore.getText().toString());
                } else {
                        formatMore = 0;
                }

                formatWhere = editWhere.getText().toString().trim();
                formatWhere = formatWhere.replaceAll("\\s+", " ");


                if (formatWhere == null || formatWhere.equals("")) {
                    edit_AddressLine = ExpandableListAdapter_Item1.edit_AddressLine;
                    edit_cityName = ExpandableListAdapter_Item1.edit_cityName;
                    edit_State = ExpandableListAdapter_Item1.edit_state;

                    formatAddressLine = (edit_AddressLine == null) ? "" : edit_AddressLine.getText().toString().trim();
                    formatCityName = (edit_cityName == null) ? "" : edit_cityName.getText().toString().trim();
                    formatState = (edit_State == null) ? "" : edit_State.getText().toString().trim();
                    if (formatAddressLine.equals("") && formatCityName.equals("") && formatState.equals("")) {
                        throwInvalidError = true;
                        errorMsg = errorMsg.concat("\n-Where");
                    } else if (formatCityName.equals("") || formatState.equals("")) {
                        throwInvalidError = true;
                        errorMsg = errorMsg.concat("\n-Where (address)");
                    } else if (formatCityName.replaceAll("( )", "").length() < 2 || formatState.replaceAll("( )", "").length() != 2) {
                        throwInvalidError = true;
                        errorMsg = errorMsg.concat("\n-Where (address)");
                    }
                    else //everything is OK
                        selectedWhere_details = true;
                }
                    else {
                        if (formatWhere.replaceAll("( )", "").length() < 3) {
                            throwInvalidError = true;
                            errorMsg = errorMsg.concat("\n-Where");
                        }
                    }



                if (throwInvalidError) {
                    Toast.makeText(getApplicationContext(), errorMsg.trim(), Toast.LENGTH_LONG).show();
                } else {

                    if (selectedWhen_details || selectedWhere_details) {
                        newEvent = new ListData(formatWhat, formatWho, format_fromWhen, format_toWhen, fromformatTime, toformatTime, formatWhere, formatAddressLine, formatCityName, formatState, formatHow, userValue);

                    }
                    else {
                        newEvent = new ListData(formatWhat, formatWho, formatWhen, formatWhere, formatHow, userValue);


                    }
                    dbHandler.addList(newEvent);

                    editWho.setText("");
                    editWhat.setText("");
                    editWhen.setText("");
                    editWhere.setText("");
                    editHow.setText("");
                    editMore.setText("");
                    if (!(edit_fromWhen == null)) {
                        edit_fromWhen.setText("");
                        edit_toWhen.setText("");
                        for (int i = 0; i<= 5; i++)
                            ExpandableListAdapter_Item1.staticSpinner2[i].setSelection(0);
                    }

                    if (!(edit_AddressLine == null)) {
                        edit_AddressLine.setText("");
                        edit_cityName.setText("");
                        edit_State.setText("");
                    }

                    Toast.makeText(getApplicationContext(), "The event '" + formatWhat + "' was successfully added!", Toast.LENGTH_LONG).show();

                    break;
                }
        }
    }
}
