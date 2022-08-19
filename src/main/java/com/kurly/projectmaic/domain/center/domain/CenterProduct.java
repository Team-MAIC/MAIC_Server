package com.kurly.projectmaic.domain.center.domain;

import com.kurly.projectmaic.domain.model.CenterProductArea;
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
public class CenterProduct extends BaseEntity {

    @Id
    @Column(name = "center_product_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long centerProductId;

    @ManyToOne
    @JoinColumn(name = "center_id")
    private Center center;

    @Column(name = "product_id")
    private Long productId;

    @Column(name = "area")
    @Enumerated(value = EnumType.STRING)
    private CenterProductArea area;

	@Column(name = "line")
	private Integer line;

    @Column(name = "location")
    private Integer location;
}
