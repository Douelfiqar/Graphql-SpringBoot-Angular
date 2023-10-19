package com.example.demo.dtos;

import com.example.demo.entities.Currency;

public record WalletDto(double balance, String currencyCode) {
}
