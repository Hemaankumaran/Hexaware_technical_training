package com.springboot.automobileinsurancesystem.model;

import com.springboot.automobileinsurancesystem.enums.PolicyStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "customer_policy")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CustomerPolicy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull
    private BigDecimal premium;
    @NotNull
    private BigDecimal sum_insured;

    @Enumerated(EnumType.STRING)
    @Column(name = "policy_status")
    private PolicyStatus policyStatus;

    private LocalDate inspection_date;
    private LocalDate start_date; // cannot give Instant, needs approval
    private int no_of_years;
    private LocalDate expiry_date;

    @ManyToOne
    private Customer customer;
    @ManyToOne
    private Officer officer;
    @ManyToOne
    private Vehicle vehicle;
    @ManyToOne
    private Policy policy;
}
