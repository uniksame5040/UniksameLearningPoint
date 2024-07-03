package com.uniksame.uniksamelearningpoint.unikhelpermodels;

public class
ModelMotivation {

    private String mTitle;
    private String videoDuration;
    private String mLink;

    public ModelMotivation() {
    }

    public ModelMotivation(String title, String videoDuration, String link) {
        this.mTitle = title;
        this.videoDuration = videoDuration;
        this.mLink = link;
    }

    public String getmTitle() {
        return mTitle;
    }

    public void setmTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public String getVideoDuration() {
        return videoDuration;
    }

    public void setVideoDuration(String videoDuration) {
        this.videoDuration = videoDuration;
    }

    public String getmLink() {
        return mLink;
    }

    public void setmLink(String mLink) {
        this.mLink = mLink;
    }
}
