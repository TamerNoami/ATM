import com.tamer.AtmUnit;
import com.tamer.Ops;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

public class ATMservlet extends HttpServlet {

    String title = "Hello World";
    Ops ops = new Ops();


     public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String message = "";

         message = loginScr();

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        String docType =
                "<!doctype html public \"-//w3c//dtd html 4.0 " + "transitional//en\">\n";

        out.println(docType +
                "<html>\n" +
                "<head><title>" + title + "</title></head>\n" +
                "<body bgcolor = \"#f0f0f0\">\n" +
                "<h1 align = \"center\">" + title + "</h1>\n" +
                message+
                "</body>" +
                "</html>"
        );
    }


    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException {
        String message = "";

        String pos = req.getParameter("pos");

        System.out.println("Pos !!"+ pos);
        if(pos==null)
        message = loginScr();
        else
        {
            switch (pos){
                case "1":
                    message = login(req.getParameter("cardNo"),req.getParameter("pass"));
                    break;
                case "2":
                    String opt = req.getParameter("optNo");
                    switch (opt){
                        case "1":
                        case "2":
                        message = amountOpt();
                    }

            }


        }

        res.setContentType("text/html");
        PrintWriter out = res.getWriter();
        String docType =
                "<!doctype html public \"-//w3c//dtd html 4.0 " + "transitional//en\">\n";

        out.println(docType +
                "<html>\n" +
                "<head><title>" + title + "</title></head>\n" +
                "<body bgcolor = \"#f0f0f0\">\n" +
                "<h1 align = \"center\">" + title + "</h1>\n" +
                message+
                "</body>" +
                "</html>"
        );
    }


    private String loginScr() {
        String atmList = atmList();
        String returnStr;
        returnStr  = "<form action = \"ATMservlet\" method = \"POST\">\n";
        returnStr += "    <input type = \"hidden\" name = \"pos\" value = \"1\">\n";
        returnStr += "    <h1>Welcome to MyBank</h1>\n";
        returnStr += "    <h3></h3>\n";
        returnStr += atmList;
        returnStr += "    ATM #:       <input type = \"text\" name = \"atmNo\">\n";
        returnStr += "\n   Card Number: <input type = \"text\" name = \"cardNo\">";
        returnStr += "    <br />";
        returnStr += "    Password:    <input type = \"password\" name = \"pass\">\n";
        returnStr += "    <br />";
        returnStr += "    <input type = \"submit\" value = \"Submit\" />\n";
        returnStr += "</form>";
        title = "Login";
        return returnStr;
    }

    private String login(String cardNo, String password) {
        String returnStr;
        Boolean logIn = ops.checkLogin(cardNo,password);
        if (!logIn) {
            returnStr = loginScr();
        } else {
            String uname = ops.getCustomers().get(cardNo).getName();

            returnStr  = "<form action = \"ATMservlet\" method = \"POST\">\n";
            returnStr += "    <input type = \"hidden\" name = \"pos\" value = \"2\">\n";
            returnStr += "    <input type = \"hidden\" name = \"username\" value = \""+uname+"\">\n";
            returnStr += "    <h1>Hi "+uname+" you're in!</h1>\n";
            returnStr += "    <h2>           Menu Option    </h2>\n";
            returnStr += "    <h3> 1) Withdraw </h3>\n";
            returnStr += "    <h3> 2) Deposit </h3>\n";
            returnStr += "    <h3> 3) Check your credit </h3>\n";
            returnStr += "    <h3> 4) Print your transactions </h3>\n";
            returnStr += "    <h3> 5) Exit </h3>\n";
            returnStr += "    Option #:<input type = \"text\" name = \"optNo\">\n";
            returnStr += "    <br />";
            returnStr += "    <input type = \"submit\" value = \"Submit\" />\n";
            returnStr += "</form>";
            title = "Logged in";
        }
        return returnStr;

    }

    private String atmList() {
        String retString ="";
        HashMap<Integer, AtmUnit> atm = ops.getAtmlist();
        for(Map.Entry<Integer,AtmUnit> entry : atm.entrySet()) {
            retString += "<h2>"+entry.getKey()+ "  "+entry.getValue().getLocation()+"</h2>\n";
                    }
        return retString;
    }


    private String amountOpt() {

        String returnStr;
        returnStr  = "<form action = \"ATMservlet\" method = \"POST\">\n";
        returnStr += "    <input type = \"hidden\" name = \"pos\" value = \"3\">\n";
        returnStr +="<p>Please select the amount:</p>";
        returnStr +="<input type=\"radio\" name=\"amount\" value=\"100\"> 100<br>\n";
        returnStr +="<input type=\"radio\" name=\"amount\" value=\"200\"> 200<br>\n";
        returnStr +="<input type=\"radio\" name=\"amount\" value=\"300\"> 300<br>\n";
        returnStr +="<input type=\"radio\" name=\"amount\" value=\"500\"> 500<br>\n";
        returnStr +="<input type=\"radio\" name=\"amount\" value=\"1000\"> 1000<br>\n";
        returnStr +="<input type=\"radio\" name=\"amount\" value=\"2000\"> 2000<br>\n";

        returnStr += "    <input type = \"submit\" value = \"Submit\" />\n";
        returnStr += "</form>";
        title = "Amount Options";
        return returnStr;
    }
}
