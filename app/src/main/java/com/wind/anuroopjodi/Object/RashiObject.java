package com.wind.anuroopjodi.Object;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONObject;

public class RashiObject implements Parcelable {

    public String id;
    public String name;

    public RashiObject() {
    }

    public RashiObject(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public RashiObject(JSONObject object) {
        try {
            this.id = object.getString("id");
            this.name = object.getString("name");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected RashiObject(Parcel in) {
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

    public static final Creator<RashiObject> CREATOR = new Creator<RashiObject>() {
        @Override
        public RashiObject createFromParcel(Parcel in) {
            return new RashiObject(in);
        }

        @Override
        public RashiObject[] newArray(int size) {
            return new RashiObject[size];
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


