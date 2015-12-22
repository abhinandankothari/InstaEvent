package instaevent.abhinandankothari.com.instaevent.models;

import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;

/**
 * Created by vjdhama on 21/12/15.
 */

@ParseClassName("Post")
public class Post extends ParseObject{
    public static final String DESCRIPTION = "description";
    public static final String IMAGE = "image";

    public ParseFile getImage(){
        return getParseFile(IMAGE);
    }

    public String getDescription(){
        return getString(DESCRIPTION);
    }

    public void setImage(ParseFile image){
        put("image", image);
    }

    public void setDescription(String description){
        put("description", description);
    }
}
