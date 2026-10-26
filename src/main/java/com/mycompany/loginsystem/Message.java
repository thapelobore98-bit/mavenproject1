/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.mycompany.loginsystem;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Message {

    private final String messageID;
    private final String recipient;
    private final String messageText;
    private final int messageNumber;
    private final String messageHash;

    private static final List<Message> messages = new ArrayList<>();

    public Message(String messageID, String recipient,
                   String messageText, int messageNumber) {
        this.messageID = messageID;
        this.recipient = recipient;
        this.messageText = messageText;
        this.messageNumber = messageNumber;
        this.messageHash = createMessageHash();
    }

    public static String generateMessageID() {
        return UUID.randomUUID().toString().substring(0, 10);
    }

    public boolean checkMessageID() {
        return messageID != null && messageID.length() <= 10;
    }

    public boolean checkRecipientCell() {
        return recipient != null && recipient.matches("\\+27[0-9]{9}");
    }

    public String createMessageHash() {
        String firstTwo = messageID.length() >= 2
                ? messageID.substring(0, 2) : messageID;
        String[] words = messageText.trim().split("\\s+");
        String firstWord = words.length > 0 ? words[0] : "";
        String lastWord = words.length > 0 ? words[words.length - 1] : "";

        return (firstTwo + ":" + messageNumber + ":"
                + firstWord + lastWord).toUpperCase();
    }

    public boolean storeMessage() {
        try (BufferedWriter writer =
                     new BufferedWriter(new FileWriter("quickchat_messages.txt", true))) {
            writer.write(messageID + "\t" + messageHash + "\t"
                    + recipient + "\t" + messageText);
            writer.newLine();
            messages.add(this);
            return true;
        } catch (IOException e) {
            System.out.println("Error saving message: " + e.getMessage());
            return false;
        }
    }

    public static List<Message> returnMessages() {
        return new ArrayList<>(messages);
    }

    public static int returnTotalMessages() {
        return messages.size();
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
