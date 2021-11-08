package menu;

import model.Account;
import service.manage.ReceiptManage;
import service.manage.RoomManage;

import java.io.IOException;
import java.text.ParseException;

public class ManageOption {

    public static void roomManageOption(String username) throws IOException, ParseException {
        int choice = -1;
        while (choice != 0) {
            ShowMenu.showRoomMenu();
            choice = Account.choiceExceptionHandling();
            RoomOption roomOption = RoomOption.getRoomOption();
            switch (choice) {
                case 1:
                    RoomManage.getRoomInstance().displayListRoom();
                    break;
                case 2:
                    roomOption.roomOptionCheckIn(username);
                    break;
                case 3:
                    roomOption.roomOptionCheckOut(username);
                    break;
                case 4:
                    roomOption.roomOptionClean(username);
                    break;
                case 5:
                    roomOption.roomOptionAdd();
                    break;
                case 6:
                    roomOption.roomOptionUpdate();
                    break;
                case 7:
                    RoomManage.getRoomInstance().displayReadyRoom();
                    break;
                case 8:
                    roomOption.roomOptionInformationById();
                    break;
                case 9:
                    roomOption.roomOptionDeleteById();
                    break;
                case 10:
                    roomOption.roomOptionInformationByPrice();
                    break;
                default:
                    System.out.println("Không có tùy chọn, vui lòng nhập lại!");
            }
        }
    }

    public static void receiptManageOption() throws IOException, ParseException {
        ReceiptOption receiptOption = ReceiptOption.getReceiptOption();
        int choice = -1;
        while (choice != 0) {
            ShowMenu.showReceiptMenu();
            choice = Account.choiceExceptionHandling();
            switch (choice) {
                case 1:
                    ReceiptManage.getReceiptInstance().displayAllReceipt();
                    break;
                case 2:
                    receiptOption.receiptOptionUpdateById();
                    break;
                case 3:
                    receiptOption.receiptOptionDeleteById();
                    break;
                case 4:
                    receiptOption.receiptOptionFindByDay();
                    break;
            }
        }
    }
}
