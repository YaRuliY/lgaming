package yaruliy.lgaming.entity;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement()
public class Attribute {
    @XmlAttribute
    public String name;
    @XmlAttribute
    public String value;

    public Attribute(){}
    public Attribute(String name, String value) {
        this.name = name;
        this.value = value;
    }
}