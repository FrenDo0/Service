import java.util.Map;
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
            Users user = new Clients();

            if (user.isExisting(username, pass)) {

                String role = user.checkRole(username, pass);
                System.out.println("You are logged successfully !");

                    //Client log in
                if (role.equals("client")) {
                    System.out.println("Commands: 1 - Create request  2 - View all your requests  3 - Edit request  4 - Remove request");
                    Scanner scn = new Scanner(System.in);
                    int num = scn.nextInt();
                    if (num == 1) {
                        System.out.println("Enter information below");
                        int clientID = user.GetUserID(username);
                        int vinNum = 0;
                        int serviceID = 0;
                        String serviceName = "";
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
                        for(Map.Entry brands : user.PrintAllBrandAndModels().entrySet()){
                            System.out.println(brands.toString().replace("{","").replace("}","")
                                    .replace("="," - ").replace("[","").replace("]",""));
                        }
                        scanner.nextLine();
                        brand = scanner.nextLine();
                        System.out.println("Enter model from ones below");
                        for(Map.Entry models : user.PrintAllBrandAndModels().entrySet()){
                            System.out.println(models.toString().replace("{","").replace("}","")
                                    .replace("="," - ").replace("[","").replace("]",""));
                        }
                        model = scanner.nextLine();
                        brandID = user.GetBrandID(brand);
                        modelID = user.GetModelID(model);
                        System.out.println("Enter date which you will leave the car");
                        dateLeave = scanner.nextLine();
                        System.out.println("Enter date when you will pick up the car");
                        datePickUp = scanner.nextLine();
                        System.out.println("Enter service");
                        System.out.println(user.printServices().toString().replace("[","")
                                .replace("]","").replace(",","\n"));
                        serviceName = scanner.nextLine();
                        serviceID = user.GetServiceID(serviceName);

                        Clients client = new Clients();
                        client.CreateRequest(clientID, vinNum, brandID, modelID,serviceID, dateLeave, datePickUp);

                    }else if(num == 2){
                        //View request

                        System.out.println("All your requests");
                        Clients cl = new Clients();
                        System.out.println(cl.printRequestByClientID(user.GetUserID(username)).toString().replace("[","")
                                .replace("]","").replace(",",""));

                    }else if(num == 3){
                        //Edit request
                        System.out.println("Enter the id of the request you want to edit");
                        Scanner requestID = new Scanner(System.in);
                        int reqID = requestID.nextInt();
                        System.out.println("Enter information below");
                        int clientID = user.GetUserID(username);
                        int vinNum = 0;
                        int serviceID = 0;
                        String serviceName = "";
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
                        for(Map.Entry brands : user.PrintAllBrandAndModels().entrySet()){
                            System.out.println(brands.toString().replace("{","").replace("}","")
                                    .replace("="," - ").replace("[","").replace("]",""));
                        }
                        scanner.nextLine();
                        brand = scanner.nextLine();
                        System.out.println("Enter model from ones below");
                        for(Map.Entry models : user.PrintAllBrandAndModels().entrySet()){
                            System.out.println(models.toString().replace("{","").replace("}","")
                                    .replace("="," - ").replace("[","").replace("]",""));
                        }
                        model = scanner.nextLine();
                        brandID = user.GetBrandID(brand);
                        modelID = user.GetModelID(model);
                        System.out.println("Enter date which you will leave the car");
                        dateLeave = scanner.nextLine();
                        System.out.println("Enter date when you will pick up the car");
                        datePickUp = scanner.nextLine();
                        System.out.println("Enter service");
                        System.out.println(user.printServices().toString().replace("[","")
                                .replace("]","").replace(",","\n"));
                        serviceName = scanner.nextLine();
                        serviceID = user.GetServiceID(serviceName);

                        Clients client = new Clients();
                        client.updateRequest(reqID,vinNum,brandID,modelID,serviceID,dateLeave,datePickUp);
                        System.out.println("Your request has been updated successfully");

                    }else if(num == 4){
                        //Remove request
                        System.out.println("Enter the id of the request you want to delete");
                        Scanner scanner = new Scanner(System.in);
                        int reqID = scanner.nextInt();
                        user.deleteRequestByID(reqID);
                        System.out.println("Your request has been deleted");
                    }

                    //Worker log in
                } else if (role.equals("worker")) {
                    System.out.println("w");
                }

            } else if (!user.isExisting(username, pass)) {
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
