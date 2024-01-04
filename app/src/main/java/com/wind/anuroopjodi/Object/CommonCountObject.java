package com.wind.anuroopjodi.Object;

import android.os.Parcel;
import android.os.Parcelable;

public class CommonCountObject implements Parcelable {

    public String id;
    public String count;
    public boolean Selected = false;

    public CommonCountObject() {
    }

    public CommonCountObject(String id, String count, boolean selected) {
        this.id = id;
        this.count = count;
        Selected = selected;
    }

    protected CommonCountObject(Parcel in) {
        id = in.readString();
        count = in.readString();
        Selected = in.readByte() != 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(count);
        dest.writeByte((byte) (Selected ? 1 : 0));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<CommonCountObject> CREATOR = new Creator<CommonCountObject>() {
        @Override
        public CommonCountObject createFromParcel(Parcel in) {
            return new CommonCountObject(in);
        }

        @Override
        public CommonCountObject[] newArray(int size) {
            return new CommonCountObject[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public boolean isSelected() {
        return Selected;
    }

    public void setSelected(boolean selected) {
        Selected = selected;
    }

    @Override
    public String toString() {
        return count;
    }
}
