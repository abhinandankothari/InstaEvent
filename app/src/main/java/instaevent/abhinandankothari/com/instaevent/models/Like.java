package instaevent.abhinandankothari.com.instaevent.models;

import com.parse.ParseClassName;
import com.parse.ParseObject;

/**
 * Created by abhinandan on 25/12/15.
 */
@ParseClassName("Like")
public class Like extends ParseObject {
    public static final String USER = "user";

    @SuppressWarnings("unused - for parse.com")
    public Like() {
    }

    public Like(User user) {
        put(USER, user);
    }

    public User getUser() {
        return (User) getParseUser(USER);
    }
}
