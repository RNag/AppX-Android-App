package appx_homescreen.appx;

//Some Espresso Related imports

import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.test.ActivityInstrumentationTestCase2;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.ViewInteraction;
import android.support.test.espresso.action.ViewActions;
import android.test.ActivityInstrumentationTestCase2;
import android.test.ApplicationTestCase;
import android.util.Log;

import android.support.test.InstrumentationRegistry;
import android.view.View;
import android.widget.TextView;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;

import static android.support.test.espresso.assertion.ViewAssertions.*;
import static android.support.test.espresso.matcher.ViewMatchers.*;
import static android.support.test.espresso.action.ViewActions.*;
import static org.hamcrest.Matchers.*; //not()

import static android.support.test.espresso.action.ViewActions.swipeLeft;
import static org.hamcrest.Matchers.containsString;






public class AddEventActivityTest extends ActivityInstrumentationTestCase2<AddEvent>{
    private AddEvent mActivity;

    public AddEventActivityTest()
    {
        super(AddEvent.class);
    }

    // The standard JUnit 3 setUp method run for for every test
    @Override
    protected void setUp() throws Exception
    {
        super.setUp();
        injectInstrumentation(InstrumentationRegistry.getInstrumentation());
        mActivity = getActivity();
    }


    String newEventName = "Just another Sample Event";
    String newEventName_2 = "The more descriptive event";



    public void testCreateEvent_sameActivity()
    {

        String newOrganizationName = "VCU";
        String newEventTitle = newEventName;
        String newEventDate = "2/5/16";
        String newEventLocation = "Richmond";
        String newEventDesc = "N/A";

        onView(withId(R.id.editWho)).perform(scrollTo(), typeText(newOrganizationName), closeSoftKeyboard());
        onView(withId(R.id.editWhat)).perform(scrollTo(), typeText(newEventTitle), closeSoftKeyboard());
        onView( withId(mActivity.editWhen.getId())).perform(scrollTo(), typeText(newEventDate), closeSoftKeyboard());
        onView( withId(mActivity.editWhere.getId())).perform(scrollTo(), typeText(newEventLocation), closeSoftKeyboard());
        onView( withId(mActivity.editHow.getId())).perform(typeText(newEventDesc), closeSoftKeyboard());
        onView(withId(R.id.doneButton)).perform(scrollTo(), click());

        //On successful event creation, all input fields will be reset
        onView(withText(newOrganizationName)).check(doesNotExist());
        onView(withText(newEventTitle)).check(doesNotExist());
        onView(withText(newEventDate)).check(doesNotExist());
        onView(withText(newEventLocation)).check(doesNotExist());
        onView(withText(newEventDesc)).check(doesNotExist());
    }


    public void testCreateEvent_2_sameActivity()
    {

        String newOrganizationName = "VCU";
        String newEventTitle = newEventName + String.valueOf(2);
        String newEventDate = "2/5/16";
        String newEventLocation = "Richmond";
        String newEventDesc = "N/A";


        onView(withId(R.id.editWho)).perform(scrollTo(), typeText(newOrganizationName), closeSoftKeyboard());
        onView(withId(R.id.editWhat)).perform(scrollTo(), typeText(newEventTitle), closeSoftKeyboard());
        onView( withId(mActivity.editWhen.getId())).perform(scrollTo(), typeText(newEventDate), closeSoftKeyboard());
        onView( withId(mActivity.editWhere.getId())).perform(scrollTo(), typeText(newEventLocation), closeSoftKeyboard());
        onView( withId(mActivity.editHow.getId())).perform(typeText(newEventDesc), closeSoftKeyboard());
        onView(withId(R.id.doneButton)).perform(scrollTo(), click());

        //On successful event creation, all input fields will be reset
        onView(withText(newOrganizationName)).check(doesNotExist());
        onView(withText(newEventTitle)).check(doesNotExist());
        onView(withText(newEventDate)).check(doesNotExist());
        onView(withText(newEventLocation)).check(doesNotExist());
        onView(withText(newEventDesc)).check(doesNotExist());
    }


    public void testCreateEvent_AdditionalInformation_sameActivity()
    {
        String newEventTitle = newEventName_2;
        String newOrganizationName = "TestSponsor";
        String newEvent_StartDate = "10/11/15";
        String newEvent_EndDate = "10/13/15";
        String[] newEvent_startTime = {"4","15","PM"};
        String[] newEvent_endTime = {"7","30","PM"};

        String newEvent_addressLine = "Monroe Park Campus";
        String newEvent_cityName = "Richmond";
        String newEvent_state = "VA";

        String newEventDesc = "Second test event";

        onView(withId(R.id.editWho)).perform(scrollTo(), typeText(newOrganizationName), closeSoftKeyboard());
        onView(withId(R.id.editWhat)).perform(scrollTo(), typeText(newEventTitle), closeSoftKeyboard());

        onView(allOf(withId(R.id.groupView), hasSibling(withId(mActivity.editWhen.getId()))))
                .perform(click());
        onView( withId(ExpandableListAdapter_Item1.edit_fromWhen.getId())).perform(scrollTo(), typeText(newEvent_StartDate), closeSoftKeyboard());
        onView(withId(ExpandableListAdapter_Item1.edit_toWhen.getId())).perform(scrollTo(), typeText(newEvent_EndDate), closeSoftKeyboard());


        for (int i =0; i< 3; i++) {
            onView(withId(ExpandableListAdapter_Item1.staticSpinner2[i].getId())).perform(click());
            onData(allOf(is(instanceOf(String.class)), containsString(newEvent_startTime[i]))).perform(click());
            onView(withId(ExpandableListAdapter_Item1.staticSpinner2[i].getId())).check(matches(withSpinnerText(containsString(newEvent_startTime[i]))));
        }

        for (int i =3; i< 6; i++) {
            onView(withId(ExpandableListAdapter_Item1.staticSpinner2[i].getId())).perform(click());
            onData(allOf(is(instanceOf(String.class)), containsString(newEvent_endTime[i-3]))).perform(click());
            onView(withId(ExpandableListAdapter_Item1.staticSpinner2[i].getId())).check(matches(withSpinnerText(containsString(newEvent_endTime[i-3]))));
        }

        onView(allOf(withId(R.id.groupView), hasSibling(withId(mActivity.editWhen.getId()))))
                .perform(click());
        onView(allOf(withId(R.id.groupView), hasSibling(withId(mActivity.editWhere.getId()))))
                .perform(click());

        onView( withId(ExpandableListAdapter_Item1.edit_AddressLine.getId())).perform(scrollTo(), typeText(newEvent_addressLine), closeSoftKeyboard());
        onView(withId(ExpandableListAdapter_Item1.edit_cityName.getId())).perform(scrollTo(), typeText(newEvent_cityName), closeSoftKeyboard());
        onView(withId(ExpandableListAdapter_Item1.edit_state.getId())).perform(scrollTo(), typeText(newEvent_state), closeSoftKeyboard());

        onView(allOf(withId(R.id.groupView), hasSibling(withId(mActivity.editWhere.getId()))))
                .perform(click());
        onView(withId(mActivity.editHow.getId())).perform(typeText(newEventDesc), closeSoftKeyboard());
        onView(withId(R.id.doneButton)).perform(scrollTo(), click());

        //On successful event creation, all input fields will be reset
        onView(withText(newOrganizationName)).check(doesNotExist());
        onView(withText(newEventTitle)).check(doesNotExist());
        onView(allOf(withId(R.id.groupView), hasSibling(withId(mActivity.editWhen.getId())))).perform(scrollTo(), click());
        onView(withText(newEvent_StartDate)).check(doesNotExist());
        onView(withText(newEvent_EndDate)).check(doesNotExist());
        onView(allOf(withId(R.id.groupView), hasSibling(withId(mActivity.editWhen.getId())))).perform(scrollTo(), click());

        waitForShortDuration();

        onView(allOf(withId(R.id.groupView), hasSibling(withId(mActivity.editWhere.getId())))).perform(scrollTo(), click());
        onView(withText(newEvent_addressLine)).check(doesNotExist());
        onView(withText(newEvent_cityName)).check(doesNotExist());
        onView(withText(newEvent_state)).check(doesNotExist());
        onView(allOf(withId(R.id.groupView), hasSibling(withId(mActivity.editWhere.getId())))).perform(scrollTo(), click());
    }


    public void testSadPath_InvalidEvent1_sameActivity()
    {

        String newOrganizationName = "Unspecified";
        String newEventTitle = "Example for an invalid event";

        String newEvent_StartDate = "10/11/15";
        String newEvent_EndDate = "8/12/15";
        String[] newEvent_startTime = {"4","45","PM"};
        String[] newEvent_endTime = {"3","30","PM"};
        String newEventLocation = "Somewhere";
        String newEventDesc = "In this event creation, the end date is earlier than the start date. Therefore, this event will not be accepted.";

        onView(withId(R.id.editWho)).perform(scrollTo(), typeText(newOrganizationName), closeSoftKeyboard());
        onView(withId(R.id.editWhat)).perform(scrollTo(), typeText(newEventTitle), closeSoftKeyboard());

        onView(allOf(withId(R.id.groupView), hasSibling(withId(mActivity.editWhen.getId()))))
                .perform(click());
        onView( withId(ExpandableListAdapter_Item1.edit_fromWhen.getId())).perform(scrollTo(), typeText(newEvent_StartDate), closeSoftKeyboard());
        onView(withId(ExpandableListAdapter_Item1.edit_toWhen.getId())).perform(scrollTo(), typeText(newEvent_EndDate), closeSoftKeyboard());


        for (int i =0; i< 3; i++) {
            onView(withId(ExpandableListAdapter_Item1.staticSpinner2[i].getId())).perform(click());
            onData(allOf(is(instanceOf(String.class)), containsString(newEvent_startTime[i]))).perform(click());
            onView(withId(ExpandableListAdapter_Item1.staticSpinner2[i].getId())).check(matches(withSpinnerText(containsString(newEvent_startTime[i]))));
        }

        for (int i =3; i< 6; i++) {
            onView(withId(ExpandableListAdapter_Item1.staticSpinner2[i].getId())).perform(click());
            onData(allOf(is(instanceOf(String.class)), containsString(newEvent_endTime[i-3]))).perform(click());
            onView(withId(ExpandableListAdapter_Item1.staticSpinner2[i].getId())).check(matches(withSpinnerText(containsString(newEvent_endTime[i-3]))));
        }
        onView(allOf(withId(R.id.groupView), hasSibling(withId(mActivity.editWhen.getId()))))
                .perform(click());

        onView( withId(mActivity.editWhere.getId())).perform(scrollTo(), typeText(newEventLocation), closeSoftKeyboard());
        onView( withId(mActivity.editHow.getId())).perform(typeText(newEventDesc), closeSoftKeyboard());
        onView(withId(R.id.doneButton)).perform(scrollTo(), click());

        //On successful event creation, all input fields will be reset
        onView(withText(newOrganizationName)).check(matches(isDisplayed()));
        onView(withText(newEventTitle)).check(matches(isDisplayed()));

        /*
        onView(allOf(withId(R.id.groupView), hasSibling(withId(mActivity.editWhen.getId())))).perform(scrollTo(), click());
        onView(withText(newEvent_StartDate)).check(matches(isDisplayed()));
        onView(withText(newEvent_EndDate)).check(matches(isDisplayed()));
        onView(allOf(withId(R.id.groupView), hasSibling(withId(mActivity.editWhen.getId())))).perform(scrollTo(), click());*/

        onView(withText(newEventLocation)).check(matches(isDisplayed()));
        onView(withText(newEventDesc)).check(matches(isDisplayed()));


    }

    public void waitForShortDuration() {
        try { //we want to wait here to pause from switching between sorting from ascending to descending order
            Thread.sleep(1000);
            // Do some stuff
        } catch (Exception e) {
            e.getLocalizedMessage();
        }
    }

    /*





 public void testSadPath_InvalidEvent2_sameActivity()
    {

        String newOrganizationName = "Unspecified";
        String newEventTitle = "Example for an invalid event";
        String newEventDate = "Saturdays";
        String newEventLocation = "?";
        String newEventDesc = "In this event creation, the when entry 'Saturdays' is accepted. However the where entry is not, in this case.";

        onView(withId(R.id.editWho)).perform(scrollTo(), typeText(newOrganizationName), closeSoftKeyboard());
        onView(withId(R.id.editWhat)).perform(scrollTo(), typeText(newEventTitle), closeSoftKeyboard());
        onView( withId(mActivity.editWhen.getId())).perform(scrollTo(), typeText(newEventDate), closeSoftKeyboard());
        onView( withId(mActivity.editWhere.getId())).perform(scrollTo(), typeText(newEventLocation), closeSoftKeyboard());
        onView( withId(mActivity.editHow.getId())).perform(typeText(newEventDesc), closeSoftKeyboard());
        onView(withId(R.id.doneButton)).perform(scrollTo(), click());

        //On successful event creation, all input fields will be reset
        onView(withText(newOrganizationName)).check(matches(isDisplayed()));
        onView(withText(newEventTitle)).check(matches(isDisplayed()));
        onView(withText(newEventDate)).check(matches(isDisplayed()));
        onView(withText(newEventLocation)).check(matches(isDisplayed()));
        onView(withText(newEventDesc)).check(matches(isDisplayed()));;
    }

    public void testSadPath_InvalidEvent3_sameActivity()
    {
        String newEventTitle = "Invalid Event 3";
        String newOrganizationName = "N/A";
        String newEvent_Date = "8/12/3";
        String newEvent_addressLine = "1209 Summerset Ave";
        String newEvent_cityName = "Woodbridge";
        String newEvent_state = "virginia";

        String newEventDesc = "In this case, an invalid state code is entered (should be 2 chars. to be accepted)";

        onView(withId(R.id.editWho)).perform(scrollTo(), typeText(newOrganizationName), closeSoftKeyboard());
        onView(withId(R.id.editWhat)).perform(scrollTo(), typeText(newEventTitle), closeSoftKeyboard());
        onView( withId(mActivity.editWhen.getId())).perform(scrollTo(), typeText(newEvent_Date), closeSoftKeyboard());

        onView(allOf(withId(R.id.groupView), hasSibling(withId(mActivity.editWhere.getId()))))
                .perform(click());
        onView( withId(ExpandableListAdapter_Item1.edit_AddressLine.getId())).perform(scrollTo(), typeText(newEvent_addressLine), closeSoftKeyboard());
        onView(withId(ExpandableListAdapter_Item1.edit_cityName.getId())).perform(scrollTo(), typeText(newEvent_cityName), closeSoftKeyboard());
        onView(withId(ExpandableListAdapter_Item1.edit_state.getId())).perform(scrollTo(), typeText(newEvent_state), closeSoftKeyboard());
        onView(allOf(withId(R.id.groupView), hasSibling(withId(mActivity.editWhere.getId()))))
                .perform(click());

        onView(withId(mActivity.editHow.getId())).perform(typeText(newEventDesc), closeSoftKeyboard());
        onView(withId(R.id.doneButton)).perform(scrollTo(), click());

        //On successful event creation, all input fields will be reset
        onView(withText(newOrganizationName)).check(matches(isDisplayed()));
        onView(withText(newEventTitle)).check(matches(isDisplayed()));
        onView(withText(newEvent_Date)).check(matches(isDisplayed()));
        onView(withText(newEventDesc)).check(matches(isDisplayed()));
        onView(allOf(withId(R.id.groupView), hasSibling(withId(mActivity.editWhere.getId())))).perform(scrollTo(), click());
        onView(withText(newEvent_addressLine)).check(matches(isDisplayed()));
        onView(withText(newEvent_cityName)).check(matches(isDisplayed()));
        onView(withText(newEvent_state)).check(matches(isDisplayed()));
        onView(allOf(withId(R.id.groupView), hasSibling(withId(mActivity.editWhere.getId())))).perform(scrollTo(), click());
    }
*/
}
