package appx_homescreen.appx;

import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.action.ViewActions;
import android.test.ActivityInstrumentationTestCase2;

import org.junit.Test;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.clearText;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withSpinnerText;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.*; //not()


/**
 * Created by Admin on 10/27/2015.
 */
public class RegisterActivityTest extends ActivityInstrumentationTestCase2<LoginActivity> {
    private LoginActivity mActivity;

    public RegisterActivityTest()
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

    /*
    @Test
    public void testRegisterActivity_checkValidEmailAddr() {
        boolean actual, expected;
        String email_toCheck = "this@valid.org";
        expected = true;
        actual = RegisterActivity.checkValidEmailAddr(email_toCheck);
        assertEquals("Is a valid email",
                expected, actual);

        email_toCheck = "this@invalid";
        expected = false;
        actual = RegisterActivity.checkValidEmailAddr(email_toCheck);
        assertEquals("Is an invalid email",
                expected, actual);

        email_toCheck = "another invalid email address";
        expected = false;
        actual = RegisterActivity.checkValidEmailAddr(email_toCheck);
        assertEquals("Is an invalid email",
                expected, actual);
    }

    @Test
    public void testRegisterActivity_isInRange() {
        boolean actual, expected;

        //checks if c is in range of a and b, i.e. a < c < b, or vice versa if a>b
        int RANGE_LOW = 1;
        int RANGE_HIGH = 100;

        actual = RegisterActivity.isInRange(RANGE_LOW, RANGE_HIGH, 96);
        expected = true;
        // use this method because float is not precise
        assertEquals("Number is in range of " + String.valueOf(RANGE_LOW) + " to " + String.valueOf(RANGE_HIGH),
                expected, actual);

        actual = RegisterActivity.isInRange(RANGE_LOW, RANGE_HIGH, 0);
        expected = false;
        assertEquals("Number is not in range of " + String.valueOf(RANGE_LOW) + " to " + String.valueOf(RANGE_HIGH),
                expected, actual);

        actual = RegisterActivity.isInRange(RANGE_LOW, RANGE_HIGH, 172);
        expected = false;
        assertEquals("Number is not in range of " + String.valueOf(RANGE_LOW) + " to " + String.valueOf(RANGE_HIGH),
                expected, actual);
    }


    public void testSadCaseRegisterAccount1_sameActivity(){
        String emailAdr = "rhparks@invalid_email";
        String firstName = "Rhonda";
        String lastName = "Parks";

        Espresso.closeSoftKeyboard();
        onView(withId(R.id.link_to_register)).perform(click());

        onView(withId(R.id.prefix_spinner)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("Mrs."))).perform(click());
        onView(withId(R.id.prefix_spinner)).check(matches(withSpinnerText(is("Mrs."))));

        onView(withId(R.id.editFirstName)).perform(scrollTo(), typeText(firstName), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.editLastName)).perform(scrollTo(), typeText(lastName), ViewActions.closeSoftKeyboard());

        onView(withId(R.id.editOccupation)).perform(scrollTo(), typeText("Lecturer"),ViewActions.closeSoftKeyboard());
        onView(withId(R.id.editOrg)).perform(scrollTo(), typeText("VCU"), ViewActions.closeSoftKeyboard());

        onView(withId(R.id.edLevel_spinner)).perform(click());
        onData(allOf(is(instanceOf(String.class)), containsString("Bachelor of Science"))).perform(click());
        onView(withId(R.id.edLevel_spinner)).check(matches(withSpinnerText(containsString("Bachelor of Science"))));

        onView(withId(R.id.editEmail)).perform(scrollTo(), clearText(), typeText(emailAdr), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.register)).perform(scrollTo(), click());
        onView(withText(("Ok"))).perform(ViewActions.pressBack());
        onView(withId(R.id.editPassword)).perform(scrollTo(), typeText("password"), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.register)).perform(click());
        onView(withText(("Ok"))).perform(ViewActions.pressBack());
        onView(withId(R.id.a6)).check(matches(isDisplayed())); //a(6) is the hazard flag for an incorrect e-mail address
        onView(withId(R.id.a7)).check(matches(not(isDisplayed()))); //a(7) is the hazard flag for an incorrect password (not shown in this case)


    }

    public void testSadCaseRegisterAccount2_sameActivity(){
        String emailAdr = "njhauser@valid_email";
        String firstName = "Nick";
        String lastName = "Hauser";

        Espresso.closeSoftKeyboard();
        onView(withId(R.id.link_to_register)).perform(click());

        onView(withId(R.id.editFirstName)).perform(scrollTo(), typeText(firstName), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.editLastName)).perform(scrollTo(), typeText(lastName), ViewActions.closeSoftKeyboard());

        onView(withId(R.id.editOccupation)).perform(scrollTo(), typeText("NA"),ViewActions.closeSoftKeyboard());
        onView(withId(R.id.editOrg)).perform(scrollTo(), typeText("U.S. Military"), ViewActions.closeSoftKeyboard());

        onView(withId(R.id.edLevel_spinner)).perform(click());
        onData(allOf(is(instanceOf(String.class)), containsString("High School"))).perform(click());
        onView(withId(R.id.edLevel_spinner)).check(matches(withSpinnerText(containsString("High School"))));

        onView(withId(R.id.editEmail)).perform(scrollTo(), clearText(), typeText(emailAdr), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.register)).perform(scrollTo(), click());
        onView(withText(containsString("required fields"))).perform(ViewActions.pressBack());
        onView(withId(R.id.editPassword)).perform(scrollTo(), typeText("NA"), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.register)).perform(scrollTo(), click());
        onView(withText(("Ok"))).perform(ViewActions.pressBack());
        onView(withId(R.id.a0)).perform(scrollTo()).check(matches(isDisplayed())); //a(0) is the hazard flag for an incorrect prefix/title
        onView(withId(R.id.a7)).check(matches(isDisplayed())); //a(7) is the hazard flag for an incorrect password


    }
*/

    public void testCreateAccount_sameActivity()
    {
        /** NOTE: YOU NEED TO CHANGE THE ADDRESS AFTER EACH TIME YOU RUN THIS TEST (OR UPDATE DATABASE VERSION IN APP_X_USERS FILE)*/
        String fillPrefix = "Mr.";
        String[] fillName = {"John", "Smith"};
        String fillOccupation = "Unknown";
        String fillSponsor = "VCU";
        String fill_edLevel = "Some College";
        String fillEmail = "smithj@gmail.com";
        String fillPassword = "test";

        Espresso.closeSoftKeyboard();
        onView(withId(R.id.link_to_register)).perform(click());

        onView(withId(R.id.prefix_spinner)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is(fillPrefix))).perform(click());
        onView(withId(R.id.prefix_spinner)).check(matches(withSpinnerText(is("Mr."))));

        onView(withId(R.id.editFirstName)).perform(scrollTo(), typeText(fillName[0]), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.editLastName)).perform(scrollTo(), typeText(fillName[1]), ViewActions.closeSoftKeyboard());

        onView(withId(R.id.editOccupation)).perform(scrollTo(), typeText(fillOccupation),ViewActions.closeSoftKeyboard());
        onView(withId(R.id.editOrg)).perform(scrollTo(), typeText(fillSponsor), ViewActions.closeSoftKeyboard());

        onView(withId(R.id.edLevel_spinner)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is(fill_edLevel))).perform(click());
        onView(withId(R.id.edLevel_spinner)).check(matches(withSpinnerText(is(fill_edLevel))));

        onView(withId(R.id.editEmail)).perform(scrollTo(), clearText(), typeText(fillEmail), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.editPassword)).perform(scrollTo(), typeText(fillPassword), ViewActions.closeSoftKeyboard());

        //Submit form after entering all required information
        onView(withId(R.id.register)).perform(scrollTo(), click());
        try { //we want to wait because RegisterActivity.class also waits before transitioning here
            Thread.sleep(2100);
            // Do some stuff
        } catch (Exception e) {
            e.getLocalizedMessage();
        }

        Espresso.closeSoftKeyboard();
        onView(withId(R.id.editUsername)).check(matches(withText(fillEmail)));

    }

}
