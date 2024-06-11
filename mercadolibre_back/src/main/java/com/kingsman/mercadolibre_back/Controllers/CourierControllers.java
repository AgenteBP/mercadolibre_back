package com.kingsman.mercadolibre_back.Controllers;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.kingsman.mercadolibre_back.Models.Courier;
import com.kingsman.mercadolibre_back.Services.CourierServices;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.data.domain.Page;

@RestController
@RequestMapping("/courier")
public class CourierControllers {
    private final int DEFAULT_PAGE_NUMBER = 0;
    private final int DEFAULT_QUANTITY_PER_PAGE = 10;

    @Autowired
    private CourierServices courierServices;

    @GetMapping("/all")
    public ResponseEntity<?> getAllMessageController(@RequestParam Map<String, String> map) {

        int page = map.containsKey("page") ? Integer.parseInt(map.get("page")) : DEFAULT_PAGE_NUMBER;
        int quantityPerPage = map.containsKey("quantity") ? Integer.parseInt(map.get("quantity")) : DEFAULT_QUANTITY_PER_PAGE;
        Integer idUser = Integer.parseInt(map.get("idUser"));

        if(!(idUser.equals(null))){
            Page<Courier> allMessageUser = courierServices.getAllMessage(idUser, page, quantityPerPage);
            return new ResponseEntity<>(allMessageUser, HttpStatus.ACCEPTED);
        }
        else{
            return new ResponseEntity<>("Bad Request", HttpStatus.BAD_REQUEST);
        }

    }
    

    @PostMapping("/send")
    public ResponseEntity<String> sendMessageController(@RequestParam Map<String, String> map){

        String recipient = map.get("email");
        Integer idSellingUser = Integer.parseInt(map.get("idSellingUser"));
        String description = map.get("description");
        Integer idArticle = Integer.parseInt(map.get("idArticle"));

        // System.out.println("email es "+ map.get("email"));
        // System.out.println("id de vendedorrrrrrr es "+ Integer.parseInt(map.get("idSellingUser")));
        // System.out.println("la descripcion es "+ map.get("description"));
        // System.out.println("idArticle es "+ Integer.parseInt(map.get("idArticle")));

        if( !(idArticle.equals(null) || idSellingUser.equals(null) || recipient.isEmpty())){
            courierServices.sendMessage(recipient, idSellingUser, description, idArticle);
            return new ResponseEntity<String>("Message sent", HttpStatus.ACCEPTED);
        }
        else{
            return new ResponseEntity<String>("Bad Request", HttpStatus.BAD_REQUEST);
        }


    }
    
}
