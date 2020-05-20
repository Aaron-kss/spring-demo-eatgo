package com.sskim.eatgo.application;

import com.sskim.eatgo.domain.Reservation;
import com.sskim.eatgo.domain.ReservationRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class ReservationService {

    private final ReservationRepository reservationRepository;

    public ReservationService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    public Reservation addReservation(Long restaurantId, Long userId, String name, String date, String time, Integer partySize) {
       return reservationRepository.save(Reservation.builder()
               .restaurantId(restaurantId)
               .userId(userId)
               .name(name)
               .date(date)
               .time(time)
               .partySize(partySize)
               .build());
    }
}