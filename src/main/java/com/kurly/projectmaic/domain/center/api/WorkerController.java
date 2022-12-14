package com.kurly.projectmaic.domain.center.api;

import com.kurly.projectmaic.domain.center.application.WorkerService;
import com.kurly.projectmaic.domain.center.dto.request.DeviceTokenUpdateRequest;
import com.kurly.projectmaic.domain.center.dto.response.WorkerInfoResponse;
import com.kurly.projectmaic.global.common.response.CustomResponseEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import static com.kurly.projectmaic.global.common.constant.WorkerIdHeader.WORKER_ID;

@RestController
@RequestMapping("/workers")
@RequiredArgsConstructor
public class WorkerController {

    private final WorkerService workerService;

    @GetMapping
    public CustomResponseEntity<WorkerInfoResponse> getWorkerInfo(
            @RequestHeader(WORKER_ID) final long workerId
    ) {
        return CustomResponseEntity.success(workerService.getWorkerInfo(workerId));
    }

    @PutMapping("/device-token")
    public CustomResponseEntity<Void> updateDeviceToken(
            @RequestHeader(WORKER_ID) final long workerId,
            @RequestBody final DeviceTokenUpdateRequest request
    ) {
        workerService.updateDeviceToken(workerId, request.deviceToken());

        return CustomResponseEntity.success();
    }
}
