import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class Workers extends Users{

    public Workers(String firstName,String secondName,String username,String password,String role){
        super(firstName,secondName,username,password,role);
    }

    public Workers(){}

    @Override
    public String toString(){

        return "Worker details " + this.getFirstName() +" "+ this.getSecondName() +" "
                + this.getUsername() +" "+ this.getPassword() +" "+ this.getRole();
    }

    public void createNewProfile(Users worker){
        strClause = "(user_fname,user_sname,user_username,user_password,user_role) VALUES (?,?,?,?,?)";
        try
        {
            worker.setRole("worker");
            Connection con = DbConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sqlInsert+tableUsers+strClause);
            ps.setString(1, worker.getFirstName());
            ps.setString(2, worker.getSecondName());
            ps.setString(3, worker.getUsername());
            ps.setString(4, worker.getPassword());
            ps.setString(5, worker.getRole());
            ps.executeUpdate();

        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public Workers getUserInformation(int userID){
        Workers cl = new Workers();
        strClause = " WHERE id_user=?";
        try
        {
            Connection con = DbConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sqlSelect+tableUsers+strClause);
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

    public void addBrand(String brand){
        strClause = " (brand_name) VALUES (?)";
        try
        {
            Connection con = DbConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sqlInsert+tableBrands+strClause);
            ps.setString(1,brand);
            ps.executeUpdate();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void addModel(String brand, String model){
        int brandID = getBrandID(brand);
        strClause = " (brand_id,model_name) VALUES(?,?)";
        try
        {
            Connection con = DbConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sqlInsert+tableModels+strClause);
            ps.setInt(1,brandID);
            ps.setString(2,model);
            ps.executeUpdate();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void deleteBrand(int brandID){
        strClause = " WHERE id_brand=?";
        try
        {
            Connection con = DbConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sqlDelete+tableBrands+strClause);
            ps.setInt(1,brandID);
            ps.executeUpdate();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void deleteModel(int modelID){
        strClause = " WHERE id_model=?";
        try
        {
            Connection con = DbConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sqlDelete+tableModels+strClause);
            ps.setInt(1,modelID);
            ps.executeUpdate();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void deleteUserProfile(int userID){
        strClause = " WHERE id_user=?";
        try
        {
            Connection con = DbConnection.getConnection();
            String sql = "DELETE FROM users WHERE id_user=?";
            PreparedStatement ps = con.prepareStatement(sqlDelete+tableUsers+strClause);
            ps.setInt(1,userID);
            ps.executeUpdate();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void addNewService(String serviceName){
        strClause = " (service_name) VALUES (?)";
        try
        {
            Connection con = DbConnection.getConnection();
            String sql = "INSERT INTO services (service_name) VALUES (?)";
            PreparedStatement ps = con.prepareStatement(sqlInsert+tableServices+strClause);
            ps.setString(1,serviceName);
            ps.executeUpdate();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void deleteService(int serviceID){
        strClause = " WHERE id_service=?";
        try
        {
            Connection con = DbConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sqlDelete+tableServices+strClause);
            ps.setInt(1,serviceID);
            ps.executeUpdate();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public List<Requests> printAllRequests(){
        List<Requests> list = new ArrayList<>();
        try
        {
            Connection con = DbConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sqlSelect+tableRequests);
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
            e.printStackTrace();
        }
        return  list;
    }


}
