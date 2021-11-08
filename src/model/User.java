package model;

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
        if (RoomManage.getRoomInstance().getRoomList().get(RoomManage.getRoomInstance().findIndexById(roomId)).doCheckIn()) {
            System.out.println("Đã hoàn tất thủ tục check-in. Thời gian: " + java.time.LocalDate.now());
        } else {
            System.err.println("Không thể hoàn tất thủ tục check-in. Phòng đang ở trạng thái: " + RoomManage.getRoomInstance().getRoomList().get(RoomManage.getRoomInstance().findIndexById(roomId)).getStatus());
        }
    }

    public void doCheckOutForCustomer(int roomId) throws IOException, ParseException {
        if (RoomManage.getRoomInstance().getRoomList().get(RoomManage.getRoomInstance().findIndexById(roomId)).doCheckOut()) {
            System.out.println("Đã hoàn tất thủ tục check-out. Thời gian: " + java.time.LocalDate.now());

            Scanner scanner = new Scanner(System.in);
            System.out.print("Nhập số hóa đơn: ");
            String receiptId = scanner.nextLine();
            //ReceiptManage.getReceiptInstance().getReceiptList();
            while (ReceiptManage.getReceiptInstance().findIndexById(receiptId) != -1) {
                System.out.println("Số hóa đơn đã tồn tại, vui lòng nhập lại");
                receiptId = scanner.nextLine();
            }
            System.out.print("Nhập tên khách hàng: ");
            String customerName = scanner.nextLine();
            String staffName = this.getFullName();
            String checkInTime = RoomManage.getRoomInstance().getRoomList().get(RoomManage.getRoomInstance().findIndexById(roomId)).getLastCheckIn();
            String checkOutTime = RoomManage.getRoomInstance().getRoomList().get(RoomManage.getRoomInstance().findIndexById(roomId)).getLastCheckOut();
            //ReceiptManage.getReceiptInstance().getReceiptList();
            Receipt receipt = new Receipt(receiptId, customerName, staffName, checkInTime, checkOutTime, roomId);
            ReceiptManage.getReceiptInstance().add(receipt);

        } else {
            System.err.println("Không thể hoàn tất thủ tục check-out. Phòng đang ở trạng thái: " + RoomManage.getRoomInstance().getRoomList().get(RoomManage.getRoomInstance().findIndexById(roomId)).getStatus());
        }
    }

    public void cleanTheRoom(int roomId) throws IOException {
        if (RoomManage.getRoomInstance().getRoomList().get(RoomManage.getRoomInstance().findIndexById(roomId)).cleanTheRoom()) {
            System.out.println("Đã dọn dẹp xong.");
        } else {
            System.err.println("Không thể dọn dẹp. Phòng đang ở trạng thái: " + RoomManage.getRoomInstance().getRoomList().get(RoomManage.getRoomInstance().findIndexById(roomId)).getStatus());
        }
    }
}
