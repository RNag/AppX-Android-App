package appx_homescreen.appx;

//Some Espresso Related imports
import android.support.test.espresso.Espresso;
import android.support.test.espresso.ViewInteraction;
import android.support.test.espresso.action.ViewActions;
import android.test.ActivityInstrumentationTestCase2;
import android.test.ApplicationTestCase;
import android.util.Log;

import android.support.test.InstrumentationRegistry;
import android.widget.ImageView;
import android.widget.SearchView;

import org.junit.Test;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;

import static android.support.test.espresso.assertion.ViewAssertions.*;
import static android.support.test.espresso.matcher.ViewMatchers.*;
import static android.support.test.espresso.action.ViewActions.*;
import static org.hamcrest.Matchers.*; //not()


/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */


public class ListActivityTest extends ActivityInstrumentationTestCase2<ListActivity>{
    private ListActivity mActivity;

    public ListActivityTest()
    {
        super(ListActivity.class);
    }

    // The standard JUnit 3 setUp method run for for every test
    @Override
    protected void setUp() throws Exception
    {
        super.setUp();
        injectInstrumentation(InstrumentationRegistry.getInstrumentation());
        mActivity = getActivity();
    }

    //Run this test case after AddEventActivityTest

    String newEventName = "Just another Sample Event";
    String newEventName_2 = "The more descriptive event";;

    //Organization and date for Event 1. This might change based on what was modified in AddEventsActivityTest
    String newOrganizationName = "VCU";
    String newEventDate = "2/5/16";

    //Organization and date for Event 2. This might change based on what was modified in AddEventsActivityTest
    String newOrganizationName_2 = "TestSponsor";
    String newEvent_StartDate_2 = "10/11/15";

    public void testCheckEventsAreAdded_sameActivity()
    {


        onView(withText(newEventName)).check(matches(isDisplayed()));
        onView(allOf(withText(newOrganizationName), hasSibling(withText(newEventName)))).check(matches(isDisplayed()));
        onView(allOf(withText(newEventDate), hasSibling(withText(newEventName)))).check(matches(isDisplayed()));

        onView(withText(newEventName + String.valueOf(2))).check(matches(isDisplayed()));
        onView(allOf(withText(newOrganizationName), hasSibling(withText(newEventName+ String.valueOf(2))))).check(matches(isDisplayed()));
        onView(allOf(withText(newEventDate), hasSibling(withText(newEventName + String.valueOf(2))))).check(matches(isDisplayed()));

        onView(withText(newEventName_2)).check(matches(isDisplayed()));
        onView(allOf(withText(newOrganizationName_2), hasSibling(withText(newEventName_2)))).check(matches(isDisplayed()));
        onView(allOf(withText(newEvent_StartDate_2), hasSibling(withText(newEventName_2)))).check(matches(isDisplayed()));
    }

    public void testSadPath_CheckEventsAreNotShown_sameActivity()
    {

        onView(withText("Next")).perform(scrollTo(), click());

        //Scroll to top of page
        onView(withId(R.id.Header1)).perform(scrollTo());

        //Check that it does NOT exist (because we just navigated to a new page)
        onView(withText(newEventName)).check(doesNotExist());
        onView(allOf(withText(newOrganizationName), hasSibling(withText(newEventName)))).check(doesNotExist());
        onView(allOf(withText(newEventDate), hasSibling(withText(newEventName)))).check(doesNotExist());

        onView(withText(newEventName + String.valueOf(2))).check(doesNotExist());
        onView(allOf(withText(newOrganizationName), hasSibling(withText(newEventName+ String.valueOf(2))))).check(doesNotExist());
        onView(allOf(withText(newEventDate), hasSibling(withText(newEventName + String.valueOf(2))))).check(doesNotExist());

        onView(withText(newEventName_2)).check(doesNotExist());
        onView(allOf(withText(newOrganizationName_2), hasSibling(withText(newEventName_2)))).check(doesNotExist());
        onView(allOf(withText(newEvent_StartDate_2), hasSibling(withText(newEventName_2)))).check(doesNotExist());
    }



    public void testCheckEventsInformation_2_isCorrect_sameActivity()
    {

        //We'll pull up additional information that was added for Event case #2 here. This is the test case where the information is more in-depth.
        String newEvent_EndDate_2 = "10/13/15";
        String Event2_startTime = "4:15 PM";
        String Event2_endTime = "7:30 PM";

        String Event2_addressLine = "Monroe Park Campus";
        String Event2_cityName = "Richmond";
        String Event2_state = "VA";

        String Event2_Desc = "Second test event";

        onView(withText(newEventName_2)).perform(click());
        onView(withText(newEventName_2)).check(matches(isDisplayed()));
        onView(withText(newOrganizationName_2)).check(matches(isDisplayed()));
        onView(withText(newEvent_StartDate_2)).check(matches(isDisplayed()));
        onView(withText(Event2_startTime)).check(matches(isDisplayed()));
        onView(withText(Event2_endTime)).check(matches(isDisplayed()));
        onView(withText(Event2_addressLine)).check(matches(isDisplayed()));
        onView(withText(Event2_cityName)).check(matches(isDisplayed()));
        onView(withText(Event2_state)).check(matches(isDisplayed()));
        onView(withText(Event2_Desc)).check(matches(isDisplayed()));
    }

    public void testCheckEventsInformation_isCorrect_sameActivity()
    {
        //Use the simple case for Event case #1 here. Basically only two additional entries (location and the full description) we need to confirm is correct.
        String newEventLocation = "Richmond";
        String newEventDesc = "N/A";

        onView(withText(newEventName)).perform(click());
        onView(withText(newEventName)).check(matches(isDisplayed()));
        onView(withText(newOrganizationName)).check(matches(isDisplayed()));
        onView(withText(newEventDate)).check(matches(isDisplayed()));
        onView(withText(newEventLocation)).check(matches(isDisplayed()));
        onView(withText(newEventDesc)).check(matches(isDisplayed()));
    }


    /*
    public void testDeleteCreatedEvent_sameActivity(){

        onView(allOf(withText("Date"), withParent(withId(R.id.linearLayout2)))).perform(scrollTo(), click());
        onView(allOf(withText("Date"), withParent(withId(R.id.linearLayout2)))).perform(scrollTo(), click());

        onView(withText("My Event")).perform(scrollTo()).check(matches(isDisplayed()));
        waitForShortDuration();
        onView(allOf(instanceOf(ImageView.class), hasSibling(withText("My Event")))).perform(doubleClick());

        onView(withText(("Delete"))).perform(ViewActions.longClick());
        onView(withText("My Event")).check(doesNotExist());
    }
*/

    public void testSearchForEvent_withId_sameActivity(){
        String Id_toSearch = "2162";

        onView(withId(R.id.columnSearch_spinner)).perform(scrollTo(), click());
        onData(allOf(is(instanceOf(String.class)), containsString("ID"))).perform(click());
        onView(withId(R.id.columnSearch_spinner)).check(matches(withSpinnerText(containsString("ID"))));

        onView(withId(R.id.searchView)).perform(typeText("62"),closeSoftKeyboard());
        onView(withText(Id_toSearch)).check(matches(isDisplayed()));
    }

    public void testSearchForEvent_withName_sameActivity(){
        String event_toSearch = "Sample VCU Event";
        //onView(withId(R.id.searchView)).perform(scrollTo(),click());

        onView(withId(R.id.columnSearch_spinner)).perform(scrollTo(), click());
        onData(allOf(is(instanceOf(String.class)), containsString("Title"))).perform(click());
        onView(withId(R.id.columnSearch_spinner)).check(matches(withSpinnerText(containsString("Title"))));

        onView(withId(R.id.searchView)).perform(typeText("vcu"),closeSoftKeyboard());
        onView(withText(event_toSearch)).check(matches(isDisplayed()));
    }

    public void testSearchForEvent_withSponsor_sameActivity(){
        String org_toSearch = "Chess Club";

        onView(withId(R.id.columnSearch_spinner)).perform(scrollTo(), click());
        onData(allOf(is(instanceOf(String.class)), containsString("Sponsor"))).perform(click());
        onView(withId(R.id.columnSearch_spinner)).check(matches(withSpinnerText(containsString("Sponsor"))));

        onView(withId(R.id.searchView)).perform(typeText("chess"),closeSoftKeyboard());
        onView(withText(org_toSearch)).check(matches(isDisplayed()));
    }

    public void testSadPath_IncorrectSearchTag_sameActivity(){
        String tag_toSearch = "vcu".toUpperCase();

        onView(withId(R.id.columnSearch_spinner)).perform(scrollTo(), click());
        onData(allOf(is(instanceOf(String.class)), containsString("Title"))).perform(click());
        onView(withId(R.id.columnSearch_spinner)).check(matches(withSpinnerText(containsString("Title"))));

        onView(withId(R.id.searchView)).perform(typeText("vc"), closeSoftKeyboard());
        onView(allOf(withText("Cycling Club @VCU"),withText(containsString(tag_toSearch)))).check(matches(isDisplayed()));
                onView(withId(R.id.searchView)).perform(typeText("k"), closeSoftKeyboard());
        onView(allOf(withText(containsString("No results found.")))).check(matches(isDisplayed()));
    }



    public void testSortByTitle_sameActivity(){
        onView(allOf(withText("Title"), withParent(withId(R.id.linearLayout2)))).perform(scrollTo(), click());
        waitForShortDuration();
        onView(allOf(withText("Title"), withParent(withId(R.id.linearLayout2)))).perform(scrollTo(), click());
        waitForShortDuration();
    }

    public void testSortByDate_sameActivity() {

        onView(allOf(withText("Date"), withParent(withId(R.id.linearLayout2)))).perform(scrollTo(), click());
        waitForShortDuration();
        onView(allOf(withText("Date"), withParent(withId(R.id.linearLayout2)))).perform(scrollTo(), click());
        waitForShortDuration();
    }

    public void waitForShortDuration() {
        try { //we want to wait here to pause from switching between sorting from ascending to descending order
            Thread.sleep(1500);
            // Do some stuff
        } catch (Exception e) {
            e.getLocalizedMessage();
        }
    }

}
