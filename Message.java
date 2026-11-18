/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


package com.mycompany.loginsystem;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class Message {

    private final String messageID;
    private final String recipient;
    private final String messageText;
    private final String messageHash;
    private final int messageNumber;

    private static int totalMessages = 0;
    private static final List<Message> messages = new ArrayList<>();

    private static final Path STORAGE_FILE =
            Path.of("quickchat_messages.txt");

    public Message(String messageID, String recipient,
                   String messageText, int messageNumber) {

        this.messageID = messageID;
        this.recipient = recipient;
        this.messageText = messageText;
        this.messageNumber = messageNumber;
        this.messageHash = createMessageHash();
    }

    public static String generateMessageID() {
        long number = ThreadLocalRandom.current().nextLong(
                1_000_000_000L,
                10_000_000_000L
        );

        return String.valueOf(number);
    }

    public boolean checkMessageID() {
        return messageID != null
                && messageID.matches("\\d{1,10}");
    }

    public boolean checkRecipientCell() {
        return recipient != null
                && recipient.matches("\\+27\\d{9}");
    }

    public String createMessageHash() {
        if (messageID == null || messageID.length() < 2
                || messageText == null
                || messageText.trim().isEmpty()) {
            return "";
        }

        String[] words = messageText.trim().split("\\s+");
        String firstWord = words[0];
        String lastWord = words[words.length - 1];

        return messageID.substring(0, 2) + ":"
                + messageNumber + ":"
                + (firstWord + lastWord).toUpperCase();
    }

    public boolean storeMessage() {
        if (!checkMessageID()
                || !checkRecipientCell()
                || messageText == null
                || messageText.trim().isEmpty()
                || messageText.length() > 250) {
            return false;
        }

        String record = messageID + "\t"
                + recipient + "\t"
                + messageText.replace("\t", " ")
                + "\t" + messageHash
                + "\t" + messageNumber
                + System.lineSeparator();

        try {
            Files.writeString(
                    STORAGE_FILE,
                    record,
                    StandardOpenOption.CREATE,
                    StandardOpenOption.APPEND
            );

            messages.add(this);
            totalMessages++;
            return true;

        } catch (IOException e) {
            System.out.println(
                    "Unable to save message: " + e.getMessage()
            );
            return false;
        }
    }

    public static List<Message> returnMessages() {
        return new ArrayList<>(messages);
    }

    public static int returnTotalMessages() {
        return totalMessages;
    }

    public String getMessageID() {
        return messageID;
    }

    public String getRecipient() {
        return recipient;
    }

    public String getMessageText() {
        return messageText;
    }

    public String getMessageHash() {
        return messageHash;
    }

    public int getMessageNumber() {
        return messageNumber;
    }
}
