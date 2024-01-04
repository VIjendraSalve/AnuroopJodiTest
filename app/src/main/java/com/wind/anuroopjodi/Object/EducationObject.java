package com.wind.anuroopjodi.Object;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONObject;

public class EducationObject implements Parcelable {

    public String id;
    public String name;
    public boolean Selected = false;

    public EducationObject() {
    }

    public EducationObject(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public EducationObject(String id, String name, boolean selected) {
        this.id = id;
        this.name = name;
        Selected = selected;
    }

    public EducationObject(JSONObject object) {
        try {
            this.id = object.getString("id");
            this.name = object.getString("name");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    protected EducationObject(Parcel in) {
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

    public static final Creator<EducationObject> CREATOR = new Creator<EducationObject>() {
        @Override
        public EducationObject createFromParcel(Parcel in) {
            return new EducationObject(in);
        }

        @Override
        public EducationObject[] newArray(int size) {
            return new EducationObject[size];
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
