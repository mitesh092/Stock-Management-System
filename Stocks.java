import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

class RegistrationUser {
    private String name, email;
    final public String password;

    public RegistrationUser(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public String passwordHash() {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(password.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (int i = 0; i < hash.length; i++) {
                String hex = Integer.toHexString(0xff & hash[i]);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }

            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }
}

class Login {
    private StringBuilder username ;
    private StringBuilder haxpass ;
    public Login(){
        try{
            File ReadData =  new File("userdata/Hostname.txt");
            Scanner Reader =  new Scanner(ReadData);
            int countLine =  0;
            
            
            while(Reader.hasNextLine()){
                
                switch(countLine){
                    case 1:
                        String nameFullLine = Reader.nextLine();
                        StringBuilder name = new StringBuilder();
                        for(int i = 0; i < nameFullLine.length(); i++){
                            if(nameFullLine.charAt(i) == ':'){
                                name.append(nameFullLine.charAt(i));
                                username = name;
                            }
                        }
                    case 3:
                        String password = Reader.nextLine();
                        StringBuilder pass = new StringBuilder();
                        for(int i = 0; i < password.length(); i++){
                            if(pass.charAt(i) == '='){
                                pass.append(password.charAt(i));
                                haxpass = pass;
                            }
                        }
                        
                }
                countLine++;
                
            }
        }catch(IOException e){
            System.out.println("Error in Line number 77");
        }
    }
    
    public StringBuilder getpass(){
        return haxpass;
    }
    public StringBuilder getusername(){
        return username;
    }
    
    
}



public class Stocks {
    private static RegistrationUser newUser;
    public static String hash(String input) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(input.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                hexString.append(String.format("%02x", b));
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static void getUserData() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter your name: ");
        String name = sc.nextLine();
        System.out.print("Enter Your Email: ");
        String email = sc.nextLine();
        System.out.println("Enter password That contains (Special char, number & alphabetic): ");
        String password = sc.nextLine();
        sc.close();
        newUser = new RegistrationUser(name, email, password);
    }

    public static void main(String[] args) {
        try {
            File userDataFile = new File("userdata/Hostname.txt");
            if (userDataFile.createNewFile()) {
                getUserData();
                FileWriter writer = new FileWriter(userDataFile);
                writer.write("[User Data]\nName: " + newUser.getName() + "\nEmail: " + newUser.getEmail() +"\nPassword Hash[Format] = " + newUser.passwordHash() );
                writer.close();

            } else {
                Login vaild = new Login();
                Scanner vaildData = new Scanner(System.in);
                System.out.print("Enter Your name");
                String name = vaildData.next();
                System.out.println("Enter your password");
                String pass = vaildData.next();
                
                if(vaild.getusername().equals(name) && vaild.getpass().equals(hash(pass.replace("=","")))){
                    System.out.println("login Successfully");

                }else{
                    System.out.println("login Faild");
                }

                
                
            }
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    
    }
}
