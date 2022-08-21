// package com.kurly.projectmaic.global.config;
//
// import javax.annotation.PostConstruct;
//
// import org.springframework.context.annotation.Configuration;
// import org.springframework.data.redis.listener.ChannelTopic;
// import org.springframework.data.redis.listener.RedisMessageListenerContainer;
//
// import com.kurly.projectmaic.global.common.constant.RedisTopic;
// import com.kurly.projectmaic.global.queue.RedisSubscriber;
//
// import lombok.RequiredArgsConstructor;
//
// @Configuration
// @RequiredArgsConstructor
// public class QueueInitConfig {
//
// 	private final RedisMessageListenerContainer container;
// 	private final RedisSubscriber subscriber;
//
// 	@PostConstruct
// 	private void init() {
// 		container.addMessageListener(subscriber, new ChannelTopic(RedisTopic.SUB));
// 		container.addMessageListener(subscriber, new ChannelTopic(RedisTopic.UNSUB));
// 	}
// }
