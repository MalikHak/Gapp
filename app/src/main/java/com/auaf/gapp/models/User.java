package com.auaf.gapp.models;

import java.util.HashMap;
import java.util.Map;

public class User {

    String uid;
    String name;
    String place;
    String job;
    int age;
    boolean isMarried;

    public User() {
    }


    public User(String uid,String name, String place, String job, int age, boolean isMarried) {
        this.uid=uid;
        this.name = name;
        this.place = place;
        this.job = job;
        this.age = age;
        this.isMarried = isMarried;
    }
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("uid", uid);
        result.put("name", name);
        result.put("place", place);
        result.put("job", job);
        result.put("age", age);
        result.put("isMarried", isMarried);

        return result;
    }



}
