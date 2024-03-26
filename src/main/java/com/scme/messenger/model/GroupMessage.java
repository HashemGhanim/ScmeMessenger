package com.scme.messenger.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.scme.messenger.model.composite.GroupMessageID;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinColumns;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;;

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

}
