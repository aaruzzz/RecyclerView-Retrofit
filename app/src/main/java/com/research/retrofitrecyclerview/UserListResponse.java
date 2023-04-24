package com.research.retrofitrecyclerview;

import com.google.gson.annotations.SerializedName;

import java.util.HashMap;
import java.util.Map;
import java.util.List;

public class UserListResponse {

    public List<hits> hits;


    public List<hits> getHits() {
        return hits;
    }

    public void setHits(List<hits> hits) {
        this.hits = hits;
    }

    public class hits {
        public int id;
        public String tags;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getTags() {
            return tags;
        }

        public void setTags(String tags) {
            this.tags = tags;
        }
    }
}
