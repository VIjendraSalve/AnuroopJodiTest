package com.whtech.anuroopjodi.Object;

import android.os.Parcel;
import android.os.Parcelable;

public class TypeMatch implements Parcelable
{
    private String title;
    private int image;

    public TypeMatch() {
    }

    public TypeMatch(String title, int image) {
        this.title = title;
        this.image = image;
    }


    protected TypeMatch(Parcel in) {
        title = in.readString();
        image = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeInt(image);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<TypeMatch> CREATOR = new Creator<TypeMatch>() {
        @Override
        public TypeMatch createFromParcel(Parcel in) {
            return new TypeMatch(in);
        }

        @Override
        public TypeMatch[] newArray(int size) {
            return new TypeMatch[size];
        }
    };

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
}
