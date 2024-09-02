package ifsul.agileproject.rachadinha.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ifsul.agileproject.rachadinha.domain.entity.Payment;
import ifsul.agileproject.rachadinha.domain.entity.Racha;
import ifsul.agileproject.rachadinha.domain.entity.User;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {

    Payment findByRachaAndUser(Racha racha, User user);

}