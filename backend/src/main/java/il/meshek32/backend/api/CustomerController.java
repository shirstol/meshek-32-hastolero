package il.meshek32.backend.api;
import il.meshek32.backend.api.ApiDtos.*;
import il.meshek32.backend.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
@RestController @RequestMapping("/api/customers")
public class CustomerController { private final OrderService orders; public CustomerController(OrderService orders){this.orders=orders;} @PostMapping("/identify") public CustomerResponse identify(@Valid @RequestBody CustomerRequest request){return orders.identifyCustomer(request);} }
