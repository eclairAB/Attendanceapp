package com.example.benedict.attendanceapp.Adapter;

public class StudentList {

    boolean isSelected;
    String studentName;

    public StudentList(boolean isSelected, String studentName) {
        this.isSelected = isSelected;
        this.studentName = studentName;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public String getstudentName() {
        return studentName;
    }

    public void setstudentName(String studentName) {
        this.studentName = studentName;
    }
}
