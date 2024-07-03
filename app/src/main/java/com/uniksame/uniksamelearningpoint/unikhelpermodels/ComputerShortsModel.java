package com.uniksame.uniksamelearningpoint.unikhelpermodels;

import java.io.Serializable;

public class ComputerShortsModel implements Serializable {

    private String CurrentDesc;
    private String CurrentImgUrl;
    private String CurrentTitle;
    private String PageTipe;
    private String videoLink;

    public ComputerShortsModel() {
    }

    public ComputerShortsModel(String currentDesc, String currentImgUrl,
                               String currentTitle, String pageType) {
        this.CurrentDesc = currentDesc;
        this.CurrentImgUrl = currentImgUrl;
        this.CurrentTitle = currentTitle;
        this.PageTipe = pageType;
//        this.videoLink = videoLink;
    }

    public String getCurrentDesc() {
        return CurrentDesc;
    }

    public void setCurrentDesc(String currentDesc) {
        CurrentDesc = currentDesc;
    }

    public String getCurrentImgUrl() {
        return CurrentImgUrl;
    }

    public void setCurrentImgUrl(String currentImgUrl) {
        CurrentImgUrl = currentImgUrl;
    }

    public String getCurrentTitle() {
        return CurrentTitle;
    }

    public void setCurrentTitle(String currentTitle) {
        CurrentTitle = currentTitle;
    }
    public String getPageTipe() {
        return PageTipe;
    }

    public void setPageTipe(String pageTipe) {
        PageTipe = pageTipe;
    }

//    public String getVideoLink() {
//        return videoLink;
//    }
//
//    public void setVideoLink(String videoLink) {
//        this.videoLink = videoLink;
//    }
}
