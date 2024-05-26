package com.scme.messenger.model;

import java.util.List;
import java.util.Set;

import java.io.Serializable;

import jakarta.persistence.*;
import org.hibernate.annotations.ColumnDefault;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.scme.messenger.model.composite.CourseID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "course")
public class Course extends BaseEntity implements Serializable {

    @EmbeddedId
    private CourseID courseID;

    private String name;

    @JsonIgnore
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "course_user", joinColumns = {
            @JoinColumn(name = "course_id"),
            @JoinColumn(name = "module_id")
    }, inverseJoinColumns = @JoinColumn(name = "user_id"))
    private Set<User> users;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "module_id", referencedColumnName = "module_id", insertable = false, updatable = false)
    private Module module;

    @JsonIgnore
    @OneToMany(mappedBy = "course")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Set<GroupMessage> groupMessages;


    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "instructor_id", referencedColumnName = "user_id")
    private User instructor;

    private boolean stopConversation = false;
    private String secretKey;

}
