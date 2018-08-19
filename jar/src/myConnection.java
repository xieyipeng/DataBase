import java.sql.*;

public class myConnection {

    public static Connection connect(String userName, String passWord, String dataBastName) {
        String DBURL = "jdbc:sqlserver://localhost:1433;databaseName="+dataBastName;
        try {
            return DriverManager.getConnection(DBURL,userName,passWord);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }




}
