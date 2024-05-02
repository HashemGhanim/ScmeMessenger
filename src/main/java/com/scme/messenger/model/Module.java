package com.scme.messenger.model;

import jakarta.persistence.*;
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
    @Id
    @Column(name = "module_id")
    private String moduleId;

    private String name;

    @OneToMany(mappedBy = "module", cascade = CascadeType.ALL , orphanRemoval = true)
    private Set<Course> courses;

}
