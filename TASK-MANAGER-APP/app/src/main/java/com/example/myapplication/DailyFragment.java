package com.example.myapplication;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.RetrofitAPI.ApiClient;
import com.example.myapplication.RetrofitAPI.TaskHolderAPI;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DailyFragment extends Fragment {
    View sheetView, fragmentView;
    RecyclerView recyclerView;
    ArrayList<Data> taskModelArray = new ArrayList<>();
    FloatingActionButton fab;
    TextView titleSheet = null;
    TextView descSheet = null;
    RadioGroup rg;
    String type;
    String TAG = DailyFragment.class.getName();
    BottomSheetDialog bottomSheetDialog;

    public DailyFragment() {
    }


    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        taskModelArray.clear();
        getTasks();
        fragmentView = inflater.inflate(R.layout.fragment_daily, container, false);
        fab = fragmentView.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View vs) {
                bottomSheetDialog = new BottomSheetDialog(Objects.requireNonNull(getContext()));
                sheetView = LayoutInflater.from(getContext()).inflate(R.layout.bottom_sheet, (LinearLayout) bottomSheetDialog.findViewById(R.id.layoutBottomSheetContainer));
                Button btn = sheetView.findViewById(R.id.button);

                titleSheet = sheetView.findViewById(R.id.titleSheet);
                descSheet = sheetView.findViewById(R.id.descSheet);
                rg = sheetView.findViewById(R.id.radioGrp);

                btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (TextUtils.isEmpty(titleSheet.getText().toString()) || TextUtils.isEmpty(descSheet.getText().toString())) {
                            titleSheet.setError("Can't be empty");
                            descSheet.setError("Can't be empty");
                        } else {
                            sendDataServer(new Data(returnBtnId(rg), descSheet.getText().toString(),titleSheet.getText().toString()));
                        }

                    }
                });
                bottomSheetDialog.setContentView(sheetView);
                bottomSheetDialog.show();
            }
        });
        return fragmentView;

    }

    private void sendDataServer(Data taskModel) {
        Log.d(TAG, "Title :" + taskModel.getTitle() + "-------" + "Desc : " + taskModel.getDescription() + "------" + taskModel.getType());

        TaskDetailModel.Data data = new TaskDetailModel.Data(returnBtnId(rg),taskModel.getDescription(),taskModel.getTitle());

        TaskHolderAPI taskHolderAPI = ApiClient.getClient().create(TaskHolderAPI.class);

        Call<TaskDetailModel.Data> dataCall = taskHolderAPI.postTaskDetail(data);

        dataCall.enqueue(new Callback<TaskDetailModel.Data>() {
            @Override
            public void onResponse(Call<TaskDetailModel.Data> call, Response<TaskDetailModel.Data> response) {
                if(!response.isSuccessful()){
                    Toast.makeText(getContext(), ""+response.code(), Toast.LENGTH_SHORT).show();
                    Log.d(TAG,"HATA"+call.request());
                    return;
                }else{
                    Log.d(TAG,"BAŞARILI");
                    Toast.makeText(getContext(), "SUCCESS", Toast.LENGTH_SHORT).show();
                    taskModelArray.clear();
                    getTasks();
                    bottomSheetDialog.hide();
                }
            }

            @Override
            public void onFailure(Call<TaskDetailModel.Data> call, Throwable t) {
                Log.d(TAG,call.request() +" ----- "+t.getMessage());
            }
        });
    }

    private void setAdapterTaskList() {
        recyclerView = fragmentView.findViewById(R.id.recyclerView);
        recyclerView.setAdapter(new TaskAdapter(getContext(), taskModelArray));
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    public void getTasks() {
        TaskHolderAPI taskHolderAPI = ApiClient.getClient().create(TaskHolderAPI.class);
        Call<TaskModel> call = taskHolderAPI.getTasks();
        call.enqueue(new Callback<TaskModel>() {
            @Override
            public void onResponse(Call<TaskModel> call, Response<TaskModel> response) {
                Log.d(TAG, call.toString() + " ----- " + response.message());
                Log.d(TAG, call.toString() + " ----- " + response.body());

                if (!response.isSuccessful()) {
                    Toast.makeText(getContext(), "" + response.code(), Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "HATA");
                    return;
                } else {
                    TaskModel taskModel = response.body();
                    if (response.body() != null) {
                        for (int i = 0; i < response.body().data.size(); i++) {
                            if(taskModel.data.get(i).getType().equals("0")){
                                taskModelArray.add(new Data(taskModel.data.get(i).getTitle(), taskModel.data.get(i).getDescription(), taskModel.data.get(i).getType(), taskModel.data.get(i).getId()));
                            }
                        }
                    }
                }
                Collections.reverse(taskModelArray);
                setAdapterTaskList();
            }

            @Override
            public void onFailure(Call<TaskModel> call, Throwable t) {
                Log.d(TAG, call.request() + " ----- " + t.getMessage());
            }
        });
    }


    private String returnBtnId(RadioGroup rg) {
        RadioButton selectedRadioButton = (RadioButton) sheetView.findViewById(rg.getCheckedRadioButtonId());
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