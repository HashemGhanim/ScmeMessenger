package com.scme.messenger.model.composite;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Embeddable
public class CourseID implements Serializable {
    @Column(name = "course_id")
    private Integer courseId;
    @Column(name = "module_id")
    private Integer moduleId;
}
