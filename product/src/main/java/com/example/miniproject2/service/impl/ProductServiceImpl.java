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
