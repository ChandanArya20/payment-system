package com.cpt.payments.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.pkcs.PrivateKeyInfo;
import org.bouncycastle.asn1.pkcs.RSAPrivateKey;
import org.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@Service
public class RSAService {

    private final ObjectMapper objectMapper = new ObjectMapper();

    // Method to serialize the data
    public String serializeData(Object object) {
        if (object == null) {
            return "";
        }

        if (object instanceof Map) {
            // Sort the keys in lexicographical order
            TreeMap<String, Object> sortedMap = new TreeMap<>((Map<String, Object>) object);

            StringBuilder serialized = new StringBuilder();
            for (Map.Entry<String, Object> entry : sortedMap.entrySet()) {
                serialized.append(entry.getKey());
                serialized.append(serializeData(entry.getValue()));
            }
            return serialized.toString();
        } else if (object instanceof List) {
            StringBuilder serialized = new StringBuilder();
            for (Object value : (List<?>) object) {
                serialized.append(serializeData(value));
            }
            return serialized.toString();
        } else {
            // Handle scalar values (non-array/non-hash)
            return object.toString();
        }
    }

    // Method to parse JSON string to Map
    public Map<String, Object> parseJson(String jsonString) throws IOException {
        return objectMapper.readValue(jsonString, new TypeReference<Map<String, Object>>() {});
    }

    // Method to read private key from PEM file
    public String readPrivateKeyPEMFile(String filePath) throws IOException {
        return new String(Files.readAllBytes(Paths.get(filePath)))
                .replace("-----BEGIN RSA PRIVATE KEY-----", "")
                .replace("-----END RSA PRIVATE KEY-----", "")
                .replaceAll("\\s", "");
    }

    // Method to read public key from PEM file
    public String readPublicKeyPEMFile(String filePath) throws IOException {
        return new String(Files.readAllBytes(Paths.get(filePath)))
                .replace("-----BEGIN PUBLIC KEY-----", "")
                .replace("-----END PUBLIC KEY-----", "")
                .replaceAll("\\s", "");
    }

    // Convert PEM formatted string to PrivateKey
    public PrivateKey getPrivateKeyFromPEM(String privateKeyPEM) throws Exception {
        byte[] keyBytes = Base64.getDecoder().decode(privateKeyPEM);

        try {
            // Try to parse as PKCS8
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            return keyFactory.generatePrivate(keySpec);
        } catch (InvalidKeySpecException e) {
            // Try to parse as PKCS1
            ASN1Primitive asn1Primitive = ASN1Primitive.fromByteArray(keyBytes);
            RSAPrivateKey rsaPrivateKey = RSAPrivateKey.getInstance(asn1Primitive);
            AlgorithmIdentifier algorithmIdentifier = new AlgorithmIdentifier(new ASN1ObjectIdentifier("1.2.840.113549.1.1.1"));
            PrivateKeyInfo privateKeyInfo = new PrivateKeyInfo(algorithmIdentifier, rsaPrivateKey);
            PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(privateKeyInfo.getEncoded());
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            return keyFactory.generatePrivate(pkcs8KeySpec);
        }
    }

    // Method to sign the data
    public String sign(String method, String uuid, String jsonData, String privateKeyPEM) throws Exception {
        Map<String, Object> data = parseJson(jsonData);
        String plaintext = method + uuid + serializeData(data);
        System.out.println("sign plaintext:" + plaintext);
        
        PrivateKey privateKey = getPrivateKeyFromPEM(privateKeyPEM);

        Signature signature = Signature.getInstance("SHA256withRSA");
        signature.initSign(privateKey);
        signature.update(plaintext.getBytes("UTF-8"));
        byte[] signatureBytes = signature.sign();

        return Base64.getEncoder().encodeToString(signatureBytes);
    }

    // Method to verify the data
    public boolean verify(String method, String uuid, String jsonData, 
    		String signatureFromTrustly, String publicKeyPEM) throws Exception {
        Map<String, Object> data = parseJson(jsonData);
        String plaintext = method + uuid + serializeData(data);

        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(Base64.getDecoder().decode(publicKeyPEM));
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PublicKey publicKey = keyFactory.generatePublic(keySpec);

        Signature signature = Signature.getInstance("SHA256withRSA");
        signature.initVerify(publicKey);
        signature.update(plaintext.getBytes("UTF-8"));
        byte[] signatureBytes = Base64.getDecoder().decode(signatureFromTrustly);

        return signature.verify(signatureBytes);
    }

    // Main method for testing
    public static void main(String[] args) throws Exception {
        RSAService service = new RSAService();
        String jsonData = "{\"MyKey\": \"MyValue\", \"MyArray\": [\"Element1\", \"Element2\", {\"mykey2\": \"myvalue2\"}]}";
        System.out.println("jsonData:" + jsonData);
        String serializedData = service.serializeData(service.parseJson(jsonData));
        System.out.println(serializedData);  // Output: MyArrayElement1Element2mykey2myvalue2MyKeyMyValue

        // Read private key from PEM file
        String privateKeyPEM = service.readPrivateKeyPEMFile(".\\src\\main\\resources\\ecom_private.pem");
        
        // Read public key from PEM file for verification
        String publicKeyPEM = service.readPublicKeyPEMFile(".\\src\\main\\resources\\ecom_public.pem");

        // Sign the data
        String method = "POST";
        String uuid = "1234-5678";
        String signature = service.sign(method, uuid, jsonData, privateKeyPEM);
        System.out.println("Signature: " + signature);

        // Verify the signature
        boolean isVerified = service.verify(method, uuid, jsonData, signature, publicKeyPEM);
        System.out.println("Signature verified: " + isVerified);
    }
}
