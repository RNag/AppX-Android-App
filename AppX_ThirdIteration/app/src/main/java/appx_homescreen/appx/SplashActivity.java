package appx_homescreen.appx;
import appx_homescreen.appx.Settings.*;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.text.style.UnderlineSpan;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class SplashActivity extends AppCompatActivity {

    String userValue = "N/A";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        TextView welcomeUserMsg = (TextView) findViewById(R.id.welcomeUser);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            welcomeUserMsg.setText("Welcome, " + extras.getString("firstName") + "!");
            userValue = extras.getString("userValue");

        }
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.link_to_events:
                Intent l = new Intent(getApplicationContext(), ListActivity.class);
                l.putExtra("userValue",userValue);
                startActivity(l);
                break;
            case R.id.link_to_newEvent:
                Intent ne = new Intent(getApplicationContext(), AddEvent.class);
                ne.putExtra("userValue",userValue);
                startActivity(ne);
                break;
            case R.id.link_to_settings:
                Intent s = new Intent(getApplicationContext(), SettingsActivity.class);
                s.putExtra("userValue",userValue);
                startActivity(s);
                break;
            default:
                break;

        }
    }
}