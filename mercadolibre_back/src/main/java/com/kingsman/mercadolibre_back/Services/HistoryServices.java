package com.kingsman.mercadolibre_back.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import com.kingsman.mercadolibre_back.Repositories.HistoryRepositories;

@Service
public class HistoryServices {
    
    @Autowired
    private HistoryRepositories  historyRepositories;

    public Page<Object[]> getMovements(int page, int quantityPerPage, String userEmail) {

        return historyRepositories.getMovementsHistory(PageRequest.of(page, quantityPerPage), userEmail);
    }
}
