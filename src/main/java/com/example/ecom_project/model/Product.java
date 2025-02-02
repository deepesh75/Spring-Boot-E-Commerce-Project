package com.example.ecom_project.model;

import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity // to create the table, bcoz jpa needs entity
@Data // below 3 are lombok anotations
@AllArgsConstructor
@NoArgsConstructor

public class Product {

  @Id // primary key
  @GeneratedValue(strategy = GenerationType.IDENTITY) // this will auto generate the id for
  // our products. i.e., 1,2,3 and so on.
  private int id;
  private String name;
  private String description;
  private String brand;
  private BigDecimal price;
  private String category;

  // 2024-01-14T18:30:00.000+00:00 will be converted to 14-01-2024
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "DD-MM-YYYY")
  private Date releaseDate;
  private boolean productAvailable;
  private int stockQuantity;

  // file name, file type and file data
  private String imageName;
  private String imageType;
  // we will store the image in the database
  @Lob // large object
  private byte[] imageData;

}
