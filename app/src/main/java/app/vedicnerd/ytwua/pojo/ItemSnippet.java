package app.vedicnerd.ytwua.pojo;

import android.os.Parcel;
import android.os.Parcelable;

public class ItemSnippet implements Parcelable {

    private String publishedAt;
    private String channelId;
    private String title;
    private String description;
    private SnippetThumbnail thumbnails;
    private String channelTitle;
    private String playlistId;
    private Integer position;
    private SnippetResourceId resourceId;

    /**
     * @return The publishedAt
     */
    public String getPublishedAt() {
        return publishedAt;
    }

    /**
     * @param publishedAt The publishedAt
     */
    public void setPublishedAt(String publishedAt) {
        this.publishedAt = publishedAt;
    }

    /**
     * @return The channelId
     */
    public String getChannelId() {
        return channelId;
    }

    /**
     * @param channelId The channelId
     */
    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    /**
     * @return The title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title The title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return The description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description The description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return The snippetThumbnail
     */
    public SnippetThumbnail getSnippetThumbnail() {
        return thumbnails;
    }

    /**
     * @param thumbnails The snippetThumbnail
     */
    public void setSnippetThumbnail(SnippetThumbnail thumbnails) {
        this.thumbnails = thumbnails;
    }

    /**
     * @return The channelTitle
     */
    public String getChannelTitle() {
        return channelTitle;
    }

    /**
     * @param channelTitle The channelTitle
     */
    public void setChannelTitle(String channelTitle) {
        this.channelTitle = channelTitle;
    }

    /**
     * @return The playlistId
     */
    public String getPlaylistId() {
        return playlistId;
    }

    /**
     * @param playlistId The playlistId
     */
    public void setPlaylistId(String playlistId) {
        this.playlistId = playlistId;
    }

    /**
     * @return The position
     */
    public Integer getPosition() {
        return position;
    }

    /**
     * @param position The position
     */
    public void setPosition(Integer position) {
        this.position = position;
    }

    /**
     * @return The resourceId
     */
    public SnippetResourceId getSnippetResourceId() {
        return resourceId;
    }

    /**
     * @param resourceId The resourceId
     */
    public void setSnippetResourceId(SnippetResourceId resourceId) {
        this.resourceId = resourceId;
    }

    ItemSnippet(Parcel in) {
        publishedAt = in.readString();
        channelId = in.readString();
        title = in.readString();
        description = in.readString();
        thumbnails = (SnippetThumbnail) in.readValue(SnippetThumbnail.class.getClassLoader());
        channelTitle = in.readString();
        playlistId = in.readString();
        position = in.readByte() == 0x00 ? null : in.readInt();
        resourceId = (SnippetResourceId) in.readValue(SnippetResourceId.class.getClassLoader());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(publishedAt);
        dest.writeString(channelId);
        dest.writeString(title);
        dest.writeString(description);
        dest.writeValue(thumbnails);
        dest.writeString(channelTitle);
        dest.writeString(playlistId);
        if (position == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeInt(position);
        }
        dest.writeValue(resourceId);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<ItemSnippet> CREATOR = new Parcelable.Creator<ItemSnippet>() {
        @Override
        public ItemSnippet createFromParcel(Parcel in) {
            return new ItemSnippet(in);
        }

        @Override
        public ItemSnippet[] newArray(int size) {
            return new ItemSnippet[size];
        }
    };

}