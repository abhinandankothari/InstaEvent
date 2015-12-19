package instaevent.abhinandankothari.com.instaevent;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.cloudinary.Cloudinary;
import com.cloudinary.android.Utils;
import com.cloudinary.utils.ObjectUtils;
import com.parse.ParseObject;

import java.io.File;


public class ImageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);
        final String imageUrl = getIntent().getStringExtra(MainActivity.URL);
        Bitmap bitmap = BitmapFactory.decodeFile(imageUrl);
        Bitmap finalBitmap = ImageHelper.resizeBitmap(bitmap, 800, 800, false);
        ImageView preview = (ImageView) findViewById(R.id.post_image_preview);
        Button button = (Button) findViewById(R.id.upload_button);
        preview.setImageBitmap(finalBitmap);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ProgressDialog progressDialog = ProgressDialog.show(ImageActivity.this, null, "Uploading", true, false);

                new AsyncTask<String, Void, String>() {

                    @Override
                    protected String doInBackground(String... params) {
                        Cloudinary cloudinary = new Cloudinary(Utils.cloudinaryUrlFromContext(ImageActivity.this));
                        try {
                            String url = params[0];
                            cloudinary.uploader().upload(new File(url), ObjectUtils.emptyMap());
                            ParseObject image = new ParseObject("Post");
                            //  image.put("image", new ParseFile(new File(url)));
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
                        progressDialog.dismiss();
                        finish();
                    }
                }.execute(imageUrl);
            }
        });
    }
}

