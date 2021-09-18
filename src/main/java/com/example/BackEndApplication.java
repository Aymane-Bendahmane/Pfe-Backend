package com.example;

import com.example.Entites.Article;
import com.example.Entites.Rating;
import com.example.GenerateCSV.GeneCsv;
import com.example.Repositories.ArticleRepository;
import com.example.Repositories.RatingRepository;
import com.example.Service.initData;
import com.example.Service.initDataImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@SpringBootApplication
public class BackEndApplication implements CommandLineRunner {
    @Autowired
    initDataImp i;
    @Autowired
    RatingRepository ratingRepository;
    @Autowired
    GeneCsv geneCsv;
    List<String[]> dataLines = new ArrayList<>();

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
        System.out.println(i.initRating());
        GeneCsv geneCsv = new GeneCsv();
        List<Rating> ratings = ratingRepository.findAll();

        dataLines.add(new String[]{"id", "id_user", "id_article", "rating","Article"});
        ratings.forEach(rating -> {
            dataLines.add(new String[]
                    {rating.getId() + "", rating.getUserr().getIdU() + "", rating.getArticle().getId() + "", rating.getStare() + "",rating.getArticle().getArtdesignation()});
        });
        geneCsv.givenDataArray_whenConvertToCSV_thenOutputCreated(dataLines);
    }
}
