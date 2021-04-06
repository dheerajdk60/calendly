package com.mountblue.app.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String email;
    private String password;

    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<Event> events;

    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<AppointmentTime> appointmentTimes;

    public void addEvent(Event event) {
        if(events==null)
        {
            events=new ArrayList<Event>();
        }
        event.setUser(this);
        events.add(event);
    }
    public void addAppointmentTime(AppointmentTime appointmentTime) {
        if(appointmentTimes==null)
        {
            appointmentTimes=new ArrayList<AppointmentTime>();
        }
        appointmentTime.setUser(this);
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Event> getEvents() {
        return events;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }

    public List<AppointmentTime> getAppointmentTimes() {
        return appointmentTimes;
    }

    public void setAppointmentTimes(List<AppointmentTime> appointmentTimes) {
        this.appointmentTimes = appointmentTimes;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", events=" + events +
                ", appointmentTimes=" + appointmentTimes +
                '}';
    }
}
