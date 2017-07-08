package appx_homescreen.appx;

import android.app.Activity;
//import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;







public class RegisterActivity extends AppCompatActivity {

    AppX_Users dbHandler;
    String fullName, tempName;
    String[] Selected = new String[3];
    int[] Selected_pos = new int[3];
    Spinner[] staticSpinner = new Spinner[3];
    EditText[] inputField = new EditText[7];
    TextView[] haz_flag = new TextView[8];
    int numericAgeValue;
    static final int AGE = 4;
    static final int ADDR = 5;
    static final int PASS = 6;
    ProgressDialog processView;
int waitTime = 1000;

    ArrayAdapter<CharSequence>[] staticAdapter = new ArrayAdapter[3];
    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        dbHandler = new AppX_Users(this, null, null, 6);
        inputField[0] = (EditText) findViewById(R.id.editFirstName);
        inputField[1] = (EditText) findViewById(R.id.editLastName);
        inputField[2] = (EditText) findViewById(R.id.editOccupation);
        inputField[3] = (EditText) findViewById(R.id.editOrg);
        inputField[AGE] = (EditText) findViewById(R.id.editAge);
        inputField[ADDR] = (EditText) findViewById(R.id.editEmail);
        inputField[PASS] = (EditText) findViewById(R.id.editPassword);

        staticSpinner[0] = (Spinner) findViewById(R.id.prefix_spinner);
        staticSpinner[1] = (Spinner) findViewById(R.id.edLevel_spinner);

        for (int i=0; i< haz_flag.length; i++) {
            int resId = getResources().getIdentifier("a" + i, "id", getPackageName());
            haz_flag[i] = (TextView) findViewById(resId);
        }
        Bundle extras = getIntent().getExtras();
        if (extras != null)
            inputField[ADDR].setText(extras.getString("tempString"));

        // Create an ArrayAdapter using the string array and a default spinner
        staticAdapter[0] = ArrayAdapter
                .createFromResource(this, R.array.Prefix,
                        android.R.layout.simple_spinner_item);
        staticAdapter[1]= ArrayAdapter
                .createFromResource(this, R.array.edLevel_values,
                        android.R.layout.simple_spinner_item);

        // Specify the layout to use when the list of choices appears


        // Apply the adapter to the spinners
        for (int i=0; i<2; i++) {
            staticAdapter[i].setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            staticSpinner[i].setAdapter(staticAdapter[i]);
            staticSpinner[i].setOnItemSelectedListener(new SpinnerActivity());
        }


    }

    public class SpinnerActivity extends Activity implements AdapterView.OnItemSelectedListener {


        public void onItemSelected(AdapterView<?> parent, View view,
                                   int pos, long id) {
            for (int i=0; i<2; i++) {
                if (parent == staticSpinner[i]){
                    Selected[i] = parent.getItemAtPosition(pos).toString();
                    Selected_pos[i] = pos;}
            }
            // An item was selected. You can retrieve the selected item using
            // parent.getItemAtPosition(pos)
        }

        public void onNothingSelected(AdapterView<?> parent) {
            // Another interface callback
        }
    }

    public void Run_Login() {

        Intent i = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(i);
        RegisterActivity.this.finish();
    }

    public void onClick (View view) {
        String errorMsg = "Please fill out the required fields.\n";
        boolean throwInvalidError = false;
        switch (view.getId()) {
            case R.id.link_to_login:
                Run_Login();
                break;

            case R.id.register:

                for (int i = 2; i < 6; i++) {
                    if (inputField[i - 2].length() < 2) {
                        throwInvalidError = true;
                        haz_flag[i].setVisibility(View.VISIBLE);
                    } else {
                        haz_flag[i].setVisibility(View.INVISIBLE);
                    }
                }

                for (int i = 0; i < 2; i++) {
                    if (Selected_pos[i] == 0) {
                        throwInvalidError = true;
                        haz_flag[i].setVisibility(View.VISIBLE);
                    } else {
                        haz_flag[i].setVisibility(View.INVISIBLE);

                    }
                }

                if (!checkValidEmailAddr(inputField[ADDR].getText().toString())) {
                    throwInvalidError = true;
                    haz_flag[6].setVisibility(View.VISIBLE);
                    errorMsg = errorMsg.concat("\n-Please enter a valid email.");

                } else if (dbHandler.checkAddr(inputField[ADDR].getText().toString())) {
                    throwInvalidError = true;
                    haz_flag[6].setVisibility(View.VISIBLE);
                    errorMsg = errorMsg.concat("\n-This email address is already taken.");
                } else {
                    haz_flag[6].setVisibility(View.INVISIBLE);
                }

                if (inputField[PASS].getText().length() < 3) {
                    throwInvalidError = true;
                    haz_flag[7].setVisibility(View.VISIBLE);
                    errorMsg = errorMsg.concat("\n-Please enter a valid password (3 char. minimum)");
                } else {
                    haz_flag[7].setVisibility(View.INVISIBLE);
                }


                if (inputField[AGE].getText().length() > 0) {
                    numericAgeValue = Integer.parseInt(inputField[AGE].getText().toString());
                    if (inputField[AGE].getText().length() > 3 || !isInRange(1, 99, numericAgeValue) || inputField[AGE].getText().toString().contains(".")) {
                        throwInvalidError = true;
                        errorMsg = errorMsg.concat("\n-Please enter a valid age.");
                    }
                } else {
                    numericAgeValue = 0;
                }


                if (throwInvalidError) {
                    AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(RegisterActivity.this);
                    dialogBuilder.setMessage(errorMsg.trim());
                    dialogBuilder.setPositiveButton("Ok", null);
                    dialogBuilder.show();
                }
                 else {
                    fullName = "";
                    for (int i = 0; i < 2; i++) {
                        tempName = inputField[i].getText().toString().replaceAll("\\s", "");
                        fullName += tempName.substring(0, 1).toUpperCase() + tempName.substring(1) + " ";
                    }


                    new MyAwesomeAsyncTask().execute();
                    new Handler().postDelayed(new Runnable() {
                        public void run() {
                            processView.dismiss();
                            Run_Login();
                        }
                    }, 2100);
                    new Thread() {
                        public void run() {

                            try {
                                sleep(waitTime);
                                processView.setMessage("Redirecting to login...");

                            } catch (Exception e) {
                            }

                        }
                    }.start();


                }


                    break;
                }
        }


    public static boolean checkValidEmailAddr(String userInput){
        boolean emailIsValid = true;
        if (TextUtils.isEmpty(userInput) || !android.util.Patterns.EMAIL_ADDRESS.matcher(userInput).matches())
            emailIsValid = false;
        return emailIsValid;
    }

    public class MyClass implements Runnable {
        @Override
        public void run() {
            // Some Code
            try {
                Thread.sleep(5000);
            } catch (Exception e) {
            }
        }
    }
    private class MyAwesomeAsyncTask extends AsyncTask<Void, Void, Void> {

        private ProgressDialog mProgress;
        InputData user;

        @Override
        protected void onPreExecute() {
            user = new InputData((RegisterActivity.this).inputField[ADDR].getText().toString(), inputField[PASS].getText().toString(), fullName.trim(), inputField[2].getText().toString(), inputField[3].getText().toString(), Selected_pos[0], Selected_pos[1], numericAgeValue);

            processView = new ProgressDialog(RegisterActivity.this);
            processView.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            processView.setIndeterminate(true);
            processView.setCancelable(false);
            processView.setTitle("Success!");
            processView.setMessage("Please wait...");
            processView.show();


        }

        @Override
        protected Void doInBackground(Void... params) {
                    dbHandler.addUser(user);
                    return null;

        }

        @Override
        protected void onPostExecute(Void result) {


            super.onPostExecute(result);


        }

    }

    public static boolean isInRange(int a, int b, int c) {
        return b > a ? c >= a && c <= b : c >= b && c <= a;
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_register, menu);
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
