package com.kingsman.mercadolibre_back.Controllers;

import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import org.springframework.data.domain.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.kingsman.mercadolibre_back.Models.User;
import com.kingsman.mercadolibre_back.Services.UserServices;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/user")
// @CrossOrigin(origins = {UrlFront.urlLocal, UrlFront.urlNetlify})
public class UserController {

    private final int DEFAULT_PAGE_NUMBER = 0;
    private final int DEFAULT_QUANTITY_PER_PAGE = 10;

    @Autowired
    private UserServices userService;

    @GetMapping("/all")
    public ResponseEntity<?> getAllUserController(@RequestParam Map<String, String> map) {
        int page = map.containsKey("page") ? Integer.parseInt(map.get("page")) : DEFAULT_PAGE_NUMBER;
        int quantityPerPage = map.containsKey("quantity") ? Integer.parseInt(map.get("quantity")) : DEFAULT_QUANTITY_PER_PAGE;

        Page<Object[]> users = userService.getAllUser(page, quantityPerPage);
        
        if(!users.getContent().isEmpty()){
            return new ResponseEntity<>(users, HttpStatus.OK);
        }
        else{
            return new ResponseEntity<String>("Empty Users", HttpStatus.NO_CONTENT);
        }
        
    }

    @GetMapping("/sumatory")
    public ResponseEntity<?>  getSumatoryController(@RequestParam String email) {

        if(!email.isEmpty()){
            Integer sumatory = userService.getSumatory(email);
            return new ResponseEntity<>(sumatory, HttpStatus.OK);
        }
        else{
            return new ResponseEntity<String>("No sales", HttpStatus.NO_CONTENT);
        }
    }
    
    

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

    @PutMapping("/{id}/average")
    public ResponseEntity<Void> updateValorization(@PathVariable Integer id){
        userService.averageValorization(id);
        return ResponseEntity.noContent().build();
    }

}