package instaevent.abhinandankothari.com.instaevent.models;

import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;

@ParseClassName("Post")
public class Post extends ParseObject {
    public static final String DESCRIPTION = "description";
    public static final String IMAGE = "image";
    public static final String USER = "user";

    @SuppressWarnings("unused - for parse framework")
    public Post() {
    }

    public Post(ParseUser user, ParseFile image, String description) {
        put(USER, user);
        put(IMAGE, image);
        put(DESCRIPTION, description);
    }

    public ParseFile getImage() {
        return getParseFile(IMAGE);
    }

    public String getDescription() {
        return getString(DESCRIPTION);
    }
}
