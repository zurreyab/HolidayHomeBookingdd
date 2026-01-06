package com.holidaybookingproject.HolidayHomeBooking.controller;

import com.holidaybookingproject.HolidayHomeBooking.dto.ChangePasswordRequest;
import com.holidaybookingproject.HolidayHomeBooking.dto.UpdateProfileRequest;
import com.holidaybookingproject.HolidayHomeBooking.entity.User;
import com.holidaybookingproject.HolidayHomeBooking.service.UserService;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")

public class UserController {
    private Logger logger= LoggerFactory.getLogger(UserController.class);

    public UserController(UserService service) {
        this.service = service;
    }

    private final UserService service;

    /* ---------- SELF OPERATIONS ---------- */
    int retrycount=1;
    @GetMapping("/user/{username}")
    //@CircuitBreaker(name = "ratingHotelBreaker", fallbackMethod = "ratingHotelFallbackExecutor")
    //@RateLimiter(name = "userRateLimiter", fallbackMethod = "ratingHotelFallbackExecutor")
    @Retry(name = "ratingHotelService", fallbackMethod = "ratingHotelFallbackExecutor")
    public ResponseEntity<User> getuser(@PathVariable("username") String username) {   // simple echo

        logger.info("Retry =============   "+retrycount);
        retrycount++;
        User user= service.getByUsername(username);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }
    @DeleteMapping("/delete/{id}")
    //@PreAuthorize("hasRole('ROLE_ADMIN') or (hasRole('ROLE_USER') and #email == principal.username)")
    public ResponseEntity<String> deleteUser(@PathVariable("id") Long id) {
        try{
            service.deleteUser(id);
            return ResponseEntity.ok("User deleted successfully");
        }catch (UsernameNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("" +
                    "error deleting user");
        }
    }

    public ResponseEntity<User> ratingHotelFallbackExecutor(String username,Throwable e) {

        //All data is dummy if any service is down and request hiting on server using builder we can set all dummy data.


        User user =new  User();
        user.setId(2l);
        user.setUsername("jahmad-----------------------");


        return new ResponseEntity<>(user, HttpStatus.OK);
    }


}
