package com.kingsman.mercadolibre_back.Services;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import com.kingsman.mercadolibre_back.Models.History;
import com.kingsman.mercadolibre_back.Repositories.HistoryRepositories;
import jakarta.transaction.Transactional;

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

    public Page<Object[]> getMovements(int page, int quantityPerPage, String userEmail) {

        return historyRepositories.getMovementsHistory(PageRequest.of(page, quantityPerPage), userEmail);
    }

    public Object sellingInsert(History history) {
        history.setSellerQualification(0);
        history.setStatus(false);
        return historyRepositories.save(history);
    }

    @Transactional
    public void finalizePurchases(Integer idComprador, Integer qualify) {
        historyRepositories.finalizePurchases(idComprador, qualify);
    }


}
