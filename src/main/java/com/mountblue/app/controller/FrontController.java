package com.mountblue.app.controller;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

import com.mountblue.app.model.*;
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

    @GetMapping("/addUser")
    public String addUser(@ModelAttribute("user") User user) {

        userService.saveUser(user);
        return "redirect:/";
    }

    @GetMapping("/login")
    public String login() {

        return "login";
    }

    @GetMapping("/check")
    public String check(@RequestParam("name") String name, @RequestParam("password") String pass, Model model, HttpSession session) {

        Optional<User> optional = userService.findByName(name);
        if (optional.isPresent()) {
            User user = optional.get();
            if (user.getPassword().equals(pass)) {
                session.setAttribute("sessionUserId", user.getId());
                model.addAttribute("user", user);
                return "redirect:/dashboard/" + user.getId();
            }
        }
        return "redirect:/login";
    }

    @GetMapping("/dashboard/{userId}")
    public String dashboard(@PathVariable("userId") int userId, Model model) {

        model.addAttribute("userId", userId);
        List<Event> events = eventService.findEventByUserId(userId);
        model.addAttribute("events", events);
        return "dashboard";
    }

    @GetMapping("/showEvent/{eventId}/{sessionUserId}")
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

    @GetMapping("/date/{eventId}/{sessionUserId}/{m}/{d}/{y}")
    public String date(@PathVariable("sessionUserId") int sessionUserId, @PathVariable("eventId") int eventId, @PathVariable("m") int m, @PathVariable("d") int d, @PathVariable("y") int y, Model model, HttpSession session) {
        boolean flag=false;
        LocalDate date = LocalDate.of(2000 + y, m, d);
       /* ArrayList<Integer> times = new ArrayList<>(Arrays.asList(9, 10, 11, 12, 13, 14, 15, 16, 17));

        User host = eventService.findById(eventId).get().getUser();
        User client = userService.findById(sessionUserId).get();
        List<Integer> hostTime = host.getAppointmentTimes().stream().filter(a -> a.getDate().isEqual(date)).map(a -> a.getTime()).collect(Collectors.toList());
        List<Integer> clientTime = client.getAppointmentTimes().stream().filter(a -> a.getDate().isEqual(date)).map(a -> a.getTime()).collect(Collectors.toList());
        times.removeAll(hostTime);
        times.removeAll(clientTime);*/

        Event event = eventService.findById(eventId).get();

        User host = event.getUser();
        User client = userService.findById(sessionUserId).get();

        int durationUnit = event.getDurationUnit();

        List<AllowedTime> allowedTimes = event.getAllowedTimes();
        Collections.sort(allowedTimes);

        List<AppointmentTime> appointmentTimes=host.getAppointmentTimes();

        List<LocalTime> times = new ArrayList<LocalTime>();
        for (AllowedTime allowedTime : allowedTimes) {
            for (LocalTime localTime = allowedTime.getFromTime(); localTime.isBefore(allowedTime.getToTime().minusMinutes(durationUnit - 1)); localTime = localTime.plusMinutes(15)) {
                for (AppointmentTime appointmentTime:appointmentTimes)
                {
                    if(appointmentTime.getUser().getId()==sessionUserId||appointmentTime.getUser().getId()==host.getId()&&
                        appointmentTime.getDate().isEqual(date)&&
                            localTime.plusMinutes(durationUnit-1).isAfter(appointmentTime.getFromTime().minusMinutes(1))&&
                            localTime.isBefore(appointmentTime.getToTime()))
                    {
                        flag=true;
                        break;
                    }
                }
                if(!flag) {
                    times.add(localTime);
                }
                flag=false;
            }
        }
        model.addAttribute("times", times);
        model.addAttribute("sessionUserId", sessionUserId);
        model.addAttribute("eventId", eventId);
        model.addAttribute("date", date);
        return "selectTimePage";
    }

    @GetMapping("/book/{userId}")
    public String book(@PathVariable("userId") int userId, Model model, HttpSession session) {
        int sessionUserId = (int) session.getAttribute("sessionUserId");
        Optional<User> optional = userService.findById(userId);
        User user = optional.get();
        List<Event> events = user.getEvents();
        model.addAttribute("events", events);
        model.addAttribute("sessionUserId", sessionUserId);
        return "/showEvents";
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

    @GetMapping("/appointment/{eventId}/{sessionUserId}/{date}/{time}")
    public String appoint(@PathVariable("sessionUserId") int sessionUserId, @PathVariable("eventId") int eventId, @PathVariable("time") String timeStr, @PathVariable("date") String dateStr, Model model, HttpSession session) {
        Event event = eventService.findById(eventId).get();
        int duration = event.getDurationUnit();

        LocalTime time = LocalTime.parse(timeStr);
        LocalDate date = LocalDate.parse(dateStr);

        AppointmentTime appointmentTime = new AppointmentTime();
        appointmentTime.setDate(date);
        appointmentTime.setFromTime(time);
        appointmentTime.setToTime(time.plusMinutes(duration));

        User host = event.getUser();
        User client = userService.findById(sessionUserId).get();

        host.addAppointmentTime(appointmentTime);
        userService.saveUser(host);

        client.addAppointmentTime(appointmentTime);
        userService.saveUser(client);

        return "redirect:/";
    }
}