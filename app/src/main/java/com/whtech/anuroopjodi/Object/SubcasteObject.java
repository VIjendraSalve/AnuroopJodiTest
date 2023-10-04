package com.whtech.anuroopjodi.Object;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONObject;

public class SubcasteObject implements Parcelable {

    public String id;
    public String casteName;
    public boolean Selected = false;



    public SubcasteObject() {
    }
    public SubcasteObject(JSONObject object) {
        try {
            this.id = object.getString("id");
            this.casteName = object.getString("casteName");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public SubcasteObject(String id, String casteName) {
        this.id = id;
        this.casteName = casteName;
       // this.Selected = selected;
    }

    protected SubcasteObject(Parcel in) {
        id = in.readString();
        casteName = in.readString();
        Selected = in.readByte() != 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(casteName);
        dest.writeByte((byte) (Selected ? 1 : 0));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<SubcasteObject> CREATOR = new Creator<SubcasteObject>() {
        @Override
        public SubcasteObject createFromParcel(Parcel in) {
            return new SubcasteObject(in);
        }

        @Override
        public SubcasteObject[] newArray(int size) {
            return new SubcasteObject[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCasteName() {
        return casteName;
    }

    public void setCasteName(String casteName) {
        this.casteName = casteName;
    }

    public boolean isSelected() {
        return Selected;
    }

    public void setSelected(boolean selected) {
        Selected = selected;
    }

    @Override
    public String toString() {
        return casteName;
    }
}
