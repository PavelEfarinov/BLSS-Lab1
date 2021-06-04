package ru.blss.lab1.domain.order;

import lombok.Data;
import ru.blss.lab1.domain.Courier;
import ru.blss.lab1.domain.OrderItem;
import ru.blss.lab1.domain.User;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "orders")
@Data
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Courier courier;

    @ManyToOne
    private User client;

    @OneToMany
    private List<OrderItem> items;

    @NotNull
    @NotBlank
    private String address;

    @Column(nullable = false)
    @NotNull
    @NotBlank
    private LocalDateTime formedDate;

    @NotNull
    @NotBlank
    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;

    @Column(nullable = false)
    @NotNull
    @NotBlank
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    @Enumerated(EnumType.STRING)
    private DeliveryMethod deliveryMethod;

    @PrePersist
    void preInsert() {
        if (this.formedDate == null)
            this.formedDate = LocalDateTime.now();

        if (this.orderStatus == null)
            this.orderStatus = OrderStatus.FORMED;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", address='" + address + '\'' +
                ", formedDate=" + formedDate +
                ", paymentStatus=" + paymentStatus +
                ", orderStatus=" + orderStatus +
                ", deliveryMethod=" + deliveryMethod +
                '}';
    }
}
