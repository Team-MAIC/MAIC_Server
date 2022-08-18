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

    @ManyToOne
    @JoinColumn(name = "center_id")
    private Center center;

    @Column(name = "center_round_number")
    private Integer centerRoundNumber;

    @Column(name = "status")
    @Enumerated(value = EnumType.STRING)
    private RoundStatus status;
}
