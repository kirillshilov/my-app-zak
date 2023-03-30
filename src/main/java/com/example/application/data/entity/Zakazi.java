package com.example.application.data.entity;

import jakarta.persistence.Entity;
import java.time.LocalDateTime;

@Entity
public class Zakazi extends AbstractEntity {

    private String name;
    private String deadManSurname;
    private String uchastok;
    private String phone;
    private String raboti;
    private LocalDateTime date;
    private String stadia;

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getDeadManSurname() {
        return deadManSurname;
    }
    public void setDeadManSurname(String deadManSurname) {
        this.deadManSurname = deadManSurname;
    }
    public String getUchastok() {
        return uchastok;
    }
    public void setUchastok(String uchastok) {
        this.uchastok = uchastok;
    }
    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public String getRaboti() {
        return raboti;
    }
    public void setRaboti(String raboti) {
        this.raboti = raboti;
    }
    public LocalDateTime getDate() {
        return date;
    }
    public void setDate(LocalDateTime date) {
        this.date = date;
    }
    public String getStadia() {
        return stadia;
    }
    public void setStadia(String stadia) {
        this.stadia = stadia;
    }

}
