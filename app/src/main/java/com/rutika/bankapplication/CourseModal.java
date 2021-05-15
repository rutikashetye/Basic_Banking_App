package com.rutika.bankapplication;

public class CourseModal {

    // variables for our course
    // name and description.
    private String cusName;
    private String cusAcc;
    private String balance;

    // creating constructor for our variables.
    public CourseModal(String courseName, String courseDescription,String balance) {
        this.cusName = courseName;
        this.cusAcc = courseDescription;
        this.balance = balance;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    // creating getter and setter methods.
    public String getCourseName() {
        return cusName;
    }

    public void setCourseName(String courseName) {
        this.cusName = courseName;
    }

    public String getCourseDescription() {
        return cusAcc;
    }

    public void setCourseDescription(String courseDescription) {
        this.cusAcc = courseDescription;
    }
}
