package com.rutika.bankapplication;

public class HistoryModal {
    private String Sender;
    private String Receiver;
    private int Amount;

    public HistoryModal(String sender, String receiver, int amount) {
        Sender = sender;
        Receiver = receiver;
        Amount = amount;
    }

    public String getSender() {
        return Sender;
    }

    public void setSender(String sender) {
        Sender = sender;
    }

    public String getReceiver() {
        return Receiver;
    }

    public void setReceiver(String receiver) {
        Receiver = receiver;
    }

    public int getAmount() {
        return Amount;
    }

    public void setAmount(int amount) {
        Amount = amount;
    }
}
