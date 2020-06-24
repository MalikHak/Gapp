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

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public boolean isMarried() {
        return isMarried;
    }

    public void setMarried(boolean married) {
        isMarried = married;
    }
}
