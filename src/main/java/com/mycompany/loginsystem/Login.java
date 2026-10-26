/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.loginsystem;

public class Login {

    private String username;
    private String password;

    public boolean checkUserName(String username) {
        return username.contains("_") && username.length() <= 5;
    }

    public boolean checkPasswordComplexity(String password) {

        boolean hasCapital = false;
        boolean hasNumber = false;
        boolean hasSpecial = false;

        for (int i = 0; i < password.length(); i++) {

            char character = password.charAt(i);

            if (Character.isUpperCase(character)) {
                hasCapital = true;
            }

            if (Character.isDigit(character)) {
                hasNumber = true;
            }

            if (!Character.isLetterOrDigit(character)) {
                hasSpecial = true;
            }
        }

        return password.length() >= 8
                && hasCapital
                && hasNumber
                && hasSpecial;
    }

    public boolean checkCellPhoneNumber(String cellPhoneNumber) {

        String regex = "^\\+27[0-9]{9}$";

        return cellPhoneNumber.matches(regex);
    }

    public String registerUser(String username, String password, String cellPhoneNumber) {

        if (!checkUserName(username)) {
            return "Username is incorrectly formatted, please ensure that your username contains an underscore and is no more than 5 characters in length.";
        }

        if (!checkPasswordComplexity(password)) {
            return "Password is not correctly formatted; please ensure that the password contains at least 8 characters, a capital letter, a number, and a special character.";
        }

        if (!checkCellPhoneNumber(cellPhoneNumber)) {
            return "Cell phone number is incorrectly formatted or does not contain an international code.";
        }

        this.username = username;
        this.password = password;

        return "Registration successful.";
    }

    public boolean loginUser(String enteredUsername, String enteredPassword) {

        return enteredUsername.equals(this.username)
                && enteredPassword.equals(this.password);
    }

    public String returnLoginStatus(String enteredUsername, String enteredPassword) {

        if (loginUser(enteredUsername, enteredPassword)) {
            return "Welcome " + enteredUsername
                    + ", it is great to see you again.";
        }

        return "Username or password incorrect, please try again.";
    }
}