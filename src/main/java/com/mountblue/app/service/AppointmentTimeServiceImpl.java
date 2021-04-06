package com.mountblue.app.service;

import com.mountblue.app.repository.AppointmentTimeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AppointmentTimeServiceImpl implements AppointmentTimeService{
    @Autowired
    AppointmentTimeRepository appointmentTimeRepository;
}
