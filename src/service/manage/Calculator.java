package service.manage;

public class Calculator {
    public static double calculate(double first, double second, String operator) {
        switch (operator) {
            case "+" :
                return first + second;
            case "-" :
                return first - second;
            case "*" :
                return first * second;
            case "/" :
                if (second == 0) {
                    throw new RuntimeException("Can't divide by zero");
                } return first / second;
            default: throw new RuntimeException("Invalid operation");
        }
    }

    public static void main(String[] args) {
        System.out.println(calculate(8,5,"+"));
    }
}
