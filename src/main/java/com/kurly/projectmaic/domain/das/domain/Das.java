package com.kurly.projectmaic.domain.das.domain;

import com.kurly.projectmaic.domain.center.domain.Round;
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
public class Das extends BaseEntity {

    @Id
    @Column(name = "das_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long dasId;

    @Column(name = "round_id")
    private Long roundId;

    @Column(name = "passage")
    private Integer passage;

    @Column(name = "status")
    @Enumerated(value = EnumType.STRING)
    private StatusType status;

	public void register(final int passage) {
		this.passage = passage;
	}
}
