package app.vedicnerd.ytwua.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import java.util.List;

import app.vedicnerd.ytwua.R;
import app.vedicnerd.ytwua.listener.OnLoadMoreListener;

public abstract class AbstractRecyclerViewFooterAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private int visibleThreshold = 5;

    private int lastVisibleItem;

    private final int ITEM_VIEW_TYPE_BASIC = 0;
    private final int ITEM_VIEW_TYPE_FOOTER = 1;

    private List<T> dataSet;

    private int firstVisibleItem, totalItemCount;
    private boolean loading;

    AbstractRecyclerViewFooterAdapter(RecyclerView recyclerView, List<T> dataSet, final OnLoadMoreListener onLoadMoreListener) {
        this.dataSet = dataSet;

        if (recyclerView.getLayoutManager() instanceof LinearLayoutManager) {
            final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
            recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {

                    totalItemCount = linearLayoutManager.getItemCount();
                    lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();
                    if (!loading && totalItemCount <= (lastVisibleItem + visibleThreshold)) {
                        // End has been reached
                        // Do something
                        if (onLoadMoreListener != null) {
                            onLoadMoreListener.onLoadMore();
                        }
                        loading = true;
                    }
                }
            });
        }
    }

    public void setLoaded() {
        loading = false;
    }

    public int getFirstVisibleItem() {
        return firstVisibleItem;
    }

    public void resetItems(@NonNull List<T> newDataSet) {
        loading = true;
        firstVisibleItem = 0;
        totalItemCount = 0;

        dataSet.clear();
        addItems(newDataSet);
    }

    public void addItems(@NonNull List<T> newDataSetItems) {
        dataSet.addAll(newDataSetItems);
        notifyDataSetChanged();
    }

    public void addItem(T item) {
        if (!dataSet.contains(item)) {
            dataSet.add(item);
            notifyItemInserted(dataSet.size() - 1);
        }
    }

    public void removeItem(T item) {
        int indexOfItem = dataSet.indexOf(item);
        if (indexOfItem != -1) {
            this.dataSet.remove(indexOfItem);
            notifyItemRemoved(indexOfItem);
        }
    }

    T getItem(int index) {
        if (dataSet != null && dataSet.get(index) != null) {
            return dataSet.get(index);
        } else {
            throw new IllegalArgumentException("Item with index " + index + " doesn't exist, dataSet is " + dataSet);
        }
    }

    public List<T> getDataSet() {
        return dataSet;
    }

    @Override
    public int getItemViewType(int position) {
        return dataSet.get(position) != null ? ITEM_VIEW_TYPE_BASIC : ITEM_VIEW_TYPE_FOOTER;
    }

    @Override
    public int getItemCount() {
        return dataSet != null ? dataSet.size() : 0;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == ITEM_VIEW_TYPE_BASIC) {
            return onCreateBasicItemViewHolder(parent, viewType);
        } else if (viewType == ITEM_VIEW_TYPE_FOOTER) {
            return onCreateFooterViewHolder(parent, viewType);
        } else {
            throw new IllegalStateException("Invalid type, this type ot items " + viewType + " can't be handled");
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder genericHolder, int position) {
        if (getItemViewType(position) == ITEM_VIEW_TYPE_BASIC) {
            onBindBasicItemView(genericHolder, position);
        } else {
            onBindFooterView(genericHolder, position);
        }
    }

    protected abstract RecyclerView.ViewHolder onCreateBasicItemViewHolder(ViewGroup parent, int viewType);

    protected abstract void onBindBasicItemView(RecyclerView.ViewHolder genericHolder, int position);

    private RecyclerView.ViewHolder onCreateFooterViewHolder(ViewGroup parent, int viewType) {
        //noinspection ConstantConditions
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.progress_bar, parent, false);
        return new ProgressViewHolder(v);
    }

    private void onBindFooterView(RecyclerView.ViewHolder genericHolder, int position) {
        ((ProgressViewHolder) genericHolder).progressBar.setIndeterminate(true);
    }

    public static class ProgressViewHolder extends RecyclerView.ViewHolder {

        public ProgressBar progressBar;

        public ProgressViewHolder(final View itemView) {
            super(itemView);
            progressBar = (ProgressBar) itemView.findViewById(R.id.progressBar);
        }
    }
}
