import java.sql.Connection;
import java.sql.DriverManager;

public class test {
    public static void main(String[] args) {
        String dbURL = "jdbc:sqlserver://localhost:1433;databaseName=design";
        String userName = "xieyipeng";
        String userPwd = "123456";
        try {
            Connection dbConn = DriverManager.getConnection(dbURL, userName, userPwd);
            System.out.println("连接数据库成功");
            dbConn.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("连接失败");
        }
    }
}