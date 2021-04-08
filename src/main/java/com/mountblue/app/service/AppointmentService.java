package com.mountblue.app.service;

import com.mountblue.app.model.AppointmentTime;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface AppointmentService {
    List<AppointmentTime> findAppointmentsByUserId(int userId);

    AppointmentTime findById(int appointmentId);

    void save(AppointmentTime appointmentTime);

    void deleteMeeting(LocalDate date, LocalTime fromTime, LocalTime toTime, int eventId);

    void removeAppointments(List<AppointmentTime> oldAppointments);
}
