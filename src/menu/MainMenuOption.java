package menu;

import service.create_object.UserCreate;
import service.file_IO.ReceiptFileIO;
import service.file_IO.RoomFileIO;
import service.file_IO.UserFileIO;
import service.manage.UserManage;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class MainMenuOption {

    public static int createChoiceMenu() {
        Scanner scanner = new Scanner(System.in);
        int choice = -1;
        try {
            choice = scanner.nextInt();
        } catch (InputMismatchException exception) {
            System.err.println("Nhập số nguyên!");
        } finally {
            scanner.nextLine();
        }
        return choice;
    }

    public static boolean login(String username, String password) {
        int index = UserManage.getUserInstance().findIndexByUsername(username);
        return UserManage.getUserInstance().getUserList().get(index).getPassword().equals(password);
    }

    public static void loginToSystem() throws IOException, ParseException {
        Scanner scanner = new Scanner(System.in);
        String username = UserCreate.createLoginUserName();
        System.out.print("Nhập mật khẩu: ");
        String password = scanner.nextLine();
        if (login(username, password)) {
            System.out.println("🌸☆🌸　Đăng nhập thành công　🌸☆🌸");
            System.out.println();
            int choice = -1;
            while (choice != 0) {
                ShowMenu.showManageMenu();
                choice = MainMenuOption.createChoiceMenu();
                switch (choice) {
                    case 1:
                        UserManage.getUserInstance().getUserInformation(username);
                        break;
                    case 2:
                        ManageOption.roomManageOption(username);
                        break;
                    case 3:
                        ManageOption.receiptManageOption();
                        break;
                }
            }
        } else {
            System.err.println("Mật khẩu sai!");
        }
    }

    public static void main(String[] args) throws IOException, ParseException {
        UserManage userInstance = UserManage.getUserInstance();
        try {
            UserFileIO.readUserFromFile();
        } catch (FileNotFoundException ignored) {
            System.err.println("Hệ thống chưa có dữ liệu tài khoản. Chọn 2 để đăng kí tài khoản mới");
        }
        try {
            RoomFileIO.readRoomFromFile();
        } catch (FileNotFoundException ignored) { }

        try {
            ReceiptFileIO.readReceiptFromFile();
        } catch (FileNotFoundException ignored) {

        }

        int choice;
        while (true) {
            Scanner scanner = new Scanner(System.in);
            ShowMenu.showLoginMenu();
            choice = createChoiceMenu();
            switch (choice) {
                case 1:
                    loginToSystem();
                    break;
                case 2:
                    userInstance.add(UserCreate.createUser());
                    System.out.println("🌸☆🌸　Đăng kí thành công　🌸☆🌸");
                    System.out.println();
                    break;
                case 3:
                    String username = UserCreate.createLoginUserName();
                    System.out.print("Nhập mật khẩu: ");
                    String password = scanner.nextLine();
                    if (login(username, password)) {
                        userInstance.deleteUser(username);
                        System.out.println("Nhập thông tin mới.");
                        userInstance.add(UserCreate.createUser());
                        System.out.println("🌸☆🌸　Cập nhật thông tin thành công　🌸☆🌸");
                        System.out.println();
                    } else {
                        System.err.println("Mật khẩu sai!");
                    }
                    break;
                case 4:
                    String usernameDelete = UserCreate.createLoginUserName();
                    System.out.print("Nhập mật khẩu: ");
                    String passwordDelete = scanner.nextLine();
                    if (login(usernameDelete, passwordDelete)) {
                        userInstance.getUserList().remove(userInstance.findIndexByUsername(usernameDelete));
                        System.out.println("🌸☆🌸　Xóa tài khoản thành công　🌸☆🌸");
                        System.out.println();
                    } else {
                        System.err.println("Mật khẩu sai!");
                    }
                    break;
                case 0:
                    System.exit(0);
                default:
                    System.err.println("Không có tùy chọn. Vui lòng nhập lại!");
            }
        }
    }
}
