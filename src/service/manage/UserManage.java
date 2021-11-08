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

    public static User createUser() throws IOException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Nhập tên đăng nhập: ");
        String username = scanner.nextLine();
        while (userInstance.findIndexByUsername(username) != -1 || !Validation.validateString(username, Validation.LOGIN_NAME_REGEX)) {
            System.err.println("Tên đăng nhập đã tồn tại hoặc không hợp lệ. Vui lòng nhập lại. (8-16 kí tự, không gồm kí tự đặc biệt)");
            username = scanner.nextLine();
        }

        System.out.println("Nhập mật khẩu: ");
        String password = scanner.nextLine();
        while (!Validation.validateString(password, Validation.PASSWORD_REGEX)) {
            System.err.println("Chưa hợp lệ. Mật khẩu gồm từ 8-16 kí tự, không gồm kí tự đặc biệt.");
            password = scanner.nextLine();
        }

        System.out.print("Nhập tên: ");
        String name = scanner.nextLine();

        System.out.print("Nhập tuổi: ");
        int age = 17 ;
        while (age < 18){
            Scanner sc = new Scanner(System.in);
            try {
                age = sc.nextInt();
                if(age < 18){
                    System.out.println("Bạn phải từ 18 tuổi trở lên. Vui lòng nhập lại.");
                }
            } catch (InputMismatchException e){
                System.out.println("Vui lòng nhập số nguyên lớn hơn 17.");
            }
        }


        System.out.print("Nhập số điện thoại: ");
        String phoneNumber = scanner.nextLine();
        while (!Validation.validateString(phoneNumber, Validation.PHONE_NUMBER_REGEX)) {
            System.err.println("Số điện thoại chưa hợp lệ. Vui lòng nhập lại.");
            phoneNumber = scanner.nextLine();
        }

        System.out.print("Nhập địa chỉ email: ");
        String email = scanner.nextLine();
        while (!Validation.validateString(email, Validation.EMAIL_REGEX)) {
            System.err.println("Địa chỉ email chưa hợp lệ. Vui lòng nhập lại.");
            email = scanner.nextLine();
        }
        return new User(name, age, phoneNumber, email, username, password);
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
