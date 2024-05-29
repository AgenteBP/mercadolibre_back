package com.kingsman.mercadolibre_back.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.kingsman.mercadolibre_back.Services.ArticleServices;

@RestController
@RequestMapping("/article")
public class ArticleControllers {

    @Autowired
    private ArticleServices articleServices;

    @PostMapping("/qualify")
    public ResponseEntity<Integer> postQualifyController(@RequestBody Integer qualify , String email, String name) {
        int qualifyVerified = qualify == null ? 0 : qualify;

        if(articleServices.postQualify(qualifyVerified, email, name) != null){
            return new ResponseEntity<>(qualifyVerified, HttpStatus.CREATED);}
        else{
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}
