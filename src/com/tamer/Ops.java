package com.tamer;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class Ops {
    private HashMap<Integer, AtmUnit> atmlist = new HashMap<>();
    private HashMap<String, Customer> customers = new HashMap<>();
    private HashMap<String, List<Trans>> transList= new HashMap<>();
    Random random = new Random();



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
        Customer c = customers.get(cardId);
        AtmUnit a = atmlist.get(atmno);
        System.out.println("User Info " + c.toString());
        System.out.println("ATM Info" + a.toString());

        StdIO.writeLine("");
        StdIO.writeLine("Please choose the amonut : ");
        amount = widhrawRules();

        if (amount < c.getC_Balance()) {
            if (amount < a.getAtmBalnce()) {
                StdIO.writeLine("Please collect your card and money");
                c.setC_Balance(c.getC_Balance()-amount);
                a.setAtmBalnce(a.getAtmBalnce()-amount);
                String ran = String.valueOf(random.nextInt(1999999-1000000)+1000000);
                Trans trans = new Trans(ran,c.getCustomer_Id(), LocalDateTime.now(),a.getLocation(),amount,c.getC_Balance());
                if(transList.containsKey(c.getCustomer_Id())){
                    transList.get(c.getCustomer_Id()).add(trans);
                }
                else{
                    List<Trans> t = new ArrayList<>();
                    t.add(trans);
                    transList.put(c.getCustomer_Id(),t);
                }
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
        System.out.println(" -- Transaction info --");
        transList.forEach((k,v)-> System.out.println(v));
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
