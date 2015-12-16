package app.vedicnerd.ytwua.pojo;

import java.util.ArrayList;

public class PlaylistResponse {

    private String kind;
    private String etag;
    private String nextPageToken;
    private String prevPageToken;
    private ResponsePageInfo pageInfo;
    private ArrayList<PlaylistItem> items = new ArrayList<>();

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
     * @return The nextPageToken
     */
    public String getNextPageToken() {
        return nextPageToken;
    }

    /**
     * @param nextPageToken The nextPageToken
     */
    public void setNextPageToken(String nextPageToken) {
        this.nextPageToken = nextPageToken;
    }

    /**
     * @return The prevPageToken
     */
    public String getPrevPageToken() {
        return prevPageToken;
    }

    /**
     * @param prevPageToken The prevPageToken
     */
    public void setPrevPageToken(String prevPageToken) {
        this.prevPageToken = prevPageToken;
    }


    /**
     * @return The responsePageInfo
     */
    public ResponsePageInfo getResponsePageInfo() {
        return pageInfo;
    }

    /**
     * @param pageInfo The responsePageInfo
     */
    public void setResponsePageInfo(ResponsePageInfo pageInfo) {
        this.pageInfo = pageInfo;
    }

    /**
     * @return The playlistItems
     */
    public ArrayList<PlaylistItem> getPlaylistItems() {
        return items;
    }

    /**
     * @param items The playlistItems
     */
    public void setPlaylistItems(ArrayList<PlaylistItem> items) {
        this.items = items;
    }
}