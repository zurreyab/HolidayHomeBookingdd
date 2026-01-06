package com.holidaybookingproject.HolidayHomeBooking.repository;

import com.holidaybookingproject.HolidayHomeBooking.entity.Role;
import com.holidaybookingproject.HolidayHomeBooking.entity.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, RoleName> {}
