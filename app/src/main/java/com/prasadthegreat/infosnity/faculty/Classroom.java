package com.prasadthegreat.infosnity.faculty;

import java.util.HashMap;
import java.util.Vector;

public class Classroom {
    String className;
    String admin;


    String adminId;

    public Classroom() {
    }

    public Classroom(String className, String admin) {
        this.className = className;
        this.admin = admin;
    }

    public Classroom(String className, String admin, String adminId) {
        this.className = className;
        this.admin = admin;
        this.adminId = adminId;
    }

    public String getClassName() {
        return className;
    }

    public String getAdmin() {
        return admin;
    }



    public void setAdminId(String adminId) {
        this.adminId = adminId;
    }

    public String getAdminId() {
        return adminId;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public void setAdmin(String admin) {
        this.admin = admin;
    }
}
