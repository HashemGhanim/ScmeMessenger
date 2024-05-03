package com.scme.messenger.model;

import jakarta.persistence.*;

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
@Table(name = "image_table")
public class Image extends BaseEntity{

    @Id
    @Column(name = "user_id")
    private String id;

    private String filename;

    private String mime_type;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    private byte[] data;

    @OneToOne
    @JoinColumn(name = "id", referencedColumnName = "user_id", updatable = false , insertable = false)
    private User user;
}
