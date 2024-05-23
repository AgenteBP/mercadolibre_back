package com.kingsman.mercadolibre_back.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.kingsman.mercadolibre_back.Models.Article;

@Repository
public interface ArticleRepositories extends JpaRepository<Article, Integer>{
    
}
