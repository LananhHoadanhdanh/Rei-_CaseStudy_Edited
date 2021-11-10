package service.manage;

import model.Room;
import service.file_IO.RoomFileIO;

import java.io.*;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;

public class RoomManage {
    private static ArrayList<Room> roomList;

    RoomManage() {
    }

    private static RoomManage roomInstance;

    public static RoomManage getRoomInstance() {
        if (roomInstance == null) roomInstance = new RoomManage();
        return roomInstance;
    }

    public ArrayList<Room> getRoomList() {
        return roomList;
    }

    public void setRoomList(ArrayList<Room> roomList) {
        RoomManage.roomList = roomList;
    }

    public void add(Room room) throws IOException, ParseException {
        roomList.add(room);
        RoomFileIO.writeRoomToFile();
        RoomFileIO.readRoomFromFile();
    }

    public void delete(int id) throws IOException {
        roomList.remove(findIndexById(id));
        RoomFileIO.writeRoomToFile();
        RoomFileIO.readRoomFromFile();
    }

    public int findIndexById(int id) {
        for (int i = 0; i < roomList.size(); i++) {
            if (roomList.get(i).getRoomID() == id) {
                return i;
            }
        } return -1;
    }

    public void displayListRoom() {
        Collections.sort(roomList);
        System.out.println();
        System.out.println("________________________***  DANH SÁCH PHÒNG  ***________________________");
        System.out.printf("%-10s %-10s %-20s %-15s %-15s %n", "Số phòng", "Giá phòng", "Trạng thái phòng", "Số giường ngủ", "Số nhà VS");
        System.out.println();
        for (Room room : roomList) {
            System.out.println(room);
        }
        System.out.println("_________________________________________________________________________");
        System.out.println();
    }

    public void findRoomByPrice(int minPrice, int maxPrice) {
        System.out.println();
        System.out.println("____________________***  DANH SÁCH PHÒNG PHÙ HỢP  ***____________________");
        System.out.printf("%-10s %-10s %-20s %-15s %-15s %n", "Số phòng", "Giá phòng", "Trạng thái phòng", "Số giường ngủ", "Số nhà VS");
        System.out.println();
        for (Room room : roomList) {
            if (room.getPrice() >= minPrice && room.getPrice() <= maxPrice) {
                System.out.println(room);
            }
        }
        System.out.println("_________________________________________________________________________");
        System.out.println();
    }

    public void displayReadyRoom() {
        System.out.println();
        System.out.println("______________________***  DANH SÁCH PHÒNG TRỐNG ***______________________");
        System.out.printf("%-10s %-10s %-20s %-15s %-15s %n", "Số phòng", "Giá phòng", "Trạng thái phòng", "Số giường ngủ", "Số nhà VS");
        System.out.println();
        for (Room room : roomList) {
            if (room.getStatus().equals(Room.READY)) {
                System.out.println(room);
            }
        }
        System.out.println("_____________________________________________________________________");
        System.out.println();
    }

    public void getInformationById(int id) {
        Room room = RoomManage.getRoomInstance().getRoomList().get(RoomManage.getRoomInstance().findIndexById(id));
        System.out.println();
        System.out.println("______________________***  THÔNG TIN VỀ PHÒNG "+ id +" ***______________________");
        System.out.printf("%-10s %-10s %-20s %-15s %-15s %n", "Số phòng", "Giá phòng", "Trạng thái phòng", "Số giường ngủ", "Số nhà VS");
        System.out.println();
        System.out.println(room);
        System.out.println("__________________________________________________________________________");
        System.out.println();
    }

}
