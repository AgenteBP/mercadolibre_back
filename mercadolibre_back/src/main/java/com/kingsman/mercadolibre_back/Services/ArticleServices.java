package com.kingsman.mercadolibre_back.Services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.kingsman.mercadolibre_back.Enumerated.Color;
import com.kingsman.mercadolibre_back.Models.Article;
import com.kingsman.mercadolibre_back.Repositories.ArticleRepositories;

@Service
public class ArticleServices {
    
    @Autowired
    private ArticleRepositories articleRepositories;


    public Article addArticle(Article article){
        return articleRepositories.save(article);
    }

    public Article updateArticle(Integer id, Article updatedArticle){
        Article article2 = new Article();
        Optional<Article> existArticle = articleRepositories.findById(id);
        if (existArticle.isPresent()){
            Article existingArticle = existArticle.get();
            existingArticle.setName(updatedArticle.getName());
            existingArticle.setType(updatedArticle.getType());
            existingArticle.setDescription(updatedArticle.getDescription());
            existingArticle.setSale(updatedArticle.getSale());
            existingArticle.setStock(updatedArticle.getStock());
            existingArticle.setColor(updatedArticle.getColor());
            existingArticle.setImage(updatedArticle.getImage());
            existingArticle.setReputacion(updatedArticle.getReputacion());
            existingArticle.setActive(updatedArticle.isActive());
            existingArticle.setIdUser(updatedArticle.getIdUser());
            return articleRepositories.save(existingArticle);
        }else {
            return article2;
        }
    }

    public Optional<Article> getArticleById(Integer id){
        return articleRepositories.findById(id);}

    public Page<Article> searchArticles(String searchTerm, String type, Color color, Float minSale, Float maxSale) {
        // Limpiar y procesar el término de búsqueda
        String cleanedSearchTerm = searchTerm != null ? searchTerm.trim().toLowerCase() : "";
        return articleRepositories.searchArticles(cleanedSearchTerm, type, color, minSale, maxSale);
    }


    public Integer postQualify(int qualifyVerified, String email, String name) {
        // Sumo la anterior calificacion y divido por 2
        Integer previousQualify = articleRepositories.getPreviousQualify(email, name);
        if(previousQualify == 0){
            return articleRepositories.postQualifyWithEmail(qualifyVerified, email, name);
        }
        else{
            qualifyVerified = previousQualify + qualifyVerified / 2;
            return articleRepositories.postQualifyWithEmail(qualifyVerified, email, name);
        }
        

    }
    
}
