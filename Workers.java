import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Workers extends Users{

    public Workers(String firstName,String secondName,String username,String password,String role){
        super(firstName,secondName,username,password,role);
    }

    public Workers(){}

    @Override
    public String toString(){

        return "\nWorker account details -> " +" First name: "+ this.getFirstName() +" Second name: "+ this.getSecondName() +" Username: "
                + this.getUsername() +" Password: ****** Role: "+ this.getRole();
    }


    public void createNewProfile(Users worker){
        str_clause = "(user_fname,user_sname,user_username,user_password,user_role) VALUES (?,?,?,?,?)";
        String sql = SQL_INSERT + TABLE_USERS + str_clause;
        worker.setRole("worker");
        List<Object> list = new ArrayList<>();
        Collections.addAll(list,worker.getFirstName(),worker.getSecondName(),worker.getUsername(),worker.getPassword(),worker.getRole());
       dbUpdate(sql,list);
    }

    public Workers getUserInformation(int userID){
        str_clause = " WHERE id_user=?";
        String sql = SQL_SELECT+TABLE_USERS+str_clause;
        List<Object> listColumns = new ArrayList<>();
        Collections.addAll(listColumns,"user_fname","user_sname","user_username","user_password","user_role");
        List<Object> listObj = dbSelect(sql,listColumns,userID);
        int current = 0;
        Workers worker = new Workers();
        for(int i = 0; i < 1;i++){
            worker.setFirstName(String.valueOf(listObj.get(current)));
            current++;
            worker.setSecondName(String.valueOf(listObj.get(current)));
            current++;
            worker.setUsername(String.valueOf(listObj.get(current)));
            current++;
            worker.setPassword(String.valueOf(listObj.get(current)));
            current++;
            worker.setRole(String.valueOf(listObj.get(current)));
        }
        return worker;
    }

    public String getUserNames(int userID){
        str_clause = " WHERE id_user=?";
        String sql = SQL_SELECT+TABLE_USERS+str_clause;
        List<Object> listColumns = new ArrayList<>();
        Collections.addAll(listColumns,"user_fname","user_sname");
        List<Object> listObj = dbSelect(sql,listColumns,userID);
        String result = listObj.get(0)+" "+ listObj.get(1);
        return result;
    }

    public void addBrand(String brand){
        str_clause = " (brand_name) VALUES (?)";
        String sql = SQL_INSERT + TABLE_BRANDS + str_clause;
        List<Object> list = new ArrayList<>();
        list.add(brand);
        dbUpdate(sql,list);
    }

    //String brand, String model -> method getBrandID
    public void addModel(int brandID, String model){
        str_clause = " (model_name,brand_id) VALUES(?,?)";
        String sql = SQL_INSERT + TABLE_MODELS + str_clause;
        List<String> listStr = new ArrayList<>();
        List<Integer> listInt = new ArrayList<>();
        listStr.add(model);
        listInt.add(brandID);
        dbUpdate(sql,listStr,listInt);
    }

    public void deleteBrand(int brandID){
        str_clause = " WHERE id_brand=?";
        String sql = SQL_DELETE + TABLE_BRANDS + str_clause;
        List<Object> list = new ArrayList<>();
        list.add(brandID);
        dbUpdate(sql,list);
    }

    public void deleteModel(int modelID){
        str_clause = " WHERE id_model=?";
        String sql = SQL_DELETE + TABLE_MODELS + str_clause;
        List<Object> list = new ArrayList<>();
        list.add(modelID);
        dbUpdate(sql,list);
    }

    public void deleteUserProfile(int userID){
        str_clause = " WHERE id_user=?";
        String sql = SQL_DELETE + TABLE_USERS + str_clause;
        List<Object> list = new ArrayList<>();
        list.add(userID);
        dbUpdate(sql,list);
    }

    public void addNewService(String serviceName){
        str_clause = " (service_name) VALUES (?)";
        String sql = SQL_INSERT + TABLE_SERVICE + str_clause;
        List<Object> list = new ArrayList<>();
        list.add(serviceName);
        dbUpdate(sql,list);
    }

    public void deleteService(int serviceID){
        str_clause = " WHERE id_service=?";
        String sql = SQL_DELETE + TABLE_SERVICE + str_clause;
        List<Object> list = new ArrayList<>();
        list.add(serviceID);
        dbUpdate(sql,list);
    }

    public List<Object> printAllUsersByRole(String role){

        str_clause = " WHERE user_role=?";
        String sql = SQL_SELECT+TABLE_USERS+str_clause;
        List<Object> listColumns = new ArrayList<>();
        Collections.addAll(listColumns,"id_user","user_fname","user_sname","user_username","user_password","user_role");
        List<Object> listResult = new ArrayList<>();
        List<Object> listObj = dbSelect(sql,listColumns,role);

        int listSize = listObj.size() / 6;
        int current = 0;
        if(role.equals("client")){
            for(int i = 0; i < listSize; i++){
                Clients client = new Clients();
                client.setUserID(Integer.parseInt(listObj.get(current).toString()));
                current++;
                client.setFirstName(String.valueOf(listObj.get(current)));
                current++;
                client.setSecondName(String.valueOf(listObj.get(current)));
                current++;
                client.setUsername(String.valueOf(listObj.get(current)));
                current++;
                client.setPassword(String.valueOf(listObj.get(current)));
                current++;
                client.setRole(String.valueOf(listObj.get(current)));
                current++;
                listResult.add(client);
            }
        }else if(role.equals("worker")){
            for(int i = 0; i < listSize; i++){
                Workers worker = new Workers();
                worker.setUserID(Integer.parseInt(listObj.get(current).toString()));
                current++;
                worker.setFirstName(String.valueOf(listObj.get(current)));
                current++;
                worker.setSecondName(String.valueOf(listObj.get(current)));
                current++;
                worker.setUsername(String.valueOf(listObj.get(current)));
                current++;
                worker.setPassword(String.valueOf(listObj.get(current)));
                current++;
                worker.setRole(String.valueOf(listObj.get(current)));
                current++;
                listResult.add(worker);
            }
        }else{
            System.out.println("Wrong role !");
        }
        return listResult;
    }

    public List<Requests> printSortedRequests(String sort){
        str_clause = " WHERE status=?";
        String sql = SQL_SELECT+TABLE_REQUESTS+str_clause;
        List<Object> listStr = new ArrayList<>();
        Collections.addAll(listStr,"id_request","client_id","car_vin","car_brand_id","car_model_id","service","date_leave","date_pickup","status");
        List<Requests> listReq = new ArrayList<>();
        List<Object> list = dbSelect(sql,listStr,sort);
        int listSize = list.size() / 9;
        int current = 0;
        for(int i = 0; i <listSize; i++){
            Requests req = new Requests();
            req.setRequestID(Integer.parseInt(list.get(current).toString()));
            current++;
            req.setClientID(Integer.parseInt(list.get(current).toString()));
            current++;
            req.setCarVin(Integer.parseInt(list.get(current).toString()));
            current++;
            req.setBrandID(Integer.parseInt(list.get(current).toString()));
            current++;
            req.setModelID(Integer.parseInt(list.get(current).toString()));
            current++;
            req.setService(Integer.parseInt(list.get(current).toString()));
            current++;
            req.setDateLeave(String.valueOf(list.get(current)));
            current++;
            req.setDatePickUp(String.valueOf(list.get(current)));
            current++;
            req.setStatus(String.valueOf(list.get(current)));
            current++;
            listReq.add(req);
        }
        return listReq;
    }

    public List<Requests> printAllRequests(){
        String sql = SQL_SELECT+TABLE_REQUESTS;
        List<Object> listColumns = new ArrayList<>();
        Collections.addAll(listColumns,"id_request","client_id","car_vin","car_brand_id","car_model_id","service","date_leave","date_pickup","status");
        List<Requests> listReq = new ArrayList<>();
        List<Object> listObj = dbSelect(sql,listColumns);
        int listSize = listObj.size() / 9;
        int current = 0;
        for(int i = 0; i <listSize; i++){
            Requests req = new Requests();
            req.setRequestID(Integer.parseInt(listObj.get(current).toString()));
            current++;
            req.setClientID(Integer.parseInt(listObj.get(current).toString()));
            current++;
            req.setCarVin(Integer.parseInt(listObj.get(current).toString()));
            current++;
            req.setBrandID(Integer.parseInt(listObj.get(current).toString()));
            current++;
            req.setModelID(Integer.parseInt(listObj.get(current).toString()));
            current++;
            req.setService(Integer.parseInt(listObj.get(current).toString()));
            current++;
            req.setDateLeave(String.valueOf(listObj.get(current)));
            current++;
            req.setDatePickUp(String.valueOf(listObj.get(current)));
            current++;
            req.setStatus(String.valueOf(listObj.get(current)));
            current++;
            listReq.add(req);
        }
        return listReq;
    }

    public void changeRequestStatus(int requestID,String status){
        String sql = "UPDATE requests SET status=? WHERE id_request=?";
        List<String> listStr = new ArrayList<>();
        List<Integer> listInt = new ArrayList<>();
        listStr.add(status);
        listInt.add(requestID);
        dbUpdate(sql,listStr,listInt);
    }


}
