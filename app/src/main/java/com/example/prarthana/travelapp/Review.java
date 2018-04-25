package com.example.prarthana.travelapp;

class Review {
    String userIcon;
    String name;
    String rating;
    String timestamp;
    String reviewBody;

    Review(String userIcon, String name, String rating, String timestamp, String reviewBody) {
        this.userIcon = userIcon;
        this.name = name;
        this.rating = rating;
        this.timestamp = timestamp;
        this.reviewBody = reviewBody;
    }

    public String getName() {
        return this.name;
    }

    public String getUserIcon() {
        return this.userIcon;
    }

    public String getRating() {
        return this.rating;
    }

    public String getTimestamp() {
        return this.timestamp;
    }

    public String getReviewBody() {
        return this.reviewBody;
    }

}

