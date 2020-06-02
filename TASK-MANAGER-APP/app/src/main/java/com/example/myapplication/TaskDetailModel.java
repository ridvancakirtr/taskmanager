package com.example.myapplication;

import com.google.gson.annotations.SerializedName;

public class TaskDetailModel {

    @SerializedName("data")
    public Data data;
    @SerializedName("status")
    public String status;

    public static class Data {
        @SerializedName("id")
        public String id;
        @SerializedName("updatedAt")
        public String updatedAt;
        @SerializedName("createdAt")
        public String createdAt;
        @SerializedName("type")
        public String type;
        @SerializedName("description")
        public String description;
        @SerializedName("title")
        public String title;

        public String getId() {
            return id;
        }

        public String getUpdatedAt() {
            return updatedAt;
        }

        public String getCreatedAt() {
            return createdAt;
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

        public Data(String type, String description, String title) {
            this.type = type;
            this.description = description;
            this.title = title;
        }
    }
}
