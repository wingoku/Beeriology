package com.wingoku.punkBeer;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.idling.CountingIdlingResource;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.widget.EditText;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.pressImeActionButton;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static android.support.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.startsWith;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class MainActivityEspressoTest {
    @Rule
    public ActivityTestRule<MainActivity> mActivityRule =
            new ActivityTestRule<>(MainActivity.class);

    @Test
    public void useAppContext() throws Exception {
        CountingIdlingResource mainActivityIdlingResource = mActivityRule.getActivity().getEspressoIdlingResourceForMainActivity();

        // register IdlingResource used by MainActivity.java to enable syncing with Espresso for hand-made threads. For more info regarding
        // idling resource: https://developer.android.com/reference/android/support/test/espresso/IdlingResource.html
        Espresso.registerIdlingResources(mainActivityIdlingResource);
        beerDetailFragmentOpeningTest();

        // perform back press event
        Espresso.pressBack();

        beerImageBrowserFragmentOpeningTest();

        // perform back press event
        Espresso.pressBack();
        beerPhLevelFilteringTest();
    }

    private void beerDetailFragmentOpeningTest() {
        onView(withId(R.id.list_beers))
                .perform(actionOnItemAtPosition(1, click()));
        onView(withText(R.string.string_dialog_details_button)).perform(click()); // perform click on the dialog
        onView(withId(R.id.tv_title)).check(matches(isDisplayed()));
    }

    private void beerImageBrowserFragmentOpeningTest() {
        onView(withId(R.id.list_beers))
                .perform(actionOnItemAtPosition(1, click()));
        onView(withText(R.string.string_dialog_browser_button)).perform(click()); // perform click on the dialog
        onView(withText(startsWith(getResourceString(R.string.string_toolbar_beer_name)))).check(matches(isDisplayed()));
    }

    private void beerPhLevelFilteringTest() {
        onView(withId(R.id.action_search)).perform(click());
        // Type the text in the search field and submit the query
        onView(isAssignableFrom(EditText.class)).perform(typeText("4.4"), pressImeActionButton());
        Espresso.closeSoftKeyboard();
        onView(withText(R.string.string_end_budget_mode)).check(matches(isDisplayed()));
    }

    private String getResourceString(int id) {
        Context targetContext = InstrumentationRegistry.getTargetContext();
        return targetContext.getResources().getString(id);
    }
}