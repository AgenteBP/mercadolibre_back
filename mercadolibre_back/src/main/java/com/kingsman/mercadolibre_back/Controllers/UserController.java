package com.kingsman.mercadolibre_back.Controllers;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.kingsman.mercadolibre_back.Models.User;
import com.kingsman.mercadolibre_back.Services.UserServices;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("/user")
// @CrossOrigin(origins = {UrlFront.urlLocal, UrlFront.urlNetlify})
public class UserController {

    @Autowired
    private UserServices userService;

    @PostMapping()
    public ResponseEntity<User> postUsuarios(@RequestBody User user) {
        User userInsertado = userService.insert(user);

        // Verifica si la inserción fue exitosa
        if (userInsertado != null && userInsertado.getId() != null) {
            // Devuelve el usuario con la ID generada y un código de estado 201 (CREATED)
            return new ResponseEntity<>(userInsertado, HttpStatus.CREATED);
        } else {
            // Si la inserción falla, puedes devolver un código de estado 500 (INTERNAL_SERVER_ERROR)
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}