package weatherapp.a23sokolov.com.model;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import weatherapp.a23sokolov.com.model.elements.Report;

/**
 * Created by alexey on 19/12/16.
 */
@Root(name = "MMWEATHER")
public class WeatherReport {

    @Element(name = "REPORT")
    private Report report;

    public Report getReport() {
        return report;
    }

    @Override
    public String toString() {
        final StringBuilder strB = new StringBuilder("{");
        strB.append("report=").append(report).append("}");
        return strB.toString();
    }
}


