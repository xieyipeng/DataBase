package bean;

public class Forecast {
    private String week;
    private String sunrise;
    private String sunset;
    private String high;
    private String low;
    private String aqi;
    private String fengxiang;
    private String fenglevel;
    private String type;
    private String notice;

    public String getWeek() {
        return "\'"+week+"\'";
    }

    public void setWeek(String week) {
        this.week = week;
    }

    public String getSunrise() {
        return "\'"+sunrise+"\'";
    }

    public void setSunrise(String sunrise) {
        this.sunrise = sunrise;
    }

    public String getSunset() {
        return "\'"+sunset+"\'";
    }

    public void setSunset(String sunset) {
        this.sunset = sunset;
    }

    public String getHigh() {
        return "\'"+high+"\'";
    }

    public void setHigh(String high) {
        this.high = high;
    }

    public String getLow() {
        return "\'"+low+"\'";
    }

    public void setLow(String low) {
        this.low = low;
    }

    public String getAqi() {
        return "\'"+aqi+"\'";
    }

    public void setAqi(String aqi) {
        this.aqi = aqi;
    }

    public String getFengxiang() {
        return "\'"+fengxiang+"\'";
    }

    public void setFengxiang(String fengxiang) {
        this.fengxiang = fengxiang;
    }

    public String getFenglevel() {
        return "\'"+fenglevel+"\'";
    }

    public void setFenglevel(String fenglevel) {
        this.fenglevel = fenglevel;
    }

    public String getType() {
        return "\'"+type+"\'";
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getNotice() {
        return "\'"+notice+"\'";
    }

    public void setNotice(String notice) {
        this.notice = notice;
    }
}
