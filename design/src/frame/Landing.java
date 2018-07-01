package frame;

import tools.MineDialog;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.Objects;

public class Landing extends JFrame {

    private static final String DBURL = "jdbc:sqlserver://localhost:1433;databaseName=design";
    private static final String userNameSQL = "xieyipeng";
    private static final String userPwd = "123456";

    private static int count = 0;
    private static JButton loadingButton;//登陆按钮
    private static JButton exitButton;//退出按钮
    private static JButton forgetButton;//忘记密码
    private static JButton loginButton;//注册
    private static JFrame loadingFrame;//登陆的框架
    public static JComboBox<String> userChooseLanding;
    public static JTextField userName;//用户名
    private static JPasswordField passWord;//密码
    public static final String user[] = {"系统管理员", "气象部门工作人员", "普通用户", "统计人员"};

    private Landing() {
        loadingFrame = new JFrame("Loading");
        JLabel loadingLabel = new JLabel();
        ImageIcon bgim = new ImageIcon(Landing.class.getResource("/frame/icon/Loading.png"));
        bgim.setImage(bgim.getImage().getScaledInstance(bgim.getIconWidth(), bgim.getIconHeight(), Image.SCALE_DEFAULT));

        loadingLabel.setIcon(bgim);
        loadingFrame.setSize(bgim.getIconWidth(), bgim.getIconHeight());
        //380，500
        userChooseLanding = new JComboBox<>(user);
        userChooseLanding.setBounds(115, 190, 270, 30);

        JLabel userText = new JLabel("用户名");
        userText.setBounds(80, 50, 60, 50);
        JLabel passwordText = new JLabel("密码");
        passwordText.setBounds(80, 120, 60, 50);
        userName = new JTextField();
        userName.setBounds(150, 50, 270, 50);
        passWord = new JPasswordField();
        passWord.setBounds(150, 120, 270, 50);

        forgetButton = new JButton("忘记密码");
        forgetButton.setBounds(50, 290, 100, 30);
        loginButton = new JButton("注册");
        loginButton.setBounds(350, 290, 100, 30);

        loadingButton = new JButton("登陆");
        loadingButton.setBounds(100, 240, 100, 40);
        exitButton = new JButton("退出");
        exitButton.setBounds(300, 240, 100, 40);

        initClick();

        loadingLabel.add(userName);
        loadingLabel.add(passWord);
        loadingLabel.add(userText);
        loadingLabel.add(passwordText);
        loadingLabel.add(loadingButton);
        loadingLabel.add(exitButton);
        loadingLabel.add(userChooseLanding);
        loadingLabel.add(forgetButton);
        loadingLabel.add(loginButton);
        loadingFrame.add(loadingLabel);
        loadingFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        loadingFrame.setLocation(300, 400);
        loadingFrame.setVisible(true);
    }

    public static void main(String[] args) {
        new Landing();
    }

    /**
     * 设置登陆和退出的点击事件
     */
    private static void initClick() {
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Login();
            }
        });

        loadingButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String admin = "\'" + userName.getText() + "\'";
                char[] password = passWord.getPassword();
                String str = "\'" + String.valueOf(password) + "\'";
                Connection connection = null;
                Statement statement = null;
                ResultSet resultSet = null;
                switch (Objects.requireNonNull(userChooseLanding.getSelectedItem()).toString()) {
                    case "系统管理员":
                        try {
                            connection = DriverManager.getConnection(DBURL, userNameSQL, userPwd);
                            statement = connection.createStatement();
                            String nextOrNot = "select *" +
                                    "FROM management " +
                                    "WHERE management.userName=" + admin;
                            resultSet = statement.executeQuery(nextOrNot);
                            if (ifSuccess(str, resultSet)) return;
                            connection.close();
                        } catch (SQLException e1) {
                            e1.printStackTrace();
                        } finally {
                            if (resultSet != null) {
                                try {
                                    resultSet.close();
                                } catch (SQLException e4) {
                                    e4.printStackTrace();
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
                                } catch (SQLException e3) {
                                    e3.printStackTrace();
                                }
                            }
                        }
                        break;
                    case "普通用户":
                        try {
                            connection = DriverManager.getConnection(DBURL, userNameSQL, userPwd);
                            statement = connection.createStatement();
                            String nextOrNot = "select *" +
                                    "FROM ordinaryUser " +
                                    "WHERE ordinaryUser.userName=" + admin;
                            resultSet = statement.executeQuery(nextOrNot);
                            if (resultSet.next()) {
                                String a = str.substring(1, str.length() - 1);
                                String same = resultSet.getString(2).replace(" ", "");
                                if (a.equals(same)) {
                                    new Main();
                                    loadingFrame.dispose();
                                } else {
                                    new MineDialog("密码错误，请重新输入密码");
                                }
                                return;
                            } else {
                                new MineDialog("用户名不存在，请检查用户名是否正确");
                            }
                            connection.close();
                        } catch (SQLException e1) {
                            e1.printStackTrace();
                        } finally {
                            if (resultSet != null) {
                                try {
                                    resultSet.close();
                                } catch (SQLException e4) {
                                    e4.printStackTrace();
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
                                } catch (SQLException e3) {
                                    e3.printStackTrace();
                                }
                            }
                        }
                        break;
                    default:
                        break;
                }

            }
        });

        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadingFrame.dispose();
            }
        });
    }

    private static boolean ifSuccess(String str, ResultSet resultSet) throws SQLException {
        if (resultSet.next()) {
            //检验密码正确与否，登陆进去
            String a = str.substring(1, str.length() - 1);
            String same = resultSet.getString(2).replace(" ", "");
            if (a.equals(same)) {
                new Manage();
                loadingFrame.dispose();
            } else {
                new MineDialog("密码错误，请重新输入密码");
            }
            return true;
        } else {
            new MineDialog("用户名不存在，请检查用户名是否正确");
        }
        return false;
    }
}
