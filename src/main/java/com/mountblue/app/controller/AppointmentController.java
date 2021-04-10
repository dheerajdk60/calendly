package com.mountblue.app.controller;


import com.mountblue.app.model.AppointmentTime;
import com.mountblue.app.model.Event;
import com.mountblue.app.model.User;
import com.mountblue.app.service.AppointmentService;
import com.mountblue.app.service.EventService;
import com.mountblue.app.service.SendEmailService;
import com.mountblue.app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.time.LocalTime;

@Controller
@RequestMapping("/appointment")
public class AppointmentController {
    @Autowired
    private UserService userService;
    @Autowired
    private EventService eventService;
    @Autowired
    private AppointmentService appointmentService;
    @Autowired
    private SendEmailService sendEmailService;

    @GetMapping("/{eventId}/{sessionUserId}/{date}/{time}")
    public String appoint(@PathVariable("sessionUserId") int sessionUserId, @PathVariable("eventId") int eventId, @PathVariable("time") String timeStr, @PathVariable("date") String dateStr) {
        Event event = eventService.findById(eventId).get();
        int duration = event.getDurationUnit();

        LocalTime time = LocalTime.parse(timeStr);
        LocalDate date = LocalDate.parse(dateStr);

        AppointmentTime appointmentTime = new AppointmentTime();
        appointmentTime.setDate(date);
        appointmentTime.setFromTime(time);
        appointmentTime.setToTime(time.plusMinutes(duration));

        event.addAppointmentTime(appointmentTime);

        User host = event.getUser();
        User client = userService.findById(sessionUserId).get();

        host.addAppointmentTime(appointmentTime);
        userService.saveUser(host);

        client.addAppointmentTime(appointmentTime);
        userService.saveUser(client);

        String body=String.format("Hello, \nAn appointment has been booked with \nHost : %s\nClient : %s\non %s from %s to %s.\n The Topic is %s",host.getName(),client.getName(),appointmentTime.getDate()+"",appointmentTime.getFromTime()+"",appointmentTime.getToTime()+"",event.getName());

        sendEmailService.sendNotification(body,host.getEmail(),client.getEmail());

        return "redirect:/dashboard/"+sessionUserId;
    }
    @GetMapping("/delete/{appointmentId}")
    public String delete(@PathVariable("appointmentId") int appointmentId) {
       int cancellationTime=30;
       AppointmentTime appointmentTime=appointmentService.findById(appointmentId);

       if(appointmentTime.getDate().isAfter(LocalDate.now())||appointmentTime.getDate().isEqual(LocalDate.now())&&
          appointmentTime.getFromTime().isAfter(LocalTime.now().plusMinutes(appointmentTime.getEvent().getCancellationTime()-1)) )
        {
            appointmentService.deleteMeeting(appointmentTime.getDate(),appointmentTime.getFromTime(),appointmentTime.getToTime(),appointmentTime.getEvent().getId());
        }
        return "redirect:/event/appointments/"+appointmentTime.getUser().getId();

    }
}
