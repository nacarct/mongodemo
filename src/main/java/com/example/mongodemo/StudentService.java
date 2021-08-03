package com.example.mongodemo;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class StudentService {
    private StudentRepository studentRepository;

    public List<Student> getAllStudents(){
        return this.studentRepository.findAll();
    }
}
