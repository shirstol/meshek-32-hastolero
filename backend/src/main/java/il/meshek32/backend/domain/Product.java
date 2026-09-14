package il.meshek32.backend.domain;
import jakarta.persistence.*;
import java.math.BigDecimal;
@Entity @Table(name="products")
public class Product {
 @Id @Column(length=80) private String id;
 @Column(nullable=false,length=120) private String name;
 @Column(nullable=false,length=400) private String description;
 @Column(length=80) private String category;
 @Column(length=1000) private String imageUrl;
 @Column(nullable=false,precision=10,scale=2) private BigDecimal price;
 @Column(nullable=false) private boolean available;
 private Integer maxQuantity;
 protected Product(){}
 public Product(String id,String name,String description,String category,String imageUrl,BigDecimal price,boolean available,Integer maxQuantity){this.id=id;this.name=name;this.description=description;this.category=category;this.imageUrl=imageUrl;this.price=price;this.available=available;this.maxQuantity=maxQuantity;}
 public String getId(){return id;} public String getName(){return name;} public String getDescription(){return description;} public String getCategory(){return category;} public String getImageUrl(){return imageUrl;} public BigDecimal getPrice(){return price;} public boolean isAvailable(){return available;} public Integer getMaxQuantity(){return maxQuantity;}
}
