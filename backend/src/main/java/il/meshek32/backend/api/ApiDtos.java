package il.meshek32.backend.api;
import il.meshek32.backend.domain.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
public final class ApiDtos {
 private ApiDtos() { }
 public record CustomerRequest(@NotBlank @Size(max = 120) String fullName, @NotBlank @Size(max = 30) String phone) { }
 public record CustomerResponse(Long id, String fullName, String phone) { }
 public record ProductResponse(String id, String name, String description, String category, String imageUrl, BigDecimal price, boolean available, Integer maxQuantity) { }
 public record DistributionPointResponse(String id, String locality, String name, String details) { }
 public record StoreResponse(String id, String slug, String locality, String name, String details, String aboutText) { }
 public record StoreCatalogResponse(StoreResponse store, List<ProductResponse> products) { }
 public record AdminStoreProductRequest(@NotBlank @Size(max=120) String name, @NotBlank @Size(max=400) String description, @NotBlank @Size(max=80) String category, @Size(max=1000) String imageUrl, @NotNull @DecimalMin("0.01") BigDecimal price, boolean available, @Min(1) Integer maxQuantity) { }
 public record ImageUploadResponse(String imageUrl) { }
 public record OrderItemRequest(@NotBlank String productId, @Min(1) @Max(100) int quantity) { }
 public record CreateOrderRequest(@Valid @NotNull CustomerRequest customer, @NotBlank String distributionPointId, @NotEmpty List<@Valid OrderItemRequest> items, @NotNull PaymentMethod paymentMethod) { }
 public record OrderItemResponse(String productId, String productName, BigDecimal unitPrice, int quantity) { }
 public record OrderResponse(Long id, String orderNumber, CustomerResponse customer, DistributionPointResponse distributionPoint, List<OrderItemResponse> items, BigDecimal total, OrderStatus status, boolean packed, String adminNote, PaymentMethod paymentMethod, PaymentStatus paymentStatus, Instant createdAt) { }
 public record AdminOrderUpdate(@NotNull OrderStatus status, boolean packed, @Size(max = 2000) String adminNote) { }
 public record DashboardResponse(long totalOrders, long packedOrders, long openOrders, BigDecimal totalRevenue, List<PickupSummary> byPickupPoint) { }
 public record PickupSummary(String distributionPointId, String distributionPointName, long orders, BigDecimal revenue) { }
}
