package com.springboot.automobileinsurancesystem.model;

import com.springboot.automobileinsurancesystem.enums.ClaimStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "claims")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Claim {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull
    private LocalDate incident_date;
    private LocalDate claim_date; // when the ClaimStatus is APPROVED

    @NotNull
    private BigDecimal claim_amt;

    @NotNull
    @NotBlank
    @Column(length = 1000)
    private String claim_desc;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "claim_status")
    private ClaimStatus claimStatus;

    @ManyToOne
    private CustomerPolicy customerPolicy;
}
