package com.whtech.anuroopjodi.Object;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONObject;

public class StateObject implements Parcelable {

    public String id;
    public String name;

    public StateObject() {
    }

    public StateObject(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public StateObject(JSONObject object) {
        try {
            this.id = object.getString("id");
            this.name = object.getString("name");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected StateObject(Parcel in) {
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

    public static final Creator<StateObject> CREATOR = new Creator<StateObject>() {
        @Override
        public StateObject createFromParcel(Parcel in) {
            return new StateObject(in);
        }

        @Override
        public StateObject[] newArray(int size) {
            return new StateObject[size];
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
