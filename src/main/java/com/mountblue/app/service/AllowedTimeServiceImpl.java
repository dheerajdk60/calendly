package com.mountblue.app.service;

import com.mountblue.app.model.AllowedTime;
import com.mountblue.app.repository.AllowedTimeRepository;
import com.mountblue.app.specification.AllowedTimeSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AllowedTimeServiceImpl implements AllowedTimeService{

    @Autowired
    private AllowedTimeRepository allowedTimeRepository;
    @Override
    public void deleteByEventId(int eventId) {
        Specification<AllowedTime> specification= AllowedTimeSpecification.findAllowedTimeByEventId(eventId);
        List<AllowedTime> deleteEvent= allowedTimeRepository.findAll(specification);
        allowedTimeRepository.deleteAll(deleteEvent);
    }
}
