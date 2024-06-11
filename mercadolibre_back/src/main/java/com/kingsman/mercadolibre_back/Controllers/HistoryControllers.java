package com.kingsman.mercadolibre_back.Controllers;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.kingsman.mercadolibre_back.Services.HistoryServices;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.kingsman.mercadolibre_back.Models.History;

@RestController
@RequestMapping("/history")
public class HistoryControllers {
    private final int DEFAULT_PAGE_NUMBER = 0;
    private final int DEFAULT_QUANTITY_PER_PAGE = 10;

    @Autowired
    private HistoryServices historyServices;

    @GetMapping("/see")
    public ResponseEntity<?> getMovementsController(@RequestParam Map<String, String> map) {
        int page = map.containsKey("page") ? Integer.parseInt(map.get("page")) : DEFAULT_PAGE_NUMBER;
        int quantityPerPage = map.containsKey("quantity") ? Integer.parseInt(map.get("quantity")) : DEFAULT_QUANTITY_PER_PAGE;
        String userEmail = map.get("email");
        Page<Object[]> history;

        if(!userEmail.isEmpty()){
            history = historyServices.getMovements(page, quantityPerPage, userEmail);

            if(!history.getContent().isEmpty()){
                return new ResponseEntity<>(history, HttpStatus.OK);
            }else{
                return new ResponseEntity<String>("Empty History", HttpStatus.NO_CONTENT);
            }
            
        }
        else{
            return new ResponseEntity<>("Email parameter is missing", HttpStatus.BAD_REQUEST);
        }

    }

    @PutMapping("/{id}")
    public ResponseEntity<History> removeMovements(@PathVariable Integer id, @RequestBody History history) {
        History updateHistory = historyServices.deleteMovements(id);
        return ResponseEntity.ok(updateHistory);
    }

    @PostMapping("/selling")
    public ResponseEntity<Object> selling(@RequestBody History history){
        return ResponseEntity.ok(historyServices.sellingInsert(history));
    }

    @PutMapping("/finalize/{idComprador}")
    public ResponseEntity<Void> finalizePurchases(@PathVariable Integer idComprador, @RequestParam Integer qualify) {
        historyServices.finalizePurchases(idComprador, qualify);
        return ResponseEntity.ok().build();
    }
}
