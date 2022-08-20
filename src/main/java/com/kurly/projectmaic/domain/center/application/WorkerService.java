package com.kurly.projectmaic.domain.center.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kurly.projectmaic.domain.center.dao.RedisWorkerRepository;
import com.kurly.projectmaic.domain.center.dao.WorkerRepository;
import com.kurly.projectmaic.domain.center.domain.Center;
import com.kurly.projectmaic.domain.center.domain.Worker;
import com.kurly.projectmaic.domain.center.dto.WorkerInfoDto;
import com.kurly.projectmaic.domain.center.dto.response.WorkerInfoResponse;
import com.kurly.projectmaic.domain.center.exception.WorkerNotFoundException;
import com.kurly.projectmaic.global.common.response.ResponseCode;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class WorkerService {

    private final WorkerRepository workerRepository;
	private final RedisWorkerRepository redisWorkerRepository;

    @Transactional()
    public WorkerInfoResponse getWorkerInfo(final long workerId) {

		WorkerInfoDto workerInfoDto = redisWorkerRepository.getWorkerInfo(workerId);

		if (workerInfoDto == null) {
			Worker worker = workerRepository.findById(workerId)
				.orElseThrow(() ->
					new WorkerNotFoundException(ResponseCode.NOT_FOUND_WORKER,
						String.format("workerId : %s", workerId)));

			Center center = null;

			if (worker.getCenter() != null) {
				center = worker.getCenter();
			}

			workerInfoDto = new WorkerInfoDto(
				worker.getWorkerId(),
				(center != null) ? center.getCenterId() : null,
				(center != null) ? center.getCenterName() : null,
				worker.getRole(),
				worker.getPassage(),
				worker.getArea()
			);

			redisWorkerRepository.putWorkerInfo(workerInfoDto);
		}

		return new WorkerInfoResponse(
			workerInfoDto.getWorkerId(),
			workerInfoDto.getCenterId(),
			workerInfoDto.getCenterName(),
			workerInfoDto.getRole(),
			workerInfoDto.getPassage(),
			workerInfoDto.getArea()
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
