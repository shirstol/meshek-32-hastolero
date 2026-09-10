package il.meshek32.backend.api;
import il.meshek32.backend.api.ApiDtos.*;
import il.meshek32.backend.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
@RestController @RequestMapping("/api/orders")
public class OrderController { private final OrderService orders; public OrderController(OrderService orders){this.orders=orders;} @PostMapping @ResponseStatus(HttpStatus.CREATED) public OrderResponse create(@Valid @RequestBody CreateOrderRequest request){return orders.createOrder(request);} }
