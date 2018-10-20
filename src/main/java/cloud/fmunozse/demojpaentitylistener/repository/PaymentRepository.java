package cloud.fmunozse.demojpaentitylistener.repository;

import cloud.fmunozse.demojpaentitylistener.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository  extends JpaRepository<Payment, Long> {

}
