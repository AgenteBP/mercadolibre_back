package com.kingsman.mercadolibre_back.Services;

import java.util.List;
import java.util.Optional;
import java.util.OptionalDouble;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.kingsman.mercadolibre_back.Repositories.HistoryRepositories;
import com.kingsman.mercadolibre_back.Repositories.UserRepositories;
import com.kingsman.mercadolibre_back.Security.JWT.JwtService;
import com.kingsman.mercadolibre_back.Security.JWT.TokenResponse;
import com.kingsman.mercadolibre_back.Security.Request.LoginRequest;
import com.kingsman.mercadolibre_back.Models.History;
import com.kingsman.mercadolibre_back.Models.User;
import jakarta.transaction.Transactional;

@Service
public class UserServices {

    @Autowired
    private UserRepositories userRepositories;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Page<Object[]> getAllUser(int page, int quantityPerPage) {
        return userRepositories.getAllRepository(PageRequest.of(page, quantityPerPage));
    }

    public Integer getSumatory(String email) {
        return userRepositories.getSumatoryUser(email);
    }

   
    public TokenResponse login(LoginRequest request){
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUserName(), request.getPassword()));

        UserDetails userDetails = userRepositories.getUserByEmail(request.getUserName()).orElseThrow();
        String token = jwtService.getToken(userDetails);

        return TokenResponse.builder().token(token).build();
    }

    @Transactional
    public TokenResponse insert(User user) {
        
        User userExistente = userRepositories.findByEmail(user.getEmail());

        if (userExistente != null) {
            // Ya existe un usuario con el mismo correo electrónico
            // Puedes decidir qué hacer en este punto, por ejemplo, actualizar el usuario existente

            userExistente.setName(user.getName());
            userExistente.setLastName(user.getLastName());
            userExistente.setPassword(passwordEncoder.encode(user.getPassword()));
            userExistente.setValorizacion(user.getValorizacion());
            userExistente.setNroCompras(user.getNroCompras());
            userExistente.setNroVentas(user.getNroVentas());

            // Guardar el usuario actualizado en la base de datos
            userRepositories.save(userExistente);
            
            return TokenResponse.builder()
            .token(jwtService.getToken(userExistente))
            .build();
        } else {
            // No existe un usuario con el mismo correo electrónico, proceder con la inserción
            user.setValorizacion((float) 0);
            user.setNroCompras(0);
            user.setNroVentas(0);
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            
            userRepositories.save(user);
            
            return TokenResponse.builder()
            .token(jwtService.getToken(user))
            .build();
        }
        
    }
  
    @Transactional
    public User getUser(int  id) {
        User user = userRepositories.findById(id);
        return user;

    }
    @Autowired
    private HistoryRepositories historyRepositories;

    @Transactional
    public void averageValorization(Integer userId){

        // traigo todos las valorizcaciones de los objetos q hayan sido insertado su valorizacion
        List<History> histories = historyRepositories.findByUserIdAndValorizationIsNotNull(userId);

        // tranforma todos los valores de la lista, los mapea y saca el promedio
        OptionalDouble averageVOpt = histories.stream().mapToDouble(History::getSellerQualification).average();
        
        // una vez q tenemos el promedio de la valorizacion lo insertamos en el usuario
        if(averageVOpt.isPresent()) {
            Double averageVal = averageVOpt.getAsDouble();
            Optional<User> user = userRepositories.findById(userId);
            User user2 = user.get();
            user2.setValorizacion(averageVal.floatValue());
            userRepositories.save(user2);
        }
    }

}