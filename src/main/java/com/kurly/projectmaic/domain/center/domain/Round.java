package com.kurly.projectmaic.domain.center.domain;

import com.kurly.projectmaic.domain.center.enumeration.RoundStatus;
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
public class Round extends BaseEntity {

    @Id
    @Column(name = "round_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long roundId;

    @Column(name = "center_id")
	private Long centerId;

    @Column(name = "center_round_number")
    private Long centerRoundNumber;

	@Column(name = "passage")
	private Integer passage;

    @Column(name = "status")
    @Enumerated(value = EnumType.STRING)
    private RoundStatus status;

	public Round(Long roundId, Long centerId, Long centerRoundNumber,
		RoundStatus status) {
		this.roundId = roundId;
		this.centerId = centerId;
		this.centerRoundNumber = centerRoundNumber;
		this.passage = null;
		this.status = status;
	}

	public void startPick() {
		if (this.status == RoundStatus.READY) {
			this.status = RoundStatus.PICK;
		}
	}

	public void startDas(final int passage) {
		if (this.status == RoundStatus.PICK) {
			this.status = RoundStatus.DAS;
			this.passage = passage;
		}
	}
}
