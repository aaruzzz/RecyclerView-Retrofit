package com.research.retrofitrecyclerview;

import static com.research.retrofitrecyclerview.R.*;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.UsersViewHolder> {
    Context context;
    List<UserListResponse.hits> userListResponseData;
    public UsersAdapter(Context context, List<UserListResponse.hits> userListResponseData) {
        this.context = context;
        this.userListResponseData = userListResponseData;
    }

    @NonNull
    @Override
    public UsersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(layout.user_list_items, parent, false);
        return new UsersViewHolder(view);
    }

    @SuppressLint({"StringFormatMatches", "StringFormatInvalid"})
    @Override
    public void onBindViewHolder(@NonNull UsersViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        final UserListResponse.hits hit = userListResponseData.get(position);
        holder.id.setText(context.getApplicationContext().getString(string.id, hit.getId()));
        holder.tags.setText(context.getApplicationContext().getString(string.tag, hit.getTags()));

        holder.itemView.setOnClickListener(view -> Toast.makeText(context, userListResponseData.get(position).getTags(), Toast.LENGTH_SHORT).show());
    }


    @Override
    public int getItemCount() {
        return userListResponseData.size();
    }

    static class UsersViewHolder extends  RecyclerView.ViewHolder{
        TextView id,tags;

        public UsersViewHolder(View itemView){
            super(itemView);
            id = itemView.findViewById(R.id.id_text);
            tags = itemView.findViewById(R.id.tags_text);
        }
    }
}
