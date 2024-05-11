import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.InputMismatchException;



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
    public String hashmaker(String pass){
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(pass.getBytes());
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
    public static String gethaxpass(){
        String filepath = "userdata/Hostname.txt";
        String passhax = "";
        int countLine = 0;
        try {
            File read = new File(filepath);
            Scanner sc = new Scanner(read);
            while(sc.hasNextLine()){
                String data = sc.nextLine();
                if(countLine == 3){
                    passhax = data.substring(23).trim();
                    break;
                }
                countLine ++;
            }
            sc.close();

        } catch (IOException e) {
            System.out.println("Error");
        }
        return passhax;

    }
    public String getname(){
        String filepath = "userdata/Hostname.txt";
        int countLine = 0;
        

        String name = "";
        try {
            File read = new File(filepath);
            Scanner sc = new Scanner(read);
            while(sc.hasNextLine()){
                String data = sc.nextLine();
                if(countLine == 1){
                    name = data.substring(5);
                    name = name.trim();
                    
                }
                countLine ++;
            }
            sc.close();
        }catch(IOException e){
            System.out.println("File : " +  filepath);
            System.out.println("Data fetching Error : "+ e);
        }
        return name;
    }
    
    
    
}

class Option {
    
    public void DisplayOption(){
        String[] option = new String[] {"ADD   ","EDIT  ","VIEW  ","DELETE"};
        for (int i = 0; i < option.length; i++){
            System.out.println("*|"+ (i+1) +"."+ option[i] + "        " + "|*");
        }  
    }
    
}
class Menu extends Thread{
    private String message = "Welcome Back "; 
    

    public Menu(String name){
        message += name;
    }
    public void run(){
        int i = 0;
        while(i < message.length()){
            System.out.print(message.charAt(i));
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                System.out.println(e);
            }
            i++;
        }
    }

    public void menuOptions(){
        Stocks.Design();
    }
    
    
}



public class Stocks {
    
    private static RegistrationUser newUser;
    // String password to hash String Conversion Method
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
        System.out.println("");
        System.out.print("Enter your Username: ");
        String name = sc.nextLine();
        System.out.print("Enter Your Email: ");
        String email = sc.nextLine();
        System.out.print("Enter password That contains (Special char, number & alphabetic): ");
        String password = sc.nextLine();
        sc.close();
        newUser = new RegistrationUser(name, email, password);
    }
    public static void Design (){
        int i = 0;
        while(i < 100){
            System.out.print("*");
            i++;
        }
    }
    //overload Design method 
    public static void Design(int i){
        System.out.print("*|");
        int j = 0;
        while(j < i-1){
            System.out.print("*");
            j++;
        }
        System.out.println("|*");
    }

    // Get choice in Main Menu
    public static int Getchoice(){
    
        Scanner Sc = new Scanner(System.in);
        int Choice = Sc.nextInt();

        return Choice;

    }

    
    public static void main(String[] args) {
        try {
            Design();
            System.out.println();
            int j = 0;
            while (j < 2) {
               
                System.out.println("|*"+"                                                                                                |* ");
                j++;
                
            }
            
           System.out.println("|*                                  STOCKS MANAGEMENT SYSTEM                                      |*");
            j = 0;
            while (j < 2) {                 
                System.out.println("|*"+"                                                                                                |* ");
                j++;
                
            }
            Design(); 
            System.out.println("");
            File userDataFile = new File("userdata/Hostname.txt");
            if (userDataFile.createNewFile()) {
                getUserData();
                FileWriter writer = new FileWriter(userDataFile);
                writer.write("[User Data]\nName: " + newUser.getName() + "\nEmail: " + newUser.getEmail() +"\nPassword Hash[Format] = " + newUser.passwordHash() );
                writer.close();

            }else {
                Login vaild = new Login();
                Scanner vaildData = new Scanner(System.in);
                System.out.print("Enter Your Username : ");
                String name = vaildData.next();
                System.out.println("Enter your password : ");
                String pass = vaildData.next();
                //vaildData.close();
                 
                if(vaild.getname().equals(name) && vaild.gethaxpass().equals(vaild.hashmaker(pass))){
                    System.out.println("Login Successfully");
                    Menu MenuThread = new Menu(name);
                    
                    MenuThread.menuOptions();
                    System.out.println();
                    System.out.print("|*                                     ");
                    MenuThread.start();
                    try{
                        MenuThread.sleep(2000);
                    }catch(InterruptedException I){
                        System.out.println("MenuThread Error !");
                    }
                    System.out.print("                                         *|");
                    System.out.println();
                    Design();
                    try {
                        MenuThread.sleep(1000);
                        System.out.println("\n");
                        Design(17);
                        Option MainOption =  new Option();
                        MainOption.DisplayOption();
                        Design(17);
                        System.out.println("\n");
                       
                        int i = 0;
                        String message = "--->ENTER YOUR CHOICE :" ;
                        while(i < message.length()){
                            System.out.print(message.charAt(i));
                            try {
                                MenuThread.sleep(100);
                            
                            } catch (InterruptedException e) {
                                System.out.println(e);
                            }
                            i++;
                        }
                        
                        try {
                            int choice = Getchoice();
                            switch (choice) {
                                case 1:
                                    //call method add data
                                    break;
                                case 2:
                                    //call method Edit data 
                                    break;
                                case 3:
                                    //call method view data 
                                    break;
                                case 4:
                                    //call method delete data
                                default:
                                    break;
                            }
                            
                        } catch (InputMismatchException e) {
                            System.out.println("ERROR : ONLY Numberic Choice allowed ");
                            i = 0;
                            message = "--->ENTER YOUR CHOICE : " ;
                            while(i < message.length()){
                                System.out.print(message.charAt(i));
                                try {
                                    MenuThread.sleep(100);
                                
                                } catch (InterruptedException interup) {
                                    System.out.println(interup);
                                }
                                i++;
                            }
                            int choice = Getchoice();
                            switch (choice) {
                                case 1:
                                    //call method add data
                                    break;
                                case 2:
                                    //call method Edit data 
                                    break;
                                case 3:
                                    //call method view data 
                                    break;
                                case 4:
                                    //call method delete data
                                default:
                                    break;
                            }
                            
                        }
                        
                        

                    } catch (InterruptedException  e) {
                        System.out.println("Error at MenuTread");
                    }
              
                }else{
                    System.out.println("failed Login Plz try again");
                }
            }
                
            
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    
    }
}
