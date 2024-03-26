package com.scme.messenger.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "module_table")
public class Module extends BaseEntity {
    // Attributes
    @Id
    @Column(name = "module_id")
    private Integer moduleId;

    private String name;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "dep_id", referencedColumnName = "dep_id")
    private Department department;

    @OneToMany(mappedBy = "module")
    private Set<Course> courses;

}
