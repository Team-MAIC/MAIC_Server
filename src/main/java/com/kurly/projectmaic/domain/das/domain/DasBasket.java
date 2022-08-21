package com.kurly.projectmaic.domain.das.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class DasBasket {

	@Id
	@Column(name = "das_basket_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long dasBasketId;

	@Column(name = "order_info_id")
	private Long orderInfoId;

	@Column(name = "basket_num")
	private int basketNum;
}
