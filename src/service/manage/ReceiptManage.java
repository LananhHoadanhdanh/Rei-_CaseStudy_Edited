package service.manage;

import model.DateCalculator;
import model.Receipt;
import service.file_IO.ReceiptFileIO;

import java.io.*;
import java.text.ParseException;
import java.util.*;

public class ReceiptManage{
    private static ArrayList<Receipt> receiptList;

    private ReceiptManage() {
    }

    private static ReceiptManage receiptInstance;

    public static ReceiptManage getReceiptInstance() {
        if (receiptInstance == null) receiptInstance = new ReceiptManage();
        return receiptInstance;
    }

    public ArrayList<Receipt> getReceiptList() {
        return receiptList;
    }

    public void setReceiptList(ArrayList<Receipt> receiptList) {
        ReceiptManage.receiptList = receiptList;
    }

    public void add(Receipt receipt) throws IOException, ParseException {
        receiptList.add(receipt);
        ReceiptFileIO.writeReceiptToFile();
        ReceiptFileIO.readReceiptFromFile();
    }

    public void delete(String id) throws IOException, ParseException {
        receiptList.remove(findIndexById(id));
        ReceiptFileIO.writeReceiptToFile();
        ReceiptFileIO.readReceiptFromFile();
    }

    public void displayAllReceipt() {
        Collections.sort(receiptList);
        System.out.println();
        System.out.println("__________________________________*** DANH SÁCH TOÀN BỘ HÓA ĐƠN ***__________________________________");
        System.out.printf("%-15s %-20s %-20s %-15s %-15s %-15s %n", "Số hóa đơn", "Khách hàng", "Nhân viên", "Ngày check-in", "Ngày check-out", "Tổng tiền");
        for (Receipt receipt : receiptList) {
            System.out.println(receipt);
        }
        System.out.println("_____________________________________________________________________________________________________");
        System.out.println();
    }

    public int findIndexById(String id) {
        for (int i = 0; i < receiptList.size(); i++) {
            if (receiptList.get(i).getReceiptID().equals(id)) {
                return i;
            }
        } return -1;
    }

    public void displayReceiptListByDay(String startDay, String endDay) throws ParseException, IOException {
        Collections.sort(receiptList);
        int sumTotal = 0;
        System.out.println();
        System.out.println("__________________*** DANH SÁCH HÓA ĐƠN TỪ NGÀY " + startDay + " ĐẾN NGÀY " + endDay + " ***__________________");
        System.out.printf("%-15s %-20s %-20s %-15s %-15s %-15s %n", "Số hóa đơn", "Khách hàng", "Nhân viên", "Ngày check-in", "Ngày check-out", "Tổng tiền");
        for (Receipt receipt : receiptList) {
            int startCompare = DateCalculator.dateCompare(receipt.getCheckOutTime(), startDay);
            int endCompare = DateCalculator.dateCompare(receipt.getCheckOutTime(), endDay);
            if (startCompare >= 0 && endCompare <= 0 || startDay.equals(receipt.getCheckInTime()) || endDay.equals(receipt.getCheckOutTime())) {
                sumTotal += receipt.getTotalPrice();
                System.out.println(receipt);
            }
        }
        System.out.println("____________________________________________________________________________________________________");
        System.out.println("Tổng số tiền: " + sumTotal);
        System.out.println();
    }
}
