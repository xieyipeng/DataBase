package frame;

import bean.Forecast;
import bean.Weather;
import tools.MineDialog;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Vector;

import static bean.City.getCityJson;
import static bean.City.getProvinceFromJson;
import static frame.Login.*;
import static frame.Main.parse;

/**
 * search 中 User/Manage Table添加修改逻辑;
 * 添加删除按钮
 */

public class Manage extends JFrame {
    private static JFrame manageFrame;
    private static JLabel manageLabel;
    private static JTabbedPane leftTabbedPan;
    private static ImageIcon image;

    private static Weather weather = new Weather();
    private static List<Forecast> forecasts = new ArrayList<>();

    private static JPanel add;
    private static JButton addButton;
    private static JTable addTable;
    private static int addRow;
    private static int addColumn;
    private static DefaultTableModel addModel;

    private static JPanel del;
    private static JMenuItem delMenuItem;
    private static int delRow;
    //    private static int delColumn;
    private static DefaultTableModel delModelWeather;
    private static JTable delWeatherTable;
    private static JPopupMenu delMenu;

    private static JPanel update;
    private static int alterRow;
    private static int alterColumn;
    private static JTable updateUserTable;
    private static JTable updateManageTable;
    private static JTable updateWeatherTable;
    private static DefaultTableModel updateModelUser;
    private static DefaultTableModel updateModelManage;
    private static DefaultTableModel updateModelWeather;

    private static JPanel search;
    private static JButton searchRefreshButton;
    private static JTable searchUserTable;
    private static JTable searchManageTable;
    private static JTable searchWeatherTable;
    private static DefaultTableModel searchModelUser;
    private static DefaultTableModel searchModelManage;
    private static DefaultTableModel searchModelWeather;

    private static JComboBox<String> provinceChoose;
    private static JComboBox<String> cityChoose;

    public Manage() {
        manageFrame = new JFrame("管理员");
        manageLabel = new JLabel();
        image = new ImageIcon(Main.class.getResource("/frame/icon/manage.png"));
        image.setImage(image.getImage().getScaledInstance(image.getIconWidth(), image.getIconHeight(), Image.SCALE_DEFAULT));
        manageLabel.setIcon(image);
        manageFrame.setSize(image.getIconWidth(), image.getIconHeight());
//        975
//        595
        add = new JPanel();
        add.setLayout(null);

        del = new JPanel();
        del.setLayout(null);

        update = new JPanel();
        update.setLayout(null);

        search = new JPanel();
        search.setLayout(null);

        addAdd();
        addDel();
        addUpdate();
        addSearch();

        initClicks();

        leftTabbedPan = new JTabbedPane(JTabbedPane.LEFT);
        leftTabbedPan.add("添加", add);
        leftTabbedPan.add("删除", del);
        leftTabbedPan.add("修改", update);
        leftTabbedPan.add("查找", search);
        leftTabbedPan.setBounds(0, 0, image.getIconWidth(), image.getIconHeight());
        leftTabbedPan.setSelectedIndex(0);

        manageFrame.add(leftTabbedPan);
        manageFrame.add(manageLabel);
        manageFrame.setLocation(300, 200);
        manageFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        manageFrame.setVisible(true);
    }

    private void addDel() {
        Vector nameWeather = new Vector();
        nameWeather.add("日期");
        nameWeather.add("城市");
        nameWeather.add("湿度");
        nameWeather.add("空气质量");
        nameWeather.add("温度");
        nameWeather.add("日出");
        nameWeather.add("日落");
        nameWeather.add("天气");
        nameWeather.add("星期");
        nameWeather.add("最高气温");
        nameWeather.add("最低气温");
        Vector dataWeather = new Vector();
        delModelWeather = new DefaultTableModel(dataWeather, nameWeather) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        delWeatherTable = new JTable(delModelWeather);
        JScrollPane jScrollPaneWeather = new JScrollPane(delWeatherTable);
        jScrollPaneWeather.setBounds(25, 25, 860, 500);

        delMenu = new JPopupMenu();
        delMenuItem = new JMenuItem();
        delMenuItem.setText("删除");

        delMenu.add(delMenuItem);

        ResultSet resultSet = null;
        Connection connection = null;
        Statement statement = null;

        try {
            connection = DriverManager.getConnection(DBURL, userNameSQL, userPwd);
            String searchWeatherFirst = "SELECT * FROM weather";

            statement = connection.createStatement();
            resultSet = statement.executeQuery(searchWeatherFirst);

            addWeather(resultSet, delModelWeather);
            connection.close();
        } catch (SQLException e1) {
            e1.printStackTrace();
            new MineDialog("数据库查询出错");
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e2) {
                    e2.printStackTrace();
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
        }

        del.add(jScrollPaneWeather);

    }

    private void addWeather(ResultSet resultSet, DefaultTableModel defaultTableModel) throws SQLException {
        while (resultSet.next()) {
            Vector vWeather = new Vector();
            vWeather.add(resultSet.getString(1));
            vWeather.add(resultSet.getString(2));
            vWeather.add(resultSet.getString(3));
            vWeather.add(resultSet.getString(4));
            vWeather.add(resultSet.getString(5));
            vWeather.add(resultSet.getString(6));
            vWeather.add(resultSet.getString(7));
            vWeather.add(resultSet.getString(8));
            vWeather.add(resultSet.getString(9));
            vWeather.add(resultSet.getString(10));
            vWeather.add(resultSet.getString(11));
            defaultTableModel.addRow(vWeather);
        }
    }

    private void addUpdate() {
        Vector nameWeather = new Vector();
        nameWeather.add("日期");
        nameWeather.add("城市");
        nameWeather.add("湿度");
        nameWeather.add("空气质量");
        nameWeather.add("温度");
        nameWeather.add("日出");
        nameWeather.add("日落");
        nameWeather.add("天气");
        nameWeather.add("星期");
        nameWeather.add("最高气温");
        nameWeather.add("最低气温");
        Vector dataWeather = new Vector();
        updateModelWeather = new DefaultTableModel(dataWeather, nameWeather) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column != 0 && column != 1;
//                if (column==0||column==1){
//                    return false;
//                }else {
//                    return true;
//                }
            }
        };
        updateWeatherTable = new JTable(updateModelWeather);
        JScrollPane jScrollPaneWeather = new JScrollPane(updateWeatherTable);
        jScrollPaneWeather.setBounds(25, 270, 860, 200);

        Vector nameManage = new Vector();
        nameManage.add("用户名");
        nameManage.add("密码");
        Vector dataManage = new Vector();
        updateModelManage = new DefaultTableModel(dataManage, nameManage) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        updateManageTable = new JTable(updateModelManage);
        JScrollPane jScrollPaneManage = new JScrollPane(updateManageTable);
        jScrollPaneManage.setBounds(500, 40, 150, 200);

        Vector nameUser = new Vector();
        nameUser.add("用户名");
        nameUser.add("密码");
        Vector dataUser = new Vector();
        updateModelUser = new DefaultTableModel(dataUser, nameUser) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        updateUserTable = new JTable(updateModelUser);
        JScrollPane jScrollPaneUser = new JScrollPane(updateUserTable);
        jScrollPaneUser.setBounds(250, 40, 150, 200);

        JLabel updateUserText = new JLabel("用户：");
        updateUserText.setBounds(250, 10, 100, 30);

        JLabel updateManageText = new JLabel("管理员：");
        updateManageText.setBounds(500, 10, 100, 30);

        JLabel updateWeatherText = new JLabel("天气：");
        updateWeatherText.setBounds(25, 240, 100, 30);


        tableFirstAddSomeThing(updateModelUser, updateModelManage, updateModelWeather);

        update.add(updateWeatherText);
        update.add(updateManageText);
        update.add(updateUserText);
        update.add(jScrollPaneWeather);
        update.add(jScrollPaneUser);
        update.add(jScrollPaneManage);

    }

    private void addSearch() {

        Vector nameWeather = new Vector();
        nameWeather.add("日期");
        nameWeather.add("城市");
        nameWeather.add("湿度");
        nameWeather.add("空气质量");
        nameWeather.add("温度");
        nameWeather.add("日出");
        nameWeather.add("日落");
        nameWeather.add("天气");
        nameWeather.add("星期");
        nameWeather.add("最高气温");
        nameWeather.add("最低气温");
        Vector dataWeather = new Vector();
        searchModelWeather = new DefaultTableModel(dataWeather, nameWeather) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        searchWeatherTable = new JTable(searchModelWeather);
        JScrollPane jScrollPaneWeather = new JScrollPane(searchWeatherTable);
        jScrollPaneWeather.setBounds(25, 270, 860, 200);

        Vector nameManage = new Vector();
        nameManage.add("用户名");
        nameManage.add("密码");
        Vector dataManage = new Vector();
        searchModelManage = new DefaultTableModel(dataManage, nameManage) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        searchManageTable = new JTable(searchModelManage);
        JScrollPane jScrollPaneManage = new JScrollPane(searchManageTable);
        jScrollPaneManage.setBounds(500, 40, 150, 200);

        Vector nameUser = new Vector();
        nameUser.add("用户名");
        nameUser.add("密码");
        Vector dataUser = new Vector();
        searchModelUser = new DefaultTableModel(dataUser, nameUser) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        searchUserTable = new JTable(searchModelUser);
        JScrollPane jScrollPaneUser = new JScrollPane(searchUserTable);
        jScrollPaneUser.setBounds(250, 40, 150, 200);

        JLabel searchUserText = new JLabel("用户：");
        searchUserText.setBounds(250, 10, 100, 30);

        JLabel searchManageText = new JLabel("管理员：");
        searchManageText.setBounds(500, 10, 100, 30);

        JLabel searchWeatherText = new JLabel("天气：");
        searchWeatherText.setBounds(25, 240, 100, 30);


        tableFirstAddSomeThing(searchModelUser, searchModelManage, searchModelWeather);

        searchRefreshButton = new JButton("刷新一下");
        searchRefreshButton.setBounds(320, 490, 250, 30);

        search.add(searchRefreshButton);

        search.add(searchWeatherText);
        search.add(searchManageText);
        search.add(searchUserText);

        search.add(jScrollPaneWeather);
        search.add(jScrollPaneUser);
        search.add(jScrollPaneManage);

    }

    private void tableFirstAddSomeThing(DefaultTableModel ModelUser, DefaultTableModel ModelManage, DefaultTableModel ModelWeather) {

        ResultSet resultSet = null;
        Connection connection = null;
        Statement statement = null;

        try {
            connection = DriverManager.getConnection(DBURL, userNameSQL, userPwd);

            String searchUserDataFirst = "SELECT * FROM ordinaryUser";
            String searchManageFirst = "SELECT * FROM management";
            String searchWeatherFirst = "SELECT * FROM weather";

            searchAddFirstUserAndManage(searchUserDataFirst, ModelUser);
            searchAddFirstUserAndManage(searchManageFirst, ModelManage);

            statement = connection.createStatement();
            resultSet = statement.executeQuery(searchWeatherFirst);
            addWeather(resultSet, ModelWeather);
            connection.close();
        } catch (SQLException e1) {
            e1.printStackTrace();
            new MineDialog("数据库查询出错");
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e2) {
                    e2.printStackTrace();
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }

    private void searchAddFirstUserAndManage(String searchWeatherFirst, DefaultTableModel searchModelWeather) throws SQLException {

        Connection connection = DriverManager.getConnection(DBURL, userNameSQL, userPwd);
        Statement statement = null;
        ResultSet resultSet = null;


        statement = connection.createStatement();
        resultSet = statement.executeQuery(searchWeatherFirst);
        while (resultSet.next()) {
            Vector vWeather = new Vector();
            vWeather.add(resultSet.getString(1));
            vWeather.add(resultSet.getString(2));
            searchModelWeather.addRow(vWeather);
        }
    }

    private void addAdd() {
        JLabel pro = new JLabel("省份：");
        pro.setBounds(200, 50, 50, 30);

        JLabel city = new JLabel("城市：");
        city.setBounds(500, 50, 50, 30);

        List<String> prov = getProvinceFromJson();
        String[] provs = new String[prov.size()];
        prov.toArray(provs);
        provinceChoose = new JComboBox<>(provs);
        provinceChoose.setSelectedIndex(30);
        provinceChoose.setBounds(300, 50, 100, 30);

        List<String> cityList = getCityJson("山西");
        String[] provs1 = new String[cityList.size()];
        cityList.toArray(provs1);
        cityChoose = new JComboBox<>(provs1);
        cityChoose.setSelectedIndex(2);
        cityChoose.setBounds(600, 50, 100, 30);

        System.out.println(Objects.requireNonNull(cityChoose.getSelectedItem()).toString());
        parse(Objects.requireNonNull(cityChoose.getSelectedItem()).toString(), weather, forecasts);

        Vector name = new Vector();
        name.add("日期");
        name.add("城市");
        name.add("湿度");
        name.add("空气质量");
        name.add("温度");
        name.add("日出");
        name.add("日落");
        name.add("天气");
        name.add("星期");
        name.add("最高气温");
        name.add("最低气温");
        Vector data = new Vector();
        data.add(weather.getWeatherDate());
        data.add(weather.getWeatherCity());
        data.add(weather.getShidu());
        data.add(weather.getQuality());
        data.add(weather.getWendu());
        data.add(forecasts.get(0).getSunrise());
        data.add(forecasts.get(0).getSunset());
        data.add(forecasts.get(0).getType());
        data.add(forecasts.get(0).getWeek());
        data.add(forecasts.get(0).getHigh());
        data.add(forecasts.get(0).getLow());
        Vector vData = new Vector();
        vData.add(data.clone());
        addModel = new DefaultTableModel(vData, name) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        addTable = new JTable(addModel);
        JScrollPane jScrollPane = new JScrollPane(addTable);
        jScrollPane.setBounds(25, 100, 860, 200);

        String addButtonText = "将" + Objects.requireNonNull(cityChoose.getSelectedItem()).toString() + "市的天气数据添加到数据库";
        addButton = new JButton(addButtonText);
        addButton.setBounds(320, 350, 250, 30);

        add.add(jScrollPane);
        add.add(pro);
        add.add(provinceChoose);
        add.add(city);
        add.add(cityChoose);
        add.add(addButton);
    }

    public static void main(String[] args) {
        new Manage();
    }

    private void initClicks() {
        provinceChoose.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //选择省份。
                List<String> city1 = getCityJson(Objects.requireNonNull(Manage.provinceChoose.getSelectedItem()).toString());
                String[] provsl = new String[city1.size()];
                city1.toArray(provsl);
                ComboBoxModel aModel = new DefaultComboBoxModel(provsl);
                cityChoose.setModel(aModel);
            }
        });

        cityChoose.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //选择窗口，给表添加一行。
                String newCity = Objects.requireNonNull(cityChoose.getSelectedItem()).toString();
                parse(newCity, weather, forecasts);
                Vector vector = new Vector();

                vector.add(weather.getWeatherDate());
                vector.add(weather.getWeatherCity());
                vector.add(weather.getShidu());
                vector.add(weather.getQuality());
                vector.add(weather.getWendu());
                vector.add(forecasts.get(0).getSunrise());
                vector.add(forecasts.get(0).getSunset());
                vector.add(forecasts.get(0).getType());
                vector.add(forecasts.get(0).getWeek());
                vector.add(forecasts.get(0).getHigh());
                vector.add(forecasts.get(0).getLow());
                addModel.addRow(vector);
                String addButtonText = "将" + Objects.requireNonNull(cityChoose.getSelectedItem()).toString() + "市的天气数据添加到数据库";
                addButton.setText(addButtonText);
                addTable.setRowSelectionInterval(addTable.getRowCount()-1,addTable.getRowCount()-1);
                addRow=addTable.getRowCount()-1;
                addColumn=0;
                System.out.println("set addTable select :"+addRow+" "+addColumn);
            }
        });

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                ResultSet resultSet = null;
                Connection connection = null;
                Statement statement = null;

                alterRow=0;
                alterColumn=0;

                System.out.println("get addTable :"+addRow+" "+addColumn);

                //添加数据到数据库
                try {
                    connection = DriverManager.getConnection(DBURL, userNameSQL, userPwd);
                    statement = connection.createStatement();
                    String insert = "INSERT INTO weather(weather_date,city,shidu,quality,wendu,sunrise,sunset,weather_type,weather_week,high,low) " +
                            "VALUES (" + addTable.getValueAt(addRow,0) + "," + addTable.getValueAt(addRow,1) + "," + addTable.getValueAt(addRow,2) + "," +
                            addTable.getValueAt(addRow,3) + "," + addTable.getValueAt(addRow,4) + "," + addTable.getValueAt(addRow,5) + ","
                            + addTable.getValueAt(addRow,6) + "," +
                            addTable.getValueAt(addRow,7) + "," + addTable.getValueAt(addRow,8) + "," + addTable.getValueAt(addRow,9) + ","
                            + addTable.getValueAt(addRow,10) + ")";

                    System.out.println(insert);

                    statement.executeUpdate(insert);

                    //每添加一条数据，刷新删除标签下的表
                    delModelWeather.setRowCount(0);
                    String searchWeatherFirst = "SELECT * FROM weather";
                    System.out.println(searchWeatherFirst);
                    resultSet = statement.executeQuery(searchWeatherFirst);
                    addWeather(resultSet, delModelWeather);


                    //刷新修改标签下的表
                    updateModelWeather.setRowCount(0);
                    String updateSQL = "SELECT * FROM weather";
                    System.out.println(updateSQL);
                    resultSet = statement.executeQuery(updateSQL);
                    addWeather(resultSet, updateModelWeather);

                    new MineDialog("添加成功！");
                    connection.close();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                    new MineDialog("数据出错或添加重复的键");
                } finally {
                    if (statement != null) {
                        try {
                            statement.close();
                        } catch (SQLException e2) {
                            e2.printStackTrace();
                        }
                    }
                    if (connection != null) {
                        try {
                            connection.close();
                        } catch (SQLException e1) {
                            e1.printStackTrace();
                        }
                    }
                }
            }
        });
        /**
         * 添加点击事件，更改addButton的内容
         * 点击，得到行数，更改addButton中城市的名称
         */
        addTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                addRow=addTable.rowAtPoint(e.getPoint());
                addColumn=addTable.columnAtPoint(e.getPoint());
                System.out.println("set addTable click :"+addRow+" "+addColumn);
                String cityString = (String) addTable.getValueAt(addTable.rowAtPoint(e.getPoint()), 1);
                cityString = cityString.substring(1, cityString.length() - 1);
                String addButtonText = "将" + cityString + "市的天气数据添加到数据库";
                addButton.setText(addButtonText);
            }
        });

        delMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("del");
                ResultSet resultSet = null;
                Connection connection = null;
                Statement statement = null;
                try {
                    connection = DriverManager.getConnection(DBURL, userNameSQL, userPwd);
                    statement = connection.createStatement();
                    String dataSQL = "\'" + delWeatherTable.getValueAt(delRow, 0) + "\'";
                    String citySQL = "\'" + delWeatherTable.getValueAt(delRow, 1) + "\'";
                    String delSQL = "DELETE " +
                            "FROM weather " +
                            "WHERE weather_date=" + dataSQL + "AND city=" + citySQL;
                    System.out.println(delSQL);
                    statement.execute(delSQL);

                    //更新删除标签下的表
                    delModelWeather.setRowCount(0);
                    String searchWeatherFirst = "SELECT * FROM weather";
                    resultSet = statement.executeQuery(searchWeatherFirst);
                    addWeather(resultSet, delModelWeather);


                    //刷新修改标签下的表
                    alterColumn=0;
                    alterRow=0;
                    updateModelWeather.setRowCount(0);
                    String updateSQL = "SELECT * FROM weather";
                    resultSet = statement.executeQuery(updateSQL);
                    addWeather(resultSet, updateModelWeather);

                    new MineDialog("删除成功");
                    connection.close();
                } catch (SQLException e1) {
                    new MineDialog("删除数据库出错");
                    e1.printStackTrace();
                } finally {
                    if (resultSet != null) {
                        try {
                            resultSet.close();
                        } catch (SQLException e1) {
                            e1.printStackTrace();
                        }
                    }
                    if (statement != null) {
                        try {
                            statement.close();
                        } catch (SQLException e2) {
                            e2.printStackTrace();
                        }
                    }
                    if (connection != null) {
                        try {
                            connection.close();
                        } catch (SQLException e1) {
                            e1.printStackTrace();
                        }
                    }
                }
            }
        });

        delWeatherTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                delRow = delWeatherTable.rowAtPoint(e.getPoint());
//                delColumn=delWeatherTable.columnAtPoint(e.getPoint());
            }
        });


        delWeatherTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON3) {
                    int focusedRowIndex = delWeatherTable.rowAtPoint(e.getPoint());
                    if (focusedRowIndex == -1) {
                        return;
                    }
                    delWeatherTable.setRowSelectionInterval(focusedRowIndex, focusedRowIndex);
                    delMenu.show(delWeatherTable, e.getX(), e.getY());
                }
            }
        });

        updateWeatherTable.getModel().addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent e) {
//                System.out.println("type:" + e.getType());
//                System.out.println("m行" + alterRow);
//                System.out.println("m列" + alterColumn);
//                System.out.println("修改的值：" + updateWeatherTable.getValueAt(alterRow, alterColumn));
                ResultSet resultSet = null;
                Connection connection = null;
                Statement statement = null;
                try {
                    connection = DriverManager.getConnection(DBURL, userNameSQL, userPwd);

                    if (alterColumn==0&&alterRow==0){
                        System.out.println("alterRow == alterColumn == 0");
                        //更新删除标签下的表
                        delModelWeather.setRowCount(0);
                        String searchWeatherFirst = "SELECT * FROM weather";
                        statement = connection.createStatement();
                        resultSet = statement.executeQuery(searchWeatherFirst);
                        addWeather(resultSet, delModelWeather);
                    }else {
                        System.out.println("alterRow != 0 && alterColumn != 0");
                        String alterString = getAlterString(alterRow, alterColumn);
                        statement = connection.createStatement();
                        statement.executeUpdate(alterString);
                        new MineDialog("修改成功");
                    }


                } catch (SQLException e1) {
                    new MineDialog("修改数据库出错");
                    e1.printStackTrace();
                }
            }
        });

        updateWeatherTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
//                System.out.println("mo行" + alterRow);
//                System.out.println("mo列" + alterColumn);
                alterRow = updateWeatherTable.rowAtPoint(e.getPoint());
                alterColumn = updateWeatherTable.columnAtPoint(e.getPoint());
            }
        });


//        updateRefreshButton.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                updateModelUser.setRowCount(0);
//                updateModelManage.setRowCount(0);
//                updateModelWeather.setRowCount(0);
//                try {
//                    connection = DriverManager.getConnection(DBURL, userNameSQL, userPwd);
//
//                    String searchUserData = "SELECT * FROM ordinaryUser";
//                    String searchManageFirst = "SELECT * FROM management";
//                    String searchWeatherFirst = "SELECT * FROM weather";
//
//                    statement = connection.createStatement();
//                    resultSet = statement.executeQuery(searchUserData);
//                    while (resultSet.next()) {
//                        Vector vector = new Vector();
//                        vector.add(resultSet.getString(1));
//                        vector.add(resultSet.getString(2));
//                        updateModelUser.addRow(vector);
//                    }
//
//                    resultSet = statement.executeQuery(searchManageFirst);
//                    while (resultSet.next()) {
//                        Vector vector = new Vector();
//                        vector.add(resultSet.getString(1));
//                        vector.add(resultSet.getString(2));
//                        updateModelManage.addRow(vector);
//                    }
//
//                    resultSet = statement.executeQuery(searchWeatherFirst);
//                    while (resultSet.next()) {
//                        Vector vector = new Vector();
//                        vector.add(resultSet.getString(1));
//                        vector.add(resultSet.getString(2));
//                        vector.add(resultSet.getString(3));
//                        vector.add(resultSet.getString(4));
//                        vector.add(resultSet.getString(5));
//                        vector.add(resultSet.getString(6));
//                        vector.add(resultSet.getString(7));
//                        vector.add(resultSet.getString(8));
//                        vector.add(resultSet.getString(9));
//                        vector.add(resultSet.getString(10));
//                        vector.add(resultSet.getString(11));
//                        vector.add(resultSet.getString(12));
//                        updateModelWeather.addRow(vector);
//                    }
//                    connection.close();
//                } catch (SQLException e1) {
//                    e1.printStackTrace();
//                    new MineDialog("数据库查询出错");
//                } finally {
//                    if (resultSet != null) {
//                        try {
//                            resultSet.close();
//                        } catch (SQLException e1) {
//                            e1.printStackTrace();
//                        }
//                    }
//                    if (statement != null) {
//                        try {
//                            statement.close();
//                        } catch (SQLException e2) {
//                            e2.printStackTrace();
//                        }
//                    }
//                    if (connection != null) {
//                        try {
//                            connection.close();
//                        } catch (SQLException e1) {
//                            e1.printStackTrace();
//                        }
//                    }
//                }
//                new MineDialog("刷新成功！");
//            }
//        });

        searchRefreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //查询界面，刷新三张表。
//                int temp1;
//                System.out.println("searchUserTable " + searchUserTable.getRowCount());
//                for (temp1 = 0; temp1 < searchUserTable.getRowCount(); temp1++) {
//                    searchModelUser.removeRow(temp1);
//                }
//                int temp2;
//                System.out.println("searchManageTable " + searchManageTable.getRowCount());
//                for (temp2 = 0; temp2 < searchManageTable.getRowCount(); temp2++) {
//                    searchModelManage.removeRow(temp2);
//                }
//                int temp3;
//                System.out.println("searchWeatherTable " + searchWeatherTable.getRowCount());
//                for (temp3 = 0; temp3 < searchWeatherTable.getRowCount(); temp3++) {
//                    System.out.println(temp3);
//                    searchModelWeather.removeRow(temp3);
//                }
                searchModelUser.setRowCount(0);
                searchModelManage.setRowCount(0);
                searchModelWeather.setRowCount(0);
                ResultSet resultSet = null;
                Connection connection = null;
                Statement statement = null;
                try {
                    connection = DriverManager.getConnection(DBURL, userNameSQL, userPwd);

                    String searchUserData = "SELECT * FROM ordinaryUser";
                    String searchManageFirst = "SELECT * FROM management";
                    String searchWeatherFirst = "SELECT * FROM weather";

                    searchAddFirstUserAndManage(searchUserData, searchModelUser);
                    searchAddFirstUserAndManage(searchManageFirst, searchModelManage);
                    statement = connection.createStatement();
                    resultSet = statement.executeQuery(searchWeatherFirst);
                    addWeather(resultSet, searchModelWeather);
                    connection.close();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                    new MineDialog("数据库查询出错");
                } finally {
                    if (resultSet != null) {
                        try {
                            resultSet.close();
                        } catch (SQLException e1) {
                            e1.printStackTrace();
                        }
                    }
                    if (statement != null) {
                        try {
                            statement.close();
                        } catch (SQLException e2) {
                            e2.printStackTrace();
                        }
                    }
                    if (connection != null) {
                        try {
                            connection.close();
                        } catch (SQLException e1) {
                            e1.printStackTrace();
                        }
                    }
                }
                new MineDialog("刷新成功！");
            }
        });
    }

    private String getAlterString(int alterRow, int alterColumn) {
        String temp = "";
        System.out.println(alterRow + " " + alterColumn);
        System.out.println(updateWeatherTable.getRowCount());
        String change = "\'" + updateWeatherTable.getValueAt(alterRow, alterColumn) + "\'";
        String keyDate = "\'" + updateWeatherTable.getValueAt(alterRow, 0) + "\'";
        String keyCity = "\'" + updateWeatherTable.getValueAt(alterRow, 1) + "\'";
//        System.out.println("keyDate:" + keyDate);
//        System.out.println("keyCity:" + keyCity);
//        System.out.println("change:" + change);
        switch (alterColumn) {
            case 2:
                temp = "UPDATE weather " +
                        "SET shidu=" + change + " " +
                        "WHERE weather.weather_date=" + keyDate + " AND weather.city=" + keyCity;
                break;
            case 3:
                temp = "UPDATE weather " +
                        "SET quality=" + change + " " +
                        "WHERE weather.weather_date=" + keyDate + " AND weather.city=" + keyCity;
                break;
            case 4:
                temp = "UPDATE weather " +
                        "SET wendu=" + change + " " +
                        "WHERE weather.weather_date=" + keyDate + " AND weather.city=" + keyCity;
                break;
            case 5:
                temp = "UPDATE weather " +
                        "SET sunrise=" + change + " " +
                        "WHERE weather.weather_date=" + keyDate + " AND weather.city=" + keyCity;
                break;
            case 6:
                temp = "UPDATE weather " +
                        "SET sunset=" + change + " " +
                        "WHERE weather.weather_date=" + keyDate + " AND weather.city=" + keyCity;
                break;
            case 7:
                temp = "UPDATE weather " +
                        "SET weather_type=" + change + " " +
                        "WHERE weather.weather_date=" + keyDate + " AND weather.city=" + keyCity;
                break;
            case 8:
                temp = "UPDATE weather " +
                        "SET weather_week=" + change + " " +
                        "WHERE weather.weather_date=" + keyDate + " AND weather.city=" + keyCity;
                break;
            case 9:
                temp = "UPDATE weather " +
                        "SET high=" + change + " " +
                        "WHERE weather.weather_date=" + keyDate + " AND weather.city=" + keyCity;
                break;
            case 10:
                temp = "UPDATE weather " +
                        "SET low=" + change + " " +
                        "WHERE weather.weather_date=" + keyDate + " AND weather.city=" + keyCity;
                break;
            default:
                break;
        }
        System.out.println("执行修改：" + temp);
        return temp;
    }
}