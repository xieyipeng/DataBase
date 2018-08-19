import search.Select;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;


public class test {
    private static final String DBURL = "design";
    private static final String userNameSQL = "xieyipeng";
    private static final String userPwd = "123456";

    public static void main(String[] args) {
        Connection connection = myConnection.connect(userNameSQL, userPwd, DBURL);
        List<List<String>> lists = new ArrayList<>();
        if (connection != null) {
            lists = Select.selectSimple(connection, null, "SELECT * FROM weather WHERE weather_date='20180702'","weather");
        }
        for (List<String> list : lists) {
            for (String aList : list) {
                System.out.print(aList+"  ");
            }
            System.out.println();
        }
    }
}
