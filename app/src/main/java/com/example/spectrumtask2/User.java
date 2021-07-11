package com.example.spectrumtask2;

import org.jetbrains.annotations.NotNull;

public class User {
    public String fullName,uid, email;


    public User() {

    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
       this.email = email;
    }


    @Override
    public @NotNull String toString() {
        return "User{" +
                "fullName='" + fullName + '\'' +
                ", uid='" + uid + '\'' +
                ", Email='" + email + '\'' +
                '}';
    }
}
