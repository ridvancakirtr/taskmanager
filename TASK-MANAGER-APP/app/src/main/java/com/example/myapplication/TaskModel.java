package com.example.myapplication;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TaskModel {

    @Expose
    @SerializedName("data")
    public List<Data> data;
    @Expose
    @SerializedName("status")
    public String status;
}
