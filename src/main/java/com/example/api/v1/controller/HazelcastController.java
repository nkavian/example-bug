package com.example.api.v1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.datastore.model.Setting;
import com.example.datastore.service.SettingService;
import com.hazelcast.core.HazelcastInstance;

import io.swagger.v3.oas.annotations.Hidden;

@Hidden
@CrossOrigin // NOSONAR
@RestController("hazelcast-spring")
@RequestMapping(path = "hazelcast")
public class HazelcastController {

    @Autowired
    private SettingService settingService;

    @Autowired
    private HazelcastInstance hazelcast;

    @Hidden
    @GetMapping
    public String redoc() {
        // Works
        System.out.println(settingService.readValue("abc"));

        // Doesn't work
        System.out.println(hazelcast.getMap(Setting.KIND).get("abc"));
 
        return "hello";
    }

}
