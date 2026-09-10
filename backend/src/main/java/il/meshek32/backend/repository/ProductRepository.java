package il.meshek32.backend.repository;
import il.meshek32.backend.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;
public interface ProductRepository extends JpaRepository<Product, String> { }
