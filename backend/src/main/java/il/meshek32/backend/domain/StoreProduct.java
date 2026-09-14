package il.meshek32.backend.domain;
import jakarta.persistence.*;
import java.math.BigDecimal;
@Entity @Table(name="store_products",uniqueConstraints=@UniqueConstraint(columnNames={"store_id","product_id"}))
public class StoreProduct {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 @ManyToOne(optional=false,fetch=FetchType.LAZY) @JoinColumn(name="store_id") private DistributionPoint store;
 @ManyToOne(optional=false,fetch=FetchType.LAZY) @JoinColumn(name="product_id") private Product product;
 @Column(nullable=false,precision=10,scale=2) private BigDecimal price;
 @Column(nullable=false) private boolean available=true;
 private Integer maxQuantity;
 protected StoreProduct(){}
 public StoreProduct(DistributionPoint store,Product product,BigDecimal price,boolean available,Integer maxQuantity){this.store=store;this.product=product;this.price=price;this.available=available;this.maxQuantity=maxQuantity;}
 public DistributionPoint getStore(){return store;} public Product getProduct(){return product;} public BigDecimal getPrice(){return price;} public boolean isAvailable(){return available;} public Integer getMaxQuantity(){return maxQuantity;}
}
