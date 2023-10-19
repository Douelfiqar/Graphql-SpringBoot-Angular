package com.example.demo.service;

import com.example.demo.dtos.WalletDto;
import com.example.demo.entities.Currency;
import com.example.demo.entities.Wallet;
import com.example.demo.entities.WalletTransaction;
import com.example.demo.enums.TransactionType;
import com.example.demo.repositories.CurrencyRepository;
import com.example.demo.repositories.WalletRepository;
import com.example.demo.repositories.WalletTransactionRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;


import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

@Service
@Transactional
@AllArgsConstructor
public class WalletService  {
    private CurrencyRepository currencyRepository;
    private WalletRepository walletRepository;
    private WalletTransactionRepository walletTransactionRepository;
    public void loadData() throws IOException {
        URI uri = new ClassPathResource("static/codes-all.csv").getURI();
        Path path = Paths.get(uri);
        List<String> lines = Files.readAllLines(path);

        for(int i = 1; i < lines.size(); i++){
            String [] line = lines.get(i).split(",");
            Currency currency = Currency.builder().name(line[1]).code(line[0]).symbole(UUID.randomUUID().toString()).salePrice(Math.random() * 5000).puchasePrice(Math.random()*10000).build();
            currencyRepository.save(currency);

        }

        Stream.of("AFGHANISTAN","ALBANIA","AUSTRIA", "BENIN", "BOTSWANA").forEach(currencyCode -> {
            Currency currency1 = currencyRepository.findById(currencyCode).orElseThrow(()-> new RuntimeException(String.format("Currency not found %s", currencyCode)));
            Wallet wallet = Wallet.builder().balance(1000.0).currency(currency1).createdAt(System.currentTimeMillis()).userId("user1").build();
            walletRepository.save(wallet);
        });

        for(int i =0;i<10;i++){
            walletRepository.findAll().forEach(wallet -> {
                WalletTransaction debitWalletTransaction = WalletTransaction.builder()
                        .amount(Math.random() * 10000)
                        .wallet(wallet)
                        .timestamp(System.currentTimeMillis())
                        .type(TransactionType.DEBIT)
                        .build();
                walletTransactionRepository.save(debitWalletTransaction);
                wallet.setBalance(wallet.getBalance() - debitWalletTransaction.getAmount());
                WalletTransaction creditWalletTransaction = WalletTransaction.builder()
                        .amount(Math.random() * 10000)
                        .wallet(wallet)
                        .timestamp(System.currentTimeMillis())
                        .type(TransactionType.CREDIT)
                        .build();

                wallet.setBalance(wallet.getBalance() + creditWalletTransaction.getAmount());

                walletTransactionRepository.save(creditWalletTransaction);
            });

        }
    }

    public Wallet save(WalletDto walletDto){
        Currency currency = currencyRepository.findById(walletDto.currencyCode()).orElseThrow();

        Wallet wallet = Wallet.builder()
                            .userId("user1")
                            .createdAt(System.currentTimeMillis())
                            .balance(walletDto.balance())
                            .currency(currency)
                            .build();
        return walletRepository.save(wallet);
    }
}
