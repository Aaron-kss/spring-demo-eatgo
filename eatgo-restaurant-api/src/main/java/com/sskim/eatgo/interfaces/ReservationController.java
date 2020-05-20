package com.sskim.eatgo.interfaces;

import com.sskim.eatgo.application.ReservationService;
import com.sskim.eatgo.domain.Reservation;
import io.jsonwebtoken.Claims;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ReservationController {

    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService){
        this.reservationService = reservationService;
    }

    @GetMapping("/reservations")
    public List<Reservation> list(
            Authentication authentication
    ){

        Claims claims = (Claims) authentication.getPrincipal();


        List<Reservation> reservations = reservationService.getReservations(claims.get("restaurantId", Long.class));

        return reservations;
    }
}