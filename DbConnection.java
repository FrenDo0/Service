import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class DbConnection {

    private static Connection con;

    public static synchronized Connection getConnection(){
        ResourceBundle rb = ResourceBundle.getBundle("connection");
        String url = rb.getString("url");
        String username = rb.getString("username");
        String password = rb.getString("password");
        try
        {
            con = DriverManager.getConnection(url,username,password);
        }catch (SQLException e){
            System.out.println(e.getMessage());
            ExceptionWriter wr = new ExceptionWriter();
            wr.writeExceptionToFile(e);
        }
        return con;
    }

}
