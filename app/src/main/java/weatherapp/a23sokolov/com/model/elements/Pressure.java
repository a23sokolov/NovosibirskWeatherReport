package weatherapp.a23sokolov.com.model.elements;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Root;

/**
 * Created by alexey on 21/12/16.
 */
@Root(strict = false)
public class Pressure {
    @Attribute
    private Integer max;

    public Integer getMax() {
        return max;
    }

    @Override
    public String toString() {
        final StringBuilder stringBuilder = new StringBuilder("{");
        stringBuilder.append("max=").append(max).append("}");
        return stringBuilder.toString();
    }
}
