package menu;

import model.Account;
import service.manage.RoomManage;
import service.manage.UserManage;

import java.io.IOException;
import java.text.ParseException;
import java.util.Scanner;

public class LoginMenu{

    public static String createUsername() throws IOException {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Nhập tên đăng nhập: ");
        String username = scanner.nextLine();
        while (UserManage.getUserInstance().findIndexByUsername(username) == -1) {
            System.err.println("Sai tên đăng nhập. Vui lòng nhập lại!");
            username = scanner.nextLine();
        }
        return username;
    }

    public static void loginToSystem() throws IOException, ParseException {
        Scanner scanner = new Scanner(System.in);
        String username = createUsername();
        System.out.print("Nhập mật khẩu: ");
        String password = scanner.nextLine();
        if (Account.login(username, password)) {
            System.out.println("Đăng nhập thành công!");
            int choice = -1;
            while (choice != 0) {
                ShowMenu.showManageMenu();
                choice = Account.choiceExceptionHandling();
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
}
