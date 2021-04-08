package com.mountblue.app.controller;

import com.mountblue.app.model.AllowedTime;
import com.mountblue.app.model.AppointmentTime;
import com.mountblue.app.model.Event;
import com.mountblue.app.model.User;
import com.mountblue.app.service.AppointmentService;
import com.mountblue.app.service.EventService;
import com.mountblue.app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Controller
@RequestMapping("/event")
public class EventController {

    public static final int HOUR = 0;
    public static final int MINUTE = 1;

    @Autowired
    private EventService eventService;
    @Autowired
    private UserService userService;

    @Autowired
    private AppointmentService appointmentService;

    @GetMapping("/create/{userId}")
    public String eventCreate(@PathVariable("userId") int userId, Model model) {
        Event event = new Event();

        model.addAttribute("userId", userId);
        model.addAttribute("event", event);

        return "eventForm";
    }
    @GetMapping("/show/{eventId}/{sessionUserId}")
    public String showEvent(@PathVariable("sessionUserId") int sessionUserId, @PathVariable("eventId") int eventId, Model model, HttpSession session) {
        Optional<Event> optional = eventService.findById(eventId);
        Event event = optional.get();

        List<LocalDate> dates = new ArrayList<LocalDate>();
        LocalDate i = LocalDate.now();
        while (i.isBefore(event.getEventCreatedAt().plusDays(event.getEventLife()))) {
            dates.add(i);
            i = i.plusDays(1);
        }
        model.addAttribute("event", event);
        model.addAttribute("sessionUserId", sessionUserId);
        model.addAttribute("dates", dates);
        return "/showEvent";
    }
    @GetMapping("/insert")
    public String eventInsert(@RequestParam("userId") int userId,
                              @ModelAttribute("event") Event event,
                              Model model,
                              @RequestParam String[] fromTime,
                              @RequestParam String[] toTime) {
        Optional<User> optional = userService.findById(userId);
        User user = optional.get();

        event.setEventCreatedAt(LocalDate.now());

        for (int i = 0; i < toTime.length; i++) {
            AllowedTime allowedTime = new AllowedTime();

            String from[] = fromTime[i].split("[^0-9]");
            String to[] = toTime[i].split("[^0-9]");

            allowedTime.setFromTime(LocalTime.parse(from[HOUR] + ":" + (MINUTE < from.length ? from[MINUTE] : "00")));
            allowedTime.setToTime(LocalTime.parse(to[HOUR] + ":" + (MINUTE < to.length ? to[MINUTE] : "00")));

            event.addAllowedTime(allowedTime);
        }
        user.addEvent(event);
        userService.saveUser(user);

        return "redirect:/dashboard/" + userId;
    }
    @GetMapping("/appointments/{userId}")
    public String appointments(@PathVariable("userId") int userId, Model model) {
        List<AppointmentTime> appointmentTimes=appointmentService.findAppointmentsByUserId(userId);
        List<AppointmentTime> oldAppointments=appointmentTimes.stream().filter(appointment->appointment.getDate().isBefore(LocalDate.now())||appointment.getDate().isEqual(LocalDate.now())&&appointment.getFromTime().isBefore(LocalTime.now())).collect(Collectors.toList());
        appointmentService.removeAppointments(oldAppointments);
        appointmentTimes.removeAll(oldAppointments);
        model.addAttribute("appointmentTimes", appointmentTimes);
        return "appointments";
    }

}
