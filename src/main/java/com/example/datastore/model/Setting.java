package com.example.datastore.model;

import java.time.ZonedDateTime;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(
    name = "settings",
    indexes = {
        @Index(name = "name", columnList = "name")}
)
public class Setting {

    public static final String KIND = "Setting";

    @Id
    @GeneratedValue
    @Column(columnDefinition = "BINARY(16) NOT NULL", nullable = false, updatable = false)
    private UUID settingId;

    @Column(columnDefinition = "DATETIME(3) NOT NULL", nullable = false, updatable = false)
    private ZonedDateTime created;

    @Column(columnDefinition = "DATETIME(3) NOT NULL", nullable = false)
    private ZonedDateTime updated;

    @Column(columnDefinition = "DATETIME(3)")
    private ZonedDateTime deleted;

    @Column(length = 128, nullable = false)
    private String name;

    @Column(length = 128, nullable = false)
    private String value;

    public boolean checkDeleted() {
        return deleted != null;
    }

}
