import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public abstract class Users extends DbConnection{

    private int userID;
    private String firstName;
    private String secondName;
    private String username;
    private String password;
    private String role;

    public String str_clause;
    public static final String TABLE_BRANDS = " brands";
    public static final String TABLE_MODELS = " models";
    public static final String TABLE_USERS = " users";
    public static final String TABLE_SERVICE = " services";
    public static final String TABLE_REQUESTS = " requests";
    public static final String SQL_SELECT = "SELECT * FROM";
    public static final String SQL_INSERT = "INSERT INTO";
    public static final String SQL_DELETE = "DELETE FROM";

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
    public void dbUpdate(String sql,List<String> strings, List<Integer> ints){
        int position = 1;
        try(Connection con = DbConnection.getConnection();PreparedStatement ps = con.prepareStatement(sql)){
            if(!strings.isEmpty()){
                for(int i = 0; i < strings.size(); i++){
                    ps.setString(position,strings.get(i));
                    position++;
                }
            }
            if(!ints.isEmpty()){
                for(int i = 0; i < ints.size(); i++){
                    ps.setInt(position,ints.get(i));
                    position++;
                }
            }
            ps.executeUpdate();
        }catch (SQLException e){
            System.out.println(e.getMessage());
            System.out.println("dbUpdate method SQL Exception");
        }
    }

    public void dbUpdateStr(String sql, List<String> strings){
        int position = 1;
        try(Connection con = DbConnection.getConnection();PreparedStatement ps = con.prepareStatement(sql)){
            if(!strings.isEmpty()){
                for(int i = 0; i < strings.size(); i++){
                    ps.setString(position,strings.get(i));
                    position++;
                }
            }
            ps.executeUpdate();
        }catch (SQLException e){
            System.out.println(e.getMessage());
            System.out.println("dbUpdaetStr method SQL Exception");
        }
    }

    public void dbUpdateInt(String sql, List<Integer> ints){
        int position = 1;
        try(Connection con = DbConnection.getConnection();PreparedStatement ps = con.prepareStatement(sql)){
            if(!ints.isEmpty()){
                for(int i = 0; i < ints.size(); i++){
                    ps.setInt(position,ints.get(i));
                    position++;
                }
            }
            ps.executeUpdate();
        }catch (SQLException e){
            System.out.println(e.getMessage());
            System.out.println("dbUpdateInt method SQL Exception");
        }
    }

    public Map<Integer,String> getBrands(){
        Map<Integer,String> list = new HashMap<>();
        try(Connection con = DbConnection.getConnection(); PreparedStatement ps = con.prepareStatement(SQL_SELECT+TABLE_BRANDS))
        {
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                list.put(rs.getInt("id_brand"),rs.getString("brand_name"));
            }

        }catch(Exception e){
            System.out.println(e.getMessage());
            System.out.println("getBrands method SQL Exception");
        }
        return list;
    }

    public Map<Integer,String> getModels(){

        Map<Integer,String> list = new HashMap<>();
        try(Connection con = DbConnection.getConnection();PreparedStatement ps = con.prepareStatement(SQL_SELECT+TABLE_MODELS))
        {
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                list.put(rs.getInt("id_model"),rs.getString("model_name"));
            }

        }catch(Exception e){
            System.out.println(e.getMessage());
            System.out.println("getModels method SQL Exception");
        }
        return list;
    }

    public Map<String, List<String>> printAllBrandAndModels(){

        Map<String,List<String>> list = new LinkedHashMap<>();
        String sql = "SELECT b.brand_name, m.model_name FROM brands b, models m WHERE b.id_brand = m.brand_id";
        try(Connection con = DbConnection.getConnection();PreparedStatement ps = con.prepareStatement(sql))
        {
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                String key = rs.getString("brand_name");
                if (!list.containsKey(key)){
                    list.put(key, new LinkedList<String>());
                }
                list.get(key).add(rs.getString("model_name"));
            }
        }catch(Exception e){
            System.out.println(e.getMessage());
            System.out.println("printAllBrandAndModels method SQL Exception");
        }
        return  list;
    }

    public List<String> printServices(){
        List<String> list = new ArrayList<>();

        try(Connection con = DbConnection.getConnection();PreparedStatement ps = con.prepareStatement(SQL_SELECT+TABLE_SERVICE))
        {
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                list.add(rs.getString("service_name"));
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
            System.out.println("printServices method SQL Exception");
        }
        return list;
    }

    public int getUserIDNumber(String username){
        int result = 0;
        str_clause = " WHERE user_username=?";
        try(Connection con = DbConnection.getConnection();PreparedStatement ps = con.prepareStatement(SQL_SELECT+TABLE_USERS+str_clause))
        {
            ps.setString(1,username);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                result = rs.getInt("id_user");
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
            System.out.println("getUserIDNumber method SQL Exception");
        }
        return result;

    }

    public int getBrandID(String brand){
        int result = 0;
        str_clause = " WHERE brand_name=?";
        try(Connection con = DbConnection.getConnection();PreparedStatement ps = con.prepareStatement(SQL_SELECT+TABLE_BRANDS+str_clause))
        {
            ps.setString(1,brand);
            ResultSet rs = ps.executeQuery();
            if(rs.next())
                result = rs.getInt("id_brand");

        }catch (Exception e){
            System.out.println(e.getMessage());
            System.out.println("getBrandID method SQL Exception");
        }
        return result;
    }

    public String getBrandName(int brandID){
        String name = "";
        str_clause = " WHERE id_brand=?";
        try(Connection con = DbConnection.getConnection(); PreparedStatement ps = con.prepareStatement(SQL_SELECT+TABLE_BRANDS+str_clause))
        {
            ps.setInt(1,brandID);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                name = rs.getString("brand_name");
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
            System.out.println("getBrandName method SQL Exception");
        }
        return name;
    }

    public String getModelName(int modelID){
        String name = "";
        str_clause = " WHERE id_model=?";
        try(Connection con = DbConnection.getConnection();PreparedStatement ps = con.prepareStatement(SQL_SELECT+TABLE_MODELS+str_clause))
        {
            ps.setInt(1,modelID);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                name = rs.getString("model_name");
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
            System.out.println("getModelName method SQL Exception");
        }
        return name;
    }

    public int getModelID(String model){
        int result = 0;
        str_clause = " WHERE model_name=?";
        try(Connection con = DbConnection.getConnection(); PreparedStatement ps = con.prepareStatement(SQL_SELECT+TABLE_MODELS+str_clause))
        {
            ps.setString(1,model);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                result = rs.getInt("id_model");
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
            System.out.println("getModelID method SQL Exception");
        }
        return result;
    }

    public boolean isExisting(String userName,String passWord){
        boolean isExisting = false;
        str_clause = " WHERE user_username=? AND user_password=?";
        try(Connection con = DbConnection.getConnection();PreparedStatement ps = con.prepareStatement(SQL_SELECT+TABLE_USERS+str_clause))
        {
            ps.setString(1,userName);
            ps.setString(2,passWord);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                isExisting = true;
            }

        }catch (Exception e){
           System.out.println(e.getMessage());
            System.out.println("isExisting method SQL Exception");
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
            System.out.println(e.getMessage());
            System.out.println("checkRole method SQL Exception");
        }
        return  role;
    }

    public String getService(int serviceID){
        String name = "";
        str_clause = " WHERE id_service=?";
        try(Connection con = DbConnection.getConnection();PreparedStatement ps = con.prepareStatement(SQL_SELECT+TABLE_SERVICE+str_clause))
        {
            ps.setInt(1,serviceID);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                name = rs.getString("service_name");
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
            System.out.println("getService method SQL Exception");
        }
        return name;
    }

    public int getServiceID(String service){
        int serviceID = 0;
        str_clause = " WHERE service_name=?";
        try(Connection con = DbConnection.getConnection();PreparedStatement ps = con.prepareStatement(SQL_SELECT+TABLE_SERVICE+str_clause))
        {
            ps.setString(1,service);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                serviceID = rs.getInt("id_service");
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
            System.out.println("getServiceID method SQL Exception");
        }
        return  serviceID;
    }

    public void deleteRequestByID(int requestID){
        str_clause = " WHERE id_request=?";
        String sql = SQL_DELETE+ TABLE_REQUESTS + str_clause;
        List<Integer> list = new ArrayList<>();
        list.add(requestID);
        dbUpdateInt(sql,list);
    }

}


