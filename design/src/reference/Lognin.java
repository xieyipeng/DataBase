package reference;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;

public class Lognin {
    JFrame frame = new JFrame("城市天气统计管理系统");
    JButton lognin = new JButton("登陆");
    String user[] = {"系统管理员", "气象部门工作人员", "普通用户", "统计人员"};
    JComboBox<String> userChoose = new JComboBox<>(user);
    JList<String> userList = new JList<>(user);
    JMenuBar menuBar = new JMenuBar();
    JMenu file = new JMenu("文件");
    JMenu edit = new JMenu("编辑");
    JMenu fromat = new JMenu("格式");
    JMenuItem newItem = new JMenuItem("新建");
    JMenuItem saveItem = new JMenuItem("保存");
    JMenuItem commentItem = new JMenuItem("注释");
    JPopupMenu pop = new JPopupMenu();
    JTextArea ta = new JTextArea(8, 20);
    ButtonGroup flavorGroup = new ButtonGroup();
    JMenuItem canceItem = new JMenuItem("取消注释");
    JRadioButtonMenuItem metalItem = new JRadioButtonMenuItem("Metal风格", true);
    JRadioButtonMenuItem windowsItem = new JRadioButtonMenuItem("Windows风格", true);

    public void init() {
        JPanel bottom = new JPanel();
        bottom.add(lognin);
        frame.add(bottom, BorderLayout.SOUTH);

        JPanel checkJPanel = new JPanel();
        checkJPanel.add(userChoose);

        Box topLeft = Box.createVerticalBox();
        JScrollPane taJspa = new JScrollPane(ta);

        topLeft.add(checkJPanel);
        topLeft.add(taJspa);

        Box top = Box.createHorizontalBox();
        top.add(topLeft);
        top.add(userList);
        frame.add(top);

        newItem.setAccelerator(KeyStroke.getKeyStroke('N', InputEvent.CTRL_MASK));
        newItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ta.append("用户单机了新建菜单\n");
            }
        });
        file.add(newItem);
        edit.add(saveItem);
        canceItem.setToolTipText("将程序代码都注释起来");
        fromat.add(commentItem);
        fromat.add(canceItem);
        edit.add(fromat);
        menuBar.add(file);
        menuBar.add(edit);

        frame.setJMenuBar(menuBar);
        flavorGroup.add(metalItem);
        flavorGroup.add(windowsItem);
        ActionListener flavorListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    switch (e.getActionCommand()) {
                        case "Metal风格":
                            changeFlavor(1);
                            break;
                        case "Windows风格":
                            changeFlavor(2);
                            break;
                        default:
                            break;
                    }
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        };
        metalItem.addActionListener(flavorListener);
        windowsItem.addActionListener(flavorListener);
        ta.setComponentPopupMenu(pop);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    private void changeFlavor(int i) throws Exception {
        switch (i) {
            case 1:
                UIManager.setLookAndFeel("Javax.swing.plaf.metal.MetalLookAndFeel");
                break;
            case 2:
                UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsClassicLookAndFell");
                break;
            default:
                break;
        }
        SwingUtilities.updateComponentTreeUI(frame.getContentPane());
        SwingUtilities.updateComponentTreeUI(menuBar);
        SwingUtilities.updateComponentTreeUI(pop);
    }

    public static void main(String[] args) {
        new Lognin().init();
    }
}
