package yaruliy.lgaming.entity;
import yaruliy.lgaming.Config;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.StringWriter;

@XmlRootElement(name = "request")
public class VerifyRequest extends AbstractRequest{
    @XmlAttribute
    int point;
    @XmlElement
    Verify verify;

    public VerifyRequest(){}
    public VerifyRequest(Verify verify) {
        this.verify = verify;
        this.point = Config.getPointNumber();
    }

    @Override
    public String getContent() throws JAXBException {
        StringWriter sw = new StringWriter();
        JAXBContext context = JAXBContext.newInstance(VerifyRequest.class);
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FRAGMENT, Boolean.TRUE);
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        marshaller.marshal(this, sw);
        return sw.toString();
    }
}