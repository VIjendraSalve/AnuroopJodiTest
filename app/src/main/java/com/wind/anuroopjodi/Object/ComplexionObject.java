package com.wind.anuroopjodi.Object;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONObject;

public class ComplexionObject implements Parcelable {

    public String id;
    public String name;

    public ComplexionObject() {
    }

    public ComplexionObject(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public ComplexionObject(JSONObject object) {
        try {
            this.id = object.getString("id");
            this.name = object.getString("name");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected ComplexionObject(Parcel in) {
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

    public static final Creator<ComplexionObject> CREATOR = new Creator<ComplexionObject>() {
        @Override
        public ComplexionObject createFromParcel(Parcel in) {
            return new ComplexionObject(in);
        }

        @Override
        public ComplexionObject[] newArray(int size) {
            return new ComplexionObject[size];
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

