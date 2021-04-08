package com.mountblue.app.service;

import com.mountblue.app.model.AppointmentTime;
import com.mountblue.app.repository.AppointmentTimeRepository;
import static com.mountblue.app.specification.AppointmentTimeSpecification.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
public class AppointmentServiceImpl implements AppointmentService {

    @Autowired
    private AppointmentTimeRepository appointmentTimeRepository;

    @Override
    public List<AppointmentTime> findAppointmentsByUserId(int userId) {
        Specification<AppointmentTime> specification= findAppointmentsByUserIdForSpecification(userId);
        return appointmentTimeRepository.findAll(specification);
    }

    @Override
    public AppointmentTime findById(int appointmentId) {
        return appointmentTimeRepository.findById(appointmentId).get();
    }

    @Override
    public void save(AppointmentTime appointmentTime) {
        appointmentTimeRepository.save(appointmentTime);
    }

    @Override
    public void deleteMeeting(LocalDate date, LocalTime fromTime, LocalTime toTime, int eventId) {
      Specification<AppointmentTime> specification=isDate(date).and(isFromTime(fromTime)).and(isToTime(toTime)).and(isEventId(eventId));
        List<AppointmentTime> deleteAppointments=appointmentTimeRepository.findAll(specification);

        appointmentTimeRepository.deleteAll(deleteAppointments);
    }

    @Override
    public void removeAppointments(List<AppointmentTime> oldAppointments) {
        for(AppointmentTime appointment:oldAppointments)
        {
            deleteMeeting(appointment.getDate(),appointment.getFromTime(),appointment.getToTime(),appointment.getEvent().getId());
        }
    }
}
