package com.example.springsecuritydemo.security;

public enum ApplicationUserPermission {
	STUDENT_READ("student:read"),
	STUDENT_WRITE("student:write"),
	COURSE_READ("course:read"),
	COURSE_WRITE("course:write");
	
	private final String permissions;
	
	private ApplicationUserPermission(String permissions) {
		this.permissions = permissions;
	}

	public String getPermissions() {
		return permissions;
	}
	
}
