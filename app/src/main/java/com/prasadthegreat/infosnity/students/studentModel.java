package com.prasadthegreat.infosnity.students;

public class studentModel {
    String name,profilepic,id;

    public studentModel() {}

    public studentModel(String name, String profilepic, String id) {
        this.name = name;
        this.profilepic = profilepic;
        this.id = id;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
