package com.kingsman.mercadolibre_back.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.kingsman.mercadolibre_back.Models.Courier;
import com.kingsman.mercadolibre_back.Repositories.CourierRepositories;

@Service
public class CourierServices {
    
    @Autowired
    private CourierRepositories courierRepositories;

    @Autowired
    private EmailService emailService;

    public Page<Courier> getAllMessage(Integer idUser, int page, int quantityPerPage) {
        return courierRepositories.getAllMessagePage(idUser, PageRequest.of(page, quantityPerPage));
    }

    public void sendMessage(String recipient, Integer idSellingUser, String description, Integer idArticle) {
        
        Courier courier = new Courier();
        courier.setEmail(recipient);
        courier.setIdSellingUser(idSellingUser);
        courier.setDescription(description);
        courier.setIdArticle(idArticle);

        courierRepositories.save(courier);

        String emailSellingUser = courierRepositories.getEmailSellingUser(idSellingUser);
        
        // Enviar el correo electrónico
        String subject = "Te va a llegar un correo spam";
        String text = "Dear seller,\n\nYou have received a new message regarding your article:\n\n" + description;
        emailService.sendSimpleMessage(emailSellingUser, subject, text, recipient);
    }

}
