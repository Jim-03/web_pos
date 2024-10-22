package com.pos.Point.Of.Sale.Entity;

import jakarta.persistence.*;

@Entity
@Table(name = "customers")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false) private String name;
    @Column(unique = true, nullable = false) private String phone;
    @Column(name = "unpaid_debt") private Double unpaidDebt;
    @Column(name = "debt_to_return") private Double debtToReturn;

    public Customer() {}

    public Customer(String name, String phone, Double unpaidDebt, Double debtToReturn) {
        this.name = name;
        this.phone = phone;
        this.unpaidDebt = unpaidDebt;
        this.debtToReturn = debtToReturn;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Double getUnpaidDebt() {
        return unpaidDebt;
    }

    public void setUnpaidDebt(Double unpaidDebt) {
        this.unpaidDebt = unpaidDebt;
    }

    public Double getDebtToReturn() {
        return debtToReturn;
    }

    public void setDebtToReturn(Double debtToReturn) {
        this.debtToReturn = debtToReturn;
    }
}
