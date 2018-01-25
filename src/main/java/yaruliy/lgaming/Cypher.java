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
import java.util.Base64;

@Component
public class Cypher {
    private PrivateKey privateKey;
    private PublicKey publicKey;
    public Cypher(){}

    public String sign(String message) throws SignatureException {
        try {
            Signature sign = Signature.getInstance("SHA1withRSA");
            System.out.println("Private Key Format: " + privateKey.getFormat() + "\nAlgorithm: " + privateKey.getAlgorithm());
            sign.initSign(privateKey);
            sign.update(message.getBytes("UTF-8"));
            //String encoded = new String(org.apache.tomcat.util.codec.binary.Base64.encodeBase64(sign.sign()), "UTF-8");
            String encoded = new String(Base64.getEncoder().encode(sign.sign()), "UTF-8");
            System.out.println("\n---------------------------Result in Sing----------------------------");
            System.out.print(encoded.substring(0, 50) + " ... ");
            System.out.println(encoded.substring(encoded.length() - 50, encoded.length()));
            System.out.println("--------------------------------------------------------------------------\n");
            return encoded;
        } catch (Exception ex) {
            throw new SignatureException(ex);
        }
    }

    public boolean verify(String message, String signature) throws SignatureException {
        try {
            Signature sign = Signature.getInstance("SHA1withRSA");
            sign.initVerify(publicKey);
            sign.update(message.getBytes());
            byte[] decoded = org.apache.tomcat.util.codec.binary.Base64.decodeBase64(signature.getBytes("UTF-8"));
            System.out.println("Decoded: " + new String(decoded));
            return sign.verify(decoded);
        } catch (Exception ex) {
            throw new SignatureException(ex);
        }
    }

    @PostConstruct
    public void construct() throws IOException, NoSuchAlgorithmException, InvalidKeySpecException, URISyntaxException {
        byte[] privateKeyByteArray = Files.readAllBytes(Paths.get(ClassLoader.getSystemResource("private_key.der").toURI()));
        PKCS8EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(privateKeyByteArray);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        this.privateKey = kf.generatePrivate(privateKeySpec);

        byte[] publicKeyByteArray = Files.readAllBytes(Paths.get(ClassLoader.getSystemResource("public_key.der").toURI()));
        X509EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(publicKeyByteArray);
        this.publicKey = kf.generatePublic(publicKeySpec);
    }
}