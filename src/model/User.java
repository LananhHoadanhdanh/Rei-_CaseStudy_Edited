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

    public void doCheckInForCustomer(int roomId) throws IOException {
        RoomManage roomInstance = RoomManage.getRoomInstance();
        Room room = roomInstance.getRoomList().get(roomInstance.findIndexById(roomId));
        if (room.doCheckIn()) {
            System.out.println("Đã hoàn tất thủ tục check-in. Thời gian: " + java.time.LocalDate.now());
        } else {
            System.err.println("Không thể hoàn tất thủ tục check-in. Phòng đang ở trạng thái: " + room.getStatus());
        }
    }

    public void doCheckOutForCustomer(int roomId) throws IOException, ParseException {
        RoomManage roomInstance = RoomManage.getRoomInstance();
        Room room = roomInstance.getRoomList().get(roomInstance.findIndexById(roomId));
        if (room.getStatus().equals(Room.OCCUPIED)) {

            String receiptId = ReceiptCreate.createNewReceiptId();
            System.out.print("Nhập tên khách hàng: ");
            String customerName = UserCreate.createFullName();
            String staffName = this.getFullName();
            String checkInTime = room.getLastCheckIn();
            String checkOutTime = java.time.LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            Receipt receipt = new Receipt(receiptId, customerName, staffName, checkInTime, checkOutTime, roomId);
            ReceiptManage.getReceiptInstance().add(receipt);

            room.setStatus(Room.ON_CHANGE);
            room.setLastCheckOut(checkOutTime);
            RoomFileIO.writeRoomToFile();
            RoomFileIO.readRoomFromFile();

            System.out.println("Đã hoàn tất thủ tục check-out. Thời gian: " + java.time.LocalDate.now());
            System.out.println("_______________*** Thông tin hóa đơn *** _______________");
            System.out.println();
            System.out.printf("%-15s %-20s %-20s %-15s %-15s %-15s %n", "Số hóa đơn", "Khách hàng", "Nhân viên", "Ngày check-in", "Ngày check-out", "Tổng tiền");
            System.out.println(receipt);
            System.out.println("_____________________________________________________________________________________________________");
            System.out.println();
        } else {
            System.err.println("Không thể hoàn tất thủ tục check-out. Phòng đang ở trạng thái: " + room.getStatus());
        }
    }

    public void cleanTheRoom(int roomId) throws IOException {
        RoomManage roomInstance = RoomManage.getRoomInstance();
        Room room = roomInstance.getRoomList().get(roomInstance.findIndexById(roomId));
        if (room.cleanTheRoom()) {
            System.out.println("Đã dọn dẹp xong.");
        } else {
            System.err.println("Không thể dọn dẹp. Phòng đang ở trạng thái: " + room.getStatus());
        }
    }
}
