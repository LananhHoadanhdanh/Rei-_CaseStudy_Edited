package menu;

import model.Room;
import model.User;
import service.create_object.RoomCreate;
import service.manage.RoomManage;
import service.manage.UserManage;

import java.io.IOException;
import java.text.ParseException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class RoomOption {
    private static RoomOption roomOption;

    private RoomOption() {
    }

    public static RoomOption getRoomOption() {
        if (roomOption == null) roomOption = new RoomOption();
        return roomOption;
    }

    public void roomOptionCheckIn(String username) throws IOException {
        int roomId = 0;
        System.out.print("Nhập số phòng: ");
        while (RoomManage.getRoomInstance().findIndexById(roomId) == -1){
            Scanner sc = new Scanner(System.in);
            try {
                roomId = sc.nextInt();
                if (RoomManage.getRoomInstance().findIndexById(roomId) == -1) {
                    System.err.println("Phòng không tồn tại. Vui lòng nhập lại.");
                }
            } catch (InputMismatchException e){
                System.err.println("Số phòng không hợp lệ. Vui lòng nhập lại");
            }
        }
        User user = UserManage.getUserInstance().getUserList().get(UserManage.getUserInstance().findIndexByUsername(username));
        user.doCheckInForCustomer(roomId);
    }

    public void roomOptionCheckOut(String username) throws IOException, ParseException {
        int roomId = 0;
        System.out.print("Nhập số phòng: ");
        while (RoomManage.getRoomInstance().findIndexById(roomId) == -1){
            Scanner sc = new Scanner(System.in);
            try {
                roomId = sc.nextInt();
                if (RoomManage.getRoomInstance().findIndexById(roomId) == -1) {
                    System.err.println("Phòng không tồn tại. Vui lòng nhập lại.");
                }
            } catch (InputMismatchException e){
                System.err.println("Số phòng không hợp lệ. Vui lòng nhập lại");
            }
        }
        User user = UserManage.getUserInstance().getUserList().get(UserManage.getUserInstance().findIndexByUsername(username));
        user.doCheckOutForCustomer(roomId);
    }

    public void roomOptionClean(String username) throws IOException {
        int roomId = 0;
        System.out.print("Nhập số phòng: ");
        while (RoomManage.getRoomInstance().findIndexById(roomId) == -1){
            Scanner sc = new Scanner(System.in);
            try {
                roomId = sc.nextInt();
                if (RoomManage.getRoomInstance().findIndexById(roomId) == -1) {
                    System.err.println("Phòng không tồn tại. Vui lòng nhập lại.");
                }
            } catch (InputMismatchException e){
                System.err.println("Số phòng không hợp lệ. Vui lòng nhập lại");
            }
        }
        User user = UserManage.getUserInstance().getUserList().get(UserManage.getUserInstance().findIndexByUsername(username));
        user.cleanTheRoom(roomId);
    }

    public void roomOptionAdd() throws IOException, ParseException {
        RoomManage.getRoomInstance().add(RoomCreate.createRoom());
    }

    public void roomOptionUpdate() throws IOException, ParseException {
        int roomId = 0;
        System.out.print("Nhập số phòng muốn sửa: ");
        while (RoomManage.getRoomInstance().findIndexById(roomId) == -1){
            Scanner sc = new Scanner(System.in);
            try {
                roomId = sc.nextInt();
                if (RoomManage.getRoomInstance().findIndexById(roomId) == -1) {
                    System.err.println("Phòng không tồn tại. Vui lòng nhập lại.");
                }
            } catch (InputMismatchException e){
                System.err.println("Số phòng không hợp lệ. Vui lòng nhập lại");
            }
        }
        RoomManage.getRoomInstance().delete(roomId);
        System.out.println("Nhập thông tin mới: ");
        RoomManage.getRoomInstance().add(RoomCreate.createRoom());
        System.out.println("Cập nhật thành công!!!");
    }

    public void roomOptionInformationById() throws IOException {
        int roomId = 0;
        System.out.print("Nhập số phòng: ");
        while (RoomManage.getRoomInstance().findIndexById(roomId) == -1){
            Scanner sc = new Scanner(System.in);
            try {
                roomId = sc.nextInt();
                if (RoomManage.getRoomInstance().findIndexById(roomId) == -1) {
                    System.err.println("Phòng không tồn tại. Vui lòng nhập lại.");
                }
            } catch (InputMismatchException e){
                System.err.println("Số phòng không hợp lệ. Vui lòng nhập lại");
            }
        }
        RoomManage.getRoomInstance().getInformationById(roomId);
    }

    public void roomOptionDeleteById() throws IOException, ParseException {
        int roomId = 0;
        String status = RoomManage.getRoomInstance().getRoomList().get(RoomManage.getRoomInstance().findIndexById(roomId)).getStatus();
        System.out.print("Nhập số phòng: ");
        while (RoomManage.getRoomInstance().findIndexById(roomId) == -1 || status.equals(Room.READY)){
            Scanner sc = new Scanner(System.in);
            try {
                roomId = sc.nextInt();
                status = RoomManage.getRoomInstance().getRoomList().get(RoomManage.getRoomInstance().findIndexById(roomId)).getStatus();
                if (RoomManage.getRoomInstance().findIndexById(roomId) == -1) {
                    System.err.println("Phòng không tồn tại. Vui lòng nhập lại.");
                }
                if (status.equals(Room.READY)) {
                    System.err.println("Không thể xóa phòng. Phòng đang ở trạng thái: " + status);
                }
            } catch (InputMismatchException e){
                System.err.println("Số phòng không hợp lệ. Vui lòng nhập lại");
            }
        }
        RoomManage.getRoomInstance().delete(roomId);
    }

    public void roomOptionInformationByPrice() {
        System.out.println("Nhập giá tiền nhỏ nhất: ");
        int minPrice = -1;
        while (minPrice < 0){
            Scanner sc = new Scanner(System.in);
            try {
                minPrice = sc.nextInt();
                if (minPrice < 0) {
                    System.err.println("Số tiền không hợp lệ. Vui lòng nhập lại.");
                }
            } catch (InputMismatchException e){
                System.err.println("Dữ liệu nhập vào không hợp lệ. Vui lòng nhập lại");
            }
        }

        System.out.println("Nhập giá tiền lớn nhất: ");
        int maxPrice = -1;
        while (maxPrice < 0 || maxPrice < minPrice){
            Scanner sc = new Scanner(System.in);
            try {
                maxPrice = sc.nextInt();
                if (maxPrice < 0) {
                    System.err.println("Số tiền không hợp lệ. Vui lòng nhập lại.");
                }
                if (maxPrice < minPrice) {
                    System.err.println("Vui lòng nhập giá tiền lớn hơn hoặc bằng giá tiền nhỏ nhất");
                }
            } catch (InputMismatchException e){
                System.err.println("Dữ liệu nhập vào không hợp lệ. Vui lòng nhập lại");
            }
        }
        RoomManage.getRoomInstance().findRoomByPrice(minPrice, maxPrice);
    }
}
