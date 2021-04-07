package com.mountblue.app.service;

import com.mountblue.app.model.AppointmentTime;
import com.mountblue.app.repository.AppointmentTimeRepository;
import com.mountblue.app.specification.AppointmentTimeSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AppointmentServiceImpl implements AppointmentService {

    @Autowired
    private AppointmentTimeRepository appointmentTimeRepository;

    @Override
    public List<AppointmentTime> findAppointmentsByUserId(int userId) {
        Specification<AppointmentTime> specification= AppointmentTimeSpecification.findAppointmentsByUserId(userId);
        return appointmentTimeRepository.findAll(specification);
    }
}
