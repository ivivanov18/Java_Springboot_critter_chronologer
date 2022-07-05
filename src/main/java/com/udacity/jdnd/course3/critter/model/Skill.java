package com.udacity.jdnd.course3.critter.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import java.util.List;

@Entity
public class Skill {
    @Id
    private Long id;

    private EmployeeSkill type;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public EmployeeSkill getType() {
        return type;
    }

    public void setType(EmployeeSkill type) {
        this.type = type;
    }
}
