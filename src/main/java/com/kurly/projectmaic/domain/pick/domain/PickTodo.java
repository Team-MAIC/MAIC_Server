package com.kurly.projectmaic.domain.pick.domain;

import com.kurly.projectmaic.domain.model.BaseEntity;
import com.kurly.projectmaic.domain.model.CenterProductArea;
import com.kurly.projectmaic.domain.model.StatusType;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class PickTodo extends BaseEntity {

    @Id
    @Column(name = "pick_todo_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pickTodoId;

    @Column(name = "round_id")
    private Long roundId;

    @Column(name = "product_id")
    private Long productId;

	@Column(name = "product_name")
	private String productName;

    @Column(name = "worker_id")
    private Long workerId;

    @Column(name = "area")
    @Enumerated(value = EnumType.STRING)
    private CenterProductArea area;

	@Column(name = "line")
	private Integer line;

    @Column(name = "location")
    private Integer location;

    @Column(name = "amount")
    private Long amount;

    @Column(name = "status")
    @Enumerated(value = EnumType.STRING)
    private StatusType status;

	public void complete(long workerId) {
		this.workerId = workerId;
		this.status = StatusType.FINISH;
	}
}
