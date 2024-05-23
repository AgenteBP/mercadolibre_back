package com.kingsman.mercadolibre_back.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.kingsman.mercadolibre_back.Repositories.HistoryRepositories;

@Service
public class HistoryServices {
    
    @Autowired
    private HistoryRepositories  historyRepositories;
}
