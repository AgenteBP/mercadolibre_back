package com.kingsman.mercadolibre_back.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.kingsman.mercadolibre_back.Repositories.ArticleRepositories;

@Service
public class ArticleServices {
    
    @Autowired
    private ArticleRepositories articleRepositories;

    public Integer postQualify(int qualifyVerified, String email, String name) {
        // Sumo la anterior calificacion y divido por 2
        Integer previousQualify = articleRepositories.getPreviousQualify(email, name);
        if(previousQualify == 0){
            return articleRepositories.postQualifyWithEmail(qualifyVerified, email, name);
        }
        else{
            qualifyVerified = previousQualify + qualifyVerified / 2;
            return articleRepositories.postQualifyWithEmail(qualifyVerified, email, name);
        }
        
    }
    
}
