import bean.City;
import bean.Forecast;
import bean.Weather;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import tools.HttpRequest;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static bean.City.getCity;
import static bean.City.getCode;

//    public static final String WEATHER_XML_URL = "https://www.sojson.com/open/api/weather/xml.shtml?city=";

public class MainTest {

    private static final String WEATHER_JSON_URL = "https://www.sojson.com/open/api/weather/json.shtml?city=";
    private static final String DBURL = "jdbc:sqlserver://localhost:1433;databaseName=design";
    private static final String userName = "xieyipeng";
    private static final String userPwd = "123456";

    public static void main(String[] args) {
        int user = initUser();
        String city = joinCity();//输入城市名称
    }

    private static int initUser() {
        System.out.println("1:系统管理员");
        System.out.println("2:气象部门工作人员");
        System.out.println("3:普通用户");
        System.out.println("4:统计人员");
        System.out.println("** 请选择登陆方式（输入数字） **");
        int user = 0;
        boolean in = true;
        while (in) {
            Scanner scanner = new Scanner(System.in);
            user = scanner.nextInt();
            if (user == 1 | user == 2 | user == 3 | user == 4) {
                in = false;
            } else {
                System.out.println("输入格式不正确，请重新输入");
            }
        }
        return user;
    }

    /**
     * 向数据库中添加城市信息
     */
    private static void initCity(int user) {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        if (user==3|user==4){
            return;
        }
        try {
            connection = DriverManager.getConnection(DBURL, userName, userPwd);
            System.out.println("添加城市信息，数据库连接成功。");
            statement = connection.createStatement();
            String beijing = "'北京'";
            String nextOrNot = "select *" +
                    "FROM city " +
                    "WHERE city.city_name=" + beijing;
            resultSet = statement.executeQuery(nextOrNot);
            if (resultSet.next()) {
                System.out.println("城市信息已经添加完毕，不能重复添加。");
                return;
            } else {
                List<String> cityCode = City.separate();
                List<String> city = getCity(cityCode);
                List<String> code = getCode(cityCode);
                for (int i = 0; i < city.size(); i++) {
                    String SQL = "INSERT INTO city(" +
                            "city_name,city_regional)" +
                            "VALUES (" + city.get(i) + "," + code.get(i) +
                            ")";
                    statement.executeUpdate(SQL);
                    int a = i + 1;
                    System.out.println("第" + a + "个城市信息添加成功！");
                }
            }
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
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
    }

    /**
     * 向数据库中添加天气数据
     *
     * @param weather   weather
     * @param forecasts forecasts
     */
    private static void addData(String city, Weather weather, List<Forecast> forecasts) {
        Connection connection = null;
        Statement statement = null;
//        ResultSet resultSet = null;
        try {
            connection = DriverManager.getConnection(DBURL, userName, userPwd);
            System.out.println("添加天气数据，数据库连接成功。");
            statement = connection.createStatement();
            if (city != null) {
                city = "\'" + city + "\'";
                String SQL = "INSERT INTO weather(weather_date,city,shidu,pm25,quality,wendu,sunrise,sunset,weather_type,notice,weather_week,high,low)" +
                        "VALUES (" + weather.getWeatherDate() + "," + city + "," + weather.getShidu() + "," +
                        weather.getPm25() + "," + weather.getQuality() + "," + weather.getWendu() + "," +
                        forecasts.get(0).getSunrise() + "," + forecasts.get(0).getSunset() + "," +
                        forecasts.get(0).getType() + "," + forecasts.get(0).getNotice() + "," +
                        forecasts.get(0).getWeek() + "," + forecasts.get(0).getHigh() + "," + forecasts.get(0).getLow() + ")";
                statement.executeUpdate(SQL);
                System.out.println("添加天气数据成功。");
            } else {
                System.out.println("city = null");
            }
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (statement != null) try {
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            if (connection != null) try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 输入城市
     *
     * @return 未解析的json
     */
    private static String joinCity() {
        System.out.println("输入城市名称：");
        Scanner scanner = new Scanner(System.in);
        String city = scanner.next();
        if (!City.CITY_CODE.contains(city)) {
            System.out.println("请输入正确的城市名称。");
            return null;
        } else {
            return city;
        }
    }
}

