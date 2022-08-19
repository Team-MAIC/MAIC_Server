package com.kurly.projectmaic.domain.model;

import lombok.Getter;

@Getter
public enum CenterProductArea {
    A(1), B(2),
    C(3), D(4),
    E(5), F(6),
    G(7), H(8),
    I(9), J(10);

    private final int code;

    CenterProductArea(int code) {
        this.code = code;
    }
}
