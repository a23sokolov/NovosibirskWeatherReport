package weatherapp.a23sokolov.com.model.elements;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Root;

/**
 * Created by alexey on 19/12/16.
 */
@Root
public class Wind {
    @Attribute
    private Integer min;

    @Attribute
    private Integer max;

    @Attribute
    private Integer direction;

    @Override
    public String toString() {
        final StringBuilder strB = new StringBuilder("{");
        strB.append("min=").append(min);
        strB.append(",max=").append(max);
        strB.append(",direction=").append(direction).append("}");
        return strB.toString();
    }

}
