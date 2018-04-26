package com.example.prarthana.travelapp;

class Review {
    String userIcon;
    String name;
    String rating;
    String timestamp;
    String reviewBody;
    String url;

    Review(String userIcon, String name, String rating, String timestamp, String reviewBody, String url) {
        this.userIcon = userIcon;
        this.name = name;
        this.rating = rating;
        this.timestamp = timestamp;
        this.reviewBody = reviewBody;
        this.url = url;
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

    public String getURL() {
        return this.url;
    }

}

