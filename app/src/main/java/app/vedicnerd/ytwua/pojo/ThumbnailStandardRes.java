package app.vedicnerd.ytwua.pojo;

import android.os.Parcel;
import android.os.Parcelable;

public class ThumbnailStandardRes implements Parcelable {

    private String url;
    private Integer width;
    private Integer height;

    /**
     * @return The url
     */
    public String getUrl() {
        return url;
    }

    /**
     * @param url The url
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * @return The width
     */
    public Integer getWidth() {
        return width;
    }

    /**
     * @param width The width
     */
    public void setWidth(Integer width) {
        this.width = width;
    }

    /**
     * @return The height
     */
    public Integer getHeight() {
        return height;
    }

    /**
     * @param height The height
     */
    public void setHeight(Integer height) {
        this.height = height;
    }

    ThumbnailStandardRes(Parcel in) {
        url = in.readString();
        width = in.readByte() == 0x00 ? null : in.readInt();
        height = in.readByte() == 0x00 ? null : in.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(url);
        if (width == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeInt(width);
        }
        if (height == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeInt(height);
        }
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<ThumbnailStandardRes> CREATOR = new Parcelable.Creator<ThumbnailStandardRes>() {
        @Override
        public ThumbnailStandardRes createFromParcel(Parcel in) {
            return new ThumbnailStandardRes(in);
        }

        @Override
        public ThumbnailStandardRes[] newArray(int size) {
            return new ThumbnailStandardRes[size];
        }
    };
}