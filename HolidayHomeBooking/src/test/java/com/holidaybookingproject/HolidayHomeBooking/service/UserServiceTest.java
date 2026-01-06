package com.holidaybookingproject.HolidayHomeBooking.service;

import com.holidaybookingproject.HolidayHomeBooking.entity.User;
import com.holidaybookingproject.HolidayHomeBooking.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

import static org.mockito.Mockito.lenient;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @InjectMocks
   RestTemplate restTemplate=new RestTemplate();
    @Mock
    UserRepository userRepo;
    @Mock
    UserService userService;

    @Test
    public void  getByUsername(){
        //String user =restTemplate.getForObject("http://localhost:8093/hotels/hotluser/vi",String.class);
        User us=new User();
        us.setUsername("vi");
        us.setName("zurreyab");
        lenient().when(userRepo.findByUsername("vi")).thenReturn(Optional.of(us));
        Assertions.assertEquals(us.getUsername(),"vi");
        Assertions.assertEquals(us.getName(),"zurreyab");



    }
}
