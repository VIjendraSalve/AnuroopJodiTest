package com.wind.anuroopjodi.Object;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import org.json.JSONObject;

public class GanObject implements Parcelable {

    String id;
    String name;


    public GanObject(String id, String name) {
        this.id = id;
        this.name = name;
    }


    public GanObject(JSONObject object) {
        try {
            this.id = object.getString("id");
            this.name = object.getString("name");

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }



    protected GanObject(Parcel in) {
        id = in.readString();
        name = in.readString();
    }

    public static final Creator<GanObject> CREATOR = new Creator<GanObject>() {
        @Override
        public GanObject createFromParcel(Parcel in) {
            return new GanObject(in);
        }

        @Override
        public GanObject[] newArray(int size) {
            return new GanObject[size];
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
