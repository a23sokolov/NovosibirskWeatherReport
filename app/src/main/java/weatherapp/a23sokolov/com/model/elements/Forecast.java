package weatherapp.a23sokolov.com.model.elements;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

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

    @Attribute(name = "weekday")
    private Integer weekDay;

    @Element(name = "PHENOMENA")
    private Phenomena phenomena;

    @Element(name = "TEMPERATURE")
    private Temperature temperature;

    @Element(name = "WIND")
    private Wind wind;

    public Integer getDay() {
        return day;
    }

    public Integer getMonth() {
        return month;
    }

    public Integer getYear() {
        return year;
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

    @Override
    public String toString() {
        final StringBuilder strB = new StringBuilder("{");
        strB.append("day=").append(day);
        strB.append(",month=").append(month);
        strB.append(",year=").append(year);
        strB.append(",weekDay=").append(weekDay);
        strB.append(",temperature=").append(temperature);
        strB.append(",phenomena=").append(phenomena);
        strB.append(",wind=").append(wind).append("}");
        return strB.toString();
    }

}
