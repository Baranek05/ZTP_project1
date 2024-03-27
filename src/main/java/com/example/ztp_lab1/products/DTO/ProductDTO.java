package com.example.ztp_lab1.products.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ProductDTO {
    private String id;
    private String name;
    private double price;
    private int quantity;
}
