package instaevent.abhinandankothari.com.instaevent;

import com.parse.Parse;
import com.parse.ParseObject;
import com.parse.ParseUser;

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
        if (ParseUser.getCurrentUser() == null) {
            ParseUser.logInInBackground("vjdhama", "test");
        }
    }
}
