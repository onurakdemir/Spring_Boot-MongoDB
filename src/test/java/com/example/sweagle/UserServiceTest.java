package com.example.sweagle;

import com.example.sweagle.model.User;
import com.example.sweagle.repository.UserRepository;
import com.example.sweagle.service.UserService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class UserServiceTest {

    private User user1;

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserService userService;

    @BeforeEach
    void setUp() {
        user1 = userRepository.save(new User("User_1"));
    }

    @AfterEach
    void tearDown() {
        userRepository.deleteAll();
    }

    @Test
    public void testIfUserExist() {
       Optional<User> retUser = userService.userExists(user1.getId());
       assertThat(retUser.isPresent());
       assertThat(retUser.get().getId().endsWith(user1.getId()));
    }

    @Test
    @DirtiesContext
    public void testUserNotExistWithInvalidParameter() {
        Optional<User> ret = userService.userExists("user2");
        assertThat(!ret.isPresent());
    }

}
