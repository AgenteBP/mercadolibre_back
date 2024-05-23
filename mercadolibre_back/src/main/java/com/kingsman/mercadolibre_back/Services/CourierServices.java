package com.kingsman.mercadolibre_back.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.kingsman.mercadolibre_back.Repositories.CourierRepositories;

@Service
public class CourierServices {
    
    @Autowired
    private CourierRepositories courierRepositories;
}
