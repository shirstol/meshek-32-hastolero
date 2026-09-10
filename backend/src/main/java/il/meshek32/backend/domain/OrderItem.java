package il.meshek32.backend.domain;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "order_items")
public class OrderItem {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private CustomerOrder order;
    @Column(nullable = false, length = 80)
    private String productId;
    @Column(nullable = false, length = 120)
    private String productName;
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal unitPrice;
    @Column(nullable = false)
    private int quantity;

    protected OrderItem() { }
    public OrderItem(CustomerOrder order, String productId, String productName, BigDecimal unitPrice, int quantity) { this.order = order; this.productId = productId; this.productName = productName; this.unitPrice = unitPrice; this.quantity = quantity; }
    public String getProductId() { return productId; }
    public String getProductName() { return productName; }
    public BigDecimal getUnitPrice() { return unitPrice; }
    public int getQuantity() { return quantity; }
}
