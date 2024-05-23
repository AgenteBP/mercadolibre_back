package com.kingsman.mercadolibre_back.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.kingsman.mercadolibre_back.Repositories.ArticleRepositories;

@Service
public class ArticleServices {
    
    @Autowired
    private ArticleRepositories articleRepositories;
    
}
