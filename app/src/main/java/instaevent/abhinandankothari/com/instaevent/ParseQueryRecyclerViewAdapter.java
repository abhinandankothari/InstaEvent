package instaevent.abhinandankothari.com.instaevent;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.RecyclerView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseQueryAdapter;

import java.util.ArrayList;
import java.util.List;

import bolts.Capture;

public abstract class ParseQueryRecyclerViewAdapter<VH extends RecyclerView.ViewHolder, T extends ParseObject> extends RecyclerView.Adapter<VH> {
    private final ParseQueryAdapter.QueryFactory<T> queryFactory;

    private Context context;
    protected List<T> objects = new ArrayList<>();
    private List<List<T>> objectPages = new ArrayList<>();
    private int currentPage = 0;
    private int objectsPerPage = 25;
    private boolean paginationEnabled = true;
    private boolean hasNextPage = true;
    private List<ParseQueryAdapter.OnQueryLoadListener<T>> onQueryLoadListeners = new ArrayList<>();
    private boolean isLocalDataStoreEnabled = false;

    public ParseQueryRecyclerViewAdapter(ParseQueryAdapter.QueryFactory<T> queryFactory) {
        this.queryFactory = queryFactory;
    }

    public void clear() {
        this.objectPages.clear();
        syncObjectsWithPages();
        this.notifyDataSetChanged();
        this.currentPage = 0;
    }

    public void loadObjects() {
        this.loadObjects(0, true);
    }

    private void loadObjects(final int page, final boolean shouldClear) {
        final ParseQuery<T> query = this.queryFactory.create();

        if (this.objectsPerPage > 0 && this.paginationEnabled) {
            this.setPageOnQuery(page, query);
        }

        this.notifyOnLoadingListeners();

        // Create a new page
        if (page >= objectPages.size()) {
            objectPages.add(page, new ArrayList<T>());
        }

        // In the case of CACHE_THEN_NETWORK, two callbacks will be called. Using this flag to keep track,
        final Capture<Boolean> firstCallBack = new Capture<>(true);

        query.findInBackground(new FindCallback<T>() {
            @SuppressLint("ShowToast")
            @Override
            public void done(List<T> foundObjects, ParseException e) {
                if ((!isLocalDataStoreEnabled &&
                        query.getCachePolicy() == ParseQuery.CachePolicy.CACHE_ONLY)
                        && (e != null) && e.getCode() == ParseException.CACHE_MISS) {
                    // no-op on cache miss
                    return;
                }

                if ((e != null)
                        && ((e.getCode() == ParseException.CONNECTION_FAILED) || (e.getCode() != ParseException.CACHE_MISS))) {
                    hasNextPage = true;
                } else if (foundObjects != null) {
                    if (shouldClear && firstCallBack.get()) {
                        objectPages.clear();
                        objectPages.add(new ArrayList<T>());
                        currentPage = page;
                        firstCallBack.set(false);
                    }

                    // Only advance the page, this prevents second call back from CACHE_THEN_NETWORK to
                    // reset the page.
                    if (page >= currentPage) {
                        currentPage = page;

                        // since we set limit == objectsPerPage + 1
                        hasNextPage = (foundObjects.size() > objectsPerPage);
                    }

                    if (paginationEnabled && foundObjects.size() > objectsPerPage) {
                        // Remove the last object, fetched in order to tell us whether there was a "next page"
                        foundObjects.remove(objectsPerPage);
                    }

                    List<T> currentPage = objectPages.get(page);
                    currentPage.clear();
                    currentPage.addAll(foundObjects);

                    syncObjectsWithPages();

                    // executes on the UI thread
                    notifyDataSetChanged();
                }

                notifyOnLoadedListeners(foundObjects, e);
            }
        });
    }

    public T getItem(int index) {
        if (index == this.getPaginationCellRow()) {
            return null;
        }
        return this.objects.get(index);
    }

    @Override
    public int getItemCount() {
        int count = this.objects.size();

        if (this.shouldShowPaginationCell()) {
            count++;
        }

        return count;
    }

    public int getItemPosition(T item) {
        for (int i = 0; i < objects.size(); i++) {
            T object = objects.get(i);
            if (object.getObjectId().equals(item.getObjectId())) return i;
        }
        return -1;
    }

    private int getPaginationCellRow() {
        return this.objects.size();
    }

    private boolean shouldShowPaginationCell() {
        return this.paginationEnabled && this.objects.size() > 0 && this.hasNextPage;
    }

    protected void syncObjectsWithPages() {
        objects.clear();
        for (List<T> pageOfObjects : objectPages) {
            objects.addAll(pageOfObjects);
        }
    }

    protected void setPageOnQuery(int page, ParseQuery<T> query) {
        query.setLimit(this.objectsPerPage + 1);
        query.setSkip(page * this.objectsPerPage);
    }

    public void addOnQueryLoadListener(ParseQueryAdapter.OnQueryLoadListener<T> listener) {
        this.onQueryLoadListeners.add(listener);
    }

    public void removeOnQueryLoadListener(ParseQueryAdapter.OnQueryLoadListener<T> listener) {
        this.onQueryLoadListeners.remove(listener);
    }

    private void notifyOnLoadingListeners() {
        for (ParseQueryAdapter.OnQueryLoadListener<T> listener : this.onQueryLoadListeners) {
            listener.onLoading();
        }
    }

    private void notifyOnLoadedListeners(List<T> objects, Exception e) {
        for (ParseQueryAdapter.OnQueryLoadListener<T> listener : this.onQueryLoadListeners) {
            listener.onLoaded(objects, e);
        }
    }
}
