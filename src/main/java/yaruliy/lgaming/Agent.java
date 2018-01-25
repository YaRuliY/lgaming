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
import java.util.List;
import java.util.Map;

@Component
public class Agent {
    private final String URL = "https://test.lgaming.net/external/extended";
    private Cypher cypher;
    @Autowired public void setCypher(Cypher cypher){this.cypher = cypher;}

    public void send(Request request) throws IOException, SignatureException {
        HttpsURLConnection conion = (HttpsURLConnection) new URL(URL).openConnection();
        conion.setRequestMethod("POST");

        String XMLContent = request.XMLRequestAsString();
        System.out.println("\n--------------------------------Request as String---------------------------------");
        System.out.println(XMLContent);
        System.out.println("------------------------------------------------------------------------------------\n");
        conion.setRequestProperty("PayLogic-Signature", cypher.sign(XMLContent));

        conion.setDoOutput(true);
        OutputStream os = conion.getOutputStream();
        os.write(XMLContent.getBytes("UTF-8"));
        os.close();

        String inputLine;
        int responseCode = conion.getResponseCode();

        BufferedReader in = new BufferedReader(new InputStreamReader(conion.getInputStream()));
        StringBuilder response = new StringBuilder();
        while((inputLine = in.readLine()) != null){ response.append(inputLine); }
        in.close();

        System.out.println("Response Code: " + responseCode);
        printXML(response.toString());

        //---------------------------------------------------------------------------------------
        String signature = conion.getHeaderField("PayLogic-Signature");
        System.out.println("\n-----------------------------------Signature from header----------------------------------------");
        System.out.print(signature.substring(0, 50) + " ... ");
        System.out.println(signature.substring(signature.length() - 50, signature.length()));
        System.out.println("--------------------------------------------------------------------------------------\n");
        System.out.println("Verify: " + cypher.verify(response.toString().trim(), signature.trim()));
    }

    private void printXML(String response){
        System.out.println("Output: [");
        System.out.println("\t" + response);
        System.out.println("]");
    }
}