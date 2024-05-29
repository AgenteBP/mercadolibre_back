package com.kingsman.mercadolibre_back.Services;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kingsman.mercadolibre_back.Models.History;
import com.kingsman.mercadolibre_back.Repositories.HistoryRepositories;

@Service
public class HistoryServices {
    
    @Autowired
    private HistoryRepositories  historyRepositories;

    public History deleteMovements(Integer id){
        Optional<History> history = historyRepositories.findById(id);
        History history2 = history.get();

        history2.setStatus(false);

        return historyRepositories.save(history2);
    }


}
