package instaevent.abhinandankothari.com.instaevent;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;


public class ImageActivity extends AppCompatActivity {
    public static Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ImageActivity.context = getApplicationContext();
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
                String result = "" + new ImageUpload().execute(imageUrl);
                if (!result.isEmpty()) {
                    finish();
                }
            }
        });
    }
}

