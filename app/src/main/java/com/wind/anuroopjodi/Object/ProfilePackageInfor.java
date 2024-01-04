package com.wind.anuroopjodi.Object;

import android.os.Parcel;
import android.os.Parcelable;

public class ProfilePackageInfor implements Parcelable
{
    private String title;
    private int image;
    private String count;

    public ProfilePackageInfor() {
    }

    public ProfilePackageInfor(String title, int image) {
        this.title = title;
        this.image = image;
    }

    public ProfilePackageInfor(String title, int image,String count) {
        this.title = title;
        this.image = image;
        this.count = count;
    }

    protected ProfilePackageInfor(Parcel in) {
        title = in.readString();
        image = in.readInt();
        count = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeInt(image);
        dest.writeString(count);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ProfilePackageInfor> CREATOR = new Creator<ProfilePackageInfor>() {
        @Override
        public ProfilePackageInfor createFromParcel(Parcel in) {
            return new ProfilePackageInfor(in);
        }

        @Override
        public ProfilePackageInfor[] newArray(int size) {
            return new ProfilePackageInfor[size];
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

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }
}
