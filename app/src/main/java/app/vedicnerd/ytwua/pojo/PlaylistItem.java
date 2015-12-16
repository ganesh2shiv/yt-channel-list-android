package app.vedicnerd.ytwua.pojo;

import android.os.Parcel;
import android.os.Parcelable;

public class PlaylistItem implements Parcelable {

    private String kind;
    private String etag;
    private String id;
    private ItemSnippet snippet;

    /**
     * @return The kind
     */
    public String getKind() {
        return kind;
    }

    /**
     * @param kind The kind
     */
    public void setKind(String kind) {
        this.kind = kind;
    }

    /**
     * @return The etag
     */
    public String getEtag() {
        return etag;
    }

    /**
     * @param etag The etag
     */
    public void setEtag(String etag) {
        this.etag = etag;
    }

    /**
     * @return The id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id The id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return The itemSnippet
     */
    public ItemSnippet getItemSnippet() {
        return snippet;
    }

    /**
     * @param snippet The snippet
     */
    public void setItemSnippet(ItemSnippet snippet) {
        this.snippet = snippet;
    }

    private PlaylistItem(Parcel in) {
        kind = in.readString();
        etag = in.readString();
        id = in.readString();
        snippet = (ItemSnippet) in.readValue(ItemSnippet.class.getClassLoader());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(kind);
        dest.writeString(etag);
        dest.writeString(id);
        dest.writeValue(snippet);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<PlaylistItem> CREATOR = new Parcelable.Creator<PlaylistItem>() {
        @Override
        public PlaylistItem createFromParcel(Parcel in) {
            return new PlaylistItem(in);
        }

        @Override
        public PlaylistItem[] newArray(int size) {
            return new PlaylistItem[size];
        }
    };
}