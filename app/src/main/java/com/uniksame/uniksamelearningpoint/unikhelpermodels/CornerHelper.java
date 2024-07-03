package com.uniksame.uniksamelearningpoint.unikhelpermodels;

public class CornerHelper {

    private String cornerImageUrls;
    private String cornerText;

    public CornerHelper() {
    }

    public CornerHelper(String cornerImageUrls, String cornerText) {
        this.cornerImageUrls = cornerImageUrls;
        this.cornerText = cornerText;
    }

    public String getCornerImageUrls() {
        return cornerImageUrls;
    }

    public void setCornerImageUrls(String cornerUrls) {
        this.cornerImageUrls = cornerImageUrls;
    }

    public String getCornerText() {
        return cornerText;
    }

    public void setCornerText(String cornerText) {
        this.cornerText = cornerText;
    }
}
