/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */





package com.mycompany.loginsystem;

import java.util.Scanner;

public class Main {

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        System.out.println("================================");
        System.out.println("       WELCOME TO QUICKCHAT");
        System.out.println("================================");

        // Registration
        System.out.println("\n--- User Registration ---");

        System.out.print("Enter username: ");
        String username = scanner.nextLine().trim();

        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        System.out.print("Enter cellphone number (+27XXXXXXXXX): ");
        String cellphone = scanner.nextLine().trim();

        Login login = new Login();

        String registrationMessage =
                login.registerUser(username, password, cellphone);

        System.out.println(registrationMessage);

        // Continue only if registration was successful.
        if (!registrationMessage.toLowerCase().contains("success")) {
            System.out.println(
                    "\nRegistration was not successful. "
                    + "Please check your details and try again."
            );
            return;
        }

        // Login
        System.out.println("\n--- User Login ---");

        System.out.print("Enter username: ");
        String loginUsername = scanner.nextLine().trim();

        System.out.print("Enter password: ");
        String loginPassword = scanner.nextLine();

        String loginStatus =
                login.returnLoginStatus(loginUsername, loginPassword);

        System.out.println(loginStatus);

        if (!loginStatus.startsWith("Welcome ")) {
            System.out.println("Login failed. QuickChat will close.");
            return;
        }

        // QuickChat menu
        boolean running = true;

        while (running) {

            System.out.println("\n================================");
            System.out.println("          QUICKCHAT MENU");
            System.out.println("================================");
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
                            "\nComing soon! This feature is under development."
                    );
                    break;

                case "3":
                    System.out.println("\nThank you for using QuickChat!");
                    System.out.println(
                            "Messages stored this session: "
                            + Message.returnTotalMessages()
                    );
                    running = false;
                    break;

                default:
                    System.out.println(
                            "Invalid option. Please choose 1, 2 or 3."
                    );
            }
        }
    }

    private static void sendMessages() {

        int numberOfMessages;

        // Validate the number of messages.
        while (true) {
            System.out.print(
                    "\nHow many messages would you like to send? "
            );

            String input = scanner.nextLine().trim();

            try {
                numberOfMessages = Integer.parseInt(input);

                if (numberOfMessages > 0) {
                    break;
                }

                System.out.println(
                        "Please enter a number greater than zero."
                );

            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid whole number.");
            }
        }

        for (int i = 1; i <= numberOfMessages; i++) {

            System.out.println("\n--- Message " + i + " ---");

            // Validate recipient cellphone number.
            String recipient;

            while (true) {
                System.out.print(
                        "Enter recipient cellphone number (+27XXXXXXXXX): "
                );

                recipient = scanner.nextLine().trim();

                if (recipient.matches("\\+27\\d{9}")) {
                    break;
                }

                System.out.println(
                        "Invalid cellphone number. Use +27 followed "
                        + "by 9 digits, for example +27821234567."
                );
            }

            // Validate message length.
            String messageText;

            while (true) {
                System.out.print("Enter your message: ");
                messageText = scanner.nextLine();

                if (messageText.trim().isEmpty()) {
                    System.out.println(
                            "Message cannot be empty. Please try again."
                    );
                } else if (messageText.length() > 250) {
                    System.out.println(
                            "Message exceeds 250 characters. "
                            + "Please shorten it and try again."
                    );
                } else {
                    break;
                }
            }

            // Create a message with a generated 10-digit ID.
            String messageID = Message.generateMessageID();

            Message message = new Message(
                    messageID,
                    recipient,
                    messageText,
                    i
            );

            // Display message information.
            System.out.println("\nMessage ID: " + message.getMessageID());
            System.out.println("Message Hash: " + message.getMessageHash());

            System.out.println("\nChoose an action:");
            System.out.println("1. Send and store message");
            System.out.println("2. Disregard message");

            String action;

            while (true) {
                System.out.print("Choose 1 or 2: ");
                action = scanner.nextLine().trim();

                if (action.equals("1") || action.equals("2")) {
                    break;
                }

                System.out.println("Invalid choice. Enter 1 or 2.");
            }

            if (action.equals("1")) {

                boolean stored = message.storeMessage();

                if (stored) {
                    System.out.println("Message successfully stored.");
                    System.out.println(
                            "Message has been saved to "
                            + "quickchat_messages.txt."
                    );
                } else {
                    System.out.println(
                            "Message could not be stored. "
                            + "Please check the message details."
                    );
                }

            } else {
                System.out.println("Message disregarded.");
            }
        }

        System.out.println("\nMessage session completed.");
        System.out.println(
                "Messages stored this session: "
                + Message.returnTotalMessages()
        );
    }
}
