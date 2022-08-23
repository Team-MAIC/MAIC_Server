package com.kurly.projectmaic.domain.center.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Message {

	@Id
	@Column(name = "message_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long messageId;

	@Column(name = "worker_id")
	private Long workerId;

	@Column(name = "content")
	private String content;

	@Column(name = "full_location")
	private String fullLocation;

	public Message(Long workerId, String content, String fullLocation) {
		this.workerId = workerId;
		this.content = content;
		this.fullLocation = fullLocation;
	}
}
