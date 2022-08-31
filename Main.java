import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;


public class Main {

    public static void main(String[] args) {
       Workers ws = new Workers();
       System.out.println(ws.printAllRequests());
       System.out.println(ws.getModelName(3));
    }
}
