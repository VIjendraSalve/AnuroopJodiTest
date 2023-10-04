package com.whtech.anuroopjodi.Object;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONObject;

public class SuccessStories implements Parcelable{

    public String id;
    public String memberId;
    public String title;
    public String story;
    public String photo;
    public String approved;
    public String entryDate;
    public String name;

    public SuccessStories() {
    }

    public SuccessStories(JSONObject object, String items_image_path) {
        try {
            this.id = object.getString("id");
            this.memberId = object.getString("memberId");
            this.title = object.getString("title");
            this.story = object.getString("story");
            this.photo = items_image_path + object.getString("photo");
            this.approved = object.getString("approved");
            this.entryDate = object.getString("entryDate");
            this.name = object.getString("name");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected SuccessStories(Parcel in) {
        id = in.readString();
        memberId = in.readString();
        title = in.readString();
        story = in.readString();
        photo = in.readString();
        approved = in.readString();
        entryDate = in.readString();
        name = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(memberId);
        dest.writeString(title);
        dest.writeString(story);
        dest.writeString(photo);
        dest.writeString(approved);
        dest.writeString(entryDate);
        dest.writeString(name);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<SuccessStories> CREATOR = new Creator<SuccessStories>() {
        @Override
        public SuccessStories createFromParcel(Parcel in) {
            return new SuccessStories(in);
        }

        @Override
        public SuccessStories[] newArray(int size) {
            return new SuccessStories[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStory() {
        return story;
    }

    public void setStory(String story) {
        this.story = story;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getApproved() {
        return approved;
    }

    public void setApproved(String approved) {
        this.approved = approved;
    }

    public String getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(String entryDate) {
        this.entryDate = entryDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
