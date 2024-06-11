package com.kingsman.mercadolibre_back.Repositories;

import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.kingsman.mercadolibre_back.Models.History;
import jakarta.transaction.Transactional;

public interface HistoryRepositories extends JpaRepository<History, Integer>{

    List<History> findByIdSellingUserAndQuantityIsNotNull(Integer idSellingUser);

    @Query(nativeQuery = true, value = "SELECT h.* FROM history h " +
                                       "JOIN user u ON h.id_selling_user = u.id " +
                                       "WHERE u.email = :email")
    Page<Object[]> getMovementsHistory(Pageable pageable, @Param("email") String userEmail);

    @Modifying
    @Transactional
    @Query(nativeQuery = true, value = "UPDATE article a " +
                   "JOIN history h ON a.id = h.id_article " +
                   "SET a.stock = a.stock - h.quantity, h.status = true, h.seller_qualification = :qualify " +
                   "WHERE h.id_buying_user = :idBuyingUser AND h.status = false")
    void finalizePurchases(@Param("idBuyingUser") Integer idBuyingUser, @Param("qualify") Integer qualify);
    
}
