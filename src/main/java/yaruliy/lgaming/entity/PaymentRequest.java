package yaruliy.lgaming.entity;
import yaruliy.lgaming.Config;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.annotation.*;
import java.io.StringWriter;
import java.util.List;

@XmlRootElement(name = "request")
public class PaymentRequest extends AbstractRequest{
    @XmlAttribute
    int point;
    private List<Payment> payments;

    @XmlElement(name="payment")
    public List<Payment> getPayments() {
        return this.payments;
    }

    public void setPayments(List<Payment> payments) {
        this.payments = payments;
    }

    public PaymentRequest(){}
    public PaymentRequest(List<Payment> payments) {
        this.payments = payments;
        this.point = Config.getPointNumber();
    }

    @Override
    public String getContent() throws JAXBException {
        StringWriter sw = new StringWriter();
        JAXBContext context = JAXBContext.newInstance(PaymentRequest.class);
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FRAGMENT, Boolean.TRUE);
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        marshaller.marshal(this, sw);
        return sw.toString();
    }
}