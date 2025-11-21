package com.example.portable.Services;

import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.security.MessageDigest;

@Service
public class HardwareService {

    public String getHardwareId() {
        try {
            Process process = Runtime.getRuntime().exec(
                    "powershell.exe -Command \"(Get-CimInstance Win32_BIOS).SerialNumber\""
            );

            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(process.getInputStream()));

            String line = reader.readLine();
            return (line != null && !line.isBlank()) ? line.trim() : "UNKNOWN";

        } catch (Exception e) {
            return "UNKNOWN";
        }
    }

    private boolean isValid(String value) {
        return value != null &&
                !value.trim().isEmpty() &&
                !value.trim().equalsIgnoreCase("serialnumber") &&
                !value.trim().equalsIgnoreCase("uuid") &&
                !value.trim().equalsIgnoreCase("unknown");
    }

    private String runCommand(String command) {
        try {
            Process process = Runtime.getRuntime().exec(command);
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(process.getInputStream()));

            reader.readLine(); // skip header
            return reader.readLine().trim();
        } catch (Exception e) {
            return "";
        }
    }

    private String sha256(String data) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            return bytesToHex(md.digest(data.getBytes()));
        } catch (Exception e) {
            return data;
        }
    }

    private String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }
}