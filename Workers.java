import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

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

    public void CreateNewProfile(Users worker){
        try
        {
            worker.setRole("worker");
            Connection con = DbConnection.getConnection();
            String sql = "INSERT INTO users" + "(user_fname,user_sname,user_username,user_password,user_role)" + "VALUES (?,?,?,?,?)";
            PreparedStatement ps = con.prepareStatement(sql);
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
