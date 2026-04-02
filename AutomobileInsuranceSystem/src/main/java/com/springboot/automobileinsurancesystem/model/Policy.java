package com.springboot.automobileinsurancesystem.model;

import com.springboot.automobileinsurancesystem.enums.PolicyStatus;
import com.springboot.automobileinsurancesystem.enums.PolicyType;
import com.springboot.automobileinsurancesystem.enums.VehicleClass;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.bind.DefaultValue;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "policies")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Policy { // all fields are required, so no need of validations
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String policy_name;
    private String description;
    private BigDecimal base_premium;
    private BigDecimal sum_insured; // fixed plans
    // duplicate fields in policy, so we can update here, which does not disturb existing records of customers
    private int duration_years;

    @Enumerated(EnumType.STRING)
    @Column(name = "vehicle_class")
    private VehicleClass vehicleClass;

    @Enumerated(EnumType.STRING)
    @Column(name = "policy_type")
    private PolicyType policyType;
}
