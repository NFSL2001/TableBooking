package wia2007.project.tablebooking.entity;

public class PasswordChecker {
    public static boolean validPassword(String password) {
        boolean containDigit = false;
        boolean containUpper = false;
        boolean containLower = false;

        for (int i = 0; i < password.length(); i++) {
            containDigit = Character.isDigit(password.charAt(i)) || containDigit;
            containUpper = Character.isUpperCase(password.charAt(i)) || containUpper;
            containLower = Character.isLowerCase(password.charAt(i)) || containLower;
        }

        return containDigit && containLower && containUpper;
    }
}
