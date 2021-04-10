package com.mountblue.app.controller;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

import com.mountblue.app.model.*;
import com.mountblue.app.service.AppointmentService;
import com.mountblue.app.service.EventService;
import com.mountblue.app.service.SendEmailService;
import com.mountblue.app.service.UserService;
/*import com.twilio.Twilio;

import com.twilio.rest.api.v2010.account.Message;
import com.twilio.rest.api.v2010.account.MessageCreator;
import com.twilio.type.PhoneNumber;*/

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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

    @Autowired
    SendEmailService sendEmailService;


    @GetMapping("/")
    public String home(Model model) {
        int id = getCurrentUserId();
        if(id!=-1)
        {
            return "redirect:/dashboard/"+id;
        }
        return "home";
    }

    @GetMapping("/register")
    public String register(Model model) {

        User user = new User();
        model.addAttribute("user", user);
        return "register";
    }

    @GetMapping("/dashboard/{userId}")
    public String dashboard(@PathVariable("userId") int userId, Model model) {

        model.addAttribute("userId", userId);
        List<Event> events = eventService.findEventByUserId(userId);
        model.addAttribute("events", events);
        return "dashboard";
    }

    @GetMapping("/date/{eventId}/{sessionUserId}/{m}/{d}/{y}")
    public String date(@PathVariable("sessionUserId") int sessionUserId, @PathVariable("eventId") int eventId, @PathVariable("m") int m, @PathVariable("d") int d, @PathVariable("y") int y, Model model) {
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
                    if (date.isAfter(LocalDate.now()) || localTime.isAfter(LocalTime.now()))
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

    @GetMapping("/login")
    public String login(Model model) {

        System.out.println("signin clicked");
        return "login";
    }


    @GetMapping("/{userName}")
    public String userName(@PathVariable("userName") String userName, Model model) {
        int sessionUserId = getCurrentUserId();
        if (userName.equals("dashboard"))
            return "redirect:/dashboard/" + sessionUserId;
        Optional<User> optional = userService.findByName(userName);
        User user = optional.get();
        List<Event> events = user.getEvents();

        model.addAttribute("events", events);
        model.addAttribute("sessionUserId", sessionUserId);
        return "/showEvents";
    }

    @GetMapping("/feedback")
    public String feedback() {
        return "feedbackForm";
    }

    @GetMapping("/sendFeedback")
    public String sendFeedback(@RequestParam("feedbackMessage") String feedackMessage) {
        int sessionUserId = getCurrentUserId();
        User user = userService.findById(sessionUserId).get();
        String body = "UserName : " + user.getName() + "\n Email : " + user.getEmail() + "\n Feedback : \n" + feedackMessage;
        sendEmailService.sendEmail(body);

       /* Twilio.init("ACea59a8f8f5f0e110961f0d22ae4fa3ca",
                "c6dc48a899e7c7515fa70680ced7fbfb");
        final PhoneNumber to = new PhoneNumber("+917026911691");
        final PhoneNumber from = new PhoneNumber("+18588793644");
        final MessageCreator creator = Message.creator(to, from, body);
        creator.create();*/

        return "redirect:/dashboard/" + sessionUserId;
    }

    public int getCurrentUserId() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = null;
        if (principal instanceof UserDetails) {
            username = ((UserDetails) principal).getUsername();
        } else {
            username = principal.toString();
        }
        if (username.equals("anonymousUser")) {
            return -1;
        }
        User user = userService.findByName(username).get();
        return user.getId();
    }


}