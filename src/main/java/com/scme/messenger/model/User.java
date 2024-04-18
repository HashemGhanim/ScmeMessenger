package com.scme.messenger.model;

import java.util.Collection;
import java.util.Set;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.scme.messenger.constants.Role;

@ToString
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user_table")
public class User extends BaseEntity implements UserDetails {

	@Id
	@Column(name = "user_id")
	private String userId;

	private String name;

	@Column(unique = true)
	private String email;

	private String password;

	private boolean twoStepVerify = false;

	@Enumerated(EnumType.STRING)
	private Role role;

	@OneToOne(mappedBy = "user")
	private Image image;

	@ManyToMany(mappedBy = "users", fetch = FetchType.LAZY)
	private Set<Course> courses;

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
