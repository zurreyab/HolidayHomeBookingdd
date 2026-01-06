package com.holidaybookingproject.HolidayHomeBooking.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;


@Entity
@Table(name = "role")
public class Role {


    @Id
    @Enumerated(EnumType.STRING)
    private RoleName name;

    public Role() {
    }

    public Role(RoleName name) {
        this.name = name;
    }
    public RoleName getName() {

        return name;
    }

    public void setName(RoleName name) {

        this.name = name;
    }





}
