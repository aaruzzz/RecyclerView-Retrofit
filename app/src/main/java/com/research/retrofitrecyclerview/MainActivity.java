package com.research.retrofitrecyclerview;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.net.UnknownHostException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    UserListResponse userListResponseData;

    EditText text_keyword;
    Button submit_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView =  findViewById(R.id.recyclerView);


        submit_btn = (Button) findViewById(R.id.submitButton);
        submit_btn.setOnClickListener(view -> checkText());

    }

    private void checkText(){

        text_keyword = (EditText) findViewById(R.id.textBox);
        String search_text = text_keyword.getText().toString();

        if(search_text.length() == 0){
            Toast.makeText(MainActivity.this, "Enter term to search!", Toast.LENGTH_SHORT).show();
        }
        else {
            getUserListData();
        }

    }

    private void getUserListData(){
        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://pixabay.com/").addConverterFactory(GsonConverterFactory.create()).build();
        ApiInterface api = retrofit.create(ApiInterface.class);

        final ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setCancelable(false); // set cancelable to false
        progressDialog.setMessage("Please Wait"); // set message
        progressDialog.show(); // show progress dialog

        text_keyword = (EditText) findViewById(R.id.textBox);
        String search_text = text_keyword.getText().toString();


        Call<UserListResponse> call = api.getHits("5303976-fd6581ad4ac165d1b75cc15b3", search_text, "photo", "true");
        call.enqueue(new Callback<UserListResponse>() {
            @Override
            public void onResponse(@NonNull Call<UserListResponse> call, @NonNull Response<UserListResponse> response) {
                progressDialog.dismiss(); //dismiss progress dialog
                userListResponseData = response.body();
                setDataInRecyclerView();
            }

            @Override
            public void onFailure(@NonNull Call<UserListResponse> call, @NonNull Throwable t) {
                if (t instanceof UnknownHostException) {
                    Toast.makeText(MainActivity.this, getApplicationContext().getString(R.string.check_connection), Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
                else {
                    Toast.makeText(MainActivity.this, getApplicationContext().getString(R.string.issue_error_text), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void setDataInRecyclerView(){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MainActivity.this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);

        UsersAdapter usersAdapter = new UsersAdapter(MainActivity.this, userListResponseData.getHits());
        recyclerView.setAdapter(usersAdapter);
    }
}