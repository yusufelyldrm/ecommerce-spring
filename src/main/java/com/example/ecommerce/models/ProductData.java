package com.example.ecommerce.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
public class ProductData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String idocNo;

    //içerisinde birden fazla product olabilir
    @OneToMany(mappedBy = "productData", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Product> products;
}
