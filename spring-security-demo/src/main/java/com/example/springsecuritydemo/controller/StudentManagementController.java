package com.example.springsecuritydemo.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.springsecuritydemo.model.Student;

@RestController
@RequestMapping("management/api/v1/students")
public class StudentManagementController {
	
	private List<Student> students = new ArrayList<Student>();
	
	@GetMapping
	public List<Student> getAllStudents(){
		return students;
	}
	
	@PostMapping
	public ResponseEntity<Student> registerStudent(@RequestBody Student student){
		students.add(student);
		ResponseEntity<Student> response = new ResponseEntity<Student>(HttpStatus.CREATED);
		return response;
	}
	
	@PutMapping(path = "{studentId}")
	public ResponseEntity<Student> updateStudent(@PathVariable Long studentId, @RequestBody Student updatedStudent){
		boolean isStudentExist = students.stream().anyMatch( s -> s.getId() == studentId);
		int index = (int) (long) studentId;
		if(isStudentExist) {
			Student student = students.get(index-1);
			student.setName(updatedStudent.getName());
		}
		
		ResponseEntity<Student> response = new ResponseEntity<Student>(HttpStatus.CREATED);
		return response;
	}
	
	@DeleteMapping( path = "{studentId}" )
	public ResponseEntity<Student> deleteStudent(@PathVariable long studentId){
		boolean isStudentExist = students.stream().anyMatch( s -> s.getId() == studentId);
		ResponseEntity<Student> response = new ResponseEntity<Student>(HttpStatus.CREATED);
		int index = (int) (long) studentId;
		if(isStudentExist) {
			students.remove(index-1);
		}
		return response;
	}
}
