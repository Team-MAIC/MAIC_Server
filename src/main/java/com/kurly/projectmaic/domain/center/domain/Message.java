package com.kurly.projectmaic.domain.center.domain;

import com.kurly.projectmaic.domain.model.BaseEntity;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Message extends BaseEntity {

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

	@Column(name = "is_visible")
	private Boolean isVisible;

	public Message(Long workerId, String content, String fullLocation) {
		this.workerId = workerId;
		this.content = content;
		this.fullLocation = fullLocation;
		this.isVisible = true;
	}

	public void visible() {
		this.isVisible = false;
	}
}
