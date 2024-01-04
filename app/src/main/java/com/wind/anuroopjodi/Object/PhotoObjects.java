package com.wind.anuroopjodi.Object;

import org.json.JSONObject;

public class PhotoObjects {

    private String id;
    private String loginId;
    private String image;
    private String approved;
    private String entryDate;

    public PhotoObjects(JSONObject object) {
        try {
            this.id = object.getString("id");
            this.loginId = object.getString("loginId");
            this.image = object.getString("image");
            this.approved = object.getString("approved");
            this.entryDate = object.getString("entryDate");

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

    public String getLoginId() {
        return loginId;
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getApproved() {
        return approved;
    }

    public void setApproved(String approved) {
        this.approved = approved;
    }

    public String getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(String entryDate) {
        this.entryDate = entryDate;
    }
}
