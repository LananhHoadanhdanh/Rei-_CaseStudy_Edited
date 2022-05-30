package service.file_IO.ReceiptFile;

import model.Receipt;
import service.file_IO.ReceiptFileCsvIO;
import service.manage.ReceiptManage;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;

public class ReceiptTxt {
    public static void writeReceiptToTxtFile(String receiptId) throws IOException, ParseException {
        ReceiptManage receiptInstance = ReceiptManage.getReceiptInstance();
        Receipt receipt = receiptInstance.getReceiptList().get(receiptInstance.findIndexById(receiptId));
        ArrayList<Receipt> receiptList = ReceiptManage.getReceiptInstance().getReceiptList();
        Collections.sort(receiptList);
        FileWriter fileWriter = new FileWriter("src/service/file_IO/ReceiptFile/receipt"+receiptId+".txt");
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
        String str = "HÓA ĐƠN BÁN HÀNG";
        str += "Tên khách hàng: " + receipt.getCustomerName();


        bufferedWriter.write(str);
        bufferedWriter.close();
    }

    public static void main(String[] args) throws IOException, ParseException {
        ReceiptFileCsvIO.readReceiptFromFile();
        writeReceiptToTxtFile("000001");
    }
}
