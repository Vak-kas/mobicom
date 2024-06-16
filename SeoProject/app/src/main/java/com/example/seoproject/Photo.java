package com.example.seoproject;

import java.util.ArrayList;
import java.util.List;

public class Photo {
    private String imageUri;
    private String tag1;
    private String tag2;
    private String tag3;

    public Photo(String imageUri, String tag1, String tag2, String tag3) {
        this.imageUri = imageUri;
        this.tag1 = tag1;
        this.tag2 = tag2;
        this.tag3 = tag3;
    }

    public String getImageUri() {
        return imageUri;
    }

    public String getTag1() {
        return tag1;
    }

    public String getTag2() {
        return tag2;
    }

    public String getTag3() {
        return tag3;
    }

    public void setTag1(String tag1) {
        this.tag1 = tag1;
    }

    public void setTag2(String tag2) {
        this.tag2 = tag2;
    }

    public void setTag3(String tag3) {
        this.tag3 = tag3;
    }

    public List<String> getTags() {
        List<String> tags = new ArrayList<>();
        if (tag1 != null && !tag1.isEmpty()) tags.add(tag1);
        if (tag2 != null && !tag2.isEmpty()) tags.add(tag2);
        if (tag3 != null && !tag3.isEmpty()) tags.add(tag3);
        return tags;
    }

    public String getFormattedTags() {
        StringBuilder formattedTags = new StringBuilder();
        for (String tag : getTags()) {
            formattedTags.append("#").append(tag).append(" ");
        }
        return formattedTags.toString().trim();
    }
}
