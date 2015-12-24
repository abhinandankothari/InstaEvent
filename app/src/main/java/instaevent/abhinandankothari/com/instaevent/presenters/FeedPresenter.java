package instaevent.abhinandankothari.com.instaevent.presenters;

import instaevent.abhinandankothari.com.instaevent.R;
import instaevent.abhinandankothari.com.instaevent.views.FeedActivityView;

public class FeedPresenter {
    FeedActivityView feedView;

    public FeedPresenter(FeedActivityView view) {
        this.feedView = view;
    }

    public void captureImage() {
        feedView.captureImage();
    }

    public void configureFabButton() {
        feedView.configureFabButton();
    }

    public boolean onNavigationItemSelected(int id) {
        try {
            switch (id) {
                case R.id.nav_profile:
                    feedView.showProfileFragment();
                    return true;
                case R.id.nav_feed:
                    feedView.showFeedFragment();
                    return true;
                case R.id.user_logout:
                    feedView.gotoLoginActivity();
                    return true;
                default:
                    return false;
            }
        } finally {
            feedView.closeDrawer();
        }
    }
}
