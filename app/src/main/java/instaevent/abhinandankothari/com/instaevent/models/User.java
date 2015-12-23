package instaevent.abhinandankothari.com.instaevent.models;

import com.parse.ParseClassName;
import com.parse.ParseUser;

/**
 * Created by abhinandan on 23/12/15.
 */
@ParseClassName("_User")
public class User extends ParseUser {

    public static final String NAME = "name";
    public static final String LOCATION = "location";

    public String getName() {
        return getString(NAME);
    }

    public String getLocation() {
        return getString(LOCATION);
    }
}
