package weatherapp.a23sokolov.com.model.elements;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Root;

/**
 * Created by alexey on 19/12/16.
 */
@Root(strict = false)
public class Phenomena {
    @Attribute
    private Integer cloudiness;

    @Attribute
    private Integer precipitation;

    public Integer getCloudiness() {
        return cloudiness;
    }

    public Integer getPrecipitation() {
        return precipitation;
    }

    @Override
    public String toString() {
        final StringBuilder strB = new StringBuilder("{");
        strB.append("max=").append(cloudiness);
        strB.append(",min=").append(precipitation).append("}");
        return strB.toString();
    }
}
