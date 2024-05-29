package com.kingsman.mercadolibre_back.Services;

import java.util.List;
import java.util.Optional;
import java.util.OptionalDouble;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.kingsman.mercadolibre_back.Repositories.HistoryRepositories;
import com.kingsman.mercadolibre_back.Repositories.UserRepositories;
import com.kingsman.mercadolibre_back.Models.History;
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

    @Transactional
    public User getUser(int  id) {
        User user = userRepositories.findById(id);
        return user;

    }
    @Autowired
    private HistoryRepositories historyRepositories;

    @Transactional
    public void averageValorization(Integer userId){

        // traigo todos las valorizcaciones de los objetos q hayan sido insertado su valorizacion
        List<History> histories = historyRepositories.findByUserIdAndValorizationIsNotNull(userId);

        // tranforma todos los valores de la lista, los mapea y saca el promedio
        OptionalDouble averageVOpt = histories.stream().mapToDouble(History::getSellerQualification).average();
        
        // una vez q tenemos el promedio de la valorizacion lo insertamos en el usuario
        if(averageVOpt.isPresent()) {
            Double averageVal = averageVOpt.getAsDouble();
            Optional<User> user = userRepositories.findById(userId);
            User user2 = user.get();
            user2.setValorizacion(averageVal.floatValue());
            userRepositories.save(user2);
        }
    }

}