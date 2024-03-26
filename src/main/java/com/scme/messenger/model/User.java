package com.scme.messenger.model;

import java.util.Collection;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.scme.messenger.constants.Role;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
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
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user_table")
public class User extends BaseEntity implements UserDetails {

	// Attributes
	@Id
	@Column(name = "user_id")
	private String userId;

	private String name;

	private String email;

	private String password;

	@Enumerated(EnumType.STRING)
	private Role role;

	// This attribute will be ignored
	@JsonIgnore
	@OneToOne(mappedBy = "user", fetch = FetchType.LAZY)
	private Department department;

	@ManyToMany(mappedBy = "users", fetch = FetchType.LAZY)
	private Set<Course> courses;

	// This attribute will be ignored
	@JsonIgnore
	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
	private Set<GroupMessage> groupMessages;

	@OneToMany(mappedBy = "sender")
	private Set<Chat> sChats;

	@OneToMany(mappedBy = "recepient")
	private Set<Chat> rChats;

	@JsonIgnore
	@OneToMany(mappedBy = "senderMessage")
	private Set<ChatMessage> sMessages;

	@JsonIgnore
	@OneToMany(mappedBy = "recepientMessage")
	private Set<ChatMessage> rMessages;

	// Methods

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return role.getAuthorities();
	}

	@Override
	public String getPassword() {
		return this.password;
	}

	@Override
	public String getUsername() {
		return this.userId;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

}
