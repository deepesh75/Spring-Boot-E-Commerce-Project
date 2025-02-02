package com.example.ecom_project.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.ecom_project.model.Product;

@Repository
public interface ProductRepo extends JpaRepository<Product, Integer> {
  // JPQL JPA Query Language
  // It similar to SQL, just the difference is
  // In sql we use table name, here we use class name.
  // In sql we use colomn name here we use field name.

  @Query("select p from Product p where " +
      "lower(p.name) like lower( concat('%', :keyword,'%')) or " +
      "lower(p.description) like lower( concat('%', :keyword,'%')) or " +
      "lower(p.brand) like lower( concat('%', :keyword,'%')) or " +
      "lower(p.category) like lower( concat('%', :keyword,'%')) ")
  List<Product> searchProducts(String keyword);
}
