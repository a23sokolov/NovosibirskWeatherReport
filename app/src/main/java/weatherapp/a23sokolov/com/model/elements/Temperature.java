package weatherapp.a23sokolov.com.model.elements;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Root;

/**
 * Created by alexey on 19/12/16.
 */
@Root
public class Temperature {
    @Attribute
    private Integer max;

    @Attribute
    private Integer min;

    public Integer getMax() {
        return max;
    }

    public Integer getMin() {
        return min;
    }

    @Override
    public String toString() {
        final StringBuilder strB = new StringBuilder("{");
        strB.append("max=").append(max);
        strB.append(",min=").append(min).append("}");
        return strB.toString();
    }
}
