package com.scme.messenger.model;

import java.util.Set;

import java.io.Serializable;

import org.hibernate.annotations.ColumnDefault;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.scme.messenger.model.composite.CourseID;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

    @ColumnDefault(value = "1")
    private Integer members;

    private String imagePath;

    @JsonIgnore
    @ManyToMany
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
    @OneToMany(mappedBy = "course", fetch = FetchType.LAZY)
    private Set<GroupMessage> groupMessages;

}
