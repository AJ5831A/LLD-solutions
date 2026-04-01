package com.example.movie;

public enum SeatCategory {
    STANDARD(180),
    PREMIUM(280),
    VIP(450);

    private final int price;

    SeatCategory(int price) {
        this.price = price;
    }

    public int getPrice() { return price; }
}
