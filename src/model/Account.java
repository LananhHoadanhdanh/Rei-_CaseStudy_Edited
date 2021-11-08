package model;

import service.manage.RoomManage;
import service.manage.UserManage;

import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Account {

    public static void register() throws IOException {
        UserManage.getUserInstance().add(UserManage.createUser());
    }

    public static boolean login(String username, String password) throws IOException {
        int index = UserManage.getUserInstance().findIndexByUsername(username);
        return UserManage.getUserInstance().getUserList().get(index).getPassword().equals(password);
    }

    public static int choiceExceptionHandling() {
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
}
