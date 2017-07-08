package appx_homescreen.appx;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import appx_homescreen.appx.Settings.AppX_SettingsEntries;
import appx_homescreen.appx.Settings.SettingsData;

public class SponsorPage extends AppCompatActivity {

    Appx_ListEntries dbHandler;
    AppX_SettingsEntries settingsHandler;
    ListData currentEvent;
    SimpleDateFormat fromFormat, toFormat;
    String formatDate, format_endDate, format_fromTime, format_toTime;
    String userValue;
    SettingsData newSettings;
    TextView AvgRating;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sponsor_info);


        dbHandler = new Appx_ListEntries(this, null, null, 6);
        settingsHandler = new AppX_SettingsEntries(this, null, null, 6);

        int EventId;
        Bundle extras = getIntent().getExtras();
        userValue = (extras != null)? extras.getString("userValue"): "N/A";
        TextView fillWho = (TextView) findViewById(R.id.fillWho);
        TextView des = (TextView) findViewById(R.id.fillDescription);
        TextView aboutOrg = (TextView) findViewById(R.id.aboutOrg);
        AvgRating = (TextView) findViewById(R.id.AvgRating);
        String desc_text = "";

        if (extras != null) {

            RatingBar sponsorRating = (RatingBar) findViewById(R.id.sponsorRating);
            EventId = extras.getInt("EventId");
            currentEvent = dbHandler.fetchDatabaseEntry("_id", String.valueOf(EventId));

            get_sponsorRating();

            if (settingsHandler.userSponsor_alreadyExists(userValue, currentEvent.get_listOrg()))
                sponsorRating.setRating(settingsHandler.fetchSponsorRating(userValue, currentEvent.get_listOrg()));

            List<ListData> ListEntriesAuthored = dbHandler.fetchListEntriesAuthored("org", currentEvent.get_listOrg());

            if (ListEntriesAuthored.size() == 0)
                des.setText("No results found.");
            else {
                fillWho.setText(currentEvent.get_listOrg());
                aboutOrg.setText("This organization has contributed a total of " + String.valueOf(ListEntriesAuthored.size()) + " listings:");
                for (int i = 0; i < ListEntriesAuthored.size(); i++) {
                    ListData fetchListItem = ListEntriesAuthored.get(i);
                    String temp_string = String.format("-%s (Id: %s)\n", fetchListItem.get_listTitle(), String.valueOf(fetchListItem.get_id()));
                    desc_text = desc_text.concat(temp_string);
                }
                des.setText(desc_text);
            }

            //if rating value is changed,
            //display the current rating value in the result (textview) automatically
            sponsorRating.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
                public void onRatingChanged(RatingBar ratingBar, float rating,
                                            boolean fromUser) {

                    //((TextView)findViewById(R.id.UserRating)).setText(String.valueOf(rating));

                    if (settingsHandler.userSponsor_alreadyExists(userValue, currentEvent.get_listOrg()))
                        settingsHandler.update_userSponsorRating(userValue, currentEvent.get_listOrg(), rating);
                    else {
                        newSettings = new SettingsData(userValue, currentEvent.get_listOrg(), rating);
                        settingsHandler.addPreferences(newSettings);
                    }

                    get_sponsorRating();

                }
            });

        }
    }

    public void get_sponsorRating(){
    List<Float> floatList = settingsHandler.fetchSponsorRatings(currentEvent.get_listOrg());
    float avg_ratings = 0;
    if (floatList.size() > 0) {
        for (Float f : floatList) {
            avg_ratings += f;
            //Toast.makeText(getApplicationContext(), String.valueOf(f), Toast.LENGTH_SHORT).show();
        }
        avg_ratings /= floatList.size();
        AvgRating.setText(String.format("Sponsor rating: %.1f (based on %d votes)", avg_ratings, floatList.size()));

    }
    else {
        AvgRating.setText("No ratings available.");
        //Sponsor rating: 0 (based on 0 ratings)
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
