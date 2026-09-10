package il.meshek32.backend.config;
import il.meshek32.backend.domain.*;
import il.meshek32.backend.repository.*;
import java.math.BigDecimal;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.*;
@Configuration public class SeedData { @Bean CommandLineRunner seed(ProductRepository products,DistributionPointRepository points){return args->{if(products.count()==0){products.save(new Product("butterhead-lettuce","חסה מסולסלת","חסה טרייה שנקטפה במשק.",new BigDecimal("10.00"),true,2));products.save(new Product("romaine-lettuce","חסה רומית","עלים פריכים המתאימים לסלט ולכריך.",new BigDecimal("12.00"),true,null));products.save(new Product("mixed-greens","מארז עלים ירוקים","מבחר עלים טריים מהחממה.",new BigDecimal("18.00"),false,null));}if(points.count()==0){points.save(new DistributionPoint("example-hartuv","הרטוב","נקודת חלוקה לדוגמה – הרטוב","יום חמישי, 16:00–18:00"));points.save(new DistributionPoint("example-jerusalem","ירושלים","נקודת חלוקה לדוגמה – ירושלים","יום חמישי, 17:00–19:00"));}};}}
