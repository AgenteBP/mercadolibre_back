package com.kingsman.mercadolibre_back.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.kingsman.mercadolibre_back.Models.User;

@Repository
public interface UserRepositories extends JpaRepository<User, Integer>{
    
    User findByEmail(String email);
}
