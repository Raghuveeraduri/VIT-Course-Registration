package com.example.pract_da2;
public class RegisteredDataModel {
    String code,title,slot;

    RegisteredDataModel(){}

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSlot() {
        return slot;
    }

    public void setSlot(String slot) {
        this.slot = slot;
    }

    public RegisteredDataModel(String code, String title, String slot) {
        this.code = code;
        this.title = title;
        this.slot = slot;
    }
}
