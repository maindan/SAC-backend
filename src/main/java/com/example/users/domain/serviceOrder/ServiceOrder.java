package com.example.users.domain.serviceOrder;

import com.example.users.domain.BaseEntity;
import com.example.users.domain.customer.Customer;
import com.example.users.domain.equipament.Equipament;
import com.example.users.domain.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Table(name = "service_order")
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ServiceOrder extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    private Date entryDate;
    private Date budgetDate;
    private Integer budgetValue;
    private Date clientFeedbackDate;
    private Date removalDate;
    private String type;
    private String status;
    private String fiscalNumber;
    private String observation;

    @ManyToMany
    @JoinTable(
            name="order_service_equipament",
            joinColumns = @JoinColumn(name = "service_order_id"),
            inverseJoinColumns = @JoinColumn(name = "equipament_id")
    )
    private List<Equipament> equipaments;

    @ManyToOne
    @JoinColumn(name = "created_by")
    private User user;
}
