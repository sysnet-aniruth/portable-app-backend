package com.example.portable.Util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.security.MessageDigest;

public class HardwareIdUtil {

    public static String getHardwareId() {
        String bios = execPS("(Get-CimInstance Win32_BIOS).SerialNumber");
        String cpu = execPS("(Get-CimInstance Win32_Processor).ProcessorId");
        String board = execPS("(Get-CimInstance Win32_BaseBoard).SerialNumber");
        String disk = execPS("(Get-CimInstance Win32_DiskDrive | Select -First 1).SerialNumber");
        String uuid = execPS("(Get-CimInstance Win32_ComputerSystemProduct).UUID");

        // Combine all
        String raw = (bios + cpu + board + disk + uuid).replace("null", "").trim();

        if (raw.isEmpty()) {
            return "UNKNOWN-HW";
        }

        return sha256(raw);
    }

    private static String execPS(String command) {
        try {
            Process p = Runtime.getRuntime().exec(
                    "powershell.exe -Command \"" + command + "\""
            );
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(p.getInputStream())
            );
            String line = reader.readLine();
            return (line != null) ? line.trim() : "";
        } catch (Exception e) {
            return "";
        }
    }

    private static String sha256(String text) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] bytes = digest.digest(text.getBytes());

            StringBuilder result = new StringBuilder();
            for (byte b : bytes) {
                result.append(String.format("%02x", b));
            }
            return result.toString();
        } catch (Exception e) {
            return text;
        }
    }
}
