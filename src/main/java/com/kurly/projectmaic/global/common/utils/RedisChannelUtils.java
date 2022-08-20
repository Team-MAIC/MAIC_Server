package com.kurly.projectmaic.global.common.utils;

import org.springframework.data.redis.listener.ChannelTopic;

import com.kurly.projectmaic.domain.model.CenterProductArea;

public class RedisChannelUtils {

	private static final String PICK_TODOS_TOPIC_FORMAT = "pick/todos/%s/%s";

	public static ChannelTopic getPickTodoTopic(final long roundId, final CenterProductArea area) {
		String channelName = String.format(PICK_TODOS_TOPIC_FORMAT, roundId, area);

		return new ChannelTopic(channelName);
	}
}
