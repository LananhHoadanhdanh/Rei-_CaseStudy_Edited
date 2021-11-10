package model;

import service.create_object.ReceiptCreate;
import service.create_object.UserCreate;
import service.file_IO.RoomFileIO;
import service.manage.ReceiptManage;
import service.manage.RoomManage;

import java.io.IOException;
import java.text.ParseException;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class User {
    private String fullName;
    private int age;
    private String phoneNumber;
    private String email;
    private String username;
    private String password;

    public User() {
    }

    public User(String fullName, int age, String phoneNumber, String email, String username, String password) {
        this.fullName = fullName;
        this.age = age;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.username = username;
        this.password = password;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String toString() {
        return String.format("%-20s %-10d %-20s %-25s", fullName, age, phoneNumber, email);
    }

}
