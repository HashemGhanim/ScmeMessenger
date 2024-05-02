package com.scme.messenger.model;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.scme.messenger.model.composite.GroupMessageID;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "group_message")
public class GroupMessage extends BaseEntity implements Serializable {

    @EmbeddedId
    private GroupMessageID groupMessageID;

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

}
