package com.kingsman.mercadolibre_back.Models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "user")
public class User {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 85)
    private String name;

    @Column(nullable = false, length = 50)
    private String lastName;

    @Column(nullable = false, unique = true, length = 40)
    private String email;

    @Column(nullable = false, unique = true, length = 80)
    private String password;

    @Column(nullable = true)
    private Float valorizacion;

    @Column(nullable = true)
    private Integer nroCompras;

    @Column(nullable = true)
    private Integer nroVentas;
    
}
