package com.mountblue.app.service;

import com.mountblue.app.model.Event;
import com.mountblue.app.model.User;
import com.mountblue.app.repository.EventRepository;
import com.mountblue.app.specification.EventSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EventServiceImpl implements EventService{

    @Autowired
    EventRepository eventRepository;

    @Override
    public void saveEvent(Event event) {
        eventRepository.save(event);
    }

    @Override
    public List<Event> findEventByUserId(int userId) {
        Specification<Event> specification= EventSpecification.findEventByUserId(userId);
        List<Event> events= eventRepository.findAll(specification);
        return events;
    }

    @Override
    public Optional<Event> findById(int eventId) {
        return eventRepository.findById(eventId);
    }
}
