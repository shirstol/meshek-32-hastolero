package il.meshek32.backend.domain;

import jakarta.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "customers", uniqueConstraints = @UniqueConstraint(columnNames = "phone"))
public class Customer {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = 120)
    private String fullName;
    @Column(nullable = false, length = 30)
    private String phone;
    @Column(nullable = false, updatable = false)
    private Instant createdAt = Instant.now();

    protected Customer() { }
    public Customer(String fullName, String phone) { this.fullName = fullName; this.phone = phone; }
    public Long getId() { return id; }
    public String getFullName() { return fullName; }
    public String getPhone() { return phone; }
    public Instant getCreatedAt() { return createdAt; }
    public void updateName(String fullName) { this.fullName = fullName; }
}
