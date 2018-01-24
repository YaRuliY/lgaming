package yaruliy.lgaming;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.stereotype.Component;
import javax.annotation.PostConstruct;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;

@Component
public class Cypher {
    private PrivateKey privateKey;
    private PublicKey publicKey;
    public Cypher(){}

    public String sign(String message) throws SignatureException {
        try {
            Signature sign = Signature.getInstance("SHA1withRSA");
            sign.initSign(privateKey);
            sign.update(message.getBytes("UTF-8"));
            String s = new String(Base64.encodeBase64(sign.sign()), "UTF-8");
            System.out.println("----------------Result in Sing-----------------");
            System.out.println(s);
            System.out.println("----------------------------------------------------");
            return s;
        } catch (Exception ex) {
            throw new SignatureException(ex);
        }
    }

    public boolean verify(String message, String signature) throws SignatureException {
        try {
            Signature sign = Signature.getInstance("SHA1withRSA");
            sign.initVerify(publicKey);
            sign.update(message.getBytes("UTF-8"));
            return sign.verify(Base64.decodeBase64(signature.getBytes("UTF-8")));
        } catch (Exception ex) {
            throw new SignatureException(ex);
        }

    }

    @PostConstruct
    public void construct() throws IOException, NoSuchAlgorithmException, InvalidKeySpecException, URISyntaxException {
        byte[] privKeyByteArray = Files.readAllBytes(Paths.get(ClassLoader.getSystemResource("private_key.pem").toURI()));
        String temp = new String(privKeyByteArray);
        String privKeyPEM = temp.replace("-----BEGIN PRIVATE KEY-----\n", "");
        privKeyPEM = privKeyPEM.replace("-----END PRIVATE KEY-----", "");

        byte[] decoded = new Base64().decode(privKeyPEM);
        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(decoded);
        this.privateKey = KeyFactory.getInstance("RSA").generatePrivate(spec);
    }
}