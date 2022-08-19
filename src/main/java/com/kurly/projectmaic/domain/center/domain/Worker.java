package com.kurly.projectmaic.domain.center.domain;

import com.kurly.projectmaic.domain.model.CenterProductArea;
import com.kurly.projectmaic.domain.center.enumeration.WorkerRole;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Worker {

    @Id
    @Column(name = "worker_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long workerId;

    @ManyToOne
    @JoinColumn(name = "center_id")
    private Center center;

    @Column(name = "role")
    @Enumerated(value = EnumType.STRING)
    private WorkerRole role;

    @Column(name = "passage")
    private Integer passage;

    @Column(name = "area")
    @Enumerated(value = EnumType.STRING)
    private CenterProductArea area;
}
