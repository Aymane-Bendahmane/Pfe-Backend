package com.example.Service;

import com.example.Entites.*;
import com.example.Repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

@Service
public class initDataImp implements initData {
    @Autowired
    RatingRepository ratingRepository;
    @Autowired
    ArticleRepository articleRepository;
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    MarqueRepository marqueRepository;
    @Autowired
    UserRepository userRepository;
    Random random;
    @Autowired
    CommandeRepository commandeRepository;

    public String initArticles() {

        random = new Random();
        List<Marque> marqueList = marqueRepository.findAll();
        categoryRepository.findAll().forEach(category -> {

            for (int i = 0; i < 12; i++) {
                articleRepository.save(new Article(null, "Item"
                        + random.nextInt(50), random.nextFloat() + 300,
                        random.nextInt(12), "Description" +
                        random.nextInt(12),
                        "empty.png", marqueList.get(random.nextInt(6)), category,
                        null, null));
            }
        });


        return "Articles initialized !";
    }

    @Override
    public String initCategories() {
        categoryRepository.save(new Category(null, "laptops", null));
        categoryRepository.save(new Category(null, "smartphones", null));
        categoryRepository.save(new Category(null, "cameras", null));
        categoryRepository.save(new Category(null, "Accessories", null));
        return "Categories initialized !";
    }

    @Override
    public String initMarque() {
        marqueRepository.save(new Marque(null, "Hp", null));
        marqueRepository.save(new Marque(null, "Macdonalds", null));
        marqueRepository.save(new Marque(null, "Dell", null));
        marqueRepository.save(new Marque(null, "Asus", null));
        marqueRepository.save(new Marque(null, "Atlas Plastic", null));
        marqueRepository.save(new Marque(null, "Triangle Toys", null));
        return "Marques initialized !";
    }

    @Override
    public String InitUser() {
        for (int i = 0; i < 4; i++) {
            userRepository.save(new Userr(null, "user" + i + "@gmail.com", "user" + i, "user" + i, random.nextBoolean() ? "male" : "female", "user" + i, "prenom" + i, random.nextInt(123456789) + "", "user" + 1 + " lot :" + random.nextInt(), null, null, null));
        }
        return "Users initialized !";
    }

    @Override
    public String initCommands() {
        Userr u = userRepository.findById(1L).get();
        List<Article> articles = new ArrayList<>();
        articles.add(articleRepository.findById(1L).get());
        articles.add(articleRepository.findById(4L).get());
        articles.add(articleRepository.findById(2L).get());
        articles.add(articleRepository.findById(3L).get());
        commandeRepository.save(new Commande(
                null, new Date(), null, "purchases description", articles, u
        ));

        return "Commands initialized !";
    }

    @Override
    public String initRating() {
        Userr u0 = userRepository.getById(1L);
        Userr u1 = userRepository.getById(2L);
        Userr u2 = userRepository.getById(3L);
        Userr u3 = userRepository.getById(4L);

        List<Article> articleList = articleRepository.findAll();

        for (int i = 0; i < 12; i++) {

            ratingRepository.save(new Rating(null, random.nextInt(5), u0, articleList.get(i)));
            ratingRepository.save(new Rating(null, random.nextInt(5), u1, articleList.get(i)));
            ratingRepository.save(new Rating(null, random.nextInt(5), u2, articleList.get(i)));
            ratingRepository.save(new Rating(null, random.nextInt(5), u3, articleList.get(i)));
        }
        return "Rating initialized";
    }
}
