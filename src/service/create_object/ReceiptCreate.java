package service.create_object;

import model.DateCalculator;
import model.Receipt;
import model.Validation;
import service.manage.ReceiptManage;
import service.manage.RoomManage;
import service.manage.UserManage;

import java.io.IOException;
import java.text.ParseException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class ReceiptCreate {
    public static String createNewReceiptId() {
        System.out.print("Nhập số hóa đơn: ");
        Scanner scanner = new Scanner(System.in);
        String receiptId = scanner.nextLine();
        while (ReceiptManage.getReceiptInstance().findIndexById(receiptId) != -1) {
            System.out.println("Số hóa đơn đã tồn tại, vui lòng nhập lại");
            receiptId = scanner.nextLine();
        }
        return receiptId;
    }

    public static String createOldReceiptId() {
        System.out.print("Nhập số hóa đơn: ");
        Scanner scanner = new Scanner(System.in);
        String receiptId = scanner.nextLine();
        while (ReceiptManage.getReceiptInstance().findIndexById(receiptId) == -1) {
            System.out.println("Số hóa đơn không tồn tại, vui lòng nhập lại");
            receiptId = scanner.nextLine();
        }
        return receiptId;
    }

    public static String createNewDate() {
        Scanner scanner = new Scanner(System.in);
        String date = scanner.nextLine();
        while (!Validation.validateString(date, Validation.DATE_REGEX)) {
            System.out.println("Ngày không hợp lệ. Vui lòng nhập lại.");
            date = scanner.nextLine();
        }
        return date;
    }

    public static Receipt createReceipt() throws ParseException, IOException {
        Scanner scanner = new Scanner(System.in);

        String receiptId = createNewReceiptId();

        System.out.print("Nhập tên khách hàng: ");
        String customerName = scanner.nextLine();

        System.out.print("Nhập tên nhân viên: ");
        String staffName = scanner.nextLine();

        System.out.println("Nhập ngày check-in (định dạng dd/MM/yyyy): ");
        String checkInTime = createNewDate();

        System.out.println("Nhập ngày check-out (định dạng dd/MM/yyyy): ");
        String checkOutTime = createNewDate();
        while (DateCalculator.dateCompare(checkInTime, checkOutTime) > 0) {
            if (DateCalculator.dateCompare(checkInTime, checkOutTime) > 0) {
                System.err.println("Ngày check-out phải sau ngày check-in!");
            }
            checkOutTime = createNewDate();
        }

        int roomId = RoomCreate.createOldRoomId();
        return new Receipt(receiptId, customerName, staffName, checkInTime, checkOutTime, roomId);
    }
}
