package com.mountblue.app.service;

import com.mountblue.app.model.AppointmentTime;

import java.util.List;

public interface AppointmentService {
    List<AppointmentTime> findAppointmentsByUserId(int userId);
}
