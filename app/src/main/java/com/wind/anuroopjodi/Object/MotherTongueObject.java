package com.wind.anuroopjodi.Object;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONObject;

public class MotherTongueObject implements Parcelable {

    public String id;
    public String languageName;
    public boolean Selected = false;

    public MotherTongueObject() {
    }

    public MotherTongueObject(String id, String languageName) {
        this.id = id;
        this.languageName = languageName;
    }

    public MotherTongueObject(String id, String languageName, boolean selected) {
        this.id = id;
        this.languageName = languageName;
        Selected = selected;
    }

    public MotherTongueObject(JSONObject object) {
        try {
            this.id = object.getString("id");
            this.languageName = object.getString("languageName");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    protected MotherTongueObject(Parcel in) {
        id = in.readString();
        languageName = in.readString();
        Selected = in.readByte() != 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(languageName);
        dest.writeByte((byte) (Selected ? 1 : 0));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<MotherTongueObject> CREATOR = new Creator<MotherTongueObject>() {
        @Override
        public MotherTongueObject createFromParcel(Parcel in) {
            return new MotherTongueObject(in);
        }

        @Override
        public MotherTongueObject[] newArray(int size) {
            return new MotherTongueObject[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLanguageName() {
        return languageName;
    }

    public void setLanguageName(String languageName) {
        this.languageName = languageName;
    }

    @Override
    public String toString() {
        return languageName;
    }

    public boolean isSelected() {
        return Selected;
    }

    public void setSelected(boolean selected) {
        Selected = selected;
    }
}
