package model;

import java.time.format.DateTimeFormatter;

public class Room implements Comparable<Room>{
    public static final String OCCUPIED = "Đang có khách thuê";
    public static final String ON_CHANGE = "Đang dọn dẹp";
    public static final String READY = "Sẵn sàng";

    private int roomID;
    private int price;
    private String status;
    private int numberOfBed;
    private int numberOfToilet;
    private String lastCheckIn;
    private String lastCheckOut;

    public Room() {
    }

    public Room(int roomID, int price, String status, int numberOfBed, int numberOfToilet) {
        this.roomID = roomID;
        this.price = price;
        this.status = status;
        this.numberOfBed = numberOfBed;
        this.numberOfToilet = numberOfToilet;
    }

    public Room(int roomID, int price, String status, int numberOfBed, int numberOfToilet, String lastCheckIn, String lastCheckOut) {
        this.roomID = roomID;
        this.price = price;
        this.status = status;
        this.numberOfBed = numberOfBed;
        this.numberOfToilet = numberOfToilet;
        this.lastCheckIn = lastCheckIn;
        this.lastCheckOut = lastCheckOut;
    }

    public int getRoomID() {
        return roomID;
    }

    public void setRoomID(int roomID) {
        this.roomID = roomID;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getNumberOfBed() {
        return numberOfBed;
    }

    public void setNumberOfBed(int numberOfBed) {
        this.numberOfBed = numberOfBed;
    }

    public int getNumberOfToilet() {
        return numberOfToilet;
    }

    public void setNumberOfToilet(int numberOfToilet) {
        this.numberOfToilet = numberOfToilet;
    }

    public String getLastCheckIn() {
        return lastCheckIn;
    }

    public void setLastCheckIn(String lastCheckIn) {
        this.lastCheckIn = lastCheckIn;
    }

    public String getLastCheckOut() {
        return lastCheckOut;
    }

    public void setLastCheckOut(String lastCheckOut) {
        this.lastCheckOut = lastCheckOut;
    }

    @Override
    public String toString() {
        return String.format("%-10d %-10d %-20s %-15d %-15d", roomID, price, status, numberOfBed, numberOfToilet);
    }

    public boolean doCheckIn() {
        if (this.getStatus().equals(Room.READY)) {
            this.setStatus(Room.OCCUPIED);
            this.setLastCheckIn(java.time.LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
            return true;
        } return false;
    }

    public boolean doCheckOut() {
        if (this.getStatus().equals(Room.OCCUPIED)) {
            this.setStatus(Room.ON_CHANGE);
            this.setLastCheckOut(java.time.LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
            return true;
        } return false;
    }

    public boolean cleanTheRoom() {
        if (this.getStatus().equals(Room.ON_CHANGE)) {
            this.setStatus(Room.READY);
            return true;
        } return false;
    }

    @Override
    public int compareTo(Room o) {
        if (getRoomID() - o.getRoomID() > 0) return 1;
        else if (getRoomID() - o.getRoomID() < 0) return -1;
        return 0;
    }
}
