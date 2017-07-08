package appx_homescreen.appx;

import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class EventPage extends AppCompatActivity {

    Appx_ListEntries dbHandler;
    ListData currentEvent;
    SimpleDateFormat fromFormat, toFormat;
    String formatDate, format_endDate, format_fromTime, format_toTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        if (ListActivity.processView != null) {
            if (ListActivity.processView.isShowing()) {
                ListActivity.processView.dismiss();
            }
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_info);

        dbHandler = new Appx_ListEntries(this, null, null, 6);

        int EventId;
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            EventId = extras.getInt("EventId");
            currentEvent = dbHandler.fetchDatabaseEntry("_id", String.valueOf(EventId));
            toFormat = new SimpleDateFormat("M/d/yy", java.util.Locale.US);
            fromFormat = new SimpleDateFormat("yyyy-MM-dd", java.util.Locale.US);
            try {
                formatDate = toFormat.format(fromFormat.parse(currentEvent.get_listDate()));
                format_endDate = (currentEvent.get_list_endDate().equals(""))? null:  toFormat.format(fromFormat.parse(currentEvent.get_list_endDate()));
            }
            catch (ParseException e) {
                formatDate = currentEvent.get_listDate();
                format_endDate = null;
            }
        }



        TextView eventName = (TextView) findViewById(R.id.fillWhat);
        TextView org = (TextView) findViewById(R.id.fillWho);
        TextView day = (TextView) findViewById(R.id.fillWhen);
        TextView des = (TextView) findViewById(R.id.fillDescription);

        TextView fromWhen = (TextView) findViewById(R.id.fromWhen);
        TextView toWhen = (TextView) findViewById(R.id.toWhen);
        TextView fromTime = (TextView) findViewById(R.id.fromTime);
        TextView toTime = (TextView) findViewById(R.id.toTime);
        TextView fromHeader = (TextView) findViewById(R.id.fromHeader);
        TextView toHeader = (TextView) findViewById(R.id.toHeader);

        TextView addressHeader = (TextView) findViewById(R.id.addressHeader);
        TextView cityHeader = (TextView) findViewById(R.id.cityHeader);
        TextView stateHeader = (TextView) findViewById(R.id.stateHeader);
        TextView loc = (TextView) findViewById(R.id.fillWhere);
        TextView fillAddress = (TextView) findViewById(R.id.fillAddress);
        TextView fillCity = (TextView) findViewById(R.id.fillCity);
        TextView fillState = (TextView) findViewById(R.id.fillState);



        /*
        fromHeader.setPaintFlags(fromHeader.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        toHeader.setPaintFlags(toHeader.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        addressHeader.setPaintFlags(addressHeader.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        cityHeader.setPaintFlags(cityHeader.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        stateHeader.setPaintFlags(stateHeader.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);*/

        if (extras != null) {
            eventName.setText(currentEvent.get_listTitle());
            org.setText(currentEvent.get_listOrg());

            if (format_endDate == null) {
                day.setText(formatDate);
                fromWhen.setVisibility(View.INVISIBLE);
                fromTime.setVisibility(View.INVISIBLE);
                toWhen.setVisibility(View.INVISIBLE);
                toTime.setVisibility(View.INVISIBLE);
                fromHeader.setVisibility(View.INVISIBLE);
                toHeader.setVisibility(View.INVISIBLE);
            } else {
                day.setVisibility(View.INVISIBLE);
                fromWhen.setText(formatDate);
                toWhen.setText(format_endDate);
                fromTime.setText(currentEvent.get_list_fromTime());
                toTime.setText(currentEvent.get_list_toTime());
            }

            //Toast.makeText(getApplicationContext(),currentEvent.get_listWhere() + "," + currentEvent.get_list_Address() + "," + currentEvent.get_list_cityName(), Toast.LENGTH_LONG).show();
            if (!currentEvent.get_list_cityName().isEmpty() || !currentEvent.get_list_State().isEmpty()) {
                loc.setVisibility(View.INVISIBLE);

                fillAddress.setText(currentEvent.get_list_Address());
                fillCity.setText(currentEvent.get_list_cityName());
                fillState.setText(currentEvent.get_list_State());
            } else {

                addressHeader.setVisibility(View.INVISIBLE);
                cityHeader.setVisibility(View.INVISIBLE);
                stateHeader.setVisibility(View.INVISIBLE);
                fillAddress.setVisibility(View.INVISIBLE);
                fillCity.setVisibility(View.INVISIBLE);
                fillState.setVisibility(View.INVISIBLE);
                loc.setText(currentEvent.get_listWhere());
            }

            des.setText(currentEvent.get_listAbout());
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
