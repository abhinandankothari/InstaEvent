package instaevent.abhinandankothari.com.instaevent;

import android.content.Intent;
import android.os.AsyncTask;

import com.cloudinary.Cloudinary;
import com.cloudinary.android.Utils;
import com.cloudinary.utils.ObjectUtils;
import com.parse.ParseObject;

import java.io.File;
import java.io.IOException;

public class ImageUpload extends AsyncTask<String, Void, String> {

    @Override
    protected String doInBackground(String... params) {
        Cloudinary cloudinary = new Cloudinary(Utils.cloudinaryUrlFromContext(ImageActivity.context));
        try {
            cloudinary.uploader().upload(new File(params[0]), ObjectUtils.emptyMap());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "Success";
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        ParseObject image = new ParseObject("Post");
        image.put("image",s);
        image.saveInBackground();

    }
}
