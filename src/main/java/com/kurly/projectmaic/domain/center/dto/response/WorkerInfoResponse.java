package com.kurly.projectmaic.domain.center.dto.response;

import com.kurly.projectmaic.domain.center.enumeration.WorkerRole;
import com.kurly.projectmaic.domain.model.CenterProductArea;

public record WorkerInfoResponse(
        long workerId,
        Long centerId,
        String centerName,
        WorkerRole role,
        Integer passage,
        CenterProductArea area
) {
}
