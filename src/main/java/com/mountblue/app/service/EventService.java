package com.mountblue.app.service;

import com.mountblue.app.model.Event;
import com.mountblue.app.model.User;
import com.mountblue.app.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


public interface EventService {


    void saveEvent(Event event);

    List<Event> findEventByUserId(int userId);

    Optional<Event> findById(int eventId);

    void deleteById(int eventId);
}
