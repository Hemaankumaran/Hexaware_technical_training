package com.springboot.automobileinsurancesystem.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Table(name = "customers")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @NotNull
    @NotBlank
    private String name;
    private int age;
    @NotNull
    @NotBlank
    private String contact;
    @Email
    private String email;
    private String address;
    @NotNull
    @NotBlank
    private String license_number;
    private String aadhaar_number;

    @OneToOne
    private User user;
}
