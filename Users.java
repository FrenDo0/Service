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

    public String tableBrands = " brands";
    public String tableModels = " models";
    public String tableUsers = " users";
    public String tableServices = " services";
    public String tableRequests = " requests";
    public String strClause;
    public String sqlSelect = "SELECT * FROM";
    public String sqlInsert = "INSERT INTO";
    public String sqlDelete = "DELETE FROM";

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
    public abstract void createNewProfile(Users user);
    public abstract Users getUserInformation(int userID);


    public String toString(){
        return "User";
    }


    //Methods
    public Map<Integer,String> getBrands(){
        Map<Integer,String> list = new HashMap<>();
        try(Connection con = DbConnection.getConnection(); PreparedStatement ps = con.prepareStatement(sqlSelect+tableBrands); ResultSet rs = ps.executeQuery())
        {
            while(rs.next()){
                list.put(rs.getInt("id_brand"),rs.getString("brand_name"));
            }

        }catch(Exception e){
            e.printStackTrace();
        }
        return list;
    }

    public Map<Integer,String> getModels(){

        Map<Integer,String> list = new HashMap<>();
        try(Connection con = DbConnection.getConnection();PreparedStatement ps = con.prepareStatement(sqlSelect+tableModels);ResultSet rs = ps.executeQuery())
        {
            while(rs.next()){
                list.put(rs.getInt("id_model"),rs.getString("model_name"));
            }

        }catch(Exception e){
            e.printStackTrace();
        }
        return list;
    }

    public Map<String, List<String>> printAllBrandAndModels(){

        Map<String,List<String>> list = new LinkedHashMap<>();
        String sql = "SELECT b.brand_name, m.model_name FROM brands b, models m WHERE b.id_brand = m.brand_id";
        try(Connection con = DbConnection.getConnection();PreparedStatement ps = con.prepareStatement(sql);ResultSet rs = ps.executeQuery())
        {
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

        try(Connection con = DbConnection.getConnection();PreparedStatement ps = con.prepareStatement(sqlSelect+tableServices);ResultSet rs = ps.executeQuery())
        {
            while(rs.next()){
                list.add(rs.getString("service_name"));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return list;
    }

    public int getUserIDNumber(String username){
        int result = 0;
        strClause = " WHERE user_username=?";
        try(Connection con = DbConnection.getConnection();PreparedStatement ps = con.prepareStatement(sqlSelect+tableUsers+strClause); ResultSet rs = ps.executeQuery())
        {
            ps.setString(1,username);
            if(rs.next()){
                result = rs.getInt("id_user");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;

    }

    public int getBrandID(String brand){
        int result = 0;
        strClause = " WHERE brand_name";
        try(Connection con = DbConnection.getConnection();PreparedStatement ps = con.prepareStatement(sqlSelect+tableBrands+strClause);ResultSet rs = ps.executeQuery())
        {
            ps.setString(1,brand);
            if(rs.next()){
                result = rs.getInt("id_brand");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }

    public String getBrandName(int brandID){
        String name = "";
        strClause = " WHERE id_brand=?";
        try(Connection con = DbConnection.getConnection(); PreparedStatement ps = con.prepareStatement(sqlSelect+tableBrands+strClause); ResultSet rs = ps.executeQuery())
        {
            ps.setInt(1,brandID);
            if(rs.next()){
                name = rs.getString("brand_name");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return name;
    }

    public String getModelName(int modelID){
        String name = "";
        strClause = " WHERE id_model=?";
        try(Connection con = DbConnection.getConnection();PreparedStatement ps = con.prepareStatement(sqlSelect+tableModels+strClause);ResultSet rs = ps.executeQuery())
        {
            ps.setInt(1,modelID);
            if(rs.next()){
                name = rs.getString("model_name");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return name;
    }

    public int getModelID(String model){
        int result = 0;
        strClause = " WHERE model_name=?";
        try(Connection con = DbConnection.getConnection(); PreparedStatement ps = con.prepareStatement(sqlSelect+tableModels+strClause);ResultSet rs = ps.executeQuery())
        {
            ps.setString(1,model);
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
        strClause = " WHERE user_username=? AND user_password=?";
        try(Connection con = DbConnection.getConnection();PreparedStatement ps = con.prepareStatement(sqlSelect+tableUsers+strClause);ResultSet rs = ps.executeQuery())
        {
            ps.setString(1,userName);
            ps.setString(2,passWord);
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
        String sql = "SELECT user_role FROM users WHERE user_username=? AND user_password=?";
        try(Connection con = DbConnection.getConnection(); PreparedStatement ps = con.prepareStatement(sql); ResultSet rs = ps.executeQuery())
        {
            ps.setString(1,userName);
            ps.setString(2,pass);
            if(rs.next()){
                role = rs.getString("user_role");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return  role;
    }

    public String getService(int serviceID){
        String name = "";
        strClause = " WHERE id_service=?";
        try(Connection con = DbConnection.getConnection();PreparedStatement ps = con.prepareStatement(sqlSelect+tableServices+strClause);ResultSet rs = ps.executeQuery())
        {
            ps.setInt(1,serviceID);
            if(rs.next()){
                name = rs.getString("service_name");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return name;
    }

    public int getServiceID(String service){
        int serviceID = 0;
        strClause = " WHERE service_name=?";
        try(Connection con = DbConnection.getConnection();PreparedStatement ps = con.prepareStatement(sqlSelect+tableServices+strClause); ResultSet rs = ps.executeQuery())
        {
            ps.setString(1,service);
            if(rs.next()){
                serviceID = rs.getInt("id_service");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return  serviceID;
    }

    public void deleteRequestByID(int requestID){
        strClause = " WHERE id_request=?";
        try(Connection con = DbConnection.getConnection(); PreparedStatement ps = con.prepareStatement(sqlDelete+tableRequests+strClause);)
        {
            ps.setInt(1,requestID);
            ps.executeUpdate();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}


