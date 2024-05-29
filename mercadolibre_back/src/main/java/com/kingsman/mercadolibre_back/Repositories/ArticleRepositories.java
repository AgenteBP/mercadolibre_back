package com.kingsman.mercadolibre_back.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.kingsman.mercadolibre_back.Models.Article;

@Repository
public interface ArticleRepositories extends JpaRepository<Article, Integer>{

    @Query(nativeQuery = true, value = "SELECT a.qualify FROM article a JOIN user u ON u.id = a.id_user "+
                                "WHERE u.email = :email AND a.name = :name")
    Integer getPreviousQualify(@Param("email") String email, @Param("name") String name);

    @Modifying
    @Query(nativeQuery = true, value = "UPDATE article a " +
                                   "JOIN user u ON a.id_user = u.id " +
                                   "SET a.qualify = :qualify " +
                                   "WHERE a.name = :name AND u.email = :email")
    int postQualifyWithEmail(@Param("qualify") int qualify, @Param("email") String email, @Param("name") String name);

    
    
}
