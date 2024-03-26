package com.scme.messenger.constants;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import static com.scme.messenger.constants.Permissions.*;

@RequiredArgsConstructor
public enum Role {
	DOCTOR(
			Set.of(
					DOCTOR_SETTINGS,
					DOCTOR_REJECT)),
	STUDENT(Collections.emptySet());

	@Getter
	private final Set<Permissions> permissions;

	public List<GrantedAuthority> getAuthorities() {
		List<GrantedAuthority> authorities = getPermissions().stream()
				.map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
				.collect(Collectors.toList());

		authorities.add(new SimpleGrantedAuthority("ROLE_" + this.name()));

		return authorities;
	}
}
