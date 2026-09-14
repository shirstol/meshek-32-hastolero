package il.meshek32.backend.api;
import il.meshek32.backend.api.ApiDtos.*;
import il.meshek32.backend.repository.*;
import il.meshek32.backend.service.OrderService;
import java.util.List;
import org.springframework.web.bind.annotation.*;
@RestController @RequestMapping("/api")
public class CatalogController {
 private final ProductRepository products; private final DistributionPointRepository points; private final OrderService orders;
 public CatalogController(ProductRepository products,DistributionPointRepository points,OrderService orders){this.products=products;this.points=points;this.orders=orders;}
 @GetMapping("/products") public List<ProductResponse> products(){return products.findAll().stream().map(OrderService::productResponse).toList();}
 @GetMapping("/distribution-points") public List<DistributionPointResponse> points(){return points.findByActiveTrueOrderByLocalityAsc().stream().map(OrderService::pointResponse).toList();}
 @GetMapping("/stores/{slug}") public StoreCatalogResponse store(@PathVariable String slug){return orders.storeCatalog(slug);}
}
