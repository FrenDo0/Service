import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
        return "Client details " + this.getFirstName() +" "+ this.getSecondName() +" "+ this.getUsername() +" "+ this.getPassword() +" "+ this.getRole();
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
        str_clause = " (client_id,car_vin,car_brand_id,car_model_id,service,date_leave,date_pickup,status) VALUES (?,?,?,?,?,?,?,?)";
        String sql = SQL_INSERT + TABLE_REQUESTS + str_clause;
        String status = "pending";
        List<Integer> listInt = new ArrayList<>();
        List<String> listStr = new ArrayList<>();
        Collections.addAll(listInt,clientID,vinNum,brandID,modelID,service);
        Collections.addAll(listStr,dateLeave,datePickUp,status);
        dbUpdate(sql,listStr,listInt);
    }

    public Clients getUserInformation(int userID){
        Clients cl = new Clients();
        str_clause = " WHERE id_user=?";
        try(Connection con = (Connection) DbConnection.getConnection(); PreparedStatement ps = con.prepareStatement(SQL_SELECT+TABLE_USERS+str_clause))
        {
            ps.setInt(1,userID);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                cl.setFirstName(rs.getString("user_fname"));
                cl.setSecondName(rs.getString("user_sname"));
                cl.setUsername(rs.getString("user_username"));
                cl.setPassword(rs.getString("user_password"));
                cl.setRole(rs.getString("user_role"));
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
            System.out.println("getUserInformation method SQL Exception");
        }
        return cl;
    }

    public List<Requests> printRequestByClientID(int clientID){
        List<Requests> list = new ArrayList<Requests>();
        str_clause = " WHERE client_id=?";
        try(Connection con = DbConnection.getConnection();PreparedStatement ps = con.prepareStatement(SQL_SELECT+TABLE_REQUESTS+str_clause))
        {
            ps.setInt(1,clientID);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                Requests req = new Requests();
                req.setRequestID(rs.getInt("id_request"));
                req.setClientID(rs.getInt("client_id"));
                req.setCarVin(rs.getInt("car_vin"));
                req.setBrandID(rs.getInt("car_brand_id"));
                req.setModelID(rs.getInt("car_model_id"));
                req.setService(rs.getInt("service"));
                req.setDateLeave(rs.getString("date_leave"));
                req.setDatePickUp(rs.getString("date_pickup"));
                req.setStatus(rs.getString("status"));
                list.add(req);
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
            System.out.println("printRequestByClientID method SQL Exception");
        }
        return  list;
    }

}
