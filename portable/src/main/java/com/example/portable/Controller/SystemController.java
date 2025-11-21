package com.example.portable.Controller;

import com.example.portable.Services.HardwareService;
import com.example.portable.Services.LicenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class SystemController {

    @Autowired
    private HardwareService hardwareService;

    @Autowired
    private LicenseService licenseService;

    @GetMapping("/hardware-id")
    public String getHardwareId() {
        return hardwareService.getHardwareId();
    }

    @GetMapping("/validate")
    public Map<String, Object> validate() {
        Map<String, Object> result = new HashMap<>();

        boolean valid = licenseService.validateLicense();
        result.put("valid", valid);

        if (valid) {
            result.put("company", licenseService.getCompanyId());
        }

        return result;
    }
}
