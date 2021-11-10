package service.file_IO;

import model.User;
import service.manage.UserManage;

import java.io.*;
import java.util.ArrayList;

public class UserFileIO {
    public static void writeUserToFile() throws IOException {
        ArrayList<User> usersList = UserManage.getUserInstance().getUserList();
        FileWriter fileWriter = new FileWriter("src/service/file_IO/userManageFile.csv");
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
        StringBuilder str = new StringBuilder("Họ và tên,Tuổi,Số điện thoại,Email,Tên đăng nhập,Password");
        for (User user : usersList) {
            str.append("\n").append(user.getFullName()).append(",");
            str.append(user.getAge()).append(",");
            str.append(user.getPhoneNumber()).append(",");
            str.append(user.getEmail()).append(",");
            str.append(user.getUsername()).append(",");
            str.append(user.getPassword());
        }
        bufferedWriter.write(str.toString());
        bufferedWriter.close();
    }

    public static void readUserFromFile() throws IOException {
        ArrayList<User> usersList = new ArrayList<>();
        FileReader fileReader = new FileReader("src/service/file_IO/userManageFile.csv");
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
        UserManage.getUserInstance().setUsersList(usersList);
        bufferedReader.close();
        fileReader.close();
    }
}
