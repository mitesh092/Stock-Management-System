import java.net.InetAddress;
import java.net.UnknownHostException;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;


class RegistrationUser {
    private String name, email, password;

    RegistrationUser(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }
}



public class Stocks {
    private static RegistrationUser newUser;

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
        String hostname = "Unknown";
        try {
            InetAddress addr = InetAddress.getLocalHost();
            hostname = addr.getHostName();
            try {
                File userDataFile = new File("userdata/Hostname.txt");
                if (userDataFile.createNewFile()) {
                    getUserData();
                    FileWriter writer = new FileWriter(userDataFile);
                    writer.write("[User Data]\nName: " + newUser.getName() + "\nEmail: " + newUser.getEmail());
                    writer.close();
                } else {
                    // make here login 
                }
            } catch (IOException e) {
                System.out.println("Error: " + e.getMessage());
            }
        } catch (UnknownHostException ex) {
            System.out.println("Hostname cannot be resolved.");
        }
    }
}
