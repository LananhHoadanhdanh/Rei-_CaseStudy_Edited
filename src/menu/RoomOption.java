package menu;

import model.Room;
import model.User;
import service.create_object.RoomCreate;
import service.manage.ReceiptManage;
import service.manage.RoomManage;
import service.manage.UserManage;

import java.io.IOException;
import java.text.ParseException;

public class RoomOption {
    private static RoomOption roomOption;
    UserManage userInstance = UserManage.getUserInstance();
    RoomManage roomInstance = RoomManage.getRoomInstance();

    private RoomOption() {
    }

    public static RoomOption getRoomOption() {
        if (roomOption == null) roomOption = new RoomOption();
        return roomOption;
    }

    public void roomOptionCheckIn(String username) throws IOException {
        int roomId = RoomCreate.createOldRoomId();
        User user = userInstance.getUserList().get(userInstance.findIndexByUsername(username));
        user.doCheckInForCustomer(roomId);
    }

    public void roomOptionCheckOut(String username) throws IOException, ParseException {
        int roomId = RoomCreate.createOldRoomId();
        User user = userInstance.getUserList().get(userInstance.findIndexByUsername(username));
        user.doCheckOutForCustomer(roomId);
    }

    public void roomOptionClean(String username) throws IOException {
        int roomId = RoomCreate.createOldRoomId();
        User user = userInstance.getUserList().get(userInstance.findIndexByUsername(username));
        user.cleanTheRoom(roomId);
    }

    public void roomOptionAdd() throws IOException, ParseException {
        roomInstance.add(RoomCreate.createRoom());
    }

    public void roomOptionUpdate() throws IOException, ParseException {
        int roomId = RoomCreate.createOldRoomId();
        roomInstance.delete(roomId);
        System.out.println("Nhập thông tin mới: ");
        roomInstance.add(RoomCreate.createRoom());
        System.out.println("🌸☆🌸　Cập nhật phòng thành công　🌸☆🌸");
    }

    public void roomOptionInformationById() {
        int roomId = RoomCreate.createOldRoomId();
        roomInstance.getInformationById(roomId);
    }

    public void roomOptionDeleteById() throws IOException {
        int roomId = RoomCreate.createOldRoomId();
        int index = roomInstance.findIndexById(roomId);
        String status = roomInstance.getRoomList().get(index).getStatus();
        while (status.equals(Room.OCCUPIED)) {
            System.out.println("Không thể xóa phòng. Phòng đang ở trạng thái: " + status);
            roomId = RoomCreate.createOldRoomId();
            index = roomInstance.findIndexById(roomId);
            status = roomInstance.getRoomList().get(index).getStatus();
        }
        roomInstance.delete(roomId);
        System.out.println("🌸☆🌸　Xóa phòng thành công　🌸☆🌸");
        System.out.println();
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
        roomInstance.findRoomByPrice(minPrice, maxPrice);
    }
}
