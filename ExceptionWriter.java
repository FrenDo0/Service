import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Date;

public class ExceptionWriter {
    public void writeExceptionToFile(SQLException e) {
        try (FileWriter file = new FileWriter("exception.txt", true);
             BufferedWriter out = new BufferedWriter(file); PrintWriter print = new PrintWriter(out, true))
        {
            Date date = new Date();
            print.println("New exception \n ***********");
            e.printStackTrace(print);
            print.println("SQLException caught !");
            print.println(date);
            print.println("*****************");
        }
        catch (Exception ie) {
            throw new RuntimeException("Could not write Exception to file", ie);
        }
    }
}
