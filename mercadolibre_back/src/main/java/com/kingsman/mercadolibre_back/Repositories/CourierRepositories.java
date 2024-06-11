package com.kingsman.mercadolibre_back.Repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.kingsman.mercadolibre_back.Models.Courier;

public interface CourierRepositories extends JpaRepository<Courier, Integer> {

    @Query(nativeQuery = true, value ="SELECT * FROM courier c WHERE c.id_selling_user = :idUser")
    Page<Courier> getAllMessagePage(@Param("idUser") Integer idUser, Pageable pageable);

    @Query(nativeQuery = true, value ="SELECT u.email FROM user u WHERE u.id = :idSellingUser")
    String getEmailSellingUser(@Param("idSellingUser") Integer idSellingUser);

    
    
}
