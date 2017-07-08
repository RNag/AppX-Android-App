package appx_homescreen.appx;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {
    EditText inputAddr;
    EditText inputPass;
    String userValue;
    AppX_Users dbHandler;
    /** These are the optional text field to see the entries in database (uncomment it out to show them) */
    //TextView testVal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        inputAddr = (EditText) findViewById(R.id.editUsername);
        inputPass = (EditText) findViewById(R.id.editPassword);
        //testVal = (TextView) findViewById(R.id.testVal);
        dbHandler = new AppX_Users(this, null, null, 4);

        if (dbHandler.mostRecentEntry()!= null) {
            inputAddr.setText(dbHandler.mostRecentEntry());
            inputPass.requestFocus();
        }
    }


    public void onClick (View view) {
        switch (view.getId()){
            case R.id.link_to_register:
                Intent i = new Intent(getApplicationContext(), RegisterActivity.class);
                i.putExtra("tempString",inputAddr.getText().toString());
                startActivity(i);
                break;

            case R.id.loginButton:
                userValue = inputAddr.getText().toString();
                if (dbHandler.checkAddr(userValue)){
                    if (dbHandler.ValidateDataEntry(userValue, inputPass.getText().toString())) {

                        Intent splash = new Intent(getApplicationContext(), SplashActivity.class);
                        String firstName = dbHandler.fetchDatabaseEntry(userValue,"name").split(" ")[0];
                        splash.putExtra("userValue",userValue);
                        splash.putExtra("firstName",firstName);
                        startActivity(splash);
                    }

                    else {
                        Toast.makeText(getApplicationContext(), "Incorrect password, try again.", Toast.LENGTH_SHORT).show();}
                }
                else{
                    Toast.makeText(this, "This email/password does not exist, please create a new one below.", Toast.LENGTH_SHORT).show();
                }

                break;


        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
