package service.manage;

import model.User;
import service.file_IO.UserFileIO;

import java.io.*;
import java.util.ArrayList;

public class UserManage {
    private static ArrayList<User> usersList;

    private UserManage() {
        usersList = new ArrayList<>();
        }

    private static UserManage userInstance;

    public static UserManage getUserInstance() {
        if (userInstance == null) userInstance = new UserManage();
        return userInstance;
    }

    public ArrayList<User> getUserList() {
        return usersList;
    }

    public void setUsersList(ArrayList<User> usersList) {
        UserManage.usersList = usersList;
    }

    public void add(User user) throws IOException {
        usersList.add(user);
        UserFileIO.writeUserToFile();
    }

    public void deleteUser(String username) throws IOException {
        usersList.remove(findIndexByUsername(username));
        UserFileIO.writeUserToFile();
    }

    public int findIndexByUsername(String username){
        for (int i = 0; i < usersList.size(); i++) {
            if (usersList.get(i).getUsername().equals(username)) {
                return i;
            }
        } return -1;
    }

    public void getUserInformation(String username) {
        if (userInstance.findIndexByUsername(username) != -1) {
            System.out.println();
            System.out.println("____________________________*** THÔNG TIN TÀI KHOẢN ***____________________________");
            System.out.printf("%-20s %-10s %-20s %-25s %n", "Họ và tên", "Tuổi", "Số điện thoại", "Email");
            System.out.println();
            System.out.println(usersList.get(userInstance.findIndexByUsername(username)));
            System.out.println("___________________________________________________________________________________");
            System.out.println();
        } else {
            System.err.println("Sai tên đăng nhập.");
        }
    }
}
