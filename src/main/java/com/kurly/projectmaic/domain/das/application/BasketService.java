package com.kurly.projectmaic.domain.das.application;

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

@Service
@RequiredArgsConstructor
public class BasketService {

	private final DasTodoRepository dasTodoRepository;
	private final RedisPublisher publisher;

	public BasketColorResponse completeDasTodo(long dasTodoId, final double basketWeight) {
		if (dasTodoId == 0) {
			return null;
		}

		DasTodo dasTodo = dasTodoRepository.findById(dasTodoId)
			.orElseThrow(() ->
				new DasTodoFoundNotException(ResponseCode.NOT_FOUND_DAS_TODO, String.format("dasTodoId : {}", dasTodoId)));

		double basketDifference = Math.abs((basketWeight - dasTodo.getBasketWeight()));
		double totalWeight = dasTodo.getProductAmount() * dasTodo.getProductWeight();
		double differenceRatio = Math.abs(basketDifference - totalWeight) / totalWeight;

		if (differenceRatio >= 0.6) {
			int num = (int) Math.floor(differenceRatio);
			double remainder = (differenceRatio % 1);
			num += (remainder >= 0.6) ? 1 : 0;

			dasTodoRepository.updateStatus(dasTodoId, BasketStatus.WRONG);

			publisher.publish(
				RedisChannelUtils.getDasTodoTopic(dasTodo.getCenterId(), dasTodo.getPassage()),
				CustomResponseEntity.success(
					new DasTodoResponse(
						dasTodo.getRoundId(),
						dasTodo.getProductId(),
						dasTodo.getProductName(),
						dasTodo.getProductAmount(),
						num,
						dasTodo.getBasketColor(),
						BasketStatus.WRONG
					)
				));

			return new BasketColorResponse(
				dasTodoId,
				BasketStatus.WRONG,
				dasTodo.getBasketColor(),
				num
			);
		}

		var a = dasTodoRepository.nextDasTodo(dasTodo);

		return null;
	}
}
