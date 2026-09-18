/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.loginsystem;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        Login login = new Login();

        System.out.println("=================================");
        System.out.println("       LOGIN SYSTEM");
        System.out.println("=================================");

        // Registration
        System.out.println("\n=== Registration ===");

        System.out.print("Enter username: ");
        String username = scanner.nextLine();

        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        System.out.print("Enter cellphone number: ");
        String cellPhoneNumber = scanner.nextLine();

        String registrationMessage = login.registerUser(
                username,
                password,
                cellPhoneNumber
        );

        System.out.println("\n" + registrationMessage);

        // Login
        if (registrationMessage.equals("Registration successful.")) {

            System.out.println("\n=== Login ===");

            System.out.print("Enter username: ");
            String loginUsername = scanner.nextLine();

            System.out.print("Enter password: ");
            String loginPassword = scanner.nextLine();

            String loginStatus = login.returnLoginStatus(
                    loginUsername,
                    loginPassword
            );

            System.out.println("\n" + loginStatus);
        }

        scanner.close();
    }
}