package model;

import service.create_object.ReceiptCreate;
import service.manage.ReceiptManage;
import service.manage.RoomManage;

import java.io.IOException;
import java.text.ParseException;
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
        int roomIndex = RoomManage.getRoomInstance().findIndexById(roomId);
        if (RoomManage.getRoomInstance().getRoomList().get(roomIndex).doCheckIn()) {
            System.out.println("Đã hoàn tất thủ tục check-in. Thời gian: " + java.time.LocalDate.now());
        } else {
            System.err.println("Không thể hoàn tất thủ tục check-in. Phòng đang ở trạng thái: " + RoomManage.getRoomInstance().getRoomList().get(roomIndex).getStatus());
        }
    }

    public void doCheckOutForCustomer(int roomId) throws IOException, ParseException {
        int roomIndex = RoomManage.getRoomInstance().findIndexById(roomId);
        if (RoomManage.getRoomInstance().getRoomList().get(roomIndex).doCheckOut()) {
            System.out.println("Đã hoàn tất thủ tục check-out. Thời gian: " + java.time.LocalDate.now());
            Scanner scanner = new Scanner(System.in);
            String receiptId = ReceiptCreate.createNewReceiptId();
            System.out.print("Nhập tên khách hàng: ");
            String customerName = scanner.nextLine();
            String staffName = this.getFullName();
            String checkInTime = RoomManage.getRoomInstance().getRoomList().get(roomIndex).getLastCheckIn();
            String checkOutTime = RoomManage.getRoomInstance().getRoomList().get(roomIndex).getLastCheckOut();
            Receipt receipt = new Receipt(receiptId, customerName, staffName, checkInTime, checkOutTime, roomId);
            ReceiptManage.getReceiptInstance().add(receipt);
            System.out.println("Đã phát hành hóa đơn. Thông tin hóa đơn:");
            System.out.println();
            System.out.printf("%-15s %-20s %-20s %-15s %-15s %-15s %n", "Số hóa đơn", "Khách hàng", "Nhân viên", "Ngày check-in", "Ngày check-out", "Tổng tiền");
            System.out.println(receipt);
            System.out.println("_____________________________________________________________________________________________________");
            System.out.println();
        } else {
            System.err.println("Không thể hoàn tất thủ tục check-out. Phòng đang ở trạng thái: " + RoomManage.getRoomInstance().getRoomList().get(roomIndex).getStatus());
        }
    }

    public void cleanTheRoom(int roomId) throws IOException {
        int roomIndex = RoomManage.getRoomInstance().findIndexById(roomId);
        if (RoomManage.getRoomInstance().getRoomList().get(roomIndex).cleanTheRoom()) {
            System.out.println("Đã dọn dẹp xong.");
        } else {
            System.err.println("Không thể dọn dẹp. Phòng đang ở trạng thái: " + RoomManage.getRoomInstance().getRoomList().get(roomIndex).getStatus());
        }
    }
}
