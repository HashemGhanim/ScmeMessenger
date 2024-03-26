package com.scme.messenger.constants;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Permissions {
	DOCTOR_SETTINGS("doctor:group:settings"),
	DOCTOR_REJECT("doctor:group:reject");

	@Getter
	private final String permission;
}
