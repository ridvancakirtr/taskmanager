package com.example.myapplication.RetrofitAPI;

import com.example.myapplication.TaskDetailModel;
import com.example.myapplication.TaskModel;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface TaskHolderAPI {
    @Headers({"Content-Type: application/json"})
    @GET("api/tasks")
    Call<TaskModel> getTasks();

    @Headers({"Content-Type: application/json"})
    @GET("api/tasks/{id}")
    Call<TaskDetailModel> getTaskDetail(@Path("id") String id);

    @Headers("Accept: application/json")
    @PUT("api/tasks/{id}")
    Call<TaskDetailModel.Data> putTaskDetail(@Path("id") String id,@Body TaskDetailModel.Data taskDetailModel);

    @Headers("Accept: application/json")
    @POST("api/tasks/")
    Call<TaskDetailModel.Data> postTaskDetail(@Body TaskDetailModel.Data taskDetailModel);

    @Headers("Accept: application/json")
    @DELETE("api/tasks/{id}")
    Call<TaskDetailModel.Data> delTaskDetail(@Path("id") String id);

    @Headers("Accept: application/json")
    @DELETE("api/tasks/")
    Call<TaskModel> delAllTask();
}
