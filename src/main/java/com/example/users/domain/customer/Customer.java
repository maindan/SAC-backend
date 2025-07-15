package com.example.users.domain.customer;

import com.example.users.domain.BaseEntity;
import com.example.users.domain.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name = "customer")
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Customer extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String phoneNumber;

    private String registerNumber;

    @Column(nullable = false)
    private Boolean typeBusiness;

    private String address;

    @ManyToOne
    @JoinColumn(name = "created_by")
    private User user;
}
