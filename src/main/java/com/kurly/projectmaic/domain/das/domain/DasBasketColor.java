package com.kurly.projectmaic.domain.das.domain;


import com.kurly.projectmaic.domain.das.enumeration.BasketColor;
import com.kurly.projectmaic.domain.model.BaseEntity;
import com.kurly.projectmaic.domain.model.StatusType;
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
public class DasBasketColor extends BaseEntity {

    @Id
    @Column(name = "das_basket_color_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long dasBasketColorId;

	@Column(name = "worker_id")
	private Long workerId;

    @Column(name = "product_id")
    private Long productId;

    @Column(name = "color")
    @Enumerated(value = EnumType.STRING)
    private BasketColor color;

    @Column(name = "status")
    @Enumerated(value = EnumType.STRING)
    private StatusType status;
}
