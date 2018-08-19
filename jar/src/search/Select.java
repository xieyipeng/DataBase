package search;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created with IntelliJ IDEA.
 * TODO: 简单查询
 * User: xieyipeng
 * Date: 2018-08-19
 * Time: 17:16
 */
public class Select {
    /**
     * TODO：所有查询
     *
     * @param connection connection
     * @param statement  statement
     * @param selectSQL  查询语句
     * @param table      表名
     * @return 查询结果
     */
    public static List<List<String>> selectSimple(Connection connection, Statement statement, String selectSQL, String table) {
        List<List<String>> lists = new ArrayList<>();
        ResultSet resultSet;
        try {
            statement = connection.createStatement();
            String[] columns;
            final String xinghua = "SELECT (.*?) FROM";
            Pattern P_ps = Pattern.compile(xinghua);
            Matcher m = P_ps.matcher(selectSQL);
            String temp = "";
            while (m.find()) {
                temp = m.group();
            }
            temp = temp.substring(7, temp.length() - 5);
            columns = SingleOrConnectionSelect.convertStrToArray(temp, table, statement);
            resultSet = statement.executeQuery(selectSQL);
            SingleOrConnectionSelect.addList(lists,resultSet,columns);
            return lists;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return lists;
    }

}
