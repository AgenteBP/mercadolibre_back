package com.kingsman.mercadolibre_back.Repositories;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.kingsman.mercadolibre_back.Models.History;

public interface HistoryRepositories extends JpaRepository<History, Integer>{

    @Query(nativeQuery = true, value = "SELECT h.* FROM history h " +
                                       "JOIN user u ON h.id_selling_user = u.id " +
                                       "WHERE u.email = :email")
    Page<Object[]> getMovementsHistory(Pageable pageable, @Param("email") String userEmail);
    
}
