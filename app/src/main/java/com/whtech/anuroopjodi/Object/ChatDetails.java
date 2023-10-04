package com.whtech.anuroopjodi.Object;

import org.json.JSONException;
import org.json.JSONObject;

public class ChatDetails {

    private String id;
    private String sender;
    private String receiver;
    private String message;
    private String delivered;
    private String seen;
    private String messageTime;
    private String toShow;
    private String isDelete;


    public ChatDetails(JSONObject jObj) {

        try {
            this.id= jObj.getString("id");
            this.sender= jObj.getString("sender");
            this.receiver= jObj.getString("receiver");
            this.message= jObj.getString("message");
            this.delivered= jObj.getString("delivered");
            this.seen= jObj.getString("seen");
            this.messageTime= jObj.getString("messageTime");
            this.toShow= jObj.getString("toShow");
            this.isDelete= jObj.getString("isDelete");


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDelivered() {
        return delivered;
    }

    public void setDelivered(String delivered) {
        this.delivered = delivered;
    }

    public String getSeen() {
        return seen;
    }

    public void setSeen(String seen) {
        this.seen = seen;
    }

    public String getMessageTime() {
        return messageTime;
    }

    public void setMessageTime(String messageTime) {
        this.messageTime = messageTime;
    }

    public String getToShow() {
        return toShow;
    }

    public void setToShow(String toShow) {
        this.toShow = toShow;
    }

    public String getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(String isDelete) {
        this.isDelete = isDelete;
    }
}
