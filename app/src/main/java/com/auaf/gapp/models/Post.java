package com.auaf.gapp.models;

import java.util.HashMap;
import java.util.Map;

public class  Post {

    String uid;
    String name;
    String photo;
    String title;
    String description;

    public Post(){

    }
    public Post(String uid, String name, String photo, String title, String description) {
        this.uid = uid;
        this.name = name;
        this.photo = photo;
        this.title = title;
        this.description = description;
    }

    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("uid", uid);
        result.put("name", name);
        result.put("photo", photo);
        result.put("title", title);
        result.put("description", description);
        return result;
    }


    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
