package Registration.FormRegistration;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

public class userServiceTest {
    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private UserService userService;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this);
    }
    @Test
    public void testingForSuccessfulUserCreation() {
        User user = new User("Obi.ekwealor@gmail.com", "Obinna", "Ekwealor", "BackEnd Engineer", "Payaza");
        when(userRepository.existsByEmail(anyString())).thenReturn(false);
        when(userRepository.save(any(User.class))).thenReturn(user);
        User createdUser = userService.createUser(user);
        assertNotNull(createdUser);
        assertEquals("Obi.ekwealor@gmail.com", createdUser.getEmail());
        assertEquals("Obinna", createdUser.getFirstName());
        assertEquals("Ekwealor", createdUser.getLastName());
        assertEquals("Backend Engineer", createdUser.getRole());
        assertEquals("Payaza", createdUser.getOrganisation());
    }
    @Test
    public void testForEmailIsAlreadyExistingMethod() {
        User user = new User("Obi.ekwealor@gmail.com", "Obinna", "Ekwealor", "BackEnd Engineer", "Payaza");
        when(userRepository.existsByEmail(anyString())).thenReturn(true);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            userService.createUser(user);
        });

        assertEquals("Email already exists", exception.getMessage());
    }

}
