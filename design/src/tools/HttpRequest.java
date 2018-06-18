package tools;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;

public class HttpRequest {
    /**
     * 发送GET请求
     *
     * @param url 请求的网站
     * @return 服务器返回的字符串
     */
    public static String sendGet(String url) {
        String result = "";
        BufferedReader bufferedReader = null;
        try {
            url = url.replace(" ", "%20");
            URL urlResult = new URL(url);
            URLConnection connection = urlResult.openConnection();
//            防范415错误
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            connection.setRequestProperty("User-Agent",
                    "Mozilla/5.0 (Windows NT 6.3; WOW64; rv:27.0) Gecko/20100101 Firefox/27.0");
            connection.connect();
//            Map<String, List<String>> map = connection.getHeaderFields();
//            for (String key :
//                    map.keySet()) {
//                System.out.println(key + "->" + map.get(key));
//            }
            bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                result = result + line;
            }
        } catch (Exception e) {
            System.out.println("发送Get请求出现异常" + e);
        } finally {
            try {
                if (bufferedReader != null) {
                    bufferedReader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    /**
     * 发送post请求
     * @param url 网站
     * @param param 请求参数
     * @return 返回字符串
     */
    public static String sendPost(String url, String param) {
        PrintWriter printWriter = null;
        BufferedReader bufferedReader = null;
        String result = "";
        try {
            url = url.replace(" ", "%20");
            URL urlResult = new URL(url);
            URLConnection connection = urlResult.openConnection();
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            connection.setRequestProperty("User-Agent",
                    "Mozilla/5.0 (Windows NT 6.3; WOW64; rv:27.0) Gecko/20100101 Firefox/27.0");
            connection.setDoInput(true);
            connection.setDoOutput(true);
            printWriter = new PrintWriter(connection.getOutputStream());
            printWriter.print(param);
            printWriter.flush();
            bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                result = result + line;
            }
        } catch (Exception e) {
            System.out.println("发送post请求出现错误" + e);
        } finally {
            try {
                if (printWriter != null) {
                    printWriter.close();
                }
                if (bufferedReader != null) {
                    bufferedReader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }
}
