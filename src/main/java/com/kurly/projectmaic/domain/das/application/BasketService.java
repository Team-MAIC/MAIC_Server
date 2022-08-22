package com.kurly.projectmaic.domain.das.application;

import com.kurly.projectmaic.domain.das.dto.response.BasketInfoResponse;
import com.kurly.projectmaic.domain.das.exception.DasTodoAlreadyException;
import org.springframework.stereotype.Service;

import com.kurly.projectmaic.domain.das.dao.DasTodoRepository;
import com.kurly.projectmaic.domain.das.domain.DasTodo;
import com.kurly.projectmaic.domain.das.dto.response.BasketColorResponse;
import com.kurly.projectmaic.domain.das.dto.response.DasTodoResponse;
import com.kurly.projectmaic.domain.das.enumeration.BasketStatus;
import com.kurly.projectmaic.domain.das.exception.DasTodoFoundNotException;
import com.kurly.projectmaic.global.common.response.CustomResponseEntity;
import com.kurly.projectmaic.global.common.response.ResponseCode;
import com.kurly.projectmaic.global.common.utils.RedisChannelUtils;
import com.kurly.projectmaic.global.queue.RedisPublisher;

import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BasketService {

	private final DasTodoRepository dasTodoRepository;
	private final RedisPublisher publisher;

	@Transactional
	public BasketColorResponse completeDasTodo(long dasTodoId, final double basketWeight) {
		if (dasTodoId == 0) {
			return null;
		}

		DasTodo dasTodo = dasTodoRepository.findById(dasTodoId)
			.orElseThrow(() ->
				new DasTodoFoundNotException(ResponseCode.NOT_FOUND_DAS_TODO, String.format("dasTodoId : {}", dasTodoId)));

		if (dasTodo.getStatus() == BasketStatus.FINISH) {
			throw new DasTodoAlreadyException(ResponseCode.ALREADY_DAS_TODO, String.format("dasTodoId : {}", dasTodoId));
		}

		double basketDifference = Math.abs((basketWeight - dasTodo.getBasketWeight()));
		double totalWeight = dasTodo.getProductAmount() * dasTodo.getProductWeight();

		if (((totalWeight * 0.9) > basketDifference && dasTodo.getStatus() != BasketStatus.WRONG) ||
			((totalWeight * 1.1) < basketDifference && dasTodo.getStatus() != BasketStatus.WRONG)) {

			int num = (int) Math.round(Math.abs(basketDifference) / dasTodo.getProductWeight());

			dasTodoRepository.updateStatus(dasTodoId, BasketStatus.WRONG);

			pubDasTodoMessage(dasTodo, num);

			return new BasketColorResponse(
				dasTodoId,
				BasketStatus.WRONG,
				dasTodo.getBasketColor(),
				num
			);
		}

		dasTodoRepository.updateStatus(dasTodoId, BasketStatus.FINISH);
		dasTodoRepository.completeStatus(dasTodo);
		dasTodoRepository.updateWeight(dasTodo, basketWeight);

		DasTodo nextDasTodo = dasTodoRepository.nextDasTodo(dasTodo);

		if (nextDasTodo == null) {
			publisher.publish(
				RedisChannelUtils.getDasTodoTopic(dasTodo.getCenterId(), dasTodo.getPassage()),
				CustomResponseEntity.success(
					new BasketInfoResponse(
						dasTodo.getBasketNum(),
						null
					)
				));

			return null;
		}

		publisher.publish(
			RedisChannelUtils.getDasTodoTopic(nextDasTodo.getCenterId(), nextDasTodo.getPassage()),
			CustomResponseEntity.success(
				new BasketInfoResponse(
					nextDasTodo.getBasketNum(),
					new DasTodoResponse(
						nextDasTodo.getRoundId(),
						nextDasTodo.getProductId(),
						nextDasTodo.getProductName(),
						nextDasTodo.getProductAmount(),
						0,
						nextDasTodo.getBasketColor(),
						nextDasTodo.getStatus()
					)
				)
			));

		return new BasketColorResponse(
			nextDasTodo.getDasTodoId(),
			nextDasTodo.getStatus(),
			nextDasTodo.getBasketColor(),
			nextDasTodo.getProductAmount()
		);
	}

	private void pubDasTodoMessage(DasTodo dasTodo, int num) {
		publisher.publish(
			RedisChannelUtils.getDasTodoTopic(dasTodo.getCenterId(), dasTodo.getPassage()),
			CustomResponseEntity.success(
				new BasketInfoResponse(
					dasTodo.getBasketNum(),
					new DasTodoResponse(
						dasTodo.getRoundId(),
						dasTodo.getProductId(),
						dasTodo.getProductName(),
						dasTodo.getProductAmount(),
						num,
						dasTodo.getBasketColor(),
						BasketStatus.WRONG
					)
				)
			));
	}
}
