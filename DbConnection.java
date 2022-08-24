import java.sql.Connection;
import java.sql.DriverManager;

public class DbConnection {

    static Connection con;

    public static Connection getConnection() throws Exception{
        if(con == null){
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/service","root", "12ra345");
        }
        return con;
    }
}
