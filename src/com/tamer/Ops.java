package com.tamer;

import java.io.IOException;
import java.util.HashMap;

public class Ops {
    private HashMap<Integer, AtmUnit> atmlist = new HashMap<>();
    private HashMap<String, Customer> customers = new HashMap<>();


    public Ops() throws IOException {
        AtmUnit atmUnit1 = new AtmUnit(10001, "Port 73, Handen, Haninge", 150000, true);
        AtmUnit atmUnit2 = new AtmUnit(10002, "Haninge Centrum, Main Entrance", 150000, true);
        Customer Ahmed = new Customer("2233445566", "Ahmed Ali Ahmed", "1234", "123456789", true, 18123.23);
        Customer David = new Customer("2233445577", "David Alex Andersson", "4321", "987654321", true, 4512.12);
        atmlist.put(atmUnit1.getAtmId(), atmUnit1);
        atmlist.put(atmUnit2.getAtmId(), atmUnit2);
        customers.put(Ahmed.getCustomer_Id(), Ahmed);
        customers.put(David.getCustomer_Id(), David);
        AtmUsing();
    }

    private void AtmUsing() throws IOException {
        StdIO.writeLine("");
        StdIO.writeLine("-- ATM List --");
        StdIO.writeLine("");
        StdIO.writeLine("ATM ID" + "     ATM LOCATION");
        atmlist.forEach((k, v) -> System.out.println(k + "   " + v.getLocation()));
        StdIO.writeLine("");
        StdIO.writeLine("Chose ATM Id you want to widhraw from");
        try {
            int atmno = Integer.parseInt(StdIO.realLine());
            if (atmlist.containsKey(atmno))
                login(atmno);
            else {
                StdIO.writeLine("Wrong ATM ID ... Please re-enter");
                AtmUsing();
            }
        }catch (NumberFormatException e){
            StdIO.writeLine(e.getMessage());
            AtmUsing();
        }



    }


    private void login(int atmno) throws IOException {
        int loginCount = 0;
        while (loginCount < 3) {


            StdIO.writeLine("");
            StdIO.writeLine("-- Logon --");
            StdIO.writeLine("");
            StdIO.writeLine("** Welocme to My Bank **");
            StdIO.writeLine("Insert your card -- Enter your card number --");
            String cardId = StdIO.realLine();
            StdIO.write("Password : ");
            String password = StdIO.realLine();
            loginCount++;
            boolean ok = checkLogin(cardId, password);
            if (ok)
                widhraw(cardId, atmno);
            else {
                StdIO.writeLine("Wrong Info ... please re-enter");
                continue;
            }

        }

    }

    private void widhraw(String cardId, int atmno) throws IOException {
        int amount = 0;
        System.out.println("User Info " + customers.get(cardId).toString());
        System.out.println("ATM Info" + atmlist.get(atmno).toString());

        StdIO.writeLine("");
        StdIO.writeLine("Please choose the amonut : ");
        amount = widhrawRules();

        if (amount < customers.get(cardId).getC_Balance()) {
            if (amount < atmlist.get(atmno).getAtmBalnce()) {
                StdIO.writeLine("Please collect your card and money");
                customers.get(cardId).setC_Balance(customers.get(cardId).getC_Balance()-amount);
                atmlist.get(atmno).setAtmBalnce(atmlist.get(atmno).getAtmBalnce()-amount);
                }
            else {
                StdIO.writeLine("Sorry ... not enough cash at the moment in the ATM");
                StdIO.writeLine("You can widhraw up to " + atmlist.get(atmno).getAtmBalnce());
            }
        } else {
            StdIO.writeLine("Sorry .... no sufficient fund in your account");
            StdIO.writeLine("You have " +customers.get(cardId).getC_Balance()+" Sek remains in your account" );
            System.exit(0);
        }
        System.out.println("User Info " + customers.get(cardId).toString());
        System.out.println("ATM Info" + atmlist.get(atmno).toString());
       // System.exit(0);
        AtmUsing();
    }

    private int widhrawRules() throws IOException {
        int amount=0;
        StdIO.writeLine("1) 100 \t\t 4)500");
        StdIO.writeLine("2) 200 \t\t 5)1000");
        StdIO.writeLine("3) 300 \t\t 6)another amount");
        String opt = StdIO.realLine();
        switch (opt){
            case "1":return 100;
            case "2":return 200;
            case "3":return 300;
            case "4":return 500;
            case "5":return 1000;
            case "6":
                StdIO.write("Enter the amount you want : ");
                amount = Integer.parseInt(StdIO.realLine());

                if (amount%100==0 && amount<=2000){

                return amount;}
                else{
                    StdIO.writeLine("Please enter amount of hundreds only up to 2000 Sek");
                    amount=widhrawRules();
                }
                default:
                    StdIO.writeLine("Wrong option number please try again");
                    amount=widhrawRules();

        }
        System.out.println("Amount = " + amount);
        return amount;
    }

    private boolean checkLogin(String cardId, String password) {

        if (customers.containsKey(cardId) && customers.get(cardId).getPassword().equals(password))
            return true;
        else
            return false;
    }


}
