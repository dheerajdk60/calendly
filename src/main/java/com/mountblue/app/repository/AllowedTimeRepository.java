package com.mountblue.app.repository;

import com.mountblue.app.model.AllowedTime;
import com.mountblue.app.model.AppointmentTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface AllowedTimeRepository extends JpaRepository<AllowedTime,Integer> , JpaSpecificationExecutor<AllowedTime> {
}
