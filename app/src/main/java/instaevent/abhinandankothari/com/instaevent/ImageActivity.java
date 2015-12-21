package instaevent.abhinandankothari.com.instaevent;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.parse.ParseFile;

import java.io.File;

import instaevent.abhinandankothari.com.instaevent.models.Post;


public class ImageActivity extends AppCompatActivity {

    private EditText description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);

        final String imageUrl = getIntent().getStringExtra(MainActivity.URL);

        description = (EditText) findViewById(R.id.editDescription);

        ImageView preview = (ImageView) findViewById(R.id.post_image_preview);

        Glide.with(this)
                .load(imageUrl)
                .into(preview);

        Button button = (Button) findViewById(R.id.upload_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ProgressDialog progressDialog = ProgressDialog.show(ImageActivity.this, null, "Uploading", true, false);
                String desc = description.getText().toString();
                new AsyncTask<String, Void, String>() {

                    @Override
                    protected String doInBackground(String... params) {
                        try {
                            String url = params[0];
                            Post post = new Post();
                            post.setImage(new ParseFile(new File(url)));
                            post.setDescription(params[1]);
                            post.save();
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
                }.execute(imageUrl,desc);
            }
        });
    }
}

