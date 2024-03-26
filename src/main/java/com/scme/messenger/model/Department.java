package com.scme.messenger.model;

import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
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
@Table(name = "department")
public class Department extends BaseEntity {
    // Attributes
    @Id
    @Column(name = "dep_id")
    private Integer depId;

    private String name;

    @OneToOne
    @JoinColumn(name = "manager_id", referencedColumnName = "user_id")
    private User user;

    @OneToMany(mappedBy = "department")
    private Set<Module> modules;

}
