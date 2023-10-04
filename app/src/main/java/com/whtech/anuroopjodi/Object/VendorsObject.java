package com.whtech.anuroopjodi.Object;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONObject;

public class VendorsObject implements Parcelable{

    public String id;
    public String category;
    public String name;
    public String vendor;
    public String contact;
    public String email;
    public String services;
    public String slug;
    public String city;
    public String image1;
    public String image2;
    public String status;
    public String entryDate;
    public String city_name;

    public VendorsObject() {


    }


    public VendorsObject(JSONObject object, String items_image_path) {
        try {
            this.id = object.getString("id");
            this.category = object.getString("category");
            this.name = object.getString("name");
            this.vendor = object.getString("vendor");
            this.contact = object.getString("contact");
            this.email = object.getString("email");
            this.services = object.getString("services");
            this.slug = object.getString("slug");
            this.city = object.getString("city");
            this.image1 = items_image_path + object.getString("image1");
            this.image2 = items_image_path + object.getString("image2");
            this.status = object.getString("status");
            this.entryDate = object.getString("entryDate");
            this.city_name = object.getString("city_name");


        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    protected VendorsObject(Parcel in) {
        id = in.readString();
        category = in.readString();
        name = in.readString();
        vendor = in.readString();
        contact = in.readString();
        email = in.readString();
        services = in.readString();
        slug = in.readString();
        city = in.readString();
        image1 = in.readString();
        image2 = in.readString();
        status = in.readString();
        entryDate = in.readString();
        city_name = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(category);
        dest.writeString(name);
        dest.writeString(vendor);
        dest.writeString(contact);
        dest.writeString(email);
        dest.writeString(services);
        dest.writeString(slug);
        dest.writeString(city);
        dest.writeString(image1);
        dest.writeString(image2);
        dest.writeString(status);
        dest.writeString(entryDate);
        dest.writeString(city_name);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<VendorsObject> CREATOR = new Creator<VendorsObject>() {
        @Override
        public VendorsObject createFromParcel(Parcel in) {
            return new VendorsObject(in);
        }

        @Override
        public VendorsObject[] newArray(int size) {
            return new VendorsObject[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVendor() {
        return vendor;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getServices() {
        return services;
    }

    public void setServices(String services) {
        this.services = services;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getImage1() {
        return image1;
    }

    public void setImage1(String image1) {
        this.image1 = image1;
    }

    public String getImage2() {
        return image2;
    }

    public void setImage2(String image2) {
        this.image2 = image2;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(String entryDate) {
        this.entryDate = entryDate;
    }

    public String getCity_name() {
        return city_name;
    }

    public void setCity_name(String city_name) {
        this.city_name = city_name;
    }
}
