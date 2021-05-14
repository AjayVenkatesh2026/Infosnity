package com.prasadthegreat.infosnity.students;

public class studentModel {
    String name,profilepic;

    public studentModel() {}

    public studentModel(String name, String profilepic) {
        this.name = name;
        this.profilepic = profilepic;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProfilepic() {
        return profilepic;
    }

    public void setProfilepic(String profilepic) {
        this.profilepic = profilepic;
    }
}
