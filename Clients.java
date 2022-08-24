import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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

    public void CreateNewProfile(Users client){
        try
        {
            client.setRole("client");
            Connection con = DbConnection.getConnection();
            String sql = "INSERT INTO users" +
                    "(user_fname,user_sname,user_username,user_password,user_role)" + "VALUES (?,?,?,?,?)";
            PreparedStatement ps = con.prepareStatement(sql);
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

    public void CreateRequest(int clientID,int vinNum,int brandID, int modelID, String dateLeave, String datePickUp){

        try
        {
            String process = "pending";
            Connection con = DbConnection.getConnection();
            String sql = "INSERT INTO requests" +"(client_id,car_vin,car_brand_id,car_model_id,date_leave,date_pickup,process)"+
                    "VALUES (?,?,?,?,?,?,?)";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1,clientID);
            ps.setInt(2,vinNum);
            ps.setInt(3,brandID);
            ps.setInt(4,modelID);
            ps.setString(5,dateLeave);
            ps.setString(6,datePickUp);
            ps.setString(7,process);
            ps.executeUpdate();

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public Clients getUserInformation(int userID){
        Clients cl = new Clients();
        try
        {
            Connection con = DbConnection.getConnection();
            String sql = "SELECT * FROM users WHERE id_user = ?";
            PreparedStatement ps = con.prepareStatement(sql);
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
            e.printStackTrace();
        }
        return cl;
    }
}
