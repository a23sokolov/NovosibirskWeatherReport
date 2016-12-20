package weatherapp.a23sokolov.com.model.elements;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * Created by alexey on 19/12/16.
 */
@Root
public class Report {
    @Element(name = "TOWN")
    private Town town;

    @Attribute
    private String type;

    public Town getTown() {
        return town;
    }

    @Override
    public String toString() {
        final StringBuilder strB = new StringBuilder("{");
        strB.append("town=").append(town).append("}");
        return strB.toString();
    }
}
