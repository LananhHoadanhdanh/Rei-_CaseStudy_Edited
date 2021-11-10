package service.create_object;

import model.Room;
import service.manage.RoomManage;

import java.time.format.DateTimeFormatter;
import java.util.InputMismatchException;
import java.util.Scanner;

public class RoomCreate {
    public static final Scanner SCANNER = new Scanner(System.in);

    public static int createOldRoomId() {
        System.out.print("Nhập số phòng: ");
        int roomId = -1;
        while (RoomManage.getRoomInstance().findIndexById(roomId) == -1 || roomId < 0) {
            try {
                roomId = SCANNER.nextInt();
                if (RoomManage.getRoomInstance().findIndexById(roomId) == -1) {
                    System.err.println("Phòng không tồn tại. Vui lòng nhập lại.");
                }
                if (roomId < 0) {
                    System.err.println("Số phòng không hợp lệ. Vui lòng nhập lại.");
                }
            } catch (InputMismatchException e){
                System.err.println("Dữ liệu nhập vào không hợp lệ. Vui lòng nhập lại");
            } finally {
                SCANNER.nextLine();
            }
        }
        return roomId;
    }

    public static int createNewRoomId() {
        System.out.print("Nhập số phòng: ");
        int roomId = -1;
        while (RoomManage.getRoomInstance().findIndexById(roomId) != -1 || roomId < 0) {
            try {
                roomId = SCANNER.nextInt();
                if (RoomManage.getRoomInstance().findIndexById(roomId) != -1) {
                    System.err.println("Phòng đã tồn tại. Vui lòng nhập lại.");
                }
                if (roomId < 0) {
                    System.err.println("Số phòng không hợp lệ. Vui lòng nhập lại.");
                }
            } catch (InputMismatchException e){
                System.err.println("Dữ liệu nhập vào không hợp lệ. Vui lòng nhập lại");
            } finally {
                SCANNER.nextLine();
            }
        }
        return roomId;
    }

    public static int createPrice() {
        int price = -1;
        while (price < 0){
            try {
                price = SCANNER.nextInt();
                if (price < 0) {
                    System.err.println("Số tiền không hợp lệ. Vui lòng nhập lại.");
                }
            } catch (InputMismatchException e){
                System.err.println("Dữ liệu nhập vào không hợp lệ. Vui lòng nhập lại");
            } finally {
                SCANNER.nextLine();
            }
        }
        return price;
    }

    public static int createNumberOfBed() {
        System.out.print("Nhập số giường ngủ: ");
        int numberOfBed = -1;
        while (numberOfBed < 0){
            try {
                numberOfBed = SCANNER.nextInt();
                if (numberOfBed < 0) {
                    System.err.println("Số không hợp lệ. Vui lòng nhập lại.");
                }
            } catch (InputMismatchException e){
                System.err.println("Dữ liệu nhập vào không hợp lệ. Vui lòng nhập lại");
            } finally {
                SCANNER.nextLine();
            }
        }
        return numberOfBed;
    }

    public static int createNumberOfToilet() {
        System.out.print("Nhập số nhà vệ sinh: ");
        int numberOfToilet = -1;
        while (numberOfToilet < 0){
            try {
                numberOfToilet = SCANNER.nextInt();
                if (numberOfToilet < 0) {
                    System.err.println("Số không hợp lệ. Vui lòng nhập lại.");
                }
            } catch (InputMismatchException e){
                System.err.println("Dữ liệu nhập vào không hợp lệ. Vui lòng nhập lại");
            } finally {
                SCANNER.nextLine();
            }
        }
        return numberOfToilet;
    }

    public static Room createRoom() {
        int roomId = createNewRoomId();
        System.out.print("Nhập giá thuê phòng: ");
        int price = createPrice();
        int numberOfBed = createNumberOfBed();
        int numberOfToilet = createNumberOfToilet();
        String lastCheckIn = java.time.LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        String lastCheckOut = java.time.LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        return new Room(roomId, price, Room.READY, numberOfBed, numberOfToilet, lastCheckIn, lastCheckOut);
    }
}
