package com.mountblue.app.specification;

import com.mountblue.app.model.AppointmentTime;
import com.mountblue.app.model.Event;
import org.springframework.data.jpa.domain.Specification;

public class AppointmentTimeSpecification {

        public static Specification<AppointmentTime> findAppointmentsByUserId(int userId) {
            return ((root,criteriaQuery,criteriaBuilder)->{

                return  criteriaBuilder.equal(root.get("user"),userId);
            });
        }

}
