package com.greenhouse.greenhouse_api.validator;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class UtilValidator {

    public String verifyString(String str) {
        if(str.trim().equals(null) || str.trim().equals("")) {
            return "String is empty";
        }else if(!hasNoSpecialChars(str)) {
            return "String has special characters!!!";
        }else {
            return "";
        }
    }

    public String verifyGender(String str) {
        if(str.trim().equalsIgnoreCase("Male") || str.trim().equalsIgnoreCase("Female")) {
            return "";
        }else {
            return "String isn't gender-related, please try again";
        }
    }

    public String verifyDependency(String str) {
        if(str.trim().equalsIgnoreCase("true") || str.trim().equalsIgnoreCase("false")) {
            return "";
        }else {
            return "String isn't a boolean value, please try again";
        }
    }

    public String verifyNumbers(double num) {
        return num >= 0 ? "" : "Number entered has a negative value";
    }

    public String verifyPassword(String str) {
        if(str.length() < 8) {
            return "Password has to be at least 8 characters";
        }else if(str.trim().isEmpty()) {
            return "Don't forget to enter password";
        }else {
            if(str.split("\\W").length < 2)
                return "Password has to have at least one special character!";
            else if(str.split("\\d").length < 2)
                return "Password has to have at least one number!";
            else return "";
        }
    }

    public String verifyEmail(String str) {
        String regex = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+$";
        if(str.matches(regex)) {
            return "";
        }else if(str.trim().equals("")){
            return "Don't forget to type in Email Address";
        }else {
            return "Email Address isn't valid";
        }
    }

    public boolean hasNoSpecialChars(String str) {
        for(int i = 0, j = str.length(); i < j; i++) {
            if(str.charAt(i) > 0 && str.charAt(i) < 32) {
                return false;
            }else if(str.charAt(i) > 32 && str.charAt(i) < 45) {
                return false;
            }else if(str.charAt(i) > 46 && str.charAt(i) < 65) {
                return false;
            }else if(str.charAt(i) > 90 && str.charAt(i) < 97) {
                return false;
            }else if(str.charAt(i) > 122 && str.charAt(i) < 128) {
                return false;
            }
        }
        return true;
    }

    public String validateDate(String str) {
        if(str.trim().isEmpty())
            return "Don't forget to type in Date of Birth";
        else {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            sdf.setLenient(false);

            try {
                Date javaDate = sdf.parse(str);
                System.out.println("This: " + str + " is a valid date");
            }catch(ParseException e) {
                System.out.println("Error message: " + e.getMessage());
                return "Mentioned Date isn't a valid one. Use the yyyy-MM-dd date format, please.";
            }
            return "";
        }
    }

    public Connection getConn() {
        String url = "jdbc:mysql://localhost:3306/greenhouse?useUnicode=yes&characterEncoding=UTF-8&useSSL=false&allowPublicKeyRetrieval=true";
        Connection con = null;
        System.out.println("Can you reach this?");
        try {
            con = DriverManager.getConnection(url, "root", "psycho");
            System.out.println("Did you get to this?");
        }catch(SQLException ex) {
            ex.printStackTrace();
        }
        return con;
    }

}
