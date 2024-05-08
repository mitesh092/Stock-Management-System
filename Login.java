import java.io.File;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

public class Login {
    static String hashmaker(String pass){
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
     
    static String getpass(){
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

        } catch (IOException e) {
            System.out.println("Error");
        }
        return passhax;

    }
    public static void main(String[] args) {
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
            
            System.out.println(name);
            System.out.println(getpass());
            Scanner passinput =  new Scanner(System.in);
            System.out.println("Enter pass :");
            String pass = passinput.nextLine();
            
            if(getpass().equals(hashmaker(pass))){
                System.out.println("Login Successfully");
            }else{
                System.out.println("failed Login Plz try again");
            }



        } catch (IOException e) {
            System.out.println("Error");
        }
        

    }
}
