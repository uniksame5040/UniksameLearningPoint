package com.uniksame.uniksamelearningpoint.unikhelpermodels;

public class ModelSoluttion {
    private String name, solutionText, date, username, imageUrl;
    private int like, dislike;

    public ModelSoluttion() {
    }

    public ModelSoluttion(String name, String solutionText, String date, int like, int dislike,
                          String username, String imageUrl) {
        this.name = name;
        this.solutionText = solutionText;
        this.date = date;
        this.like = like;
        this.dislike = dislike;
        this.username = username;
        this.imageUrl = imageUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSolutionText() {
        return solutionText;
    }

    public void setSolutionText(String solutionText) {
        this.solutionText = solutionText;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getLike() {
        return like;
    }

    public void setLike(int like) {
        this.like = like;
    }

    public int getDislike() {
        return dislike;
    }

    public void setDislike(int dislike) {
        this.dislike = dislike;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
