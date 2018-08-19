package search;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;


/**
 * Created with IntelliJ IDEA.
 * TODO: 数据库单表查询以及连接查询
 * User: xieyipeng
 * Date: 2018 - 08 - 19
 * Time: 15:58
 */

public class SingleOrConnectionSelect {

    /**
     * TODO：所有查询
     *
     * @param connection connection
     * @param statement  statement
     * @param column     列条件
     * @param table      表条件
     * @param condition  条件语句
     * @param groupBy    分组
     * @param having     having条件
     * @param orderBy    排序条件
     * @return 查询结果
     */
    public static List<List<String>> selectAll(Connection connection, Statement statement, String column, String table, String condition, String groupBy, String having, String orderBy) {
        List<List<String>> lists = new ArrayList<>();
        ResultSet resultSet;
        try {
            statement = connection.createStatement();
            String[] columns = convertStrToArray(column, table, statement);
            String selectSQL = checkSelectSQL(column, table, condition, groupBy, having, orderBy);
            resultSet = statement.executeQuery(selectSQL);
            addList(lists, resultSet, columns);
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

    /**
     * TODO：根据传入的参数选择正确的查询语句
     *
     * @param column    列条件
     * @param table     表条件
     * @param condition 条件语句
     * @param groupBy   分组
     * @param having    having条件
     * @param orderBy   排序条件
     * @return 正确的语句
     */
    private static String checkSelectSQL(String column, String table, String condition, String groupBy, String having, String orderBy) {
        String selectSQL = "";
        if (condition == null && groupBy == null && having == null && orderBy == null) {
            selectSQL = "SELECT " + column + " FROM " + table;
        }
        if (condition != null && groupBy == null && having == null && orderBy == null) {
            selectSQL = "SELECT " + column + " FROM " + table + " WHERE " + condition;
        }
        if (condition == null && groupBy != null && having == null && orderBy == null) {
            selectSQL = "SELECT " + column + " FROM " + table + " GROUP BY " + groupBy;
        }
        if (condition == null && groupBy == null && having != null && orderBy == null) {
            selectSQL = "SELECT " + column + " FROM " + table + " HAVING " + having;
        }
        if (condition == null && groupBy == null && having == null && orderBy != null) {
            selectSQL = "SELECT " + column + " FROM " + table + " ORDER BY " + orderBy;
        }

        if (condition != null && groupBy != null && having == null && orderBy == null) {
            selectSQL = "SELECT " + column + " FROM " + table + " WHERE " + condition + " GROUP BY " + groupBy;
        }
        if (condition != null && groupBy == null && having != null && orderBy == null) {
            selectSQL = "SELECT " + column + " FROM " + table + " WHERE " + condition + " HAVING " + having;
        }
        if (condition != null && groupBy == null && having == null && orderBy != null) {
            selectSQL = "SELECT " + column + " FROM " + table + " WHERE " + condition + " ORDER BY " + orderBy;
        }
        if (condition == null && groupBy != null && having != null && orderBy == null) {
            selectSQL = "SELECT " + column + " FROM " + table + " GROUP BY " + groupBy + " HAVING " + having;
        }
        if (condition == null && groupBy != null && having == null && orderBy != null) {
            selectSQL = "SELECT " + column + " FROM " + table + " GROUP BY " + groupBy + " ORDER BY " + orderBy;
        }
        if (condition == null && groupBy == null && having != null && orderBy != null) {
            selectSQL = "SELECT " + column + " FROM " + table + " HAVING " + having + " ORDER BY " + orderBy;
        }

        if (condition != null && groupBy != null && having != null && orderBy == null) {
            selectSQL = "SELECT " + column + " FROM " + table + " WHERE " + condition + " GROUP BY " + groupBy + " HAVING " + having;
        }
        if (condition != null && groupBy != null && having == null && orderBy != null) {
            selectSQL = "SELECT " + column + " FROM " + table + " WHERE " + condition + " GROUP BY " + groupBy + " ORDER BY " + orderBy;
        }
        if (condition != null && groupBy == null && having != null && orderBy != null) {
            selectSQL = "SELECT " + column + " FROM " + table + " WHERE " + condition + " HAVING " + having + " ORDER BY " + orderBy;
        }
        if (condition == null && groupBy != null && having != null && orderBy != null) {
            selectSQL = "SELECT " + column + " FROM " + table + " GROUP BY " + groupBy + " HAVING " + having + " ORDER BY " + orderBy;
        }

        if (condition != null && groupBy != null && having != null && orderBy != null) {
            selectSQL = "SELECT " + column + " FROM " + table + " WHERE " + condition + " GROUP BY " + groupBy + " HAVING " + having + " ORDER BY " + orderBy;
        }
        return selectSQL;
    }


    /**
     * TODO：对条件查询结果排序
     * @param connection connection
     * @param statement  statement
     * @param column    列条件
     * @param table     表条件
     * @param groupBy   分组
     * @param having    having条件
     * @return 查询结果
     */
    public static List<List<String>> groupBySelectWithoutConditions(Connection connection, Statement statement, String column, String table, String groupBy, String having) {
        List<List<String>> lists = new ArrayList<>();
        ResultSet resultSet;
        try {
            statement = connection.createStatement();
            String[] columns = convertStrToArray(column, table, statement);
            String selectSQL = "";
            selectSQL = "SELECT " + column + " FROM " + table + " ORDER BY " + groupBy + " HAVING " + having;
            System.out.println(selectSQL);
            resultSet = statement.executeQuery(selectSQL);
            addList(lists, resultSet, columns);
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


    /**
     * TODO：对无条件查询结果排序
     *
     * @param connection connection
     * @param statement  statement
     * @param column     列名
     * @param table      表名
     * @param orderBy    排序子句
     * @return 查询结果
     */

    public static List<List<String>> orderBySelectWithoutConditions(Connection connection, Statement statement, String column, String table, String orderBy) {
        List<List<String>> lists = new ArrayList<>();
        ResultSet resultSet;
        try {
            statement = connection.createStatement();
            String[] columns = convertStrToArray(column, table, statement);
            String selectSQL = "";
            selectSQL = "SELECT " + column + " FROM " + table + " ORDER BY " + orderBy;
            System.out.println(selectSQL);
            resultSet = statement.executeQuery(selectSQL);
            addList(lists, resultSet, columns);
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


    /**
     * TODO：对条件查询结果排序
     *
     * @param connection connection
     * @param statement  statement
     * @param column     列名
     * @param table      表名
     * @param orderBy    排序子句
     * @param conditions 条件
     * @return 查询结果
     */

    public static List<List<String>> orderBySelectWithConditions(Connection connection, Statement statement, String column, String table, String orderBy, String conditions) {
        //lists = SingleOrConnectionSelect.orderBySelect(connection, null, "*", "weather","shidu","weather_date='20180702'");
        List<List<String>> lists = new ArrayList<>();
        ResultSet resultSet;
        try {
            statement = connection.createStatement();
            String[] columns = convertStrToArray(column, table, statement);
            String selectSQL = "";
            selectSQL = "SELECT " + column + " FROM " + table + " WHERE " + conditions + " ORDER BY " + orderBy;
            resultSet = statement.executeQuery(selectSQL);
            addList(lists, resultSet, columns);
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


    /**
     * TODO：条件查询
     *
     * @param connection connection
     * @param statement  statement
     * @param column     列名
     * @param table      表名
     * @param conditions 条件语句 如："userName='xieyipeng'"
     * @param distinct   是否去掉重复行
     * @return 查询结果
     */

    public static List<List<String>> selectWithConditions(Connection connection, Statement statement, String column, String table, String conditions, boolean distinct) {
        List<List<String>> lists = new ArrayList<>();
        ResultSet resultSet;
        try {
            statement = connection.createStatement();
            String[] columns = convertStrToArray(column, table, statement);
            String selectSQL = "";
            if (distinct) {
                selectSQL = "SELECT DISTINCT " + column + " FROM " + table + " WHERE " + conditions;
            } else {
                selectSQL = "SELECT " + column + " FROM " + table + " WHERE " + conditions;
            }
            resultSet = statement.executeQuery(selectSQL);
            addList(lists, resultSet, columns);
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

    /**
     * TODO：向list中添加元素
     *
     * @param lists     目标list
     * @param resultSet 查询结果集
     * @param columns   列名
     * @throws SQLException sqlException
     */

    public static void addList(List<List<String>> lists, ResultSet resultSet, String[] columns) throws SQLException {
        while (resultSet.next()) {
            List<String> list = new ArrayList<>();
            for (String column1 : columns) {
                list.add(resultSet.getString(column1));
            }
            lists.add(list);
        }
    }


    /**
     * TODO：无条件查询
     *
     * @param connection connection
     * @param statement  statement
     * @param column     列名，例如："*"或者"userName,wordPass",中间以逗号隔开
     * @param table      表名，同列名，以逗号隔开
     * @param distinct   是否去掉重复行
     * @return 查询结果
     */

    public static List<List<String>> selectWithoutConditions(Connection connection, Statement statement, String column, String table, boolean distinct) {
        List<List<String>> lists = new ArrayList<>();
        ResultSet resultSet;
        try {
            statement = connection.createStatement();
            String[] columns = convertStrToArray(column, table, statement);
            String selectSQL = "";
            if (distinct) {
                selectSQL = "SELECT DISTINCT " + column + " FROM " + table;
            } else {
                selectSQL = "SELECT " + column + " FROM " + table;
            }
            resultSet = statement.executeQuery(selectSQL);
            addList(lists, resultSet, columns);
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


    /**
     * TODO：将一段逗号分割的字符串转换成一个数组
     *
     * @param str 字符串
     * @return 字符串
     */

    public static String[] convertStrToArray(String str, String table, Statement statement) {
        if (str.equals("*")) {
            ResultSet resultSet;
            try {
                resultSet = statement.executeQuery("SELECT * FROM " + table);
                ResultSetMetaData metaData = resultSet.getMetaData();
                str = "";
                int num = metaData.getColumnCount();
                for (int i = 0; i < num; i++) {
                    String columName = metaData.getColumnName(i + 1);
                    str = str + columName;
                    if (i != num - 1) {
                        str = str + ",";
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        StringTokenizer st = new StringTokenizer(str, ",");
        String[] strArray = new String[st.countTokens()];
        int i = 0;
        while (st.hasMoreTokens()) {
            strArray[i++] = st.nextToken();
        }
        return strArray;
    }
}
