package com.whtech.anuroopjodi.Object;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONObject;

public class ProfessionObject implements Parcelable {

    public String id;
    public String name;
    public boolean Selected = false;

    public ProfessionObject() {
    }

    public ProfessionObject(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public ProfessionObject(JSONObject object) {
        try {
            this.id = object.getString("id");
            this.name = object.getString("name");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected ProfessionObject(Parcel in) {
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

    public static final Creator<ProfessionObject> CREATOR = new Creator<ProfessionObject>() {
        @Override
        public ProfessionObject createFromParcel(Parcel in) {
            return new ProfessionObject(in);
        }

        @Override
        public ProfessionObject[] newArray(int size) {
            return new ProfessionObject[size];
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

    @Override
    public String toString() {
        return name;
    }
}
