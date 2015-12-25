package instaevent.abhinandankothari.com.instaevent.views;

import com.parse.Parse;
import com.parse.ParseFacebookUtils;
import com.parse.ParseObject;
import com.parse.ParseTwitterUtils;
import com.parse.ParseUser;

import instaevent.abhinandankothari.com.instaevent.R;
import instaevent.abhinandankothari.com.instaevent.models.Like;
import instaevent.abhinandankothari.com.instaevent.models.Post;
import instaevent.abhinandankothari.com.instaevent.models.User;

/**
 * Created by vjdhama on 16/12/15.
 */
public class InstaEventApp extends android.app.Application {
    @Override
    public void onCreate() {
        super.onCreate();

        ParseUser.registerSubclass(User.class);
        ParseObject.registerSubclass(Post.class);
        ParseObject.registerSubclass(Like.class);

        Parse.initialize(this);


        Parse.setLogLevel(Parse.LOG_LEVEL_DEBUG);


        ParseFacebookUtils.initialize(this);

        ParseTwitterUtils.initialize(getString(R.string.twitter_consumer_key),
                getString(R.string.twitter_consumer_secret));
    }
}
