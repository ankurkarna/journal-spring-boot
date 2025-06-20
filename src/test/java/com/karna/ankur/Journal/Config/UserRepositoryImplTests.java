package com.karna.ankur.Journal.Config;

import com.karna.ankur.Journal.Repository.UserRepositoryImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserRepositoryImplTests {
    @Autowired
    private UserRepositoryImpl userRepositoryImpl;

    @Test
    public void testSA(){
        Assertions.assertNotNull(userRepositoryImpl.getUserForSA());
    }
}
