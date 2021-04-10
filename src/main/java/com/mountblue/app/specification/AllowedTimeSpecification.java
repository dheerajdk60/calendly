package com.mountblue.app.specification;

import com.mountblue.app.model.AllowedTime;
import com.mountblue.app.model.Event;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.JoinType;

public class AllowedTimeSpecification {

    public static Specification<AllowedTime> findAllowedTimeByEventId(int eventId) {
        return ((root,criteriaQuery,criteriaBuilder)->{

            return  criteriaBuilder.equal(root.join("event", JoinType.INNER).get("id"),eventId);
        });
    }
}
