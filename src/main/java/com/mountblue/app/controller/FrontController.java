package com.mountblue.app.controller;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

import com.mountblue.app.model.*;
import com.mountblue.app.service.AppointmentService;
import com.mountblue.app.service.EventService;
import com.mountblue.app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
public class FrontController {

    @Autowired
    private UserService userService;

    @Autowired
    private EventService eventService;

    @Autowired
    private AppointmentService appointmentService;

    @GetMapping("/")
    public String home(Model model) {

        return "home";
    }

    @GetMapping("/register")
    public String register(Model model) {

        User user = new User();
        model.addAttribute("user", user);
        return "register";
    }


    @GetMapping("/login")
    public String login() {

        return "login";
    }

    @GetMapping("/dashboard/{userId}")
    public String dashboard(@PathVariable("userId") int userId, Model model) {

        model.addAttribute("userId", userId);
        List<Event> events = eventService.findEventByUserId(userId);
        model.addAttribute("events", events);
        return "dashboard";
    }

    @GetMapping("/date/{eventId}/{sessionUserId}/{m}/{d}/{y}")
    public String date(@PathVariable("sessionUserId") int sessionUserId, @PathVariable("eventId") int eventId, @PathVariable("m") int m, @PathVariable("d") int d, @PathVariable("y") int y, Model model, HttpSession session) {
        boolean flag = false;
        LocalDate date = LocalDate.of(2000 + y, m, d);

        Event event = eventService.findById(eventId).get();

        User host = event.getUser();
        User client = userService.findById(sessionUserId).get();

        int durationUnit = event.getDurationUnit();

        List<AllowedTime> allowedTimes = event.getAllowedTimes();
        Collections.sort(allowedTimes);

        List<AppointmentTime> appointmentTimes = host.getAppointmentTimes();

        List<LocalTime> times = new ArrayList<LocalTime>();
        for (AllowedTime allowedTime : allowedTimes) {
            for (LocalTime localTime = allowedTime.getFromTime(); localTime.isBefore(allowedTime.getToTime().minusMinutes(durationUnit - 1)); localTime = localTime.plusMinutes(15)) {
                for (AppointmentTime appointmentTime : appointmentTimes) {
                    if (appointmentTime.getUser().getId() == sessionUserId || appointmentTime.getUser().getId() == host.getId() &&
                            appointmentTime.getDate().isEqual(date) &&
                            localTime.plusMinutes(durationUnit - 1).isAfter(appointmentTime.getFromTime().minusMinutes(1)) &&
                            localTime.isBefore(appointmentTime.getToTime())) {
                        flag = true;
                        break;
                    }
                }
                if (!flag) {
                    if(date.isAfter(LocalDate.now()) || localTime.isAfter(LocalTime.now()))
                    times.add(localTime);
                }
                flag = false;
            }
        }
        model.addAttribute("times", times);
        model.addAttribute("sessionUserId", sessionUserId);
        model.addAttribute("eventId", eventId);
        model.addAttribute("date", date);
        return "selectTimePage";
    }

    @GetMapping("/{userName}")
    public String userName(@PathVariable("userName") String userName, Model model, HttpSession session) {
        int sessionUserId = (int) session.getAttribute("sessionUserId");

        Optional<User> optional = userService.findByName(userName);
        User user = optional.get();
        List<Event> events = user.getEvents();

        model.addAttribute("events", events);
        model.addAttribute("sessionUserId", sessionUserId);
        return "/showEvents";
    }


}