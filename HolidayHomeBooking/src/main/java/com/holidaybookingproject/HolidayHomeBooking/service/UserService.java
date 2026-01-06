package com.holidaybookingproject.HolidayHomeBooking.service;

import com.holidaybookingproject.HolidayHomeBooking.entity.RoleName;
import com.holidaybookingproject.HolidayHomeBooking.entity.User;
import com.holidaybookingproject.HolidayHomeBooking.exception.UserNotFoundException;
import com.holidaybookingproject.HolidayHomeBooking.repository.RoleRepository;
import com.holidaybookingproject.HolidayHomeBooking.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.holidaybookingproject.HolidayHomeBooking.dto.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserService {

    private  final UserRepository userRepo;
    private final RoleRepository roleRepo;
    private  final PasswordEncoder encoder;
    private  final RestTemplate  restTemplate=new RestTemplate();
    private Logger logger= LoggerFactory.getLogger(UserService.class);
  @Cacheable(cacheNames = "username",key ="#id")
    public User getByUsername(String username) {

       String user =restTemplate.getForObject("http://localhost:8093/hotels/hotluser/vi",String.class);

         return userRepo.findByUsername(user)
                 .orElseThrow(() -> new UserNotFoundException("User Not Found " + username));



    }

    @Transactional
    public User updateProfile(String username, UpdateProfileRequest req) {
        var user = getByUsername(username);

        if(user!=null){
            user.setName(req.getName());
            user.setUsername(req.getUsername());
            user.setEmail(req.getEmail());
            return user;
        }
        throw  new UserNotFoundException("User Not Found with username "+username);

    }

    @Transactional
    public void changePassword(String username, ChangePasswordRequest req) {
        var user = getByUsername(username);
        if (!encoder.matches(req.getOldPassword(), user.getPasswordHash()))
            throw new IllegalArgumentException("Old password incorrect");
        user.setPasswordHash(encoder.encode(req.getNewPassword()));
    }

    @Transactional
    public void promoteToAdmin(Long id) {
        var user = userRepo.findById(id).orElseThrow();
        user.getRoles().add(roleRepo.getReferenceById(RoleName.ADMIN));
    }

    public void deleteUser(Long  id) {
      //  User user = userRepo.findById(id).orElseThrow();
        if(id != null){
            userRepo.deleteById(id);
        }
        logger.info("deleteUser      "+id);

    }

    @Transactional(readOnly = true)
    public List<User> listAll() {
        return userRepo.findAll();
    }
}
