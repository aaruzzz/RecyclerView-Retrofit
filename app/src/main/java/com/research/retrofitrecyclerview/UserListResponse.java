package com.research.retrofitrecyclerview;


import java.util.List;

public class UserListResponse {

    public List<hits> hits;


    public List<hits> getHits() {
        return hits;
    }

    public static class hits {
        public int id;
        public String tags;

        public int getId() {
            return id;
        }

        public String getTags() {
            return tags;
        }

    }
}
