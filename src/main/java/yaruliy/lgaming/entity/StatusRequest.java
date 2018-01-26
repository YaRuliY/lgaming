package yaruliy.lgaming.entity;
import yaruliy.lgaming.Config;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.StringWriter;
import java.util.List;

@XmlRootElement(name = "request")
public class StatusRequest extends AbstractRequest{
    @XmlAttribute
    int point;
    private List<Status> statusList;

    @XmlElement(name = "status")
    public List<Status> getStatusList(){
        return this.statusList;
    }

    public void setStatusList(List<Status> statusList) {
        this.statusList = statusList;
    }

    public StatusRequest(){}
    public StatusRequest(List<Status> list){
        this.statusList = list;
        this.point = Config.getPointNumber();
    }

    @Override
    public String getContent() throws JAXBException {
        StringWriter sw = new StringWriter();
        JAXBContext context = JAXBContext.newInstance(StatusRequest.class);
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FRAGMENT, Boolean.TRUE);
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        marshaller.marshal(this, sw);
        return sw.toString();
    }
}