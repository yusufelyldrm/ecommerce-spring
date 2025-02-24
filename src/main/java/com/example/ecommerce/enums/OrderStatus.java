package com.example.ecommerce.enums;

public enum OrderStatus {
    PENDING("Beklemede"),
    PLACED("Sipariş Verildi"),
    CONFIRMED("Onaylandı"),
    PROCESSING("İşleniyor"),
    SHIPPED("Kargoya Verildi"),
    DELIVERED("Teslim Edildi"),
    CANCELLED("İptal Edildi"),
    REFUNDED("İade Edildi");

    private final String description;

    OrderStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}