package com.kurly.projectmaic.domain.center.api;

import com.kurly.projectmaic.domain.center.application.WorkerService;
import com.kurly.projectmaic.domain.center.dto.WorkerInfoResponse;
import com.kurly.projectmaic.global.common.response.CustomResponseEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
