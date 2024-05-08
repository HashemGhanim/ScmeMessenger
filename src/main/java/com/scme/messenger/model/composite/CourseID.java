package com.scme.messenger.model.composite;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@Embeddable
public class CourseID implements Serializable {
    @Column(name = "course_id")
    private String courseId;
    @Column(name = "module_id")
    private String moduleId;
}
