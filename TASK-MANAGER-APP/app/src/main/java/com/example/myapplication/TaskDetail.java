package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.myapplication.RetrofitAPI.ApiClient;
import com.example.myapplication.RetrofitAPI.TaskHolderAPI;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TaskDetail extends AppCompatActivity {
    EditText title,description;
    String TAG = TaskDetail.class.getName();
    RadioGroup radioGrp;
    Button button,buttondel;
    String type;


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_detail);

        title=findViewById(R.id.titleSheet);
        description=findViewById(R.id.descSheet);
        radioGrp = (RadioGroup) findViewById(R.id.radioGrp);
        button=findViewById(R.id.button);
        buttondel=findViewById(R.id.buttondel);
        Intent intent = getIntent();
        String ID = intent.getStringExtra("ID");
        MainActivity.getInstance().finish();
        TaskHolderAPI taskHolderAPI = ApiClient.getClient().create(TaskHolderAPI.class);
        Call<TaskDetailModel> call = taskHolderAPI.getTaskDetail(ID);
        call.enqueue(new Callback<TaskDetailModel>() {
            @Override
            public void onResponse(Call<TaskDetailModel> call, Response<TaskDetailModel> response) {
                Log.d(TAG,call.toString() +" ----- "+response.message());
                Log.d(TAG,call.toString() +" ----- "+response.body());

                if(!response.isSuccessful()){
                    Toast.makeText(TaskDetail.this, ""+response.code(), Toast.LENGTH_SHORT).show();
                    Log.d(TAG,"HATA");
                    return;
                }else{
                    if (response.body()!=null){
                        title.setText(response.body().data.title);
                        description.setText(response.body().data.description);
                        switch (response.body().data.type){
                            case "0":((RadioButton)radioGrp.getChildAt(0)).setChecked(true);
                                break;
                            case "1":((RadioButton)radioGrp.getChildAt(1)).setChecked(true);
                                break;
                            case "2":((RadioButton)radioGrp.getChildAt(2)).setChecked(true);
                                break;
                        }
                        Log.d(TAG,"TYPE : "+response.body().data.type);
                    }
                }
            }

            @Override
            public void onFailure(Call<TaskDetailModel> call, Throwable t) {
                Log.d(TAG,call.request() +" ----- "+t.getMessage());
            }


        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.d(TAG,"----"+returnBtnId(radioGrp)+"----"+title.getText()+"----"+description.getText()+"-----"+"ID"+ID);

                TaskDetailModel.Data data = new TaskDetailModel.Data(returnBtnId(radioGrp),description.getText().toString(),title.getText().toString());
                Call<TaskDetailModel.Data> dataCall=taskHolderAPI.putTaskDetail(ID,data);
                Log.d(TAG,dataCall.request().method());
                dataCall.enqueue(new Callback<TaskDetailModel.Data>() {
                    @Override
                    public void onResponse(Call<TaskDetailModel.Data> call, Response<TaskDetailModel.Data> response) {
                        if(!response.isSuccessful()){
                            Toast.makeText(TaskDetail.this, ""+response.code(), Toast.LENGTH_SHORT).show();
                            Log.d(TAG,"HATA"+call.request());
                            return;
                        }else{
                            Log.d(TAG,"BAŞARILI");
                            Toast.makeText(TaskDetail.this, "SUCCESS", Toast.LENGTH_SHORT).show();
                            }
                    }

                    @Override
                    public void onFailure(Call<TaskDetailModel.Data> call, Throwable t) {
                        Log.d(TAG,call.request() +" ----- "+t.getMessage());
                    }
                });
                startActivity(new Intent(TaskDetail.this,MainActivity.class));
            }
        });

        buttondel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Call<TaskDetailModel.Data> dataCall=taskHolderAPI.delTaskDetail(ID);
                Log.d(TAG,dataCall.request().method());
                dataCall.enqueue(new Callback<TaskDetailModel.Data>() {
                    @Override
                    public void onResponse(Call<TaskDetailModel.Data> call, Response<TaskDetailModel.Data> response) {
                        if(!response.isSuccessful()){
                            Toast.makeText(TaskDetail.this, ""+response.code(), Toast.LENGTH_SHORT).show();
                            Log.d(TAG,"HATA"+call.request());
                            return;
                        }else{
                            Log.d(TAG,"BAŞARILI");
                            Toast.makeText(TaskDetail.this, "REMOVE SUCCESS", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<TaskDetailModel.Data> call, Throwable t) {
                        Log.d(TAG,call.request() +" ----- "+t.getMessage());
                    }
                });

                startActivity(new Intent(TaskDetail.this,MainActivity.class));
            }
        });
    }

    private String returnBtnId(RadioGroup rg) {
        RadioButton selectedRadioButton = (RadioButton) findViewById(rg.getCheckedRadioButtonId());
        String selectedRadioButtonText = selectedRadioButton.getText().toString();
        switch (selectedRadioButtonText) {
            case "Daily":
                return type = "0";
            case "Weekly":
                return type = "1";
            case "Monthly":
                return type = "2";
        }
        return type;
    }
}