package com.example.portable;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.security.*;
import java.security.spec.*;
import java.util.Base64;
import java.util.Map;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;

public class LicenseValidator {

    public static PublicKey loadPublicKey(String resourcePath) throws Exception {
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        InputStream is = loader.getResourceAsStream(resourcePath);

        if (is == null) {
            throw new RuntimeException("Public key file not found: " + resourcePath);
        }

        String pem = new String(is.readAllBytes())
                .replace("-----BEGIN PUBLIC KEY-----", "")
                .replace("-----END PUBLIC KEY-----", "")
                .replaceAll("\\s+", "");

        byte[] decoded = Base64.getDecoder().decode(pem);
        X509EncodedKeySpec spec = new X509EncodedKeySpec(decoded);

        return KeyFactory.getInstance("RSA").generatePublic(spec);
    }

    public static Map<String, Object> loadJsonFromResources(String resourcePath) throws Exception {
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        InputStream is = loader.getResourceAsStream(resourcePath);

        if (is == null) {
            throw new RuntimeException("License file not found: " + resourcePath);
        }

        return new ObjectMapper().readValue(is, Map.class);
    }

    public static boolean verifyLicense(String licensePath, String publicKeyPath) {
        try {
            Map<String, Object> licenseMap = loadJsonFromResources(licensePath);
            Map<String, Object> licenseData = (Map<String, Object>) licenseMap.get("license");
            String signature = (String) licenseMap.get("signature");

            String licenseString = new ObjectMapper().writeValueAsString(licenseData);

            PublicKey key = loadPublicKey(publicKeyPath);

            Signature sig = Signature.getInstance("SHA256withRSA");
            sig.initVerify(key);
            sig.update(licenseString.getBytes());

            boolean valid = sig.verify(Base64.getDecoder().decode(signature));

            System.out.println(valid ? "✅ License valid" : "❌ License invalid");
            return valid;

        } catch (Exception e) {
            System.out.println("❌ License validation error: " + e.getMessage());
            return false;
        }
    }

}

