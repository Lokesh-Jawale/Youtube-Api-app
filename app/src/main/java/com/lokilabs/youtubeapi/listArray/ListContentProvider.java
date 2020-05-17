package com.lokilabs.youtubeapi.listArray;

public class ListContentProvider {

    private String titleText = "";
    private String desText = "";
    private String dateText = "";
    private String thumbnail = "";
    private String videoId = "";

    public String getTitleText() {
        return titleText;
    }

    public void setTitleText(String titleText) {
        this.titleText = titleText;
    }

    public void setDesText(String desText) {
        this.desText = desText;
    }

    public void setDateText(String dateText) {
        this.dateText = dateText;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getDesText() {
        return desText;
    }

    public String getDateText() {
        return dateText;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public String getDescription() {
        return titleText+" "+desText+" "+dateText+" "+thumbnail;
    }

    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }
}
