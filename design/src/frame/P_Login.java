package frame;

import tools.MineDialog;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.Objects;

public class P_Login extends JFrame {

    private static final String DBURL = "jdbc:sqlserver://localhost:1433;databaseName=design";
    private static final String userNameSQL = "xieyipeng";
    private static final String userPwd = "123456";

    private static JFrame loginFrame;
    private static JTextField userName;
    private static JTextField passWord;
    private static JButton okLoginButton;
    private static JButton exitLoginButton;
    private static JComboBox<String> userChoose;
    public static final String user[] = {"系统管理员", "气象部门工作人员", "普通用户", "统计人员"};

    public P_Login() {
        loginFrame = new JFrame("注册");

        ImageIcon bgim = new ImageIcon(P_Landing.class.getResource("login.png"));
        bgim.setImage(bgim.getImage().getScaledInstance(bgim.getIconWidth(), bgim.getIconHeight(), Image.SCALE_DEFAULT));
        JLabel loginLabel = new JLabel();
        loginLabel.setIcon(bgim);
        loginFrame.setSize(bgim.getIconWidth(), bgim.getIconHeight());

        userChoose = new JComboBox<>(user);
        userChoose.setBounds(50, 200, 200, 30);

        JLabel userLoginText = new JLabel("用户名");
        userLoginText.setBounds(30, 50, 40, 50);
        JLabel paswordText = new JLabel("密码");
        paswordText.setBounds(30, 120, 40, 50);

        userName = new JTextField();
        userName.setBounds(90, 50, 135, 50);
        passWord = new JTextField();
        passWord.setBounds(90, 120, 135, 50);

        okLoginButton = new JButton("确定");
        okLoginButton.setBounds(30, 300, 82, 30);
        exitLoginButton = new JButton("取消");
        exitLoginButton.setBounds(142, 300, 82, 30);

        initClicks();


        loginLabel.add(userChoose);
        loginLabel.add(userLoginText);
        loginLabel.add(userName);
        loginLabel.add(paswordText);
        loginLabel.add(passWord);
        loginLabel.add(okLoginButton);
        loginLabel.add(exitLoginButton);

        loginFrame.add(loginLabel);
        loginFrame.setVisible(true);
        loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        loginFrame.setLocation(300, 400);
    }

    public static void main(String[] args) {

    }

    private static void initClicks() {
        okLoginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = userName.getText();
                String password = passWord.getText();
                if (name.length() >= 3 && password.length() >= 3) {
                    name = "\'" + userName.getText() + "\'";
                    password = "\'" + passWord.getText() + "\'";
                    Connection connection = null;
                    Statement statement = null;
                    ResultSet resultSet = null;
                    switch (Objects.requireNonNull(userChoose.getSelectedItem()).toString()) {
                        case "系统管理员":
                            try {
                                connection = DriverManager.getConnection(DBURL, userNameSQL, userPwd);
                                statement = connection.createStatement();
                                String select="SELECT *" +
                                        "FROM management " +
                                        "WHERE management.userName="+name;
                                resultSet = statement.executeQuery(select);
                                if (resultSet.next()){
                                    new MineDialog("该用户名已被注册过，请选用新的用户名");
                                    System.out.println("相同用户名");
                                }else {
                                    String SQL = "INSERT INTO management(userName,wordPass)" +
                                            "values("+name+","+password+")";
                                    statement.executeUpdate(SQL);
                                    System.out.println("注册信息插入成功");
                                    new MineDialog("注册成功，账号信息已添加到数据库。");
                                    loginFrame.dispose();
                                }
                                connection.close();
                            } catch (SQLException e1) {
                                e1.printStackTrace();
                            }
                            finally {
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
                            break;
                        case "普通用户":
                            break;
                        default:
                            break;
                    }
                } else {
                    new MineDialog("用户名或密码格式不正确，请重新输入");
                }
            }
        });

        exitLoginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loginFrame.dispose();
            }
        });
    }
}
