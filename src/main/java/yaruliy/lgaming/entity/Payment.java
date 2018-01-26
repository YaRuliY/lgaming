package yaruliy.lgaming.entity;
import yaruliy.lgaming.Config;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import java.util.List;

public class Payment extends AbstractRequestBody{
    public Payment(int id, int sum, int check, int account) {
        super();
        this.id = id;
        this.sum = sum;
        this.check = check;
        this.account = account;
        this.service = Config.getServiceNumber();
        //this.date = new Date();
        this.date = "2007-10-12T12:00:00+0300";
    }

    public Payment(int id){
        super();
        this.id = id;
    }

    @XmlAttribute
    private int id;

    @XmlAttribute
    private int sum;

    @XmlAttribute
    private int check;

    @XmlAttribute
    private int account;

    @XmlAttribute
    private String date;

    @XmlAttribute
    private int service;

    @XmlElement(name="attribute")
    private List<Attribute> attributes;

    public void setAttributes(List<Attribute> attributes) {
        this.attributes = attributes;
    }
}
