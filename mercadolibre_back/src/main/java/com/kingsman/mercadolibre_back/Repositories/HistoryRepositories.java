package com.kingsman.mercadolibre_back.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.kingsman.mercadolibre_back.Models.History;

public interface HistoryRepositories extends JpaRepository<History, Integer>{
    List<History> findByUserIdAndValorizationIsNotNull(Integer userId);
    
}
