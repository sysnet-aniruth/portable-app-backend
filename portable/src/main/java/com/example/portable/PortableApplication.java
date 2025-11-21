package com.example.portable;

import com.example.portable.Services.LicenseService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class PortableApplication {

    public static void main(String[] args) {

        // These are classpath locations now
        String licensePath = "licenses/license.json";
        String publicKeyPath = "keys/public_key.pem";

        boolean valid = LicenseValidator.verifyLicense(licensePath, publicKeyPath);

        if (!valid) {
            System.out.println("❌ LICENSE INVALID — Shutting down backend.");
            System.exit(1);
        }

        SpringApplication.run(PortableApplication.class, args);
        System.out.println("✅ Backend started successfully!");
    }

}