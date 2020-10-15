package com.example.destresstreatment.Model;

public class PsychiatristModel {

    private String psychiatrist;
    private String contactNumber;
    private String address;

    public PsychiatristModel(String psychiatrist, String contactNumber, String address) {
        this.psychiatrist = psychiatrist;
        this.contactNumber = contactNumber;
        this.address = address;
    }

    public PsychiatristModel() {
    }

    public String getPsychiatrist() {
        return psychiatrist;
    }

    public void setPsychiatrist(String psychiatrist) {
        this.psychiatrist = psychiatrist;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
