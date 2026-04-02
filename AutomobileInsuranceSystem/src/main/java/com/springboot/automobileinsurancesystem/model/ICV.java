package com.springboot.automobileinsurancesystem.model;

import com.springboot.automobileinsurancesystem.enums.ICVBodyType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "icv")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ICV {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private int max_load; // in tonnes

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "icv_body_type")
    private ICVBodyType icvBodyType;

    @NotNull
    @NotBlank
    private String permit_number;
    private LocalDate fitness_expiry;

    @OneToOne
    private Vehicle vehicle;
}
