package menu;

import model.Room;
import model.User;
import service.create_object.RoomCreate;
import service.manage.RoomManage;
import service.manage.UserManage;

import java.io.IOException;
import java.text.ParseException;

public class RoomOption {
    private static RoomOption roomOption;

    private RoomOption() {
    }

    public static RoomOption getRoomOption() {
        if (roomOption == null) roomOption = new RoomOption();
        return roomOption;
    }

    public void roomOptionCheckIn(String username) throws IOException {
        int roomId = RoomCreate.createOldRoomId();
        User user = UserManage.getUserInstance().getUserList().get(UserManage.getUserInstance().findIndexByUsername(username));
        user.doCheckInForCustomer(roomId);
    }

    public void roomOptionCheckOut(String username) throws IOException, ParseException {
        int roomId = RoomCreate.createOldRoomId();
        User user = UserManage.getUserInstance().getUserList().get(UserManage.getUserInstance().findIndexByUsername(username));
        user.doCheckOutForCustomer(roomId);
    }

    public void roomOptionClean(String username) throws IOException {
        int roomId = RoomCreate.createOldRoomId();
        User user = UserManage.getUserInstance().getUserList().get(UserManage.getUserInstance().findIndexByUsername(username));
        user.cleanTheRoom(roomId);
    }

    public void roomOptionAdd() throws IOException, ParseException {
        RoomManage.getRoomInstance().add(RoomCreate.createRoom());
    }

    public void roomOptionUpdate() throws IOException, ParseException {
        int roomId = RoomCreate.createOldRoomId();
        RoomManage.getRoomInstance().delete(roomId);
        System.out.println("Nhập thông tin mới: ");
        RoomManage.getRoomInstance().add(RoomCreate.createRoom());
        System.out.println("Cập nhật thành công!!!");
    }

    public void roomOptionInformationById() {
        int roomId = RoomCreate.createOldRoomId();
        RoomManage.getRoomInstance().getInformationById(roomId);
    }

    public void roomOptionDeleteById() throws IOException {
        int roomId = RoomCreate.createOldRoomId();
        int index = RoomManage.getRoomInstance().findIndexById(roomId);
        String status = RoomManage.getRoomInstance().getRoomList().get(index).getStatus();
        while (status.equals(Room.OCCUPIED)) {
            System.out.println("Không thể xóa phòng. Phòng đang ở trạng thái: " + status);
            roomId = RoomCreate.createOldRoomId();
            index = RoomManage.getRoomInstance().findIndexById(roomId);
            status = RoomManage.getRoomInstance().getRoomList().get(index).getStatus();
        }
        RoomManage.getRoomInstance().delete(roomId);
        System.out.println("Đã xóa thành công!");
    }

    public void roomOptionInformationByPrice() {
        System.out.println("Nhập giá tiền nhỏ nhất: ");
        int minPrice = RoomCreate.createPrice();
        System.out.println("Nhập giá tiền lớn nhất: ");
        int maxPrice = RoomCreate.createPrice();
        while (maxPrice < minPrice) {
            System.err.println("Vui lòng nhập giá tiền lớn hơn hoặc bằng giá tiền nhỏ nhất");
            maxPrice = RoomCreate.createPrice();
        }
        RoomManage.getRoomInstance().findRoomByPrice(minPrice, maxPrice);
    }
}
