package il.meshek32.backend.api;
import il.meshek32.backend.api.ApiDtos.*;
import il.meshek32.backend.domain.OrderStatus;
import il.meshek32.backend.service.OrderService;
import jakarta.validation.Valid;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.UUID;
import org.springframework.http.MediaType;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.*;
@RestController @RequestMapping("/api/admin")
public class AdminController { private final OrderService orders; public AdminController(OrderService orders){this.orders=orders;}
 @GetMapping("/orders") public List<OrderResponse> orders(@RequestParam(required=false) String pickupPointId,@RequestParam(required=false) String productId,@RequestParam(required=false) String customerQuery,@RequestParam(required=false) Boolean packed,@RequestParam(required=false) OrderStatus status){return orders.findOrders(pickupPointId,productId,customerQuery,packed,status);}
 @PatchMapping("/orders/{id}") public OrderResponse update(@PathVariable Long id,@Valid @RequestBody AdminOrderUpdate request){return orders.updateAdminOrder(id,request);}
 @GetMapping("/dashboard") public DashboardResponse dashboard(@RequestParam(required=false) String pickupPointId){return orders.dashboard(pickupPointId);}
 @PostMapping("/stores/{slug}/products") public ProductResponse addProduct(@PathVariable String slug,@Valid @RequestBody AdminStoreProductRequest request){return orders.addProductToStore(slug,request);}
 @PatchMapping("/stores/{slug}/products/{productId}") public ProductResponse updateProduct(@PathVariable String slug,@PathVariable String productId,@Valid @RequestBody AdminStoreProductRequest request){return orders.updateProductInStore(slug,productId,request);}
 @DeleteMapping("/stores/{slug}/products/{productId}") public void removeProduct(@PathVariable String slug,@PathVariable String productId){orders.removeProductFromStore(slug,productId);}
 @PostMapping(path="/uploads",consumes=MediaType.MULTIPART_FORM_DATA_VALUE) public ImageUploadResponse upload(@RequestPart("image") MultipartFile image)throws IOException{if(image.isEmpty()||image.getContentType()==null||!image.getContentType().startsWith("image/"))throw new IllegalArgumentException("יש לבחור קובץ תמונה");if(image.getSize()>5*1024*1024)throw new IllegalArgumentException("גודל התמונה המרבי הוא 5MB");String extension=image.getOriginalFilename()!=null&&image.getOriginalFilename().contains(".")?image.getOriginalFilename().substring(image.getOriginalFilename().lastIndexOf('.')).replaceAll("[^A-Za-z0-9.]",""):".jpg";Path directory=Path.of("uploads").toAbsolutePath().normalize();Files.createDirectories(directory);String filename=UUID.randomUUID()+extension.toLowerCase();Files.copy(image.getInputStream(),directory.resolve(filename));return new ImageUploadResponse("/images/"+filename);}
}
