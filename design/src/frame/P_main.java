package frame;

import bean.Weather;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class P_main extends JFrame{
    private static JFrame jf;
    private static JLabel jl;
    private static JButton bt_open;
    private static JButton bt_close;
    private static JButton bt_sysInfo;
    private static JButton bt_back;

    public P_main(){
        jf=new JFrame("i am the new JFrame");
        jf.setVisible(true);
        jf.setLocation(300, 400);
        jf.setSize(300,300);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {

    }
}