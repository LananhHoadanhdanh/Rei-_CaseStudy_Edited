package service.create_object;

import model.User;
import model.Validation;
import service.manage.UserManage;

import java.util.InputMismatchException;
import java.util.Scanner;

public class UserCreate {
    public static String createLoginUserName() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Nhập tên đăng nhập: ");
        String username = scanner.nextLine();
        while (UserManage.getUserInstance().findIndexByUsername(username) == -1 || !Validation.validateString(username, Validation.LOGIN_NAME_REGEX)) {
            System.err.println("Tên đăng nhập không đúng hoặc không hợp lệ. Vui lòng nhập lại. (8-16 kí tự, không gồm kí tự đặc biệt)");
            username = scanner.nextLine();
        }
        return username;
    }

    public static String createRegisterUserName() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Nhập tên đăng nhập: ");
        String username = scanner.nextLine();
        boolean isValid = Validation.validateString(username, Validation.LOGIN_NAME_REGEX);
        boolean isExist = (UserManage.getUserInstance().findIndexByUsername(username) != -1);
        while (isExist || !isValid) {
            System.err.println("Tên đăng nhập đã tồn tại hoặc không hợp lệ. Vui lòng nhập lại. (8-16 kí tự, không gồm kí tự đặc biệt)");
            username = scanner.nextLine();
            isValid = Validation.validateString(username, Validation.LOGIN_NAME_REGEX);
            isExist = (UserManage.getUserInstance().findIndexByUsername(username) != -1);
        }
        return username;
    }

    public static String createRegisterPassword() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Nhập mật khẩu: ");
        String password = scanner.nextLine();
        while (!Validation.validateString(password, Validation.PASSWORD_REGEX)) {
            System.err.println("Chưa hợp lệ. Mật khẩu gồm từ 8-16 kí tự, không gồm kí tự đặc biệt.");
            password = scanner.nextLine();
        }
        return password;
    }

    public static int createAge() {
        System.out.print("Nhập tuổi.");
        int age = 17 ;
        while (age < 18){
            Scanner scanner = new Scanner(System.in);
            try {
                age = scanner.nextInt();
                if(age < 18){
                    System.out.println("Bạn phải từ 18 tuổi trở lên. Vui lòng nhập lại.");
                }
            } catch (InputMismatchException e){
                System.out.println("Vui lòng nhập số nguyên lớn hơn 17.");
            }
        }
        return age;
    }

    public static String createPhoneNumber() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Nhập số điện thoại: ");
        String phoneNumber = scanner.nextLine();
        while (!Validation.validateString(phoneNumber, Validation.PHONE_NUMBER_REGEX)) {
            System.err.println("Số điện thoại chưa hợp lệ. Vui lòng nhập lại.");
            phoneNumber = scanner.nextLine();
        }
        return phoneNumber;
    }

    public static String createEmail() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Nhập địa chỉ email: ");
        String email = scanner.nextLine();
        while (!Validation.validateString(email, Validation.EMAIL_REGEX)) {
            System.err.println("Địa chỉ email chưa hợp lệ. Vui lòng nhập lại.");
            email = scanner.nextLine();
        }
        return email;
    }

    public static User createUser() {
        Scanner scanner = new Scanner(System.in);
        String username = createRegisterUserName();
        String password = createRegisterPassword();
        String name = scanner.nextLine();
        int age = createAge();
        String phoneNumber = createPhoneNumber();
        String email = createEmail();
        return new User(name, age, phoneNumber, email, username, password);
    }
}
