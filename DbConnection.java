import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

public class DbConnection {

    private static Connection con;
    static
    {
        String url = "";
        String username = "";
        String pass = "";
        url = getCredentials()[0];
        username = getCredentials()[1];
        pass = getCredentials()[2];
        try
        {
            con = DriverManager.getConnection(url, username, pass);
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

    public static Connection getConnection(){
        return con;
    }

    private static String[] getCredentials(){
        String[] input = new String[0];
        try
        {
            File file = new File("credentials.txt");
            Scanner sc = new Scanner(file);
            while(sc.hasNextLine()){
                String data = sc.nextLine();
                input = data.split("->",3);
            }
            sc.close();

        }catch (FileNotFoundException e){
            System.out.println("File not found");
        }
        return input;
    }
}
