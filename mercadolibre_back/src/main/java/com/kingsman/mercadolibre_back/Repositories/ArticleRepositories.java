package com.kingsman.mercadolibre_back.Repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

//import com.kingsman.mercadolibre_back.Enumerated.Color;
import com.kingsman.mercadolibre_back.Models.Article;

@Repository
public interface ArticleRepositories extends JpaRepository<Article, Integer>{

    @Query(nativeQuery = true, value = "SELECT a.qualify FROM article a JOIN user u ON u.id = a.id_user "+
                                "WHERE u.email = :email AND a.name = :name")
    Integer getPreviousQualify(@Param("email") String email, @Param("name") String name);

    @Query(nativeQuery = true, value = "SELECT * FROM article a WHERE " +
           "(COALESCE(:searchTerm, '') = '' OR LOWER(a.name) LIKE LOWER(CONCAT('%', :searchTerm, '%'))) " +
           "AND (COALESCE(:type, '') = '' OR LOWER(a.type) = LOWER(:type)) " +
           "AND (COALESCE(:color, '') = '' OR LOWER(a.color) = LOWER(:color)) " +
           "AND (COALESCE(:minSale, 0) = 0 OR a.sale >= :minSale) " +
           "AND (COALESCE(:maxSale, 0) = 0 OR a.sale <= :maxSale) " +
           "AND a.stock > 0 " +
           "AND a.active = true")
    Page<Object> searchArticles(@Param("searchTerm") String searchTerm,
                                 @Param("type") String type,
                                 @Param("color") String color,
                                 @Param("minSale") Float minSale,
                                 @Param("maxSale") Float maxSale, PageRequest pages);

    @Modifying
    @Query(nativeQuery = true, value = "UPDATE article a " +
                                   "JOIN user u ON a.id_user = u.id " +
                                   "SET a.qualify = :qualify " +
                                   "WHERE a.name = :name AND u.email = :email")
    int postQualifyWithEmail(@Param("qualify") int qualify, @Param("email") String email, @Param("name") String name);

    
    
}
