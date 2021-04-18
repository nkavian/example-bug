package com.example.datastore.service;

import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.datastore.model.Setting;

@Service
@Transactional
public class SettingService {

    private Setting create(final Setting setting) {
        return null;
    }

    private Setting getByName(final String name) {
        return null;
    }

    public String readValue(final String name) {
        return "foo";
    }

    private void remove(final UUID settingId) {
    }

    private Setting update(final Setting setting) {
        return null;
    }

    public void writeValue(final String name, final String value) {
    }

}