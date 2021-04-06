package com.mountblue.app.repository;

import com.mountblue.app.model.AppointmentTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface AppointmentTimeRepository extends JpaRepository<AppointmentTime,Integer> , JpaSpecificationExecutor<AppointmentTime> {
}
