package com.example.portable.Util;

import javax.crypto.Cipher;
import java.io.FileInputStream;
import java.security.PublicKey;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;

public class CryptoUtil {

    public static String decryptWithPublicKey(byte[] encryptedData, String certPath)
            throws Exception {

        CertificateFactory cf = CertificateFactory.getInstance("X.509");
        Certificate cert = cf.generateCertificate(
                new FileInputStream(certPath));

        PublicKey publicKey = cert.getPublicKey();

        Cipher cipher = Cipher.getInstance("RSA/ECB/OAEPWithSHA-256AndMGF1Padding");
        cipher.init(Cipher.DECRYPT_MODE, publicKey);

        byte[] decrypted = cipher.doFinal(encryptedData);
        return new String(decrypted);
    }
}
