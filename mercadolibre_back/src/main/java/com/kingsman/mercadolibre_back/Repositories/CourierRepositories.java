package com.kingsman.mercadolibre_back.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kingsman.mercadolibre_back.Models.Courier;

public interface CourierRepositories extends JpaRepository<Courier, Integer> {
    
}
