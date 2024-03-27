package com.example.ztp_lab1.products;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;


@Data
@Document("products")
public class Product {
    @Id
    private String id;
    private String name;
    private double price;
    private int quantity;
    private List<ChangeHistory> changeHistory;
}
