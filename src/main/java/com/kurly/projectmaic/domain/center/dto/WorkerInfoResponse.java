package com.kurly.projectmaic.domain.center.dto;

import com.kurly.projectmaic.domain.center.enumeration.WorkerRole;
import com.kurly.projectmaic.domain.model.CenterProductArea;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class WorkerInfoResponse {

    private final long workerId;
    private final Long centerId;
    private final String centerName;
    private final WorkerRole role;
    private final Integer passage;
    private final CenterProductArea area;
}
