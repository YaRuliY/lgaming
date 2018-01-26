package yaruliy.lgaming;
import org.springframework.stereotype.Component;
import javax.annotation.PostConstruct;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

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
            return new String(org.apache.tomcat.util.codec.binary.Base64.encodeBase64(sign.sign()), "UTF-8");
        }
        catch (Exception ex) { throw new SignatureException(ex); }
    }

    public boolean verify(String message, String signature) throws SignatureException {
        try {
            Signature sign = Signature.getInstance("SHA1withRSA");
            sign.initVerify(this.publicKey);
            sign.update(message.getBytes("UTF-8"));
            byte[] decoded = org.apache.tomcat.util.codec.binary.Base64.decodeBase64(signature.getBytes("UTF-8"));
            //byte[] decoded = Base64.getDecoder().decode(signature.getBytes("UTF-8"));
            return sign.verify(decoded);
        }
        catch (Exception ex) { throw new SignatureException(ex); }
    }

    @PostConstruct
    public void construct() throws IOException, NoSuchAlgorithmException, InvalidKeySpecException, URISyntaxException {
        byte[] privateKeyByteArray = Files.readAllBytes(Paths.get(ClassLoader.getSystemResource("private_key.der").toURI()));
        PKCS8EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(privateKeyByteArray);
        this.privateKey = KeyFactory.getInstance("RSA").generatePrivate(privateKeySpec);

        byte[] publicKeyByteArray = Files.readAllBytes(Paths.get(ClassLoader.getSystemResource("public_key.der").toURI()));
        X509EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(publicKeyByteArray);
        this.publicKey = KeyFactory.getInstance("RSA").generatePublic(publicKeySpec);
    }
}