package com.whtech.anuroopjodi.Object;

import org.json.JSONObject;

public class Caste {

    private String id;
    private String caste_name;
    private String religionId;

    public Caste(String id, String caste_name) {
        this.id = id;
        this.caste_name = caste_name;
    }

    public Caste(JSONObject object) {
        try {
            this.id = object.getString("id");
            this.caste_name = object.getString("religionName");
            this.religionId = object.getString("id");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCaste_name() {
        return caste_name;
    }

    public void setCaste_name(String caste_name) {
        this.caste_name = caste_name;
    }

    public String getReligionId() {
        return religionId;
    }

    public void setReligionId(String religionId) {
        this.religionId = religionId;
    }

    @Override
    public String toString() {
        return caste_name;
    }
}
