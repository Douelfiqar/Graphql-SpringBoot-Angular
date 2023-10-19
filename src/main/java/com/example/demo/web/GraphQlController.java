package com.example.demo.web;

import com.example.demo.entities.Wallet;
import com.example.demo.repositories.WalletRepository;
import lombok.AllArgsConstructor;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
@AllArgsConstructor
public class GraphQlController {
    private WalletRepository walletRepository;

    @QueryMapping
    public List<Wallet> userWallets(){
        return walletRepository.findAll();
    }

}
