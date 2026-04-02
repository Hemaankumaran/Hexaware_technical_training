package com.springboot.automobileinsurancesystem.model;

import com.springboot.automobileinsurancesystem.enums.HCVBodyType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "hcv")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class HCV {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private int max_load; // in tonnes
    private int no_of_axles;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "hcv_body_type")
    private HCVBodyType hcvBodyType;

    @NotNull
    @NotBlank
    private String permit_number;

    @OneToOne
    private Vehicle vehicle;
}
