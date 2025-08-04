package com.ecommerce.model;

import com.ecommerce.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Entity(name = "orders")
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private UUID uuid;

    @Column(nullable = false)
    private String address;

    @Column()
    private String notes;

    @Column(nullable = false)
    private OrderStatus status = OrderStatus.PLACED;

    @ManyToOne()
    @JoinColumn(name = "customer_id", nullable = false, unique = false)
    private Customer customer;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderDetail> orderDetails;

    @PrePersist
    protected void onCreate() {
        this.setUuid(java.util.UUID.randomUUID());
    }
}
