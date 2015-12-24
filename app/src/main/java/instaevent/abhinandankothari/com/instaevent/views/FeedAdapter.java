package instaevent.abhinandankothari.com.instaevent.views;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.parse.ParseQuery;
import com.parse.ParseQueryAdapter;

import instaevent.abhinandankothari.com.instaevent.models.Post;
import instaevent.abhinandankothari.com.instaevent.R;

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
                return ParseQuery.getQuery(Post.class);
            }
        });

        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.feed_content, parent, false);

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.mTextView.setText(getItem(position).getDescription());

        String imageUri = getItem(position).getImage().getUrl();

        Glide.with(context)
                .load(imageUri)
                .into(holder.mImageView);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView mTextView;
        public ImageView mImageView;

        public ViewHolder(View itemView) {
            super(itemView);
            mTextView = (TextView) itemView.findViewById(R.id.info_text);
            mImageView = (ImageView) itemView.findViewById(R.id.post_image_preview);
        }
    }
}
