package com.example.courierserviceapp;

// CardData.java
public class CardData {
    private int iconResId;
    private String title;
    private String description;

    public CardData(int iconResId, String title, String description) {
        this.iconResId = iconResId;
        this.title = title;
        this.description = description;
    }

    public int getIconResId() {
        return iconResId;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }
}

