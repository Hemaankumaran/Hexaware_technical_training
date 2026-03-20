package com.hibernate.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.Instant;

@Entity
public class Fund {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // keep it identity to start numbering from 1
    private long id;
    private String name;

    @Column(name = "aum_amount")
    private BigDecimal aumAmount; // 'asset_under_mgmt' amt

    @Column(name = "expense_ratio")
    private BigDecimal expenseRatio;
    private Instant createdAt;

    // foreign key
    // fund -- (many to one) -- manager
    @ManyToOne
    @JoinColumn(name = "manager_id")
    private Manager manager;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getAumAmount() {
        return aumAmount;
    }

    public void setAumAmount(BigDecimal aumAmount) {
        this.aumAmount = aumAmount;
    }

    public BigDecimal getExpenseRatio() {
        return expenseRatio;
    }

    public void setExpenseRatio(BigDecimal expenseRatio) {
        this.expenseRatio = expenseRatio;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Manager getManager() {
        return manager;
    }

    public void setManager(Manager manager) {
        this.manager = manager;
    }

    @Override
    public String toString() {
        return "Fund{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", aumAmount=" + aumAmount +
                ", expenseRatio=" + expenseRatio +
                ", createdAt=" + createdAt +
                ", manager=" + manager +
                '}';
    }
}
