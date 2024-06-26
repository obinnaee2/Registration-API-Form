package Registration.FormRegistration;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.hamcrest.Matchers.is;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
public class userControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Test
    public void testingEndpointWithValidData()throws Exception{
        User user = new User("Obi.ekwealor@gmail.com", "Obinna", "Ekwealor", "BackEnd Engineer", "Payaza");
        when(userService.createUser(any(User.class))).thenReturn(user);
        mockMvc.perform(post("/api/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"email\":\"Obi.ekwealor@gmail.com\",\"firstName\":\"Obinna\",\"lastName\":\"Ekwealor\",\"role\":\"Backend Engineer\",\"organisation\":\"Payaza\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.email", is("Obi.ekwealor@gmail.com")))
                .andExpect(jsonPath("$.firstName", is("Obinna")))
                .andExpect(jsonPath("$.lastName", is("Ekwealor")))
                .andExpect(jsonPath("$.role", is("Backend Engineer")))
                .andExpect(jsonPath("$.organisation", is("PAYAZA")));
    }
    @Test
    public void testingEndPointWithInvalidData() throws Exception {
        mockMvc.perform(post("/api/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"email\":\"Ekwealor1.com\",\"firstName\":\"Obinna\",\"lastName\":\"Ekwealor\",\"role\":\"Backend Engineer\",\"organisation\":\"PAYAZA\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors.email", is("Email should be valid")));
    }
    @Test
    public void testingEndPointWithEmailAlreadyExists() throws Exception {
        when(userService.createUser(any(User.class))).thenThrow(new RuntimeException("Email already exists"));

        mockMvc.perform(post("/api/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"email\":\"Obi.ekwealor@gmail.com\",\"firstName\":\"Obinna\",\"lastName\":\"Ekwealor\",\"role\":\"Backend Engineer\",\"organisation\":\"PAYAZA\"}"))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.error", is("An unexpected error occurred: Email already exists")));
    }
    @Test
    public void testingEndPointWithMissingFields() throws Exception {
        mockMvc.perform(post("/api/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"email\":\"Obi.ekwealor@gmail.com\",\"firstName\":\"\",\"lastName\":\"Ekwealor\",\"role\":\"Backend Engineering\",\"organisation\":\"PAYAZA\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors.firstName", is("First name is required")));
    }
}
