package com.kingsman.mercadolibre_back.Controllers;

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
    
    
}
