package com.wind.anuroopjodi.Object;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONObject;

public class PhysicalStatusObj implements Parcelable {

    public String id;
    public String name;
    public boolean Selected = false;

    public PhysicalStatusObj() {
    }

    public PhysicalStatusObj(String id, String name, boolean selected) {
        this.id = id;
        this.name = name;
        Selected = selected;
    }

    public PhysicalStatusObj(JSONObject object) {
        try {
            this.id = object.getString("id");
            this.name = object.getString("name");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected PhysicalStatusObj(Parcel in) {
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

    public static final Creator<PhysicalStatusObj> CREATOR = new Creator<PhysicalStatusObj>() {
        @Override
        public PhysicalStatusObj createFromParcel(Parcel in) {
            return new PhysicalStatusObj(in);
        }

        @Override
        public PhysicalStatusObj[] newArray(int size) {
            return new PhysicalStatusObj[size];
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

