package com.example.ecom_project.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.ecom_project.service.ProductService;
import com.example.ecom_project.model.Product;

@CrossOrigin
@RestController
@RequestMapping("/api")
public class ProductController {

  @Autowired
  private ProductService service;

  @RequestMapping("/products")
  // with ResponceEntity we send both data as well as status code
  public ResponseEntity<List<Product>> getAllProducts() {
    return new ResponseEntity<>(service.getAllProducts(), HttpStatus.OK);
  }

  @RequestMapping("/product/{id}")
  public ResponseEntity<Product> geProductById(@PathVariable int id) {
    Product product = service.getProductById(id);
    if (product != null)
      return new ResponseEntity<>(service.getProductById(id), HttpStatus.OK);
    else
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
  }

  // we add question mark, when we are not sure about our return type
  @PostMapping("/product")
  public ResponseEntity<?> addProduct(@RequestPart Product product,
      @RequestPart MultipartFile imageFile) {
    try {
      Product prod = service.addProduct(product, imageFile);
      return new ResponseEntity<>(prod, HttpStatus.CREATED);
    } catch (Exception e) {
      return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

  }

  // react uses below url to display product images
  @GetMapping("/product/{productId}/image")
  public ResponseEntity<byte[]> getImageByProductId(@PathVariable int productId) {
    Product product = service.getProductById(productId);
    byte[] imageFile = product.getImageData();

    return new ResponseEntity<>(imageFile, HttpStatus.OK);

  }

  @PutMapping("/product/{id}")
  public ResponseEntity<String> updateProduct(@PathVariable int id, @RequestPart Product product,
      @RequestPart MultipartFile imageFile) {
    Product prod;
    try {
      prod = service.updateProduct(id, product, imageFile);
    } catch (IOException e) {
      return new ResponseEntity<>("Failed to update", HttpStatus.BAD_REQUEST);
    }
    if (prod != null)
      return new ResponseEntity<>("Updated", HttpStatus.OK);
    else
      return new ResponseEntity<>("Failed to update", HttpStatus.BAD_REQUEST);
  }

  @DeleteMapping("/product/{id}")
  public ResponseEntity<String> deleteProduct(@PathVariable int id) {
    Product product = service.getProductById(id);
    if (product != null) {
      service.deleteProduct(id);
      return new ResponseEntity<>("Product deleted", HttpStatus.OK);
    } else {
      return new ResponseEntity<>("product not found", HttpStatus.NOT_FOUND);
    }
  }

  @GetMapping("/products/search")
  public ResponseEntity<List<Product>> seachProducts(@RequestParam String keyword) {
    System.out.println("Searching with " + keyword);
    List<Product> products = service.seachProducts(keyword);
    return new ResponseEntity<>(products, HttpStatus.OK);

  }

}
