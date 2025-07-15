package com.example.users.domain.equipament;

import com.example.users.domain.BaseEntity;
import com.example.users.domain.customer.Customer;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Table(name = "equipament")
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Equipament extends BaseEntity {
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

}
