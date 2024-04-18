package com.scme.messenger.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.OneToOne;
import jakarta.persistence.JoinColumn;

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
@Table(name = "image_table")
public class Image extends BaseEntity{

    @Id
    @Column(name = "user_id")
    private String id;

    private String filename;

    private String mime_type;

    private byte[] data;

    @OneToOne
    @JoinColumn(name = "id", referencedColumnName = "user_id", updatable = false , insertable = false)
    private User user;
}
