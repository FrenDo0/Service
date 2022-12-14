import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public abstract class Users extends  DbConnection{

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
    ExceptionWriter wr = new ExceptionWriter();
    public void dbUpdate(String sql,List<String> strings, List<Integer> ints){
        int position = 1;
        try(Connection con =DbConnection.getConnection(); PreparedStatement ps = con.prepareStatement(sql);)
        {
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
            wr.writeExceptionToFile(e);
            System.out.println("Exception caught and stored in file !");
        }
    }

    public void dbUpdate(String sql, List<Object> list){
        int position = 1;
        try(Connection con = DbConnection.getConnection(); PreparedStatement ps = con.prepareStatement(sql);)
        {
            if(!list.isEmpty()){
                for(int i = 0; i < list.size(); i++){
                    if(list.get(i) instanceof String){
                        ps.setString(position, String.valueOf(list.get(i)));
                        position++;
                    }else if(list.get(i) instanceof Integer){
                        ps.setInt(position,(Integer)list.get(i));
                        position++;
                    }
                }
            }
            ps.executeUpdate();
        }catch (SQLException e){
            wr.writeExceptionToFile(e);
            System.out.println("Exception caught and stored in file !");
        }
    }

    public List<Object> dbSelect(String sql, List<Object> columns){
        List<Object> result = new ArrayList<>();
        try(Connection con = DbConnection.getConnection(); PreparedStatement ps = con.prepareStatement(sql);)
        {
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                for(int i = 0; i < columns.size(); i++){
                    result.add(rs.getString(String.valueOf(columns.get(i))));
                }
            }
        }catch (SQLException e){
            wr.writeExceptionToFile(e);
            System.out.println("Exception caught and stored in file !");
        }
        return result;
    }

    public List<Object> dbSelect(String sql,List<Object> columns, Object condition){
        List<Object> result = new ArrayList<>();
        try(Connection con = DbConnection.getConnection(); PreparedStatement ps = con.prepareStatement(sql);)
        {
            if(condition instanceof String){
                ps.setString(1, (String) condition);
                ResultSet rs = ps.executeQuery();
                while(rs.next()){
                    for(int i = 0; i < columns.size(); i++){
                        result.add(rs.getString(String.valueOf(columns.get(i))));
                    }
                }
            }else if(condition instanceof Integer){
                ps.setInt(1,(Integer) condition);
                ResultSet rs = ps.executeQuery();
                while(rs.next()){
                    for(int i = 0; i < columns.size(); i++){
                        result.add(rs.getString(String.valueOf(columns.get(i))));
                    }
                }
            }
        }catch (SQLException e){
          wr.writeExceptionToFile(e);
          System.out.println("Exception caught and stored in file !");
        }
        return result;
    }

    public Map<Integer,String> getBrands(){
        Map<Integer,String> list = new HashMap<>();
        try(Connection con = DbConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(SQL_SELECT+TABLE_BRANDS);)
        {
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                list.put(rs.getInt("id_brand"),rs.getString("brand_name"));
            }

        }catch(SQLException e){
            wr.writeExceptionToFile(e);
            System.out.println("Exception caught and stored in file !");
        }
        return list;
    }

    public Map<Integer,String> getModels(){

        Map<Integer,String> list = new HashMap<>();
        try(Connection con = DbConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(SQL_SELECT+TABLE_MODELS);)
        {
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                list.put(rs.getInt("id_model"),rs.getString("model_name"));
            }

        }catch(SQLException e){
            wr.writeExceptionToFile(e);
            System.out.println("Exception caught and stored in file !");
        }
        return list;
    }

    public Map<String, List<String>> printAllBrandAndModels(){

        Map<String,List<String>> list = new LinkedHashMap<>();
        String sql = "SELECT b.brand_name, m.model_name FROM brands b, models m WHERE b.id_brand = m.brand_id";
        try(Connection con = DbConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);)
        {
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                String key = rs.getString("brand_name");
                if (!list.containsKey(key)){
                    list.put(key, new LinkedList<String>());
                }
                list.get(key).add(rs.getString("model_name"));
            }
        }catch(SQLException e){
            wr.writeExceptionToFile(e);
            System.out.println("Exception caught and stored in file !");
        }
        return  list;
    }

    public boolean isExisting(String userName,String passWord){
        boolean isExisting = false;
        str_clause = " WHERE user_username=? AND user_password=?";
        try(Connection con = DbConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(SQL_SELECT+TABLE_USERS+str_clause);)
        {
            ps.setString(1,userName);
            ps.setString(2,passWord);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                isExisting = true;
            }

        }catch (SQLException e){
            wr.writeExceptionToFile(e);
            System.out.println("Exception caught and stored in file !");
        }
        return isExisting;
    }

    public String checkRole(String userName,String pass){
        String sql = "SELECT user_role FROM users WHERE user_username=? AND user_password=?";
        String role = "";
        try( Connection con = DbConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);)
        {
            ps.setString(1,userName);
            ps.setString(2,pass);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                role = rs.getString("user_role");
            }
        }catch (SQLException e){
            wr.writeExceptionToFile(e);
            System.out.println("Exception caught and stored in file !");
        }
        return  role;
    }

    public List<Object> printServices(){
        List<Object> list = new ArrayList<>();
        list.add("service_name");
        String sql = SQL_SELECT + TABLE_SERVICE;
        return dbSelect(sql,list);
    }

    public Object getUserIDNumber(String username){
        List<Object> list = new ArrayList<>();
        str_clause = " WHERE user_username=?";
        String sql = SQL_SELECT + TABLE_USERS + str_clause;
        List<Object> column = new ArrayList<>();
        column.add("id_user");
        list.add(dbSelect(sql,column,username));
        return list;
    }

    public Object getBrandID(String brand){
        List<Object> list = new ArrayList<>();
        str_clause = " WHERE brand_name=?";
        String sql = SQL_SELECT+TABLE_BRANDS+str_clause;
        List<Object> column = new ArrayList<>();
        column.add("id_brand");
        list.add((dbSelect(sql,column,brand)));
        return list;
    }

    public String getBrandName(int brandID){
        String name = "";
        str_clause = " WHERE id_brand=?";
        String sql = SQL_SELECT+TABLE_BRANDS+str_clause;
        List<Object> column = new ArrayList<>();
        column.add("brand_name");
        List<Object> list = dbSelect(sql,column,brandID);
        name = (String) list.get(0);
        return name;
    }

    public String getModelName(int modelID){
        String name = "";
        str_clause = " WHERE id_model=?";
        List<Object> column = new ArrayList<>();
        column.add("model_name");
        String sql = SQL_SELECT+TABLE_MODELS+str_clause;
        List<Object> list = dbSelect(sql,column,modelID);
        name = (String) list.get(0);
        return name;
    }

    public Object getModelID(String model){
        List<Object> list = new ArrayList<>();
        str_clause = " WHERE model_name=?";
        List<Object> column = new ArrayList<>();
        column.add("id_model");
        String sql = SQL_SELECT+TABLE_MODELS+str_clause;
        list.add(dbSelect(sql,column,model));

        return list;
    }

    public List<Object> getModelsByBrand(int brandID){
        List<Object> list = new ArrayList<>();
        str_clause = " WHERE brand_id=?";
        List<Object> column = new ArrayList<>();
        column.add("model_name");
        String sql = SQL_SELECT+TABLE_MODELS+str_clause;
        list = dbSelect(sql,column,brandID);
        return list;
    }

    public String getService(int serviceID){
        String name = "";
        str_clause = " WHERE id_service=?";
        List<Object> column = new ArrayList<>();
        column.add("service_name");
        String sql = SQL_SELECT+TABLE_SERVICE+str_clause;
        List<Object> list = dbSelect(sql,column,serviceID);
        name = (String) list.get(0);
        return name;
    }

    public Object getServiceID(String service){
        List<Object> list = new ArrayList<>();
        str_clause = " WHERE service_name=?";
        String sql = SQL_SELECT+TABLE_SERVICE+str_clause;
        List<Object> column = new ArrayList<>();
        column.add("id_service");
        list.add(dbSelect(sql,column,service));
        return list;
    }

    public void deleteRequestByID(int requestID){
        str_clause = " WHERE id_request=?";
        String sql = SQL_DELETE+ TABLE_REQUESTS + str_clause;
        List<Object> list = new ArrayList<>();
        list.add(requestID);
        dbUpdate(sql,list);
    }

}


