package com.whtech.anuroopjodi.Object;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONObject;

public class PackageObject implements Parcelable {

    public String id;
    public String packageAmount;
    public String packageName;
    public String packageDescription;
    public String chatFeature;
    public String VisibleMembers;
    public String suggesionWeb;
    public String vendors;
    public String horoscopes;
    public String validity;
    public String status;
    public String strike_thr_packageAmount;
    public String visible_email;
    public String visible_mobile;
    public boolean Selected = false;

    public PackageObject() {
    }

    public PackageObject(String id, String packageAmount, String packageName, String packageDescription, String chatFeature, String visibleMembers, String suggesionWeb, String vendors, String horoscopes, String validity, String status, boolean selected) {
        this.id = id;
        this.packageAmount = packageAmount;
        this.packageName = packageName;
        this.packageDescription = packageDescription;
        this.chatFeature = chatFeature;
        VisibleMembers = visibleMembers;
        this.suggesionWeb = suggesionWeb;
        this.vendors = vendors;
        this.horoscopes = horoscopes;
        this.validity = validity;
        this.status = status;
        Selected = selected;
    }

    public PackageObject(JSONObject object) {
        try {
            this.id = object.getString("id");
            this.packageAmount = object.getString("packageAmount");
            this.packageName = object.getString("packageName");
            this.packageDescription = object.getString("packageDescription");
            this.chatFeature = object.getString("chatFeature");
            this.VisibleMembers = object.getString("VisibleMembers");
            this.suggesionWeb = object.getString("suggesionWeb");
            this.vendors = object.getString("vendors");
            this.horoscopes = object.getString("horoscopes");
            this.validity = object.getString("validity");
            this.status = object.getString("status");
            this.strike_thr_packageAmount = object.getString("strike_thr_packageAmount");
            this.visible_email = object.getString("visible_email");
            this.visible_mobile = object.getString("visible_mobile");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected PackageObject(Parcel in) {
        id = in.readString();
        packageAmount = in.readString();
        packageName = in.readString();
        packageDescription = in.readString();
        chatFeature = in.readString();
        VisibleMembers = in.readString();
        suggesionWeb = in.readString();
        vendors = in.readString();
        horoscopes = in.readString();
        validity = in.readString();
        status = in.readString();
        strike_thr_packageAmount = in.readString();
        visible_email = in.readString();
        visible_mobile = in.readString();
        Selected = in.readByte() != 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(packageAmount);
        dest.writeString(packageName);
        dest.writeString(packageDescription);
        dest.writeString(chatFeature);
        dest.writeString(VisibleMembers);
        dest.writeString(suggesionWeb);
        dest.writeString(vendors);
        dest.writeString(horoscopes);
        dest.writeString(validity);
        dest.writeString(status);
        dest.writeString(strike_thr_packageAmount);
        dest.writeString(visible_email);
        dest.writeString(visible_mobile);
        dest.writeByte((byte) (Selected ? 1 : 0));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<PackageObject> CREATOR = new Creator<PackageObject>() {
        @Override
        public PackageObject createFromParcel(Parcel in) {
            return new PackageObject(in);
        }

        @Override
        public PackageObject[] newArray(int size) {
            return new PackageObject[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPackageAmount() {
        return packageAmount;
    }

    public void setPackageAmount(String packageAmount) {
        this.packageAmount = packageAmount;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getPackageDescription() {
        return packageDescription;
    }

    public void setPackageDescription(String packageDescription) {
        this.packageDescription = packageDescription;
    }

    public String getChatFeature() {
        return chatFeature;
    }

    public void setChatFeature(String chatFeature) {
        this.chatFeature = chatFeature;
    }

    public String getVisibleMembers() {
        return VisibleMembers;
    }

    public void setVisibleMembers(String visibleMembers) {
        VisibleMembers = visibleMembers;
    }

    public String getSuggesionWeb() {
        return suggesionWeb;
    }

    public void setSuggesionWeb(String suggesionWeb) {
        this.suggesionWeb = suggesionWeb;
    }

    public String getVendors() {
        return vendors;
    }

    public void setVendors(String vendors) {
        this.vendors = vendors;
    }

    public String getHoroscopes() {
        return horoscopes;
    }

    public void setHoroscopes(String horoscopes) {
        this.horoscopes = horoscopes;
    }

    public String getValidity() {
        return validity;
    }

    public void setValidity(String validity) {
        this.validity = validity;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public boolean isSelected() {
        return Selected;
    }

    public void setSelected(boolean selected) {
        Selected = selected;
    }

    public String getStrike_thr_packageAmount() {
        return strike_thr_packageAmount;
    }

    public void setStrike_thr_packageAmount(String strike_thr_packageAmount) {
        this.strike_thr_packageAmount = strike_thr_packageAmount;
    }

    public String getVisible_email() {
        return visible_email;
    }

    public void setVisible_email(String visible_email) {
        this.visible_email = visible_email;
    }

    public String getVisible_mobile() {
        return visible_mobile;
    }

    public void setVisible_mobile(String visible_mobile) {
        this.visible_mobile = visible_mobile;
    }
}
