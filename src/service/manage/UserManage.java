package service.manage;

import model.User;
import model.Validation;

import java.io.*;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class UserManage {
    private static ArrayList<User> usersList;

    private UserManage() {
        }

    private static UserManage userInstance;

    public static UserManage getUserInstance() {
        if (userInstance == null) userInstance = new UserManage();
        return userInstance;
    }

    public ArrayList<User> getUserList() {
        return usersList;
    }

    public void add(User user) throws IOException {
        usersList.add(user);
        writeUserToFile();
        readUserFromFile();
    }

    public void deleteUser(String username) throws IOException {
        usersList.remove(findIndexByUsername(username));
        writeUserToFile();
        readUserFromFile();
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

    public static void writeUserToFile() throws IOException {
        FileWriter fileWriter = new FileWriter("src/service/userManageFile.csv");
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
        String str = "Họ và tên,Tuổi,Số điện thoại,Email,Tên đăng nhập,Password";
        for (User user : usersList) {
            str += "\n" + user.getFullName() + ",";
            str += user.getAge() + ",";
            str += user.getPhoneNumber() + ",";
            str += user.getEmail() + ",";
            str += user.getUsername() + ",";
            str += user.getPassword();
        }
        bufferedWriter.write(str);
        bufferedWriter.close();
    }

    public static void readUserFromFile() throws IOException {
        usersList = new ArrayList<>();
        FileReader fileReader = new FileReader("src/service/userManageFile.csv");
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        String content = bufferedReader.readLine();
        while ((content = bufferedReader.readLine()) != null) {
            String[] array = content.split(",");
            String fullName = array[0];
            int age = Integer.parseInt(array[1]);
            String phoneNumber = array[2];
            String email = array[3];
            String username = array[4];
            String password = array[5];
            usersList.add(new User(fullName, age, phoneNumber, email, username, password));
        }
        bufferedReader.close();
        fileReader.close();
    }
}
