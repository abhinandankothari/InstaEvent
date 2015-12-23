package instaevent.abhinandankothari.com.instaevent;

import com.parse.Parse;
import com.parse.ParseFacebookUtils;
import com.parse.ParseObject;
import com.parse.ParseTwitterUtils;

import instaevent.abhinandankothari.com.instaevent.models.Post;

/**
 * Created by vjdhama on 16/12/15.
 */
public class InstaEventApp extends android.app.Application {
    @Override
    public void onCreate() {
        super.onCreate();

        ParseObject.registerSubclass(Post.class);

        Parse.initialize(this);


        Parse.setLogLevel(Parse.LOG_LEVEL_DEBUG);


        ParseFacebookUtils.initialize(this);

        ParseTwitterUtils.initialize(getString(R.string.twitter_consumer_key),
                getString(R.string.twitter_consumer_secret));
    }
}
