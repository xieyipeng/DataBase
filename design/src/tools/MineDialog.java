package tools;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MineDialog {
    private static JButton okMineDialog;
    private static JFrame mineDialogFrame;
    public MineDialog(String text){
        mineDialogFrame = new JFrame("MineDialog");
        mineDialogFrame.setSize(300,150);
        JTextField loginAgainText=new JTextField(text);
        loginAgainText.setBounds(20,30,245,30);

        okMineDialog =new JButton("确定");
        okMineDialog.setBounds(100,60,100,30);

        JLabel loginAgainLabel=new JLabel();
        loginAgainLabel.add(loginAgainText);
        loginAgainLabel.add(okMineDialog);

        initClicks();

        mineDialogFrame.add(loginAgainLabel);
        mineDialogFrame.setVisible(true);
        mineDialogFrame.setLocation(300, 400);
        mineDialogFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void initClicks() {
        okMineDialog.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mineDialogFrame.dispose();
            }
        });
    }

    public static void main(String[] args) {

    }
}
