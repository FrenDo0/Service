
import java.sql.*;
import java.util.Arrays;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        System.out.println("Welcome to our service portal");
        System.out.println("Useful commands:  1 - Log in  2 - Register as worker  3 - Register as client");
        Scanner sc = new Scanner(System.in);
        int input = sc.nextInt();

        if (input == 1) {

            System.out.println("Enter username: ");
            Scanner str = new Scanner(System.in);
            String username = str.nextLine();
            System.out.println("Enter password: ");
            String pass = str.nextLine();
            Users us = new Clients();

            if (us.isExisting(username, pass)) {

                String role = us.checkRole(username, pass);
                System.out.println("You are logged successfully !");

                if (role.equals("client")) {
                    System.out.println("Commands: 1 - Create request ");
                    Scanner scn = new Scanner(System.in);
                    int num = scn.nextInt();
                    if (num == 1) {
                        System.out.println("Enter information below");
                        //int clientID,int vinNum,int brandID, int modelID, String dateLeave, String datePickUp
                        int clientID = us.GetUserID(username);
                        int vinNum = 0;
                        int brandID = 0;
                        String brand = "";
                        String model = "";
                        int modelID = 0;
                        String dateLeave = "";
                        String datePickUp = "";
                        Scanner scanner = new Scanner(System.in);
                        System.out.println("Enter car vin number");
                        vinNum = scanner.nextInt();
                        System.out.println("Enter brand from ones below");
                        System.out.println(us.PrintAllBrandAndModels());
                        scanner.nextLine();
                        brand = scanner.nextLine();
                        System.out.println("Enter model from ones below");
                        System.out.println(us.PrintAllBrandAndModels());
                        model = scanner.nextLine();
                        brandID = us.GetBrandID(brand);
                        modelID = us.GetModelID(model);
                        System.out.println("Enter date which you will leave the car");
                        dateLeave = scanner.nextLine();
                        System.out.println("Enter date when you will pick up the car");
                        datePickUp = scanner.nextLine();

                        Clients client = new Clients();
                        client.CreateRequest(clientID, vinNum, brandID, modelID,"service", dateLeave, datePickUp);

                    }

                } else if (role.equals("worker")) {
                    System.out.println("w");
                }

            } else if (!us.isExisting(username, pass)) {
                System.out.println("Wrong username or password !");
            }

        } else if (input == 2) {

            System.out.println("Welcome to our register panel !");
            System.out.println("Fill the information below");
            System.out.println("First name: ");
            Scanner str = new Scanner(System.in);
            String fname = str.nextLine();
            System.out.println("Second name: ");
            String sname = str.nextLine();
            System.out.println("Username: ");
            String username = str.nextLine();
            System.out.println("Password: ");
            String pass = str.nextLine();
            Users worker = new Workers();
            worker.setFirstName(fname);
            worker.setSecondName(sname);
            worker.setUsername(username);
            worker.setPassword(pass);
            Workers wk = new Workers();
            wk.CreateNewProfile(worker);

        } else if (input == 3) {

            System.out.println("Welcome to our register panel !");
            System.out.println("Fill the information below");
            System.out.println("First name: ");
            Scanner str = new Scanner(System.in);
            String fname = str.nextLine();
            System.out.println("Second name: ");
            String sname = str.nextLine();
            System.out.println("Username: ");
            String username = str.nextLine();
            System.out.println("Password: ");
            String pass = str.nextLine();
            Users client = new Workers();
            client.setFirstName(fname);
            client.setSecondName(sname);
            client.setUsername(username);
            client.setPassword(pass);
            Clients cl = new Clients();
            cl.CreateNewProfile(client);
        }

    }

}
