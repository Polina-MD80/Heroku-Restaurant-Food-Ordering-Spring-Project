package softuni.restaurant.web.employees;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import softuni.restaurant.model.entity.UserEntity;
import softuni.restaurant.model.entity.enums.RoleEnum;
import softuni.restaurant.repository.UserRepository;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@SpringBootTest
@AutoConfigureMockMvc
class StatsControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    private UserEntity testUser;

    @BeforeEach
    void setUp() {
        testUser = initUser();
        userRepository.save(testUser);
    }

    @AfterEach
    void tearDown() {

        userRepository.delete(testUser);
    }

    @Test
    @WithMockUser(value = "testUser", username = "test", roles = "ADMIN")
    void statPageOpens() throws Exception {
        mockMvc
                .perform(get("/terminal/stats"))
                .andExpect(status().isOk())
                .andExpect(view().name("stats"));
    }


    private UserEntity initUser() {
        return new UserEntity().setUsername("test").setPassword(passwordEncoder.encode("test"))
                .setActive(true).setEmail("test@test.com").setRole(RoleEnum.ADMIN);
    }

}