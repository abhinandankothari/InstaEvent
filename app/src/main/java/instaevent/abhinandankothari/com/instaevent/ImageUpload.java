package instaevent.abhinandankothari.com.instaevent;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.cloudinary.Cloudinary;
import com.cloudinary.android.Utils;
import com.cloudinary.utils.ObjectUtils;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;

import java.io.File;
import java.io.IOException;

public class ImageUpload extends AsyncTask<String, Void, String> {

    private final Context context;

    public ImageUpload(Context context) {
        this.context = context;
    }

    @Override
    protected String doInBackground(String... params) {
        Cloudinary cloudinary = new Cloudinary(Utils.cloudinaryUrlFromContext(context));
        try {
            String url = params[0];
//            cloudinary.uploader().upload(new File(url), ObjectUtils.emptyMap());
            ParseObject image = new ParseObject("Post");
            image.put("image", new ParseFile(new File(url)));
            image.put("imageUrl", url);
            image.save();
        } catch (Exception e) {
            Log.e("APP_LOG", "Error while uploading image", e);
        }
        return "Success";
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
    }
}
