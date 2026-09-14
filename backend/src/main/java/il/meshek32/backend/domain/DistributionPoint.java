package il.meshek32.backend.domain;
import jakarta.persistence.*;
@Entity @Table(name="distribution_points")
public class DistributionPoint {
 @Id @Column(length=80) private String id;
 @Column(unique=true,length=80) private String slug;
 @Column(nullable=false,length=100) private String locality;
 @Column(nullable=false,length=160) private String name;
 @Column(nullable=false,length=250) private String details;
 @Column(length=3000) private String aboutText;
 @Column(nullable=false) private boolean active=true;
 protected DistributionPoint(){}
 public DistributionPoint(String id,String slug,String locality,String name,String details,String aboutText){this.id=id;this.slug=slug;this.locality=locality;this.name=name;this.details=details;this.aboutText=aboutText;}
 public String getId(){return id;} public String getSlug(){return slug;} public String getLocality(){return locality;} public String getName(){return name;} public String getDetails(){return details;} public String getAboutText(){return aboutText;} public boolean isActive(){return active;}
}
