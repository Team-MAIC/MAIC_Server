package com.kurly.projectmaic.domain.order.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.hibernate.cfg.AvailableSettings;
import org.hibernate.id.enhanced.SequenceStyleGenerator;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class OrderProduct {

	@Setter
    @Id
    @Column(name = "order_product_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	// @GeneratedValue(strategy = GenerationType.AUTO, generator = "sequence-generator")
	// @GenericGenerator(
	// 	name = "sequence-generator",
	// 	strategy = "sequence",
	// 	parameters = {
	// 		@Parameter(name = SequenceStyleGenerator.SEQUENCE_PARAM, value = SequenceStyleGenerator.DEF_SEQUENCE_NAME),
	// 		@Parameter(name = SequenceStyleGenerator.INITIAL_PARAM, value = "1"),
	// 		@Parameter(name = SequenceStyleGenerator.INCREMENT_PARAM, value = "1000"),
	// 		@Parameter(name = AvailableSettings.PREFERRED_POOLED_OPTIMIZER, value = "pooled-lo")
	// 	}
	// )
    private Long orderProductId;

	@Column(name = "order_info_id")
	private Long orderInfoId;

    @Column(name = "product_id")
    private Long productId;

    @Column(name = "amount")
    private Long amount;
}
