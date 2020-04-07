package com.example.bblocations;

import android.view.View;
import android.view.ViewGroup;
import com.example.about.AboutActivity;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withEffectiveVisibility;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ElementsInstrumentedTest {

    @Rule
    public ActivityTestRule<AboutActivity> activityRule = new ActivityTestRule<>(AboutActivity.class);

    @Test
    public void testUIElements() {

        // Check if the current view contains elements with given ids
        onView(withId(R.id.infoContainer)).check(matches(isDisplayed()));
        onView(childOf(withId(R.id.infoContainer), 0)).check(matches(withId(R.id.companyNameLayout)));
        onView(childOf(withId(R.id.companyNameLayout), 1)).check(matches(withId(R.id.companyName)));

        // Check if progressBar is currently visible
        onView(childOf(withId(R.id.outterLayout), 1)).check(matches(withId(R.id.progressBar)));
        onView(withId(R.id.progressBar)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));
    }

    public static Matcher<View> childOf(final Matcher<View> parentMatcher, final int childPosition) {
        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(org.hamcrest.Description description) {
                description.appendText("with "+childPosition+" child view of type parentMatcher");
            }

            @Override
            public boolean matchesSafely(View view) {
                if (!(view.getParent() instanceof ViewGroup)) {
                    return parentMatcher.matches(view.getParent());
                }

                ViewGroup group = (ViewGroup) view.getParent();
                return parentMatcher.matches(view.getParent()) && group.getChildAt(childPosition).equals(view);
            }
        };
    }
}
