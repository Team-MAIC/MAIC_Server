package com.kurly.projectmaic.domain.order.domain;

import com.kurly.projectmaic.domain.model.BaseEntity;
import com.kurly.projectmaic.domain.order.enumeration.OrderStatus;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class OrderInfo extends BaseEntity {

    @Id
    @Column(name = "order_info_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderInfoId;

    @Column(name = "round_id")
    private Long roundId;

    @Column(name = "consumer_id")
    private Long consumerId;

    @Column(name = "totla_price")
    private Long totalPrice;

    @Column(name = "status")
    @Enumerated(value = EnumType.STRING)
    private OrderStatus status;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "order")
    private List<OrderProduct> orderProducts = new ArrayList<>();
}
