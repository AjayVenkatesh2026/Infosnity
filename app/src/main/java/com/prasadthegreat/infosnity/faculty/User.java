package com.prasadthegreat.infosnity.faculty;

import java.util.HashMap;

public class User {
    private String id, name, mail, mid, profilepic, status, role;

    public User(String id, String name, String mail, String mid, String profilepic, String status) {
        this.id = id;
        this.name = name;
        this.mail = mail;
        this.mid = mid;
        this.profilepic = profilepic;
        this.status = status;
    }

    public User(String id, String name, String mail, String mid, String profilepic, String status, String role) {
        this.id = id;
        this.name = name;
        this.mail = mail;
        this.mid = mid;
        this.profilepic = profilepic;
        this.status = status;
        this.role = role;
    }

    public User() {
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public void setMid(String mid) {
        this.mid = mid;
    }

    public void setProfilepic(String profilepic) {
        this.profilepic = profilepic;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setRole(String role) {
        this.role = role;
    }


    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getMail() {
        return mail;
    }

    public String getMid() {
        return mid;
    }

    public String getProfilepic() {
        return profilepic;
    }

    public String getStatus() {
        return status;
    }

    public String getRole() {
        return role;
    }
}
