package com.wind.anuroopjodi.Object;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import org.json.JSONObject;

public class Charanobject implements Parcelable {

    String id;
    String name;

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

    public Charanobject(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public Charanobject(JSONObject object) {
        try {
            this.id = object.getString("id");
            this.name = object.getString("name");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected Charanobject(Parcel in) {
        id = in.readString();
        name = in.readString();
    }

    public static final Creator<Charanobject> CREATOR = new Creator<Charanobject>() {
        @Override
        public Charanobject createFromParcel(Parcel in) {
            return new Charanobject(in);
        }

        @Override
        public Charanobject[] newArray(int size) {
            return new Charanobject[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(name);
    }

    @NonNull
    @Override
    public String toString() {
        return name;
    }
}
