package com.kurly.projectmaic.global.queue;

import javax.annotation.PostConstruct;

import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kurly.projectmaic.domain.pick.dto.response.PickTodoCompleteResponse;
import com.kurly.projectmaic.global.common.constant.RedisTopic;
import com.kurly.projectmaic.global.common.response.CustomResponseEntity;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class RedisSubscriber implements MessageListener {

	private final ObjectMapper objectMapper;
	private final RedisTemplate<String, Object> redisTemplate;
	private final SimpMessageSendingOperations messageSender;
	private final RedisMessageListenerContainer container;

	@PostConstruct
	private void init() {
		container.addMessageListener(this, new ChannelTopic(RedisTopic.SUB));
		container.addMessageListener(this, new ChannelTopic(RedisTopic.UNSUB));
	}

	@Override
	public void onMessage(Message message, byte[] pattern) {

		String key = redisTemplate.getStringSerializer().deserialize(message.getChannel());
		String body = redisTemplate.getStringSerializer().deserialize(message.getBody());

		try {
			assert key != null;
			assert body != null;

			CustomResponseEntity<?> response = objectMapper.readValue(body, CustomResponseEntity.class);

			if (RedisTopic.SUB.equals(key)) {
				container.addMessageListener(this, new ChannelTopic((String) response.getData()));
				return;
			}

			if (RedisTopic.UNSUB.equals(key)) {
				String topicKey = (String) response.getData();

				container.removeMessageListener(this, new ChannelTopic(topicKey));

				if (topicKey.startsWith("das/todos")) {
					for (int i = 0; i < 5; i++) {
						container.removeMessageListener(this, new ChannelTopic(topicKey + i));
					}
				}
				return;
			}

			messageSender.convertAndSend("/sub/" + key, response);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
	}
}
