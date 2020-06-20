package com.example.springsecuritydemo.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.springsecuritydemo.model.Student;

@RestController
@RequestMapping("api/v1/students")
public class StudentController {

	private static List<Student> STUDENTS = Arrays.asList(new Student(1, "Aditya"), new Student(2, "Rahul"),
			new Student(3, "Prabhakar"));

	@GetMapping(path = "{studentId}")
	public Student getStudent(@PathVariable("studentId") Long studentId) {
		return STUDENTS.stream()
				.filter(student -> student.getId() == studentId)
				.findFirst()
				.orElseThrow(() -> new IllegalArgumentException());

	}
}
