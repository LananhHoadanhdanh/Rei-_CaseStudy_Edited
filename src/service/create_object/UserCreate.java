package service.create_object;

import model.User;
import model.Validation;
import service.manage.UserManage;

import java.util.InputMismatchException;
import java.util.Scanner;

public class UserCreate {
    public static final Scanner SCANNER = new Scanner(System.in);

    public static String createLoginUserName() {
        System.out.print("Nhập tên đăng nhập: ");
        String username = SCANNER.nextLine();
        while (UserManage.getUserInstance().findIndexByUsername(username) == -1 || !Validation.validateString(username, Validation.LOGIN_NAME_REGEX)) {
            System.err.println("Tên đăng nhập không đúng hoặc không hợp lệ. Vui lòng nhập lại. (8-16 kí tự, không gồm kí tự đặc biệt)");
            username = SCANNER.nextLine();
        }
        return username;
    }

    public static String createRegisterUserName() {
        System.out.print("Nhập tên đăng nhập: ");
        String username = SCANNER.nextLine();
        boolean isValid = Validation.validateString(username, Validation.LOGIN_NAME_REGEX);
        boolean isExist = (UserManage.getUserInstance().findIndexByUsername(username) != -1);
        while (isExist || !isValid) {
            System.err.println("Tên đăng nhập đã tồn tại hoặc không hợp lệ. Vui lòng nhập lại. (8-16 kí tự, không gồm kí tự đặc biệt)");
            username = SCANNER.nextLine();
            isValid = Validation.validateString(username, Validation.LOGIN_NAME_REGEX);
            isExist = (UserManage.getUserInstance().findIndexByUsername(username) != -1);
        }
        return username;
    }

    public static String createRegisterPassword() {
        System.out.print("Nhập mật khẩu: ");
        String password = SCANNER.nextLine();
        while (!Validation.validateString(password, Validation.PASSWORD_REGEX)) {
            System.err.println("Chưa hợp lệ. Mật khẩu gồm từ 8-16 kí tự, không gồm kí tự đặc biệt.");
            password = SCANNER.nextLine();
        }
        return password;
    }

    public static int createAge() {
        System.out.print("Nhập tuổi: ");
        int age = 17 ;
        while (age < 18){
            try {
                age = SCANNER.nextInt();
                if(age < 18){
                    System.out.println("Bạn phải từ 18 tuổi trở lên. Vui lòng nhập lại.");
                }
            } catch (InputMismatchException e){
                System.out.println("Vui lòng nhập số nguyên lớn hơn 17.");
            } finally {
                SCANNER.nextLine();
            }
        }
        return age;
    }

    public static String createPhoneNumber() {
        System.out.print("Nhập số điện thoại: ");
        String phoneNumber = SCANNER.nextLine();
        while (!Validation.validateString(phoneNumber, Validation.PHONE_NUMBER_REGEX)) {
            System.err.println("Số điện thoại chưa hợp lệ. Vui lòng nhập lại.");
            phoneNumber = SCANNER.nextLine();
        }
        return phoneNumber;
    }

    public static String createEmail() {
        System.out.print("Nhập địa chỉ email: ");
        String email = SCANNER.nextLine();
        while (!Validation.validateString(email, Validation.EMAIL_REGEX)) {
            System.err.println("Địa chỉ email chưa hợp lệ. Vui lòng nhập lại.");
            email = SCANNER.nextLine();
        }
        return email;
    }

    public static String createFullName() {
        String fullName = SCANNER.nextLine();
        while (!Validation.validateString(fullName, Validation.FULL_NAME)) {
            System.err.println("Họ và tên chưa hợp lệ. Vui lòng nhập lại.");
            fullName = SCANNER.nextLine();
        }
        return fullName;
    }

    public static User createUser() {
        String username = createRegisterUserName();
        String password = createRegisterPassword();
        System.out.print("Nhập họ và tên: ");
        String fullName = createFullName();
        String phoneNumber = createPhoneNumber();
        String email = createEmail();
        int age = createAge();
        return new User(fullName, age, phoneNumber, email, username, password);
    }
}
