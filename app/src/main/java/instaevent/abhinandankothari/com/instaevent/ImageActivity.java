package instaevent.abhinandankothari.com.instaevent;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;

import com.cloudinary.Cloudinary;
public class ImageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);
        String imageUrl = getIntent().getStringExtra(MainActivity.URL);
        Bitmap bitmap = BitmapFactory.decodeFile(imageUrl);
        Bitmap finalBitmap = ImageHelper.resizeBitmap(bitmap, 800, 800, false);
        ImageView preview = (ImageView) findViewById(R.id.post_image_preview);
        preview.setImageBitmap(finalBitmap);
        Cloudinary cloudinary = new Cloudinary("cloudinary://529254238611378:qJXmUFVC9tpM8Mdv5Lu_a9DB0MA@nandx64");
    }

}
