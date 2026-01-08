package net.engineeringdigest.journalApp.service;

import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;

import static org.mockito.Mockito.*;

//@SpringBootTest --->we are commenting it to avoid the component loading and to stop application context
public class UserDetailsServiceImplTest {

    //@Autowired  since application context won't start this becomes null so
    @InjectMocks
    UserDetailsServiceImpl userDetailsService;

    @Mock
    UserRepository userRepository;

    @BeforeEach
    void setUp()
    {
        MockitoAnnotations.initMocks(this);
    }
    @Test
    void loadUserByUserNameTest()
    {
        //in the below line we are not fetching anything from db but just making that method(findByUserName) to return something when it is called
        when(userRepository.findByUserName(ArgumentMatchers.anyString())).thenReturn(User.builder().userName("Rocky").password("Stary").roles(new ArrayList<>()).build());
        UserDetails userDetail =userDetailsService.loadUserByUsername("Rocky");
        Assertions.assertNotNull(userDetail);
    }

}
