package com.example.back_end;

import com.example.Repositories.ArticleRepository;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BackEndApplicationTests {
    @Autowired
    ArticleRepository articleRepository;

    @BeforeAll
    void beforeEverythings() {
        System.out.println("***************Starting Test***************");
    }

    @AfterAll
    @DisplayName("***************Starting Test***************")
    void afterEverythings() {
        System.out.println("***************Finished *******************");
    }

    @Test
    void contextLoads() {
        assertEquals("The equalibruim  : ", "a*ymane", "aymane");
    }

}
