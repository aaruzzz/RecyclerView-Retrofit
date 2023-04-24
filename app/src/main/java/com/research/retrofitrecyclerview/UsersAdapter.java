package com.research.retrofitrecyclerview;

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
        View view = LayoutInflater.from(context).inflate(R.layout.user_list_items, null);
        UsersViewHolder usersViewHolder = new UsersViewHolder(view);
        return usersViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull UsersViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        holder.id.setText("ID: "+ userListResponseData.get(position).getId());
        holder.tags.setText("Tags: " + userListResponseData.get(position).getTags());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, userListResponseData.get(position).getTags(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    public int getItemCount() {
        return userListResponseData.size();
    }

    class UsersViewHolder extends  RecyclerView.ViewHolder{
        TextView id,tags;

        public UsersViewHolder(View itemView){
            super(itemView);
            id = (TextView) itemView.findViewById(R.id.id_text);
            tags = (TextView) itemView.findViewById(R.id.tags_text);
        }

    }
}
