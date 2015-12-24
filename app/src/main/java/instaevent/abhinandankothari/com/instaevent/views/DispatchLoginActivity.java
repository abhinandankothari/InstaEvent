package instaevent.abhinandankothari.com.instaevent.views;

import com.parse.ui.ParseLoginDispatchActivity;

public class DispatchLoginActivity extends ParseLoginDispatchActivity {
    @Override
    protected Class<?> getTargetClass() {
        return FeedActivity.class;
    }
}
