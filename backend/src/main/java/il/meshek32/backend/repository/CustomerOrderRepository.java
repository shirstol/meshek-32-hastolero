package il.meshek32.backend.repository;
import il.meshek32.backend.domain.CustomerOrder;
import java.util.List;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
public interface CustomerOrderRepository extends JpaRepository<CustomerOrder, Long> { @EntityGraph(attributePaths = {"customer", "distributionPoint", "items"}) List<CustomerOrder> findAllByOrderByCreatedAtDesc(); }
