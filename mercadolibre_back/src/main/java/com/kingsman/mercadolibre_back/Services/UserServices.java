package com.kingsman.mercadolibre_back.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import com.kingsman.mercadolibre_back.Repositories.UserRepositories;
import com.kingsman.mercadolibre_back.Models.User;

import jakarta.transaction.Transactional;

@Service
public class UserServices {

    @Autowired
    private UserRepositories userRepositories;

    @Transactional
    public User insert(User user) {
        
        User user2 =  new User();
        User userExistente = userRepositories.findByEmail(user.getEmail());
        try {
            
            if (userExistente != null) {
                // Ya existe un usuario con el mismo correo electrónico
                // Puedes decidir qué hacer en este punto, por ejemplo, actualizar el usuario existente

                userExistente.setName(user.getName());
                userExistente.setLastName(user.getLastName());
                userExistente.setPassword(user.getPassword());
                userExistente.setValorizacion(user.getValorizacion());
                userExistente.setNroCompras(user.getNroCompras());
                userExistente.setNroVentas(user.getNroVentas());

                // Guardar el usuario actualizado en la base de datos
                return userRepositories.save(userExistente);
            } else {
                // No existe un usuario con el mismo correo electrónico, proceder con la inserción

                return userRepositories.save(user);
            }
            
        } catch (DataIntegrityViolationException e) {
            // e.printStackTrace();
            // throw new ErrorResponse(e.getMostSpecificCause().getMessage(),
            //         HttpStatus.UNPROCESSABLE_ENTITY);
            System.out.println("no funcaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
            e.printStackTrace();  // Imprime el log completo de la excepción
            System.out.println("Error en la inserción: " + e.getMessage());
        }
        return user2;
    }

}