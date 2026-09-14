package il.meshek32.backend.domain;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "customer_orders")
public class CustomerOrder {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true, length = 32)
    private String orderNumber;
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Customer customer;
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private DistributionPoint distributionPoint;
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> items = new ArrayList<>();
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal total;
    @Enumerated(EnumType.STRING) @Column(nullable = false)
    private OrderStatus status = OrderStatus.RECEIVED;
    @Column(nullable = false)
    private boolean packed;
    @Column(length = 2000)
    private String adminNote;
    @Enumerated(EnumType.STRING) @Column(nullable = false)
    private PaymentMethod paymentMethod = PaymentMethod.NONE;
    @Enumerated(EnumType.STRING) @Column(nullable = false)
    private PaymentStatus paymentStatus = PaymentStatus.NOT_REQUESTED;
    @Column(nullable = false, updatable = false)
    private Instant createdAt = Instant.now();

    protected CustomerOrder() { }
    public CustomerOrder(String orderNumber, Customer customer, DistributionPoint distributionPoint, BigDecimal total, PaymentMethod paymentMethod) {
        this.orderNumber = orderNumber; this.customer = customer; this.distributionPoint = distributionPoint; this.total = total; this.paymentMethod = paymentMethod;
    }
    public void addItem(Product product, BigDecimal price, int quantity) { items.add(new OrderItem(this, product.getId(), product.getName(), price, quantity)); }
    public Long getId() { return id; }
    public String getOrderNumber() { return orderNumber; }
    public Customer getCustomer() { return customer; }
    public DistributionPoint getDistributionPoint() { return distributionPoint; }
    public List<OrderItem> getItems() { return items; }
    public BigDecimal getTotal() { return total; }
    public OrderStatus getStatus() { return status; }
    public boolean isPacked() { return packed; }
    public String getAdminNote() { return adminNote; }
    public PaymentMethod getPaymentMethod() { return paymentMethod; }
    public PaymentStatus getPaymentStatus() { return paymentStatus; }
    public Instant getCreatedAt() { return createdAt; }
    public void updateAdminDetails(OrderStatus status, boolean packed, String adminNote) { this.status = status; this.packed = packed; this.adminNote = adminNote; }
}
