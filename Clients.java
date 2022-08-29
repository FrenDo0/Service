import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

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
        strClause = " (user_fname,user_sname,user_username,user_password,user_role) VALUES (?,?,?,?,?)";
        try(Connection con = DbConnection.getConnection();PreparedStatement ps = con.prepareStatement(sqlInsert+tableUsers+strClause);)
        {
            client.setRole("client");
            ps.setString(1, client.getFirstName());
            ps.setString(2, client.getSecondName());
            ps.setString(3, client.getUsername());
            ps.setString(4, client.getPassword());
            ps.setString(5, client.getRole());
            ps.executeUpdate();

        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void createRequest(int clientID,int vinNum,int brandID, int modelID,int service, String dateLeave, String datePickUp){

        strClause = " (client_id,car_vin,car_brand_id,car_model_id,service,date_leave,date_pickup,status) VALUES (?,?,?,?,?,?,?,?)";
        try(Connection con = DbConnection.getConnection(); PreparedStatement ps = con.prepareStatement(sqlInsert+tableRequests+strClause))
        {
            String status = "pending";
            ps.setInt(1,clientID);
            ps.setInt(2,vinNum);
            ps.setInt(3,brandID);
            ps.setInt(4,modelID);
            ps.setInt(5,service);
            ps.setString(6,dateLeave);
            ps.setString(7,datePickUp);
            ps.setString(8,status);
            ps.executeUpdate();

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public Clients getUserInformation(int userID){
        Clients cl = new Clients();
        strClause = " WHERE id_user=?";
        try(Connection con = DbConnection.getConnection();PreparedStatement ps = con.prepareStatement(sqlSelect+tableUsers+strClause);ResultSet rs = ps.executeQuery())
        {
            ps.setInt(1,userID);
            if(rs.next()){
                cl.setFirstName(rs.getString("user_fname"));
                cl.setSecondName(rs.getString("user_sname"));
                cl.setUsername(rs.getString("user_username"));
                cl.setPassword(rs.getString("user_password"));
                cl.setRole(rs.getString("user_role"));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return cl;
    }

    public List<Requests> printRequestByClientID(int clientID){
        List<Requests> list = new ArrayList<Requests>();
        strClause = " WHERE client_id=?";
        try(Connection con = DbConnection.getConnection();PreparedStatement ps = con.prepareStatement(sqlSelect+tableRequests+strClause); ResultSet rs = ps.executeQuery())
        {
            ps.setInt(1,clientID);
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
            e.printStackTrace();
        }
        return  list;
    }

    public void updateRequest(int requestID,int vinNum,int brandID, int modelID,int service, String dateLeave, String datePickUp){
        String sql = "UPDATE requests SET car_vin=?,car_brand_id=?,car_model_id=?,service=?,date_leave=?,date_pickup=?" +
                "WHERE id_request=?";
        try(Connection con = DbConnection.getConnection(); PreparedStatement ps = con.prepareStatement(sql))
        {
            ps.setInt(7,requestID);
            ps.setInt(1,vinNum);
            ps.setInt(2,brandID);
            ps.setInt(3,modelID);
            ps.setInt(4,service);
            ps.setString(5,dateLeave);
            ps.setString(6,datePickUp);
            ps.executeUpdate();

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
