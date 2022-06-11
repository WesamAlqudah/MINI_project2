package com.example.miniproject2.service.impl;

import com.example.miniproject2.domain.Product;
import com.example.miniproject2.repository.ProductRepository;
import com.example.miniproject2.service.ProductService;

import java.util.List;
import java.util.Optional;

public class ProductServiceImpl implements ProductService {
    private ProductRepository productRepository;
    @Override
    public Product getProduct(Long productId) {
        Optional<Product> productOptional = productRepository.findById(productId);
        return productOptional.orElse(null);
    }

    @Override
    public List<Product> getProductByCategory(String category) {
        Optional<List<Product>> productOptional = productRepository.findByCategory(category);
        return productOptional.orElse(null);
    }

    @Override
    public Product updateProduct(Long productId, Product product) {
        Optional<Product> productOptional = productRepository.findById(productId);
        if(productOptional.isPresent()){
            return productRepository.save(product);
        }
        return null;
    }

    @Override
    public boolean removeProduct(Long productId) {
        Optional<Product> productOptional = productRepository.findById(productId);
        if(productOptional.isPresent()){
            productRepository.deleteById(productId);
            return true;
        }
        return false;

    }
}
/*
@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepo orderRepo;

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public Order save(Order p) {
        double MamaMia = 0.00;
        for(int productId:p.getProductIds()){
            Product product =
                    restTemplate.getForObject("http://PRODUCT-SERVICE/product/" + productId
                            ,Product.class);
            MamaMia = MamaMia + Double.parseDouble(product.getPrice());
        }
        p.setTotalPrice(MamaMia);
        return orderRepo.save(p);
    }

    @Override
    public OrderWithProductVO getOrder(int id){
        log.info("Inside getOrderWithProducts of Order-Service");
        OrderWithProductVO orderWithProductVO = new OrderWithProductVO();
        Order order = orderRepo.findByOrderId(id);
        System.out.println(order);
        List<Product> products = new ArrayList<>();
        for(int productId:order.getProductIds()){
            Product product =
                    restTemplate.getForObject("http://PRODUCT-SERVICE/product/" + productId
                            ,Product.class);
            products.add(product);
        }
        orderWithProductVO.setOrder(order);
        orderWithProductVO.setProducts(products);
        return orderWithProductVO;
    }

    @Override
    public List<Order> getAll(){
        var result= new ArrayList<Order>();
         orderRepo.findAll().forEach(item -> {
            Order order = item;
            result.add(order);
        });
         return result;
    }

    @Override
    public Boolean delete(int id) {
        var order = orderRepo.findById(id);
        if(order.isPresent()){
          orderRepo.deleteById(id);
            return true;
        }
        return false;
    }

}*/