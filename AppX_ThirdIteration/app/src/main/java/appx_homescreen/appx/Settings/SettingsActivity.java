package appx_homescreen.appx.Settings;
import appx_homescreen.appx.*;


import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
        import android.support.v7.app.AppCompatActivity;
        import android.view.Menu;
        import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
        import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by sueparks on 11/17/15.
 */
public class SettingsActivity extends AppCompatActivity {
    AppX_SettingsEntries settingsHandler; //Public reference to database file
    SettingsData newSettings;

    String userValue;
    EditText  edit_Password;  // editWhat, editWhen, editWhere, editHow, editMore;  //User-input fields
    EditText edit_Email;
    private CheckBox saveLoginCheckBox;
    // private ListView songsListView;

    // String formatEmail, formatPassword; //, formatWhen, formatWhere, formatHow;

    public static String[] Edited = new String[2];
    public static int[] Edited_pos = new int[2];
    String editUserValue;
    ListData newPreferences;
    int selectedPosition = 0;

    final static String[] numbers = new String[] {
            "3", "5", "10", "20"};

    @Override public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        settingsHandler = new AppX_SettingsEntries(this,null,null,6);
        Bundle extras = getIntent().getExtras();
        userValue = (extras != null)? extras.getString("userValue"): "N/A";
        // songsListView = (ListView) findViewById(R.id.settingsListView);

        edit_Email = (EditText) findViewById(R.id.edit_Email);
        edit_Password = (EditText) findViewById(R.id.edit_Password);
        //   databaseHelper = new AppX_SettingsEntries(this, null, null, 4);

        //   if (databaseHelper.mostRecentEntry()!= null) {
        //     edit_Email.setText(databaseHelper.mostRecentEntry());
        //   edit_Password.requestFocus();


        TextView link_to_profile = (TextView) findViewById(R.id.link_to_profile);
        link_to_profile.setTextColor(0xFF0C9CC6);
        link_to_profile.setOnClickListener(onClick());
        link_to_profile.setOnTouchListener(onTouch());



        final GridView gridView = (GridView) findViewById(R.id.gridView);

        gridView.setDrawSelectorOnTop(false);

        //v.setSelected(true);

        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, numbers);/*
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);

                int color = android.R.color.transparent; // Transparent
                if (position == selectedPosition) {
                    color = android.R.color.holo_orange_light; // Opaque Blue
                }
                view.setBackgroundResource(color);
                return view;
            }
        };
*/
        gridView.setAdapter(adapter);


        //gridView.setItemChecked(1,true);
        //gridView.performItemClick(gridView,2,0);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                selectedPosition = position;
                newSettings = new SettingsData(userValue, Integer.parseInt(numbers[position]));
                settingsHandler.addPreferences(newSettings);
                settingsHandler.update_userPreferences_NumEntries(userValue, Integer.parseInt(numbers[position]));

                //v.setPressed(true);
                //v.setSelected(true);
                //v.setBackgroundResource(android.R.layout.
            }
        });

    }
    // }


    // expandableView = (ExpandableListView) findViewById(R.id.expandableList);

    //dbHandler = new Appx_ListEntries(this, null, null, 5);

    // createData();
    // ExpandableListView listView = (ExpandableListView) findViewById(R.id.expandableList);
    //ExpandableListAdapter_Item1 adapter = new ExpandableListAdapter_Item1(this,
    //      groups);
    //listView.setAdapter(adapter);



    // public void createData() {
    //  String[] Group_names = {"Email:","Password:"};
    //  Group group = null;
    //  for (int j = 0; j < 2; j++) {
    //      group = new Group(Group_names[j]);

    //     if (j == 0){
    //         final View layout = getLayoutInflater().inflate(R.layout.listgroup_when_details, null);
    //        group.children.add(layout.findViewById(R.id.RLayout));
    //    }
    //     else {
    //        final View layout = getLayoutInflater().inflate(R.layout.listgroup_where_details, null);
    //        group.children.add(layout.findViewById(R.id.RLayout));

    //   }
    //   groups.append(j, group);
    // }
    //  }



    //selection=(TextView)findViewById(R.id.filter_Categories);
    //Spinner spin=(Spinner)findViewById(R.id.spinner);
    //spin.setOnItemSelectedListener((AdapterView.OnItemSelectedListener) this);
    //ArrayAdapter<String>aa=new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, items);
    //aa.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item);
    //spin.setAdapter(aa); } public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {
    //selection.setText(items[position]);

    // public void onNothingSelected(AdapterView<?> parent) {
    //   selection.setText(""); }


    //protected void onCreate(Bundle savedInstanceState) {
    //  super.onCreate(savedInstanceState);
    //setContentView(R.layout.activity_settings);
    //}

    //  CheckBox rememberMeCheckBox = (CheckBox) findViewById(R.id.checkbox_remember_me);

    // boolean rememberMe = rememberMeCheckBox.isChecked(); // get checked status

    // detect user change - onChechedChangeListener
    // CompoundButton.OnCheckedChangeListener onRememberMeCheckBoxCheckedChange = new CompoundButton.OnCheckedChangeListener() {

    //  @Override
    //   public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
    //     if(checked)
    //     {
    //  Toast.makeText(SettingsActivity.this, "rememberMeCheckBox was checked", Toast.LENGTH_SHORT).show();
    //     }
    //   else
    //  {
    //    Toast.makeText(SettingsActivity.this, "rememberMeCheckBox was unchecked", Toast.LENGTH_SHORT).show();
    //   }
    // rememberMeCheckBox.setOnCheckedChangeListener(onRememberMeCheckBoxCheckedChange);

    // }

    // };



    public View.OnClickListener onClick () {
        return new View.OnClickListener() {

            public void onClick(View view) {

                switch(view.getId()) {
                    case R.id.link_to_profile:
                        Intent profile = new Intent(getApplicationContext(), UserProfile.class);
                        profile.putExtra("userValue",userValue);
                        startActivity(profile);
                        break;
                    default:
                        break;
                }
            }
        };
    }

            public View.OnTouchListener onTouch () {
        return new View.OnTouchListener() {

            public boolean onTouch(View view, MotionEvent event) {
                TextView castView = (TextView) view;

                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    //if (view.getParent().equals(findViewById(R.id.linearLayout2))) {
                    castView.setTextColor(0xFF0DB6E2);

                }
                else {
                    castView.setTextColor(0xFF0C9CC6);
                }
                return false;
            }

        };
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



