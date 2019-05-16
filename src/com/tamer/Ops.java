package com.tamer;

import com.sun.xml.internal.ws.util.StringUtils;

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
        StdIO.writeLine(ConsoleColors.BLUE_BOLD+"ATM ID" + "     ATM LOCATION");
        StdIO.writeLine("----------------------------------"+ConsoleColors.RESET);
        atmlist.forEach((k, v) -> System.out.println(k + "   " + v.getLocation()));
        StdIO.writeLine("");
        StdIO.writeLine(ConsoleColors.BLACK_BOLD+"Chose ATM Id you want to widhraw from"+ConsoleColors.RESET);
        try {
            int atmNo = Integer.parseInt(StdIO.realLine());
            if (atmlist.containsKey(atmNo))
                login(atmNo);
            else {
                StdIO.writeLine(ConsoleColors.RED_BOLD_BRIGHT+"Wrong ATM ID ... Please re-enter"+ConsoleColors.RESET);
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
            StdIO.writeLine(ConsoleColors.PURPLE_BACKGROUND_BRIGHT+"-- Login --"+ConsoleColors.RESET);
            StdIO.writeLine("");
            StdIO.writeLine(ConsoleColors.CYAN_BOLD+"** Welocme to My Bank **"+ConsoleColors.RESET);
            StdIO.writeLine(ConsoleColors.BLACK_BOLD+"Insert your card -- Enter your card number --");
            String customerId = StdIO.realLine();
            StdIO.write("Password : "+ConsoleColors.RESET);
            String password = StdIO.realLine();
            loginCount++;
            boolean ok = checkLogin(customerId, password);
            if (ok) {
                StdIO.writeLine(ConsoleColors.PURPLE_BOLD+"Welcom "+customers.get(customerId).getName()+ConsoleColors.RESET);
                optionScreen(customerId, atmno);//widhraw(cardId, atmno);
                }
            else {
                StdIO.writeLine(ConsoleColors.RED_BOLD_BRIGHT+"Wrong Info ... please re-enter"+ConsoleColors.RESET);
                continue;
            }

        }

    }

    private void optionScreen(String customerId, int atmNo) throws IOException {
        StdIO.writeLine("");
        StdIO.writeLine("Options");
        StdIO.writeLine("1) Withdraw");
        StdIO.writeLine("2) Deposit");
        StdIO.writeLine("3) Check your credit");
        StdIO.writeLine("4) Print your transactions");
        StdIO.writeLine("5) Change your pass code");
        StdIO.writeLine("0) Exit");
        StdIO.write(">> ");
        String menuOption = StdIO.realLine();
        switch (menuOption){
            case "1":widhraw(customerId, atmNo);optionScreen(customerId,atmNo);break;
            case "2":Deposit(customerId, atmNo);optionScreen(customerId,atmNo);break;
            case "3":StdIO.writeLine("You have "+ customers.get(customerId).getC_Balance()+ " SEK remains in your account");optionScreen(customerId,atmNo);break;
            case "4":transPrint(customerId);optionScreen(customerId,atmNo);break;
            case "5":changePass(customerId);optionScreen(customerId,atmNo);break;
            case "0":{StdIO.writeLine("Thanks for using our Bank ATM");System.exit(0);}
            default: StdIO.writeLine("Wrong Option number");optionScreen(customerId,atmNo);break;
        }
    }

    private void changePass(String customerId) throws IOException {
        StdIO.writeLine("Please enter your old password ");
        StdIO.write(">> ");
        String oldPass =StdIO.realLine();
        if(checkLogin(customerId,oldPass)){
            String newPass=checkPass();
            customers.get(customerId).setPassword(newPass);
            StdIO.writeLine("Password changed successfully");
              }
    }

    private String checkPass() throws IOException {
        StdIO.writeLine("Enter your new password .. 4 numbers only");
        StdIO.write(">> ");
        String newPass1=StdIO.realLine();
        StdIO.writeLine("Repeat you new password");
        String newPass2=StdIO.realLine();

        if(newPass1.length()!=4 || newPass2.length()!=4 ){
                StdIO.writeLine("Only four digits are allowed");
                checkPass() ;}
        try{
        int temp = Integer.parseInt(newPass1);
        }catch (NumberFormatException e){
                StdIO.writeLine("Only numbers are allowed  ");
                checkPass() ;
        }

        if(!newPass1.equals(newPass2)) {
            StdIO.writeLine("Wrong password");
            checkPass() ;
        }
        else
            return newPass1;
        return newPass1;
    }

    private void transPrint(String cardId) {
        List<Trans> trans = transList.get(cardId);
        StdIO.writeLine("** Transaction **\n");
        StdIO.writeLine("\t\t"+ customers.get(cardId).getName());
        StdIO.writeLine("  No. \t Date \t\t Time \t\t Amount \t Balance \t Location");
       if(!transList.containsKey(cardId))
           StdIO.writeLine("\n No transactions");
       else
        for (Trans t:trans){
            StdIO.writeLine(t.getTransNo()+"\t"+t.getTransTime().toString().substring(0,10) + "\t" + t.getTransTime().toString().substring(11,19) + "\t  " + t.getAmount() + "\t" + t.getBalance() + "\t" + t.getAtmLocation());
        }
        StdIO.writeLine("");
        StdIO.write("---------------------------------------------------------------------");
    }

    private void Deposit(String cardId, int atmno) throws IOException {
        StdIO.writeLine(" -- Deposit -- ");
        StdIO.writeLine("Please chose the amount you want to deposit :");
        int depAmount =ATMRules();
        Customer customer =  customers.get(cardId);
        AtmUnit atm = atmlist.get(atmno);
        customer.setC_Balance(customer.getC_Balance()+depAmount);
        writeTrans(depAmount, customer, atm);
        StdIO.writeLine("Thanks for depositing ");
    }

    private void writeTrans(int depAmount, Customer customer, AtmUnit atm) {
        String ran = String.valueOf(random.nextInt(1999999-1000000)+1000000);
        Trans trans = new Trans(ran,customer.getCustomer_Id(), LocalDateTime.now(),atm.getLocation(),depAmount,customer.getC_Balance());
        if(transList.containsKey(customer.getCustomer_Id())){
            transList.get(customer.getCustomer_Id()).add(trans);
        }
        else{
            List<Trans> t = new ArrayList<>();
            t.add(trans);
            transList.put(customer.getCustomer_Id(),t);
        }
    }

    private void widhraw(String cardId, int atmNo) throws IOException {
        int amount = 0;
        Customer c = customers.get(cardId);
        AtmUnit a = atmlist.get(atmNo);
        // System.out.println("User Info " + c.toString());
        // System.out.println("ATM Info" + a.toString());

        StdIO.writeLine("");
        StdIO.writeLine(ConsoleColors.BLACK_BOLD+"Please choose the amonut : "+ConsoleColors.RESET);
        amount = ATMRules();

        if (amount < c.getC_Balance()) {
            if (amount < a.getAtmBalnce()) {
                StdIO.writeLine("");
                StdIO.writeLine(ConsoleColors.BLACK_BOLD_BRIGHT+"Withdrawing in progress .... "+ConsoleColors.RESET);
                StdIO.writeLine(ConsoleColors.BLACK_BOLD+"Please collect your card and money"+ConsoleColors.RESET);
                c.setC_Balance(c.getC_Balance()-amount);
                a.setAtmBalnce(a.getAtmBalnce()-amount);
                writeTrans(amount, c, a);
                optionScreen(cardId,atmNo);
            }
            else {
                StdIO.writeLine(ConsoleColors.RED_BOLD_BRIGHT+"Sorry ... not enough cash at the moment in the ATM"+ConsoleColors.RESET);
                StdIO.writeLine("You can widhraw up to " + atmlist.get(atmNo).getAtmBalnce());
            }
        } else {
            StdIO.writeLine(ConsoleColors.RED_BOLD_BRIGHT+"Sorry .... no sufficient fund in your account"+ConsoleColors.RESET);
            StdIO.writeLine("You have " +customers.get(cardId).getC_Balance()+" Sek remains in your account" );
            System.exit(0);
        }
//        System.out.println("User Info " + customers.get(cardId).toString());
//        System.out.println("ATM Info" + atmlist.get(atmno).toString());
//        System.out.println(" -- Transaction info --");
//        transList.forEach((k,v)-> System.out.println(v));

        AtmUsing();// System.exit(0);
    }

    private int ATMRules() throws IOException {
        int amount=0;
        StdIO.writeLine(ConsoleColors.GREEN_BOLD+"1) 100 \t\t 4)500");
        StdIO.writeLine("2) 200 \t\t 5)1000");
        StdIO.writeLine("3) 300 \t\t 6)another amount");
        StdIO.write(">> "+ConsoleColors.RESET);
        String opt = StdIO.realLine();
        switch (opt){
            case "1":return 100;
            case "2":return 200;
            case "3":return 300;
            case "4":return 500;
            case "5":return 1000;
            case "6":
                StdIO.write(ConsoleColors.BLACK_BOLD+"Enter the amount you want : "+ConsoleColors.RESET);
                amount = Integer.parseInt(StdIO.realLine());

                if (amount%100==0 && amount<=2000){

                return amount;}
                else{
                    StdIO.writeLine(ConsoleColors.RED_BOLD_BRIGHT+"Please enter amount of hundreds only up to 2000 Sek"+ConsoleColors.RESET);
                    return ATMRules();
                }
                default:
                    StdIO.writeLine(ConsoleColors.RED_BOLD_BRIGHT+"Wrong option number please try again"+ConsoleColors.RESET);
                    amount=ATMRules();

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
