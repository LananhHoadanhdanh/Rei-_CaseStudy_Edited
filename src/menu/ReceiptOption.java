package menu;

import model.DateCalculator;
import service.create_object.ReceiptCreate;
import service.manage.ReceiptManage;

import java.io.IOException;
import java.text.ParseException;

public class ReceiptOption {

    ReceiptManage receiptInstance = ReceiptManage.getReceiptInstance();

    private static ReceiptOption receiptOption;

    private ReceiptOption() {
    }

    public static ReceiptOption getReceiptOption() {
        if (receiptOption == null) receiptOption = new ReceiptOption();
        return receiptOption;
    }

    public void receiptOptionUpdateById() throws ParseException, IOException {
        String receiptId = ReceiptCreate.createOldReceiptId();
        receiptInstance.delete(receiptId);
        receiptInstance.add(ReceiptCreate.createReceipt());
    }

    public void receiptOptionDeleteById() throws IOException, ParseException {
        String receiptId = ReceiptCreate.createOldReceiptId();
        receiptInstance.delete(receiptId);
        System.out.println("🌸☆🌸　Xóa hóa đơn thành công　🌸☆🌸");
    }

    public void receiptOptionFindByDay() throws ParseException, IOException {
        System.out.println("Nhập ngày check-in (định dạng dd/MM/yyyy): ");
        String startTime = ReceiptCreate.createNewDate();

        System.out.println("Nhập ngày check-out (định dạng dd/MM/yyyy): ");
        String endTime = ReceiptCreate.createNewDate();
        while (DateCalculator.dateCompare(startTime, endTime) > 0) {
            if (DateCalculator.dateCompare(startTime, endTime) > 0) {
                System.err.println("Ngày check-out phải sau ngày check-in!");
            }
            endTime = ReceiptCreate.createNewDate();
        }
        receiptInstance.displayReceiptListByDay(startTime, endTime);
    }
}
