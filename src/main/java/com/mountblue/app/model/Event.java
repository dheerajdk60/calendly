package com.mountblue.app.model;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;
    private String description;
    private int durationUnit;
    private LocalDate eventCreatedAt;
    private int eventLife;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.DETACH})
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<AllowedTime> allowedTimes;

    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<AppointmentTime> appointmentTimes;

    public void addAllowedTime(AllowedTime allowedTime) {
        if (allowedTimes == null) {
            allowedTimes = new ArrayList<AllowedTime>();
        }
        allowedTime.setEvent(this);
        allowedTimes.add(allowedTime);
    }

    public void addAppointmentTime(AppointmentTime appointmentTime) {
        if (appointmentTime == null) {
            appointmentTimes = new ArrayList<AppointmentTime>();
        }
        appointmentTime.setEvent(this);
        appointmentTimes.add(appointmentTime);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getDurationUnit() {
        return durationUnit;
    }

    public void setDurationUnit(int durationUnit) {
        this.durationUnit = durationUnit;
    }

    public LocalDate getEventCreatedAt() {
        return eventCreatedAt;
    }

    public void setEventCreatedAt(LocalDate eventCreatedAt) {
        this.eventCreatedAt = eventCreatedAt;
    }

    public int getEventLife() {
        return eventLife;
    }

    public void setEventLife(int eventLife) {
        this.eventLife = eventLife;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<AllowedTime> getAllowedTimes() {
        return allowedTimes;
    }

    public List<AppointmentTime> getAppointmentTimes() {
        return appointmentTimes;
    }

    public void setAppointmentTimes(List<AppointmentTime> appointmentTimes) {
        this.appointmentTimes = appointmentTimes;
    }

    public void setAllowedTimes(List<AllowedTime> allowedTimes) {
        this.allowedTimes = allowedTimes;
    }
}
