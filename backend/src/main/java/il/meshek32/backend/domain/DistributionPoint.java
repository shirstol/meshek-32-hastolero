package il.meshek32.backend.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "distribution_points")
public class DistributionPoint {
    @Id @Column(length = 80)
    private String id;
    @Column(nullable = false, length = 100)
    private String locality;
    @Column(nullable = false, length = 160)
    private String name;
    @Column(nullable = false, length = 250)
    private String details;
    @Column(nullable = false)
    private boolean active = true;

    protected DistributionPoint() { }
    public DistributionPoint(String id, String locality, String name, String details) { this.id = id; this.locality = locality; this.name = name; this.details = details; }
    public String getId() { return id; }
    public String getLocality() { return locality; }
    public String getName() { return name; }
    public String getDetails() { return details; }
    public boolean isActive() { return active; }
}
