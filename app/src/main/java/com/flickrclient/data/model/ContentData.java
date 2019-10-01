package com.flickrclient.data.model;

import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;


@Parcel
public class ContentData {
    @SerializedName("_content")
    private String content;

    public ContentData() {
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
