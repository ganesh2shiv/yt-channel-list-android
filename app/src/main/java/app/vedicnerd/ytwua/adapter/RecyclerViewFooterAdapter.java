package app.vedicnerd.ytwua.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import app.vedicnerd.ytwua.R;
import app.vedicnerd.ytwua.listener.OnLoadMoreListener;
import app.vedicnerd.ytwua.pojo.PlaylistItem;

public class RecyclerViewFooterAdapter extends AbstractRecyclerViewFooterAdapter<PlaylistItem> {

    private final Context mContext;

    public RecyclerViewFooterAdapter(RecyclerView recyclerView, List<PlaylistItem> dataset, OnLoadMoreListener onLoadMoreListener, Context mContext) {
        super(recyclerView, dataset, onLoadMoreListener);
        this.mContext = mContext;
    }

    public void resetItems(@NonNull List<PlaylistItem> newDataSet) {
        resetItems(newDataSet);
    }

    @Override
    public RecyclerView.ViewHolder onCreateBasicItemViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_playlist_video, parent, false);
        return new PlaylistViewHolder(v);
    }

    @Override
    public void onBindBasicItemView(RecyclerView.ViewHolder genericHolder, int position) {
        final PlaylistViewHolder holder = (PlaylistViewHolder) genericHolder;

        PlaylistItem playlistItem = getItem(position);
        holder.video_title.setText(playlistItem.getItemSnippet().getTitle());
        holder.video_desc.setText(playlistItem.getItemSnippet().getDescription());

        if (playlistItem.getItemSnippet().getSnippetThumbnail() != null) {
            if (TextUtils.isDigitsOnly(playlistItem.getItemSnippet().getSnippetThumbnail().getDefault().getUrl())) {
                Picasso.with(mContext).load(Integer.valueOf(playlistItem.getItemSnippet().getSnippetThumbnail().getDefault().getUrl())).error(R.drawable.loading).placeholder(R.drawable.loading).into(holder.video_thumbnail);
            } else {
                Picasso.with(mContext).load(playlistItem.getItemSnippet().getSnippetThumbnail().getDefault().getUrl().replaceAll(" ", "%20")).error(R.drawable.loading).placeholder(R.drawable.loading).into(holder.video_thumbnail);
            }
        }
    }

    private static OnItemClickListener listener;

    // Define the listener interface
    public interface OnItemClickListener {
        void onItemClick(PlaylistItem playlistItem);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    class PlaylistViewHolder extends RecyclerView.ViewHolder {

        public final TextView video_title;
        public final TextView video_desc;
        public final ImageView video_thumbnail;

        public PlaylistViewHolder(View itemView) {
            super(itemView);
            video_title = (TextView) itemView.findViewById(R.id.video_title);
            video_desc = (TextView) itemView.findViewById(R.id.video_desc);
            video_thumbnail = (ImageView) itemView.findViewById(R.id.video_thumbnail);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (listener != null) {
                        listener.onItemClick(getItem(getLayoutPosition()));
                    }
                }
            });

        }
    }

}