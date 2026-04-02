package com.springboot.automobileinsurancesystem.model;

import com.springboot.automobileinsurancesystem.enums.LCVBodyType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "lcv")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class LCV {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private int max_load; // in tonnes
    private int seating_cap;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "lcv_body_type")
    private LCVBodyType lcvBodyType;
    private boolean is_commercial;

    @OneToOne
    private Vehicle vehicle;
}
