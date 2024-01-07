package com.example.demo.controller;

import lombok.Getter;
import org.springframework.expression.spel.ast.Assign;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/assignment")
public class AssignmentController {
    // see all assignments sorted by recent date/deadline
    // see all assignments by subject sorted by recent date/deadline
    // create assignment
    // edit assignment
    // delete assignment
    @GetMapping("/hi")
    public String hi(){
        return "accessed";
    }
}
