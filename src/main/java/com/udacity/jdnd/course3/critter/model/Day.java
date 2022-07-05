package com.udacity.jdnd.course3.critter.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.DayOfWeek;

@Entity
public class Day {
    @Id
    private Long id;

    private DayOfWeek day;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public DayOfWeek getDay() {
        return day;
    }

    public void setDay(DayOfWeek day) {
        this.day = day;
    }
}
