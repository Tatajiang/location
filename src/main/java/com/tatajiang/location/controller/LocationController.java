package com.tatajiang.location.controller;

import cn.hutool.core.util.StrUtil;
import com.tatajiang.location.service.LocationInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/local")
public class LocationController {

    @Autowired
    private LocationInfo locationInfo;

    @GetMapping("/info/{code}")
    public String getLocationNameByCode(@PathVariable("code") String code) {
        if (StrUtil.isBlank(code) || code.length() > 6) {
            return "地区编码不能超过 6 位数";
        }
        return locationInfo.searchLocationName(code);
    }
}
