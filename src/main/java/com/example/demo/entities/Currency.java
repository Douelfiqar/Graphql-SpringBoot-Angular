package com.example.demo.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Entity
@Data @AllArgsConstructor @NoArgsConstructor @Builder
public class Currency {
    @Id
    private String code;
    private String name;
    private String symbole;
    private Double salePrice;
    private Double puchasePrice;
    @OneToMany
    private List<Wallet> walletList;
}
