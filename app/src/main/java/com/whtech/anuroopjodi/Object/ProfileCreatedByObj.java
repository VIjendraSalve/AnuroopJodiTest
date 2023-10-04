package com.whtech.anuroopjodi.Object;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONObject;

public class ProfileCreatedByObj implements Parcelable {

    public String id;
    public String name;
    public boolean Selected=false;

    public ProfileCreatedByObj() {
    }

    public ProfileCreatedByObj(String id, String name, boolean selected) {
        this.id = id;
        this.name = name;
        Selected = selected;
    }

    public ProfileCreatedByObj(JSONObject object) {
        try {
            this.id = object.getString("id");
            this.name = object.getString("name");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected ProfileCreatedByObj(Parcel in) {
        id = in.readString();
        name = in.readString();
        Selected = in.readByte() != 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
        dest.writeByte((byte) (Selected ? 1 : 0));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ProfileCreatedByObj> CREATOR = new Creator<ProfileCreatedByObj>() {
        @Override
        public ProfileCreatedByObj createFromParcel(Parcel in) {
            return new ProfileCreatedByObj(in);
        }

        @Override
        public ProfileCreatedByObj[] newArray(int size) {
            return new ProfileCreatedByObj[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isSelected() {
        return Selected;
    }

    public void setSelected(boolean selected) {
        Selected = selected;
    }
}

