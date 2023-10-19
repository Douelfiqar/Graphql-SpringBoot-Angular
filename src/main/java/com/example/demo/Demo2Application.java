package com.example.demo;

import com.example.demo.entities.Currency;
import com.example.demo.repositories.CurrencyRepository;
import com.example.demo.service.WalletService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Demo2Application {

    public static void main(String[] args) {
        SpringApplication.run(Demo2Application.class, args);
    }

    private CurrencyRepository currencyRepository;

    @Bean
    CommandLineRunner start(WalletService walletService){
        return args -> {
            walletService.loadData();

        };
    }

}
