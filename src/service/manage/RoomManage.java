package service.manage;

import model.Receipt;
import model.Room;
import model.User;
import service.create_object.ReceiptCreate;
import service.create_object.UserCreate;
import service.file_IO.RoomFileCsvIO;

import java.io.*;
import java.text.ParseException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;

public class RoomManage {
    private static ArrayList<Room> roomList;

    RoomManage() {
        roomList = new ArrayList<>();
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
        RoomFileCsvIO.writeRoomToFile();
    }

    public void delete(int id) throws IOException {
        roomList.remove(findIndexById(id));
        RoomFileCsvIO.writeRoomToFile();
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
        System.out.println("____________________***  DANH SÁCH TOÀN BỘ PHÒNG  ***_____________________");
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
        System.out.println("________________________***  DANH SÁCH PHÒNG TRỐNG ***________________________");
        System.out.printf("%-10s %-10s %-20s %-15s %-15s %n", "Số phòng", "Giá phòng", "Trạng thái phòng", "Số giường ngủ", "Số nhà VS");
        System.out.println();
        for (Room room : roomList) {
            if (room.getStatus().equals(Room.READY)) {
                System.out.println(room);
            }
        }
        System.out.println("______________________________________________________________________________");
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

    public void doCheckIn(int roomId) throws IOException {
        Room room = roomInstance.getRoomList().get(roomInstance.findIndexById(roomId));
        if (room.getStatus().equals(Room.READY)) {
            room.setStatus(Room.OCCUPIED);
            room.setLastCheckIn(java.time.LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
            RoomFileCsvIO.writeRoomToFile();
            System.out.println("Đã hoàn tất thủ tục check-in. Thời gian: " + java.time.LocalDate.now());
            System.out.println();
        } else {
            System.err.println("Không thể hoàn tất thủ tục check-in. Phòng đang ở trạng thái: " + room.getStatus());
        }
    }

    public void doCheckOut(String username, int roomId) throws IOException, ParseException {
        Room room = roomInstance.getRoomList().get(roomInstance.findIndexById(roomId));
        UserManage userInstance = UserManage.getUserInstance();
        User user = userInstance.getUserList().get(userInstance.findIndexByUsername(username));
        if (room.getStatus().equals(Room.OCCUPIED)) {
            String receiptId = ReceiptCreate.createNewReceiptId();
            System.out.print("Nhập tên khách hàng: ");
            String customerName = UserCreate.createFullName();
            String staffName = user.getFullName();
            String checkInTime = room.getLastCheckIn();
            String checkOutTime = java.time.LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            Receipt receipt = new Receipt(receiptId, customerName, staffName, checkInTime, checkOutTime, roomId);
            ReceiptManage.getReceiptInstance().add(receipt);
            room.setStatus(Room.ON_CHANGE);
            room.setLastCheckOut(checkOutTime);
            RoomFileCsvIO.writeRoomToFile();
            System.out.println("Đã hoàn tất thủ tục check-out. Thời gian: " + java.time.LocalDate.now());
            System.out.println();
            System.out.println("_____________________________________*** Thông tin hóa đơn *** _____________________________________");
            System.out.println();
            System.out.printf("%-15s %-20s %-20s %-15s %-15s %-15s %n", "Số hóa đơn", "Khách hàng", "Nhân viên", "Ngày check-in", "Ngày check-out", "Tổng tiền");
            System.out.println(receipt);
            System.out.println("____________________________________________________________________________________________________");
            System.out.println();
        } else {
            System.err.println("Không thể hoàn tất thủ tục check-out. Phòng đang ở trạng thái: " + room.getStatus());
        }
    }

    public void cleanTheRoom(int roomId) throws IOException {
        RoomManage roomInstance = RoomManage.getRoomInstance();
        Room room = roomInstance.getRoomList().get(roomInstance.findIndexById(roomId));
        if (room.getStatus().equals(Room.ON_CHANGE)) {
            room.setStatus(Room.READY);
            RoomFileCsvIO.writeRoomToFile();
            System.out.println("🌸☆🌸　Đã dọn dẹp phòng xong　🌸☆🌸");
            System.out.println();
        } else {
            System.err.println("Không thể dọn dẹp. Phòng đang ở trạng thái: " + room.getStatus());
        }
    }

}
