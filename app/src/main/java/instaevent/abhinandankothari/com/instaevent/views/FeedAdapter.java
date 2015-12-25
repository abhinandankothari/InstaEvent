package instaevent.abhinandankothari.com.instaevent.views;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.bumptech.glide.Glide;
import com.parse.ParseQuery;
import com.parse.ParseQueryAdapter;
import com.parse.ParseRelation;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

import bolts.Continuation;
import bolts.Task;
import instaevent.abhinandankothari.com.instaevent.R;
import instaevent.abhinandankothari.com.instaevent.models.Like;
import instaevent.abhinandankothari.com.instaevent.models.Post;
import instaevent.abhinandankothari.com.instaevent.models.User;

import static android.text.format.DateUtils.MINUTE_IN_MILLIS;

/**
 * Created by vjdhama on 21/12/15.
 */
// TODO: Amir - 23/12/15 - format code, remove extra empty lines
// TODO: Amir - 23/12/15 - optimize and remove warnings
public class FeedAdapter extends ParseQueryRecyclerViewAdapter<FeedAdapter.ViewHolder, Post> {

    private Context context;

    public FeedAdapter(Context context) {
        super(new ParseQueryAdapter.QueryFactory<Post>() {
            @Override
            public ParseQuery<Post> create() {
                return ParseQuery
                        .getQuery(Post.class)
                        .include(Post.USER)
                        .setCachePolicy(ParseQuery.CachePolicy.CACHE_ELSE_NETWORK);
            }
        });
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.feed_content, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bindView(this.context, getItem(position));
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView userName;
        private final ImageView profileImage;
        private final TextView postTime;
        private final TextView likeCounts;
        private final TextView commentCounts;
        private final ToggleButton btnLike;
        public TextView description;
        public ImageView mImageView;

        public ViewHolder(View itemView) {
            super(itemView);
            description = (TextView) itemView.findViewById(R.id.info_text);
            mImageView = (ImageView) itemView.findViewById(R.id.post_image_preview);
            userName = ((TextView) itemView.findViewById(R.id.txt_name));
            profileImage = ((ImageView) itemView.findViewById(R.id.img_profile));
            postTime = ((TextView) itemView.findViewById(R.id.txt_post_time));
            likeCounts = ((TextView) itemView.findViewById(R.id.txt_likes));
            commentCounts = ((TextView) itemView.findViewById(R.id.txt_comments));
            btnLike = ((ToggleButton) itemView.findViewById(R.id.btn_like));
        }

        private void bindView(final Context context, final Post item) {
            description.setText(item.getDescription());
            String imageUri = item.getImage().getUrl();
            Glide.with(context)
                    .load(imageUri)
                    .into(mImageView);
            userName.setText(item.getUser().getName());
            postTime.setText(DateUtils.getRelativeTimeSpanString(item.getCreatedAt().getTime(), System.currentTimeMillis(), MINUTE_IN_MILLIS));
            likeCounts.setText(Integer.toString(item.getLikeCounts()));
            commentCounts.setText(Integer.toString(item.getCommentCounts()));
            btnLike.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final ProgressDialog dialog = ProgressDialog.show(context, null, "Saving", true, false);
                    Task.forResult(item)
                            .onSuccess(new Continuation<Post, Post>() {
                                @Override
                                public Post then(Task<Post> task) throws Exception {
                                    if (btnLike.isChecked()) {
                                        item.incrementLikeCount();
                                        ParseRelation<Like> likes = item.getLikes();
                                        Like like = new Like((User) ParseUser.getCurrentUser());
                                        like.save();
                                        likes.add(like);
                                        item.save();
                                    } else {
                                        item.decrementLikeCount();
                                        ParseRelation<Like> likes = item.getLikes();
                                        Like toRemove = likes.getQuery().whereEqualTo(Like.USER, ParseUser.getCurrentUser()).getFirst();
                                        likes.remove(toRemove);
                                        item.save();
                                        toRemove.delete();
                                    }
                                    return null;
                                }
                            }, Task.BACKGROUND_EXECUTOR)
                            .continueWith(new Continuation<Post, Void>() {
                                @Override
                                public Void then(Task<Post> task) throws Exception {
                                    dialog.dismiss();
                                    if (task.isFaulted()) {
                                        Log.e("INSTA_APP", "Error while saving like", task.getError());
                                    }
                                    long likeCounts = item.getLikeCounts();
                                    ViewHolder.this.likeCounts.setText(Long.toString(likeCounts));
                                    return null;
                                }
                            }, Task.UI_THREAD_EXECUTOR);
                }
            });
            likeCounts.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final ProgressDialog dialog = ProgressDialog.show(context, null, "Loading", true, false);
                    Task.forResult(item)
                            .onSuccess(new Continuation<Post, List<Like>>() {
                                @Override
                                public List<Like> then(Task<Post> task) throws Exception {
                                    Post post = task.getResult();
                                    ParseRelation<Like> likes = post.getLikes();
                                    return likes.getQuery().setLimit(25).include(Like.USER).find();
                                }
                            }, Task.BACKGROUND_EXECUTOR)
                            .continueWith(new Continuation<List<Like>, Void>() {
                                @Override
                                public Void then(Task<List<Like>> task) throws Exception {
                                    dialog.dismiss();
                                    if (task.isFaulted()) {
                                        Log.e("INSTA_LOG", "Error while fetching likes", task.getError());
                                        return null;
                                    }

                                    List<Like> likes = task.getResult();
                                    ArrayList<String> users = new ArrayList<>();
                                    for (Like like : likes) {
                                        users.add(like.getUser().getName());
                                    }
                                    new AlertDialog.Builder(context)
                                            .setTitle("Likes")
                                            .setAdapter(new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, users), null)
                                            .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    dialog.dismiss();
                                                }
                                            })
                                            .show();
                                    return null;
                                }
                            }, Task.UI_THREAD_EXECUTOR);
                }
            });
        }

    }
}
