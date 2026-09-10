package il.meshek32.backend.repository;
import il.meshek32.backend.domain.Customer;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
public interface CustomerRepository extends JpaRepository<Customer, Long> { Optional<Customer> findByPhone(String phone); }
