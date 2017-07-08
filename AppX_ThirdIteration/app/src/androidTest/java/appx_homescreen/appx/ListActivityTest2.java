package appx_homescreen.appx;

//Some Espresso Related imports
import android.support.test.espresso.Espresso;
import android.support.test.espresso.ViewInteraction;
import android.support.test.espresso.action.ViewActions;
import android.test.ActivityInstrumentationTestCase2;
import android.test.ApplicationTestCase;
import android.util.Log;

import android.support.test.InstrumentationRegistry;

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


public class ListActivityTest2 extends ActivityInstrumentationTestCase2<ListActivity> {
    private ListActivity mActivity;

    public ListActivityTest2() {
        super(ListActivity.class);
    }

    // The standard JUnit 3 setUp method run for for every test
    @Override
    protected void setUp() throws Exception {
        super.setUp();
        injectInstrumentation(InstrumentationRegistry.getInstrumentation());
        mActivity = getActivity();
    }

    //Run this test case after ListActivityTest


    @Test
    public void testListActivity_UniqueID() {
        int actualID, expectedID;

        mActivity.UniqueID = 9;
        actualID = mActivity.getUniqueID();
        expectedID = 10;
        assertEquals("A unique ID was successfully calculated",
                expectedID, actualID);

        mActivity.UniqueID = 17;
        actualID = mActivity.getUniqueID();
        expectedID = 6;
        assertThat(expectedID, not(equalTo(actualID)));

        mActivity.UniqueID = 122;
        actualID = mActivity.getUniqueID();
        expectedID = 123;
        assertThat(expectedID, equalTo(actualID));
    }

    @Test
    public void testAppX_ListEntries_isThreadCreator() {
        boolean actual, expected;
        actual = false;
        Appx_ListEntries dbHandler = new Appx_ListEntries(mActivity, null, null, 6);
        expected = dbHandler.isThreadCreator("Just another Sample Event", "Me");

        assertEquals("Is thread creator",
                expected, actual);

    }

    @Test
    public void testAppX_ListEntries_listItem_alreadyExists() {
        boolean actual, expected;

        actual = true;
        Appx_ListEntries dbHandler = new Appx_ListEntries(mActivity, null, null, 6);
        expected = dbHandler.listItem_alreadyExists("listname", "The second event");

        assertEquals("Event exists",
                expected, actual);

        actual = true;
        //another event that is automatically generated when running the ListActivity for first time
        expected = dbHandler.listItem_alreadyExists("listname", "Random meeting");
        assertEquals("Event exists",
                expected, actual);

        actual = false;
        expected = dbHandler.listItem_alreadyExists("listname", "Event that does not exist");
        assertEquals("Event does not exist",
                expected, actual);

        actual = true;
        expected = dbHandler.listItem_alreadyExists("org", "VCU");
        assertEquals("Event exists",
                expected, actual);
    }
}
