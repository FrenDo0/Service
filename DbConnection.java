import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnection {

    private static Connection con;

    public static Connection getConnection() throws SQLException {
        if(con == null){
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/service","root", "12ra345");
        }
        return con;
    }
}
