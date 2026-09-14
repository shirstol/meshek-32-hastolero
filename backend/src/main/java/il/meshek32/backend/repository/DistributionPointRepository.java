package il.meshek32.backend.repository;
import il.meshek32.backend.domain.DistributionPoint;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
public interface DistributionPointRepository extends JpaRepository<DistributionPoint, String> { List<DistributionPoint> findByActiveTrueOrderByLocalityAsc(); Optional<DistributionPoint> findBySlugAndActiveTrue(String slug); }
