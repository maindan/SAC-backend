package com.example.users.domain.equipament;

import com.example.users.domain.customer.Customer;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.Date;

@Table(name = "equipament")
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Equipament {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "type_id")
    private EquipamentType type;

    @Column(nullable = false)
    private String model;
    private String fabricator;
    private Date buyDate;
    private String voltage;
    private String serialNumber;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt = Instant.now();

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt = Instant.now();

}
