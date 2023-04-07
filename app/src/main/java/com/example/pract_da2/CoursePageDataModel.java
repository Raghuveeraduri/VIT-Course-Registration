package com.example.pract_da2;

public class CoursePageDataModel {
    String code,title,slot;
    CoursePageDataModel(){}
    public CoursePageDataModel(String code, String title, String slot) {
        this.code = code;
        this.title = title;
        this.slot = slot;
    }

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
}
