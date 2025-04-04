package com.example.users.domain.serviceOrder;

import com.example.users.domain.customer.Customer;
import com.example.users.domain.equipament.Equipament;
import com.example.users.domain.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.time.Instant;
import java.util.Date;
import java.util.List;

@Table(name = "service_order")
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ServiceOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @Column(nullable = false)
    private Date entryDate;
    private Date budgetDate;
    private Float budgetValue;
    private Date warrantyClaimDate;
    private Date warrantyApprovalDate;
    private Date removalDate;

    @Enumerated(EnumType.STRING)
    private ServiceOrderType type;

    @Enumerated(EnumType.STRING)
    private ServiceOrderStatus status;

    private String fiscalNumber;
    private String observation;

    @ManyToMany
    @JoinTable(
            name="order_service_equipament",
            joinColumns = @JoinColumn(name = "service_order_id"),
            inverseJoinColumns = @JoinColumn(name = "equipament_id")
    )
    private List<Equipament> equipaments;

    @OneToOne
    @JoinColumn(name = "parent_id", referencedColumnName = "id", nullable = true)
    private ServiceOrder parentServiceOrder;

    @ManyToOne
    @JoinColumn(name = "created_by")
    private User user;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt = Instant.now();

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt = Instant.now();
}
