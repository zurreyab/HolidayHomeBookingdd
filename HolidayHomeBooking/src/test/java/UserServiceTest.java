import com.holidaybookingproject.HolidayHomeBooking.dto.UpdateProfileRequest;
import com.holidaybookingproject.HolidayHomeBooking.entity.User;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.holidaybookingproject.HolidayHomeBooking.exception.UserNotFoundException;
import com.holidaybookingproject.HolidayHomeBooking.repository.UserRepository;
import com.holidaybookingproject.HolidayHomeBooking.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;


//@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class UserServiceTest {

    @Test
    void contextLoads() {
    }



}


