package frame;

import bean.Forecast;
import bean.Weather;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import tools.HttpRequest;
import tools.MineDialog;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import static bean.City.getCityJson;
import static bean.City.getProvinceFromJson;

public class Main extends JFrame {

    public static final String CITY = "太原";
    private static final String WEATHER_JSON_URL = "https://www.sojson.com/open/api/weather/json.shtml?city=";
    private Weather weather = new Weather();
    private List<Forecast> forecasts = new ArrayList<>();


    private static int year = 0;
    private static int month = 0;
    private static int day = 0;
    private static int hour = 0;
    private static int minute = 0;
    private static int second = 0;
    private static long datatime;

    private static JFrame mainFrame;
    private static JLabel mainLabel;

    private static JLabel date;
    private static JLabel bigIcon;
    private static JLabel wendu;
    private static JLabel sheshidu;
    private static JLabel weatherType;
    private static JLabel highAndLow;
    private static JLabel feng;
    private static JLabel aqi;

    private static JLabel week1;
    private static JLabel bigIcon1;
    private static JLabel highAndLow1;
    private static JLabel weatherType1;
    private static JLabel feng1;
    private static JLabel aqi1;

    private static JLabel week2;
    private static JLabel bigIcon2;
    private static JLabel highAndLow2;
    private static JLabel weatherType2;
    private static JLabel feng2;
    private static JLabel aqi2;

    private static JLabel week3;
    private static JLabel bigIcon3;
    private static JLabel highAndLow3;
    private static JLabel weatherType3;
    private static JLabel feng3;
    private static JLabel aqi3;

    private static JLabel week4;
    private static JLabel bigIcon4;
    private static JLabel highAndLow4;
    private static JLabel weatherType4;
    private static JLabel feng4;
    private static JLabel aqi4;

    private static JLabel helloText;

    private static JComboBox<String> provinceChoose;
    private static JComboBox<String> cityChoose;


    public Main() {
        mainFrame = new JFrame("城市天气统计管理系统");
        mainLabel = new JLabel();

        parse(CITY, weather, forecasts);//解析json

        ImageIcon image = new ImageIcon(Main.class.getResource("/frame/icon/main.png"));
        image.setImage(image.getImage().getScaledInstance(image.getIconWidth(), image.getIconHeight(), Image.SCALE_DEFAULT));
        mainLabel.setIcon(image);
        mainFrame.setSize(image.getIconWidth(), image.getIconHeight());

        getBigLabel(weather, forecasts);
        getLabel1(forecasts);
        getLabel2(forecasts);
        getLabel3(forecasts);
        getLabel4(forecasts);

        List<String> prov = getProvinceFromJson();
        String[] provs = new String[prov.size()];
        prov.toArray(provs);
        provinceChoose = new JComboBox<>(provs);
        provinceChoose.setSelectedIndex(30);
        provinceChoose.setBounds(100, 450, 100, 30);

        /**
         * 由IP地址获得真正的地理位置
         */
        List<String> city = getCityJson("山西");
        String[] provs1 = new String[city.size()];
        city.toArray(provs1);
        cityChoose = new JComboBox<>(provs1);
        cityChoose.setSelectedIndex(2);
        cityChoose.setBounds(250, 450, 100, 30);

        initHello();
        initClick();
        addLabel();

        mainLabel.add(provinceChoose);
        mainLabel.add(cityChoose);
        mainLabel.add(helloText);

        mainFrame.add(mainLabel);
        mainFrame.setVisible(true);
        mainFrame.setLocation(300, 200);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void initHello() {
        helloText = new JLabel("hello，" + Landing.userName.getText() + "。");
        helloText.setBounds(850, 550, 150, 30);
    }

    public static void main(String[] args) {
        //默认太原市
        new Main();

    }


    private static void initClick() {
        provinceChoose.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                List<String> city = getCityJson(Objects.requireNonNull(Main.provinceChoose.getSelectedItem()).toString());
                String[] provs1 = new String[city.size()];
                city.toArray(provs1);
                ComboBoxModel aModel1 = new DefaultComboBoxModel(provs1);
                cityChoose.setModel(aModel1);
            }
        });
        cityChoose.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String newCity = Objects.requireNonNull(Main.cityChoose.getSelectedItem()).toString();
                Weather newWeather = new Weather();
                List<Forecast> newForecasts = new ArrayList<>();
                parse(newCity, newWeather, newForecasts);
                setBig(newWeather, newForecasts);
                setJLabel1(newForecasts);
                setJLabel2(newForecasts);
                setJLabel3(newForecasts);
                setJLabel4(newForecasts);
            }
        });
        /**
         * 鼠标移入，移除
         */
        bigIcon.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
            }
        });
    }

    /**
     * 为今天的天气做准备
     *
     * @param weather
     * @param forecasts
     */
    private void getBigLabel(Weather weather, List<Forecast> forecasts) {
        date = new JLabel(changeDate(weather.getWeatherDate()));//更改
        date.setFont(new Font("Dialog", Font.BOLD, 15));
        date.setForeground(Color.WHITE);
        date.setBounds(115, 30, 150, 30);

        bigIcon = new JLabel();
        setBigIconSwitch(forecasts);

        wendu = new JLabel(changeWendu(weather.getWendu()));//更改
        wendu.setFont(new Font("Dialog", Font.PLAIN, 90));
        wendu.setForeground(Color.WHITE);
        wendu.setBounds(100, 200, 100, 100);

        sheshidu = new JLabel("℃");
        sheshidu.setFont(new Font("Dialog", Font.PLAIN, 20));
        sheshidu.setForeground(Color.WHITE);
        sheshidu.setBounds(205, 210, 50, 50);

        weatherType = new JLabel(changeWeatherType(forecasts.get(0).getType()));//更改
        weatherType.setFont(new Font("Dialog", Font.PLAIN, 20));
        weatherType.setForeground(Color.WHITE);
        weatherType.setBounds(205, 220, 100, 100);

        highAndLow = new JLabel(changeLow(forecasts.get(0).getLow()) + " - " + changeHign(forecasts.get(0).getHigh()));//更改
        highAndLow.setFont(new Font("Dialog", Font.PLAIN, 20));
        highAndLow.setForeground(Color.WHITE);
        highAndLow.setBounds(110, 310, 200, 30);

        feng = new JLabel(changeFengXiang(forecasts.get(0).getFengxiang()) + " " + changeFengLi(forecasts.get(0).getFenglevel()));//更改
        feng.setFont(new Font("Dialog", Font.PLAIN, 20));
        feng.setForeground(Color.WHITE);
        feng.setBounds(115, 345, 200, 30);

        aqi = new JLabel(changeAqi(forecasts.get(0).getAqi()));//更改
        aqi.setFont(new Font("Dialog", Font.PLAIN, 20));
        aqi.setForeground(Color.WHITE);
        aqi.setBounds(125, 380, 200, 30);
    }

    private static void setBig(Weather weather, List<Forecast> forecasts) {
        date.setText(changeDate(weather.getWeatherDate()));
        setBigIconSwitch(forecasts);
        wendu.setText(changeWendu(weather.getWendu()));
        weatherType.setText(changeWeatherType(forecasts.get(0).getType()));
        highAndLow.setText(changeLow(forecasts.get(0).getLow()) + " - " + changeHign(forecasts.get(0).getHigh()));
        feng.setText(changeFengXiang(forecasts.get(0).getFengxiang()) + " " + changeFengLi(forecasts.get(0).getFenglevel()));
        aqi.setText(changeAqi(forecasts.get(0).getAqi()));
    }

    private static void setBigIconSwitch(List<Forecast> forecasts) {
        switch (forecasts.get(0).getType()) {
            case "'多云'":
            case "'阴'":
                ImageIcon image1 = new ImageIcon(Main.class.getResource("/frame/icon/cloudyBig.png"));
                image1.setImage(image1.getImage().getScaledInstance(image1.getIconWidth(), image1.getIconHeight(), Image.SCALE_DEFAULT));
                bigIcon.setBounds(105, 60, 150, 150);
                bigIcon.setIcon(image1);
                break;
            case "'晴'":
                ImageIcon image2 = new ImageIcon(Main.class.getResource("/frame/icon/sunnyBig.png"));
                image2.setImage(image2.getImage().getScaledInstance(image2.getIconWidth(), image2.getIconHeight(), Image.SCALE_DEFAULT));
                bigIcon.setBounds(105, 60, 150, 150);
                bigIcon.setIcon(image2);
                break;
            case "'雷阵雨'":
            case "'小雨'":
                System.out.println("小雨");
                ImageIcon image3 = new ImageIcon(Main.class.getResource("/frame/icon/rainBig.png"));
                image3.setImage(image3.getImage().getScaledInstance(image3.getIconWidth(), image3.getIconHeight(), Image.SCALE_DEFAULT));
                bigIcon.setBounds(105, 60, 150, 150);
                bigIcon.setIcon(image3);
                break;
            default:
                break;
        }
    }

    /**
     * 为明天天气做准备
     *
     * @param forecasts
     */
    private static void setJLabel1(List<Forecast> forecasts) {
        week1.setText(changeFengLi(forecasts.get(1).getWeek()));
        setLabelswitch1(forecasts);
        highAndLow1.setText(changeLow(forecasts.get(1).getLow()) + " - " + changeHign(forecasts.get(1).getHigh()));
        weatherType1.setText(changeWeatherType(forecasts.get(1).getType()));
        feng1.setText(changeFengXiang(forecasts.get(1).getFengxiang()) + " " + changeFengLi(forecasts.get(1).getFenglevel()));
        aqi1.setText(changeAqi(forecasts.get(1).getAqi()));
    }

    private void getLabel1(List<Forecast> forecasts) {
        week1 = new JLabel(changeFengLi(forecasts.get(1).getWeek()));//更改
        week1.setFont(new Font("Dialog", Font.BOLD, 15));
        week1.setForeground(Color.WHITE);
        week1.setBounds(390, 80, 150, 30);

        bigIcon1 = new JLabel();
        System.out.println(forecasts.get(1).getType());
        setLabelswitch1(forecasts);

        highAndLow1 = new JLabel(changeLow(forecasts.get(1).getLow()) + " - " + changeHign(forecasts.get(1).getHigh()));
        highAndLow1.setFont(new Font("Dialog", Font.PLAIN, 20));
        highAndLow1.setForeground(Color.WHITE);
        highAndLow1.setBounds(370, 230, 200, 30);

        weatherType1 = new JLabel(changeWeatherType(forecasts.get(1).getType()));
        weatherType1.setFont(new Font("Dialog", Font.PLAIN, 20));
        weatherType1.setForeground(Color.WHITE);
        weatherType1.setBounds(410, 230, 100, 100);

        feng1 = new JLabel(changeFengXiang(forecasts.get(1).getFengxiang()) + " " + changeFengLi(forecasts.get(1).getFenglevel()));
        feng1.setFont(new Font("Dialog", Font.PLAIN, 20));
        feng1.setForeground(Color.WHITE);
        feng1.setBounds(380, 300, 200, 30);

        aqi1 = new JLabel(changeAqi(forecasts.get(1).getAqi()));
        aqi1.setFont(new Font("Dialog", Font.PLAIN, 20));
        aqi1.setForeground(Color.WHITE);
        aqi1.setBounds(405, 340, 200, 30);
    }

    private static void setLabelswitch1(List<Forecast> forecasts) {
        switch (forecasts.get(1).getType()) {
            case "'多云'":
            case "'阴'":
                ImageIcon image = new ImageIcon(Main.class.getResource("/frame/icon/cloudy.png"));
                image.setImage(image.getImage().getScaledInstance(image.getIconWidth(), image.getIconHeight(), Image.SCALE_DEFAULT));
                bigIcon1.setBounds(398, 115, 100, 100);
                bigIcon1.setIcon(image);
                break;
            case "'晴'":
                ImageIcon image2 = new ImageIcon(Main.class.getResource("/frame/icon/sunny.png"));
                image2.setImage(image2.getImage().getScaledInstance(image2.getIconWidth(), image2.getIconHeight(), Image.SCALE_DEFAULT));
                bigIcon1.setBounds(398, 115, 100, 100);
                bigIcon1.setIcon(image2);
                break;
            case "'雷阵雨'":
            case "'小雨'":
                ImageIcon image3 = new ImageIcon(Main.class.getResource("/frame/icon/rain.png"));
                image3.setImage(image3.getImage().getScaledInstance(image3.getIconWidth(), image3.getIconHeight(), Image.SCALE_DEFAULT));
                bigIcon1.setBounds(398, 115, 100, 100);
                bigIcon1.setIcon(image3);
                break;
            default:
                break;
        }
    }


    /**
     * 为后天天气做准备
     *
     * @param forecasts
     */
    private void getLabel2(List<Forecast> forecasts) {
        week2 = new JLabel(changeFengLi(forecasts.get(2).getWeek()));//更改
        week2.setFont(new Font("Dialog", Font.BOLD, 15));
        week2.setForeground(Color.WHITE);
        week2.setBounds(560, 80, 150, 30);

        bigIcon2 = new JLabel();
        System.out.println(forecasts.get(2).getType());
        setLabelSwitch2(forecasts);

        highAndLow2 = new JLabel(changeLow(forecasts.get(2).getLow()) + " - " + changeHign(forecasts.get(2).getHigh()));
        highAndLow2.setFont(new Font("Dialog", Font.PLAIN, 20));
        highAndLow2.setForeground(Color.WHITE);
        highAndLow2.setBounds(535, 230, 200, 30);

        weatherType2 = new JLabel(changeWeatherType(forecasts.get(2).getType()));
        weatherType2.setFont(new Font("Dialog", Font.PLAIN, 20));
        weatherType2.setForeground(Color.WHITE);
        weatherType2.setBounds(575, 230, 100, 100);

        feng2 = new JLabel(changeFengXiang(forecasts.get(2).getFengxiang()) + " " + changeFengLi(forecasts.get(2).getFenglevel()));
        feng2.setFont(new Font("Dialog", Font.PLAIN, 20));
        feng2.setForeground(Color.WHITE);
        feng2.setBounds(540, 300, 200, 30);

        aqi2 = new JLabel(changeAqi(forecasts.get(2).getAqi()));
        aqi2.setFont(new Font("Dialog", Font.PLAIN, 20));
        aqi2.setForeground(Color.WHITE);
        aqi2.setBounds(570, 340, 200, 30);
    }

    private static void setJLabel2(List<Forecast> forecasts) {
        week2.setText(changeFengLi(forecasts.get(2).getWeek()));
        setLabelSwitch2(forecasts);
        highAndLow2.setText(changeLow(forecasts.get(2).getLow()) + " - " + changeHign(forecasts.get(2).getHigh()));
        weatherType2.setText(changeWeatherType(forecasts.get(2).getType()));
        feng2.setText(changeFengXiang(forecasts.get(2).getFengxiang()) + " " + changeFengLi(forecasts.get(2).getFenglevel()));
        aqi2.setText(changeAqi(forecasts.get(2).getAqi()));
    }

    private static void setLabelSwitch2(List<Forecast> forecasts) {
        switch (forecasts.get(2).getType()) {
            case "'多云'":
            case "'阴'":
                ImageIcon image = new ImageIcon(Main.class.getResource("/frame/icon/cloudy.png"));
                image.setImage(image.getImage().getScaledInstance(image.getIconWidth(), image.getIconHeight(), Image.SCALE_DEFAULT));
                bigIcon2.setBounds(565, 115, 100, 100);
                bigIcon2.setIcon(image);
                break;
            case "'晴'":
                ImageIcon image2 = new ImageIcon(Main.class.getResource("/frame/icon/sunny.png"));
                image2.setImage(image2.getImage().getScaledInstance(image2.getIconWidth(), image2.getIconHeight(), Image.SCALE_DEFAULT));
                bigIcon2.setBounds(565, 115, 100, 100);
                bigIcon2.setIcon(image2);
                break;
            case "'雷阵雨'":
            case "'小雨'":
                ImageIcon image3 = new ImageIcon(Main.class.getResource("/frame/icon/rain.png"));
                image3.setImage(image3.getImage().getScaledInstance(image3.getIconWidth(), image3.getIconHeight(), Image.SCALE_DEFAULT));
                bigIcon2.setBounds(565, 115, 100, 100);
                bigIcon2.setIcon(image3);
                break;
            default:
                break;
        }
    }

    /**
     * 为大后天天气做准备
     *
     * @param forecasts
     */
    private static void setLabelSwitch3(List<Forecast> forecasts) {
        switch (forecasts.get(3).getType()) {
            case "'多云'":
            case "'阴'":
                ImageIcon image = new ImageIcon(Main.class.getResource("/frame/icon/cloudy.png"));
                image.setImage(image.getImage().getScaledInstance(image.getIconWidth(), image.getIconHeight(), Image.SCALE_DEFAULT));
                bigIcon3.setBounds(725, 115, 100, 100);
                bigIcon3.setIcon(image);
                break;
            case "'晴'":
                ImageIcon image2 = new ImageIcon(Main.class.getResource("/frame/icon/sunny.png"));
                image2.setImage(image2.getImage().getScaledInstance(image2.getIconWidth(), image2.getIconHeight(), Image.SCALE_DEFAULT));
                bigIcon3.setBounds(725, 115, 100, 100);
                bigIcon3.setIcon(image2);
                break;
            case "'雷阵雨'":
            case "'小雨'":
                ImageIcon image3 = new ImageIcon(Main.class.getResource("/frame/icon/rain.png"));
                image3.setImage(image3.getImage().getScaledInstance(image3.getIconWidth(), image3.getIconHeight(), Image.SCALE_DEFAULT));
                bigIcon3.setBounds(725, 115, 100, 100);
                bigIcon3.setIcon(image3);
                break;
            default:
                break;
        }
    }

    private void getLabel3(List<Forecast> forecasts) {
        week3 = new JLabel(changeFengLi(forecasts.get(3).getWeek()));//更改
        week3.setFont(new Font("Dialog", Font.BOLD, 15));
        week3.setForeground(Color.WHITE);
        week3.setBounds(720, 80, 150, 30);

        bigIcon3 = new JLabel();
        setLabelSwitch3(forecasts);

        highAndLow3 = new JLabel(changeLow(forecasts.get(3).getLow()) + " - " + changeHign(forecasts.get(3).getHigh()));
        highAndLow3.setFont(new Font("Dialog", Font.PLAIN, 20));
        highAndLow3.setForeground(Color.WHITE);
        highAndLow3.setBounds(700, 230, 200, 30);

        weatherType3 = new JLabel(changeWeatherType(forecasts.get(3).getType()));
        weatherType3.setFont(new Font("Dialog", Font.PLAIN, 20));
        weatherType3.setForeground(Color.WHITE);
        weatherType3.setBounds(740, 230, 100, 100);

        feng3 = new JLabel(changeFengXiang(forecasts.get(3).getFengxiang()) + " " + changeFengLi(forecasts.get(3).getFenglevel()));
        feng3.setFont(new Font("Dialog", Font.PLAIN, 20));
        feng3.setForeground(Color.WHITE);
        feng3.setBounds(705, 300, 200, 30);

        aqi3 = new JLabel(changeAqi(forecasts.get(3).getAqi()));
        aqi3.setFont(new Font("Dialog", Font.PLAIN, 20));
        aqi3.setForeground(Color.WHITE);
        aqi3.setBounds(735, 340, 200, 30);
    }

    private static void setJLabel3(List<Forecast> forecasts) {
        week3.setText(changeFengLi(forecasts.get(3).getWeek()));
        setLabelSwitch3(forecasts);
        highAndLow3.setText(changeLow(forecasts.get(3).getLow()) + " - " + changeHign(forecasts.get(3).getHigh()));
        weatherType3.setText(changeWeatherType(forecasts.get(3).getType()));
        feng3.setText(changeFengXiang(forecasts.get(3).getFengxiang()) + " " + changeFengLi(forecasts.get(3).getFenglevel()));
        aqi3.setText(changeAqi(forecasts.get(3).getAqi()));
    }

    /**
     * 为大大后天天气做准备
     *
     * @param forecasts
     */

    private static void setJLabel4(List<Forecast> forecasts) {
        week4.setText(changeFengLi(forecasts.get(4).getWeek()));
        setLabelSwitch4(forecasts);
        highAndLow4.setText(changeLow(forecasts.get(4).getLow()) + " - " + changeHign(forecasts.get(4).getHigh()));
        weatherType4.setText(changeWeatherType(forecasts.get(4).getType()));
        feng4.setText(changeFengXiang(forecasts.get(4).getFengxiang()) + " " + changeFengLi(forecasts.get(4).getFenglevel()));
        aqi4.setText(changeAqi(forecasts.get(4).getAqi()));
    }

    private void getLabel4(List<Forecast> forecasts) {
        week4 = new JLabel(changeFengLi(forecasts.get(4).getWeek()));//更改
        week4.setFont(new Font("Dialog", Font.BOLD, 15));
        week4.setForeground(Color.WHITE);
        week4.setBounds(875, 80, 150, 30);

        bigIcon4 = new JLabel();
        setLabelSwitch4(forecasts);

        highAndLow4 = new JLabel(changeLow(forecasts.get(4).getLow()) + " - " + changeHign(forecasts.get(4).getHigh()));
        highAndLow4.setFont(new Font("Dialog", Font.PLAIN, 20));
        highAndLow4.setForeground(Color.WHITE);
        highAndLow4.setBounds(855, 230, 200, 30);

        weatherType4 = new JLabel(changeWeatherType(forecasts.get(4).getType()));
        weatherType4.setFont(new Font("Dialog", Font.PLAIN, 20));
        weatherType4.setForeground(Color.WHITE);
        weatherType4.setBounds(890, 230, 100, 100);

        feng4 = new JLabel(changeFengXiang(forecasts.get(4).getFengxiang()) + " " + changeFengLi(forecasts.get(4).getFenglevel()));
        feng4.setFont(new Font("Dialog", Font.PLAIN, 20));
        feng4.setForeground(Color.WHITE);
        feng4.setBounds(860, 300, 200, 30);

        aqi4 = new JLabel(changeAqi(forecasts.get(4).getAqi()));
        aqi4.setFont(new Font("Dialog", Font.PLAIN, 20));
        aqi4.setForeground(Color.WHITE);
        aqi4.setBounds(885, 340, 200, 30);
    }

    private static void setLabelSwitch4(List<Forecast> forecasts) {
        switch (forecasts.get(4).getType()) {
            case "'多云'":
            case "'阴'":
                ImageIcon image = new ImageIcon(Main.class.getResource("/frame/icon/cloudy.png"));
                image.setImage(image.getImage().getScaledInstance(image.getIconWidth(), image.getIconHeight(), Image.SCALE_DEFAULT));
                bigIcon4.setBounds(880, 115, 100, 100);
                bigIcon4.setIcon(image);
                break;
            case "'晴'":
                ImageIcon image2 = new ImageIcon(Main.class.getResource("/frame/icon/sunny.png"));
                image2.setImage(image2.getImage().getScaledInstance(image2.getIconWidth(), image2.getIconHeight(), Image.SCALE_DEFAULT));
                bigIcon4.setBounds(880, 115, 100, 100);
                bigIcon4.setIcon(image2);
                break;

            case "'雷阵雨'":
            case "'小雨'":
                ImageIcon image3 = new ImageIcon(Main.class.getResource("/frame/icon/rain.png"));
                image3.setImage(image3.getImage().getScaledInstance(image3.getIconWidth(), image3.getIconHeight(), Image.SCALE_DEFAULT));
                bigIcon4.setBounds(880, 115, 100, 100);
                bigIcon4.setIcon(image3);
                break;
            default:
                break;
        }
    }


    private static void addLabel() {
        mainLabel.add(date);
        mainLabel.add(bigIcon);
        mainLabel.add(wendu);
        mainLabel.add(sheshidu);
        mainLabel.add(weatherType);
        mainLabel.add(highAndLow);
        mainLabel.add(feng);
        mainLabel.add(aqi);

        mainLabel.add(week1);
        mainLabel.add(bigIcon1);
        mainLabel.add(highAndLow1);
        mainLabel.add(weatherType1);
        mainLabel.add(feng1);
        mainLabel.add(aqi1);

        mainLabel.add(week2);
        mainLabel.add(bigIcon2);
        mainLabel.add(highAndLow2);
        mainLabel.add(weatherType2);
        mainLabel.add(feng2);
        mainLabel.add(aqi2);

        mainLabel.add(week3);
        mainLabel.add(bigIcon3);
        mainLabel.add(highAndLow3);
        mainLabel.add(weatherType3);
        mainLabel.add(feng3);
        mainLabel.add(aqi3);

        mainLabel.add(week4);
        mainLabel.add(bigIcon4);
        mainLabel.add(highAndLow4);
        mainLabel.add(weatherType4);
        mainLabel.add(feng4);
        mainLabel.add(aqi4);
    }

    private static String changeAqi(String s) {
        s = s.substring(1, s.length() - 3);
        int a = Integer.valueOf(s);
        String result;
        if (a >= 0 && a <= 100) {
            result = a + "  良";
        } else {
            result = a + "  轻度";
        }
        return result;
    }

    private static String changeFengLi(String s) {
        s = s.substring(1, s.length() - 1);
        return s;
    }

    private static String changeFengXiang(String s) {
        s = s.substring(1, s.length() - 1);
        return s;
    }

    /**
     * @param s
     * @return
     */
    private static String changeLow(String s) {
        s = s.substring(4, s.length() - 2);
        return s;
    }

    /**
     * @param wendu
     * @return
     */
    private static String changeHign(String wendu) {
        wendu = wendu.substring(4, wendu.length() - 1);
        return wendu;
    }

    /**
     * @param type
     * @return
     */
    private static String changeWeatherType(String type) {
        type = type.substring(1, type.length() - 1);
        return type;
    }

    /**
     * 改变温度
     *
     * @param wendu 温度
     * @return 温度
     */
    private static String changeWendu(String wendu) {
        wendu = wendu.substring(1, wendu.length() - 1);
        return wendu;
    }

    /**
     * 添加时间
     *
     * @param date json获取到的时间
     * @return 正常的时间格式
     */
    private static String changeDate(String date) {
        String year = date.substring(1, 5);
        String mouth = date.substring(5, 7);
        String smallDate = date.substring(7, 9);
        return year + "年 " + mouth + "月 " + smallDate + "日";
    }

    /**
     * 解析json
     *
     * @param city      解析
     * @param weather   天气
     * @param forecasts 预测天气
     */
    public static void parse(String city, Weather weather, List<Forecast> forecasts) {

        LocalDateTime time = LocalDateTime.now();


        if (datatime != 0) {
            if ((((new Date().getTime())-(datatime))/1000)>3){
                System.out.println(((new Date().getTime())-(datatime))/1000+"秒");
            }else {
                new MineDialog("不能连续三秒内多次查询");
                return;
            }
        } else {
            new MineDialog("分钟数等于秒数，即初次调用");
        }
        datatime = new Date().getTime();


//        if (day != 0 && minute != 0 && second != 0) {
//            if (day == time.getDayOfYear()) {
//
//            } else if (day < time.getDayOfYear()) {
//                if (month > time.getMonthValue()) {
//
//                } else {
//                    if (day > time.getDayOfYear()) ;
//                }
//            }
//        } else {
//
//        }
        day = time.getDayOfYear();
        hour = time.getHour();
        minute = time.getMinute();
        second = time.getSecond();

        forecasts.clear();
        String result = HttpRequest.sendGet(WEATHER_JSON_URL + city);
        System.out.println("parse " + city);
        System.out.println(result);
        JsonParser parser = new JsonParser();
        try {
            JsonObject object = (JsonObject) parser.parse(result);
            weather.setWeatherDate(object.get("date").getAsString());//日期
            weather.setWeatherCity(object.get("city").getAsString());//城市
            /**
             * 获得预期的天气信息
             */
            JsonObject data = object.get("data").getAsJsonObject();
            weather.setShidu(data.get("shidu").getAsString());//湿度
            weather.setQuality(data.get("quality").getAsString());//quality
            weather.setWendu(data.get("wendu").getAsString());//温度
            JsonArray forecast = data.get("forecast").getAsJsonArray();
            for (int i = 0; i < forecast.size(); i++) {
                JsonObject subObject = forecast.get(i).getAsJsonObject();
                Forecast f = new Forecast();
                f.setWeek(subObject.get("date").getAsString());
                f.setSunrise(subObject.get("sunrise").getAsString());
                f.setSunset(subObject.get("sunset").getAsString());
                f.setHigh(subObject.get("high").getAsString());
                f.setLow(subObject.get("low").getAsString());
                f.setAqi(subObject.get("aqi").getAsString());
                f.setFengxiang(subObject.get("fx").getAsString());
                f.setFenglevel(subObject.get("fl").getAsString());
                f.setType(subObject.get("type").getAsString());
                f.setNotice(subObject.get("notice").getAsString());
                forecasts.add(f);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}