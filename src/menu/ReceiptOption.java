package menu;

import model.DateCalculator;
import model.Validation;
import service.manage.ReceiptManage;

import java.io.IOException;
import java.text.ParseException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class ReceiptOption {

    private static ReceiptOption receiptOption;

    private ReceiptOption() {
    }

    public static ReceiptOption getReceiptOption() {
        if (receiptOption == null) receiptOption = new ReceiptOption();
        return receiptOption;
    }

    public void receiptOptionUpdateById() throws ParseException, IOException {
        String receiptId = null;
        System.out.print("Nhập số hóa đơn muốn sửa: ");
        while (ReceiptManage.getReceiptInstance().findIndexById(receiptId) == -1){
            Scanner sc = new Scanner(System.in);
            try {
                receiptId = sc.nextLine();
                if (ReceiptManage.getReceiptInstance().findIndexById(receiptId) == -1) {
                    System.err.println("Phòng không tồn tại. Vui lòng nhập lại.");
                }
            } catch (InputMismatchException e){
                System.err.println("Số phòng không hợp lệ. Vui lòng nhập lại");
            }
        }
        ReceiptManage.getReceiptInstance().delete(receiptId);
        ReceiptManage.getReceiptInstance().add(ReceiptManage.createReceipt());
    }

    public void receiptOptionDeleteById() throws IOException, ParseException {
        String receiptId = null;
        System.out.print("Nhập số hóa đơn muốn xóa: ");
        while (ReceiptManage.getReceiptInstance().findIndexById(receiptId) == -1){
            Scanner sc = new Scanner(System.in);
            try {
                receiptId = sc.nextLine();
                if (ReceiptManage.getReceiptInstance().findIndexById(receiptId) == -1) {
                    System.err.println("Phòng không tồn tại. Vui lòng nhập lại.");
                }
            } catch (InputMismatchException e){
                System.err.println("Số phòng không hợp lệ. Vui lòng nhập lại");
            }
        }
        ReceiptManage.getReceiptInstance().delete(receiptId);
        System.out.println("Đã xóa thành công!");
    }

    public void receiptOptionFindByDay() throws ParseException, IOException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Nhập ngày bắt đầu (định dạng dd/MM/yyyy): ");
        String startTime = scanner.nextLine();
        while (!Validation.validateString(startTime, Validation.DATE_REGEX)) {
            System.out.println("Ngày không hợp lệ. Vui lòng nhập lại.");
            startTime = scanner.nextLine();
        }
        System.out.println("Nhập ngày kết thúc (định dạng dd/MM/yyyy): ");
        String endTime = scanner.nextLine();
        while (!Validation.validateString(endTime, Validation.DATE_REGEX) || DateCalculator.dateCompare(startTime, endTime) > 0) {
            if (!Validation.validateString(endTime, Validation.DATE_REGEX)) {
                System.err.println("Ngày không hợp lệ. Vui lòng nhập lại.");
            }
            if (DateCalculator.dateCompare(startTime, endTime) > 0) {
                System.err.println("Ngày kết thúc phải sau ngày bắt đầu!");
            }
            startTime = scanner.nextLine();
        }
        ReceiptManage.getReceiptInstance().displayReceiptListByDay(startTime, endTime);
    }
}
