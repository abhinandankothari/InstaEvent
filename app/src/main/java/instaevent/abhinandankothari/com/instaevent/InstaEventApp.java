package instaevent.abhinandankothari.com.instaevent;

import com.squareup.picasso.LruCache;
import com.squareup.picasso.Picasso;

/**
 * Created by vjdhama on 16/12/15.
 */
public class InstaEventApp extends android.app.Application {
    @Override
    public void onCreate() {
        super.onCreate();

        Picasso picasso = new Picasso.Builder(this)
                .loggingEnabled(true)
                .memoryCache(new LruCache(1024 * 1024 * 30))
                .build();

        Picasso.setSingletonInstance(picasso);

    }
}
