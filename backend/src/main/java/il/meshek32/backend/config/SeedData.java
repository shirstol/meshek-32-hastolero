package il.meshek32.backend.config;
import il.meshek32.backend.domain.*;
import il.meshek32.backend.repository.*;
import java.math.BigDecimal;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.*;
@Configuration public class SeedData {
 @Bean CommandLineRunner seed(ProductRepository products,DistributionPointRepository points,StoreProductRepository storeProducts){return args->{
  DistributionPoint store=points.findBySlugAndActiveTrue("zippori-store").orElseGet(()->points.save(new DistributionPoint("zippori-store","zippori-store","מושב ציפורי","איסוף מהחנות שלנו","מושב ציפורי","נעים להכיר,\n\nלאחר 34 שנים של עבודה באינטל ממשיך את תשוקת ילדותי – החקלאות.\nביחד עם המשפחה פתחנו חוות בוטיק הידרופונית בה גדלות חסות מעולות מסוג רומית, ליק וסלנובה.\nהחסות שותות בריכות מים, בסביבה מוגנת מחרקים, מזיקים, אדמה ובוץ.")));
  if(storeProducts.findByStore_SlugOrderByProduct_NameAsc("zippori-store").isEmpty()){
   add(products,storeProducts,store,"little-gem","ליטל ג'ם - חסה עם לב","חסות ועלים ירוקים","חסה קטנה ופריכה עם לב עדין.","/images/little-gem.png","10.00");
   add(products,storeProducts,store,"lettuce-crisp","חסה ליק קריספית - מעולה לכריכים וסלט שוק","חסות ועלים ירוקים","חסה פריכה במיוחד, מתאימה לכריכים ולסלט שוק.","/images/lik.png","10.00");
   add(products,storeProducts,store,"romaine","חסה רומית","חסות ועלים ירוקים","מעולה לסלט קיסר ולסלטים משולבי פירות.","/images/romaine.png","10.00");
   add(products,storeProducts,store,"leaf-mix","מיקס עלים - חסלט","חסות ועלים ירוקים","מיקס עלי ליק, סלנובה ירוקה וסלנובה אדומה.",null,"10.00");
   add(products,storeProducts,store,"green-salanova","חסה סלנובה ירוקה - מעולה לסלטי עלים","חסות ועלים ירוקים","חסה ירוקה ועדינה לסלטי עלים.","/images/salnova.png","10.00");
   add(products,storeProducts,store,"baby-arugula","רוקט בייבי מארז של 100 גרם","חסות ועלים ירוקים","עלים צעירים ורכים בטעם נדיר, לכריכים, סלטים ותבלון.",null,"10.00");
   add(products,storeProducts,store,"colorful-salanova","חסה סלנובה צבעונית - מוסיפה גוונים לכל סלט","חסות ועלים ירוקים","סלנובה צבעונית טרייה.",null,"10.00");
   add(products,storeProducts,store,"leek-leaves","עלי ליק בתפזורת","חסות ועלים ירוקים","עלי ליק טריים בתפזורת.",null,"10.00");
   add(products,storeProducts,store,"passionfruit-bamba","פסיפלורה במבה בטעם אקזוטי - מארז 400 גרם","מן הגינה","פסיפלורה במבה בטעם אקזוטי.","/images/passionfruit.png","20.00");
   add(products,storeProducts,store,"pecan","פקאן ענק במארז של 0.5 ק\"ג","מן הגינה","פקאנים גדולים מאוד, ללא ריסוס והדברה.","/images/pecan.png","25.00");
   add(products,storeProducts,store,"rosemary","גבעולי רוזמרין טרי","מן הגינה","זר רוזמרין במשקל 50 גרם.","/images/rosemary.png","7.00");
   add(products,storeProducts,store,"lemon","לימון טרי ועסיסי במארז של 1 ק\"ג","מן הגינה","לימונים טריים ועסיסיים.","/images/lemon.png","12.00");
  }
  setImage(products,"little-gem","/images/little-gem.png");
  setImage(products,"lettuce-crisp","/images/lik.png");
  setImage(products,"romaine","/images/romaine.png");
  setImage(products,"green-salanova","/images/salnova.png");
  setImage(products,"passionfruit-bamba","/images/passionfruit.png");
  setImage(products,"pecan","/images/pecan.png");
  setImage(products,"rosemary","/images/rosemary.png");
  setImage(products,"lemon","/images/lemon.png");
  setDetails(products,"leek-leaves","עלי ליק בתפזורת","עלי ליק טריים בתפזורת.","חסות ועלים ירוקים",null);
 };}
 private static void add(ProductRepository products,StoreProductRepository offers,DistributionPoint store,String id,String name,String category,String description,String imageUrl,String price){Product product=products.findById(id).orElseGet(()->new Product(id,name,description,category,imageUrl,new BigDecimal(price),true,null));product.setImageUrl(imageUrl);products.save(product);offers.save(new StoreProduct(store,product,new BigDecimal(price),true,null));}
 private static void setImage(ProductRepository products,String id,String imageUrl){products.findById(id).ifPresent(product->{product.setImageUrl(imageUrl);products.save(product);});}
 private static void setDetails(ProductRepository products,String id,String name,String description,String category,String imageUrl){products.findById(id).ifPresent(product->{product.updateCatalogDetails(name,description,category,imageUrl);products.save(product);});}
}
