package weatherapp.a23sokolov.com.model.elements;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

/**
 * Created by alexey on 19/12/16.
 */
@Root(strict = false)
public class Town {
    @ElementList(data = true, inline = true)
    private List<Forecast> forecasts;

    public List<Forecast> getForecasts() {
        return forecasts;
    }



    @Override
    public String toString() {
        final StringBuilder strB = new StringBuilder("{");
        strB.append("forecasts=").append(forecasts).append("}");
        return strB.toString();
    }
}
