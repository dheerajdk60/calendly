package com.mountblue.app.specification;

import com.mountblue.app.model.AppointmentTime;
import com.mountblue.app.model.Event;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.JoinType;
import java.time.LocalDate;
import java.time.LocalTime;

public class AppointmentTimeSpecification {

        public static Specification<AppointmentTime> findAppointmentsByUserIdForSpecification(int userId) {
            return ((root,criteriaQuery,criteriaBuilder)->{
                return  criteriaBuilder.equal(root.get("user"),userId);
            });
        }
    public static Specification<AppointmentTime> isDate(LocalDate date) {
        return ((root,criteriaQuery,criteriaBuilder)->{
            return  criteriaBuilder.equal(root.get("date"),date);
        });
    }

    public static Specification<AppointmentTime> isFromTime(LocalTime fromTime) {
        return ((root,criteriaQuery,criteriaBuilder)->{
            return  criteriaBuilder.equal(root.get("fromTime"),fromTime);
        });
    }
    public static Specification<AppointmentTime> isToTime(LocalTime toTime) {
        return ((root,criteriaQuery,criteriaBuilder)->{
            return  criteriaBuilder.equal(root.get("toTime"),toTime);
        });
    }
    public static Specification<AppointmentTime> isEventId(int eventId) {
        return ((root,criteriaQuery,criteriaBuilder)->{
            return  criteriaBuilder.equal(root.join("event", JoinType.INNER).get("id"),eventId);
        });
    }

}
