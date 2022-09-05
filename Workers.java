import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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

        return "Worker details " + this.getFirstName() +" "+ this.getSecondName() +" "
                + this.getUsername() +" "+ this.getPassword() +" "+ this.getRole();
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
        Workers cl = new Workers();
        str_clause = " WHERE id_user=?";
        try
        {
            Connection con = (Connection) DbConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(SQL_SELECT+TABLE_USERS+str_clause);
            ps.setInt(1,userID);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                cl.setFirstName(rs.getString("user_fname"));
                cl.setSecondName(rs.getString("user_sname"));
                cl.setUsername(rs.getString("user_username"));
                cl.setPassword(rs.getString("user_password"));
                cl.setRole(rs.getString("user_role"));
            }
        }catch (SQLException e){
            writeExceptionToFile(e);
            System.out.println("Exception caught and stored in file !");
        }
        return cl;
    }

    public void addBrand(String brand){
        str_clause = " (brand_name) VALUES (?)";
        String sql = SQL_INSERT + TABLE_BRANDS + str_clause;
        List<Object> list = new ArrayList<>();
        list.add(brand);
        dbUpdate(sql,list);
    }

    //String brand, String model -> method getBrandID
    public void addModel(Integer brandID, String model){
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

    public List<Requests> printAllRequests(){
        List<Requests> list = new ArrayList<>();
        try
        {
            Connection con = (Connection) DbConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(SQL_SELECT+TABLE_REQUESTS);
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
        }catch (SQLException e){
            writeExceptionToFile(e);
            System.out.println("Exception caught and stored in file !");
        }
        return  list;
    }
}
