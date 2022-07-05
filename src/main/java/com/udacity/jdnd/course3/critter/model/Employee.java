package com.udacity.jdnd.course3.critter.model;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
public class Employee extends ServiceUser {
    @OneToMany
    private List<Skill> skills;

    @OneToMany
    private List<Day> daysAvailable;

    public List<Skill> getSkills() {
        return skills;
    }

    public void setSkills(List<Skill> skills) {
        this.skills = skills;
    }

    public List<Day> getDaysAvailable() {
        return daysAvailable;
    }

    public void setDaysAvailable(List<Day> daysAvailable) {
        this.daysAvailable = daysAvailable;
    }
}
