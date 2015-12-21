package instaevent.abhinandankothari.com.instaevent;

import com.parse.Parse;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.squareup.picasso.LruCache;
import com.squareup.picasso.Picasso;

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

        Picasso picasso = new Picasso.Builder(this)
                .loggingEnabled(true)
                .indicatorsEnabled(true)
                .memoryCache(new LruCache(1024 * 1024 * 30))
                .build();

        Picasso.setSingletonInstance(picasso);
    }
}
