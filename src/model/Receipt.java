package model;

import service.manage.RoomManage;

import java.io.IOException;
import java.text.ParseException;

public class Receipt implements Comparable<Receipt>{
    private String receiptID;
    private String customerName;
    private String staffName;
    private String checkInTime;
    private String checkOutTime;
    private int roomId;

    public Receipt() {
    }

    public Receipt(String receiptID, String customerName, String staffName, String checkInTime, String checkOutTime, int roomId) {
        this.receiptID = receiptID;
        this.customerName = customerName;
        this.staffName = staffName;
        this.checkInTime = checkInTime;
        this.checkOutTime = checkOutTime;
        this.roomId = roomId;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public String getReceiptID() {
        return receiptID;
    }

    public void setReceiptID(String receiptID) {
        this.receiptID = receiptID;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getStaffName() {
        return staffName;
    }

    public void setStaffName(String staffName) {
        this.staffName = staffName;
    }

    public String getCheckInTime() {
        return checkInTime;
    }

    public void setCheckInTime(String checkInTime) {
        this.checkInTime = checkInTime;
    }

    public String getCheckOutTime() {
        return checkOutTime;
    }

    public void setCheckOutTime(String checkOutTime) {
        this.checkOutTime = checkOutTime;
    }

    public long getTotalPrice() throws ParseException, IOException {
        int roomPrice = RoomManage.getRoomInstance().getRoomList().get(RoomManage.getRoomInstance().findIndexById(roomId)).getPrice();
        long dateCal = DateCalculator.dateCalculator(checkInTime, checkOutTime);
        return roomPrice * (dateCal + 1);
    }

    @Override
    public String toString() {
        String str = null;
        try {
            str = String.format("%-15s %-20s %-20s %-15s %-15s %-15d", receiptID, customerName, staffName, checkInTime, checkOutTime, getTotalPrice());
        } catch (ParseException | IOException e) {
            e.printStackTrace();
        }
        return str;
    }

    @Override
    public int compareTo(Receipt o) {
        if (getReceiptID().compareTo(o.getReceiptID()) > 0) return 1;
        else if (getReceiptID().compareTo(o.getReceiptID()) < 0) return -1;
        return 0;
    }
}
