package bean;

public class Weather {
    private String weatherDate;
    private String weatherCity;
    private String shidu;
    private String pm25;
    private String quality;
    private String wendu;

    public String getWeatherDate() {
        return "\'"+weatherDate+"\'";
    }

    public void setWeatherDate(String weatherDate) {
        this.weatherDate = weatherDate;
    }

    public String getWeatherCity() {
        return "\'"+weatherCity+"\'";
    }

    public void setWeatherCity(String weatherCity) {
        this.weatherCity = weatherCity;
    }

    public String getShidu() {
        return "\'"+shidu+"\'";
    }

    public void setShidu(String shidu) {
        this.shidu = shidu;
    }

    public String getPm25() {
        return "\'"+pm25+"\'";
    }

    public void setPm25(String pm25) {
        this.pm25 = pm25;
    }

    public String getQuality() {
        return "\'"+quality+"\'";
    }

    public void setQuality(String quality) {
        this.quality = quality;
    }

    public String getWendu() {
        return "\'"+wendu+"\'";
    }

    public void setWendu(String wendu) {
        this.wendu = wendu;
    }
}
