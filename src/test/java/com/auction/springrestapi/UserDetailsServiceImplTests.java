package com.auction.springrestapi;

import com.auction.springrestapi.Entity.User;
import com.auction.springrestapi.Repo.UserRepo;
import com.auction.springrestapi.UserDetailsImpl.UserDetailsServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

/*@ActiveProfiles("dev")*/
@ExtendWith(MockitoExtension.class) // Modern way to initialize mocks
class UserDetailsServiceImplTests {

    @InjectMocks
    private UserDetailsServiceImpl userDetailsService;

    @Mock
    private UserRepo userRepository;

    // No need for @BeforeEach with @ExtendWith(MockitoExtension.class)

    @Test
    void loadUserByUsernameTest_WhenUserExists() {
        // Given - Create a mock User object
        User mockUser = new User();
        mockUser.setId(1L);
        mockUser.setUserName("ram");
        mockUser.setPassword("password123");
        mockUser.setRoles(List.of("USER"));
        // Set other required fields as needed

        // Mock the repository to return the user
        when(userRepository.findByUserName("ram")).thenReturn(Optional.of(mockUser));

        // When
        UserDetails userDetails = userDetailsService.loadUserByUsername("ram");

        // Then
        assertNotNull(userDetails);
        assertEquals("ram", userDetails.getUsername());
        assertEquals("password123", userDetails.getPassword());

        // Verify the repository method was called
        verify(userRepository, times(1)).findByUserName("rakesh");
    }

    @Test
    void loadUserByUsernameTest_WhenUserNotFound() {
        // Given - Mock repository to return null
        when(userRepository.findByUserName("nonexistent")).thenReturn(null);

        // When & Then - Should throw UsernameNotFoundException
        assertThrows(NullPointerException.class, () -> {
            userDetailsService.loadUserByUsername("nonexistent");
        });

        verify(userRepository, times(1)).findByUserName("nonexistent");
    }

    // If your repository returns Optional<User> instead of User
    @Test
    void loadUserByUsernameTest_WithOptional_WhenUserExists() {
        // Given
        User mockUser = new User();
        mockUser.setUserName("ram");
        mockUser.setPassword("password123");
        mockUser.setRoles(List.of("USER"));

        // If your repo method returns Optional<User>
        when(userRepository.findByUserName("ram")).thenReturn(Optional.of(mockUser));
        // When
        UserDetails userDetails = userDetailsService.loadUserByUsername("ram");

        // Then
        assertNotNull(userDetails);
        assertEquals("ram", userDetails.getUsername());
    }
}