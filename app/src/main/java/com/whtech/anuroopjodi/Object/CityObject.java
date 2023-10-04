package com.whtech.anuroopjodi.Object;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONObject;

public class CityObject implements Parcelable {

    public String id;
    public String city_name;

    public CityObject() {
    }

    public CityObject(String id, String city_name) {
        this.id = id;
        this.city_name = city_name;
    }

    public CityObject(JSONObject object) {
        try {
            this.id = object.getString("id");
            this.city_name = object.getString("city_name");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected CityObject(Parcel in) {
        id = in.readString();
        city_name = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(city_name);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<CityObject> CREATOR = new Creator<CityObject>() {
        @Override
        public CityObject createFromParcel(Parcel in) {
            return new CityObject(in);
        }

        @Override
        public CityObject[] newArray(int size) {
            return new CityObject[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCasteName() {
        return city_name;
    }

    public void setCasteName(String city_name) {
        this.city_name = city_name;
    }

    @Override
    public String toString() {
        return city_name;
    }
}
