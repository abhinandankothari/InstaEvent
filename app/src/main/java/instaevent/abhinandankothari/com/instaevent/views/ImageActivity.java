package instaevent.abhinandankothari.com.instaevent.views;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.parse.ParseFile;
import com.parse.ParseUser;

import java.io.File;

import bolts.Continuation;
import bolts.Task;
import instaevent.abhinandankothari.com.instaevent.R;
import instaevent.abhinandankothari.com.instaevent.models.Post;


public class ImageActivity extends AppCompatActivity {

    private EditText description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);

        final String imageUrl = getIntent().getStringExtra(FeedActivity.URL);

        description = (EditText) findViewById(R.id.editDescription);

        ImageView preview = (ImageView) findViewById(R.id.post_image_preview);

        Glide.with(this)
                .load(imageUrl)
                .into(preview);
        // TODO: Amir - 23/12/15 - presenter pattern with unit test
        Button button = (Button) findViewById(R.id.upload_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ProgressDialog progressDialog = ProgressDialog.show(ImageActivity.this, null, "Uploading", true, false);
                String desc = description.getText().toString();
                Task.forResult(new Post(ParseUser.getCurrentUser(), new ParseFile(new File(imageUrl)), desc))
                        .onSuccess(new Continuation<Post, Post>() {
                            @Override
                            public Post then(Task<Post> task) throws Exception {
                                Post post = task.getResult();
                                post.save();
                                return post;
                            }
                        }, Task.BACKGROUND_EXECUTOR)
                        .continueWith(new Continuation<Post, Void>() {
                            @Override
                            public Void then(Task<Post> task) throws Exception {
                                progressDialog.dismiss();
                                if (task.isFaulted()) {
                                    Log.e("INSTA_LOG", "Post save failed", task.getError());
                                    Toast.makeText(ImageActivity.this, "Post save failed", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(ImageActivity.this, "Post saved", Toast.LENGTH_SHORT).show();
                                    finish();
                                }
                                return null;
                            }
                        }, Task.UI_THREAD_EXECUTOR);
            }
        });
    }
}

