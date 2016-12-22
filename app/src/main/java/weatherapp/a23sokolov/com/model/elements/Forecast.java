package weatherapp.a23sokolov.com.model.elements;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import java.util.Calendar;

import weatherapp.a23sokolov.com.misc.Utils;

/**
 * Created by alexey on 19/12/16.
 */
@Root(name = "FORECAST", strict = false)
public class Forecast {
    @Attribute
    private Integer day;

    @Attribute
    private Integer month;

    @Attribute
    private Integer year;

    @Attribute
    private Integer hour;

    @Attribute(name = "weekday")
    private Integer weekDay;

    @Element(name = "PHENOMENA")
    private Phenomena phenomena;

    @Element(name = "TEMPERATURE")
    private Temperature temperature;

    @Element(name = "PRESSURE")
    private Pressure pressure;

    @Element(name = "WIND")
    private Wind wind;

    @Element(name = "RELWET")
    private Humidity humidity;

    public Integer getDay() {
        return day;
    }

    public Integer getMonth() {
        return month;
    }

    public Integer getYear() {
        return year;
    }

    public Integer getHour() {
        return hour;
    }

    public Integer getWeekDay() {
        return weekDay;
    }

    public Temperature getTemperature() {
        return temperature;
    }

    public Phenomena getPhenomena() {
        return phenomena;
    }

    public Wind getWind() {
        return wind;
    }

    public Pressure getPressure() {
        return pressure;
    }

    public Humidity getHumidity() {
        return humidity;
    }

    public String getDate() {
        final Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month - 1);
        calendar.set(Calendar.DATE, day);
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return Utils.formatDateTimeByPattern("EEE dd.MM.yyyy HH:mm", calendar.getTime());
    }

    public int getCloudiness() {
        return getPhenomena().getCloudiness();
    }

    public String getWindSpeed() {
        return getWind().getMin() + "-" + getWind().getMax();
    }

    @Override
    public String toString() {
        final StringBuilder strB = new StringBuilder("{");
        strB.append("day=").append(day);
        strB.append(",month=").append(month);
        strB.append(",year=").append(year);
        strB.append(",weekDay=").append(weekDay);
        strB.append(",pressure").append(pressure);
        strB.append(",humidity").append(humidity);
        strB.append(",temperature=").append(temperature);
        strB.append(",phenomena=").append(phenomena);
        strB.append(",wind=").append(wind).append("}");
        return strB.toString();
    }

}
