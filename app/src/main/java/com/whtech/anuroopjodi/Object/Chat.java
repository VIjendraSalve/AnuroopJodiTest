package com.whtech.anuroopjodi.Object;

import org.json.JSONException;
import org.json.JSONObject;

public class Chat {

    private String userID;
    private String sender_name;
    private String sender_id;
    private String receiver_name;
    private String receiver_id;
    private String sender_mobile_no;
    private String receiver_mobile_no;
    private String senderProfile;
    private String senderemailId;
    private String senderState;
    private String senderCity;
    private String senderGender;
    private String receiverProfile;
    private String receiveremailId;
    private String receiverState;
    private String receiverCity;
    private String receiverGender;
    private String is_block;

    public Chat(JSONObject jObj) {

        try {
            this.userID= jObj.getString("userID");
            this.sender_name= jObj.getString("sender_name");
            this.sender_id= jObj.getString("sender_id");
            this.receiver_name= jObj.getString("receiver_name");
            this.receiver_id= jObj.getString("receiver_id");
            this.sender_mobile_no= jObj.getString("sender_mobile_no");
            this.receiver_mobile_no= jObj.getString("receiver_mobile_no");
            this.senderProfile= jObj.getString("senderProfile");
            this.senderemailId= jObj.getString("senderemailId");
            this.senderState= jObj.getString("senderState");
            this.senderCity= jObj.getString("senderCity");
            this.senderGender= jObj.getString("senderGender");
            this.receiverProfile= jObj.getString("receiverProfile");
            this.receiveremailId= jObj.getString("receiveremailId");
            this.receiverState= jObj.getString("receiverState");
            this.receiverCity= jObj.getString("receiverCity");
            this.receiverGender= jObj.getString("receiverGender");
            this.is_block= jObj.getString("is_block");

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getSender_name() {
        return sender_name;
    }

    public void setSender_name(String sender_name) {
        this.sender_name = sender_name;
    }

    public String getSender_id() {
        return sender_id;
    }

    public void setSender_id(String sender_id) {
        this.sender_id = sender_id;
    }

    public String getReceiver_name() {
        return receiver_name;
    }

    public void setReceiver_name(String receiver_name) {
        this.receiver_name = receiver_name;
    }

    public String getReceiver_id() {
        return receiver_id;
    }

    public void setReceiver_id(String receiver_id) {
        this.receiver_id = receiver_id;
    }

    public String getSender_mobile_no() {
        return sender_mobile_no;
    }

    public void setSender_mobile_no(String sender_mobile_no) {
        this.sender_mobile_no = sender_mobile_no;
    }

    public String getReceiver_mobile_no() {
        return receiver_mobile_no;
    }

    public void setReceiver_mobile_no(String receiver_mobile_no) {
        this.receiver_mobile_no = receiver_mobile_no;
    }

    public String getSenderProfile() {
        return senderProfile;
    }

    public void setSenderProfile(String senderProfile) {
        this.senderProfile = senderProfile;
    }

    public String getSenderemailId() {
        return senderemailId;
    }

    public void setSenderemailId(String senderemailId) {
        this.senderemailId = senderemailId;
    }

    public String getSenderState() {
        return senderState;
    }

    public void setSenderState(String senderState) {
        this.senderState = senderState;
    }

    public String getSenderCity() {
        return senderCity;
    }

    public void setSenderCity(String senderCity) {
        this.senderCity = senderCity;
    }

    public String getSenderGender() {
        return senderGender;
    }

    public void setSenderGender(String senderGender) {
        this.senderGender = senderGender;
    }

    public String getReceiverProfile() {
        return receiverProfile;
    }

    public void setReceiverProfile(String receiverProfile) {
        this.receiverProfile = receiverProfile;
    }

    public String getReceiveremailId() {
        return receiveremailId;
    }

    public void setReceiveremailId(String receiveremailId) {
        this.receiveremailId = receiveremailId;
    }

    public String getReceiverState() {
        return receiverState;
    }

    public void setReceiverState(String receiverState) {
        this.receiverState = receiverState;
    }

    public String getReceiverCity() {
        return receiverCity;
    }

    public void setReceiverCity(String receiverCity) {
        this.receiverCity = receiverCity;
    }

    public String getReceiverGender() {
        return receiverGender;
    }

    public void setReceiverGender(String receiverGender) {
        this.receiverGender = receiverGender;
    }

    public String getIs_block() {
        return is_block;
    }

    public void setIs_block(String is_block) {
        this.is_block = is_block;
    }
}
