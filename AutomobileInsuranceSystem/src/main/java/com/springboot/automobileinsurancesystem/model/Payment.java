package com.springboot.automobileinsurancesystem.model;

import com.springboot.automobileinsurancesystem.enums.PaymentStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name = "payments")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "payment_status")
    private PaymentStatus paymentStatus;

    @NotNull
    private BigDecimal amt;

    @CreationTimestamp
    @Column(updatable = false)
    private Instant payment_date;

    @NotNull
    @NotBlank
    private String transaction_id;

    @ManyToOne
    private CustomerPolicy customerPolicy;
}
