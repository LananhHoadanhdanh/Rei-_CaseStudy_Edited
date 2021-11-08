package service.manage;

import model.Room;

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

    public void add(Room room) throws IOException, ParseException {
        roomList.add(room);
        writeRoomToFile();
        readRoomFromFile();
    }

    public void delete(int id) throws IOException {
        roomList.remove(findIndexById(id));
        writeRoomToFile();
        readRoomFromFile();
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

    public static void writeRoomToFile() throws IOException{
        Collections.sort(roomList);
        FileWriter fileWriter = new FileWriter("src/service/roomManageFile.csv");
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
        StringBuilder str = new StringBuilder("Số phòng,Giá thuê phòng,Trạng thái hiện tại,Số giường ngủ, Số nhà vệ sinh");
        for (Room room : roomList) {
            str.append("\n").append(room.getRoomID()).append(",");
            str.append(room.getPrice()).append(",");
            str.append(room.getStatus()).append(",");
            str.append(room.getNumberOfBed()).append(",");
            str.append(room.getNumberOfToilet());
        }
        bufferedWriter.write(str.toString());
        bufferedWriter.close();
    }

    public static void readRoomFromFile() throws IOException {
        roomList = new ArrayList<>();
        FileReader fileReader = new FileReader("src/service/roomManageFile.csv");
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        String content = bufferedReader.readLine();
        while ((content = bufferedReader.readLine()) != null) {
            String[] array = content.split(",");
            int roomID = Integer.parseInt(array[0]);
            int price = Integer.parseInt(array[1]);
            String status = array[2];
            int numberOfBed = Integer.parseInt(array[3]);
            int numberOfToilet = Integer.parseInt(array[4]);
            roomList.add(new Room(roomID, price, status, numberOfBed, numberOfToilet));
        }
        bufferedReader.close();
        fileReader.close();
    }
}
