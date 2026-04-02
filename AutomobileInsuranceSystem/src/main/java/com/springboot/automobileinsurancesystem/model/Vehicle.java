package com.springboot.automobileinsurancesystem.model;

import com.springboot.automobileinsurancesystem.enums.FuelType;
import com.springboot.automobileinsurancesystem.enums.HCVBodyType;
import com.springboot.automobileinsurancesystem.enums.VehicleClass;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Year;

@Entity
@Table(name = "vehicles")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Vehicle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @NotNull
    @NotBlank
    private String reg_no;
    private String model;
    private BigDecimal original_showroom_price;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "fuel_type")
    private FuelType fuelType;
    private Year mfg_year;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "vehicle_class")
    private VehicleClass vehicleClass;
}
