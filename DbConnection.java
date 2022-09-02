import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
public class DbConnection {

    private static Connection con;
    static
    {
        try
        {
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/service","root", "12ra345");
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

    public static Connection getConnection(){
        return con;
    }

}
