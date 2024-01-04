package com.wind.anuroopjodi.InitialActivities.RegistrationSteps;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import org.json.JSONObject;

public class NakshatraObject implements Parcelable {
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

    String id;
    String name;


    public NakshatraObject(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public NakshatraObject(JSONObject object) {
        try {
            this.id = object.getString("id");
            this.name = object.getString("name");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected NakshatraObject(Parcel in) {
        id = in.readString();
        name = in.readString();
    }

    public static final Creator<NakshatraObject> CREATOR = new Creator<NakshatraObject>() {
        @Override
        public NakshatraObject createFromParcel(Parcel in) {
            return new NakshatraObject(in);
        }

        @Override
        public NakshatraObject[] newArray(int size) {
            return new NakshatraObject[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
    }
    @Override
    public String toString() {
        return name;
    }
}
