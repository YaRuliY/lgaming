package yaruliy.lgaming;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;
import java.security.SignatureException;

@Component
public class Agent {
    private final String URL = "https://test.lgaming.net/external/extended";
    private Cypher cypher;
    @Autowired public void setCypher(Cypher cypher){this.cypher = cypher;}

    public void send(Request request) throws IOException, SignatureException {
        HttpsURLConnection conion = (HttpsURLConnection) new URL(URL).openConnection();
        conion.setRequestMethod("POST");
        //conion.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
        conion.setRequestProperty("Content-Type", "application/soap+xml; charset=utf-8");

        String s = request.XMLRequestAsString();
        System.out.println("----------------Request as String-----------------");
        System.out.println(s);
        System.out.println("----------------------------------------------------");
        conion.setRequestProperty("PayLogic-Signature", cypher.sign(request.getBody()));

        conion.setDoOutput(true);
        OutputStream os = conion.getOutputStream();
        os.write(s.getBytes("UTF-8"));
        os.close();

        int responseCode = conion.getResponseCode();
        System.out.println("Response Code : " + responseCode);

        BufferedReader in = new BufferedReader(new InputStreamReader(conion.getInputStream()));
        String inputLine;
        StringBuilder response = new StringBuilder();

        while((inputLine = in.readLine()) != null){ response.append(inputLine); }
        in.close();

        System.out.println("Output: [");
        System.out.println("\t" + response.toString());
        System.out.println("]");
    }
}