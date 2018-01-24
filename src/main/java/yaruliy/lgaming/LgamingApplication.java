package yaruliy.lgaming;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.io.IOException;
import java.security.SignatureException;

@SpringBootApplication
public class LgamingApplication {
    private static Agent agent;
    @Autowired public LgamingApplication(Agent agent){ LgamingApplication.agent = agent; }

    public static void main(String[] args) throws IOException, SignatureException {
		SpringApplication.run(LgamingApplication.class, args);
        Request request = new Request();
        agent.send(request);
	}
}