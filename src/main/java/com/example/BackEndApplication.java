package com.example;

import com.example.Entites.Article;
import com.example.Repositories.ArticleRepository;
import com.example.Service.initData;
import com.example.Service.initDataImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Random;

@SpringBootApplication
public class BackEndApplication implements CommandLineRunner {
    @Autowired
    initDataImp i;

    public static void main(String[] args) {
        SpringApplication.run(BackEndApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println(i.initCategories());
        System.out.println(i.initMarque());
        System.out.println(i.initArticles());
        System.out.println(i.InitUser());
        System.out.println(i.initCommands());
    }
}
