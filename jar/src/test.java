import java.sql.Connection;

/**
 * Created with IntelliJ IDEA.
 * TODO:
 * User: xieyipeng
 * Date: 2018-08-20
 * Time: 10:58
 */
public class test {
    public static void main(String[] args) {
        Connection connection=myConnection.connect("xieyipeng","123456","design");
        if (connection!=null){
            System.out.println("123");
        }else {
            System.out.println("234");
        }
    }
}
