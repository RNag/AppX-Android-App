package appx_homescreen.appx.Settings;
import appx_homescreen.appx.*;


        import android.os.Bundle;
        import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
        import android.view.MenuItem;
        import android.view.View;
        import android.widget.AdapterView;
        import android.widget.ArrayAdapter;
        import android.widget.Spinner;
        import android.widget.TextView;

        import java.util.List;



/**
 * Created by sueparks on 11/23/15.
 */

/*
public  class ProfileSettings extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    Spinner mSpinSettings, mSpinSettings_Simple;
    TextView mName, mAge, mEmail;
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spinner);
        mSpinSettings_Simple= (Spinner) findViewById(R.id.spinStudent);

        mAge = (TextView)findViewById(R.id.mAge);
        mEmail = (TextView)findViewById(R.id.mEmail);
        mName = (TextView)findViewById(R.id.mAge);
        mSpinSettings_Simple.setOnItemSelectedListener(this);

        //insertDummyData();
        //load_SettingsSample();
        //load_SettingsSimple();
    }

    public void OnItemSelected (AdapterView<?> parentView, View selectedItemView, int position, long id){
        if (parentView == findViewById(R.id.spinStudent)) {
            SettingsData selected = (SettingsData) parentView.getItemAtPosition(position);
            mName.setText("Name: " + selected.getName());
            mAge.setText("Age: " + selected.getAge().toString());
            mEmail.setText("Email: " + selected.getEmail());
        }
        else if (parentView == findViewById((R.id.spinStudent_Simple))) {
            mName.setText(((TextView) selectedItemView).getText());
            mAge.setText("You must  query DB");
            mEmail.setText("You must query DB");

        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parentView) {
        mAge.setText("");
        mName.setText("");
        mEmail.setText("");
    }


    private void insertDummyData() {
        SettingsCRUD set = new SettingsCRUD(this);
        set.Delete();

        for (Integer i =0; i<5; i++) {
            SettingsData settingsData = new SettingsData();
            settingsData.setAge(25);
            settingsData.setEmail("johnDoe@gmail.com");
            settingsData.setName("John Doe " + i.toString());

            set.insert(settingsData);
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    private void load_SettingsSimple() {
        ArrayAdapter<String> spinnerAdpater;
        SettingsCRUD db = new SettingsCRUD(getApplicationContext());
        List<String> list = db.getAll_Simple();
        spinnerAdpater = new ArrayAdapter<String>(ProfileSettings.this,android.R.layout.simple_spinner_item, list);
        mSpinSettings_Simple.setAdapter(spinnerAdpater);

        spinnerAdpater.setDropDownViewResource(android.R.layout.simple_spinner_item);
    }

    //This is the customize adapter binding method as you can see
    //there is only a slight different if you compare with functino loadStudent_Simple.
    public  void load_SettingsSample(){
        SettingsAdapter  adapter;
        SettingsCRUD db = new SettingsCRUD(getApplicationContext());
        List<SettingsData> sample_list = db.getAll();
        adapter = new SettingsAdapter(ProfileSettings.this,
                android.R.layout.simple_spinner_item , sample_list );
        mSpinSettings_Simple.setAdapter(adapter);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

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
*/
