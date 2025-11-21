package com.example.portable.Services;

import com.example.portable.Util.CryptoUtil;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;

@Service
public class LicenseService {

    @Autowired
    private HardwareService hardwareService;

    private Path getBackendPath() {
        return Paths.get("").toAbsolutePath().resolve("portable-app").resolve("app-backend");
    }

    public boolean validateLicense() {
        try {
            Path basePath = getBackendPath();

            Path licensePath = basePath.resolve("license.enc");
            Path certPath = basePath.resolve("company_cert.pem");

            byte[] encrypted = Files.readAllBytes(licensePath);

            String decryptedJson = CryptoUtil.decryptWithPublicKey(
                    encrypted,
                    certPath.toString()
            );

            JSONObject json = new JSONObject(decryptedJson);

            String allowedHardwareId = json.getString("fingerprint");
            String expiry = json.getString("expiry");

            String currentHardwareId = hardwareService.getHardwareId();

            // Hardware mismatch
            if (!currentHardwareId.equals(allowedHardwareId)) {
                System.out.println("❌ Hardware mismatch");
                return false;
            }

            // Expiry check
            if (LocalDate.now().isAfter(LocalDate.parse(expiry))) {
                System.out.println("❌ License expired");
                return false;
            }

            System.out.println("✅ License VALID");
            return true;

        } catch (Exception e) {
            System.out.println("❌ License validation error: " + e.getMessage());
            return false;
        }
    }

    public String getCompanyId() {
        try {
            Path basePath = getBackendPath();

            Path licensePath = basePath.resolve("license.enc");
            Path certPath = basePath.resolve("company_cert.pem");

            byte[] encrypted = Files.readAllBytes(licensePath);

            String decryptedJson = CryptoUtil.decryptWithPublicKey(
                    encrypted,
                    certPath.toString()
            );

            JSONObject json = new JSONObject(decryptedJson);
            return json.getString("company_id");

        } catch (Exception e) {
            return "UNKNOWN";
        }
    }
}
