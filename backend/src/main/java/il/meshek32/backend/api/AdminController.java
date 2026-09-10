package il.meshek32.backend.api;
import il.meshek32.backend.api.ApiDtos.*;
import il.meshek32.backend.domain.OrderStatus;
import il.meshek32.backend.service.OrderService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.web.bind.annotation.*;
@RestController @RequestMapping("/api/admin")
public class AdminController { private final OrderService orders; public AdminController(OrderService orders){this.orders=orders;}
 @GetMapping("/orders") public List<OrderResponse> orders(@RequestParam(required=false) String pickupPointId,@RequestParam(required=false) String productId,@RequestParam(required=false) String customerQuery,@RequestParam(required=false) Boolean packed,@RequestParam(required=false) OrderStatus status){return orders.findOrders(pickupPointId,productId,customerQuery,packed,status);}
 @PatchMapping("/orders/{id}") public OrderResponse update(@PathVariable Long id,@Valid @RequestBody AdminOrderUpdate request){return orders.updateAdminOrder(id,request);}
 @GetMapping("/dashboard") public DashboardResponse dashboard(@RequestParam(required=false) String pickupPointId){return orders.dashboard(pickupPointId);}
}
