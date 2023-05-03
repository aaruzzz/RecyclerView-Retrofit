package com.research.retrofitrecyclerview;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.graphics.Canvas;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

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

    SwipeController swipeController = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);


        text_keyword = (EditText) findViewById(R.id.textBox);
        submit_btn = (Button) findViewById(R.id.submitButton);
        submit_btn.setOnClickListener(view -> checkText());

        text_keyword.setOnEditorActionListener((textView, i, keyEvent) -> {
            if ((keyEvent != null && (keyEvent.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (i == EditorInfo.IME_ACTION_DONE)) {
                submit_btn.performClick();
            }
            return false;
        });
    }

    private void checkText() {

        text_keyword = findViewById(R.id.textBox);
        String search_text = text_keyword.getText().toString();

        if (search_text.length() == 0) {
            Toast.makeText(MainActivity.this, "Enter term to search!", Toast.LENGTH_SHORT).show();
        } else {
            getUserListData();
        }

    }

    private void getUserListData() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://pixabay.com/").addConverterFactory(GsonConverterFactory.create()).build();
        ApiInterface api = retrofit.create(ApiInterface.class);

        final ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setCancelable(false); // set cancelable to false
        progressDialog.setMessage("Please Wait"); // set message
        progressDialog.show(); // show progress dialog

        text_keyword = findViewById(R.id.textBox);
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
                } else {
                    Toast.makeText(MainActivity.this, getApplicationContext().getString(R.string.issue_error_text), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void setDataInRecyclerView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MainActivity.this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);

        UsersAdapter usersAdapter = new UsersAdapter(MainActivity.this, userListResponseData.getHits());
        recyclerView.setAdapter(usersAdapter);

        swipeController = new SwipeController(new SwipeControllerActions() {
            @Override
            public void onRightClicked(int position) {
                usersAdapter.userListResponseData.remove(position);
                usersAdapter.notifyItemRemoved(position);
                usersAdapter.notifyItemRangeChanged(position, usersAdapter.getItemCount());
            }

            @Override
            public void onLeftClicked(int position) {
//                usersAdapter.userListResponseData.set();
                Toast.makeText(getApplicationContext(), "UNDER DEVELOPMENT", Toast.LENGTH_SHORT).show();
                usersAdapter.notifyItemChanged(position);
//                usersAdapter.notifyItemRangeChanged(position, usersAdapter.getItemCount());
            }
        });

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(swipeController);
        itemTouchHelper.attachToRecyclerView(recyclerView);

        recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
                swipeController.onDraw(c);
            }
        });
    }
}