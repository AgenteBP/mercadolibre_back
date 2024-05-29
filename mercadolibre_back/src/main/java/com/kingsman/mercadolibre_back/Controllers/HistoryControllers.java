package com.kingsman.mercadolibre_back.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kingsman.mercadolibre_back.Models.History;
import com.kingsman.mercadolibre_back.Services.HistoryServices;

@RestController
@RequestMapping("/history")
public class HistoryControllers {
    
    @Autowired
    private HistoryServices historyServices;

    @PutMapping("/{id}")
    public ResponseEntity<History> removeMovements(@PathVariable Integer id, @RequestBody History history) {
        History updateHistory = historyServices.deleteMovements(id);
        return ResponseEntity.ok(updateHistory);
    }
}
