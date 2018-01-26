package yaruliy.lgaming.entity;
import yaruliy.lgaming.Config;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement
public class Verify extends AbstractRequestBody {
    public Verify(){
        super();
        this.service = Config.getServiceNumber();
    }

    @XmlAttribute
    private int service;

    @XmlAttribute
    private int account;
    public void setAccount(int account){ this.account = account; }

    @XmlElement(name="attribute")
    private List<Attribute> attributes;

    public void setAttributes(List<Attribute> attributes) {
        this.attributes = attributes;
    }
}