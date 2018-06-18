import bean.Weather;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import tools.HttpRequest;

public class MainTest {
    private static final String WEATHER_JSON_URL = "https://www.sojson.com/open/api/weather/json.shtml?city=";
    public static final String WEATHER_XML_URL = "https://www.sojson.com/open/api/weather/xml.shtml?city=";

    public static void main(String[] args) {
        Weather weather = new Weather();
        String city = "北京";
        String result = HttpRequest.sendGet(WEATHER_JSON_URL + city);
        parse(result, weather);
    }

    private static void parse(String result, Weather weather) {
        JsonParser parser = new JsonParser();
        try {
            /**
             * 获得今天的天气信息
             */
            JsonObject object = (JsonObject) parser.parse(result);
            System.out.println("date:" + object.get("date").getAsString());
            /**
             * 获得预期的天气信息
             */
            JsonObject data=object.get("data").getAsJsonObject();
            JsonArray forecast=data.get("forecast").getAsJsonArray();
            for (int i = 0; i < forecast.size(); i++) {
                JsonObject subObject = forecast.get(i).getAsJsonObject();
                System.out.println("date:"+subObject.get("date").getAsString());
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}

