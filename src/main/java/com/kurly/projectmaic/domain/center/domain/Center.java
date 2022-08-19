package com.kurly.projectmaic.domain.center.domain;

import com.kurly.projectmaic.domain.model.BaseEntity;
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
public class Center extends BaseEntity {

    @Id
    @Column(name = "center_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long centerId;

    @Column(name = "center_name")
    private String centerName;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "center")
    private List<Worker> workers = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "center")
    private List<CenterProduct> products = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "center")
    private List<Round> rounds = new ArrayList<>();
}
