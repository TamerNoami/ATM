package com.tamer;

public class Customer {

    private String Customer_Id;
    private String name;
    private String password;
    private String Card_No;
    private boolean status;
    private double C_Balance;

    public Customer(String customer_Id, String name, String password, String card_No, boolean status, double c_Balance) {
        Customer_Id = customer_Id;
        this.name = name;
        this.password = password;
        Card_No = card_No;
        this.status = status;
        C_Balance = c_Balance;
    }

    public String getCustomer_Id() {
        return Customer_Id;
    }

    public void setCustomer_Id(String customer_Id) {
        Customer_Id = customer_Id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCard_No() {
        return Card_No;
    }

    public void setCard_No(String card_No) {
        Card_No = card_No;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public double getC_Balance() {
        return C_Balance;
    }

    public void setC_Balance(double c_Balance) {
        C_Balance = c_Balance;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "Customer_Id='" + Customer_Id + '\'' +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", Card_No='" + Card_No + '\'' +
                ", status=" + status +
                ", C_Balance=" + C_Balance +
                '}';
    }
}
