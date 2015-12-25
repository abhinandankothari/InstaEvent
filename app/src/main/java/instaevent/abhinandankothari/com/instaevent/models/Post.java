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
    public static final String LIKE_COUNT = "like_count";
    public static final String COMMENT_COUNT = "comment_count";

    @SuppressWarnings("unused - for parse framework")
    public Post() {
    }

    public Post(User user, ParseFile image, String description) {
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

    public User getUser() {
        return (User) getParseUser(USER);
    }

    public int getLikeCounts() {
        return getInt(LIKE_COUNT);
    }

    public int getCommentCounts() {
        return getInt(COMMENT_COUNT);
    }
}
