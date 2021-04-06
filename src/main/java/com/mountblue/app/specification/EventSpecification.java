package com.mountblue.app.specification;

import com.mountblue.app.model.Event;
import org.springframework.data.jpa.domain.Specification;

public class EventSpecification {

    public static Specification<Event> findEventByUserId(int userId) {
        return ((root,criteriaQuery,criteriaBuilder)->{

            return  criteriaBuilder.equal(root.get("user"),userId);
        });
    }
}
