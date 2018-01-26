package yaruliy.lgaming;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.xml.sax.SAXException;
import yaruliy.lgaming.entity.*;
import javax.xml.bind.JAXBException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.security.SignatureException;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class LgamingApplication {
    private static Agent agent;
    @Autowired public LgamingApplication(Agent agent){ LgamingApplication.agent = agent; }

    public static void main(String[] args)
            throws IOException, SignatureException, JAXBException, ParserConfigurationException, SAXException, TransformerException {
		SpringApplication.run(LgamingApplication.class, args);
        Verify verify = new Verify();
        verify.setAccount(913234567);
        List<Attribute> list = new ArrayList<>();
        list.add(new Attribute("email","info@rol.ru"));
        list.add(new Attribute("00000","1111111"));
        verify.setAttributes(list);

        VerifyRequest mainRequest = new VerifyRequest(verify);
        agent.send(mainRequest);
        //--------------------------------------------------------------------------------
        Payment payment = new Payment(1,2,3, 4);

        List<Attribute> list2 = new ArrayList<>();
        list2.add(new Attribute("email","info@rol.ru"));
        payment.setAttributes(list2);

        Payment payment2 = new Payment(5,6,7, 8);

        List<Payment> payments = new ArrayList<>();
        payments.add(payment);
        payments.add(payment2);
        PaymentRequest paymentRequest = new PaymentRequest(payments);
        agent.send(paymentRequest);
        //--------------------------------------------------------------------------------
        Status status = new Status(1,2,3,4,5,6,7,8,9);
        Status status2 = new Status(1,2,3,4,5,6,7,8,9);
        List<Status> statusList = new ArrayList<>();
        statusList.add(status);
        statusList.add(status2);

        StatusRequest statusRequest = new StatusRequest(statusList);
        agent.send(statusRequest);
	}
}