package com.kurly.projectmaic.domain.center.application;

import com.kurly.projectmaic.domain.center.dao.MessageRepository;
import com.kurly.projectmaic.domain.center.domain.Message;
import com.kurly.projectmaic.domain.center.dto.response.MessageResponse;
import com.kurly.projectmaic.domain.center.dto.response.MessagesResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kurly.projectmaic.domain.center.dao.WorkerRepository;
import com.kurly.projectmaic.domain.center.domain.Center;
import com.kurly.projectmaic.domain.center.domain.Worker;
import com.kurly.projectmaic.domain.center.dto.response.WorkerInfoResponse;
import com.kurly.projectmaic.domain.center.exception.WorkerNotFoundException;
import com.kurly.projectmaic.global.common.response.ResponseCode;

import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class WorkerService {

    private final WorkerRepository workerRepository;

    @Transactional()
    public WorkerInfoResponse getWorkerInfo(final long workerId) {
		Worker worker = workerRepository.findById(workerId)
			.orElseThrow(() ->
				new WorkerNotFoundException(ResponseCode.NOT_FOUND_WORKER,
					String.format("workerId : %s", workerId)));

		Center center = null;

		if (worker.getCenter() != null) {
			center = worker.getCenter();
		}

		return new WorkerInfoResponse(
			worker.getWorkerId(),
			(center != null) ? center.getCenterId() : null,
			(center != null) ? center.getCenterName() : null,
			worker.getRole(),
			worker.getPassage(),
			worker.getArea()
		);
    }

    @Transactional
    public void updateDeviceToken(final long workerId, final String deviceToken) {
        Worker worker = workerRepository.findById(workerId)
                .orElseThrow(() ->
                        new WorkerNotFoundException(ResponseCode.NOT_FOUND_WORKER,
                                String.format("workerId : %s", workerId)));

        worker.updateDeviceToken(deviceToken);
        workerRepository.save(worker);
    }
}
