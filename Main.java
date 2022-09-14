import java.util.Map;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws InterruptedException {

        System.out.println("Welcome to our service portal");
        System.out.println("Useful commands");
        System.out.println(" 0 -> Exit \n 1 -> Log in \n 2 -> Register as client \n 3 -> Register as worker");
        Scanner scanner = new Scanner(System.in);
        int input = scanner.nextInt();

        while(input != 0){

                //Log in
            if(input == 1){
                System.out.println("Enter username and password below");
                System.out.println("Username: ");
                Scanner sc = new Scanner(System.in);
                String username = sc.nextLine();
                System.out.println("Password: ");
                String password = sc.nextLine();
                Users user = new Clients();
                    //Existing
                if(user.isExisting(username,password)){

                        //Logged as client
                    if(user.checkRole(username,password).equals("client")){

                        System.out.println("You are logged as client !");
                        System.out.println("Commands you can use");
                        System.out.println(" 1 -> Create request \n 2 -> View your requests \n 3 -> Check services");
                        Scanner sc1 = new Scanner(System.in);
                        int userInput = sc1.nextInt();
                                //Create request
                            if(userInput == 1){

                                System.out.println("Enter information below");
                                System.out.println("Enter car brand");

                                for(Map.Entry brandAndModels : user.printAllBrandAndModels().entrySet()){
                                    System.out.println(brandAndModels.toString().replace("{","").replace("}","")
                                            .replace("="," - ").replace("[","").replace("]",""));
                                }

                                Scanner sc2 = new Scanner(System.in);
                                String brand = sc2.nextLine();
                                System.out.println("Enter car model");
                                int brandID = Integer.parseInt(user.getBrandID(brand).toString().replace("[","").replace("]",""));
                                System.out.println(user.getModelsByBrand(brandID));
                                String model = sc2.nextLine();
                                int modelID = Integer.parseInt(user.getModelID(model).toString().replace("[","").replace("]",""));
                                System.out.println("Choose service");
                                System.out.println(user.printServices());
                                String service = sc2.nextLine();
                                int serviceID = Integer.parseInt(user.getServiceID(service).toString().replace("[","").replace("]",""));
                                System.out.println("Date of leaving");
                                String dateLeave = sc2.nextLine();
                                System.out.println("Dat to pick up your car");
                                String datePick = sc2.nextLine();
                                System.out.println("Enter car VIN number");
                                int carVin = sc2.nextInt();
                                Clients client = new Clients();
                                int clientID = Integer.parseInt(user.getUserIDNumber(username).toString().replace("[","").replace("]",""));
                                client.createRequest(clientID,carVin, brandID,modelID,serviceID,dateLeave,datePick);
                                System.out.println("Your request has been sent !");
                                input = scanner.nextInt();

                                //View requests
                            }else if(userInput == 2){
                                System.out.println("Here are all your requests");
                                Clients client = new Clients();
                                int clientID = Integer.parseInt(client.getUserIDNumber(username).toString().replace("[","").replace("]",""));
                                System.out.println(client.printRequestByClient(clientID).toString().replace("[","").replace("]","").replace(",",""));

                                //View services
                            }else if(userInput == 3){
                                System.out.println("Those are all services we currently provide");
                                System.out.println(user.printServices().toString().replace("[","").replace("]",""));
                            }

                            //Logged as worker
                    }else if(user.checkRole(username,password).equals("worker")){

                        System.out.println("You are logged as worker !");
                        System.out.println("Welcome to work !");
                        System.out.println(" 1 -> Open request section \n 2 -> Open user section \n 3 -> Open service section");
                        Scanner scanner1 = new Scanner(System.in);
                        Workers worker = new Workers();
                        int workerInput = scanner1.nextInt();
                        Scanner scanner2 = new Scanner(System.in);

                            //Open request section
                        if(workerInput == 1){
                            System.out.println(" 1 -> View all requests  \n 2 -> Sort request by status \n " +
                                    "3 -> Change request status \n 4 -> Delete request by id");
                            int wkInput = scanner2.nextInt();

                                //View all requests
                            if(wkInput == 1){
                                System.out.println(worker.printAllRequests().toString().replace("[","").replace(",","").replace("]",""));

                                //Sort requests by status
                            }else if(wkInput == 2){
                                System.out.println("To see sorted requests write request status");
                                System.out.println("Request statuses: done,returned,pending,accepted");
                                scanner2.nextLine();
                                String status = scanner2.nextLine();
                                System.out.println(worker.printSortedRequests(status).toString().replace("[","").replace(",","").replace("]",""));

                                //Change request status
                            }else if(wkInput == 3){
                                System.out.println("Update request status by typing request id");
                                System.out.println(worker.printAllRequests().toString().replace("[","").replace(",","").replace("]",""));
                                System.out.println("Write request id");
                                int requestID = scanner2.nextInt();
                                System.out.println("Set the status to: done,returned,accepted");
                                scanner2.nextLine();
                                String status = scanner2.nextLine();
                                System.out.println("You updated request status !");

                                //Delete request by id
                            }else if(wkInput == 4){
                                System.out.println("Delete request by id");
                                System.out.println(worker.printAllRequests().toString().replace("[","").replace(",","").replace("]",""));
                                int requestID = scanner2.nextInt();
                                worker.deleteRequestByID(requestID);
                                System.out.println("You deleted request");
                            }

                            //Open user section
                        }else if(workerInput == 2){
                            System.out.println(" 1 -> Check all user accounts \n 2 -> Delete user account \n 3 -> Check user information by id");
                            int wkInput = scanner2.nextInt();

                                //Check all user accounts
                            if(wkInput == 1){
                                System.out.println("Those are all user profiles");
                                System.out.println(worker.printAllUsersByRole("client").toString().replace("[","").replace(",","").replace("]",""));
                                System.out.println(worker.printAllUsersByRole("worker").toString().replace("[","").replace(",","").replace("]",""));

                            }else if(wkInput == 2){
                                System.out.println("Delete user account by user id");
                                System.out.println();
                                int userID = scanner2.nextInt();
                                worker.deleteUserProfile(userID);
                                System.out.println("You deleted user profile !");
                            }

                            //Open service section
                        }else if(workerInput == 3){

                            System.out.println(" 1 -> Add brand \n 2 -> Delete brand \n 3 -> Add model \n 4 -> Delete model \n" +
                                    " 5 -> Add service \n 6 -> Delete service");
                            int wkInput = scanner2.nextInt();
                            scanner2.nextLine();

                                //Add brand
                            if(wkInput == 1){
                                System.out.println("Brand name: ");
                                String brand = scanner2.nextLine();
                                worker.addBrand(brand);
                                System.out.println("Successfully added new brand !");

                                //Delete brand
                            }else if(wkInput == 2){
                                System.out.println(worker.getBrands().toString().replace("{","").replace("}","")
                                        .replace("="," - ").replace("[","").replace("]",""));
                                System.out.println("Enter brand to delete");
                                String brand = scanner2.nextLine();
                                int brandID = Integer.parseInt(worker.getBrandID(brand).toString().replace("[","").replace("]",""));
                                worker.deleteBrand(brandID);
                                System.out.println("Deleted brand !");

                                //Add model
                            }else if(wkInput == 3){
                                System.out.println("Write the name of the brand and model you want to add");
                                for(Map.Entry brandAndModels : worker.printAllBrandAndModels().entrySet()){
                                    System.out.println(brandAndModels.toString().replace("{","").replace("}","")
                                            .replace("="," - ").replace("[","").replace("]",""));
                                }
                                System.out.println("Brand name: ");
                                String brand = scanner2.nextLine();
                                int brandID = Integer.parseInt(worker.getBrandID(brand).toString().replace("[","").replace("]",""));
                                System.out.println("Model name");
                                String model = scanner2.nextLine();
                                worker.addModel(brandID,model);
                                System.out.println("Successfully added new model");

                                //Delete model
                            }else if(wkInput == 4){
                                System.out.println("Enter model id you want to delete");
                                System.out.println(worker.getModels().toString().replace("{","").replace("}","")
                                        .replace("="," - ").replace("[","").replace("]",""));
                                int modelID = scanner2.nextInt();
                                String model = worker.getModelName(modelID);
                                worker.deleteModel(modelID);
                                System.out.println("You deleted model: " + model);

                                //Add service
                            }else if(wkInput == 5){
                                System.out.println("Write the service you want to add");
                                String serviceName = scanner2.nextLine();
                                worker.addNewService(serviceName);
                                System.out.println("You added new service "+ serviceName);

                                //Delete service
                            }else if(wkInput == 6){
                                System.out.println("Write the service you want to delete");
                                System.out.println(worker.printServices().toString().replace("{","").replace("}","")
                                        .replace("="," - ").replace("[","").replace("]",""));
                                String serviceName = scanner2.nextLine();
                                int serviceID = Integer.parseInt(worker.getServiceID(serviceName).toString().replace("[","").replace("]",""));
                                worker.deleteService(serviceID);
                                System.out.println("You deleted service " + serviceName);
                            }
                        }
                    }
                    //User doesnt exist/wrong username or password
                }else{
                    System.out.println("Wrong username or password !");
                    System.out.println("0 -> Exit \n 1 -> Log in \n 2 -> Register as client \n 3 -> Register as worker");
                    input = scanner.nextInt();
                }

                //Register as client
            }else if(input == 2){
                System.out.println("Fill the information below");
                Scanner sc = new Scanner(System.in);
                System.out.println("First name: ");
                String nameFirst = sc.nextLine();
                System.out.println("Second name: ");
                String nameSecond = sc.nextLine();
                System.out.println("Username: ");
                String username = sc.nextLine();
                System.out.println("Password: ");
                String password = sc.nextLine();
                Users user = new Clients();
                user.setUsername(username);
                user.setPassword(password);
                user.setFirstName(nameFirst);
                user.setSecondName(nameSecond);
                Clients client = new Clients();
                client.createNewProfile(user);
                System.out.println("You created your account as client !");

                //Register as worker
            }else if(input == 3){
                System.out.println("Fill the information below");
                Scanner sc = new Scanner(System.in);
                System.out.println("First name: ");
                String nameFirst = sc.nextLine();
                System.out.println("Second name: ");
                String nameSecond = sc.nextLine();
                System.out.println("Username: ");
                String username = sc.nextLine();
                System.out.println("Password: ");
                String password = sc.nextLine();
                Users user = new Workers();
                user.setUsername(username);
                user.setPassword(password);
                user.setFirstName(nameFirst);
                user.setSecondName(nameSecond);
                Workers worker = new Workers();
                worker.createNewProfile(user);
                System.out.println("You created your account as worker !");
            }
        }
    }
}
