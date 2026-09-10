package il.meshek32.backend.service;

import il.meshek32.backend.api.ApiDtos.*;
import il.meshek32.backend.domain.*;
import il.meshek32.backend.repository.*;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
public class OrderService {
 private final CustomerRepository customers; private final ProductRepository products; private final DistributionPointRepository distributionPoints; private final CustomerOrderRepository orders;
 public OrderService(CustomerRepository customers, ProductRepository products, DistributionPointRepository distributionPoints, CustomerOrderRepository orders) { this.customers=customers; this.products=products; this.distributionPoints=distributionPoints; this.orders=orders; }
 @Transactional public CustomerResponse identifyCustomer(CustomerRequest request) {
  String phone=normalizePhone(request.phone()); Customer customer=customers.findByPhone(phone).map(existing->{existing.updateName(request.fullName().trim());return existing;}).orElseGet(()->new Customer(request.fullName().trim(),phone)); Customer saved=customers.save(customer); return customerResponse(saved);
 }
 @Transactional public OrderResponse createOrder(CreateOrderRequest request) {
  CustomerResponse customerResponse=identifyCustomer(request.customer()); Customer customer=customers.getReferenceById(customerResponse.id());
  DistributionPoint point=distributionPoints.findById(request.distributionPointId()).filter(DistributionPoint::isActive).orElseThrow(()->badRequest("נקודת החלוקה אינה זמינה"));
  Map<String,Integer> requested=new LinkedHashMap<>(); for(OrderItemRequest item:request.items()) requested.merge(item.productId(),item.quantity(),Integer::sum);
  List<Product> selected=products.findAllById(requested.keySet()); if(selected.size()!=requested.size()) throw badRequest("אחד המוצרים לא נמצא"); BigDecimal total=BigDecimal.ZERO;
  for(Product product:selected){int quantity=requested.get(product.getId()); if(!product.isAvailable()) throw badRequest(product.getName()+" אינו זמין"); if(product.getMaxQuantity()!=null&&quantity>product.getMaxQuantity()) throw badRequest("חרגת ממגבלת הכמות של "+product.getName()); total=total.add(product.getPrice().multiply(BigDecimal.valueOf(quantity)));}
  CustomerOrder order=new CustomerOrder("M32-"+UUID.randomUUID().toString().substring(0,8).toUpperCase(),customer,point,total,request.paymentMethod()); selected.forEach(product->order.addItem(product,requested.get(product.getId()))); return orderResponse(orders.save(order));
 }
 @Transactional(readOnly=true) public List<OrderResponse> findOrders(String pickupPointId,String productId,String customerQuery,Boolean packed,OrderStatus status){ return orders.findAllByOrderByCreatedAtDesc().stream().filter(order->pickupPointId==null||order.getDistributionPoint().getId().equals(pickupPointId)).filter(order->productId==null||order.getItems().stream().anyMatch(item->item.getProductId().equals(productId))).filter(order->packed==null||order.isPacked()==packed).filter(order->status==null||order.getStatus()==status).filter(order->customerQuery==null||matchesCustomer(order,customerQuery)).map(this::orderResponse).toList(); }
 @Transactional public OrderResponse updateAdminOrder(Long id,AdminOrderUpdate update){ CustomerOrder order=orders.findById(id).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"ההזמנה לא נמצאה")); order.updateAdminDetails(update.status(),update.packed(),blankToNull(update.adminNote())); return orderResponse(order); }
 @Transactional(readOnly=true) public DashboardResponse dashboard(String pickupPointId){ List<CustomerOrder> filtered=orders.findAllByOrderByCreatedAtDesc().stream().filter(order->pickupPointId==null||order.getDistributionPoint().getId().equals(pickupPointId)).toList(); BigDecimal revenue=filtered.stream().map(CustomerOrder::getTotal).reduce(BigDecimal.ZERO,BigDecimal::add); Map<DistributionPoint,List<CustomerOrder>> groups=filtered.stream().collect(Collectors.groupingBy(CustomerOrder::getDistributionPoint)); List<PickupSummary> summaries=groups.entrySet().stream().map(entry->new PickupSummary(entry.getKey().getId(),entry.getKey().getName(),entry.getValue().size(),entry.getValue().stream().map(CustomerOrder::getTotal).reduce(BigDecimal.ZERO,BigDecimal::add))).toList(); return new DashboardResponse(filtered.size(),filtered.stream().filter(CustomerOrder::isPacked).count(),filtered.stream().filter(order->order.getStatus()!=OrderStatus.DELIVERED&&order.getStatus()!=OrderStatus.CANCELLED).count(),revenue,summaries); }
 public static CustomerResponse customerResponse(Customer customer){return new CustomerResponse(customer.getId(),customer.getFullName(),customer.getPhone());}
 public static ProductResponse productResponse(Product product){return new ProductResponse(product.getId(),product.getName(),product.getDescription(),product.getPrice(),product.isAvailable(),product.getMaxQuantity());}
 public static DistributionPointResponse pointResponse(DistributionPoint point){return new DistributionPointResponse(point.getId(),point.getLocality(),point.getName(),point.getDetails());}
 private OrderResponse orderResponse(CustomerOrder order){return new OrderResponse(order.getId(),order.getOrderNumber(),customerResponse(order.getCustomer()),pointResponse(order.getDistributionPoint()),order.getItems().stream().map(item->new OrderItemResponse(item.getProductId(),item.getProductName(),item.getUnitPrice(),item.getQuantity())).toList(),order.getTotal(),order.getStatus(),order.isPacked(),order.getAdminNote(),order.getPaymentMethod(),order.getPaymentStatus(),order.getCreatedAt());}
 private static boolean matchesCustomer(CustomerOrder order,String query){String lower=query.toLowerCase(Locale.ROOT).trim();return order.getCustomer().getFullName().toLowerCase(Locale.ROOT).contains(lower)||order.getCustomer().getPhone().contains(query.trim());}
 private static String normalizePhone(String phone){return phone.replaceAll("[^0-9+]","");}
 private static String blankToNull(String value){return value==null||value.isBlank()?null:value.trim();}
 private static ResponseStatusException badRequest(String message){return new ResponseStatusException(HttpStatus.BAD_REQUEST,message);}
}
