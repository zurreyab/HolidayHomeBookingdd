package com.holidaybookingproject.HolidayHomeBooking.controller;

import com.holidaybookingproject.HolidayHomeBooking.dto.JwtResponse;
import com.holidaybookingproject.HolidayHomeBooking.dto.LoginRequest;
import com.holidaybookingproject.HolidayHomeBooking.dto.SignupRequest;
import com.holidaybookingproject.HolidayHomeBooking.entity.RoleName;
import com.holidaybookingproject.HolidayHomeBooking.entity.User;
import com.holidaybookingproject.HolidayHomeBooking.repository.RoleRepository;
import com.holidaybookingproject.HolidayHomeBooking.repository.UserRepository;
import com.holidaybookingproject.HolidayHomeBooking.security.JwtUtil;
import jakarta.validation.Valid;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import com.github.benmanes.caffeine.cache.Cache;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserRepository userRepo;
    private final RoleRepository roleRepo;
    private final PasswordEncoder encoder;
    private final AuthenticationManager authManager;
    private final JwtUtil jwtUtil;

    private final Cache<String, Boolean> jwtBlacklist;

    public AuthController(UserRepository userRepo,
                          RoleRepository roleRepo,
                          PasswordEncoder encoder,
                          AuthenticationManager authManager,
                          JwtUtil jwtUtil, Cache<String, Boolean> jwtBlacklist) {
        this.userRepo = userRepo;
        this.roleRepo = roleRepo;
        this.encoder = encoder;
        this.authManager = authManager;
        this.jwtUtil = jwtUtil;
        this.jwtBlacklist = jwtBlacklist;
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@Valid @RequestBody SignupRequest req) {
        if (userRepo.existsByEmail(req.email()))
            return ResponseEntity.badRequest().body("Email already in use");

        var user = new User(
                req.name(),
                req.username(),
                req.email(),
                encoder.encode(req.password()),req.address());
             System.out.println("   ---------------   "+req.role());
             if(req.role().equals("ADMIN")){
                 user.getRoles().add(roleRepo.getReferenceById(RoleName.ADMIN));
             }else{
                 user.getRoles().add(roleRepo.getReferenceById(RoleName.USER));
             }

        userRepo.save(user);
        return ResponseEntity.ok("User registered");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest req) {

try {
    Authentication authenticationf= authManager.authenticate(new UsernamePasswordAuthenticationToken(req.username(), req.password()));

        var user = userRepo.findByUsername(req.username()).orElseThrow();
        var roleNames = user.getRoles().stream().map(r -> r.getName().name()).collect(Collectors.toSet());
        String token = jwtUtil.generate(user.getUsername(), roleNames);


           /*return new JwtResponse(token, Long.parseLong(System.getProperty("security.jwt.expiration",
                   "900000")),
                   user.getUsername(),
                   roleNames);*/

        return ResponseEntity.ok(new JwtResponse(token, Long.parseLong(System.getProperty("security.jwt.expiration",
                "900000")),
                user.getUsername(),
                roleNames));

}


catch (Exception e){
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Wrong User name and Password");
}


    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String header) {

        if (header != null && header.startsWith("Bearer ")) {
            String token = header.substring(7);

            jwtBlacklist.put(token, Boolean.TRUE);   // mark as blacklisted
            return ResponseEntity.ok("No");
        }else {
            return ResponseEntity.ok("Logged out");
        }

    }
}
