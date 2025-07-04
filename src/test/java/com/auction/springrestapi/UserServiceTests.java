package com.auction.springrestapi;

import com.auction.springrestapi.Repo.UserRepo;
import com.auction.springrestapi.Service.UserService;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)  // Only Mockito extension needed
class UserServiceTests {

    @Mock
    private UserRepo userRepository;

    @InjectMocks
    private UserService userService;

    @ParameterizedTest
    @CsvSource({
            "1,1,2",
            "2,10,12",
            "3,3,6"
    })
    void testAddition(int a, int b, int expected) {
        assertEquals(expected, a + b);
    }
}