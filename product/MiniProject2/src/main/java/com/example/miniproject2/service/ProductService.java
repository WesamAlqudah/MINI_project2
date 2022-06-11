package com.example.miniproject2.service;

import com.example.miniproject2.domain.Product;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ProductService {
    Product getProduct(Long productId);
    List<Product> getProductByCategory(String category);
    Product updateProduct(Long productId, Product product);
    boolean removeProduct(Long productId);
}
