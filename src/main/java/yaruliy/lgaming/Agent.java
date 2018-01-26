package yaruliy.lgaming;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;
import yaruliy.lgaming.entity.AbstractRequest;
import javax.net.ssl.HttpsURLConnection;
import javax.xml.bind.JAXBException;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.net.URL;
import java.security.SignatureException;

@Component
public class Agent {
    private final String URL = "https://test.lgaming.net/external/extended";
    private Cypher cypher;
    @Autowired public void setCypher(Cypher cypher){this.cypher = cypher;}

    public void send(AbstractRequest abstractRequestBody)
            throws IOException, SignatureException, JAXBException, TransformerException, ParserConfigurationException, SAXException {
        HttpsURLConnection conion = (HttpsURLConnection) new URL(URL).openConnection();
        conion.setRequestMethod("POST");

        String XMLContent = abstractRequestBody.getContent();
        System.out.println("-------------------------" + abstractRequestBody.getClass().getSimpleName() +
                "------------------------------------------");
        System.out.println(XMLContent);
        String signa = cypher.sign(XMLContent);
        System.out.println("\nRequest Signature: " + signa);
        conion.setRequestProperty("PayLogic-Signature", signa);

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
        System.out.println();
        printXML(response.toString());
        String signature = conion.getHeaderField("PayLogic-Signature");
        System.out.println("Response Signature: " + signature);
        System.out.println("Verify: " + cypher.verify(response.toString().trim(), signature.trim()));
        System.out.println("---------------------------------------------------------------------------------");
    }

    private void printXML(String response) throws ParserConfigurationException, TransformerException, IOException, SAXException {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        dbf.setValidating(false);
        Document doc = dbf.newDocumentBuilder().parse(new ByteArrayInputStream(response.getBytes()));

        Transformer tf = TransformerFactory.newInstance().newTransformer();
        tf.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
        tf.setOutputProperty(OutputKeys.INDENT, "yes");
        tf.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
        Writer out = new StringWriter();
        tf.transform(new DOMSource(doc), new StreamResult(out));
        System.out.println(out.toString());
    }
}