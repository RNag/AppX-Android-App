package appx_homescreen.appx;

//Some Espresso Related imports
import android.support.test.espresso.Espresso;
import android.support.test.espresso.ViewInteraction;
import android.support.test.espresso.action.ViewActions;
import android.test.ActivityInstrumentationTestCase2;
import android.test.ApplicationTestCase;
import android.util.Log;

import android.support.test.InstrumentationRegistry;
import static android.support.test.espresso.Espresso.*;
import static android.support.test.espresso.assertion.ViewAssertions.*;
import static android.support.test.espresso.matcher.ViewMatchers.*;
import static android.support.test.espresso.action.ViewActions.*;
import static org.hamcrest.Matchers.*; //not()


public class LoginActivityTest extends ActivityInstrumentationTestCase2<LoginActivity>{
    private LoginActivity mActivity;

    public LoginActivityTest()
    {
        super(LoginActivity.class);
    }

    // The standard JUnit 3 setUp method run for for every test
    @Override
    protected void setUp() throws Exception
    {
        super.setUp();
        injectInstrumentation(InstrumentationRegistry.getInstrumentation());
        mActivity = getActivity();
    }

    /** NOTE: you should only run the test cases for this activity after you run the RegisterActivityTest, before that creates the
     * default account smithj@gmail.com that is used in this test case. Also make sure that it is the most recently created account.
     * If needed, you can always change the DATABASE_VERSION defined in AppX_Users and all known accounts will be deleted.
     */


    public void testSadCase1_InvalidAccount_sameActivity()
    {

        String emailAdr = "this@invalid_email";

        Espresso.closeSoftKeyboard();
        onView(withId(R.id.editUsername)).perform(clearText(),typeText(emailAdr), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.editPassword)).perform(typeText("pass"), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.loginButton)).perform(click());

        //get the text which the fragment shows
        ViewInteraction welcomeText = onView(withId(R.id.welcomeUser));

        //check the fragments text is now visible in the activity
        welcomeText.check(doesNotExist());

        /*
        onView(withId(R.id.editUsername)).perform(clearText(),typeText(emailAdr + ".com"), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.editPassword)).perform(clearText(),typeText("pass"), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.loginButton)).perform(click());
        welcomeText.check(doesNotExist());*/

    }


    public void testvalidateAccount_sameActivity()
    {
        String fillEmail = "smithj@gmail.com";
        String firstName = "John";

        Espresso.closeSoftKeyboard();
        onView(withId(R.id.editUsername)).perform(clearText(),typeText(fillEmail), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.editPassword)).perform(typeText("test2"), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.loginButton)).perform(click());

        //get the text field which the next activity shows
        ViewInteraction welcomeText = onView(withId(R.id.welcomeUser));

        //check the activity's text is now visible in the activity
        welcomeText.check(doesNotExist());

        try { //we want to wait because RegisterActivity.class also waits before transitioning here
            Thread.sleep(1500);
            // Do some stuff
        } catch (Exception e) {
            e.getLocalizedMessage();
        }


        Espresso.closeSoftKeyboard();


        onView(withId(R.id.editUsername)).check(matches(withText(fillEmail)));
        onView(withId(R.id.editPassword)).perform(clearText(), typeText("test"), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.loginButton)).perform(click());


        //check the activity's text is now visible in the activity
        welcomeText.check(matches(isDisplayed()));
        welcomeText.check(matches(withText("Welcome, " + firstName + "!")));

    }

}




