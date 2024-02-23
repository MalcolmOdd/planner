package com.l2o.planner.db;

import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

import com.l2o.planner.dto.Task;
import com.l2o.planner.dto.TaskResponse;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity(name = "task")
public class DbTask {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(name = "tasktype_id")
    private UUID tasktypeId;
    @Column(name = "start_ts")
    private Instant start;
    @Column(name = "end_ts")
    private Instant end;

    public DbTask() {
    }

    public DbTask(Task task) {
	copyFrom(task);
    }

    @Override
    public int hashCode() {
	return Objects.hash(end, id, start, tasktypeId);
    }

    @Override
    public boolean equals(Object obj) {
	if (this == obj)
	    return true;
	if (obj == null)
	    return false;
	if (getClass() != obj.getClass())
	    return false;
	DbTask other = (DbTask) obj;
	return Objects.equals(end, other.end) && Objects.equals(id, other.id) && Objects.equals(start, other.start)
		&& Objects.equals(tasktypeId, other.tasktypeId);
    }

    public DbTask copyFrom(Task task) {
	this.tasktypeId = task.tasktypeId;
	this.start = task.start;
	this.end = task.end;
	return this;
    }

    public TaskResponse toResponse() {
	TaskResponse taskResult = new TaskResponse();
	taskResult.id = id;
	taskResult.tasktypeId = tasktypeId;
	taskResult.start = start;
	taskResult.end = end;
	return taskResult;
    }

    public UUID getId() {
	return id;
    }

    public void setId(UUID id) {
	this.id = id;
    }

    public UUID getTasktypeId() {
	return tasktypeId;
    }

    public void setTasktypeId(UUID tasktype_id) {
	this.tasktypeId = tasktype_id;
    }

    public Instant getStart() {
	return start;
    }

    public void setStart(Instant start) {
	this.start = start;
    }

    public Instant getEnd() {
	return end;
    }

    public void setEnd(Instant end) {
	this.end = end;
    }
}
