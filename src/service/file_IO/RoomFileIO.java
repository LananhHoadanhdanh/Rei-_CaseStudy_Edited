package service.file_IO;

import model.Room;
import service.manage.ReceiptManage;
import service.manage.RoomManage;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;

public class RoomFileIO {

    public static void writeRoomToFile() throws IOException {
        ArrayList<Room> roomList = RoomManage.getRoomInstance().getRoomList();
        Collections.sort(roomList);
        FileWriter fileWriter = new FileWriter("src/service/file_IO/roomManageFile.csv");
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
        StringBuilder str = new StringBuilder("Số phòng,Giá thuê phòng,Trạng thái hiện tại,Số giường ngủ, Số nhà vệ sinh,Giờ check-in cuối,Giờ check-out cuối");
        for (Room room : roomList) {
            str.append("\n").append(room.getRoomID()).append(",");
            str.append(room.getPrice()).append(",");
            str.append(room.getStatus()).append(",");
            str.append(room.getNumberOfBed()).append(",");
            str.append(room.getNumberOfToilet()).append(",");
            str.append(room.getLastCheckIn()).append(",");
            str.append(room.getLastCheckOut());
        }
        bufferedWriter.write(str.toString());
        bufferedWriter.close();
    }

    public static void readRoomFromFile() throws IOException {
        ArrayList<Room> roomList = new ArrayList<>();
        FileReader fileReader = new FileReader("src/service/file_IO/roomManageFile.csv");
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        String content = bufferedReader.readLine();
        while ((content = bufferedReader.readLine()) != null) {
            String[] array = content.split(",");
            int roomID = Integer.parseInt(array[0]);
            int price = Integer.parseInt(array[1]);
            String status = array[2];
            int numberOfBed = Integer.parseInt(array[3]);
            int numberOfToilet = Integer.parseInt(array[4]);
            String lastCheckIn = array[5];
            String lastCheckOut = array[6];
            roomList.add(new Room(roomID, price, status, numberOfBed, numberOfToilet, lastCheckIn, lastCheckOut));
        }
        RoomManage.getRoomInstance().setRoomList(roomList);
        bufferedReader.close();
        fileReader.close();
    }
}
