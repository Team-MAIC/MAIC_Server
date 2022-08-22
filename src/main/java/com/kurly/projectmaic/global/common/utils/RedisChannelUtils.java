package com.kurly.projectmaic.global.common.utils;

import org.springframework.data.redis.listener.ChannelTopic;

import com.kurly.projectmaic.domain.model.CenterProductArea;

public class RedisChannelUtils {

	private static final String PICK_TODOS_TOPIC_FORMAT = "pick/todos/%s/%s";
	private static final String DAS_TODOS_TOPIC_FORMAT = "das/todos/%s/%s";
	private static final String DAS_BASKETS_TOPIC_FORMAT = "das/todos/%s/%s/%s";

	public static ChannelTopic getPickTodoTopic(final long roundId, final CenterProductArea area) {
		String channelName = String.format(PICK_TODOS_TOPIC_FORMAT, roundId, area);

		return new ChannelTopic(channelName);
	}

	public static String getPickTodoTopicName(final long roundId, final CenterProductArea area) {
		return String.format(PICK_TODOS_TOPIC_FORMAT, roundId, area);
	}

	public static ChannelTopic getDasTodoTopic(final long centerId, final int passage) {
		String channelName = String.format(DAS_TODOS_TOPIC_FORMAT, centerId, passage);

		return new ChannelTopic(channelName);
	}

	public static String getDasTodoTopicName(final long centerId, final int passage) {
		return String.format(DAS_TODOS_TOPIC_FORMAT, centerId, passage);
	}

	public static ChannelTopic getDasBasketTopic(final long centerId, final int passage, final int basketNum) {
		String channelName = String.format(DAS_BASKETS_TOPIC_FORMAT, centerId, passage, basketNum);

		return new ChannelTopic(channelName);
	}

	public static String getDasBasketTopicName(final long centerId, final int passage, final int basketNum) {
		return String.format(DAS_BASKETS_TOPIC_FORMAT, centerId, passage, basketNum);
	}
}
