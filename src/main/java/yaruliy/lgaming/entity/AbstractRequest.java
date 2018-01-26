package yaruliy.lgaming.entity;
import javax.xml.bind.JAXBException;

public abstract class AbstractRequest {
    public abstract String getContent() throws JAXBException;
}