package com.example.demo.web;

import com.example.demo.dtos.WalletDto;
import com.example.demo.entities.Wallet;
import com.example.demo.repositories.WalletRepository;
import com.example.demo.service.WalletService;
import lombok.AllArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
@AllArgsConstructor
public class GraphQlController {
    private WalletRepository walletRepository;
    private WalletService walletService;
    @QueryMapping
    public List<Wallet> userWallets(){
        return walletRepository.findAll();
    }

    @QueryMapping
    public Wallet userWalletById(@Argument String id){
        return walletRepository.findById(id).orElseThrow(()->new RuntimeException(String.format("user not found with id = %s", id)));
    }

    @MutationMapping
    public Wallet saveWallet(@Argument WalletDto walletDto){
        return walletService.save(walletDto);
    }

}
