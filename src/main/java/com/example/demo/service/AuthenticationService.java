package com.example.demo.service;

import com.example.demo.dto.LoginResponse;
import com.example.demo.dto.SignRequest;
import com.example.demo.model.Authority;
import com.example.demo.model.Student;
import com.example.demo.model.Teacher;
import com.example.demo.model.User;
import com.example.demo.repository.AuthorityRepository;
import com.example.demo.repository.StudentRepository;
import com.example.demo.repository.TeacherRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.security.JwtProvider;
import com.example.demo.service.jwt.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final JwtProvider jwtUtil;

    private final AuthenticationManager authenticationManager;

    private final CustomUserDetailsService customUserDetailsService;

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final AuthorityRepository authorityRepository;

    private final StudentRepository studentRepository;
    private final TeacherRepository teacherRepository;

    public LoginResponse login(SignRequest request) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getName(), request.getPassword()));
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("Incorrect username or password!");
        } catch (DisabledException disabledException) {
            throw new DisabledException("User is not activated");
        }

        final UserDetails userDetails = customUserDetailsService.loadUserByUsername(request.getName());

        final String jwt = jwtUtil.generateToken(userDetails.getUsername());

        return new LoginResponse(jwt);
    }

    @Transactional
    public void register(SignRequest request) throws Exception{
        try {
            User user = User.builder()
                    .name(request.getName())
                    .password(passwordEncoder.encode(request.getPassword()))
                    .build();
            userRepository.save(user);

            // Determine the role and create corresponding entity
            if ("ROLE_STUDENT".equals(request.getRole())) {
                Authority authority = new Authority();
                authority.setName("ROLE_STUDENT");
                authority.setUser(user);
                authorityRepository.save(authority);
                Student student = new Student();
                student.setUser(user);
                studentRepository.save(student);
            } else if ("ROLE_TEACHER".equals(request.getRole())) {
                Authority authority = new Authority();
                authority.setName("ROLE_TEACHER");
                authority.setUser(user);
                authorityRepository.save(authority);
                Teacher teacher = new Teacher();
                teacher.setUser(user);
                teacherRepository.save(teacher);
            } else {
                throw new IllegalArgumentException("Invalid role: " + request.getRole());
            }
        } catch (Exception e) {
            // Log the exception
            System.out.println("Registration failed: " + e.getMessage());
            throw new Exception("Registration failed");
        }
    }
}
