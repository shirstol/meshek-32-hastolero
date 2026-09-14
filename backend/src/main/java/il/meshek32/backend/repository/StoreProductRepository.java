package il.meshek32.backend.repository;
import il.meshek32.backend.domain.StoreProduct;
import java.util.Collection;
import java.util.List;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
public interface StoreProductRepository extends JpaRepository<StoreProduct,Long> {
 @EntityGraph(attributePaths={"store","product"}) List<StoreProduct> findByStore_SlugOrderByProduct_NameAsc(String slug);
 @EntityGraph(attributePaths={"store","product"}) List<StoreProduct> findByStoreIdAndProductIdIn(String storeId,Collection<String> productIds);
}
