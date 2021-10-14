package com.example.Controller;

import com.example.Entites.*;

import com.example.Repositories.*;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@Api("Other endpoints beside spring data")
@ApiOperation(value = "", authorizations = { @Authorization(value="jwtToken") })

public class APiController {
    @Autowired
    ArticleRepository articleRepository;
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    CommandeRepository commandeRepository;
    @Autowired
    ProductItemsRepository productItemsRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    RatingRepository ratingRepository;

    @GetMapping(path = "/photoProduct/{id}", produces = MediaType.IMAGE_PNG_VALUE)
    public byte[] getPhoto(@PathVariable("id") Long id) throws IOException {
        //System.out.println(id);
        //System.out.println("+++++++++"+Paths.get(System.getProperty("User.home")));
        Article article = articleRepository.findById(id).get();
        return Files.readAllBytes(Paths.get(System.getProperty("user.home") + "/PfeEcomerce/Articles/" + article.getPhoto()));

    }

    @PostMapping(path = "/uploadPhoto/{id}")
    public void uploadPhoto(MultipartFile file, @PathVariable Long id) throws IOException {
        Article article = articleRepository.findById(id).get();
        article.setPhoto(id + "__" + file.getOriginalFilename());
        Files.write(Paths.get(System.getProperty("user.home") + "/PfeEcomerce/Articles/" + article.getPhoto()), file.getBytes());
        articleRepository.save(article);
    }


    @GetMapping("/getArticlesBycategories/{category}")
    public List<Article> getArticlesByCategories(@PathVariable String category) {
        Category category1 = categoryRepository.findCategoryByCatNom(category);
        return articleRepository.findArticleByCategory(category1);
    }


    @GetMapping("/getFirstArticlesByCategories/{category}")
    public List<Article> getFirstArticlesByCategories(@PathVariable String category) {

        Category category1 = categoryRepository.findCategoryByCatNom(category);
        List<Article> articles = articleRepository.findArticleByCategory(category1);

        return articles.subList(articles.size() - 4, articles.size());
    }

    @Autowired
    PasswordEncoder passwordEncoder;

    @PostMapping("/createUser")
    Userr createUSer(@RequestBody Userr u) {
        u.setUserPassword(passwordEncoder.encode(u.getUserPassword()));
        System.out.println(u);
        return null;
    }

    @PostMapping("/createCommande")
    ResponseEntity<Commande> createCommande(@RequestBody Commande cammande) {
        try {
            Userr u;
            if (cammande.getIdUser() != null) {
                u = userRepository.findById(cammande.getIdUser()).get();
                cammande.setUserr(u);
            }

            if (!cammande.getProductItems().isEmpty())
                cammande.getProductItems().forEach(productItem -> {
                    Long id = productItem.getId_atyicle();
                    Article a = articleRepository.findArticleById(id);
                    productItem.setArticle(a);
                    productItemsRepository.save(productItem);
                });
            cammande.setDate(new Date());
            commandeRepository.save(cammande);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return new ResponseEntity<>(cammande, HttpStatus.OK);
    }

    @GetMapping("/getAverageRating/{id}")
    ResponseEntity<Integer> getAverageRating(@PathVariable Long id) {
        int sum=0;
        Integer a=0;
        Article article = articleRepository.findById(id).get();
        if (article != null) {
            a = ratingRepository.countRatingByArticle(article);
            sum = ratingRepository.findAllByArticle(article).stream().mapToInt(Rating::getStare).sum();
        }
        System.out.println(sum/a);
        return new ResponseEntity<>(sum/a, HttpStatus.OK);
    }

}
