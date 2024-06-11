package com.kingsman.mercadolibre_back.Models;

//import java.util.List;

import com.kingsman.mercadolibre_back.Enumerated.Payment;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "history")
public class History {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private Integer idArticle;

    @Column(nullable = false)
    private Integer idSellingUser;

    @Column(nullable = false)
    private Integer idBuyingUser;

    @Column(nullable = true)
    private Integer sellerQualification;

    @Column(nullable = false)
    private Integer quantity;

    @Enumerated(EnumType.STRING) 
    private Payment payment;

    @Column(nullable = false)
    private boolean status = false;

    // Relaciones
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "idArticle", referencedColumnName = "id",insertable=false, 
			updatable = false)
    private Article articles;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "idSellingUser", referencedColumnName = "id",insertable=false, 
			updatable = false)
    private User user;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "idBuyingUser", referencedColumnName = "id",insertable=false, 
			updatable = false)
    private User user2;
}
