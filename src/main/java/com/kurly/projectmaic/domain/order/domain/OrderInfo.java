package com.kurly.projectmaic.domain.order.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.kurly.projectmaic.domain.model.BaseEntity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

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

    @Column(name = "total_price")
    private Long totalPrice;
}
