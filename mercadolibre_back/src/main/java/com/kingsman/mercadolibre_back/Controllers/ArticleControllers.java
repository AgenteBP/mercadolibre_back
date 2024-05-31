package com.kingsman.mercadolibre_back.Controllers;

import java.util.Optional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.kingsman.mercadolibre_back.Services.ArticleServices;
import com.kingsman.mercadolibre_back.Enumerated.Color;
import com.kingsman.mercadolibre_back.Models.Article;

@RestController
@RequestMapping("/article")
public class ArticleControllers {

    @Autowired
    private ArticleServices articleServices;

    @PostMapping
    public ResponseEntity<Article> addArticle(@RequestBody Article article) {
        Article newArticle = articleServices.addArticle(article);
        return ResponseEntity.ok(newArticle);
    }

    @PutMapping("/id")
    public ResponseEntity<Article> updateArticle(@PathVariable Integer id, @RequestBody Article article) throws Exception {
        Article updateArticle = articleServices.updateArticle(id, article);
        return ResponseEntity.ok(updateArticle);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Article> getArticle(@PathVariable Integer id){
        Optional<Article> article = articleServices.getArticleById(id);
        if (article.isPresent()){
            return ResponseEntity.ok(article.get());
        }else{
            return ResponseEntity.notFound().build();}
    }

    @GetMapping("/search")
    public ResponseEntity<Page<Article>> searchArticles(
            @RequestParam(required = false) String searchTerm,
            @RequestParam(required = false) String type,
            @RequestParam(required = false) Color color,
            @RequestParam(required = false) Float minSale,
            @RequestParam(required = false) Float maxSale) {
        
            Page<Article> articles = articleServices.searchArticles(searchTerm, type, color, minSale, maxSale);
        return ResponseEntity.ok(articles);
    }
    
    @PostMapping("/qualify")
    public ResponseEntity<Integer> postQualifyController(@RequestBody Integer qualify , String email, String name) {
        int qualifyVerified = qualify == null ? 0 : qualify;

        if(articleServices.postQualify(qualifyVerified, email, name) != null){
            return new ResponseEntity<>(qualifyVerified, HttpStatus.CREATED);}
        else{
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}
