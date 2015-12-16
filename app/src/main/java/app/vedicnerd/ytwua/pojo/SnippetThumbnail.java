package app.vedicnerd.ytwua.pojo;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class SnippetThumbnail implements Parcelable {

    @SerializedName("default")
    private ThumbDefaultRes _default;
    private ThumbnailMediumRes medium;
    private ThumbnailHighRes high;
    private ThumbnailStandardRes standard;
    private ThumbMaxRes maxres;

    SnippetThumbnail(Parcel in) {
        _default = (ThumbDefaultRes) in.readValue(ThumbDefaultRes.class.getClassLoader());
        medium = (ThumbnailMediumRes) in.readValue(ThumbnailMediumRes.class.getClassLoader());
        high = (ThumbnailHighRes) in.readValue(ThumbnailHighRes.class.getClassLoader());
        standard = (ThumbnailStandardRes) in.readValue(ThumbnailStandardRes.class.getClassLoader());
        maxres = (ThumbMaxRes) in.readValue(ThumbMaxRes.class.getClassLoader());
    }

    /**
     * @return The _Thumb_defaultRes
     */
    public ThumbDefaultRes getDefault() {
        return _default;
    }

    /**
     * @param _default The default
     */
    public void setDefault(ThumbDefaultRes _default) {
        this._default = _default;
    }

    /**
     * @return The medium
     */
    public ThumbnailMediumRes getThumbnailMediumRes() {
        return medium;
    }

    /**
     * @param medium The medium
     */
    public void setThumbnailMediumRes(ThumbnailMediumRes medium) {
        this.medium = medium;
    }

    /**
     * @return The high
     */
    public ThumbnailHighRes getThumbnailHighRes() {
        return high;
    }

    /**
     * @param high The high
     */
    public void setThumbnailHighRes(ThumbnailHighRes high) {
        this.high = high;
    }

    /**
     * @return The standard
     */
    public ThumbnailStandardRes getThumbnailStandardRes() {
        return standard;
    }

    /**
     * @param standard The standard
     */
    public void setThumbnailStandardRes(ThumbnailStandardRes standard) {
        this.standard = standard;
    }

    /**
     * @return The maxres
     */
    public ThumbMaxRes getMaxres() {
        return maxres;
    }

    /**
     * @param maxres The maxres
     */
    public void setMaxres(ThumbMaxRes maxres) {
        this.maxres = maxres;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(_default);
        dest.writeValue(medium);
        dest.writeValue(high);
        dest.writeValue(standard);
        dest.writeValue(maxres);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<SnippetThumbnail> CREATOR = new Parcelable.Creator<SnippetThumbnail>() {
        @Override
        public SnippetThumbnail createFromParcel(Parcel in) {
            return new SnippetThumbnail(in);
        }

        @Override
        public SnippetThumbnail[] newArray(int size) {
            return new SnippetThumbnail[size];
        }
    };
}