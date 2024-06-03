package com.kingsman.mercadolibre_back.Repositories;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.kingsman.mercadolibre_back.Models.User;

@Repository
public interface UserRepositories extends JpaRepository<User, Integer>{
    
    User findByEmail(String email);
  
    User findById(int id);

    Page<Object[]> getAllRepository(PageRequest pages);

    @Query(nativeQuery = true, value ="SELECT SUM(h.seller_qualification) FROM history h " +
    "JOIN user u ON h.id_selling_user = u.id " +
    "WHERE u.email = :email AND h.status = true")
    Integer getSumatoryUser(@Param("email") String email);

    Page<Object[]> findAllByActiveTrue(PageRequest pages);

    @Query(nativeQuery = true, value ="SELECT * FROM user u WHERE u.email = :email u.active = true")
    Optional<User> getUserByEmail(@Param("email") String email);

}
