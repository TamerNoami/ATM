package com.tamer;

import java.time.LocalDateTime;

public class Trans {
    private int transNo;
    private String Customer_Id;
    private LocalDateTime transTime;
    private String atmLocation;
    private double amount;
    private double balance;

    public Trans(int transNo, String customer_Id, String atmLocation, double amount, double balance) {
        this.transNo = transNo;
        Customer_Id = customer_Id;
        this.atmLocation = atmLocation;
        this.amount = amount;
        this.balance = balance;
    }

    public int getTransNo() {
        return transNo;
    }

    public void setTransNo(int transNo) {
        this.transNo = transNo;
    }

    public String getCustomer_Id() {
        return Customer_Id;
    }

    public void setCustomer_Id(String customer_Id) {
        Customer_Id = customer_Id;
    }

    public LocalDateTime getTransTime() {
        return transTime;
    }

    public void setTransTime(LocalDateTime transTime) {
        this.transTime = transTime;
    }

    public String getAtmLocation() {
        return atmLocation;
    }

    public void setAtmLocation(String atmLocation) {
        this.atmLocation = atmLocation;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
}
