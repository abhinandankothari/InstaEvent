package instaevent.abhinandankothari.com.instaevent;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import instaevent.abhinandankothari.com.instaevent.views.FeedActivityView;
import instaevent.abhinandankothari.com.instaevent.presenters.FeedPresenter;

import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class FeedPresenterTest {
    @Mock
    FeedActivityView view;

    @Test
    public void callsCaptureImageOnFabClick() {
        FeedPresenter feedPresenter = new FeedPresenter(view);
        feedPresenter.captureImage();
        verify(view).captureImage();
    }

    @Test
    public void testProfileOnNavigationItemSelected() throws Exception {
        FeedPresenter feedPresenter = new FeedPresenter(view);
        feedPresenter.onNavigationItemSelected(R.id.nav_profile);
        verify(view).showProfileFragment();
        verify(view).closeDrawer();
    }

    @Test
    public void testFeedOnNavigationItemSelected() throws Exception {
        FeedPresenter feedPresenter = new FeedPresenter(view);
        feedPresenter.onNavigationItemSelected(R.id.nav_feed);
        verify(view).showFeedFragment();
        verify(view).closeDrawer();
    }

    @Test
    public void testLogoutOnNavigationItemSelected() throws Exception {
        FeedPresenter feedPresenter = new FeedPresenter(view);
        feedPresenter.onNavigationItemSelected(R.id.user_logout);
        verify(view).gotoLoginActivity();
        verify(view).closeDrawer();
    }
}