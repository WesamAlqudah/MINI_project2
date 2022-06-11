package MainProject.repository;

import MainProject.model.Payment;
import MainProject.model.PaymentEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
	Optional<Payment> findByName(PaymentEnum name);
}
