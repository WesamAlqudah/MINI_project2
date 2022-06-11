package com.example.miniproject2.repository;

import com.example.miniproject2.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product,Long> {
    Optional<List<Product>> findByCategory(String category);
}
