/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


package com.mycompany.loginsystem;

import com.mycompany.loginsystem.Login;
import java.util.Scanner;

public class Main {

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        Login login = new Login();

        System.out.println("=================================");
        System.out.println("   ACADEMIC LOGIN SYSTEM");
        System.out.println("=================================");

        System.out.println("\n=== User Registration ===");

        System.out.print("Enter username: ");
        String username = scanner.nextLine().trim();

        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        System.out.print("Enter cellphone number: ");
        String cellphone = scanner.nextLine().trim();

        String registrationMessage =
                login.registerUser(username, password, cellphone);

        System.out.println("\n" + registrationMessage);

        if (registrationMessage.equals("Registration successful.")) {

            System.out.println("\n=== Login ===");

            System.out.print("Enter username: ");
            String loginUsername = scanner.nextLine().trim();

            System.out.print("Enter password: ");
            String loginPassword = scanner.nextLine();

            String loginStatus =
                    login.returnLoginStatus(loginUsername, loginPassword);

            System.out.println("\n" + loginStatus);

            if (loginStatus.startsWith("Welcome ")) {
                quickChatMenu();
            }
        }

        scanner.close();
    }

    private static void quickChatMenu() {

        boolean running = true;

        while (running) {

            System.out.println("\n=================================");
            System.out.println("          QUICKCHAT MENU");
            System.out.println("=================================");
            System.out.println("1. Send Messages");
            System.out.println("2. Recently Sent Messages");
            System.out.println("3. Quit");

            System.out.print("Choose an option: ");
            String choice = scanner.nextLine().trim();

            switch (choice) {

                case "1":
                    sendMessages();
                    break;

                case "2":
                    System.out.println(
                            "Recently sent messages are not yet implemented."
                    );
                    break;

                case "3":
                    System.out.println("Thank you for using QuickChat!");
                    System.out.println(
                            "Messages stored this session: "
                            + Message.returnTotalMessages()
                    );
                    running = false;
                    break;

                default:
                    System.out.println("Please choose 1, 2 or 3.");
            }
        }
    }

    private static void sendMessages() {

        int numberOfMessages;

        while (true) {

            System.out.print("\nHow many messages would you like to send? ");
            String input = scanner.nextLine().trim();

            try {
                numberOfMessages = Integer.parseInt(input);

                if (numberOfMessages > 0) {
                    break;
                }

                System.out.println("Enter a number greater than zero.");

            } catch (NumberFormatException e) {
                System.out.println("Enter a valid whole number.");
            }
        }

        for (int i = 1; i <= numberOfMessages; i++) {

            System.out.println("\n--- Message " + i + " ---");

            String recipient;

            while (true) {

                System.out.print("Recipient number (+27XXXXXXXXX): ");
                recipient = scanner.nextLine().trim();

                if (recipient.matches("\\+27\\d{9}")) {
                    break;
                }

                System.out.println(
                        "Invalid number. Example: +27821234567"
                );
            }

            String messageText;

            while (true) {

                System.out.print("Enter your message: ");
                messageText = scanner.nextLine();

                if (messageText.trim().isEmpty()) {
                    System.out.println("Message cannot be empty.");
                } else if (messageText.length() > 250) {
                    System.out.println(
                            "Message exceeds 250 characters."
                    );
                } else {
                    break;
                }
            }

            String messageID = Message.generateMessageID();

            Message message = new Message(
                    messageID, recipient, messageText, i
            );

            System.out.println("Message ID: " + message.getMessageID());
            System.out.println("Message Hash: " + message.getMessageHash());

            System.out.println("1. Send and store message");
            System.out.println("2. Disregard message");

            String action;

            while (true) {

                System.out.print("Choose 1 or 2: ");
                action = scanner.nextLine().trim();

                if (action.equals("1") || action.equals("2")) {
                    break;
                }

                System.out.println("Please enter 1 or 2.");
            }

            if (action.equals("1")) {

                if (message.storeMessage()) {
                    System.out.println("Message successfully stored.");
                } else {
                    System.out.println("Message could not be stored.");
                }

            } else {
                System.out.println("Message disregarded.");
            }
        }
    }
}
