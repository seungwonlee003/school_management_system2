package com.example.demo.service;

import com.example.demo.dto.LoginResponse;
import com.example.demo.dto.SignRequest;
import com.example.demo.model.Authority;
import com.example.demo.model.User;
import com.example.demo.repository.AuthorityRepository;
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


@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final JwtProvider jwtUtil;

    private final AuthenticationManager authenticationManager;

    private final CustomUserDetailsService customUserDetailsService;

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final AuthorityRepository authorityRepository;

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

    public void register(SignRequest request) throws Exception{
        try {
            User user = User.builder().name(request.getName()).password(passwordEncoder.encode(request.getPassword()))
                            .build();
            userRepository.save(user);
            Authority authority = new Authority();
            authority.setName("ROLE_ADMIN");
            authority.setUser(user);
            authorityRepository.save(authority);
            // have logic where, based on the roll, student/teacher entity of the user will be created
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new Exception();
        }
    }
}
