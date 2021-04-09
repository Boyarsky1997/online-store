package com.github.boyarsky1997.store.model;

import java.sql.Date;

public class Order {
    private int id;
    private int buyerId;
    private double price;
    private int count;
    private Date date;
    private Status status;

    public Order() {
    }

    public Order(int id, int buyerId, double price, int count, Date date, Status status) {
        this.id = id;
        this.buyerId = buyerId;
        this.price = price;
        this.count = count;
        this.date = date;
        this.status = status;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(int buyerId) {
        this.buyerId = buyerId;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", buyerId=" + buyerId +
                ", price=" + price +
                ", count=" + count +
                ", date=" + date +
                ", status=" + status +
                '}';
    }
}
