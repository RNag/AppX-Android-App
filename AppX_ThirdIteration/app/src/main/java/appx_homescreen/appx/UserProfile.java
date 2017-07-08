package appx_homescreen.appx;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class UserProfile extends AppCompatActivity {

    AppX_Users dbHandler;
    InputData currentUser;
    String userValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        dbHandler = new AppX_Users(this, null, null, 6);
        Bundle extras = getIntent().getExtras();
        userValue = (extras != null)? extras.getString("userValue"): "N/A";

        if (extras != null) {
            currentUser = dbHandler.returnUserEntries(userValue);



        TextView firstName = (TextView) findViewById(R.id.firstName);
        TextView lastName = (TextView) findViewById(R.id.lastName);
        TextView fillGender = (TextView) findViewById(R.id.fillGender);
        TextView fillAge = (TextView) findViewById(R.id.fillAge);

        TextView fillOccupation = (TextView) findViewById(R.id.fillOccupation);
        TextView fillOrg = (TextView) findViewById(R.id.fillOrg);
        TextView fillEdLevel = (TextView) findViewById(R.id.fillEdLevel);

        TextView fillEmail = (TextView) findViewById(R.id.fillEmail);
        TextView desc = (TextView) findViewById(R.id.fillDescription);

               // toHeader.setVisibility(View.INVISIBLE);


            //Toast.makeText(getApplicationContext(), userValue, Toast.LENGTH_LONG).show();

            if (currentUser != null) {
                String[] Name = (currentUser.get_fullName()).split(" ");
                firstName.setText(Name[0]);
                lastName.setText(Name[1]);

                String sexValue = (currentUser.get_prefix() == 1)? "M" : "F";
                fillGender.setText(sexValue);

                String ageValue = (currentUser.get_age() == 0)? "N/A" : String.valueOf(currentUser.get_age());
                fillAge.setText(ageValue);

                fillOccupation.setText(currentUser.get_Occupation());
                fillOrg.setText(currentUser.get_Org());

                String[] string_array = getResources().getStringArray(R.array.edLevel_values);

                fillEdLevel.setText(string_array[currentUser.get_edLevel()]);


                fillEmail.setText(currentUser.get_userName());

            }

        }

    }

/*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_event_info, menu);
        return true;
    }*/

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
