package com.uniksame.uniksamelearningpoint.unikhelpermodels;

public class WorkModelHelper {

    private String todayProgress;
    private String weaklyProgress;
    private String approach;
    private String earning;
    private String refers;
    private String levelPromotion;

    public WorkModelHelper() {
    }

    public WorkModelHelper(String todayProgress, String weaklyProgress,
                           String approach, String earning,
                           String refers, String levelPromotion) {

        this.todayProgress = todayProgress;
        this.weaklyProgress = weaklyProgress;
        this.approach = approach;
        this.earning = earning;
        this.refers = refers;
        this.levelPromotion = levelPromotion;
    }

    public String getTodayProgress() {
        return todayProgress;
    }

    public void setTodayProgress(String todayProgress) {
        this.todayProgress = todayProgress;
    }

    public String getWeaklyProgress() {
        return weaklyProgress;
    }

    public void setWeaklyProgress(String weaklyProgress) {
        this.weaklyProgress = weaklyProgress;
    }

    public String getApproach() {
        return approach;
    }

    public void setApproach(String approach) {
        this.approach = approach;
    }

    public String getEarning() {
        return earning;
    }

    public void setEarning(String earning) {
        this.earning = earning;
    }

    public String getRefers() {
        return refers;
    }

    public void setRefers(String refers) {
        this.refers = refers;
    }

    public String getLevelPromotion() {
        return levelPromotion;
    }

    public void setLevelPromotion(String levelPromotion) {
        this.levelPromotion = levelPromotion;
    }
}
