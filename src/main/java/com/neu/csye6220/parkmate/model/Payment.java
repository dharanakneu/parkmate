package com.neu.csye6220.parkmate.model;

import jakarta.persistence.*;
import org.springframework.stereotype.Component;

@Component
@Entity
@Table(name = "payments")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private double amount;

    private String status;

    @ManyToOne
    @JoinColumn(name = "rentee_id", nullable = false)
    private Rentee sender;

    @ManyToOne
    @JoinColumn(name = "renter_id", nullable = false)
    private Renter receiver;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Rentee getSender() {
        return sender;
    }

    public void setSender(Rentee sender) {
        this.sender = sender;
    }

    public Renter getReceiver() {
        return receiver;
    }

    public void setReceiver(Renter receiver) {
        this.receiver = receiver;
    }
}
