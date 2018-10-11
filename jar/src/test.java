import java.io.File;
import java.util.Arrays;

/**
 * Created with IntelliJ IDEA.
 * TODO:
 * User: xieyipeng
 * Date: 2018-08-20
 * Time: 10:58
 */
public class test {
    public static final String resoult = "[(1163, 287, 0), (64, 571, 9), (669, 432, 2), (183, 652, 11), (599, 611, 12), (870, 505, 4), (907, 661, 13), (125, 361, 7), (1443, 487, 8)]";

    public static void main(String[] args) {
//        Connection connection=myConnection.connect("xieyipeng","123456","design");
//        if (connection!=null){
//            System.out.println("123");
//        }else {
//            System.out.println("234");
//        }
        getBallList(resoult);
    }

    //TODO: 获得到服务器传来的球的数据，进行的操作
    private static void getBallList(String resoult) {
        resoult = resoult.trim();
        String a = "[()]";
        String[] strings = resoult.split(a);
        System.out.println("大小： " + strings.length / 2);
        for (int i = 0; i < strings.length; i++) {
            if (i % 2 != 0) {
                String temp = strings[i];
                String[] newStrings = temp.split(",");
                for (int j = 0; j < newStrings.length; j++) {
                    newStrings[j] = newStrings[j].trim();
                    System.out.print(Integer.valueOf(newStrings[j]) + " ");
                }
                System.out.println();
            }
        }
    }
}
