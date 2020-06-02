package com.example.myapplication;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {
    @Expose
    @SerializedName("id")
    private String id;
    @Expose
    @SerializedName("updatedAt")
    private String updatedAt;
    @Expose
    @SerializedName("createdAt")
    private String createdAt;
    @Expose
    @SerializedName("type")
    private String type;
    @Expose
    @SerializedName("description")
    private String description;
    @Expose
    @SerializedName("title")
    private String title;

    public Data(String type, String description, String title) {
        this.type = type;
        this.description = description;
        this.title = title;
    }

    public Data(String title, String description,String type,String id) {
        this.type = type;
        this.id = id;
        this.title = title;
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public String getDescription() {
        return description;
    }

    public String getTitle() {
        return title;
    }

    public String getId() {
        return id;
    }
}
