import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.*;

public abstract class Users {

    private int userID;
    private String firstName;
    private String secondName;
    private String username;
    private String password;
    private String role;

    public Users(String firstName,String secondName,String username,String password,String role){
        this.firstName = firstName;
        this.secondName = secondName;
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public Users(){}


    //Encapsulation
    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }



    //Abstract methods
    public abstract void CreateNewProfile(Users user);
    public abstract Users getUserInformation(int userID);


    public String toString(){
        return "User";
    }


    //Methods
    public Map<Integer,String> GetBrands(){
        Map<Integer,String> list = new HashMap<>();
        try
        {
            Connection con = DbConnection.getConnection();
            String sql = "SELECT * FROM brands";
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                list.put(rs.getInt("id_brand"),rs.getString("brand_name"));
            }

        }catch(Exception e){
            e.printStackTrace();
        }
        return list;
    }

    public Map<Integer,String> GetModels(){

        Map<Integer,String> list = new HashMap<>();

        try
        {
            Connection con = DbConnection.getConnection();
            String sql = "SELECT * FROM models";
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                list.put(rs.getInt("id_model"),rs.getString("model_name"));
            }

        }catch(Exception e){
            e.printStackTrace();
        }
        return list;
    }

    public Map<String, List<String>> PrintAllBrandAndModels(){

        Map<String,List<String>> list = new LinkedHashMap<>();

        try
        {
            Connection con = DbConnection.getConnection();
            String sql = "SELECT b.brand_name, m.model_name FROM brands b, models m WHERE b.id_brand = m.brand_id";
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                String key = rs.getString("brand_name");
                if (!list.containsKey(key)){
                    list.put(key, new LinkedList<String>());
                }
                list.get(key).add(rs.getString("model_name"));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return  list;
    }

    public List<String> printServices(){
        List<String> list = new ArrayList<>();
        try
        {
            Connection con = DbConnection.getConnection();
            String sql = "SELECT * FROM services";
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                list.add(rs.getString("service_name"));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return list;
    }

    public int GetUserID(String username){
        int result = 0;
        try
        {
            Connection con = DbConnection.getConnection();
            String sql = "SELECT * FROM users WHERE user_username=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1,username);
            ResultSet rs = ps.executeQuery();

            if(rs.next()){
                result = rs.getInt("id_user");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;

    }

    public int GetBrandID(String brand){
        int result = 0;
        try
        {
            Connection con = DbConnection.getConnection();
            String sql = "SELECT * FROM brands WHERE brand_name=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1,brand);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                result = rs.getInt("id_brand");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }

    public String GetBrandName(int brandID){
        String name = "";
        try
        {
            Connection con = DbConnection.getConnection();
            String sql = "SELECT * FROM brands WHERE id_brand=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1,brandID);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                name = rs.getString("brand_name");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return name;
    }

    public String GetModelName(int modelID){
        String name = "";
        try
        {
            Connection con = DbConnection.getConnection();
            String sql = "SELECT * FROM models WHERE id_model=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1,modelID);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                name = rs.getString("model_name");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return name;
    }

    public int GetModelID(String model){
        int result = 0;
        try
        {
            Connection con = DbConnection.getConnection();
            String sql = "SELECT * FROM models WHERE model_name=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1,model);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                result = rs.getInt("id_model");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }

    public boolean isExisting(String userName,String passWord){
        boolean isExisting = false;
        try
        {
            Connection con = DbConnection.getConnection();
            String sql = "SELECT * FROM users WHERE user_username=? AND user_password=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1,userName);
            ps.setString(2,passWord);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                isExisting = true;
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        return isExisting;
    }

    public String checkRole(String userName,String pass){
        String role = "";
        try
        {
            Connection con = DbConnection.getConnection();
            String sql = "SELECT user_role FROM users WHERE user_username=? AND user_password=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1,userName);
            ps.setString(2,pass);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                role = rs.getString("user_role");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return  role;
    }

    public String GetService(int serviceID){
        String name = "";
        try
        {
            Connection con = DbConnection.getConnection();
            String sql = "SELECT * FROM services WHERE id_service=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1,serviceID);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                name = rs.getString("service_name");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return name;
    }

    public int GetServiceID(String service){
        int serviceID = 0;
        try
        {
            Connection con = DbConnection.getConnection();
            String sql = "SELECT * FROM services WHERE service_name=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1,service);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                serviceID = rs.getInt("id_service");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return  serviceID;
    }

    public void deleteRequestByID(int requestID){
        try
        {
            Connection con = DbConnection.getConnection();
            String sql = "DELETE FROM requests WHERE id_request=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1,requestID);
            ps.executeUpdate();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}


