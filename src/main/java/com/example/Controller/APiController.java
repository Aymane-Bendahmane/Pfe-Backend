package com.example.Controller;

import com.example.Entites.*;

import com.example.Repositories.*;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.xml.ws.Response;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@Api("Other endpoints beside spring data")
@ApiOperation(value = "", authorizations = {@Authorization(value = "jwtToken")})

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
    public MarqueRepository marqueRepository;
    @Autowired
    public RoleRepository rolesReposiroty;
    @Autowired
    public RatingRepository ratingRepository;

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
        userRepository.save(u);
        Collection<Role>rolelist=new ArrayList<>();
        rolelist.add(rolesReposiroty.findByRole("USER"));
        System.out.println(rolelist);
        u.setRoles(rolelist);
        return userRepository.save(u);
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
        int sum = 0;
        Integer a = 0;
        Article article = articleRepository.findById(id).get();
        if (article != null) {
            a = ratingRepository.countRatingByArticle(article);
            sum = ratingRepository.findAllByArticle(article).stream().mapToInt(Rating::getStare).sum();
        }
        System.out.println(sum / a);
        return new ResponseEntity<>(sum / a, HttpStatus.OK);
    }

    @PostMapping("/saveRating")
    ResponseEntity<Rating> saveRAting(@RequestBody Rating r) {

        return new ResponseEntity<>(ratingRepository.save(r), HttpStatus.OK);
    }

    /**************************************************** */
    @RequestMapping(value = "/marks", method = RequestMethod.GET)
    public List<Marque> listedes() {
        return marqueRepository.findAll();

    }

    @RequestMapping(value = "/products", method = RequestMethod.GET)
    public List<Article> listAr() {
        return articleRepository.findAll();

    }

    @RequestMapping(value = "/roless", method = RequestMethod.GET)
    public List<Role> ListRoles() {
        return rolesReposiroty.findAll();

    }

    @RequestMapping(value = "/categ", method = RequestMethod.GET)
    public List<Category> listC() {
        return categoryRepository.findAll();

    }

    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public List<Userr> listU() {
        return userRepository.findAll();

    }

    @RequestMapping(value = "/commandess", method = RequestMethod.GET)
    public List<Commande> com() {
        return commandeRepository.findAll();

    }

    @RequestMapping(value = "/addMarque", method = RequestMethod.POST)
    public Marque save(@RequestBody Marque m) {


        return marqueRepository.save(m);
    }

    @RequestMapping(value = "/useer", method = RequestMethod.POST)
    public Userr Userrrsave(@RequestBody Userr u) {

        u.setUserPassword(passwordEncoder.encode(u.getUserPassword()));
        userRepository.save(u);
        Collection<Role> rolelist = new ArrayList<>();
        rolelist.add(rolesReposiroty.findByRole("USER"));
        System.out.println(rolelist);
        u.setRoles(rolelist);
        return userRepository.save(u);
    }

    @RequestMapping(value = "/addCat", method = RequestMethod.POST)
    public Category save(@RequestBody Category m) {


        return categoryRepository.save(m);
    }

    @GetMapping("/categ/{id}")
    public Category getCat(@PathVariable("id") Long id) {
        return categoryRepository.getById(id);
    }

    @PutMapping("/editCateg/{id}")
    public ResponseEntity<Category> editUser(@PathVariable(value = "id") Long id,
                                             @Validated @RequestBody Category category) throws ResourceNotFoundException {
        Category category1 = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cat not found for this id :: " + id));


        final Category updateCat = categoryRepository.save(category);
        return ResponseEntity.ok(updateCat);
    }

    @GetMapping("/article/{id}")
    public Article getarticle(@PathVariable("id") Long id) {
        return articleRepository.getById(id);
    }

    @GetMapping("/roles")
    public Article getRoles(@PathVariable("id") Long id) {
        return articleRepository.getById(id);
    }

    @PatchMapping("/editArticle/{id}")
    public Article updateA(@PathVariable(name = "id") Long id, @RequestBody Article p) {
        p.setId(id);
        return articleRepository.save(p);
    }


    @DeleteMapping(value = "/DmarkA/{id}")
    public void deleteM(@PathVariable(name = "id") Long id) {
        Marque mark = marqueRepository.findById(id).get();
        Collection<Commande> cm = commandeRepository.findAll();
        mark.getArticles().forEach(a -> {

            cm.forEach(c -> {
                c.getProductItems().forEach(p -> {
                    if (p.getArticle().getId().equals(a.getId())) {
                        c.getProductItems().remove(p);
                        productItemsRepository.deleteById(p.getId());
                    }
                });
                //commandeRepository.deleteById(c.getId());
            });
            articleRepository.deleteById(a.getId());
        });
        marqueRepository.deleteById(id);
    }

    @DeleteMapping(value = "/Darticle/{id}")
    public void deleteA(@PathVariable(name = "id") Long id) {
        Collection<ProductItem> pi;
        Collection<Commande> cm;
        cm = commandeRepository.findAll();
        pi = productItemsRepository.findAll();
        pi.forEach(prod -> {
            if (prod.getArticle().getId().equals(id)) {
                cm.forEach(com -> {
                    com.getProductItems().forEach(c -> {
                        if (c.getArticle().getId().equals(prod.getId())) {
                            if (com.getId() != null) {
                                commandeRepository.deleteById(com.getId());
                               /*Commande cc= commandeRepository.getById(com.getId());
                               if(c.getArticle()!=null){
                               c.setArticle(null);
                               productItemsRepository.save(c);
                               commandeRepository.save(com);

                              }*/
                                com.getProductItems().remove(prod);

                            }


                        }
                    });

                });
            }
        });
        articleRepository.deleteById(id);
    }

    /*********************************************************************************/
    @DeleteMapping(value = "/Darticles/{id}")
    public void deleteAr(@PathVariable(name = "id") Long id) {
        Collection<ProductItem> pi;
        Collection<Commande> cm;
        cm = commandeRepository.findAll();
        pi = productItemsRepository.findAll();
        pi.forEach(prod -> {
            if (prod.getArticle().getId().equals(id)) {
                cm.forEach(com -> {
                    com.getProductItems().forEach(c -> {
                        if (c.getArticle().getId().equals(id)) {
                            com.getProductItems().remove(prod);
                            productItemsRepository.deleteById(prod.getId());
                        }
                    });

                });
            }
        });
        articleRepository.deleteById(id);
    }


    @DeleteMapping(value = "/Dcommande/{id}")
    public void deleteC(@PathVariable(name = "id") Long id) {

        commandeRepository.deleteById(id);
    }

    @DeleteMapping("/Dusers/{id}")
    public void deleteU(@PathVariable(name = "id") Long id) {
        Userr u = userRepository.findById(id).get();
        u.getRatings().forEach(r -> {
            ratingRepository.deleteById(r.getId());
        });
        u.getCommandes().forEach(c -> {
            commandeRepository.deleteById(c.getId());
        });
        userRepository.deleteById(id);
    }

    @DeleteMapping("/Dcateg/{id}")
    public void deleteCaTeg(@PathVariable(name = "id") Long id) {
        Category categ = categoryRepository.findById(id).get();
        Collection<ProductItem> pi;
        Collection<Commande> cm;
        cm = commandeRepository.findAll();
        pi = productItemsRepository.findAll();
        categ.getArticles().forEach(cc -> {
            pi.forEach(prod -> {
                if (prod.getArticle().getId().equals(cc.getId())) {
                    cm.forEach(com -> {
                        com.getProductItems().forEach(c -> {
                            if (c.getArticle().getId().equals(cc.getId())) {
                                com.getProductItems().remove(prod);
                                productItemsRepository.deleteById(prod.getId());
                                cc.getRatings().forEach(r -> {
                                    ratingRepository.deleteById(r.getId());
                                });
                            }
                        });
                    });
                }
            });
        });
        categoryRepository.deleteById(id);
    }

    @RequestMapping(value = "/addUser", method = RequestMethod.POST)
    public Userr save(@RequestBody Userr m) {


        return userRepository.save(m);
    }

    @GetMapping("/users/{idU}")
    public Userr getUser(@PathVariable("idU") Long idU) {
        return userRepository.getById(idU);
    }

    @PatchMapping("/editUser/{idU}")
    public ResponseEntity<Userr> editUser(@PathVariable(value = "idU") Long idU,
                                          @Validated @RequestBody Userr userr) throws ResourceNotFoundException {
        Userr userr1 = userRepository.findById(idU)
                .orElseThrow(() -> new ResourceNotFoundException("User not found for this id :: " + idU));


        final Userr updatedUser = userRepository.save(userr);
        return ResponseEntity.ok(updatedUser);
    }


    @GetMapping("/marks/{id}")
    public Marque getMrk(@PathVariable("id") Long id) {
        return marqueRepository.getById(id);
    }

    @PutMapping("/editMark/{id}")
    public ResponseEntity<Marque> editMark(@PathVariable(value = "id") Long id,
                                           @Validated @RequestBody Marque marque) throws ResourceNotFoundException {
        Marque mk = marqueRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Mark not found for this id :: " + id));

        mk.setId(marque.getId());
        mk.setMarqNom(marque.getMarqNom());

        final Marque updatedMk = marqueRepository.save(marque);
        return ResponseEntity.ok(updatedMk);
    }

    @RequestMapping(value = "/addProd", method = RequestMethod.POST)
    public Article save(@RequestBody Article m) {


        return articleRepository.save(m);
    }

    @RequestMapping(value = "/addCmd", method = RequestMethod.POST)
    public Commande save(@RequestBody Commande m) {


        return commandeRepository.save(m);
    }

}
