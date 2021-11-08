package service.manage;

import model.DateCalculator;
import model.Receipt;
import model.Validation;

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

    public void add(Receipt receipt) throws IOException, ParseException {
        receiptList.add(receipt);
        writeReceiptToFile();
        readReceiptFromFile();

    }

    public void delete(String id) throws IOException, ParseException {
        receiptList.remove(findIndexById(id));
        writeReceiptToFile();
        readReceiptFromFile();
    }

    public void displayAllReceipt() {
        Collections.sort(receiptList);
        System.out.println();
        System.out.println("________________________________________________*** DANH SÁCH TOÀN BỘ HÓA ĐƠN ***_______________________________________________");
        System.out.printf("%-15s %-20s %-20s %-15s %-15s %-15s %n", "Số hóa đơn", "Khách hàng", "Nhân viên", "Ngày check-in", "Ngày check-out", "Tổng tiền");
        for (int i = 0; i < receiptList.size(); i++) {
            System.out.println(receiptList.get(i));
        }
        System.out.println("______________________________________________________________________________________________________");
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
        System.out.println("__________________*** DANH SÁCH HÓA ĐƠN TỪ NGÀY " + startDay + " ĐẾN NGÀY " + endDay + " ***_________________");
        System.out.printf("%-15s %-20s %-20s %-15s %-15s %-15s %n", "Số hóa đơn", "Khách hàng", "Nhân viên", "Ngày check-in", "Ngày check-out", "Tổng tiền");
        for (int i = 0; i < receiptList.size(); i++) {
            Receipt receipt = receiptList.get(i);
            int startCompare = DateCalculator.dateCompare(receipt.getCheckOutTime(), startDay);
            int endCompare = DateCalculator.dateCompare(receipt.getCheckOutTime(), endDay);
            if (startCompare >= 0 && endCompare <=0) {
                sumTotal += receipt.getTotalPrice();
                System.out.println(receipt);
            }
        }
        System.out.println("___________________________________________________________________________________________________");
        System.out.println("Tổng số tiền: " + sumTotal);
        System.out.println();
    }

    public static void writeReceiptToFile() throws IOException, ParseException {
        Collections.sort(receiptList);
        FileWriter fileWriter = new FileWriter("src/service/receiveManageFile.csv");
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
        String str = "Số hóa đơn,Tên khách hàng,Tên nhân viên,Số phòng, Giờ check-in,Giờ check-out,Tổng số tiền";
        for (Receipt receipt : receiptList) {
            str += "\n";
            str += receipt.getReceiptID() + ",";
            str += receipt.getCustomerName() + ",";
            str += receipt.getStaffName() + ",";
            str += receipt.getRoomId() + ",";
            str += receipt.getCheckInTime() + ",";
            str += receipt.getCheckOutTime() + ",";
            str += receipt.getTotalPrice();
        }
        bufferedWriter.write(str);
        bufferedWriter.close();
    }

    public static void readReceiptFromFile() throws IOException {
        receiptList = new ArrayList<>();
        FileReader fileReader = new FileReader("src/service/receiveManageFile.csv");
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        String content = bufferedReader.readLine();
        while ((content = bufferedReader.readLine()) != null) {
            String[] array = content.split(",");
            String receiptId = array[0];
            String customerName = array[1];
            String staffName = array[2];
            int roomId = Integer.parseInt(array[3]);
            String checkInTime = array[4];
            String checkOutTime = array[5];
            receiptList.add(new Receipt(receiptId, customerName, staffName, checkInTime, checkOutTime, roomId));
        }
        bufferedReader.close();
        fileReader.close();
    }

    public static Receipt createReceipt() throws ParseException, IOException {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Nhập số hóa đơn: ");
        String receiptId = scanner.nextLine();
        //ReceiptManage.getReceiptInstance().getReceiptList();
        while (ReceiptManage.getReceiptInstance().findIndexById(receiptId) != -1) {
            System.out.println("Số hóa đơn đã tồn tại, vui lòng nhập lại");
            receiptId = scanner.nextLine();
        }

        System.out.print("Nhập tên khách hàng: ");
        String customerName = scanner.nextLine();

        System.out.print("Nhập tên nhân viên: ");
        String staffName = scanner.nextLine();

        System.out.println("Nhập ngày check-in (định dạng dd/MM/yyyy): ");
        String checkInTime = scanner.nextLine();
        while (!Validation.validateString(checkInTime, Validation.DATE_REGEX)) {
            System.out.println("Ngày không hợp lệ. Vui lòng nhập lại.");
            checkInTime = scanner.nextLine();
        }
        System.out.println("Nhập ngày check-out (định dạng dd/MM/yyyy): ");
        String checkOutTime = scanner.nextLine();
        while (!Validation.validateString(checkOutTime, Validation.DATE_REGEX) || DateCalculator.dateCompare(checkInTime, checkOutTime) > 0) {
            if (!Validation.validateString(checkOutTime, Validation.DATE_REGEX)) {
                System.err.println("Ngày không hợp lệ. Vui lòng nhập lại.");
            }
            if (DateCalculator.dateCompare(checkInTime, checkOutTime) > 0) {
                System.err.println("Ngày check-out phải sau ngày check-in!");
            }
            checkInTime = scanner.nextLine();
        }

        System.out.print("Nhập số phòng: ");
        int roomId = -1;
        while (RoomManage.getRoomInstance().findIndexById(roomId) == -1 || roomId < 0) {
            Scanner sc = new Scanner(System.in);
            try {
                roomId = sc.nextInt();
                if (RoomManage.getRoomInstance().findIndexById(roomId) == -1) {
                    System.err.println("Phòng không tồn tại. Vui lòng nhập lại.");
                }
                if (roomId < 0) {
                    System.err.println("Số phòng không hợp lệ. Vui lòng nhập lại.");
                }
            } catch (InputMismatchException e){
                System.err.println("Dữ liệu nhập vào không hợp lệ. Vui lòng nhập lại");
            }
        }
        return new Receipt(receiptId, customerName, staffName, checkInTime, checkOutTime, roomId);
    }
}
