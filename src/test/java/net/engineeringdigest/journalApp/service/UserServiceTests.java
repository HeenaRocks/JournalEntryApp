package net.engineeringdigest.journalApp.service;

import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.repository.UserRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("dev")
public class UserServiceTests
{
    @Autowired
    UserRepository userRepository;

    @Autowired
    UserService userService;

//    @BeforeEach
//    public  void runsBeforeEachtest(){
//
//    }
//
//    @BeforeAll
//    @AfterEach
//    @AfterAll


    @ParameterizedTest
    @ValueSource(strings = {
        "Rock1",
            "Kousar",
         "Heena"
    })
    public void testFindByUserName(String name)
    {
      assertNotNull(userRepository.findByUserName(name));

    }

    @ParameterizedTest
    @ArgumentsSource(UserArgumentProvider.class)
    public void testSaveNewUser(User name)
    {
        assertTrue(userService.saveNewUser(name));

    }

    @Disabled
    @ParameterizedTest
    @CsvSource({
            "1,1,2",
            "2,10,12",
            "1,5,8"
    })
    public void test(int a, int b, int expected)
    {
        assertEquals(expected, a , b);
    }


    //  @Disabled  //makes this test disable means this test won't run
    @Test
    public void someTestWays()
    {
        User user= userRepository.findByUserName("Rock1");
        assertTrue(!user.getJournalEntries().isEmpty());
        //        assertEquals(4,2+2);
//        assertNotNull(userRepository.findByUserName("Rock1"));
//        assertTrue(3>1);
        // assertTrue(userRepository.deleteByUserName("Heena1"));
    }
}
