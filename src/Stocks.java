import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;



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
    private final static String Path = "userdata/database/Stocknames.txt"; 
    private final static String PriceFile = "userdata/database/Money.txt";
    private final static String PRofitFile = "userdata/database/Proloss.txt";
    private final static String NoteFile = "userdata/database/NOTE.txt";
    
    public void DisplayOption(){
        String[] option = new String[] {"ADD      ","VIEW     ","DELETE   ","BALANCE  ","SAVE&EXIT"};
        for (int i = 0; i < option.length; i++){
            System.out.println("*|"+ (i+1) +"."+ option[i] + "        " + "|*");
        }  
    }
    public void ADD(){
        
        
        try{
            
            System.out.print("Enter StockName : ");
            Scanner sc = new Scanner(System.in);
            String  name =  sc.nextLine();
            System.out.println(" >>>>>>>>>>[  Stocks DATA ] <<<<<<<<<<<<<<");
            System.out.println("\nStocksName:" + name );
            try{
                System.out.println("Enter Price Where you Bought Stock : " + name);
                System.out.print("-->");
                int Entryprice = sc.nextInt();
                System.out.println("Enter Price Where you Sold  Stock : " + name);
                System.out.print("-->");
                int Exitprice = sc.nextInt(); 
                System.out.print("Enter  Quntity : ");
                int quntity  = sc.nextInt();
                int ProLoss = (Exitprice - Entryprice)*quntity;

                if(ProLoss < 0){
                    System.out.println("\n");
                    System.out.println(" YOU Loss $" + ProLoss);
                    String message = " DONT GIVE-UP ! LOSSES MAKE YOU PERFECT ";
                    try{
                        for(int i = 0; i < message.length(); i++){
                            System.out.print(message.charAt(i));
                            Thread.sleep(100);
                        }
                    }catch(InterruptedException e){
                        e.printStackTrace();
                    }
                    System.out.println("\n");

                }else{
                    System.out.println("YOUR PROFIT  $" + ProLoss );
                }
                FileWriter PRolossfile  = new FileWriter(PRofitFile,true);
                PRolossfile.write(ProLoss + "\n");
                PRolossfile.close();
                FileWriter DATA = new FileWriter(PriceFile,true); 
                DATA.write("ENTRY PRICE --> " + Entryprice  +"  "+ "EXIT PRICE --> " + Exitprice + " Profit/loss : " + ProLoss);
                DATA.write("\n");
                DATA.close();
                
                System.out.println("Enter Notes That Attached with Stock DATA : ");
                Scanner newsc  = new Scanner(System.in);
                String Notesdata = newsc.nextLine();
                
                
                
                FileWriter Notes  = new FileWriter(NoteFile,true);
                Notes.write(Notesdata+"\n");
                Notes.close();
            }catch (InputMismatchException i){
                System.out.println("Enter only number ");
                ADD();
            }
            

            FileWriter Pointer = new FileWriter(Path,true);
            Pointer.write(name+'\n');
            Pointer.close();
            System.out.println("---->DATA SAVED SUCCESSFULLY. ");
            
            
        }catch(IOException e){
            e.printStackTrace();

        }
    }
    
    public static void VIEW(){
        try {
            File Reader = new File(Path);
            Scanner View = new Scanner(Reader);
            File Maindata = new File(PriceFile);
            Scanner newView = new Scanner(Maindata);
            File Notes = new File(NoteFile);
            Scanner n = new Scanner(Notes);
            int i = 1;
            if(View.hasNextLine() != true){
                System.out.println("DATA is Empty . ");

            }else{
                while(View.hasNextLine()){
                    System.out.println("-------------------------------------------------------");
                    String Stockname = View.nextLine();
                    System.out.println("[Stockname] : ");
                    System.out.print(i+"."+Stockname);
                    System.out.println("\n");
                    String Data = newView.nextLine();
                    System.out.println(Data);
                    
                    System.out.println("                                   [NOTES]                     ");
                    if(n.hasNextLine()){
                        String newData = n.nextLine();
                        System.out.println(newData);
                    }else{
                        System.out.println("NO Note Attached with this Stocks !");
                    }
                    System.out.println("-------------------------------------------------------");
                    i++;
    
                }
            }
            

        } catch (IOException e) {
            System.out.println("Error openning File " + e);
        }catch(NoSuchElementException N) {
            System.out.println("Empty Notes");
        }
    }
    public void BALANCE(){
        try{
            File bal =  new File(PRofitFile);
            Scanner newbal  = new Scanner(bal);
            if(newbal.hasNextLine() != true){
                System.out.println("DATA is Empty . ");

            }else{
                int balance = 0;
                while(newbal.hasNextLine()){
                    String evryproloss = newbal.nextLine();
                    if(evryproloss.isEmpty()){
                        continue;
                    }
                    balance +=  Integer.parseInt(evryproloss);

                }
                System.out.println("\n");
                System.out.println("ALL TIME PROFIT/LOSS : " + balance);
                System.out.println("\n");
            }
            
        }catch(IOException e){
            e.printStackTrace();

        }
    }
    public static void removeLineFromFile(String filePath, int lineToRemove) throws IOException {
        Path path = Paths.get(filePath);
        List<String> lines = Files.readAllLines(path);

        if (lineToRemove > 0 && lineToRemove <= lines.size()) {
            lines.remove(lineToRemove - 1); // Remove the line (1-based index)
        }

        Files.write(path, lines);
    }

    public void DELETE() {
        
        Scanner enter = new Scanner(System.in);
        Scanner newEnterButton = new Scanner(System.in);

        try {
            File frereder = new File(Path);
            Scanner newviewer = new Scanner(frereder);
            if(newviewer.hasNextLine() != true ){
                System.out.println("DATA is Empty , We cant Delete Empty DATA.");
            }else{
                VIEW();
                System.out.println("\nRemoving Specific DATA from local database.\n");

                String[] files = { Path, PriceFile, PRofitFile, NoteFile };
                System.out.println("-------------------------------------------------------");

                System.out.print("Enter stock number to delete: ");
                int delnum = enter.nextInt();
                enter.nextLine();  // Consume the leftover newline

                System.out.println("[WARNING....] Removing Data");
                System.out.println("Do you want to remove the above data? If yes, press Enter. If not, type 'No'.");

                String confirmation = newEnterButton.nextLine().trim().toUpperCase();
                if ("NO".equals(confirmation)) {
                    System.out.println("DATA Protected.");
                } else {
                    for (String file : files) {
                        removeLineFromFile(file, delnum);
                    }
                    System.out.println("Data removed successfully.");
                }
            }
            
        } catch (InputMismatchException e) {
            System.out.println("Only number of stock allowed!");
            enter.nextLine();  
            DELETE();
        } catch (IOException e) {
            System.out.println("File not found: \n" + e);
        } catch (NoSuchElementException e) {
            System.out.println("No such element: \n" + e);
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
        int Choice = -1;
        try{
            Scanner Sc = new Scanner(System.in);
            Choice = Sc.nextInt();
            
        }catch(InputMismatchException Mis){
                        System.out.println("Not Allowed only Number Command Allowed ! Enter 1 ReStart menu ");
                        Getchoice();
        }
        
        if(Choice == -1 ){
            // we cant retrun any thing;

        }return Choice;

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
                System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>[Registration]<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");

                // registration
                getUserData();
                FileWriter writer = new FileWriter(userDataFile);
                writer.write("[User Data]\nName: " + newUser.getName() + "\nEmail: " + newUser.getEmail() +"\nPassword Hash[Format] = " + newUser.passwordHash() );
                System.out.println("\nRegistration Successfully\nNow Run Stock.class File");
                writer.close();

            }else {
                System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>[LOGIN]<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
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
                        Design(20);
                        Option MainOption =  new Option();
                        MainOption.DisplayOption();
                        Design(20);
                        System.out.println("\n");
                       
                        int i = 0;
                        int count = 0;
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
                        while(true){
                            
                            if(count != 0){
                                MenuThread.sleep(1000);
                                System.out.println("\n");
                                Design(20);
                                MainOption.DisplayOption();
                                Design(20);
                                System.out.println("\n");
                                System.out.println("\n");
                                System.out.print(message);

                            }

                            int choice = Getchoice();
                            count = 1;
                            if(choice == 5){
                                System.out.println("\n");
                                String Endcodemsg = "Thank you ! \nfor using STOCK MANAGEMENT SYSTEM\n                                                      __CODE WRITEN BY MITESH(Bunny)";
                                int at  = 0;
                                while(at < Endcodemsg.length()){
                                    System.out.print(Endcodemsg.charAt(at));
                                    Thread.sleep(100);
                                    at++;
                                }
                                break;

                            }

                            try {
                            
                                switch (choice) {
                                    case 1:
                                        MainOption.ADD();
                                        break;
                                    case 2:
                                        MainOption.VIEW();
                                        break;
                                    case 3:
                                        MainOption.DELETE();
                                        break;
                                    case 4:
                                        MainOption.BALANCE();
                                        break;
                                    case -1:
                                        System.out.println("Sorry plz try again ! ");
                                        break;
                                    default:
                                        System.out.println("Not found Command " + choice);
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
                                int secchoice = Getchoice();
                                switch (secchoice) {
                                    case 1:
                                        MainOption.ADD();
                                        break;
                                    case 2:
                                        MainOption.VIEW();  
                                        break;
                                    case 3:
                                        MainOption.DELETE();
                                        break;
                                    case 4:
                                        MainOption.BALANCE();
                                        break;
                                    case -1:
                                        System.out.println("Sorry plz try again Enter 1 to Open Menu ! ");
                                        break;
                                    default:
                                        System.out.println("Not found Command " + choice);
                                        break;
                                }

                            }
                        }
                        
                        

                    } catch (InterruptedException  e) {
                        System.out.println("Error at " + e); 
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
