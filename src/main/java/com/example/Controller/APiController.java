package com.example.Controller;

import com.example.Entites.Article;
import com.example.Entites.Category;
import com.example.Repositories.ArticleRepository;
import com.example.Repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
public class APiController {
    @Autowired
    ArticleRepository articleRepository ;
    @Autowired
    CategoryRepository categoryRepository ;
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
        article.setPhoto(id+"__"+file.getOriginalFilename());
        Files.write(Paths.get(System.getProperty("user.home") + "/PfeEcomerce/Articles/" + article.getPhoto()),file.getBytes()) ;
        articleRepository.save(article);
    }

    @CrossOrigin("*")
    @GetMapping("/getArticlesBycategories/{category}")
    public List<Article> getArticlesByCategories(@PathVariable String category){
        Category category1 = categoryRepository.findCategoryByCatNom(category);
        return articleRepository.findArticleByCategory(category1);
    }
}