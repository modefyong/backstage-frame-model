package com.lrhb.dataaccess.model.com;

public class IngIntergral {
    private String id;

    private String intergral;

    private String user;

    private String age;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getIntergral() {
        return intergral;
    }

    public void setIntergral(String intergral) {
        this.intergral = intergral == null ? null : intergral.trim();
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user == null ? null : user.trim();
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age == null ? null : age.trim();
    }
}