package com.wind.anuroopjodi.Object;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONObject;

public class BloodGroupObject implements Parcelable {

    public String id;
    public String name;

    public BloodGroupObject() {
    }

    public BloodGroupObject(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public BloodGroupObject(JSONObject object) {
        try {
            this.id = object.getString("id");
            this.name = object.getString("name");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected BloodGroupObject(Parcel in) {
        id = in.readString();
        name = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<BloodGroupObject> CREATOR = new Creator<BloodGroupObject>() {
        @Override
        public BloodGroupObject createFromParcel(Parcel in) {
            return new BloodGroupObject(in);
        }

        @Override
        public BloodGroupObject[] newArray(int size) {
            return new BloodGroupObject[size];
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

    @Override
    public String toString() {
        return name;
    }
}


