package com.example.portable.Controller;

import com.example.portable.Util.HardwareIdUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LicenseController {

    @GetMapping("/hardware-id")
    public String getHardwareId() {
        return HardwareIdUtil.getHardwareId();
    }
}