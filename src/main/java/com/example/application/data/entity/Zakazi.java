package com.example.application.data.entity;

import jakarta.persistence.Entity;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
public class Zakazi extends AbstractEntity {

    private String name;
    private String deadManSurname;
    private String uchastok;
    private String phone;
    private String raboti;
    private LocalDateTime date;
    private String stadia;
    private String primechanie;

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

    public String getPrimechanie() {
        return primechanie;
    }

    public void setPrimechanie(String primechanie) {
        this.primechanie = primechanie;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Zakazi zakazi = (Zakazi) o;
        return Objects.equals(name, zakazi.name) && Objects.equals(deadManSurname, zakazi.deadManSurname) && Objects.equals(uchastok, zakazi.uchastok) && Objects.equals(phone, zakazi.phone) && Objects.equals(raboti, zakazi.raboti) && Objects.equals(date, zakazi.date) && Objects.equals(stadia, zakazi.stadia) && Objects.equals(primechanie, zakazi.primechanie);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), name, deadManSurname, uchastok, phone, raboti, date, stadia, primechanie);
    }

    @Override
    public String toString() {
        return "Zakazi{" +
                "name='" + name + '\'' +
                ", deadManSurname='" + deadManSurname + '\'' +
                ", uchastok='" + uchastok + '\'' +
                ", phone='" + phone + '\'' +
                ", raboti='" + raboti + '\'' +
                ", date=" + date +
                ", stadia='" + stadia + '\'' +
                ", primechanie='" + primechanie + '\'' +
                '}';
    }
}
