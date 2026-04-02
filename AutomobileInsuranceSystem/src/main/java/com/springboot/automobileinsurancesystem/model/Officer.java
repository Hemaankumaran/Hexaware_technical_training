package com.springboot.automobileinsurancesystem.model;

import com.springboot.automobileinsurancesystem.enums.OfficerDesignation;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "officers")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Officer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull
    @NotBlank
    private String name;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "officer_designation")
    private OfficerDesignation officerDesignation;

    @NotNull
    @NotBlank
    private String contact;

    @OneToOne
    private User user;
}
