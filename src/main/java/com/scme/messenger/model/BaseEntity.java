package com.scme.messenger.model;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@MappedSuperclass
@Getter
@Setter
@ToString
@EntityListeners(AuditingEntityListener.class)
public class BaseEntity {

	@CreatedDate
	@Column(updatable = false)
	private LocalDateTime created_at;

	@CreatedBy
	@Column(updatable = false)
	private String created_by;

	@LastModifiedDate
	@Column(insertable = false)
	private LocalDateTime updated_at;

	@LastModifiedBy
	@Column(insertable = false)
	private String updated_by;
}
