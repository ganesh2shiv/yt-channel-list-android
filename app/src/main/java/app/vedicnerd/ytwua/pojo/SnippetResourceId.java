package app.vedicnerd.ytwua.pojo;

import android.os.Parcel;
import android.os.Parcelable;

public class SnippetResourceId implements Parcelable {

    private String kind;
    private String videoId;

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
     * @return The videoId
     */
    public String getVideoId() {
        return videoId;
    }

    /**
     * @param videoId The videoId
     */
    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

    SnippetResourceId(Parcel in) {
        kind = in.readString();
        videoId = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(kind);
        dest.writeString(videoId);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<SnippetResourceId> CREATOR = new Parcelable.Creator<SnippetResourceId>() {
        @Override
        public SnippetResourceId createFromParcel(Parcel in) {
            return new SnippetResourceId(in);
        }

        @Override
        public SnippetResourceId[] newArray(int size) {
            return new SnippetResourceId[size];
        }
    };
}