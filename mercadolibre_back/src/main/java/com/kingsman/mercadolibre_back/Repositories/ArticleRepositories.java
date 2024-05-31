package com.kingsman.mercadolibre_back.Repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.kingsman.mercadolibre_back.Enumerated.Color;
import com.kingsman.mercadolibre_back.Models.Article;

@Repository
public interface ArticleRepositories extends JpaRepository<Article, Integer>{

    @Query(nativeQuery = true, value = "SELECT a.qualify FROM article a JOIN user u ON u.id = a.id_user "+
                                "WHERE u.email = :email AND a.name = :name")
    Integer getPreviousQualify(@Param("email") String email, @Param("name") String name);

    @Query("SELECT a FROM Article a WHERE " +
           "(LOWER(a.name) LIKE LOWER(CONCAT('%', :searchTerm, '%')) " +
           "OR LOWER(a.type) LIKE LOWER(CONCAT('%', :searchTerm, '%')) " +
           "OR LOWER(a.description) LIKE LOWER(CONCAT('%', :searchTerm, '%'))) " +
           "AND (:type IS NULL OR a.type = :type) " +
           "AND (:color IS NULL OR a.color = :color) " +
           "AND (:minSale IS NULL OR a.sale >= :minSale) " +
           "AND (:maxSale IS NULL OR a.sale <= :maxSale) " +
           "AND a.stock > 0 " +
           "AND a.active = true")
    Page<Article> searchArticles(@Param("searchTerm") String searchTerm,
                                 @Param("type") String type,
                                 @Param("color") Color color,
                                 @Param("minSale") Float minSale,
                                 @Param("maxSale") Float maxSale);

    @Modifying
    @Query(nativeQuery = true, value = "UPDATE article a " +
                                   "JOIN user u ON a.id_user = u.id " +
                                   "SET a.qualify = :qualify " +
                                   "WHERE a.name = :name AND u.email = :email")
    int postQualifyWithEmail(@Param("qualify") int qualify, @Param("email") String email, @Param("name") String name);

    
    
}
