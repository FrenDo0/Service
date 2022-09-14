import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Clients extends Users{

    public Clients(String firstName,String secondName,String username,String password,String role){
        super(firstName,secondName,username,password,role);
    }

    public Clients(){}

    @Override
    public String toString(){
        return "\nClient profile details ->  First name: " + this.getFirstName() +" Second name: "
                + this.getSecondName() +" Username: "+ this.getUsername() +" Password: ****** Role: "+ this.getRole();
    }

    public void createNewProfile(Users client){
        str_clause = " (user_fname,user_sname,user_username,user_password,user_role) VALUES (?,?,?,?,?)";
        String sql = SQL_INSERT + TABLE_USERS + str_clause;
        List<Object> list = new ArrayList<>();
        client.setRole("client");
        Collections.addAll(list,client.getFirstName(),client.getSecondName(),client.getUsername(),client.getPassword(),client.getRole());
        dbUpdate(sql,list);
    }

    public void createRequest(int clientID,int vinNum,int brandID, int modelID,int service, String dateLeave, String datePickUp){
        str_clause = " (date_leave,date_pickup,status,client_id,car_vin,car_brand_id,car_model_id,service) VALUES (?,?,?,?,?,?,?,?)";
        String sql = SQL_INSERT + TABLE_REQUESTS + str_clause;
        String status = "pending";
        List<Integer> listInt = new ArrayList<>();
        List<String> listStr = new ArrayList<>();
        Collections.addAll(listInt,clientID,vinNum,brandID,modelID,service);
        Collections.addAll(listStr,dateLeave,datePickUp,status);
        dbUpdate(sql,listStr,listInt);
    }

    public Clients getUserInformation(int userID){
        str_clause = " WHERE id_user=?";
        String sql = SQL_SELECT+TABLE_USERS+str_clause;
        List<Object> listColumns = new ArrayList<>();
        Collections.addAll(listColumns,"user_fname","user_sname","user_username","user_password","user_role");
        List<Object> listObj = dbSelect(sql,listColumns,userID);
        int current = 0;
        Clients client = new Clients();
        for(int i = 0; i < 1;i++){
            client.setFirstName(String.valueOf(listObj.get(current)));
            current++;
            client.setSecondName(String.valueOf(listObj.get(current)));
            current++;
            client.setUsername(String.valueOf(listObj.get(current)));
            current++;
            client.setPassword(String.valueOf(listObj.get(current)));
            current++;
            client.setRole(String.valueOf(listObj.get(current)));
        }
        return client;
    }

    public List<Requests> printRequestByClient(int clientID){
        str_clause = " WHERE client_id=?";
        String sql = SQL_SELECT+TABLE_REQUESTS+str_clause;
        List<Object> listStr = new ArrayList<>();
        Collections.addAll(listStr,"id_request","client_id","car_vin","car_brand_id","car_model_id","service","date_leave","date_pickup","status");
        List<Requests> listReq = new ArrayList<>();
        List<Object> list = dbSelect(sql,listStr,clientID);
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
}
