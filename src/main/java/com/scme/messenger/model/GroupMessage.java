package com.scme.messenger.model;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "group_message")
public class GroupMessage extends BaseEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "message_id")
    private UUID messageId;

    @Column(name = "course_id")
    private String courseId;

    @Column(name = "module_id")
    private String moduleId;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "sender_id", referencedColumnName = "user_id")
    private User user;

    @JsonIgnore
    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "course_id", referencedColumnName = "course_id", insertable = false, updatable = false),
            @JoinColumn(name = "module_id", referencedColumnName = "module_id", insertable = false, updatable = false)
    })
    private Course course;

    private String content;
    private Date timestamp;

    private boolean pinned = false;

    @OneToOne(mappedBy = "groupMessage", fetch = FetchType.EAGER,cascade = CascadeType.ALL , orphanRemoval = true)
    private GroupMessageAttachment attachment;

}
