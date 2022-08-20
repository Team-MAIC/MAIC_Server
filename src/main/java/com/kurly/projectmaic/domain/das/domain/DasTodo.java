package com.kurly.projectmaic.domain.das.domain;

import com.kurly.projectmaic.domain.das.enumeration.BasketStatus;
import com.kurly.projectmaic.domain.model.BaseEntity;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class DasTodo extends BaseEntity {

    @Id
    @Column(name = "das_todo_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long dasTodoId;

    @ManyToOne
    @JoinColumn(name = "das_basket_color_id")
    private DasBasketColor dasBasketColor;

    @Column(name = "order_info_id")
    private Long orderInfoId;

    @Column(name = "product_id")
    private Long productId;

	@Column(name = "das_id")
	private Long dasId;

    @Column(name = "product_name")
    private String productName;

    @Column(name = "product_amount")
    private Integer productAmount;

    @Column(name = "product_weight")
    private Double productWeight;

    @Column(name = "basket_location")
    private Integer basketLocation;

    @Column(name = "basket_weight")
    private Double basketWeight;

    @Column(name = "status")
    @Enumerated(value = EnumType.STRING)
    private BasketStatus status;
}
