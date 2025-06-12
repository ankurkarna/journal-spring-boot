//package com.karna.ankur.Journal.service;
//
//import com.karna.ankur.Journal.Entity.UserEntity;
//import com.karna.ankur.Journal.Repository.UserRepository;
//import com.karna.ankur.Journal.Service.UserService;
//import org.junit.jupiter.api.Disabled;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.params.ParameterizedTest;
//import org.junit.jupiter.params.provider.ArgumentsSource;
//import org.junit.jupiter.params.provider.CsvSource;
//import org.junit.jupiter.params.provider.ValueSource;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//@SpringBootTest
//public class UserServiceTests {
//
//    @Autowired
//    UserService userService;
//
//    @Autowired
//    private UserRepository userRepository;
//
//
//    @ParameterizedTest
//    @ArgumentsSource(UserArgumentProvider.class)
//    public void testCreateUserService(UserEntity userEntity) {
//        assertTrue(userService.saveNewUser(userEntity));
//    }
//
//    /*
//    these are annotations used on method for specific scenarios
//    @beforeAll
//    @afterAll
//     */
//
////    @ParameterizedTest
////    @ValueSource(strings = {
////        "ankur",
////        "ram",
////        "ankit",
////        "faiz"
////    })
////    public void testFindByUserName(String userName) {
////        assertNotNull(userRepository.findByUserName(userName));
////    }
//
//    @Disabled
//    @ParameterizedTest
//    @CsvSource({
//            "3,1,2",
//            "45,1,44",
//            "3,2,4",
//            "3,1,4"
//    })
//    public void test(int a, int b, int expected) {
//        assertEquals(expected, a + b);
//    }
//
//}
